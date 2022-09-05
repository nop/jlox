package com.craftinginterpreters.lox;

// TODO: Turn this into an enum and eliminate the need for the type field?
public record Token(TokenType type, String lexeme, Object literal, int line) {

	@Override
	public String toString() {
		return line + ": " + type + " " + (lexeme == null || lexeme.isEmpty() ? "" : lexeme + " ") + literal;
	}
}
