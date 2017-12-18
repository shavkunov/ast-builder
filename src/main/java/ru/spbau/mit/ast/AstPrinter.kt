package ru.spbau.mit.ast

import ru.spbau.mit.ast.nodes.*
import ru.spbau.mit.ast.nodes.Function
import java.io.PrintStream

class AstPrinter(private val printer: PrintStream = System.out) : Visitor<Unit> {
    private var indent = ""

    override fun visitFile(file: File) {
        printer.println("${indent}File ${file.coordinates}:")
        file.functions.forEach { visit(it) }
        visit(file.block)
    }

    override fun visitFunction(function: Function) {
        printer.println("${indent}Function ${function.coordinates}:")
        increaseIndent()
        visit(function.identifier)

        printer.println("Parameters :")
        increaseIndent()
        function.paramNames.forEach { visit(it) }
        decreaseIndent()

        visit(function.body)
        decreaseIndent()
    }

    override fun visitBlock(block: Block) {
        printer.println("${indent}Block ${block.coordinates}:")
        increaseIndent()
        block.statements.forEach { visit(it) }
        decreaseIndent()
    }

    override fun visitAssignment(assignment: Assignment) {
        printer.println("${indent}Assignment ${assignment.coordinates}:")
        increaseIndent()
            visit(assignment.identifier)
            visit(assignment.expression)
        decreaseIndent()
    }

    override fun visitWriteCall(writeCall: WriteCall) {
        printer.println("${indent}WriteCall ${writeCall.coordinates}:")
        increaseIndent()
        visit(writeCall.expression)
        decreaseIndent()
    }

    override fun visitReadCall(readCall: ReadCall) {
        printer.println("${indent}ReadCall ${readCall.coordinates}:")
        increaseIndent()
        visit(readCall.identifier)
        decreaseIndent()
    }

    override fun visitFunctionCall(functionCall: FunctionCall) {
        printer.println("${indent}FunctionCall ${functionCall.coordinates}:")
        increaseIndent()
            visit(functionCall.funIdentifier)
            functionCall.arguments.forEach{ visit(it) }
        decreaseIndent()
    }

    override fun visitReturnStatement(returnStatement: ReturnStatement) {
        printer.println("${indent} Return ${returnStatement.coordinates}:")
        increaseIndent()
        visit(returnStatement.expression)
        decreaseIndent()
    }

    override fun visitWhileCycle(whileCycle: WhileCycle) {
        printer.println("${indent}WhileCycle ${whileCycle.coordinates}:")
        increaseIndent()
            visit(whileCycle.condition)
            visit(whileCycle.body)
        decreaseIndent()
    }

    override fun visitIfStatement(ifStatement: IfStatement) {
        printer.println("${indent}IfStatement ${ifStatement.coordinates}:")
        increaseIndent()
            visit(ifStatement.condition)
            visit(ifStatement.body)
            if (ifStatement.elseBody != null) {
                visit(ifStatement.elseBody)
            }
        decreaseIndent()
    }

    override fun visitBinaryExpression(binaryExpression: BinaryExpression) {
        printer.println("${indent}BinaryExpression ${binaryExpression.coordinates}:")
        increaseIndent()
            visit(binaryExpression.leftOp)
            printer.println("${indent}op : ${binaryExpression.operator}")
            visit(binaryExpression.rightOp)
        decreaseIndent()
    }

    override fun visitInnerExpression(innerExpression: InnerExpression) {
        printer.println("${indent}InnerExpression :")
        increaseIndent()
        visit(innerExpression.expression)
        decreaseIndent()
    }

    override fun visitIdentifier(identifier: Identifier) {
        printer.println("${indent}Identifier ${identifier.coordinates}: ${identifier.name}")
    }

    override fun visitLiteral(literal: Literal) {
        printer.println("${indent}Literal ${literal.coordinates}: ${literal.text}")
    }

    private fun increaseIndent() {
        indent += "  "
    }

    private fun decreaseIndent() {
        indent = indent.removeSuffix("  ")
    }
}