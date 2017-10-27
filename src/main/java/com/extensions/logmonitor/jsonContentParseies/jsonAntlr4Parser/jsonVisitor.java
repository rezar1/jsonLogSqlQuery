// Generated from json.g4 by ANTLR 4.5
package com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link jsonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface jsonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code jsonFileRoot}
	 * labeled alternative in {@link jsonParser#jsonFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonFileRoot(jsonParser.JsonFileRootContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objectPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectPart(jsonParser.ObjectPartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayPart}
	 * labeled alternative in {@link jsonParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayPart(jsonParser.ArrayPartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objPair}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjPair(jsonParser.ObjPairContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyObj}
	 * labeled alternative in {@link jsonParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyObj(jsonParser.EmptyObjContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayValues}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayValues(jsonParser.ArrayValuesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyArray}
	 * labeled alternative in {@link jsonParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyArray(jsonParser.EmptyArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code keyValue}
	 * labeled alternative in {@link jsonParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValue(jsonParser.KeyValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValue(jsonParser.StringValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberValue(jsonParser.NumberValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subObject}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubObject(jsonParser.SubObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subArray}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubArray(jsonParser.SubArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueValue(jsonParser.TrueValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseValue(jsonParser.FalseValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullValue}
	 * labeled alternative in {@link jsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullValue(jsonParser.NullValueContext ctx);
}