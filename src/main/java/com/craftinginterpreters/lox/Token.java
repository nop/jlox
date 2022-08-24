package com.craftinginterpreters.lox;

import java.util.Objects;

public class Token {
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
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof Token other)) return false;
		return this.type == other.type
				&& Objects.equals(this.lexeme, other.lexeme)
				&& Objects.equals(this.literal, other.literal)
				&& this.line == other.line;
	}

	@Override
	public String toString() {
		return line + ": " + type + " " + (lexeme == null || lexeme.isEmpty() ? "" : lexeme + " ") + literal;
	}
}
