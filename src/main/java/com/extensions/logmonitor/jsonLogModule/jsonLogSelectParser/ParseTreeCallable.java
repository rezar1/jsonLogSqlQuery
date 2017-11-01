package com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonLexer;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年11月1日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@Slf4j
public class ParseTreeCallable implements Callable<ParseTree> {

	private String line;

	@Override
	public ParseTree call() throws Exception {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(line.trim().getBytes());
			// 词语、语法解析，生成抽象语法树
			ANTLRInputStream input = new ANTLRInputStream(bais);
			jsonLexer lexer = new jsonLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			jsonParser parser = new jsonParser(tokens);
			ParseTree tree = parser.jsonFile();
			bais.reset();
			return tree;
		} catch (IOException e) {
			log.info("error while parser jsonLogString:{} ", e);
		}
		return null;
	}

}
