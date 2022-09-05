package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.craftinginterpreters.lox.TokenType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {

	@Test
	void andy() {
		String source = "andy";
		List<Token> expected = Arrays.asList(
			new Token(IDENTIFIER, "andy", null, 1),
			new Token(EOF, "", null, 1)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Test
	void identifiers() {
		String source = """
			andy formless fo _ _123 _abc abc123
			abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_
			""";
		List<Token> expected = Arrays.asList(
			new Token(IDENTIFIER, "andy", null, 1),
			new Token(IDENTIFIER, "formless", null, 1),
			new Token(IDENTIFIER, "fo", null, 1),
			new Token(IDENTIFIER, "_", null, 1),
			new Token(IDENTIFIER, "_123", null, 1),
			new Token(IDENTIFIER, "_abc", null, 1),
			new Token(IDENTIFIER, "abc123", null, 1),
			new Token(IDENTIFIER, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_", null, 2),
			new Token(EOF, "", null, 3)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void keywords() {
		String source = """
			and class else false for fun if nil or print return super this true var while
			""";
		List<Token> expected = Arrays.asList(
			new Token(AND, "and", null, 1),
			new Token(CLASS, "class", null, 1),
			new Token(ELSE, "else", null, 1),
			new Token(FALSE, "false", null, 1),
			new Token(FOR, "for", null, 1),
			new Token(FUN, "fun", null, 1),
			new Token(IF, "if", null, 1),
			new Token(NIL, "nil", null, 1),
			new Token(OR, "or", null, 1),
			new Token(PRINT, "print", null, 1),
			new Token(RETURN, "return", null, 1),
			new Token(SUPER, "super", null, 1),
			new Token(THIS, "this", null, 1),
			new Token(TRUE, "true", null, 1),
			new Token(VAR, "var", null, 1),
			new Token(WHILE, "while", null, 1),
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void numbers() {
		String source = """
			123
			123.456
			.456
			123.
			""";
		List<Token> expected = Arrays.asList(
			new Token(NUMBER, "123", 123.0, 1),
			new Token(NUMBER, "123.456", 123.456, 2),
			new Token(DOT, ".", null, 3),
			new Token(NUMBER, "456", 456.0, 3),
			new Token(NUMBER, "123", 123.0, 4),
			new Token(DOT, ".", null, 4),
			new Token(EOF, "", null, 5)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Test
	void punctuators() {
		String source = """
			( ) { } , . - + ; * / ! != = == > >= < <=
			""";
		List<Token> expected = Arrays.asList(
			new Token(LEFT_PAREN, "(", null, 1),
			new Token(RIGHT_PAREN, ")", null, 1),
			new Token(LEFT_BRACE, "{", null, 1),
			new Token(RIGHT_BRACE, "}", null, 1),
			new Token(COMMA, ",", null, 1),
			new Token(DOT, ".", null, 1),
			new Token(MINUS, "-", null, 1),
			new Token(PLUS, "+", null, 1),
			new Token(SEMICOLON, ";", null, 1),
			new Token(STAR, "*", null, 1),
			new Token(SLASH, "/", null, 1),
			new Token(BANG, "!", null, 1),
			new Token(BANG_EQUAL, "!=", null, 1),
			new Token(EQUAL, "=", null, 1),
			new Token(EQUAL_EQUAL, "==", null, 1),
			new Token(GREATER, ">", null, 1),
			new Token(GREATER_EQUAL, ">=", null, 1),
			new Token(LESS, "<", null, 1),
			new Token(LESS_EQUAL, "<=", null, 1),
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void strings() {
		String source = """
			""
			"string"
			""";
		List<Token> expected = Arrays.asList(
			new Token(STRING, "\"\"", "", 1),
			new Token(STRING, "\"string\"", "string", 2),
			new Token(EOF, "", null, 3)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentLine() {
		String source = """
			// This is a comment
			""";
		List<Token> expected = List.of(
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentBlockOneLine() {
		String source = """
			/* This is a single-line block comment. */
			""";
		List<Token> expected = List.of(
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentBlockMultiLine() {
		String source = """
			/* This is a
			multi-line block comment. */
			""";
		List<Token> expected = List.of(
			new Token(EOF, "", null, 3)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentBlockNested() {
		// The comment should be /* This is a /* nested */
		String source = """
			/* This is a /* nested */ block comment. */
			""";
		List<Token> expected = List.of(
			new Token(IDENTIFIER, "block", null, 1),
			new Token(IDENTIFIER, "comment", null, 1),
			new Token(DOT, ".", null, 1),
			new Token(STAR, "*", null, 1),
			new Token(SLASH, "/", null, 1),
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentBlockDouble() {
		String source = """
			/* This is a block comment. *//* This is a block comment. */
			""";
		List<Token> expected = List.of(
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void commentBlockThenLine() {
		String source = """
			/* This is a block comment. */// This is a line comment.
			""";
		List<Token> expected = List.of(
			new Token(EOF, "", null, 2)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}

	@Test
	void whitespace() {
		String source = """
			space    tabs				newlines
			    
			    
			    
			    
			end
			""";
		List<Token> expected = Arrays.asList(
			new Token(IDENTIFIER, "space", null, 1),
			new Token(IDENTIFIER, "tabs", null, 1),
			new Token(IDENTIFIER, "newlines", null, 1),
			new Token(IDENTIFIER, "end", null, 6),
			new Token(EOF, "", null, 7)
		);
		assertEquals(expected, new Scanner(source).scanTokens());
	}
}