package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RpnPrinterTest {

    @Test
    void visitBinaryExpr() {
        List<Token> tokens = new Scanner("(1 + 2) * (4 - 3)").scanTokens();
        Expr expression = new Parser(tokens).parse();
/*
        Expr expression = new Expr.Binary(
            new Expr.Unary(
                    new Token(TokenType.MINUS, "-", null, 1),
                    new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67)));
*/
        assertEquals("1.0 2.0 + 4.0 3.0 - *", new RpnPrinter().print(expression));
    }
}