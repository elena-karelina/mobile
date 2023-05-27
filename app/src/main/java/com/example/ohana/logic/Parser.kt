package com.example.ohana.logic

fun parse(blocks: MutableList<Block>): Body {
    val code = Body()
    val bodyStack = ArrayDeque<Body>()
    bodyStack.addLast(code)

    blocks.forEach {
        bodyStack.last().appendBlock(it)
        when (it) {
            is IfBlock -> bodyStack.addLast(it.ifBody)
            is WhileBlock -> bodyStack.addLast(it.body)
            is EndBlock -> bodyStack.removeLast()
        }
    }

    return code
}