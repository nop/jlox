package com.craftinginterpreters.lox;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class InterpreterTest {
	@Tag("math")
	@DisplayName("Simple Calculations")
	@ParameterizedTest(name = "{0} = {1}")
	@CsvSource({"1+1,2", "(1+1),2", "(1+2)*(3+4),21"})
	void simpleMath(String source, double expected) {
		List<Token> tokens = new Scanner(source).scanTokens();
		Expr ast = new Parser(tokens).parse();
		Object evaluation = new Interpreter().evaluate(ast);
		assertInstanceOf(Double.class, evaluation, "Did not evaluate to a Double");
		assertEquals(expected, new Interpreter().evaluate(ast));
	}
}