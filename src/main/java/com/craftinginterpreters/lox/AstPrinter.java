package com.craftinginterpreters.lox;

public class AstPrinter implements Expr.Visitor<String> {
	public String print(Expr expr) {
		return expr.accept(this);
	}

	@Override
	public String visitAssignExpr(final Expr.Assign expr) {
		return parenthesize("=" + expr.name.lexeme, expr.value);
	}

	@Override
	public String visitBinaryExpr(final Expr.Binary expr) {
		return parenthesize(expr.operator.lexeme, expr.left, expr.right);
	}

	@Override
	public String visitCallExpr(Expr.Call expr) {
//		return parenthesize(expr.callee, expr.arguments.toArray());
		return "";
	}

	@Override
	public String visitGroupingExpr(final Expr.Grouping expr) {
		return parenthesize("group", expr.expression);
	}

	@Override
	public String visitLiteralExpr(final Expr.Literal expr) {
		if (expr.value == null) return "nil";
		return expr.value.toString();
	}

	@Override
	public String visitLogicalExpr(Expr.Logical expr) {
		return parenthesize(expr.operator.lexeme, expr.left, expr.right);
	}

	@Override
	public String visitUnaryExpr(final Expr.Unary expr) {
		return parenthesize(expr.operator.lexeme, expr.right);
	}

	@Override
	public String visitVariableExpr(Expr.Variable expr) {
		return expr.name.lexeme;
	}

	private String parenthesize(final String name, final Expr... exprs) {
		final StringBuilder builder = new StringBuilder();
		builder.append("(").append(name);
		for (final Expr expr : exprs) {
			builder.append(" ");
			builder.append(expr.accept(this));
		}
		builder.append(")");
		return builder.toString();
	}
}