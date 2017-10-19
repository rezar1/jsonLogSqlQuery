//package com.extensions.logmonitor.jsonLogAnalyzerTest;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import org.antlr.v4.runtime.ANTLRInputStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.antlr.v4.runtime.tree.ParseTreeWalker;
//
//import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
//import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.JsonContentAnalyzer;
//import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.JsonLogSqlAnalyzer;
//import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlLexer;
//import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
//import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 
// * @say little Boy, don't be sad.
// * @name Rezar
// * @time 2017年9月9日
// * @Desc this guy is to lazy , noting left.
// *
// */
//@Slf4j
//public class FirstTest {
//
//	public static void main(String[] args) {
//		String queryStr = null;
//		String jsonLogStr = "";
//		queryStr = "select * from log where score = 0 and code <= 1004";
//		jsonLogStr = "{\"score\":0,\"code\":1004,\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}";
//		queryStr = "select * from log where address.street = '科技园路.' and links.name = 'Google'";
//		queryStr = "select count(age) as ageCount, age, name from log where  address.street = '科技园路.' and idfa is null and name like 'BeJson%' and age in (23,24,26) group by age , name having count(age) > 1";
//		jsonLogStr = "{\"name\": \"BeJsonHello\", \"age\":23 ,\"idfa\":null,\"url\": \"http://www.bejson.com\",\"page\": 88,\"isNonProfit\": true,\"address\": {\"street\": \"科技园路.\",\"city\": \"江苏苏州\",\"country\": \"中国\"},\"links\": [{\"name\": \"Google\",\"url\": \"http://www.google.com\",\"test\":{\"score\":\"0\",\"code\":\"1004\",\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}},{\"name\": \"Baidu\",\"url\": \"http://www.baidu.com\"},{\"name\": \"SoSo\",\"url\": \"http://www.SoSo.com\"}]}";
//		String jsonLogStr2 = "{\"name\": \"BeJsonHello\", \"age\":23 ,\"idfa\":null,\"url\": \"http://www.bejson.com\",\"page\": 88,\"isNonProfit\": true,\"address\": {\"street\": \"科技园路.\",\"city\": \"江苏苏州\",\"country\": \"中国\"},\"links\": [{\"name\": \"Google\",\"url\": \"http://www.google.com\",\"test\":{\"score\":\"0\",\"code\":\"1004\",\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}},{\"name\": \"Baidu\",\"url\": \"http://www.baidu.com\"},{\"name\": \"SoSo\",\"url\": \"http://www.SoSo.com\"}]}";
//		String jsonLogStr3 = "{\"name\": \"BeJsonHello\", \"age\":23 ,\"idfa\":null,\"url\": \"http://www.bejson.com\",\"page\": 88,\"isNonProfit\": true,\"address\": {\"street\": \"科技园路.\",\"city\": \"江苏苏州\",\"country\": \"中国\"},\"links\": [{\"name\": \"Google\",\"url\": \"http://www.google.com\",\"test\":{\"score\":\"0\",\"code\":\"1004\",\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}},{\"name\": \"Baidu\",\"url\": \"http://www.baidu.com\"},{\"name\": \"SoSo\",\"url\": \"http://www.SoSo.com\"}]}";
//		QueryExecutor initQueryExecutor = initQueryExecutor(queryStr);
//		testJsonLogAnalyzer(initQueryExecutor, jsonLogStr);
//		testJsonLogAnalyzer(initQueryExecutor, jsonLogStr2);
//		testJsonLogAnalyzer(initQueryExecutor, jsonLogStr3);
//		log.info("whereCondition are:{} ", initQueryExecutor.getWhereCondition().getOptExecuteQuickVisitCache());
//		log.info("GroupByExecutor is:{} ", initQueryExecutor.getGroupExecutor().getGroupByCondition()
//				.quickVisitOptExecute().getOptExecuteQuickVisitCache());
//		boolean checkWhereIsSuccess = initQueryExecutor.getWhereCondition().checkWhereIsSuccess();
//		log.info("query is OK:{}", checkWhereIsSuccess);
//		List<QueryResultDataItem> doHandle = initQueryExecutor.doHandle();
//		for (QueryResultDataItem item : doHandle) {
//			System.out.println(item.getQueryResult());
//		}
//		System.out.println(doHandle);
//	}
//
//	/**
//	 * @param initQueryExecutor
//	 * @param jsonLogStr
//	 */
//	private static void testJsonLogAnalyzer(QueryExecutor initQueryExecutor, String jsonLogStr) {
//		/* 执行 SQL 语句分析 */
//		InputStream is = new ByteArrayInputStream(jsonLogStr.getBytes(StandardCharsets.UTF_8));
//		try {
//			// 词语、语法解析，生成抽象语法树
//			ANTLRInputStream input = new ANTLRInputStream(is);
//			jsonLexer lexer = new jsonLexer(input);
//			CommonTokenStream tokens = new CommonTokenStream(lexer);
//			jsonParser parser = new jsonParser(tokens);
//			ParseTree tree = parser.json();
//			ParseTreeWalker walker = new ParseTreeWalker();
//			JsonContentAnalyzer jsonLogSqlAnalyzer = new JsonContentAnalyzer(initQueryExecutor,
//					initQueryExecutor.createQueryResultDataItem());
//			walker.walk(jsonLogSqlAnalyzer, tree);
//			log.info(tree.toStringTree(parser));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @return
//	 * 
//	 */
//	private static QueryExecutor initQueryExecutor(String queryStr) {
//		/* 执行 SQL 语句分析 */
//		InputStream is = new ByteArrayInputStream(queryStr.getBytes(StandardCharsets.UTF_8));
//		try {
//			// 词语、语法解析，生成抽象语法树
//			ANTLRInputStream input = new ANTLRInputStream(is);
//			jsonLogSqlLexer lexer = new jsonLogSqlLexer(input);
//			CommonTokenStream tokens = new CommonTokenStream(lexer);
//			jsonLogSqlParser parser = new jsonLogSqlParser(tokens);
//			ParseTree tree = parser.select_expression();
//			ParseTreeWalker walker = new ParseTreeWalker();
//			JsonLogSqlAnalyzer jsonLogSqlAnalyzer = new JsonLogSqlAnalyzer();
//			walker.walk(jsonLogSqlAnalyzer, tree);
//			log.info(tree.toStringTree(parser));
//			QueryExecutor queryExecutor = jsonLogSqlAnalyzer.queryExecutor;
//			log.info("queryExecutor selectPart:{}", queryExecutor.getSelectPart());
//			log.info("fromLogTable:{}", queryExecutor.getFromTableLogName());
//			return queryExecutor;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//}
