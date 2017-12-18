package ru.spbau.mit.ast.nodes

import ru.spbau.mit.ast.Visitor

interface Expression : Statement

data class BinaryExpression(
        val leftOp: Expression,
        val rightOp: Expression,
        val operator: String,
        val coordinates: Coordinates
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitBinaryExpression(this)
    }
}

data class Identifier(
        val name: String,
        val coordinates: Coordinates
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitIdentifier(this)
    }
}

data class Literal(
        val text: String,
        val coordinates: Coordinates
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitLiteral(this)
    }
}

data class InnerExpression(
        val expression: Expression,
        val coordinates: Coordinates
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T =
            visitor.visitInnerExpression(this)
}

data class FunctionCall(
        val funIdentifier: Identifier,
        val arguments: List<Expression>,
        val coordinates: Coordinates
) : Expression {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitFunctionCall(this)
    }
}
