// Generated from LExpr.g4 by ANTLR 4.7.1
package antlr.listener;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LExprParser}.
 */
public interface LExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LExprParser#s}.
	 * @param ctx the parse tree
	 */
	void enterS(LExprParser.SContext ctx);
	/**
	 * Exit a parse tree produced by {@link LExprParser#s}.
	 * @param ctx the parse tree
	 */
	void exitS(LExprParser.SContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Add}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void enterAdd(LExprParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void exitAdd(LExprParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void enterMult(LExprParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mult}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void exitMult(LExprParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void enterInt(LExprParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link LExprParser#e}.
	 * @param ctx the parse tree
	 */
	void exitInt(LExprParser.IntContext ctx);
}