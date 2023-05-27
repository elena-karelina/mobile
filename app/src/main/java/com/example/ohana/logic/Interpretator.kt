package com.example.ohana.logic

import androidx.compose.runtime.mutableStateListOf

class Scope {
    val variables = mutableMapOf<String, Double>()
        get() = field
    val logs = mutableStateListOf<String>()
        get() = field
    val arrays = mutableMapOf<String, Array<Double>>()
        get() = field

    var exception = false

    fun setVariable(name: String, value: Double) {
        if (name.matches(Regex("[a-z]*\\[.*\\]"))) {
            val item = name.replace("[", " ").replace("]", "").split(" ")
            arrays[item[0]]!![solveMathExpression(this, item[1].replace("_", " ")).toInt()] = value
        } else {
            variables[name] = value
        }
    }

    fun setArray(name: String, size: Int) {
        arrays[name] = Array<Double>(size) { 0.0 }
    }

    fun setLog(log: String) {
        logs.add(log)
    }

    fun throwException(exceptionMessage: String) {
        exception = true
        setLog(exceptionMessage)
    }
}

class Interpretator(val scope: Scope) {
    var program = Body()

    fun launch() {
        program._body.forEach {
            if (scope.exception) throw Exception()
            it.execute(scope)
        }
    }
}