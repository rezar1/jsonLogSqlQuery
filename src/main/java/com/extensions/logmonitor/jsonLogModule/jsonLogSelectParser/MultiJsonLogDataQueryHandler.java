package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月18日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class MultiJsonLogDataQueryHandler {

	private static Object endObj = new Object();

	private int parseTreeThreadCount = 4;
	private int visitParseTreeThreadCount = 1;

	private Thread[] parseThreads;
	private Thread visitThread;
	private boolean isOver;
	private BlockingQueue<String> lineStrings = new ArrayBlockingQueue<>(2);
	private BlockingQueue<Object> parseTreeQueue = new ArrayBlockingQueue<>(8);
	private JsonLogDataQueryHandler handler;
	private Semaphore stopSema;

	public MultiJsonLogDataQueryHandler(JsonLogDataQueryHandler handler) {
		this.handler = handler;
		this.stopSema = new Semaphore(this.parseTreeThreadCount + this.visitParseTreeThreadCount);
		this.parseThreads = new Thread[parseTreeThreadCount];
		for (int i = 0; i < this.parseThreads.length; i++) {
			this.parseThreads[i] = new ParseTreeThread(this, lineStrings, parseTreeQueue, this.stopSema);
		}
		this.visitThread = new VisitorTreeThread(parseTreeQueue, this.handler, this.stopSema);
	}

	/**
	 * 
	 */
	public void startWork() {
		this.isOver = false;
		for (int i = 0; i < this.parseThreads.length; i++) {
			this.parseThreads[i].start();
		}
		this.visitThread.start();
	}

	public void writeString(String line) {
		try {
			this.lineStrings.put(line);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class VisitorTreeThread extends Thread {

		private BlockingQueue<Object> parseTreeQueue = null;
		private JsonLogDataQueryHandler handler;
		private Semaphore stopSema;

		public VisitorTreeThread(BlockingQueue<Object> parseTreeQueue, JsonLogDataQueryHandler handler,
				Semaphore stopSema) {
			this.parseTreeQueue = parseTreeQueue;
			this.handler = handler;
			this.stopSema = stopSema;
		}

		public void run() {
			try {
				this.stopSema.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					Object take = this.parseTreeQueue.take();
					if (take == endObj) {
						break;
					}
					handler.visitTree((ParseTree) take);
					take = null;
				} catch (InterruptedException e) {
				}
			}
			this.stopSema.release();
			System.out.println("visit stop====");
		}

	}

	public static class ParseTreeThread extends Thread {
		private BlockingQueue<String> lineStrings = null;
		private BlockingQueue<Object> parseTreeQueue = null;
		private MultiJsonLogDataQueryHandler handler;
		private Semaphore stopSema;

		public ParseTreeThread(MultiJsonLogDataQueryHandler handler, BlockingQueue<String> lineStrings,
				BlockingQueue<Object> parseTreeQueue, Semaphore stopSema) {
			this.handler = handler;
			this.lineStrings = lineStrings;
			this.parseTreeQueue = parseTreeQueue;
			this.stopSema = stopSema;
		}

		@Override
		public void run() {
			try {
				this.stopSema.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while (!handler.isOver()) {
				try {
					String take = this.lineStrings.take();
					try {
						ByteArrayInputStream bais = new ByteArrayInputStream(take.trim().getBytes());
						// 词语、语法解析，生成抽象语法树
						ANTLRInputStream input = new ANTLRInputStream(bais);
						jsonLexer lexer = new jsonLexer(input);
						CommonTokenStream tokens = new CommonTokenStream(lexer);
						jsonParser parser = new jsonParser(tokens);
						ParseTree tree = parser.jsonFile();
						this.parseTreeQueue.put(tree);
						bais.reset();
					} catch (Exception e) {
						log.info("can not parse linelog:{}", take);
						// log.info("error while parser jsonLogString:{} ", e);
					}
				} catch (InterruptedException e) {
					if (this.handler.isOver()) {
						break;
					}
				}
			}
			System.out.println("stop====");
			this.stopSema.release();
		}

	}

	/**
	 * @return
	 */
	public boolean isOver() {
		return this.isOver;
	}

	public void over() {
		this.isOver = true;
		for (Thread thread : this.parseThreads) {
			thread.interrupt();
		}
		try {
			this.parseTreeQueue.put(endObj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			this.stopSema.acquire(this.parseTreeThreadCount + this.visitParseTreeThreadCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
