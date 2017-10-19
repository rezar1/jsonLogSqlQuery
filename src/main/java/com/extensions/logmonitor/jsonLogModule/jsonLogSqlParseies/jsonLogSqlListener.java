// Generated from jsonLogSql.g4 by ANTLR 4.5
package com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link jsonLogSqlParser}.
 */
public interface jsonLogSqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#keyword}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterKeyword(jsonLogSqlParser.KeywordContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#keyword}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitKeyword(jsonLogSqlParser.KeywordContext ctx);

	/**
	 * Enter a parse tree produced by
	 * {@link jsonLogSqlParser#delimited_statement}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterDelimited_statement(jsonLogSqlParser.Delimited_statementContext ctx);

	/**
	 * Exit a parse tree produced by
	 * {@link jsonLogSqlParser#delimited_statement}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitDelimited_statement(jsonLogSqlParser.Delimited_statementContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#relational_op}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterRelational_op(jsonLogSqlParser.Relational_opContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#relational_op}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitRelational_op(jsonLogSqlParser.Relational_opContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#interval_unit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterInterval_unit(jsonLogSqlParser.Interval_unitContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#interval_unit}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitInterval_unit(jsonLogSqlParser.Interval_unitContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#string_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterString_literal(jsonLogSqlParser.String_literalContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#string_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitString_literal(jsonLogSqlParser.String_literalContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#number_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterNumber_literal(jsonLogSqlParser.Number_literalContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#number_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitNumber_literal(jsonLogSqlParser.Number_literalContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#hex_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterHex_literal(jsonLogSqlParser.Hex_literalContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#hex_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitHex_literal(jsonLogSqlParser.Hex_literalContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#boolean_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterBoolean_literal(jsonLogSqlParser.Boolean_literalContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#boolean_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitBoolean_literal(jsonLogSqlParser.Boolean_literalContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#bit_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterBit_literal(jsonLogSqlParser.Bit_literalContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#bit_literal}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitBit_literal(jsonLogSqlParser.Bit_literalContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#literal_value}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLiteral_value(jsonLogSqlParser.Literal_valueContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#literal_value}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLiteral_value(jsonLogSqlParser.Literal_valueContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#functionList}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFunctionList(jsonLogSqlParser.FunctionListContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#functionList}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFunctionList(jsonLogSqlParser.FunctionListContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#time_functions}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterTime_functions(jsonLogSqlParser.Time_functionsContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#time_functions}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitTime_functions(jsonLogSqlParser.Time_functionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#group_functions}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterGroup_functions(jsonLogSqlParser.Group_functionsContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#group_functions}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitGroup_functions(jsonLogSqlParser.Group_functionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#table_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterTable_name(jsonLogSqlParser.Table_nameContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#table_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitTable_name(jsonLogSqlParser.Table_nameContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#column_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterColumn_name(jsonLogSqlParser.Column_nameContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#column_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitColumn_name(jsonLogSqlParser.Column_nameContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#alias}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAlias(jsonLogSqlParser.AliasContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#alias}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAlias(jsonLogSqlParser.AliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#any_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAny_name(jsonLogSqlParser.Any_nameContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#any_name}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAny_name(jsonLogSqlParser.Any_nameContext ctx);

	/**
	 * Enter a parse tree produced by
	 * {@link jsonLogSqlParser#any_name_exclude_keyword}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterAny_name_exclude_keyword(jsonLogSqlParser.Any_name_exclude_keywordContext ctx);

	/**
	 * Exit a parse tree produced by
	 * {@link jsonLogSqlParser#any_name_exclude_keyword}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitAny_name_exclude_keyword(jsonLogSqlParser.Any_name_exclude_keywordContext ctx);

	/**
	 * Enter a parse tree produced by the {@code whereOr} labeled alternative in
	 * {@link jsonLogSqlParser#expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterWhereOr(jsonLogSqlParser.WhereOrContext ctx);

	/**
	 * Exit a parse tree produced by the {@code whereOr} labeled alternative in
	 * {@link jsonLogSqlParser#expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitWhereOr(jsonLogSqlParser.WhereOrContext ctx);

	/**
	 * Enter a parse tree produced by the {@code whereXOR} labeled alternative
	 * in {@link jsonLogSqlParser#exp_factor1}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterWhereXOR(jsonLogSqlParser.WhereXORContext ctx);

	/**
	 * Exit a parse tree produced by the {@code whereXOR} labeled alternative in
	 * {@link jsonLogSqlParser#exp_factor1}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitWhereXOR(jsonLogSqlParser.WhereXORContext ctx);

	/**
	 * Enter a parse tree produced by the {@code whereAnd} labeled alternative
	 * in {@link jsonLogSqlParser#exp_factor2}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterWhereAnd(jsonLogSqlParser.WhereAndContext ctx);

	/**
	 * Exit a parse tree produced by the {@code whereAnd} labeled alternative in
	 * {@link jsonLogSqlParser#exp_factor2}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitWhereAnd(jsonLogSqlParser.WhereAndContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condEq} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondEq(jsonLogSqlParser.CondEqContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condEq} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondEq(jsonLogSqlParser.CondEqContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condNotEq} labeled alternative
	 * in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondNotEq(jsonLogSqlParser.CondNotEqContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condNotEq} labeled alternative
	 * in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondNotEq(jsonLogSqlParser.CondNotEqContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condLt} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondLt(jsonLogSqlParser.CondLtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condLt} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondLt(jsonLogSqlParser.CondLtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condGt} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondGt(jsonLogSqlParser.CondGtContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condGt} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondGt(jsonLogSqlParser.CondGtContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condLet} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondLet(jsonLogSqlParser.CondLetContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condLet} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondLet(jsonLogSqlParser.CondLetContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condGet} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondGet(jsonLogSqlParser.CondGetContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condGet} labeled alternative in
	 * {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondGet(jsonLogSqlParser.CondGetContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condInOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondInOrNot(jsonLogSqlParser.CondInOrNotContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condInOrNot} labeled alternative
	 * in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondInOrNot(jsonLogSqlParser.CondInOrNotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condBetweenOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondBetweenOrNot(jsonLogSqlParser.CondBetweenOrNotContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condBetweenOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondBetweenOrNot(jsonLogSqlParser.CondBetweenOrNotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condLikeOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondLikeOrNot(jsonLogSqlParser.CondLikeOrNotContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condLikeOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondLikeOrNot(jsonLogSqlParser.CondLikeOrNotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condRegexpOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondRegexpOrNot(jsonLogSqlParser.CondRegexpOrNotContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condRegexpOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondRegexpOrNot(jsonLogSqlParser.CondRegexpOrNotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condIsTFNOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondIsTFNOrNot(jsonLogSqlParser.CondIsTFNOrNotContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condIsTFNOrNot} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondIsTFNOrNot(jsonLogSqlParser.CondIsTFNOrNotContext ctx);

	/**
	 * Enter a parse tree produced by the {@code condSubConds} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterCondSubConds(jsonLogSqlParser.CondSubCondsContext ctx);

	/**
	 * Exit a parse tree produced by the {@code condSubConds} labeled
	 * alternative in {@link jsonLogSqlParser#predicate}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitCondSubConds(jsonLogSqlParser.CondSubCondsContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#simple_expr}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSimple_expr(jsonLogSqlParser.Simple_exprContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#simple_expr}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSimple_expr(jsonLogSqlParser.Simple_exprContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#function_call}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterFunction_call(jsonLogSqlParser.Function_callContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#function_call}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitFunction_call(jsonLogSqlParser.Function_callContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#column_spec}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterColumn_spec(jsonLogSqlParser.Column_specContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#column_spec}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitColumn_spec(jsonLogSqlParser.Column_specContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#expression_list}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterExpression_list(jsonLogSqlParser.Expression_listContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#expression_list}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitExpression_list(jsonLogSqlParser.Expression_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#table_references}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterTable_references(jsonLogSqlParser.Table_referencesContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#table_references}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitTable_references(jsonLogSqlParser.Table_referencesContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#select_statement}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelect_statement(jsonLogSqlParser.Select_statementContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#select_statement}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelect_statement(jsonLogSqlParser.Select_statementContext ctx);

	/**
	 * Enter a parse tree produced by
	 * {@link jsonLogSqlParser#select_expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelect_expression(jsonLogSqlParser.Select_expressionContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#select_expression}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelect_expression(jsonLogSqlParser.Select_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#where_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterWhere_clause(jsonLogSqlParser.Where_clauseContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#where_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitWhere_clause(jsonLogSqlParser.Where_clauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#groupby_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterGroupby_clause(jsonLogSqlParser.Groupby_clauseContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#groupby_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitGroupby_clause(jsonLogSqlParser.Groupby_clauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#groupby_item}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterGroupby_item(jsonLogSqlParser.Groupby_itemContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#groupby_item}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitGroupby_item(jsonLogSqlParser.Groupby_itemContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#having_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterHaving_clause(jsonLogSqlParser.Having_clauseContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#having_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitHaving_clause(jsonLogSqlParser.Having_clauseContext ctx);

	/**
	 * Enter a parse tree produced by the {@code orderBy} labeled alternative in
	 * {@link jsonLogSqlParser#orderby_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterOrderBy(jsonLogSqlParser.OrderByContext ctx);

	/**
	 * Exit a parse tree produced by the {@code orderBy} labeled alternative in
	 * {@link jsonLogSqlParser#orderby_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitOrderBy(jsonLogSqlParser.OrderByContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#orderby_item}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterOrderby_item(jsonLogSqlParser.Orderby_itemContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#orderby_item}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitOrderby_item(jsonLogSqlParser.Orderby_itemContext ctx);

	/**
	 * Enter a parse tree produced by the {@code limit} labeled alternative in
	 * {@link jsonLogSqlParser#limit_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLimit(jsonLogSqlParser.LimitContext ctx);

	/**
	 * Exit a parse tree produced by the {@code limit} labeled alternative in
	 * {@link jsonLogSqlParser#limit_clause}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLimit(jsonLogSqlParser.LimitContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#offset}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterOffset(jsonLogSqlParser.OffsetContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#offset}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitOffset(jsonLogSqlParser.OffsetContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#row_count}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterRow_count(jsonLogSqlParser.Row_countContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#row_count}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitRow_count(jsonLogSqlParser.Row_countContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#select_list}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelect_list(jsonLogSqlParser.Select_listContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#select_list}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelect_list(jsonLogSqlParser.Select_listContext ctx);

	/**
	 * Enter a parse tree produced by the {@code selectAll} labeled alternative
	 * in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelectAll(jsonLogSqlParser.SelectAllContext ctx);

	/**
	 * Exit a parse tree produced by the {@code selectAll} labeled alternative
	 * in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelectAll(jsonLogSqlParser.SelectAllContext ctx);

	/**
	 * Enter a parse tree produced by the {@code selectTableDotAll} labeled
	 * alternative in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelectTableDotAll(jsonLogSqlParser.SelectTableDotAllContext ctx);

	/**
	 * Exit a parse tree produced by the {@code selectTableDotAll} labeled
	 * alternative in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelectTableDotAll(jsonLogSqlParser.SelectTableDotAllContext ctx);

	/**
	 * Enter a parse tree produced by the {@code selectTableColumn} labeled
	 * alternative in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelectTableColumn(jsonLogSqlParser.SelectTableColumnContext ctx);

	/**
	 * Exit a parse tree produced by the {@code selectTableColumn} labeled
	 * alternative in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelectTableColumn(jsonLogSqlParser.SelectTableColumnContext ctx);

	/**
	 * Enter a parse tree produced by the {@code selectFun} labeled alternative
	 * in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterSelectFun(jsonLogSqlParser.SelectFunContext ctx);

	/**
	 * Exit a parse tree produced by the {@code selectFun} labeled alternative
	 * in {@link jsonLogSqlParser#displayed_column}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitSelectFun(jsonLogSqlParser.SelectFunContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterLength(jsonLogSqlParser.LengthContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitLength(jsonLogSqlParser.LengthContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#varchar_length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterVarchar_length(jsonLogSqlParser.Varchar_lengthContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#varchar_length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitVarchar_length(jsonLogSqlParser.Varchar_lengthContext ctx);

	/**
	 * Enter a parse tree produced by {@link jsonLogSqlParser#binary_length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void enterBinary_length(jsonLogSqlParser.Binary_lengthContext ctx);

	/**
	 * Exit a parse tree produced by {@link jsonLogSqlParser#binary_length}.
	 * 
	 * @param ctx
	 *            the parse tree
	 */
	void exitBinary_length(jsonLogSqlParser.Binary_lengthContext ctx);
}