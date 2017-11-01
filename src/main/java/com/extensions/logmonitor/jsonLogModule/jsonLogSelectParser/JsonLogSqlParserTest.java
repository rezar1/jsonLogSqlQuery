package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.JsonContentVisitor3;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;
import com.extensions.logmonitor.jsonLogModule.copy.JsonLogSqlAnalyzer;
import com.extensions.logmonitor.jsonLogModule.copy.jsonLogSqlLexer;
import com.extensions.logmonitor.jsonLogModule.copy.jsonLogSqlParser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.BatchTimeWatcher;
import com.extensions.logmonitor.util.LogDataCreator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月28日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class JsonLogSqlParserTest {

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
			SelectPart selectPart = queryExecutor.getSelectPart();
			System.out.println("selectPart:" + selectPart.getAllQueryExecute());
			return queryExecutor;
		} catch (Exception e) {
			log.error("{} error while parser:" + jsonLogQuerySql, e);
			return null;
		}
	}

	private static BatchTimeWatcher watcher = new BatchTimeWatcher(100000, new BatchTimeWatcher.BatchWatchOutput() {
		@Override
		public void output(int batchIndex, int batchCount, int batchUseTime, long preTime, long currentTime) {
			System.out.println("doInWalkers " + batchIndex + "\thandle " + batchCount + " visit use all time:"
					+ batchUseTime + "\t" + (batchCount / (batchUseTime)) + " visit/ms");
		}
	});

	private static void doHandle(String lineLog) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(lineLog.trim().getBytes());
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(bais);
			jsonLexer lexer = new jsonLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			jsonParser parser = new jsonParser(tokens);
			watcher.countSingleTimeStart();
			ParseTree tree = parser.jsonFile();
			watcher.countSingleTimeEnd();
			JsonContentVisitor3 visitor = new JsonContentVisitor3();
			visitor.visit(tree);
			bais.reset();
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
	}

	public static void main(String[] args) {
		// testJsonSqlCreate();
		long time1, time2;
		time1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			String createLogLines = LogDataCreator.createLogLines();
			doHandle(createLogLines);
		}
		time2 = System.currentTimeMillis();
		System.out.print("all use time:" + (time2 - time1));
	}

	/**
	 * 
	 */
	public static void testJsonSqlCreate() {
		String jsonLogQuerySql = "select * from test where app.info[*].name in (1,2,3)";
		QueryExecutor createQueryExecutor = createQueryExecutor(jsonLogQuerySql);
		WhereCondition whereCondition = createQueryExecutor.getWhereCondition();
		whereCondition.quickVisitOptExecute();
		List<OptExecute> findOptExecutes = whereCondition.findOptExecutes("COLUMN_NAME_PREFIX_app.info[1].name");
		System.out.println(findOptExecutes);
	}

}
