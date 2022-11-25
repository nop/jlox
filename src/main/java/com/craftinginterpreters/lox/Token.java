package com.craftinginterpreters.lox;

import java.util.Objects;

// TODO: Turn this into an enum and eliminate the need for the type field?
// TODO: Make tokens immutable?
public final class Token {
	final TokenType type;
	final String lexeme;
	final Object literal;
	final int line;

	public Token(TokenType type, String lexeme, Object literal, int line) {
		this.type = type;
		this.lexeme = lexeme;
		this.literal = literal;
		this.line = line;
	}

	@Override
	public String toString() {
		return line + ": " + type + " " + (lexeme == null || lexeme.isEmpty() ? "" : lexeme + " ") + literal;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Token) obj;
		return Objects.equals(this.type, that.type) &&
				Objects.equals(this.lexeme, that.lexeme) &&
				Objects.equals(this.literal, that.literal) &&
				this.line == that.line;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, lexeme, literal, line);
	}
}
