package com.example.ohana.ui.theme

interface Block {
    fun operate()
}

class BlockHolder {
    private val _blocks: MutableList<Unit> = mutableListOf()
    val blocks: List<Unit> get() = _blocks

    fun addBlock(block: Unit) {
        _blocks.add(block)
    }

    fun print() {
        _blocks.forEach{

        }
    }
}