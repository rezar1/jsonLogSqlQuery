package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.extensions.logmonitor.jsonLogModule.copy.JsonLogSqlAnalyzer;
import com.extensions.logmonitor.jsonLogModule.copy.jsonLogSqlLexer;
import com.extensions.logmonitor.jsonLogModule.copy.jsonLogSqlParser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;

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

	public static void main(String[] args) {
		String jsonLogQuerySql = "select * from test where app.info[*].name in (1,2,3)";
		QueryExecutor createQueryExecutor = createQueryExecutor(jsonLogQuerySql);
		WhereCondition whereCondition = createQueryExecutor.getWhereCondition();
		whereCondition.quickVisitOptExecute();
		List<OptExecute> findOptExecutes = whereCondition.findOptExecutes("COLUMN_NAME_PREFIX_app.info[1].name");
		System.out.println(findOptExecutes);

	}

}
