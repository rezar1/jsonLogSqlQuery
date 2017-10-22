package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.sortExecute;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.Check64bitsJVM;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.Constants;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.IntHashMap;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.LeafFileBlockStore;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class FileStore {

	private static final Logger log = Logger.getLogger(LeafFileBlockStore.class);
	/**
	 * File associated to this store
	 */
	private File file = null;
	/**
	 * RamdomAccessFile for this store
	 */
	private RandomAccessFile raf = null;
	/**
	 * FileChannel for this store
	 */
	private FileChannel fileChannel = null;
	/**
	 * Support for mmap
	 */
	private boolean useMmap = false;
	/**
	 * Support for Locking
	 */
	private boolean useLock = false;
	/**
	 * In Valid State?
	 */
	private boolean validState = false;
	/**
	 * Callback called when flush buffers to disk
	 */
	private CallbackSync callback = null;
	/**
	 * File Lock
	 */
	private FileLock lock = null;

	/**
	 * Instantiate FileBlockStore
	 * 
	 * @param file
	 *            name of file to open
	 * @param blockSize
	 *            size of block
	 * @param isDirect
	 *            use DirectByteBuffer or HeapByteBuffer?
	 */
	public FileStore(final String file) {
		this(new File(file));
	}

	/**
	 * Instantiate FileBlockStore
	 * 
	 * @param file
	 *            file to open
	 * @param blockSize
	 *            size of block
	 * @param isDirect
	 *            use DirectByteBuffer or HeapByteBuffer?
	 */
	public FileStore(final File file) {
		this.file = file;
		if (this.file != null && this.file.exists()) {
			this.file.delete();
		}
	}

	// ========= Open / Close =========

	/**
	 * Open file for read/write
	 * 
	 * @return true if valid state
	 */
	public boolean open() {
		return open(false);
	}

	/**
	 * Open file
	 * 
	 * @param readOnly
	 *            open for readOnly?
	 * @return true if valid state
	 */
	public boolean open(final boolean readOnly) {
		if (isOpen()) {
			close();
		}
		if (log.isDebugEnabled()) {
			log.debug("open(" + file + ")");
		}
		try {
			raf = new RandomAccessFile(file, readOnly ? "r" : "rw");
			fileChannel = raf.getChannel();
			if (useLock) {
				lock(readOnly);
			}
		} catch (Exception e) {
			log.error("Exception in open()", e);
			try {
				unlock();
			} catch (Exception ign) {
			}
			try {
				fileChannel.close();
			} catch (Exception ign) {
			}
			try {
				raf.close();
			} catch (Exception ign) {
			}
			raf = null;
			fileChannel = null;
		}
		validState = isOpen();
		return validState;
	}

	public void changeToRead() {
		try {
			this.fileChannel.position(0);
			this.byteBufferQueue = new ByteBufferQueue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close file
	 */
	public void close() {
		mmaps.clear(false);
		try {
			unlock();
		} catch (Exception ign) {
		}
		try {
			fileChannel.close();
		} catch (Exception ign) {
		}
		try {
			raf.close();
		} catch (Exception ign) {
		}
		fileChannel = null;
		raf = null;
		validState = false;
	}

	// ========= Locking ======

	/**
	 * Lock file
	 * 
	 * @throws IOException
	 */
	public boolean lock(final boolean readOnly) throws IOException {
		if (isOpen() && lock == null) {
			lock = fileChannel.lock(0L, Long.MAX_VALUE, readOnly);
			return true;
		}
		return false;
	}

	/**
	 * Unlock file
	 * 
	 * @throws IOException
	 */
	public boolean unlock() throws IOException {
		if (lock != null) {
			lock.release();
			lock = null;
			return true;
		}
		return false;
	}

	// ========= Info =========

	/**
	 * @return true if file is open
	 */
	public boolean isOpen() {
		try {
			if (fileChannel != null) {
				return fileChannel.isOpen();
			}
		} catch (Exception ign) {
		}
		return false;
	}

	// ========= Destroy =========

	/**
	 * Truncate file
	 */
	public void clear() {
		if (!validState) {
			throw new InvalidStateException();
		}
		try {
			fileChannel.position(0).truncate(0);
			sync();
		} catch (Exception e) {
			log.error("Exception in clear()", e);
		}
	}

	/**
	 * Delete file
	 */
	public void delete() {
		close();
		try {
			file.delete();
		} catch (Exception ign) {
		}
	}

	// ========= Operations =========

	/**
	 * set callback called when buffers where synched to disk
	 * 
	 * @param callback
	 */
	public void setCallback(final CallbackSync callback) {
		this.callback = callback;
	}

	private ByteBufferQueue byteBufferQueue;

	/**
	 * Read block from file
	 * 
	 * @param index
	 *            of block
	 * @return ByteBuffer from pool with data
	 */
	public ByteBuffer read() {
		if (!validState) {
			throw new InvalidStateException();
		}
		if (byteBufferQueue == null) {
			byteBufferQueue = new ByteBufferQueue();
		}
		return byteBufferQueue.poll();
	}

	private class ByteBufferQueue {
		private ByteBuffer seeByteBuffer = ByteBuffer.allocate(4);
		private Queue<ByteBuffer> byteBufferQueue = new ArrayBlockingQueue<>(100);
		int batchSize = 100;
		boolean isReadOver;

		public ByteBuffer poll() {
			if (!isReadOver && byteBufferQueue.size() < batchSize) {
				int readSize = batchSize - this.byteBufferQueue.size();
				for (int i = 0; i < readSize; i++) {
					try {
						seeByteBuffer.clear();
						int read = fileChannel.read(seeByteBuffer);
						if (read == -1) {
							isReadOver = true;
							break;
						}
						seeByteBuffer.rewind();
						int size = this.seeByteBuffer.getInt();
						if (useMmap) {
							final MappedByteBuffer mbb = getMmapForIndex((int) fileChannel.position(), size);
							if (mbb != null) {
								byteBufferQueue.add(mbb);
							}
						} else {
							final ByteBuffer buf = ByteBuffer.allocate(size);
							fileChannel.read(buf);
							buf.rewind();
							byteBufferQueue.add(buf);
						}
					} catch (Exception e) {
						isReadOver = true;
						e.printStackTrace();
						break;
					}
				}
			}
			return this.byteBufferQueue.poll();
		}

	}

	/**
	 * Write from buf to file
	 * 
	 * @param index
	 *            of block
	 * @param buf
	 *            ByteBuffer to write
	 * @return true if write is OK
	 */
	public boolean set(final ByteBuffer buf) {
		if (!validState) {
			throw new InvalidStateException();
		}
		try {
			if (useMmap) {
				final MappedByteBuffer mbb = getMmapForIndex((int) this.fileChannel.position(), buf.capacity());
				if (mbb != null) {
					mbb.put(buf);
					return true;
				}
			}
			fileChannel.position(this.fileChannel.position()).write(buf);
			return true;
		} catch (Exception e) {
			log.error("Exception in set()", e);
		}
		return false;
	}

	/**
	 * Forces any updates to this file to be written to the storage device that
	 * contains it.
	 */
	public void sync() {
		if (!validState) {
			throw new InvalidStateException();
		}
		if (useMmap) {
			syncAllMmaps();
		}
		if (fileChannel != null) {
			try {
				fileChannel.force(false);
			} catch (Exception ign) {
				ign.printStackTrace();
			}
		}
		if (callback != null) {
			callback.synched();
		}
	}

	public static interface CallbackSync {
		public void synched();
	}

	public static class StorageWriteBuffer {
		private final LeafFileBlockStore storage;
		private final int index;
		private final boolean mmaped;
		private ByteBuffer buf;

		private StorageWriteBuffer(final LeafFileBlockStore storage, final int index, final boolean mmaped,
				final ByteBuffer buf) {
			this.storage = storage;
			this.index = index;
			this.mmaped = mmaped;
			this.buf = buf;
		}

		public ByteBuffer buf() {
			return buf;
		}

		/**
		 * Save and release the buffer
		 * 
		 * @return successful operation?
		 */
		public boolean save() {
			if (mmaped) {
				return true;
			}
			final boolean ret = storage.set(index, buf);
			buf = null;
			return ret;
		}
	}

	// ========= Mmap ===============

	// 128KB
	@SuppressWarnings("rawtypes")
	private final IntHashMap<BufferReference> mmaps = new IntHashMap<BufferReference>(128, BufferReference.class);
	/**
	 * Comparator for write by Idx
	 */
	private Comparator<BufferReference<MappedByteBuffer>> comparatorByIdx = new Comparator<BufferReference<MappedByteBuffer>>() {
		@Override
		public int compare(final BufferReference<MappedByteBuffer> o1, final BufferReference<MappedByteBuffer> o2) {
			if (o1 == null) {
				if (o2 == null) {
					return 0; // o1 == null & o2 == null
				}
				return 1; // o1 == null & o2 != null
			}
			if (o2 == null) {
				return -1; // o1 != null & o2 == null
			}
			final int thisVal = (o1.idx < 0 ? -o1.idx : o1.idx);
			final int anotherVal = (o2.idx < 0 ? -o2.idx : o2.idx);
			return ((thisVal < anotherVal) ? -1 : ((thisVal == anotherVal) ? 0 : 1));
		}
	};

	/**
	 * Is enabled mmap for this store?
	 * 
	 * @return true/false
	 */
	public boolean useMmap() {
		return useMmap;
	}

	/**
	 * Enable mmap of files (default is not enabled), call before use
	 * {@link #open()}
	 * <p/>
	 * Recommended use of: {@link #enableMmapIfSupported()}
	 * <p/>
	 * <b>NOTE:</b> 32bit JVM can only address 2GB of memory, enable mmap can
	 * throw <b>java.lang.OutOfMemoryError: Map failed</b> exceptions
	 */
	public void enableMmap() {
		if (validState) {
			throw new InvalidStateException();
		}
		if (Check64bitsJVM.JVMis64bits()) {
			log.info("Enabled mmap on 64bits JVM");
		} else {
			log.warn("Enabled mmap on 32bits JVM, risk of: java.lang.OutOfMemoryError: Map failed");
		}
		useMmap = true;
	}

	/**
	 * Enable mmap of files (default is not enabled) if JVM is 64bits, call
	 * before use {@link #open()}
	 */
	public void enableMmapIfSupported() {
		if (validState) {
			throw new InvalidStateException();
		}
		useMmap = Check64bitsJVM.JVMis64bits();
		if (useMmap) {
			log.info("Enabled mmap on 64bits JVM");
		} else {
			log.info("Disabled mmap on 32bits JVM");
		}
	}

	/**
	 * Enable Lock of files (default is not enabled), call before use
	 * {@link #open()}
	 */
	public void enableLocking() {
		if (validState) {
			throw new InvalidStateException();
		}
		if (Boolean.getBoolean(Constants.PROP_IO_LOCKING)) {
			useLock = false;
			log.info("Disabled Locking in System Property (" + Constants.PROP_IO_LOCKING + ")");
		} else {
			useLock = true;
			log.info("Enabled Locking");
		}
	}

	public final MappedByteBuffer getMmapForIndex(final int mapIdx, int mapSize) {
		if (!validState) {
			throw new InvalidStateException();
		}
		try {
			@SuppressWarnings("unchecked")
			final Reference<MappedByteBuffer> bref = mmaps.get(mapIdx);
			MappedByteBuffer mbb = null;
			if (bref != null) {
				mbb = bref.get();
			}
			if (mbb == null) { // Create mmap
				mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, mapIdx, mapSize);
				// mbb.load();
				mmaps.put(mapIdx, new BufferReference<MappedByteBuffer>(mapIdx, mbb));
			} else {
				mbb.clear();
			}
			return mbb;
		} catch (IOException e) {
			log.error("IOException in getMmapForIndex(" + mapIdx + ")", e);
		}
		return null;
	}

	private void syncAllMmaps() {
		@SuppressWarnings("unchecked")
		final BufferReference<MappedByteBuffer>[] maps = mmaps.getValues();
		Arrays.sort(maps, comparatorByIdx);
		for (final Reference<MappedByteBuffer> ref : maps) {
			if (ref == null) {
				break;
			}
			final MappedByteBuffer mbb = ref.get();
			if (mbb != null) {
				try {
					mbb.force();
				} catch (Exception ign) {
				}
			}
		}
	}

	static class BufferReference<T extends MappedByteBuffer> extends SoftReference<T> {
		final int idx;

		public BufferReference(final int idx, final T referent) {
			super(referent);
			this.idx = idx;
		}
	}

	// ========= Exceptions =========

	/**
	 * Exception throwed when store is in invalid state (closed)
	 */
	public static class InvalidStateException extends RuntimeException {
		private static final long serialVersionUID = 42L;
	}

	// ========= END =========

}
