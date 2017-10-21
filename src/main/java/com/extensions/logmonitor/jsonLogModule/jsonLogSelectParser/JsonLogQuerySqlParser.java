package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang.time.StopWatch;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.JsonContentAnalyzer;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.JsonLogSqlAnalyzer;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlLexer;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.LogDataCreator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class JsonLogQuerySqlParser {

	public static QueryExecutor createQueryExecutor(String jsonLogQuerySql) {
		try {
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(jsonLogQuerySql.getBytes()));
			jsonLogSqlLexer lexer = new jsonLogSqlLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			jsonLogSqlParser parser = new jsonLogSqlParser(tokens);
			ParseTree tree = parser.select_expression();
			ParseTreeWalker walker = new ParseTreeWalker();
			JsonLogSqlAnalyzer jsonLogSqlAnalyzer = new JsonLogSqlAnalyzer();
			walker.walk(jsonLogSqlAnalyzer, tree);
			QueryExecutor queryExecutor = jsonLogSqlAnalyzer.queryExecutor;
			return queryExecutor;
		} catch (Exception e) {
			log.error("{} error while parser:" + jsonLogQuerySql, e);
			return null;
		}
	}

	/**
	 * @param queryExecutor
	 * @param queryResultDataItem
	 * @param jsonLogString
	 */
	public static void doJsonLogStrAnalyzer(List<QueryExecutor> queryExecutores, byte[] bytes) {
		try {
			// 词语、语法解析，生成抽象语法树
			// ANTLRInputStream input = new ANTLRFileStream(
			// "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles/test.log",
			// "utf-8");
			ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(bytes));
			com.extensions.logmonitor.jsonContentParseies.copy.jsonLexer lexer = new com.extensions.logmonitor.jsonContentParseies.copy.jsonLexer(
					input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			com.extensions.logmonitor.jsonContentParseies.copy.jsonParser parser = new com.extensions.logmonitor.jsonContentParseies.copy.jsonParser(
					tokens);
			ParseTree tree = parser.jsonFile();
			ParseTreeWalker walker = new ParseTreeWalker();
			JsonContentAnalyzer jsonLogSqlAnalyzer = new JsonContentAnalyzer(queryExecutores);
			walker.walk(jsonLogSqlAnalyzer, tree);
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
	}

	/**
	 * @param queryExecutor
	 * @param queryResultDataItem
	 * @param jsonLogString
	 */
	public static void doJsonLogStrAnalyzer(List<QueryExecutor> queryExecutores) {
		try {
			// 词语、语法解析，生成抽象语法树
			CharStream input = new UnbufferedCharStream(
					new FileInputStream("/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles/test.log"));
			jsonLexer lexer = new jsonLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			TokenStream tokens = new UnbufferedTokenStream<>(lexer);
			jsonParser parser = new jsonParser(tokens);
			parser.setBuildParseTree(false);
			parser.jsonFile();
			// ParseTreeWalker walker = new ParseTreeWalker();
			// JsonContentAnalyzer jsonLogSqlAnalyzer = new
			// JsonContentAnalyzer(queryExecutores);
			// walker.walk(jsonLogSqlAnalyzer, tree);
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
	}

	public static void main(String[] args) throws IOException {
		String logSql = "select name,age from testJson where age between 50 and 95 order by age desc limit 0,10";
		String logSql2 = "select name,age from testJson where age between 10 and 25 order by age asc limit 0,10";
		QueryExecutor executor = createQueryExecutor(logSql);
		QueryExecutor executor2 = createQueryExecutor(logSql2);
		List<QueryExecutor> executores = new ArrayList<>();
		executores.add(executor);
		executores.add(executor2);
		JsonLogDataQueryHandler handler = new JsonLogDataQueryHandler(executores);
		StopWatch watch = new StopWatch();
		watch.start();
		watch.split();
		for (int i = 0; i < 10000; i++) {
			handler.wirteString(LogDataCreator.createLogLines());
		}
		watch.stop();
		System.out.println("all use time:" + watch.getTime());
		for (QueryExecutor qe : executores) {
			List<QueryResultDataItem> doHandle = qe.doHandle();
			for (QueryResultDataItem qrdi : doHandle) {
				System.out.println(qrdi.getRecordId() + "--qrdi:" + qrdi.getQueryResult());
			}
		}
	}

	public static byte[] readStrings() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		LogDataCreator.createLogFiles(out, 2000);
		return out.toByteArray();
	}

}
