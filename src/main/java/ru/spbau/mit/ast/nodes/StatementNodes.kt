package ru.spbau.mit.ast.nodes

import ru.spbau.mit.ast.Visitor

interface Statement : AstNode

data class WhileCycle(
        val condition: Expression,
        val body: Block,
        val coordinates: Coordinates
) : Statement {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitWhileCycle(this)
    }
}

data class IfStatement(
        val condition: Expression,
        val body: Block,
        val elseBody: Block?,
        val coordinates: Coordinates
) : Statement {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitIfStatement(this)
    }
}

data class Assignment(
        val identifier: Identifier,
        val expression: Expression,
        val coordinates: Coordinates
) : Statement {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitAssignment(this)
    }
}

data class WriteCall(
        val expression: Expression,
        val coordinates: Coordinates
) : Statement {
    override fun <T> accept(visitor: Visitor<T>): T =
            visitor.visitWriteCall(this)
}

data class ReadCall(
        val identifier: Identifier,
        val coordinates: Coordinates
) : Statement {
    override fun <T> accept(visitor: Visitor<T>): T =
            visitor.visitReadCall(this)
}