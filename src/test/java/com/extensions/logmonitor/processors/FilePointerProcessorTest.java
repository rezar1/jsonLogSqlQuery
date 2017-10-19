package com.extensions.logmonitor.processors;

import static com.extensions.logmonitor.Constants.FILEPOINTER_FILENAME;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import com.extensions.logmonitor.processors.FilePointer;
import com.extensions.logmonitor.processors.FilePointerProcessor;

public class FilePointerProcessorTest {

	private FilePointerProcessor classUnderTest;

	@Test
	public void testUpdateFilePointerFileIsPersisted() {
		classUnderTest = new FilePointerProcessor();
		String logPath = "src/test/resources/test-log-3.log";
		FilePointer origFilePointer = classUnderTest.getFilePointer(logPath, logPath);
		assertEquals(0, origFilePointer.getLastReadPosition().get());
		// lets update the filePointer
		long newFilePointer = 1234;
		String newFilename = "src/test/resources/test-log-4.log";
		origFilePointer.setFilename(newFilename);
		origFilePointer.updateLastReadPosition(newFilePointer);
		classUnderTest.updateFilePointerFile();

		// re-initialise the filepointer
		// it should pick up from the file
		classUnderTest = new FilePointerProcessor();
		FilePointer result = classUnderTest.getFilePointer(logPath, logPath);
		assertEquals(newFilePointer, result.getLastReadPosition().get());
		assertEquals(newFilename, result.getFilename());
	}

	@After
	public void deleteFilePointerFile() throws Exception {
		File filePointerFile = new File(
				"./target/classes/com/appdynamics/extensions/logmonitor/" + FILEPOINTER_FILENAME);

		if (filePointerFile.exists()) {
			filePointerFile.delete();
		}
	}
}
