// Generated from json.g4 by ANTLR 4.5
package com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class jsonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		STRING=10, NUMBER=11, WS=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"STRING", "NUMBER", "ESC", "UNICODE", "HEX", "INT", "EXP", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "','", "'}'", "'['", "']'", "':'", "'true'", "'false'", "'null'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "STRING", 
		"NUMBER", "WS"
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


	public jsonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "json.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u0084\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\7\13E"+
		"\n\13\f\13\16\13H\13\13\3\13\3\13\3\f\5\fM\n\f\3\f\3\f\3\f\3\f\5\fS\n"+
		"\f\3\f\5\fV\n\f\3\f\3\f\3\f\3\f\5\f\\\n\f\3\f\5\f_\n\f\3\r\3\r\3\r\5\r"+
		"d\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\7\20q\n\20"+
		"\f\20\16\20t\13\20\5\20v\n\20\3\21\3\21\5\21z\n\21\3\21\3\21\3\22\6\22"+
		"\177\n\22\r\22\16\22\u0080\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\2\33\2\35\2\37\2!\2#\16\3\2\n\4\2$$^^\n\2$"+
		"$\61\61^^ddhhppttvv\4\2\62;CH\3\2\63;\3\2\62;\4\2GGgg\4\2--//\5\2\13\f"+
		"\17\17\"\"\u008b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t+\3\2\2"+
		"\2\13-\3\2\2\2\r/\3\2\2\2\17\61\3\2\2\2\21\66\3\2\2\2\23<\3\2\2\2\25A"+
		"\3\2\2\2\27^\3\2\2\2\31`\3\2\2\2\33e\3\2\2\2\35k\3\2\2\2\37u\3\2\2\2!"+
		"w\3\2\2\2#~\3\2\2\2%&\7}\2\2&\4\3\2\2\2\'(\7.\2\2(\6\3\2\2\2)*\7\177\2"+
		"\2*\b\3\2\2\2+,\7]\2\2,\n\3\2\2\2-.\7_\2\2.\f\3\2\2\2/\60\7<\2\2\60\16"+
		"\3\2\2\2\61\62\7v\2\2\62\63\7t\2\2\63\64\7w\2\2\64\65\7g\2\2\65\20\3\2"+
		"\2\2\66\67\7h\2\2\678\7c\2\289\7n\2\29:\7u\2\2:;\7g\2\2;\22\3\2\2\2<="+
		"\7p\2\2=>\7w\2\2>?\7n\2\2?@\7n\2\2@\24\3\2\2\2AF\7$\2\2BE\5\31\r\2CE\n"+
		"\2\2\2DB\3\2\2\2DC\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GI\3\2\2\2HF\3"+
		"\2\2\2IJ\7$\2\2J\26\3\2\2\2KM\7/\2\2LK\3\2\2\2LM\3\2\2\2MN\3\2\2\2NO\5"+
		"\37\20\2OP\7\60\2\2PR\5\37\20\2QS\5!\21\2RQ\3\2\2\2RS\3\2\2\2S_\3\2\2"+
		"\2TV\7/\2\2UT\3\2\2\2UV\3\2\2\2VW\3\2\2\2WX\5\37\20\2XY\5!\21\2Y_\3\2"+
		"\2\2Z\\\7/\2\2[Z\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]_\5\37\20\2^L\3\2\2\2^"+
		"U\3\2\2\2^[\3\2\2\2_\30\3\2\2\2`c\7^\2\2ad\t\3\2\2bd\5\33\16\2ca\3\2\2"+
		"\2cb\3\2\2\2d\32\3\2\2\2ef\7w\2\2fg\5\35\17\2gh\5\35\17\2hi\5\35\17\2"+
		"ij\5\35\17\2j\34\3\2\2\2kl\t\4\2\2l\36\3\2\2\2mv\7\62\2\2nr\t\5\2\2oq"+
		"\t\6\2\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2sv\3\2\2\2tr\3\2\2\2u"+
		"m\3\2\2\2un\3\2\2\2v \3\2\2\2wy\t\7\2\2xz\t\b\2\2yx\3\2\2\2yz\3\2\2\2"+
		"z{\3\2\2\2{|\5\37\20\2|\"\3\2\2\2}\177\t\t\2\2~}\3\2\2\2\177\u0080\3\2"+
		"\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083"+
		"\b\22\2\2\u0083$\3\2\2\2\17\2DFLRU[^cruy\u0080\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}