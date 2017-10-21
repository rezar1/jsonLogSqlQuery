package com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class TestCl {

	public static void main(String[] args) {
		String queryStr = "";
		queryStr = "select count(*) from adRequst where app like 'Rezar*' or age is null and ip in ('def','abc') and adSlotId=12 and appId = 'Rezar' and address != 'RezarHome' or id = 12";
		queryStr = "select count(*) ,sum(*) from adRequst where  address is  not null and ip in ('def','abc') and app.userInfo.name like \"Rezar%\" and time between '20171209' and '20301212' and (userId = 23 and age = 4)";
		queryStr = "select * from adRequst where  address is  not null and ip in ('def','abc') and app.userInfo.name like \"Rezar%\" and time between '20171209' and '20301212' and (userId = 23 and age = 4) order by app.userInfo.name,ip limit 10";
		queryStr = "select * from adRequest where age < 20 group by age ";
		/* 执行 SQL 语句分析 */
		InputStream is = new ByteArrayInputStream(queryStr.getBytes(StandardCharsets.UTF_8));
		try {
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(is);
			jsonLogSqlLexer lexer = new jsonLogSqlLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			jsonLogSqlParser parser = new jsonLogSqlParser(tokens);
			ParseTree tree = parser.select_expression();
			ParseTreeWalker walker = new ParseTreeWalker();
			JsonLogSqlAnalyzer jsonLogSqlAnalyzer = new JsonLogSqlAnalyzer();
			walker.walk(jsonLogSqlAnalyzer, tree);
			log.info(tree.toStringTree(parser));
			QueryExecutor queryExecutor = jsonLogSqlAnalyzer.queryExecutor;
			log.info("queryExecutor selectPart:{}", queryExecutor.getSelectPart());
			WhereCondition whereCondition = queryExecutor.getWhereCondition();
			// Map<String, List<OptExecute>> optExecuteQuickVisitCache =
			// whereCondition.getOptExecuteQuickVisitCache();
			// OptExecute optExecute =
			// optExecuteQuickVisitCache.get("app.userInfo.name");
			// whereCondition.addWhereExecuteResult(optExecute,
			// optExecute.OptSuccess("RezarH"));
			// OptExecute optExecute2 = optExecuteQuickVisitCache.get("userId");
			// whereCondition.addWhereExecuteResult(optExecute2,
			// optExecute2.OptSuccess(23));
			// OptExecute optExecute3 = optExecuteQuickVisitCache.get("age");
			// whereCondition.addWhereExecuteResult(optExecute3,
			// optExecute3.OptSuccess(4));
			// OptExecute optExecute4 = optExecuteQuickVisitCache.get("time");
			// whereCondition.addWhereExecuteResult(optExecute4,
			// optExecute4.OptSuccess("20181212"));
			// OptExecute optExecute5 = optExecuteQuickVisitCache.get("ip");
			// whereCondition.addWhereExecuteResult(optExecute5,
			// optExecute5.OptSuccess("def"));
			// OptExecute optExecute6 =
			// optExecuteQuickVisitCache.get("address");
			// whereCondition.addWhereExecuteResult(optExecute6,
			// optExecute6.OptSuccess(""));
			log.info("executeResult:{}", whereCondition.checkWhereIsSuccess());
			// log.info("fromLogTable:{}", queryExecutor.getFromTableLogName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
