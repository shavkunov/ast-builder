package ru.spbau.mit.ast

import ru.spbau.mit.ast.nodes.*
import ru.spbau.mit.ast.nodes.Function
import java.io.PrintStream

interface Visitor<out T> {
    fun visit(node: AstNode): T {
        return node.accept(this)
    }

    fun visitFile(file: File): T

    fun visitBlock(block: Block): T

    fun visitFunction(function: Function): T

    fun visitWhileCycle(whileCycle: WhileCycle): T

    fun visitIfStatement(ifStatement: IfStatement): T

    fun visitAssignment(assignment: Assignment): T

    fun visitFunctionCall(functionCall: FunctionCall): T

    fun visitWriteCall(writeCall: WriteCall): T

    fun visitReadCall(readCall: ReadCall): T

    fun visitBinaryExpression(binaryExpression: BinaryExpression): T

    fun visitIdentifier(identifier: Identifier): T

    fun visitLiteral(literal: Literal): T

    fun visitInnerExpression(innerExpression: InnerExpression): T
}
