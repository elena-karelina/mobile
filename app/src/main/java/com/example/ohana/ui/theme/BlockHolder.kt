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
}