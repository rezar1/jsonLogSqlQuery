package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;

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
public class JsonLogDataQueryHandler {

	private ByteArrayOutputStream baos;
	// private int lineCount;
	private List<QueryExecutor> queryExecutors;

	public JsonLogDataQueryHandler(List<QueryExecutor> queryExecutors) {
		this.baos = new ByteArrayOutputStream();
		this.queryExecutors = queryExecutors;
	}

	public void wirteString(String lineLog) {
		this.doHandle(lineLog);
	}

	private void doHandle(String lineLog) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(lineLog.getBytes());
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(bais);
			com.extensions.logmonitor.jsonContentParseies.copy.jsonLexer lexer = new com.extensions.logmonitor.jsonContentParseies.copy.jsonLexer(
					input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			com.extensions.logmonitor.jsonContentParseies.copy.jsonParser parser = new com.extensions.logmonitor.jsonContentParseies.copy.jsonParser(
					tokens);
			ParseTree tree = parser.jsonFile();
			ParseTreeWalker walker = new ParseTreeWalker();
			com.extensions.logmonitor.jsonContentParseies.copy.JsonContentAnalyzer jsonLogSqlAnalyzer = new com.extensions.logmonitor.jsonContentParseies.copy.JsonContentAnalyzer(
					queryExecutors);
			walker.walk(jsonLogSqlAnalyzer, tree);
			bais.reset();
			this.baos.reset();
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
	}

	public void doAnalyzerResult() {
		// if (this.lineCount != 0) {
		// this.doHandle();
		// }
		for (QueryExecutor qe : this.queryExecutors) {
			List<QueryResultDataItem> doHandle = qe.doHandle();
			for (QueryResultDataItem qrdi : doHandle) {
				System.out.println(qrdi.getRecordId() + "\t" + qrdi.getQueryResult());
			}
		}
	}

}
