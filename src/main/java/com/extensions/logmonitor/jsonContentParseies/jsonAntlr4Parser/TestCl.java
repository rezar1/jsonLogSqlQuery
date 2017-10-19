//package com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//
//import org.antlr.v4.runtime.ANTLRInputStream;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.antlr.v4.runtime.tree.ParseTreeWalker;
//
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.JsonContentAnalyzer;
//import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 
// * @say little Boy, don't be sad.
// * @name Rezar
// * @time 2017年9月4日
// * @Desc this guy is to lazy , noting left.
// *
// */
//@Slf4j
//public class TestCl {
//
//	public static void main(String[] args) {
//		String queryStr = "";
//		queryStr = "{\"score\":\"0\",\"code\":\"1004\",\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}";
//		queryStr = "{\"sid\": \"cc10615988f6aa761\",\"client_ip\": \"125.223.253.31\",\"user_agent\": \"PHP-5.3.6\",\"net_type\": \"NT_UnKnown\",\"os\": \"android\",\"os_version\": \"5.0\",\"device_info\": {\"screen_width\": 400,\"screen_height\": 45,\"model\": \"xiaomi\"},\"app_info\": {\"app_id\": \"lbsad\",\"app_name\": \"wifi万能钥匙\"},\"id_info\": {\"imei\": \"862966025166314\",\"mac\": \"8c:be:be:8d:18:9b\"},\"ad_slots\": [{\"id\": \"6\",\"width\": 0,\"height\": 0,\"ad_slot_type\": \"AST_PUSH\",\"req_num\": 4}],\"is_test\": true,\"longitude\": 124.02453,\"latitude\": 44.341374,\"lalo_type\": 1,\"media_index\": \"17\",\"type_id_list\": [\"101\"]}";
//		queryStr = "{\"name\": \"BeJson\",\"url\": \"http://www.bejson.com\",\"page\": 88,\"isNonProfit\": true,\"address\": {\"street\": \"科技园路.\",\"city\": \"江苏苏州\",\"country\": \"中国\"},\"links\": [{\"name\": \"Google\",\"url\": \"http://www.google.com\",\"test\":{\"score\":\"0\",\"code\":\"1004\",\"ip\":\"43.227.137.138\",\"idfa\":null,\"ad_slot_id\":\"4590005\",\"imei\":\"862052039864424\",\"time\":\"2017-09-09 10:05:01.289\",\"android_id\":\"10a1c243bc372ea4\",\"app_id\":\"fc560f67\"}},{\"name\": \"Baidu\",\"url\": \"http://www.baidu.com\"},{\"name\": \"SoSo\",\"url\": \"http://www.SoSo.com\"}]}";
//		/* 执行 SQL 语句分析 */
//		InputStream is = new ByteArrayInputStream(queryStr.getBytes(StandardCharsets.UTF_8));
//		try {
//			// 词语、语法解析，生成抽象语法树
//			ANTLRInputStream input = new ANTLRInputStream(is);
//			jsonLexer lexer = new jsonLexer(input);
//			CommonTokenStream tokens = new CommonTokenStream(lexer);
//			jsonParser parser = new jsonParser(tokens);
//			ParseTree tree = parser.json();
//			ParseTreeWalker walker = new ParseTreeWalker();
//			QueryExecutor queryExecutor = new QueryExecutor();
//			JsonContentAnalyzer jsonLogSqlAnalyzer = new JsonContentAnalyzer(queryExecutor,
//					queryExecutor.createQueryResultDataItem());
//			walker.walk(jsonLogSqlAnalyzer, tree);
//			log.info(tree.toStringTree(parser));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//}
