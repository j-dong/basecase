// Generated from src/TestCase.g4 by ANTLR 4.9.2
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TestCaseParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TestCaseVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(TestCaseParser.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(TestCaseParser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#typedExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedExpr(TestCaseParser.TypedExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(TestCaseParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link TestCaseParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(TestCaseParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link TestCaseParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(TestCaseParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link TestCaseParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(TestCaseParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code paren}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParen(TestCaseParser.ParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constant}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(TestCaseParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binary}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(TestCaseParser.BinaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(TestCaseParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(TestCaseParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rel}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel(TestCaseParser.RelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code index}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(TestCaseParser.IndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link TestCaseParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(TestCaseParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#branch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch(TestCaseParser.BranchContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(TestCaseParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code scalar}
	 * labeled alternative in {@link TestCaseParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalar(TestCaseParser.ScalarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link TestCaseParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(TestCaseParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link TestCaseParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(TestCaseParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code infinity}
	 * labeled alternative in {@link TestCaseParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfinity(TestCaseParser.InfinityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code list}
	 * labeled alternative in {@link TestCaseParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(TestCaseParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(TestCaseParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestCaseParser#indexOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexOp(TestCaseParser.IndexOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryRule}
	 * labeled alternative in {@link TestCaseParser#prod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryRule(TestCaseParser.BinaryRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compareRule}
	 * labeled alternative in {@link TestCaseParser#prod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareRule(TestCaseParser.CompareRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexRule}
	 * labeled alternative in {@link TestCaseParser#prod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexRule(TestCaseParser.IndexRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcRule}
	 * labeled alternative in {@link TestCaseParser#prod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncRule(TestCaseParser.FuncRuleContext ctx);
}