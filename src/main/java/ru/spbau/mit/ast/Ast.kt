package ru.spbau.mit.ast

import org.antlr.v4.runtime.ParserRuleContext
import ru.spbau.mit.ast.nodes.*
import ru.spbau.mit.ast.nodes.Function
import ru.spbau.mit.parser.FunBaseVisitor
import ru.spbau.mit.parser.FunParser

data class Ast(val root: AstNode)

class AstVisitor : FunBaseVisitor<AstNode>() {
    fun createAst(context: ParserRuleContext): Ast {
        val rootNode = visit(context)

        return Ast(rootNode)
    }

    override fun visitFile(context: FunParser.FileContext): AstNode {
        val functions = context.function().map { visit(it) as Function}
        val block = visit(context.block()) as Block
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        return File(functions, block, coordinates)
    }

    override fun visitBlock(context: FunParser.BlockContext): AstNode {
        val statements = context.statement().map { visit(it) as Statement }
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)

        return Block(statements, coordinates)
    }

    override fun visitBlockWithBraces(context: FunParser.BlockWithBracesContext): AstNode {
        return visit(context.block())
    }

    override fun visitFunctionCallExpression(context: FunParser.FunctionCallExpressionContext): AstNode {
        return visitFunctionCall(context.functionCall())
    }

    override fun visitFunction(context: FunParser.FunctionContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val funName = Identifier(context.IDENTIFIER().text,
                                    Coordinates(context.IDENTIFIER().sourceInterval.a,
                                    context.IDENTIFIER().sourceInterval.b))

        val paramsList = context.parameterNames()
                .IDENTIFIER()?.map { Identifier(it.text, Coordinates(it.sourceInterval.a, it.sourceInterval.b)) }
                .orEmpty()

        val body = visit(context.blockWithBraces()) as Block

        return Function(funName, paramsList, body, coordinates)
    }

    override fun visitVariableExpression(context: FunParser.VariableExpressionContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        return Identifier(context.IDENTIFIER().text, coordinates)
    }

    override fun visitWhileCycle(context: FunParser.WhileCycleContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val condition = visit(context.expression()) as Expression
        val body = visit(context.blockWithBraces()) as Block

        return WhileCycle(condition, body, coordinates)
    }

    override fun visitIfStatement(context: FunParser.IfStatementContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val condition = visit(context.expression()) as Expression
        val ifBlocks = context.blockWithBraces().map { visit(it) }
        val body = ifBlocks[0] as Block
        val elseBody = ifBlocks.getOrNull(1) as Block?

        return IfStatement(condition, body, elseBody, coordinates)
    }

    override fun visitAssignment(context: FunParser.AssignmentContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val identifier = Identifier(context.IDENTIFIER().text,
                Coordinates(context.IDENTIFIER().sourceInterval.a, context.IDENTIFIER().sourceInterval.b))
        val expression = visit(context.expression()) as Expression

        return Assignment(identifier, expression, coordinates)
    }

    override fun visitFunctionCall(context: FunParser.FunctionCallContext): AstNode {
        val identifier = Identifier(context.IDENTIFIER().text,
                Coordinates(context.IDENTIFIER().sourceInterval.a, context.IDENTIFIER().sourceInterval.b))

        val arguments = context.arguments().expression().map { visit(it) as Expression }
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)

        return FunctionCall(identifier, arguments, coordinates)
    }

    override fun visitReturnStatement(context: FunParser.ReturnStatementContext): AstNode {
        val expression = visit(context.expression()) as Expression
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        return ReturnStatement(expression, coordinates)
    }

    override fun visitInnerExpression(context: FunParser.InnerExpressionContext): AstNode {
        return visit(context.expression())
    }

    override fun visitWriteCall(context: FunParser.WriteCallContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val expression = visit(context.expression()) as Expression

        return WriteCall(expression, coordinates)
    }

    override fun visitReadCall(context: FunParser.ReadCallContext): AstNode {
        val identifier = Identifier(context.IDENTIFIER().text,
                Coordinates(context.IDENTIFIER().sourceInterval.a, context.IDENTIFIER().sourceInterval.b))
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        return ReadCall(identifier, coordinates)
    }

    override fun visitLiteralExpression(context: FunParser.LiteralExpressionContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        return Literal(context.LITERAL().text, coordinates)
    }

    override fun visitBinaryExpression(context: FunParser.BinaryExpressionContext): AstNode {
        val coordinates = Coordinates(context.start.line, context.start.charPositionInLine)
        val leftOp = context.leftOp
        val rightOp = context.rightOp
        val operation = context.operation

        val visitedLeft = visit(leftOp) as Expression
        val visitedRight = visit(rightOp) as Expression
        return BinaryExpression(visitedLeft, visitedRight, operation.text, coordinates)
    }

    override fun aggregateResult(aggregate: AstNode?, nextResult: AstNode?): AstNode? =
            aggregate ?: nextResult
}