package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RpnPrinterTest {

	@Test
	void visitBinaryExpr() {
		List<Token> tokens = new Scanner("(1 + 2) * (4 - 3)").scanTokens();
		Expr expression = new Parser(tokens).parse();
		assertEquals("1.0 2.0 + 4.0 3.0 - *", new RpnPrinter().print(expression));
	}
}