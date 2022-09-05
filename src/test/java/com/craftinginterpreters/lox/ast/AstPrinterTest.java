package com.craftinginterpreters.lox.ast;

import com.craftinginterpreters.lox.AstPrinter;
import com.craftinginterpreters.lox.Expr;
import com.craftinginterpreters.lox.Token;
import com.craftinginterpreters.lox.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AstPrinterTest {
	// HACK: This is just a test to see if I can print the AST.
	//       It's not a real test.
	@Test
	void visitBinaryExpr() {
		Expr expression = new Expr.Binary(
			new Expr.Unary(
				new Token(TokenType.MINUS, "-", null, 1),
				new Expr.Literal(123)),
			new Token(TokenType.STAR, "*", null, 1),
			new Expr.Grouping(new Expr.Literal(45.67)));
		assertEquals("(* (- 123) (group 45.67))", new AstPrinter().print(expression));
	}
}