// Generated from jsonLogSql.g4 by ANTLR 4.5
package com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class jsonLogSqlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LCASE=1, LOWER=2, LTRIM=3, RTRIM=4, CONCAT=5, SUBSTR=6, TO_CHAR=7, TRIM=8, 
		UCASE=9, REGEX_GROUP=10, UPPER=11, LENGTH=12, REVERSE=13, TRUE=14, FALSE=15, 
		ALL=16, NOT=17, LIKE=18, IF=19, EXISTS=20, ASC=21, DESC=22, ORDER=23, 
		GROUP=24, BY=25, HAVING=26, WHERE=27, FROM=28, AS=29, SELECT=30, DISTINCT=31, 
		IS=32, NULL=33, CAST=34, ADD=35, BETWEEN=36, RLIKE=37, REGEXP=38, LIMIT=39, 
		IN=40, DAY=41, HOUR=42, MINUTE=43, MONTH=44, SECOND=45, YEAR=46, AVG=47, 
		COUNT=48, MAX=49, MIN=50, SUM=51, DIVIDE=52, MOD=53, OR=54, AND=55, XOR=56, 
		EQ=57, NOT_EQ=58, LET=59, GET=60, SET_VAR=61, SHIFT_LEFT=62, SHIFT_RIGHT=63, 
		SEMI=64, COLON=65, DOT=66, COMMA=67, ASTERISK=68, RPAREN=69, LPAREN=70, 
		RBRACK=71, LBRACK=72, PLUS=73, MINUS=74, NEGATION=75, VERTBAR=76, BITAND=77, 
		POWER_OP=78, GTH=79, LTH=80, Double_Quote=81, INTEGER_NUM=82, VARCHAR_NUM=83, 
		BINARY_NUM=84, HEX_DIGIT=85, BIT_NUM=86, REAL_NUMBER=87, TEXT_STRING=88, 
		ID=89, LINE_COMMENT=90, BLOCKCOMMENT=91, WHITE_SPACE=92, SL_COMMENT=93, 
		Regex_Escaped_Unicode=94;
	public static final int
		RULE_keyword = 0, RULE_delimited_statement = 1, RULE_relational_op = 2, 
		RULE_interval_unit = 3, RULE_string_literal = 4, RULE_number_literal = 5, 
		RULE_hex_literal = 6, RULE_boolean_literal = 7, RULE_bit_literal = 8, 
		RULE_literal_value = 9, RULE_functionList = 10, RULE_time_functions = 11, 
		RULE_group_functions = 12, RULE_char_functions = 13, RULE_table_name = 14, 
		RULE_column_name = 15, RULE_alias = 16, RULE_any_name = 17, RULE_any_name_exclude_keyword = 18, 
		RULE_expression = 19, RULE_exp_factor1 = 20, RULE_exp_factor2 = 21, RULE_predicate = 22, 
		RULE_simple_expr = 23, RULE_function_call = 24, RULE_column_spec = 25, 
		RULE_expression_list = 26, RULE_table_references = 27, RULE_select_statement = 28, 
		RULE_select_expression = 29, RULE_where_clause = 30, RULE_groupby_clause = 31, 
		RULE_groupby_item = 32, RULE_having_clause = 33, RULE_orderby_clause = 34, 
		RULE_orderby_item = 35, RULE_limit_clause = 36, RULE_offset = 37, RULE_row_count = 38, 
		RULE_select_list = 39, RULE_displayed_column = 40, RULE_length = 41, RULE_varchar_length = 42, 
		RULE_binary_length = 43;
	public static final String[] ruleNames = {
		"keyword", "delimited_statement", "relational_op", "interval_unit", "string_literal", 
		"number_literal", "hex_literal", "boolean_literal", "bit_literal", "literal_value", 
		"functionList", "time_functions", "group_functions", "char_functions", 
		"table_name", "column_name", "alias", "any_name", "any_name_exclude_keyword", 
		"expression", "exp_factor1", "exp_factor2", "predicate", "simple_expr", 
		"function_call", "column_spec", "expression_list", "table_references", 
		"select_statement", "select_expression", "where_clause", "groupby_clause", 
		"groupby_item", "having_clause", "orderby_clause", "orderby_item", "limit_clause", 
		"offset", "row_count", "select_list", "displayed_column", "length", "varchar_length", 
		"binary_length"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "'<='", 
		"'>='", "':='", "'<<'", "'>>'", "';'", "':'", "'.'", "','", "'*'", "')'", 
		"'('", "']'", "'['", "'+'", "'-'", "'~'", "'|'", "'&'", "'^'", "'>'", 
		"'<'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LCASE", "LOWER", "LTRIM", "RTRIM", "CONCAT", "SUBSTR", "TO_CHAR", 
		"TRIM", "UCASE", "REGEX_GROUP", "UPPER", "LENGTH", "REVERSE", "TRUE", 
		"FALSE", "ALL", "NOT", "LIKE", "IF", "EXISTS", "ASC", "DESC", "ORDER", 
		"GROUP", "BY", "HAVING", "WHERE", "FROM", "AS", "SELECT", "DISTINCT", 
		"IS", "NULL", "CAST", "ADD", "BETWEEN", "RLIKE", "REGEXP", "LIMIT", "IN", 
		"DAY", "HOUR", "MINUTE", "MONTH", "SECOND", "YEAR", "AVG", "COUNT", "MAX", 
		"MIN", "SUM", "DIVIDE", "MOD", "OR", "AND", "XOR", "EQ", "NOT_EQ", "LET", 
		"GET", "SET_VAR", "SHIFT_LEFT", "SHIFT_RIGHT", "SEMI", "COLON", "DOT", 
		"COMMA", "ASTERISK", "RPAREN", "LPAREN", "RBRACK", "LBRACK", "PLUS", "MINUS", 
		"NEGATION", "VERTBAR", "BITAND", "POWER_OP", "GTH", "LTH", "Double_Quote", 
		"INTEGER_NUM", "VARCHAR_NUM", "BINARY_NUM", "HEX_DIGIT", "BIT_NUM", "REAL_NUMBER", 
		"TEXT_STRING", "ID", "LINE_COMMENT", "BLOCKCOMMENT", "WHITE_SPACE", "SL_COMMENT", 
		"Regex_Escaped_Unicode"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "jsonLogSql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public jsonLogSqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class KeywordContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(jsonLogSqlParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(jsonLogSqlParser.FALSE, 0); }
		public TerminalNode ALL() { return getToken(jsonLogSqlParser.ALL, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public TerminalNode LIKE() { return getToken(jsonLogSqlParser.LIKE, 0); }
		public TerminalNode IF() { return getToken(jsonLogSqlParser.IF, 0); }
		public TerminalNode EXISTS() { return getToken(jsonLogSqlParser.EXISTS, 0); }
		public TerminalNode ASC() { return getToken(jsonLogSqlParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(jsonLogSqlParser.DESC, 0); }
		public TerminalNode ORDER() { return getToken(jsonLogSqlParser.ORDER, 0); }
		public TerminalNode GROUP() { return getToken(jsonLogSqlParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(jsonLogSqlParser.BY, 0); }
		public TerminalNode HAVING() { return getToken(jsonLogSqlParser.HAVING, 0); }
		public TerminalNode WHERE() { return getToken(jsonLogSqlParser.WHERE, 0); }
		public TerminalNode FROM() { return getToken(jsonLogSqlParser.FROM, 0); }
		public TerminalNode AS() { return getToken(jsonLogSqlParser.AS, 0); }
		public TerminalNode SELECT() { return getToken(jsonLogSqlParser.SELECT, 0); }
		public TerminalNode DISTINCT() { return getToken(jsonLogSqlParser.DISTINCT, 0); }
		public TerminalNode IS() { return getToken(jsonLogSqlParser.IS, 0); }
		public TerminalNode NULL() { return getToken(jsonLogSqlParser.NULL, 0); }
		public TerminalNode ADD() { return getToken(jsonLogSqlParser.ADD, 0); }
		public TerminalNode BETWEEN() { return getToken(jsonLogSqlParser.BETWEEN, 0); }
		public TerminalNode AVG() { return getToken(jsonLogSqlParser.AVG, 0); }
		public TerminalNode COUNT() { return getToken(jsonLogSqlParser.COUNT, 0); }
		public TerminalNode MAX() { return getToken(jsonLogSqlParser.MAX, 0); }
		public TerminalNode MIN() { return getToken(jsonLogSqlParser.MIN, 0); }
		public TerminalNode SUM() { return getToken(jsonLogSqlParser.SUM, 0); }
		public TerminalNode DIVIDE() { return getToken(jsonLogSqlParser.DIVIDE, 0); }
		public TerminalNode MOD() { return getToken(jsonLogSqlParser.MOD, 0); }
		public TerminalNode OR() { return getToken(jsonLogSqlParser.OR, 0); }
		public TerminalNode AND() { return getToken(jsonLogSqlParser.AND, 0); }
		public TerminalNode XOR() { return getToken(jsonLogSqlParser.XOR, 0); }
		public TerminalNode EQ() { return getToken(jsonLogSqlParser.EQ, 0); }
		public TerminalNode NOT_EQ() { return getToken(jsonLogSqlParser.NOT_EQ, 0); }
		public TerminalNode LET() { return getToken(jsonLogSqlParser.LET, 0); }
		public TerminalNode GET() { return getToken(jsonLogSqlParser.GET, 0); }
		public TerminalNode SET_VAR() { return getToken(jsonLogSqlParser.SET_VAR, 0); }
		public TerminalNode SHIFT_LEFT() { return getToken(jsonLogSqlParser.SHIFT_LEFT, 0); }
		public TerminalNode SHIFT_RIGHT() { return getToken(jsonLogSqlParser.SHIFT_RIGHT, 0); }
		public TerminalNode LCASE() { return getToken(jsonLogSqlParser.LCASE, 0); }
		public TerminalNode LOWER() { return getToken(jsonLogSqlParser.LOWER, 0); }
		public TerminalNode TO_CHAR() { return getToken(jsonLogSqlParser.TO_CHAR, 0); }
		public TerminalNode LTRIM() { return getToken(jsonLogSqlParser.LTRIM, 0); }
		public TerminalNode RTRIM() { return getToken(jsonLogSqlParser.RTRIM, 0); }
		public TerminalNode CONCAT() { return getToken(jsonLogSqlParser.CONCAT, 0); }
		public TerminalNode SUBSTR() { return getToken(jsonLogSqlParser.SUBSTR, 0); }
		public TerminalNode TRIM() { return getToken(jsonLogSqlParser.TRIM, 0); }
		public TerminalNode UCASE() { return getToken(jsonLogSqlParser.UCASE, 0); }
		public TerminalNode UPPER() { return getToken(jsonLogSqlParser.UPPER, 0); }
		public TerminalNode LENGTH() { return getToken(jsonLogSqlParser.LENGTH, 0); }
		public TerminalNode REVERSE() { return getToken(jsonLogSqlParser.REVERSE, 0); }
		public TerminalNode REGEX_GROUP() { return getToken(jsonLogSqlParser.REGEX_GROUP, 0); }
		public KeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitKeyword(this);
		}
	}

	public final KeywordContext keyword() throws RecognitionException {
		KeywordContext _localctx = new KeywordContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_keyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCASE) | (1L << LOWER) | (1L << LTRIM) | (1L << RTRIM) | (1L << CONCAT) | (1L << SUBSTR) | (1L << TO_CHAR) | (1L << TRIM) | (1L << UCASE) | (1L << REGEX_GROUP) | (1L << UPPER) | (1L << LENGTH) | (1L << REVERSE) | (1L << TRUE) | (1L << FALSE) | (1L << ALL) | (1L << NOT) | (1L << LIKE) | (1L << IF) | (1L << EXISTS) | (1L << ASC) | (1L << DESC) | (1L << ORDER) | (1L << GROUP) | (1L << BY) | (1L << HAVING) | (1L << WHERE) | (1L << FROM) | (1L << AS) | (1L << SELECT) | (1L << DISTINCT) | (1L << IS) | (1L << NULL) | (1L << ADD) | (1L << BETWEEN) | (1L << AVG) | (1L << COUNT) | (1L << MAX) | (1L << MIN) | (1L << SUM) | (1L << DIVIDE) | (1L << MOD) | (1L << OR) | (1L << AND) | (1L << XOR) | (1L << EQ) | (1L << NOT_EQ) | (1L << LET) | (1L << GET) | (1L << SET_VAR) | (1L << SHIFT_LEFT) | (1L << SHIFT_RIGHT))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Delimited_statementContext extends ParserRuleContext {
		public TerminalNode Regex_Escaped_Unicode() { return getToken(jsonLogSqlParser.Regex_Escaped_Unicode, 0); }
		public Delimited_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delimited_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterDelimited_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitDelimited_statement(this);
		}
	}

	public final Delimited_statementContext delimited_statement() throws RecognitionException {
		Delimited_statementContext _localctx = new Delimited_statementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_delimited_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(Regex_Escaped_Unicode);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relational_opContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(jsonLogSqlParser.EQ, 0); }
		public TerminalNode LTH() { return getToken(jsonLogSqlParser.LTH, 0); }
		public TerminalNode GTH() { return getToken(jsonLogSqlParser.GTH, 0); }
		public TerminalNode NOT_EQ() { return getToken(jsonLogSqlParser.NOT_EQ, 0); }
		public TerminalNode LET() { return getToken(jsonLogSqlParser.LET, 0); }
		public TerminalNode GET() { return getToken(jsonLogSqlParser.GET, 0); }
		public Relational_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterRelational_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitRelational_op(this);
		}
	}

	public final Relational_opContext relational_op() throws RecognitionException {
		Relational_opContext _localctx = new Relational_opContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_relational_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			_la = _input.LA(1);
			if ( !(((((_la - 57)) & ~0x3f) == 0 && ((1L << (_la - 57)) & ((1L << (EQ - 57)) | (1L << (NOT_EQ - 57)) | (1L << (LET - 57)) | (1L << (GET - 57)) | (1L << (GTH - 57)) | (1L << (LTH - 57)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Interval_unitContext extends ParserRuleContext {
		public TerminalNode SECOND() { return getToken(jsonLogSqlParser.SECOND, 0); }
		public TerminalNode MINUTE() { return getToken(jsonLogSqlParser.MINUTE, 0); }
		public TerminalNode HOUR() { return getToken(jsonLogSqlParser.HOUR, 0); }
		public TerminalNode DAY() { return getToken(jsonLogSqlParser.DAY, 0); }
		public TerminalNode MONTH() { return getToken(jsonLogSqlParser.MONTH, 0); }
		public TerminalNode YEAR() { return getToken(jsonLogSqlParser.YEAR, 0); }
		public Interval_unitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval_unit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterInterval_unit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitInterval_unit(this);
		}
	}

	public final Interval_unitContext interval_unit() throws RecognitionException {
		Interval_unitContext _localctx = new Interval_unitContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_interval_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DAY) | (1L << HOUR) | (1L << MINUTE) | (1L << MONTH) | (1L << SECOND) | (1L << YEAR))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_literalContext extends ParserRuleContext {
		public TerminalNode TEXT_STRING() { return getToken(jsonLogSqlParser.TEXT_STRING, 0); }
		public String_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterString_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitString_literal(this);
		}
	}

	public final String_literalContext string_literal() throws RecognitionException {
		String_literalContext _localctx = new String_literalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_string_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(TEXT_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Number_literalContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public TerminalNode REAL_NUMBER() { return getToken(jsonLogSqlParser.REAL_NUMBER, 0); }
		public TerminalNode PLUS() { return getToken(jsonLogSqlParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(jsonLogSqlParser.MINUS, 0); }
		public Number_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterNumber_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitNumber_literal(this);
		}
	}

	public final Number_literalContext number_literal() throws RecognitionException {
		Number_literalContext _localctx = new Number_literalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_number_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(98);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(101);
			_la = _input.LA(1);
			if ( !(_la==INTEGER_NUM || _la==REAL_NUMBER) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Hex_literalContext extends ParserRuleContext {
		public TerminalNode HEX_DIGIT() { return getToken(jsonLogSqlParser.HEX_DIGIT, 0); }
		public Hex_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hex_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterHex_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitHex_literal(this);
		}
	}

	public final Hex_literalContext hex_literal() throws RecognitionException {
		Hex_literalContext _localctx = new Hex_literalContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_hex_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(HEX_DIGIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_literalContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(jsonLogSqlParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(jsonLogSqlParser.FALSE, 0); }
		public Boolean_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterBoolean_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitBoolean_literal(this);
		}
	}

	public final Boolean_literalContext boolean_literal() throws RecognitionException {
		Boolean_literalContext _localctx = new Boolean_literalContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_boolean_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bit_literalContext extends ParserRuleContext {
		public TerminalNode BIT_NUM() { return getToken(jsonLogSqlParser.BIT_NUM, 0); }
		public Bit_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bit_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterBit_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitBit_literal(this);
		}
	}

	public final Bit_literalContext bit_literal() throws RecognitionException {
		Bit_literalContext _localctx = new Bit_literalContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_bit_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(BIT_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_valueContext extends ParserRuleContext {
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public Number_literalContext number_literal() {
			return getRuleContext(Number_literalContext.class,0);
		}
		public Hex_literalContext hex_literal() {
			return getRuleContext(Hex_literalContext.class,0);
		}
		public Boolean_literalContext boolean_literal() {
			return getRuleContext(Boolean_literalContext.class,0);
		}
		public Bit_literalContext bit_literal() {
			return getRuleContext(Bit_literalContext.class,0);
		}
		public TerminalNode NULL() { return getToken(jsonLogSqlParser.NULL, 0); }
		public Literal_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterLiteral_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitLiteral_value(this);
		}
	}

	public final Literal_valueContext literal_value() throws RecognitionException {
		Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			switch (_input.LA(1)) {
			case TEXT_STRING:
				{
				setState(109);
				string_literal();
				}
				break;
			case PLUS:
			case MINUS:
			case INTEGER_NUM:
			case REAL_NUMBER:
				{
				setState(110);
				number_literal();
				}
				break;
			case HEX_DIGIT:
				{
				setState(111);
				hex_literal();
				}
				break;
			case TRUE:
			case FALSE:
				{
				setState(112);
				boolean_literal();
				}
				break;
			case BIT_NUM:
				{
				setState(113);
				bit_literal();
				}
				break;
			case NULL:
				{
				setState(114);
				match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionListContext extends ParserRuleContext {
		public Time_functionsContext time_functions() {
			return getRuleContext(Time_functionsContext.class,0);
		}
		public Char_functionsContext char_functions() {
			return getRuleContext(Char_functionsContext.class,0);
		}
		public FunctionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterFunctionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitFunctionList(this);
		}
	}

	public final FunctionListContext functionList() throws RecognitionException {
		FunctionListContext _localctx = new FunctionListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_functionList);
		try {
			setState(119);
			switch (_input.LA(1)) {
			case DAY:
			case HOUR:
			case MINUTE:
			case MONTH:
			case SECOND:
			case YEAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				time_functions();
				}
				break;
			case LCASE:
			case LOWER:
			case LTRIM:
			case RTRIM:
			case CONCAT:
			case SUBSTR:
			case TO_CHAR:
			case TRIM:
			case UCASE:
			case REGEX_GROUP:
			case UPPER:
			case LENGTH:
			case REVERSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				char_functions();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Time_functionsContext extends ParserRuleContext {
		public TerminalNode DAY() { return getToken(jsonLogSqlParser.DAY, 0); }
		public TerminalNode HOUR() { return getToken(jsonLogSqlParser.HOUR, 0); }
		public TerminalNode MINUTE() { return getToken(jsonLogSqlParser.MINUTE, 0); }
		public TerminalNode MONTH() { return getToken(jsonLogSqlParser.MONTH, 0); }
		public TerminalNode SECOND() { return getToken(jsonLogSqlParser.SECOND, 0); }
		public TerminalNode YEAR() { return getToken(jsonLogSqlParser.YEAR, 0); }
		public Time_functionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_functions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterTime_functions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitTime_functions(this);
		}
	}

	public final Time_functionsContext time_functions() throws RecognitionException {
		Time_functionsContext _localctx = new Time_functionsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_time_functions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DAY) | (1L << HOUR) | (1L << MINUTE) | (1L << MONTH) | (1L << SECOND) | (1L << YEAR))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Group_functionsContext extends ParserRuleContext {
		public TerminalNode AVG() { return getToken(jsonLogSqlParser.AVG, 0); }
		public TerminalNode COUNT() { return getToken(jsonLogSqlParser.COUNT, 0); }
		public TerminalNode MAX() { return getToken(jsonLogSqlParser.MAX, 0); }
		public TerminalNode MIN() { return getToken(jsonLogSqlParser.MIN, 0); }
		public TerminalNode SUM() { return getToken(jsonLogSqlParser.SUM, 0); }
		public Group_functionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group_functions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterGroup_functions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitGroup_functions(this);
		}
	}

	public final Group_functionsContext group_functions() throws RecognitionException {
		Group_functionsContext _localctx = new Group_functionsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_group_functions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AVG) | (1L << COUNT) | (1L << MAX) | (1L << MIN) | (1L << SUM))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Char_functionsContext extends ParserRuleContext {
		public TerminalNode LCASE() { return getToken(jsonLogSqlParser.LCASE, 0); }
		public TerminalNode LOWER() { return getToken(jsonLogSqlParser.LOWER, 0); }
		public TerminalNode LTRIM() { return getToken(jsonLogSqlParser.LTRIM, 0); }
		public TerminalNode RTRIM() { return getToken(jsonLogSqlParser.RTRIM, 0); }
		public TerminalNode CONCAT() { return getToken(jsonLogSqlParser.CONCAT, 0); }
		public TerminalNode SUBSTR() { return getToken(jsonLogSqlParser.SUBSTR, 0); }
		public TerminalNode TRIM() { return getToken(jsonLogSqlParser.TRIM, 0); }
		public TerminalNode UCASE() { return getToken(jsonLogSqlParser.UCASE, 0); }
		public TerminalNode UPPER() { return getToken(jsonLogSqlParser.UPPER, 0); }
		public TerminalNode TO_CHAR() { return getToken(jsonLogSqlParser.TO_CHAR, 0); }
		public TerminalNode LENGTH() { return getToken(jsonLogSqlParser.LENGTH, 0); }
		public TerminalNode REVERSE() { return getToken(jsonLogSqlParser.REVERSE, 0); }
		public TerminalNode REGEX_GROUP() { return getToken(jsonLogSqlParser.REGEX_GROUP, 0); }
		public Char_functionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_char_functions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterChar_functions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitChar_functions(this);
		}
	}

	public final Char_functionsContext char_functions() throws RecognitionException {
		Char_functionsContext _localctx = new Char_functionsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_char_functions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCASE) | (1L << LOWER) | (1L << LTRIM) | (1L << RTRIM) | (1L << CONCAT) | (1L << SUBSTR) | (1L << TO_CHAR) | (1L << TRIM) | (1L << UCASE) | (1L << REGEX_GROUP) | (1L << UPPER) | (1L << LENGTH) | (1L << REVERSE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_nameContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Table_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterTable_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitTable_name(this);
		}
	}

	public final Table_nameContext table_name() throws RecognitionException {
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_table_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			any_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_nameContext extends ParserRuleContext {
		public Any_nameContext any_name() {
			return getRuleContext(Any_nameContext.class,0);
		}
		public Column_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterColumn_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitColumn_name(this);
		}
	}

	public final Column_nameContext column_name() throws RecognitionException {
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_column_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			any_name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AliasContext extends ParserRuleContext {
		public Any_name_exclude_keywordContext any_name_exclude_keyword() {
			return getRuleContext(Any_name_exclude_keywordContext.class,0);
		}
		public TerminalNode AS() { return getToken(jsonLogSqlParser.AS, 0); }
		public AliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitAlias(this);
		}
	}

	public final AliasContext alias() throws RecognitionException {
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_alias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(131);
				match(AS);
				}
			}

			setState(134);
			any_name_exclude_keyword();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Any_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(jsonLogSqlParser.ID, 0); }
		public KeywordContext keyword() {
			return getRuleContext(KeywordContext.class,0);
		}
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public Any_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterAny_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitAny_name(this);
		}
	}

	public final Any_nameContext any_name() throws RecognitionException {
		Any_nameContext _localctx = new Any_nameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_any_name);
		try {
			setState(139);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				match(ID);
				}
				break;
			case LCASE:
			case LOWER:
			case LTRIM:
			case RTRIM:
			case CONCAT:
			case SUBSTR:
			case TO_CHAR:
			case TRIM:
			case UCASE:
			case REGEX_GROUP:
			case UPPER:
			case LENGTH:
			case REVERSE:
			case TRUE:
			case FALSE:
			case ALL:
			case NOT:
			case LIKE:
			case IF:
			case EXISTS:
			case ASC:
			case DESC:
			case ORDER:
			case GROUP:
			case BY:
			case HAVING:
			case WHERE:
			case FROM:
			case AS:
			case SELECT:
			case DISTINCT:
			case IS:
			case NULL:
			case ADD:
			case BETWEEN:
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case SUM:
			case DIVIDE:
			case MOD:
			case OR:
			case AND:
			case XOR:
			case EQ:
			case NOT_EQ:
			case LET:
			case GET:
			case SET_VAR:
			case SHIFT_LEFT:
			case SHIFT_RIGHT:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				keyword();
				}
				break;
			case TEXT_STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				string_literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Any_name_exclude_keywordContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(jsonLogSqlParser.ID, 0); }
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public Any_name_exclude_keywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_name_exclude_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterAny_name_exclude_keyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitAny_name_exclude_keyword(this);
		}
	}

	public final Any_name_exclude_keywordContext any_name_exclude_keyword() throws RecognitionException {
		Any_name_exclude_keywordContext _localctx = new Any_name_exclude_keywordContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_any_name_exclude_keyword);
		try {
			setState(143);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				match(ID);
				}
				break;
			case TEXT_STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(142);
				string_literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WhereOrContext extends ExpressionContext {
		public List<Exp_factor1Context> exp_factor1() {
			return getRuleContexts(Exp_factor1Context.class);
		}
		public Exp_factor1Context exp_factor1(int i) {
			return getRuleContext(Exp_factor1Context.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(jsonLogSqlParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(jsonLogSqlParser.OR, i);
		}
		public WhereOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterWhereOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitWhereOr(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expression);
		int _la;
		try {
			_localctx = new WhereOrContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			exp_factor1();
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(146);
				match(OR);
				setState(147);
				exp_factor1();
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exp_factor1Context extends ParserRuleContext {
		public Exp_factor1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp_factor1; }
	 
		public Exp_factor1Context() { }
		public void copyFrom(Exp_factor1Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WhereXORContext extends Exp_factor1Context {
		public List<Exp_factor2Context> exp_factor2() {
			return getRuleContexts(Exp_factor2Context.class);
		}
		public Exp_factor2Context exp_factor2(int i) {
			return getRuleContext(Exp_factor2Context.class,i);
		}
		public List<TerminalNode> XOR() { return getTokens(jsonLogSqlParser.XOR); }
		public TerminalNode XOR(int i) {
			return getToken(jsonLogSqlParser.XOR, i);
		}
		public WhereXORContext(Exp_factor1Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterWhereXOR(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitWhereXOR(this);
		}
	}

	public final Exp_factor1Context exp_factor1() throws RecognitionException {
		Exp_factor1Context _localctx = new Exp_factor1Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_exp_factor1);
		int _la;
		try {
			_localctx = new WhereXORContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			exp_factor2();
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==XOR) {
				{
				{
				setState(154);
				match(XOR);
				setState(155);
				exp_factor2();
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exp_factor2Context extends ParserRuleContext {
		public Exp_factor2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp_factor2; }
	 
		public Exp_factor2Context() { }
		public void copyFrom(Exp_factor2Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WhereAndContext extends Exp_factor2Context {
		public List<PredicateContext> predicate() {
			return getRuleContexts(PredicateContext.class);
		}
		public PredicateContext predicate(int i) {
			return getRuleContext(PredicateContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(jsonLogSqlParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(jsonLogSqlParser.AND, i);
		}
		public WhereAndContext(Exp_factor2Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterWhereAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitWhereAnd(this);
		}
	}

	public final Exp_factor2Context exp_factor2() throws RecognitionException {
		Exp_factor2Context _localctx = new Exp_factor2Context(_ctx, getState());
		enterRule(_localctx, 42, RULE_exp_factor2);
		int _la;
		try {
			_localctx = new WhereAndContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			predicate();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(162);
				match(AND);
				setState(163);
				predicate();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
	 
		public PredicateContext() { }
		public void copyFrom(PredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CondGtContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode GTH() { return getToken(jsonLogSqlParser.GTH, 0); }
		public CondGtContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondGt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondGt(this);
		}
	}
	public static class CondEqContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(jsonLogSqlParser.EQ, 0); }
		public CondEqContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondEq(this);
		}
	}
	public static class CondLikeOrNotContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode LIKE() { return getToken(jsonLogSqlParser.LIKE, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public CondLikeOrNotContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondLikeOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondLikeOrNot(this);
		}
	}
	public static class CondSubCondsContext extends PredicateContext {
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public CondSubCondsContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondSubConds(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondSubConds(this);
		}
	}
	public static class CondLtContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode LTH() { return getToken(jsonLogSqlParser.LTH, 0); }
		public CondLtContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondLt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondLt(this);
		}
	}
	public static class CondLetContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode LET() { return getToken(jsonLogSqlParser.LET, 0); }
		public CondLetContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondLet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondLet(this);
		}
	}
	public static class CondNotEqContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode NOT_EQ() { return getToken(jsonLogSqlParser.NOT_EQ, 0); }
		public CondNotEqContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondNotEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondNotEq(this);
		}
	}
	public static class CondInOrNotContext extends PredicateContext {
		public Simple_exprContext simple_expr() {
			return getRuleContext(Simple_exprContext.class,0);
		}
		public TerminalNode IN() { return getToken(jsonLogSqlParser.IN, 0); }
		public TerminalNode LPAREN() { return getToken(jsonLogSqlParser.LPAREN, 0); }
		public List<Literal_valueContext> literal_value() {
			return getRuleContexts(Literal_valueContext.class);
		}
		public Literal_valueContext literal_value(int i) {
			return getRuleContext(Literal_valueContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(jsonLogSqlParser.RPAREN, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public CondInOrNotContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondInOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondInOrNot(this);
		}
	}
	public static class CondGetContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode GET() { return getToken(jsonLogSqlParser.GET, 0); }
		public CondGetContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondGet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondGet(this);
		}
	}
	public static class CondRegexpOrNotContext extends PredicateContext {
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public TerminalNode REGEXP() { return getToken(jsonLogSqlParser.REGEXP, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public CondRegexpOrNotContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondRegexpOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondRegexpOrNot(this);
		}
	}
	public static class CondIsTFNOrNotContext extends PredicateContext {
		public Simple_exprContext simple_expr() {
			return getRuleContext(Simple_exprContext.class,0);
		}
		public TerminalNode IS() { return getToken(jsonLogSqlParser.IS, 0); }
		public Boolean_literalContext boolean_literal() {
			return getRuleContext(Boolean_literalContext.class,0);
		}
		public TerminalNode NULL() { return getToken(jsonLogSqlParser.NULL, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public CondIsTFNOrNotContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondIsTFNOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondIsTFNOrNot(this);
		}
	}
	public static class CondBetweenOrNotContext extends PredicateContext {
		public Simple_exprContext simple_expr() {
			return getRuleContext(Simple_exprContext.class,0);
		}
		public TerminalNode BETWEEN() { return getToken(jsonLogSqlParser.BETWEEN, 0); }
		public List<Literal_valueContext> literal_value() {
			return getRuleContexts(Literal_valueContext.class);
		}
		public Literal_valueContext literal_value(int i) {
			return getRuleContext(Literal_valueContext.class,i);
		}
		public TerminalNode AND() { return getToken(jsonLogSqlParser.AND, 0); }
		public TerminalNode NOT() { return getToken(jsonLogSqlParser.NOT, 0); }
		public CondBetweenOrNotContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterCondBetweenOrNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitCondBetweenOrNot(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_predicate);
		int _la;
		try {
			setState(242);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new CondEqContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(169);
				simple_expr();
				setState(170);
				match(EQ);
				setState(171);
				simple_expr();
				}
				}
				break;
			case 2:
				_localctx = new CondNotEqContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(173);
				simple_expr();
				setState(174);
				match(NOT_EQ);
				setState(175);
				simple_expr();
				}
				}
				break;
			case 3:
				_localctx = new CondLtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(177);
				simple_expr();
				setState(178);
				match(LTH);
				setState(179);
				simple_expr();
				}
				}
				break;
			case 4:
				_localctx = new CondGtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(181);
				simple_expr();
				setState(182);
				match(GTH);
				setState(183);
				simple_expr();
				}
				}
				break;
			case 5:
				_localctx = new CondLetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				{
				setState(185);
				simple_expr();
				setState(186);
				match(LET);
				setState(187);
				simple_expr();
				}
				}
				break;
			case 6:
				_localctx = new CondGetContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				{
				setState(189);
				simple_expr();
				setState(190);
				match(GET);
				setState(191);
				simple_expr();
				}
				}
				break;
			case 7:
				_localctx = new CondInOrNotContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				{
				setState(193);
				simple_expr();
				setState(195);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(194);
					match(NOT);
					}
				}

				setState(197);
				match(IN);
				setState(198);
				match(LPAREN);
				setState(199);
				literal_value();
				setState(204);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(200);
					match(COMMA);
					setState(201);
					literal_value();
					}
					}
					setState(206);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(207);
				match(RPAREN);
				}
				}
				break;
			case 8:
				_localctx = new CondBetweenOrNotContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				{
				setState(209);
				simple_expr();
				setState(211);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(210);
					match(NOT);
					}
				}

				setState(213);
				match(BETWEEN);
				setState(214);
				literal_value();
				setState(215);
				match(AND);
				setState(216);
				literal_value();
				}
				}
				break;
			case 9:
				_localctx = new CondLikeOrNotContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				{
				setState(218);
				simple_expr();
				setState(220);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(219);
					match(NOT);
					}
				}

				setState(222);
				match(LIKE);
				setState(223);
				simple_expr();
				}
				}
				break;
			case 10:
				_localctx = new CondRegexpOrNotContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				{
				setState(225);
				simple_expr();
				setState(227);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(226);
					match(NOT);
					}
				}

				setState(229);
				match(REGEXP);
				setState(230);
				simple_expr();
				}
				}
				break;
			case 11:
				_localctx = new CondIsTFNOrNotContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				{
				setState(232);
				simple_expr();
				{
				setState(233);
				match(IS);
				setState(235);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(234);
					match(NOT);
					}
				}

				setState(239);
				switch (_input.LA(1)) {
				case TRUE:
				case FALSE:
					{
					setState(237);
					boolean_literal();
					}
					break;
				case NULL:
					{
					setState(238);
					match(NULL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				}
				break;
			case 12:
				_localctx = new CondSubCondsContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				{
				setState(241);
				expression_list();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Simple_exprContext extends ParserRuleContext {
		public Literal_valueContext literal_value() {
			return getRuleContext(Literal_valueContext.class,0);
		}
		public Column_specContext column_spec() {
			return getRuleContext(Column_specContext.class,0);
		}
		public Function_callContext function_call() {
			return getRuleContext(Function_callContext.class,0);
		}
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Simple_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSimple_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSimple_expr(this);
		}
	}

	public final Simple_exprContext simple_expr() throws RecognitionException {
		Simple_exprContext _localctx = new Simple_exprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_simple_expr);
		try {
			setState(248);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				literal_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				column_spec();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(246);
				function_call();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(247);
				expression_list();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Function_callContext extends ParserRuleContext {
		public FunctionListContext functionList() {
			return getRuleContext(FunctionListContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(jsonLogSqlParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(jsonLogSqlParser.RPAREN, 0); }
		public List<Simple_exprContext> simple_expr() {
			return getRuleContexts(Simple_exprContext.class);
		}
		public Simple_exprContext simple_expr(int i) {
			return getRuleContext(Simple_exprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public Group_functionsContext group_functions() {
			return getRuleContext(Group_functionsContext.class,0);
		}
		public TerminalNode ASTERISK() { return getToken(jsonLogSqlParser.ASTERISK, 0); }
		public TerminalNode ALL() { return getToken(jsonLogSqlParser.ALL, 0); }
		public TerminalNode DISTINCT() { return getToken(jsonLogSqlParser.DISTINCT, 0); }
		public Function_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterFunction_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitFunction_call(this);
		}
	}

	public final Function_callContext function_call() throws RecognitionException {
		Function_callContext _localctx = new Function_callContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_function_call);
		int _la;
		try {
			setState(276);
			switch (_input.LA(1)) {
			case LCASE:
			case LOWER:
			case LTRIM:
			case RTRIM:
			case CONCAT:
			case SUBSTR:
			case TO_CHAR:
			case TRIM:
			case UCASE:
			case REGEX_GROUP:
			case UPPER:
			case LENGTH:
			case REVERSE:
			case DAY:
			case HOUR:
			case MINUTE:
			case MONTH:
			case SECOND:
			case YEAR:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(250);
				functionList();
				setState(263);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(251);
					match(LPAREN);
					setState(260);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCASE) | (1L << LOWER) | (1L << LTRIM) | (1L << RTRIM) | (1L << CONCAT) | (1L << SUBSTR) | (1L << TO_CHAR) | (1L << TRIM) | (1L << UCASE) | (1L << REGEX_GROUP) | (1L << UPPER) | (1L << LENGTH) | (1L << REVERSE) | (1L << TRUE) | (1L << FALSE) | (1L << ALL) | (1L << NOT) | (1L << LIKE) | (1L << IF) | (1L << EXISTS) | (1L << ASC) | (1L << DESC) | (1L << ORDER) | (1L << GROUP) | (1L << BY) | (1L << HAVING) | (1L << WHERE) | (1L << FROM) | (1L << AS) | (1L << SELECT) | (1L << DISTINCT) | (1L << IS) | (1L << NULL) | (1L << ADD) | (1L << BETWEEN) | (1L << DAY) | (1L << HOUR) | (1L << MINUTE) | (1L << MONTH) | (1L << SECOND) | (1L << YEAR) | (1L << AVG) | (1L << COUNT) | (1L << MAX) | (1L << MIN) | (1L << SUM) | (1L << DIVIDE) | (1L << MOD) | (1L << OR) | (1L << AND) | (1L << XOR) | (1L << EQ) | (1L << NOT_EQ) | (1L << LET) | (1L << GET) | (1L << SET_VAR) | (1L << SHIFT_LEFT) | (1L << SHIFT_RIGHT))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (LPAREN - 70)) | (1L << (PLUS - 70)) | (1L << (MINUS - 70)) | (1L << (INTEGER_NUM - 70)) | (1L << (HEX_DIGIT - 70)) | (1L << (BIT_NUM - 70)) | (1L << (REAL_NUMBER - 70)) | (1L << (TEXT_STRING - 70)) | (1L << (ID - 70)))) != 0)) {
						{
						setState(252);
						simple_expr();
						setState(257);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(253);
							match(COMMA);
							setState(254);
							simple_expr();
							}
							}
							setState(259);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
					}

					setState(262);
					match(RPAREN);
					}
				}

				}
				}
				break;
			case AVG:
			case COUNT:
			case MAX:
			case MIN:
			case SUM:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(265);
				group_functions();
				setState(266);
				match(LPAREN);
				setState(268);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(267);
					_la = _input.LA(1);
					if ( !(((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (ALL - 16)) | (1L << (DISTINCT - 16)) | (1L << (ASTERISK - 16)))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					break;
				}
				setState(272);
				switch (_input.LA(1)) {
				case LCASE:
				case LOWER:
				case LTRIM:
				case RTRIM:
				case CONCAT:
				case SUBSTR:
				case TO_CHAR:
				case TRIM:
				case UCASE:
				case REGEX_GROUP:
				case UPPER:
				case LENGTH:
				case REVERSE:
				case TRUE:
				case FALSE:
				case ALL:
				case NOT:
				case LIKE:
				case IF:
				case EXISTS:
				case ASC:
				case DESC:
				case ORDER:
				case GROUP:
				case BY:
				case HAVING:
				case WHERE:
				case FROM:
				case AS:
				case SELECT:
				case DISTINCT:
				case IS:
				case NULL:
				case ADD:
				case BETWEEN:
				case DAY:
				case HOUR:
				case MINUTE:
				case MONTH:
				case SECOND:
				case YEAR:
				case AVG:
				case COUNT:
				case MAX:
				case MIN:
				case SUM:
				case DIVIDE:
				case MOD:
				case OR:
				case AND:
				case XOR:
				case EQ:
				case NOT_EQ:
				case LET:
				case GET:
				case SET_VAR:
				case SHIFT_LEFT:
				case SHIFT_RIGHT:
				case LPAREN:
				case PLUS:
				case MINUS:
				case INTEGER_NUM:
				case HEX_DIGIT:
				case BIT_NUM:
				case REAL_NUMBER:
				case TEXT_STRING:
				case ID:
					{
					setState(270);
					simple_expr();
					}
					break;
				case ASTERISK:
					{
					setState(271);
					match(ASTERISK);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(274);
				match(RPAREN);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_specContext extends ParserRuleContext {
		public Column_nameContext column_name() {
			return getRuleContext(Column_nameContext.class,0);
		}
		public List<Table_nameContext> table_name() {
			return getRuleContexts(Table_nameContext.class);
		}
		public Table_nameContext table_name(int i) {
			return getRuleContext(Table_nameContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(jsonLogSqlParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(jsonLogSqlParser.DOT, i);
		}
		public Column_specContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterColumn_spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitColumn_spec(this);
		}
	}

	public final Column_specContext column_spec() throws RecognitionException {
		Column_specContext _localctx = new Column_specContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_column_spec);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(278);
					table_name();
					setState(279);
					match(DOT);
					}
					} 
				}
				setState(285);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			{
			setState(286);
			column_name();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expression_listContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(jsonLogSqlParser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(jsonLogSqlParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public Expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterExpression_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitExpression_list(this);
		}
	}

	public final Expression_listContext expression_list() throws RecognitionException {
		Expression_listContext _localctx = new Expression_listContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_expression_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(LPAREN);
			setState(289);
			expression();
			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(290);
				match(COMMA);
				setState(291);
				expression();
				}
				}
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(297);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_referencesContext extends ParserRuleContext {
		public List<Table_nameContext> table_name() {
			return getRuleContexts(Table_nameContext.class);
		}
		public Table_nameContext table_name(int i) {
			return getRuleContext(Table_nameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public Table_referencesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_references; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterTable_references(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitTable_references(this);
		}
	}

	public final Table_referencesContext table_references() throws RecognitionException {
		Table_referencesContext _localctx = new Table_referencesContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_table_references);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			table_name();
			setState(304);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(300);
				match(COMMA);
				setState(301);
				table_name();
				}
				}
				setState(306);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_statementContext extends ParserRuleContext {
		public List<Select_expressionContext> select_expression() {
			return getRuleContexts(Select_expressionContext.class);
		}
		public Select_expressionContext select_expression(int i) {
			return getRuleContext(Select_expressionContext.class,i);
		}
		public List<TerminalNode> ALL() { return getTokens(jsonLogSqlParser.ALL); }
		public TerminalNode ALL(int i) {
			return getToken(jsonLogSqlParser.ALL, i);
		}
		public List<TerminalNode> DISTINCT() { return getTokens(jsonLogSqlParser.DISTINCT); }
		public TerminalNode DISTINCT(int i) {
			return getToken(jsonLogSqlParser.DISTINCT, i);
		}
		public Select_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelect_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelect_statement(this);
		}
	}

	public final Select_statementContext select_statement() throws RecognitionException {
		Select_statementContext _localctx = new Select_statementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_select_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			select_expression();
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ALL) | (1L << SELECT) | (1L << DISTINCT))) != 0)) {
				{
				{
				setState(309);
				_la = _input.LA(1);
				if (_la==ALL || _la==DISTINCT) {
					{
					setState(308);
					_la = _input.LA(1);
					if ( !(_la==ALL || _la==DISTINCT) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
				}

				setState(311);
				select_expression();
				}
				}
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_expressionContext extends ParserRuleContext {
		public Select_listContext select_list() {
			return getRuleContext(Select_listContext.class,0);
		}
		public TerminalNode FROM() { return getToken(jsonLogSqlParser.FROM, 0); }
		public Table_referencesContext table_references() {
			return getRuleContext(Table_referencesContext.class,0);
		}
		public Orderby_clauseContext orderby_clause() {
			return getRuleContext(Orderby_clauseContext.class,0);
		}
		public Limit_clauseContext limit_clause() {
			return getRuleContext(Limit_clauseContext.class,0);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Groupby_clauseContext groupby_clause() {
			return getRuleContext(Groupby_clauseContext.class,0);
		}
		public Select_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelect_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelect_expression(this);
		}
	}

	public final Select_expressionContext select_expression() throws RecognitionException {
		Select_expressionContext _localctx = new Select_expressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_select_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			select_list();
			setState(326);
			_la = _input.LA(1);
			if (_la==FROM) {
				{
				setState(318);
				match(FROM);
				setState(319);
				table_references();
				setState(321);
				_la = _input.LA(1);
				if (_la==WHERE) {
					{
					setState(320);
					where_clause();
					}
				}

				setState(324);
				_la = _input.LA(1);
				if (_la==GROUP) {
					{
					setState(323);
					groupby_clause();
					}
				}

				}
			}

			setState(329);
			_la = _input.LA(1);
			if (_la==ORDER) {
				{
				setState(328);
				orderby_clause();
				}
			}

			setState(332);
			_la = _input.LA(1);
			if (_la==LIMIT) {
				{
				setState(331);
				limit_clause();
				}
			}

			setState(335);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(334);
				match(SEMI);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Where_clauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(jsonLogSqlParser.WHERE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Where_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterWhere_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitWhere_clause(this);
		}
	}

	public final Where_clauseContext where_clause() throws RecognitionException {
		Where_clauseContext _localctx = new Where_clauseContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_where_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(WHERE);
			setState(338);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Groupby_clauseContext extends ParserRuleContext {
		public TerminalNode GROUP() { return getToken(jsonLogSqlParser.GROUP, 0); }
		public TerminalNode BY() { return getToken(jsonLogSqlParser.BY, 0); }
		public List<Groupby_itemContext> groupby_item() {
			return getRuleContexts(Groupby_itemContext.class);
		}
		public Groupby_itemContext groupby_item(int i) {
			return getRuleContext(Groupby_itemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public Having_clauseContext having_clause() {
			return getRuleContext(Having_clauseContext.class,0);
		}
		public Groupby_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupby_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterGroupby_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitGroupby_clause(this);
		}
	}

	public final Groupby_clauseContext groupby_clause() throws RecognitionException {
		Groupby_clauseContext _localctx = new Groupby_clauseContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_groupby_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(GROUP);
			setState(341);
			match(BY);
			setState(342);
			groupby_item();
			setState(347);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(343);
				match(COMMA);
				setState(344);
				groupby_item();
				}
				}
				setState(349);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(351);
			_la = _input.LA(1);
			if (_la==HAVING) {
				{
				setState(350);
				having_clause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Groupby_itemContext extends ParserRuleContext {
		public Column_specContext column_spec() {
			return getRuleContext(Column_specContext.class,0);
		}
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public Function_callContext function_call() {
			return getRuleContext(Function_callContext.class,0);
		}
		public Groupby_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupby_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterGroupby_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitGroupby_item(this);
		}
	}

	public final Groupby_itemContext groupby_item() throws RecognitionException {
		Groupby_itemContext _localctx = new Groupby_itemContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_groupby_item);
		try {
			setState(356);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(353);
				column_spec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				match(INTEGER_NUM);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(355);
				function_call();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Having_clauseContext extends ParserRuleContext {
		public TerminalNode HAVING() { return getToken(jsonLogSqlParser.HAVING, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Having_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_having_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterHaving_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitHaving_clause(this);
		}
	}

	public final Having_clauseContext having_clause() throws RecognitionException {
		Having_clauseContext _localctx = new Having_clauseContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_having_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			match(HAVING);
			setState(359);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Orderby_clauseContext extends ParserRuleContext {
		public Orderby_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderby_clause; }
	 
		public Orderby_clauseContext() { }
		public void copyFrom(Orderby_clauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrderByContext extends Orderby_clauseContext {
		public TerminalNode ORDER() { return getToken(jsonLogSqlParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(jsonLogSqlParser.BY, 0); }
		public List<Orderby_itemContext> orderby_item() {
			return getRuleContexts(Orderby_itemContext.class);
		}
		public Orderby_itemContext orderby_item(int i) {
			return getRuleContext(Orderby_itemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public OrderByContext(Orderby_clauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterOrderBy(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitOrderBy(this);
		}
	}

	public final Orderby_clauseContext orderby_clause() throws RecognitionException {
		Orderby_clauseContext _localctx = new Orderby_clauseContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_orderby_clause);
		int _la;
		try {
			_localctx = new OrderByContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(ORDER);
			setState(362);
			match(BY);
			setState(363);
			orderby_item();
			setState(368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(364);
				match(COMMA);
				setState(365);
				orderby_item();
				}
				}
				setState(370);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Orderby_itemContext extends ParserRuleContext {
		public Groupby_itemContext groupby_item() {
			return getRuleContext(Groupby_itemContext.class,0);
		}
		public TerminalNode ASC() { return getToken(jsonLogSqlParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(jsonLogSqlParser.DESC, 0); }
		public Orderby_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orderby_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterOrderby_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitOrderby_item(this);
		}
	}

	public final Orderby_itemContext orderby_item() throws RecognitionException {
		Orderby_itemContext _localctx = new Orderby_itemContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_orderby_item);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			groupby_item();
			setState(373);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(372);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Limit_clauseContext extends ParserRuleContext {
		public Limit_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limit_clause; }
	 
		public Limit_clauseContext() { }
		public void copyFrom(Limit_clauseContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LimitContext extends Limit_clauseContext {
		public TerminalNode LIMIT() { return getToken(jsonLogSqlParser.LIMIT, 0); }
		public Row_countContext row_count() {
			return getRuleContext(Row_countContext.class,0);
		}
		public OffsetContext offset() {
			return getRuleContext(OffsetContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(jsonLogSqlParser.COMMA, 0); }
		public LimitContext(Limit_clauseContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterLimit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitLimit(this);
		}
	}

	public final Limit_clauseContext limit_clause() throws RecognitionException {
		Limit_clauseContext _localctx = new Limit_clauseContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_limit_clause);
		try {
			_localctx = new LimitContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			match(LIMIT);
			{
			setState(379);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				setState(376);
				offset();
				setState(377);
				match(COMMA);
				}
				break;
			}
			setState(381);
			row_count();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OffsetContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public OffsetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_offset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterOffset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitOffset(this);
		}
	}

	public final OffsetContext offset() throws RecognitionException {
		OffsetContext _localctx = new OffsetContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_offset);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			match(INTEGER_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Row_countContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public Row_countContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_row_count; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterRow_count(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitRow_count(this);
		}
	}

	public final Row_countContext row_count() throws RecognitionException {
		Row_countContext _localctx = new Row_countContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_row_count);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			match(INTEGER_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_listContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(jsonLogSqlParser.SELECT, 0); }
		public List<Displayed_columnContext> displayed_column() {
			return getRuleContexts(Displayed_columnContext.class);
		}
		public Displayed_columnContext displayed_column(int i) {
			return getRuleContext(Displayed_columnContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(jsonLogSqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(jsonLogSqlParser.COMMA, i);
		}
		public TerminalNode ALL() { return getToken(jsonLogSqlParser.ALL, 0); }
		public TerminalNode DISTINCT() { return getToken(jsonLogSqlParser.DISTINCT, 0); }
		public Select_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelect_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelect_list(this);
		}
	}

	public final Select_listContext select_list() throws RecognitionException {
		Select_listContext _localctx = new Select_listContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_select_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(SELECT);
			setState(389);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(388);
				_la = _input.LA(1);
				if ( !(_la==ALL || _la==DISTINCT) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			}
			setState(391);
			displayed_column();
			setState(396);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(392);
				match(COMMA);
				setState(393);
				displayed_column();
				}
				}
				setState(398);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Displayed_columnContext extends ParserRuleContext {
		public Displayed_columnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_displayed_column; }
	 
		public Displayed_columnContext() { }
		public void copyFrom(Displayed_columnContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SelectAllContext extends Displayed_columnContext {
		public TerminalNode ASTERISK() { return getToken(jsonLogSqlParser.ASTERISK, 0); }
		public SelectAllContext(Displayed_columnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelectAll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelectAll(this);
		}
	}
	public static class SelectFunContext extends Displayed_columnContext {
		public Function_callContext function_call() {
			return getRuleContext(Function_callContext.class,0);
		}
		public AliasContext alias() {
			return getRuleContext(AliasContext.class,0);
		}
		public SelectFunContext(Displayed_columnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelectFun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelectFun(this);
		}
	}
	public static class SelectTableColumnContext extends Displayed_columnContext {
		public Column_specContext column_spec() {
			return getRuleContext(Column_specContext.class,0);
		}
		public AliasContext alias() {
			return getRuleContext(AliasContext.class,0);
		}
		public SelectTableColumnContext(Displayed_columnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelectTableColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelectTableColumn(this);
		}
	}
	public static class SelectTableDotAllContext extends Displayed_columnContext {
		public Column_specContext column_spec() {
			return getRuleContext(Column_specContext.class,0);
		}
		public TerminalNode DOT() { return getToken(jsonLogSqlParser.DOT, 0); }
		public TerminalNode ASTERISK() { return getToken(jsonLogSqlParser.ASTERISK, 0); }
		public SelectTableDotAllContext(Displayed_columnContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterSelectTableDotAll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitSelectTableDotAll(this);
		}
	}

	public final Displayed_columnContext displayed_column() throws RecognitionException {
		Displayed_columnContext _localctx = new Displayed_columnContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_displayed_column);
		int _la;
		try {
			setState(412);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				_localctx = new SelectAllContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(399);
				match(ASTERISK);
				}
				break;
			case 2:
				_localctx = new SelectTableDotAllContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(400);
				column_spec();
				setState(401);
				match(DOT);
				setState(402);
				match(ASTERISK);
				}
				break;
			case 3:
				_localctx = new SelectTableColumnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(404);
				column_spec();
				setState(406);
				_la = _input.LA(1);
				if (((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (AS - 29)) | (1L << (TEXT_STRING - 29)) | (1L << (ID - 29)))) != 0)) {
					{
					setState(405);
					alias();
					}
				}

				}
				}
				break;
			case 4:
				_localctx = new SelectFunContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(408);
				function_call();
				setState(410);
				_la = _input.LA(1);
				if (((((_la - 29)) & ~0x3f) == 0 && ((1L << (_la - 29)) & ((1L << (AS - 29)) | (1L << (TEXT_STRING - 29)) | (1L << (ID - 29)))) != 0)) {
					{
					setState(409);
					alias();
					}
				}

				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LengthContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public LengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_length; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterLength(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitLength(this);
		}
	}

	public final LengthContext length() throws RecognitionException {
		LengthContext _localctx = new LengthContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_length);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			match(INTEGER_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Varchar_lengthContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public Varchar_lengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varchar_length; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterVarchar_length(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitVarchar_length(this);
		}
	}

	public final Varchar_lengthContext varchar_length() throws RecognitionException {
		Varchar_lengthContext _localctx = new Varchar_lengthContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_varchar_length);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(416);
			match(INTEGER_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Binary_lengthContext extends ParserRuleContext {
		public TerminalNode INTEGER_NUM() { return getToken(jsonLogSqlParser.INTEGER_NUM, 0); }
		public Binary_lengthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binary_length; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).enterBinary_length(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof jsonLogSqlListener ) ((jsonLogSqlListener)listener).exitBinary_length(this);
		}
	}

	public final Binary_lengthContext binary_length() throws RecognitionException {
		Binary_lengthContext _localctx = new Binary_lengthContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_binary_length);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			match(INTEGER_NUM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3`\u01a7\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\5\7f\n\7\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\5\13v\n\13\3"+
		"\f\3\f\5\fz\n\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\5"+
		"\22\u0087\n\22\3\22\3\22\3\23\3\23\3\23\5\23\u008e\n\23\3\24\3\24\5\24"+
		"\u0092\n\24\3\25\3\25\3\25\7\25\u0097\n\25\f\25\16\25\u009a\13\25\3\26"+
		"\3\26\3\26\7\26\u009f\n\26\f\26\16\26\u00a2\13\26\3\27\3\27\3\27\7\27"+
		"\u00a7\n\27\f\27\16\27\u00aa\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\5\30\u00c6\n\30\3\30\3\30\3\30\3\30\3\30\7\30"+
		"\u00cd\n\30\f\30\16\30\u00d0\13\30\3\30\3\30\3\30\3\30\5\30\u00d6\n\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00df\n\30\3\30\3\30\3\30\3\30"+
		"\3\30\5\30\u00e6\n\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00ee\n\30\3"+
		"\30\3\30\5\30\u00f2\n\30\3\30\5\30\u00f5\n\30\3\31\3\31\3\31\3\31\5\31"+
		"\u00fb\n\31\3\32\3\32\3\32\3\32\3\32\7\32\u0102\n\32\f\32\16\32\u0105"+
		"\13\32\5\32\u0107\n\32\3\32\5\32\u010a\n\32\3\32\3\32\3\32\5\32\u010f"+
		"\n\32\3\32\3\32\5\32\u0113\n\32\3\32\3\32\5\32\u0117\n\32\3\33\3\33\3"+
		"\33\7\33\u011c\n\33\f\33\16\33\u011f\13\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\34\7\34\u0127\n\34\f\34\16\34\u012a\13\34\3\34\3\34\3\35\3\35\3\35\7"+
		"\35\u0131\n\35\f\35\16\35\u0134\13\35\3\36\3\36\5\36\u0138\n\36\3\36\7"+
		"\36\u013b\n\36\f\36\16\36\u013e\13\36\3\37\3\37\3\37\3\37\5\37\u0144\n"+
		"\37\3\37\5\37\u0147\n\37\5\37\u0149\n\37\3\37\5\37\u014c\n\37\3\37\5\37"+
		"\u014f\n\37\3\37\5\37\u0152\n\37\3 \3 \3 \3!\3!\3!\3!\3!\7!\u015c\n!\f"+
		"!\16!\u015f\13!\3!\5!\u0162\n!\3\"\3\"\3\"\5\"\u0167\n\"\3#\3#\3#\3$\3"+
		"$\3$\3$\3$\7$\u0171\n$\f$\16$\u0174\13$\3%\3%\5%\u0178\n%\3&\3&\3&\3&"+
		"\5&\u017e\n&\3&\3&\3\'\3\'\3(\3(\3)\3)\5)\u0188\n)\3)\3)\3)\7)\u018d\n"+
		")\f)\16)\u0190\13)\3*\3*\3*\3*\3*\3*\3*\5*\u0199\n*\3*\3*\5*\u019d\n*"+
		"\5*\u019f\n*\3+\3+\3,\3,\3-\3-\3-\2\2.\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVX\2\r\5\2\3#%&\61A\4\2;>QR"+
		"\3\2+\60\3\2KL\4\2TTYY\3\2\20\21\3\2\61\65\3\2\3\17\5\2\22\22!!FF\4\2"+
		"\22\22!!\3\2\27\30\u01bc\2Z\3\2\2\2\4\\\3\2\2\2\6^\3\2\2\2\b`\3\2\2\2"+
		"\nb\3\2\2\2\fe\3\2\2\2\16i\3\2\2\2\20k\3\2\2\2\22m\3\2\2\2\24u\3\2\2\2"+
		"\26y\3\2\2\2\30{\3\2\2\2\32}\3\2\2\2\34\177\3\2\2\2\36\u0081\3\2\2\2 "+
		"\u0083\3\2\2\2\"\u0086\3\2\2\2$\u008d\3\2\2\2&\u0091\3\2\2\2(\u0093\3"+
		"\2\2\2*\u009b\3\2\2\2,\u00a3\3\2\2\2.\u00f4\3\2\2\2\60\u00fa\3\2\2\2\62"+
		"\u0116\3\2\2\2\64\u011d\3\2\2\2\66\u0122\3\2\2\28\u012d\3\2\2\2:\u0135"+
		"\3\2\2\2<\u013f\3\2\2\2>\u0153\3\2\2\2@\u0156\3\2\2\2B\u0166\3\2\2\2D"+
		"\u0168\3\2\2\2F\u016b\3\2\2\2H\u0175\3\2\2\2J\u0179\3\2\2\2L\u0181\3\2"+
		"\2\2N\u0183\3\2\2\2P\u0185\3\2\2\2R\u019e\3\2\2\2T\u01a0\3\2\2\2V\u01a2"+
		"\3\2\2\2X\u01a4\3\2\2\2Z[\t\2\2\2[\3\3\2\2\2\\]\7`\2\2]\5\3\2\2\2^_\t"+
		"\3\2\2_\7\3\2\2\2`a\t\4\2\2a\t\3\2\2\2bc\7Z\2\2c\13\3\2\2\2df\t\5\2\2"+
		"ed\3\2\2\2ef\3\2\2\2fg\3\2\2\2gh\t\6\2\2h\r\3\2\2\2ij\7W\2\2j\17\3\2\2"+
		"\2kl\t\7\2\2l\21\3\2\2\2mn\7X\2\2n\23\3\2\2\2ov\5\n\6\2pv\5\f\7\2qv\5"+
		"\16\b\2rv\5\20\t\2sv\5\22\n\2tv\7#\2\2uo\3\2\2\2up\3\2\2\2uq\3\2\2\2u"+
		"r\3\2\2\2us\3\2\2\2ut\3\2\2\2v\25\3\2\2\2wz\5\30\r\2xz\5\34\17\2yw\3\2"+
		"\2\2yx\3\2\2\2z\27\3\2\2\2{|\t\4\2\2|\31\3\2\2\2}~\t\b\2\2~\33\3\2\2\2"+
		"\177\u0080\t\t\2\2\u0080\35\3\2\2\2\u0081\u0082\5$\23\2\u0082\37\3\2\2"+
		"\2\u0083\u0084\5$\23\2\u0084!\3\2\2\2\u0085\u0087\7\37\2\2\u0086\u0085"+
		"\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\5&\24\2\u0089"+
		"#\3\2\2\2\u008a\u008e\7[\2\2\u008b\u008e\5\2\2\2\u008c\u008e\5\n\6\2\u008d"+
		"\u008a\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008c\3\2\2\2\u008e%\3\2\2\2"+
		"\u008f\u0092\7[\2\2\u0090\u0092\5\n\6\2\u0091\u008f\3\2\2\2\u0091\u0090"+
		"\3\2\2\2\u0092\'\3\2\2\2\u0093\u0098\5*\26\2\u0094\u0095\78\2\2\u0095"+
		"\u0097\5*\26\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2"+
		"\2\2\u0098\u0099\3\2\2\2\u0099)\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u00a0"+
		"\5,\27\2\u009c\u009d\7:\2\2\u009d\u009f\5,\27\2\u009e\u009c\3\2\2\2\u009f"+
		"\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1+\3\2\2\2"+
		"\u00a2\u00a0\3\2\2\2\u00a3\u00a8\5.\30\2\u00a4\u00a5\79\2\2\u00a5\u00a7"+
		"\5.\30\2\u00a6\u00a4\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8"+
		"\u00a9\3\2\2\2\u00a9-\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac\5\60\31"+
		"\2\u00ac\u00ad\7;\2\2\u00ad\u00ae\5\60\31\2\u00ae\u00f5\3\2\2\2\u00af"+
		"\u00b0\5\60\31\2\u00b0\u00b1\7<\2\2\u00b1\u00b2\5\60\31\2\u00b2\u00f5"+
		"\3\2\2\2\u00b3\u00b4\5\60\31\2\u00b4\u00b5\7R\2\2\u00b5\u00b6\5\60\31"+
		"\2\u00b6\u00f5\3\2\2\2\u00b7\u00b8\5\60\31\2\u00b8\u00b9\7Q\2\2\u00b9"+
		"\u00ba\5\60\31\2\u00ba\u00f5\3\2\2\2\u00bb\u00bc\5\60\31\2\u00bc\u00bd"+
		"\7=\2\2\u00bd\u00be\5\60\31\2\u00be\u00f5\3\2\2\2\u00bf\u00c0\5\60\31"+
		"\2\u00c0\u00c1\7>\2\2\u00c1\u00c2\5\60\31\2\u00c2\u00f5\3\2\2\2\u00c3"+
		"\u00c5\5\60\31\2\u00c4\u00c6\7\23\2\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6"+
		"\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\7*\2\2\u00c8\u00c9\7H\2\2\u00c9"+
		"\u00ce\5\24\13\2\u00ca\u00cb\7E\2\2\u00cb\u00cd\5\24\13\2\u00cc\u00ca"+
		"\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00d1\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d2\7G\2\2\u00d2\u00f5\3\2"+
		"\2\2\u00d3\u00d5\5\60\31\2\u00d4\u00d6\7\23\2\2\u00d5\u00d4\3\2\2\2\u00d5"+
		"\u00d6\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8\7&\2\2\u00d8\u00d9\5\24"+
		"\13\2\u00d9\u00da\79\2\2\u00da\u00db\5\24\13\2\u00db\u00f5\3\2\2\2\u00dc"+
		"\u00de\5\60\31\2\u00dd\u00df\7\23\2\2\u00de\u00dd\3\2\2\2\u00de\u00df"+
		"\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\7\24\2\2\u00e1\u00e2\5\60\31"+
		"\2\u00e2\u00f5\3\2\2\2\u00e3\u00e5\5\60\31\2\u00e4\u00e6\7\23\2\2\u00e5"+
		"\u00e4\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\7("+
		"\2\2\u00e8\u00e9\5\60\31\2\u00e9\u00f5\3\2\2\2\u00ea\u00eb\5\60\31\2\u00eb"+
		"\u00ed\7\"\2\2\u00ec\u00ee\7\23\2\2\u00ed\u00ec\3\2\2\2\u00ed\u00ee\3"+
		"\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00f2\5\20\t\2\u00f0\u00f2\7#\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f5\5\66"+
		"\34\2\u00f4\u00ab\3\2\2\2\u00f4\u00af\3\2\2\2\u00f4\u00b3\3\2\2\2\u00f4"+
		"\u00b7\3\2\2\2\u00f4\u00bb\3\2\2\2\u00f4\u00bf\3\2\2\2\u00f4\u00c3\3\2"+
		"\2\2\u00f4\u00d3\3\2\2\2\u00f4\u00dc\3\2\2\2\u00f4\u00e3\3\2\2\2\u00f4"+
		"\u00ea\3\2\2\2\u00f4\u00f3\3\2\2\2\u00f5/\3\2\2\2\u00f6\u00fb\5\24\13"+
		"\2\u00f7\u00fb\5\64\33\2\u00f8\u00fb\5\62\32\2\u00f9\u00fb\5\66\34\2\u00fa"+
		"\u00f6\3\2\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00f9\3\2"+
		"\2\2\u00fb\61\3\2\2\2\u00fc\u0109\5\26\f\2\u00fd\u0106\7H\2\2\u00fe\u0103"+
		"\5\60\31\2\u00ff\u0100\7E\2\2\u0100\u0102\5\60\31\2\u0101\u00ff\3\2\2"+
		"\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0107"+
		"\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u00fe\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u0108\u010a\7G\2\2\u0109\u00fd\3\2\2\2\u0109\u010a\3\2"+
		"\2\2\u010a\u0117\3\2\2\2\u010b\u010c\5\32\16\2\u010c\u010e\7H\2\2\u010d"+
		"\u010f\t\n\2\2\u010e\u010d\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0112\3\2"+
		"\2\2\u0110\u0113\5\60\31\2\u0111\u0113\7F\2\2\u0112\u0110\3\2\2\2\u0112"+
		"\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115\7G\2\2\u0115\u0117\3\2"+
		"\2\2\u0116\u00fc\3\2\2\2\u0116\u010b\3\2\2\2\u0117\63\3\2\2\2\u0118\u0119"+
		"\5\36\20\2\u0119\u011a\7D\2\2\u011a\u011c\3\2\2\2\u011b\u0118\3\2\2\2"+
		"\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u0120"+
		"\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\5 \21\2\u0121\65\3\2\2\2\u0122"+
		"\u0123\7H\2\2\u0123\u0128\5(\25\2\u0124\u0125\7E\2\2\u0125\u0127\5(\25"+
		"\2\u0126\u0124\3\2\2\2\u0127\u012a\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129"+
		"\3\2\2\2\u0129\u012b\3\2\2\2\u012a\u0128\3\2\2\2\u012b\u012c\7G\2\2\u012c"+
		"\67\3\2\2\2\u012d\u0132\5\36\20\2\u012e\u012f\7E\2\2\u012f\u0131\5\36"+
		"\20\2\u0130\u012e\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132"+
		"\u0133\3\2\2\2\u01339\3\2\2\2\u0134\u0132\3\2\2\2\u0135\u013c\5<\37\2"+
		"\u0136\u0138\t\13\2\2\u0137\u0136\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139"+
		"\3\2\2\2\u0139\u013b\5<\37\2\u013a\u0137\3\2\2\2\u013b\u013e\3\2\2\2\u013c"+
		"\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d;\3\2\2\2\u013e\u013c\3\2\2\2"+
		"\u013f\u0148\5P)\2\u0140\u0141\7\36\2\2\u0141\u0143\58\35\2\u0142\u0144"+
		"\5> \2\u0143\u0142\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0146\3\2\2\2\u0145"+
		"\u0147\5@!\2\u0146\u0145\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0149\3\2\2"+
		"\2\u0148\u0140\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u014b\3\2\2\2\u014a\u014c"+
		"\5F$\2\u014b\u014a\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014e\3\2\2\2\u014d"+
		"\u014f\5J&\2\u014e\u014d\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0151\3\2\2"+
		"\2\u0150\u0152\7B\2\2\u0151\u0150\3\2\2\2\u0151\u0152\3\2\2\2\u0152=\3"+
		"\2\2\2\u0153\u0154\7\35\2\2\u0154\u0155\5(\25\2\u0155?\3\2\2\2\u0156\u0157"+
		"\7\32\2\2\u0157\u0158\7\33\2\2\u0158\u015d\5B\"\2\u0159\u015a\7E\2\2\u015a"+
		"\u015c\5B\"\2\u015b\u0159\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2"+
		"\2\2\u015d\u015e\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u015d\3\2\2\2\u0160"+
		"\u0162\5D#\2\u0161\u0160\3\2\2\2\u0161\u0162\3\2\2\2\u0162A\3\2\2\2\u0163"+
		"\u0167\5\64\33\2\u0164\u0167\7T\2\2\u0165\u0167\5\62\32\2\u0166\u0163"+
		"\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0165\3\2\2\2\u0167C\3\2\2\2\u0168"+
		"\u0169\7\34\2\2\u0169\u016a\5(\25\2\u016aE\3\2\2\2\u016b\u016c\7\31\2"+
		"\2\u016c\u016d\7\33\2\2\u016d\u0172\5H%\2\u016e\u016f\7E\2\2\u016f\u0171"+
		"\5H%\2\u0170\u016e\3\2\2\2\u0171\u0174\3\2\2\2\u0172\u0170\3\2\2\2\u0172"+
		"\u0173\3\2\2\2\u0173G\3\2\2\2\u0174\u0172\3\2\2\2\u0175\u0177\5B\"\2\u0176"+
		"\u0178\t\f\2\2\u0177\u0176\3\2\2\2\u0177\u0178\3\2\2\2\u0178I\3\2\2\2"+
		"\u0179\u017d\7)\2\2\u017a\u017b\5L\'\2\u017b\u017c\7E\2\2\u017c\u017e"+
		"\3\2\2\2\u017d\u017a\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u017f\3\2\2\2\u017f"+
		"\u0180\5N(\2\u0180K\3\2\2\2\u0181\u0182\7T\2\2\u0182M\3\2\2\2\u0183\u0184"+
		"\7T\2\2\u0184O\3\2\2\2\u0185\u0187\7 \2\2\u0186\u0188\t\13\2\2\u0187\u0186"+
		"\3\2\2\2\u0187\u0188\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018e\5R*\2\u018a"+
		"\u018b\7E\2\2\u018b\u018d\5R*\2\u018c\u018a\3\2\2\2\u018d\u0190\3\2\2"+
		"\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018fQ\3\2\2\2\u0190\u018e"+
		"\3\2\2\2\u0191\u019f\7F\2\2\u0192\u0193\5\64\33\2\u0193\u0194\7D\2\2\u0194"+
		"\u0195\7F\2\2\u0195\u019f\3\2\2\2\u0196\u0198\5\64\33\2\u0197\u0199\5"+
		"\"\22\2\u0198\u0197\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u019f\3\2\2\2\u019a"+
		"\u019c\5\62\32\2\u019b\u019d\5\"\22\2\u019c\u019b\3\2\2\2\u019c\u019d"+
		"\3\2\2\2\u019d\u019f\3\2\2\2\u019e\u0191\3\2\2\2\u019e\u0192\3\2\2\2\u019e"+
		"\u0196\3\2\2\2\u019e\u019a\3\2\2\2\u019fS\3\2\2\2\u01a0\u01a1\7T\2\2\u01a1"+
		"U\3\2\2\2\u01a2\u01a3\7T\2\2\u01a3W\3\2\2\2\u01a4\u01a5\7T\2\2\u01a5Y"+
		"\3\2\2\2\60euy\u0086\u008d\u0091\u0098\u00a0\u00a8\u00c5\u00ce\u00d5\u00de"+
		"\u00e5\u00ed\u00f1\u00f4\u00fa\u0103\u0106\u0109\u010e\u0112\u0116\u011d"+
		"\u0128\u0132\u0137\u013c\u0143\u0146\u0148\u014b\u014e\u0151\u015d\u0161"+
		"\u0166\u0172\u0177\u017d\u0187\u018e\u0198\u019c\u019e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}