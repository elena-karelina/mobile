package com.example.ohana.logic

import androidx.compose.runtime.mutableStateListOf

class Scope {
    val variables = mutableMapOf<String, Double>()
        get() = field
    val logs = mutableStateListOf<String>()
        get() = field

    fun setVariable(name: String, value: Double) {
        this.variables[name] = value
    }

    fun setLog(log: String) {
        logs.add(log)
    }
}

class Interpretator(val scope: Scope) {
    var program = Body()

    fun launch() {
        program._body.forEach {
            it.execute(scope)
        }
    }
}