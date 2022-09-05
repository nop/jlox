package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

class GenerateAst {
	/**
	 * Indentation size in spaces.
	 */
	private static final int INDENT_WIDTH = 4;
	private static int indentLevel = 0;
	private static PrintWriter writer;

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Usage: generate_ast <output directory>");
			System.exit(64);
		}
		String outputDir = args[0];
		String baseName = args[1];
		defineAst(outputDir, baseName, List.of(Arrays.copyOfRange(args, 2, args.length)));
	}

	private static void defineAst(final String outputDir, final String baseName, final List<String> types)
		throws IOException
	{
		final String path = outputDir + "/" + baseName + ".java";
		writer = new PrintWriter(path, StandardCharsets.UTF_8);
		write("""
			package com.craftinginterpreters.lox;
								
			import java.util.List;
								
			public abstract class %s {
			""", baseName);

		defineVisitor(baseName, types);
		// The base accept() method.
		write("abstract <R> R accept(Visitor<R> visitor);");
		writeln();

		// The AST classes.
		for (final String type : types) {
			final String className = type.split(":")[0].trim();
			final String fields = type.split(":")[1].trim();
			defineType(baseName, className, fields);
		}

		write("}"); // end abstract class
		writer.close();
	}

	private static void defineVisitor(final String baseName, final List<String> types) {
		write("public interface Visitor<R> {");
		for (String type : types) {
			String typeName = type.split(":")[0].trim();
			write("R visit%s%s(%s %s);", typeName, baseName, typeName, baseName.toLowerCase());
		}
		write("}"); // end interface
	}

	private static void defineType(
		final String superClass,
		final String className,
		final String fieldList)
	{
		writeln();
		write("public static class %s extends %s {", className, superClass);

		// Fields.
		final String[] fields = fieldList.split(", ");
		for (final String field : fields) {
			write("final %s;", field);
		}

		// Constructor.
		writeln();
		write("public %s(%s) {", className, fieldList);
		// Store parameters in fields.
		for (final String field : fields) {
			final String name = field.split(" ")[1];
			write("this.%s = %s;", name, name);
		}
		write("}"); // end constructor

		writeln();
		write("""
			@Override
			<R> R accept(Visitor<R> visitor) {
			""");
		write("return visitor.visit%s%s(this);", className, superClass);
		write("}"); // end accept()
		write("}"); // end class
	}


	private static void writeln() {
		writer.println();
	}

	// TODO: Use StringBuilder instead of many PrintWriter or write() calls.
	//       Methods should return a String.
	private static void write(final String sourceCode, final Object... args) {
		// dedent before writing
		if (sourceCode.contains("}")) {
			indentLevel--;
		}

		writer.format(sourceCode.indent(INDENT_WIDTH * indentLevel), args);

		/*
		HACK: This is a hack to get the indentation right.
		       This is neither robust nor efficient.
			   This only works as intended if there is one opening or closing brace per line.
		 */
		if (sourceCode.contains("{")) {
			indentLevel++;
		}
	}
}
