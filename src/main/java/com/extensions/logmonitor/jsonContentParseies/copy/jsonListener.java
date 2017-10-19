// Generated from json.g4 by ANTLR 4.5
package com.extensions.logmonitor.jsonContentParseies.copy;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link jsonParser}.
 */
public interface jsonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code jsonFileRoot}
	 * labeled alternative in {@link jsonParser#jsonFile}.
	 * @param ctx the parse tree
	 */
	void enterJsonFileRoot(jsonParser.JsonFileRootContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonFileRoot}
	 * labeled alternative in {@link jsonParser#jsonFile}.
	 * @param ctx the parse tree
	 */
	void exitJsonFileRoot(jsonParser.JsonFileRootContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 */
	void enterObjectPart(jsonParser.ObjectPartContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 */
	void exitObjectPart(jsonParser.ObjectPartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 */
	void enterArrayPart(jsonParser.ArrayPartContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 */
	void exitArrayPart(jsonParser.ArrayPartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objPair}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObjPair(jsonParser.ObjPairContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objPair}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObjPair(jsonParser.ObjPairContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyObj}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 */
	void enterEmptyObj(jsonParser.EmptyObjContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyObj}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 */
	void exitEmptyObj(jsonParser.EmptyObjContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayValues}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArrayValues(jsonParser.ArrayValuesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayValues}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArrayValues(jsonParser.ArrayValuesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyArray}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 */
	void enterEmptyArray(jsonParser.EmptyArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyArray}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 */
	void exitEmptyArray(jsonParser.EmptyArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code keyValue}
	 * labeled alternative in {@link jsonParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterKeyValue(jsonParser.KeyValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code keyValue}
	 * labeled alternative in {@link jsonParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitKeyValue(jsonParser.KeyValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterStringValue(jsonParser.StringValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitStringValue(jsonParser.StringValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNumberValue(jsonParser.NumberValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNumberValue(jsonParser.NumberValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subObject}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterSubObject(jsonParser.SubObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subObject}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitSubObject(jsonParser.SubObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subArray}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterSubArray(jsonParser.SubArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subArray}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitSubArray(jsonParser.SubArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterTrueValue(jsonParser.TrueValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitTrueValue(jsonParser.TrueValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterFalseValue(jsonParser.FalseValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitFalseValue(jsonParser.FalseValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterNullValue(jsonParser.NullValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitNullValue(jsonParser.NullValueContext ctx);
}