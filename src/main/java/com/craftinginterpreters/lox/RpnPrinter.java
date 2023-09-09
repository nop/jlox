package com.craftinginterpreters.lox;

public class RpnPrinter implements Expr.Visitor<String> {
	public String print(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public String visitAssignExpr(Expr.Assign expr) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	@Override
	public String visitBinaryExpr(Expr.Binary expr) {
		return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
	}

	@Override
	public String visitCallExpr(Expr.Call expr) {
		return "NOT IMPLEMENTED";
	}

	@Override
	public String visitGroupingExpr(Expr.Grouping expr) {
		return expr.expression.accept(this);
	}

	@Override
	public String visitLiteralExpr(Expr.Literal expr) {
		if (expr.value == null) return "nil";
		return expr.value.toString();
	}

	@Override
	public String visitLogicalExpr(Expr.Logical expr) {
		return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
	}

	@Override
	public String visitUnaryExpr(Expr.Unary expr) {
		return expr.right.accept(this) + expr.operator.lexeme;
	}

	@Override
	public String visitVariableExpr(Expr.Variable expr) {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
