package com.example.ohana.ui.theme

interface Block {
    fun operate()
}

class BlockHolder {
    private val _blocks: MutableList<Block> = mutableListOf()
    val blocks: List<Block> get() = _blocks

    fun addBlock(block: Block) {
        _blocks.add(block)
    }

    fun displayBlocks(Blocks: BlockHolder) {
        for (i in Blocks.blocks.indices) {
            var l: Int = 1
//        я пока не понял как выводить блоки из массива на экран
        }
    }
}

class VariableHolder {
    private val variableMap = mutableMapOf<String, Any>()

    fun addVariable(variable: String, value: Any) {
        this.variableMap[variable] = value
    }

}
