package com.example.ohana

class State {
    public var variables: Map<String, Double>
    public var logs: List<String>

    constructor() {
        variables = mapOf("finished indicator" to 0.0)
        logs = listOf("launching...")
    }

    fun setVariable(name: String, value: Double) {
        variables = variables + Pair(name, value)
    }

    fun setLog(log: String) {
        logs = logs + log;
    }

    fun reset() {
        variables = mapOf("finished indicator" to 0.0)
        logs = listOf("launching...")
    }
}

abstract class Block {
}

abstract class functionBlock: Block {
    public val nextBlock: Block
    constructor(next: Block) {
        nextBlock = next
    }
}

abstract class arithmeticBlock: Block {
    public var item1: Double
    public var item2: Double

    constructor(it1: Double, it2: Double) {
        item1 = it1
        item2 = it2
    }

    open fun run(): Double { return 0.0 }
}

class additionBlock(item1: Double, item2: Double): arithmeticBlock(item1, item2) {
    public override fun run(): Double {
        return item1 + item2
    }
}

class subscriptingBlock(item1: Double, item2: Double): arithmeticBlock(item1, item2) {
    public override fun run(): Double {
        return item1 - item2
    }
}

class productBlock(item1: Double, item2: Double): arithmeticBlock(item1, item2) {
    public override fun run(): Double {
        return item1 * item2
    }
}

class divisionBlock(item1: Double, item2: Double): arithmeticBlock(item1, item2) {
    public override fun run(): Double {
        return item1 / item2
    }
}

//class printBlock() {
//    public fun run(log: String, logs: List<String>) {
//        logs = logs + log
//    }
//}