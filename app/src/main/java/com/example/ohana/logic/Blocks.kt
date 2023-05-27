package com.example.ohana.logic

open class Block {
    open fun execute(scope: Scope) { }
    open var value1: String = ""
    open var value2: String = ""
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

class SetArrBlock(var name: String, var size: String): Block() {
    override fun execute(scope: Scope) {
        scope.setArray(name, solveMathExpression(scope, size).toInt())
    }
}

class PrintBlock(var log: String): Block() {
    override fun execute(scope: Scope) {
        try {
            scope.setLog(solveMathExpression(scope, log).toString())
        } catch (e: Exception) {
            try {
                scope.setLog(solveLogicalExpression(scope, log).toString())
            } catch (e: Exception) {
                scope.setLog(log)
            }
        }
    }
}

class EndBlock(): Block(){
}

class IfBlock(var condition: String): Block() {
    var ifBody = Body()

    override fun execute(scope: Scope) {
        if (solveLogicalExpression(scope, condition) == 1.0) {
            ifBody._body.forEach {
                it.execute(scope)
            }
        }
        ifBody = Body()
    }
}

class WhileBlock(var condition: String): Block() {
    var body = Body()

    override fun execute(scope: Scope) {
        if (solveLogicalExpression(scope, condition) == 1.0) {
            body._body.forEach {
                it.execute(scope)
            }
            this.execute(scope)
        }
        body = Body()
    }
}