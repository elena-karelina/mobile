class Scope {
    val variables: MutableMap<String, Int> = mutableMapOf("execution code" to 0)
        get() = field
    val logs: MutableList<String> = mutableListOf("launching program")
        get() = field

    fun setVariable(name: String, value: Int) {
        this.variables[name] = value;
    }

    fun setLog(log: String) {
        logs.add(log)
    }
}

open class Block{
    open fun execute() {}
}

class PrintBlock(val scope: Scope, val log: String): Block() {
    override fun execute() {
        scope.logs.add(this.log)
    }
}

class SetVariableBlock(val scope: Scope, val name: String, val value: Int): Block() {
    override fun execute() {
        scope.variables[name] = value
    }
}

class plusBlock(val leftField: Int, val rightField: Int) {
    fun execute(): Int {
        return leftField + rightField
    }
}

class minusBlock(val leftField: Int, val rightField: Int) {
    fun execute(): Int {
        return leftField - rightField
    }
}

class multiplyBlock(val leftField: Int, val rightField: Int) {
    fun execute(): Int {
        return leftField * rightField
    }
}

class divisionBlock(val leftField: Double, val rightField: Double) {
    fun execute(): Double {
        return leftField / rightField
    }
}

class modBlock(val leftField: Int, val rightField: Int) {
    fun execute(): Int {
        return leftField % rightField
    }
}

class moreBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField > rightField
    }
}

class lessBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField < rightField
    }
}

class moreOrEqualBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField >= rightField
    }
}

class lessOrEqualBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField <= rightField
    }
}

class equalBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField == rightField
    }
}

class notEqualBlock(val leftField: Boolean, val rightField: Boolean) {
    fun execute(): Boolean {
        return leftField != rightField
    }
}

class Interpretator(val scope: Scope) {
    val blockList: MutableList<Block> = mutableListOf(Block())

    fun launch() {
        this.blockList.forEach {
            it.execute()
        }
    }
}