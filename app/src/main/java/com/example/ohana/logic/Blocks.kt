package com.example.ohana.logic

open class Block {
    open fun execute(scope: Scope) { }
}

class Body {
    var _body: MutableList<Block> = mutableListOf(Block())
        get() = field

    fun appendBlock(block: Block) {
        _body.add(block)
    }
}

class SetVariableBlock(var name: String, var value: String): Block() {
    override fun execute(scope: Scope) {
        scope.setVariable(name, solveMathExpression(scope, value))
    }
}

class PrintBlock(var log: String): Block() {
    override fun execute(scope: Scope) {
        scope.setLog(solveMathExpression(scope, log).toString())
    }
}

class IfBlock(val condition: String): Block() {
    val ifBody = Body()

    override fun execute(scope: Scope) {
        if (solveLogicalExpression(scope, condition) == 1.0) {
            ifBody._body.forEach {
                it.execute(scope)
            }
        }
    }
}

class IfElseBlock(val condition: String): Block() {
    val ifBody = Body()
    val elseBody = Body()

    override fun execute(scope: Scope) {
        if (solveLogicalExpression(scope, condition) == 1.0) {
            ifBody._body.forEach {
                it.execute(scope)
            }
        }
        else {
            elseBody._body.forEach {
                it.execute(scope)
            }
        }
    }
}

class WhileBlock(var condition: String): Block() {
    val body = Body()

    override fun execute(scope: Scope) {
        if (solveLogicalExpression(scope, condition) == 1.0) {
            body._body.forEach {
                it.execute(scope)
            }
            this.execute(scope)
        }
    }
}