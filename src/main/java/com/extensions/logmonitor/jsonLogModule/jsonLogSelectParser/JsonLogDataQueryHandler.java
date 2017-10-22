package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.JsonContentAnalyzer;
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
	private List<QueryExecutor> queryExecutors;
	private JsonContentAnalyzer jsonLogSqlAnalyzer;

	public JsonLogDataQueryHandler(List<QueryExecutor> queryExecutors) {
		this.baos = new ByteArrayOutputStream();
		this.queryExecutors = queryExecutors;
		this.jsonLogSqlAnalyzer = new JsonContentAnalyzer(queryExecutors);
	}

	public void wirteString(String lineLog) {
		this.doHandle(lineLog);
	}

	private void doHandle(String lineLog) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(lineLog.getBytes());
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(bais);
			jsonLexer lexer = new jsonLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			jsonParser parser = new jsonParser(tokens);
			ParseTree tree = parser.jsonFile();
			ParseTreeWalker walker = new ParseTreeWalker();
			walker.walk(jsonLogSqlAnalyzer, tree);
			bais.reset();
			this.baos.reset();
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
	}

	public void doAnalyzerResult() {
		for (QueryExecutor qe : this.queryExecutors) {
			List<QueryResultDataItem> doHandle = qe.doHandle();
			for (QueryResultDataItem qrdi : doHandle) {
				System.out.println(qrdi.getRecordId() + "\t" + qrdi.getQueryResult());
			}
		}
	}

}
