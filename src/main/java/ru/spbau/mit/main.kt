package ru.spbau.mit

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.spbau.mit.ast.Ast
import ru.spbau.mit.ast.AstPrinter
import ru.spbau.mit.ast.AstVisitor
import ru.spbau.mit.parser.FunLexer
import ru.spbau.mit.parser.FunParser
import java.io.PrintStream

fun createAst(source: String): Ast {
    val funLexer = FunLexer(CharStreams.fromFileName(source))
    val tokens = CommonTokenStream(funLexer)
    val funParser = FunParser(tokens)
    val fileContext = funParser.file()

    if (funParser.numberOfSyntaxErrors > 0) {
        throw ParsingException("there are syntax errors")
    }

    return AstVisitor().createAst(fileContext)
}

fun printAst(source: String, printStream: PrintStream) {
    val ast = createAst(source)
    val printer = AstPrinter(printStream)
    printer.visit(ast.root)
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Excepted path to file")
        return
    }
    try {
        printAst(args[0], System.out)
    } catch (e: ParsingException) {
        System.err.println(e.message)
        System.err.println("The AST wasn't created")
    }
}