package com.example.ohana.ui.theme
import com.example.ohana.ui.theme.BlockHolder

class LogicalComparison {
    val a: Boolean = true
    val b: Boolean = false

    fun andOperation(a: Boolean, b: Boolean): Boolean {
        return a and b
    }

    fun orOperation(a: Boolean, b: Boolean): Boolean {
        return a or b
    }

    fun implicationOperation(a: Boolean, b: Boolean): Boolean {
        return a <= b
    }

}