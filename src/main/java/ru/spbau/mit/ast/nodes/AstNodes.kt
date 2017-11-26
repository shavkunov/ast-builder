package ru.spbau.mit.ast.nodes

import ru.spbau.mit.ast.Visitor

interface AstNode {
    fun <T> accept(visitor: Visitor<T>): T
}

data class File(val functions: List<Function>, val block: Block, val coordinates: Coordinates) : AstNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitFile(this)
    }
}

data class Block(val statements: List<Statement>, val coordinates: Coordinates) : AstNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visitBlock(this)
    }
}

data class Function(
        val identifier: Identifier,
        val paramNames: List<Identifier>,
        val body: Block,
        val coordinates: Coordinates
) : AstNode {
    override fun <T> accept(visitor: Visitor<T>): T =
            visitor.visitFunction(this)
}