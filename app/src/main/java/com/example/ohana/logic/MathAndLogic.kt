package com.example.ohana.logic

val MATH_OPERATORS = arrayOf("+", "-", "*", "/", "//", "%", "**")
val LOGIC_OPERATORS = arrayOf("<", "<=", ">", ">=", "==", "!=", "&&", "||", "!")

val getMathOperatorPriority = { operator: String, -> Int
    when (operator) {
        "(" -> 1
        "+" -> 2
        "-" -> 2
        "*" -> 3
        "/" -> 3
        "//" -> 3
        "%" -> 4
        "**" -> 5
        else -> -1 // Error
    }
}

val getLogicalOperatorPriority = { operator: String, -> Int
    when (operator) {
        "(" -> 1
        "||" -> 2
        "&&" -> 3
        "<" -> 4
        ">" -> 4
        "<=" -> 4
        ">=" -> 4
        "==" -> 4
        "!=" -> 4
        "!" -> 5
        else -> -1 // Error
    }
}

class LogicalOperators {
    fun or(a: Double, b: Double): Double {
        return if ((a == 1.0) || (b == 1.0)) 1.0 else 0.0
    }
    fun and(a: Double, b: Double): Double {
        return if ((a == 1.0) && (b == 1.0)) 1.0 else 0.0
    }
    fun nott(a: Double): Double {
        return if (a == 1.0) 0.0 else 1.0
    }
    fun more(a: Double, b: Double): Double {
        return if (a > b) 1.0 else 0.0
    }
    fun moreOrEqual(a: Double, b: Double): Double {
        return if (a >= b) 1.0 else 0.0
    }
    fun less(a: Double, b: Double): Double {
        return if (a < b) 1.0 else 0.0
    }
    fun lessOrEqual(a: Double, b: Double): Double {
        return if (a <= b) 1.0 else 0.0
    }
    fun equal(a: Double, b: Double): Double {
        return if (a == b) 1.0 else 0.0
    }
    fun notEqual(a: Double, b: Double): Double {
        return if (a != b) 1.0 else 0.0
    }
}

fun pow(value: Double, exponent: Int): Double {
    var result = 1.0
    for (i in 1..exponent) {
        result *= value
    }
    return result
}

fun convertToRPN(scope: Scope, a: String, operators: Array<String>, getOperatorPriority: (op: String) -> Int): List<String> {
    val expression = a.split(" ")
    val result: MutableList<String> = mutableListOf()
    val stack: ArrayDeque<String> = ArrayDeque()

    expression.forEach {
        if (it in operators) {
            if (stack.size == 0) {
                stack.add(it)
            }
            else if (getOperatorPriority(stack.last()) < getOperatorPriority(it)) {
                stack.add(it)
            }
            else {
                while (stack.size != 0) {
                    if (getOperatorPriority(stack.last()) < getOperatorPriority(it)) {
                        break
                    }
                    result.add(stack.removeLast())
                }
                stack.add(it)
            }
        }
        else if (it == "(") {
            stack.add(it)
        }
        else if (it == ")") {
            while (stack.last() != "(") {
                result.add(stack.removeLast())
                if (stack.size == 0) {// TODO
                    return listOf("Error")
                }
            }
            stack.removeLast()
        }
        else {
            result.add(it)
        }
    }

    while (stack.size != 0) {
        result.add(stack.removeLast())
    }

    return result
}

fun solveMathExpression(scope: Scope, expression: String): Double {
    val stack: ArrayDeque<Double> = ArrayDeque()
    val expressionRPN = convertToRPN(scope, expression, MATH_OPERATORS, getMathOperatorPriority)
    if (expressionRPN[0] != "Error") {
        expressionRPN.forEach {
            when (it) {
                "+" -> stack.add(stack.removeLast() + stack.removeLast())
                "*" -> stack.add(stack.removeLast() * stack.removeLast())
                "/" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.add(a / b) // TODO
                }
                "-" ->  {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.add(a - b) // TODO
                }
                "//" ->  {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.add((a.toInt() / b.toInt()).toDouble()) // TODO
                }
                "%" ->  {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.add(a % b) // TODO
                }
                "**" ->  {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.add(pow(a, b.toInt())) // TODO
                }
                else ->  {
                    if (it.toDoubleOrNull() == null) {
                        if (scope.variables.containsKey(it)) {
                            stack.add(scope.variables[it]!!)
                        } else {
                            scope.setLog("Invalid value: $it")
                            // return "Exception" // TODO
                        }
                    }
                    else {
                        stack.add(it.toDouble())
                    }
                }
            }
        }
    }
    return stack.removeLast()
}

fun solveLogicalExpression(scope: Scope, expression: String): Any {
    val stack: ArrayDeque<Double> = ArrayDeque()
    val logicalOperators = LogicalOperators()
    val expressionRPN = convertToRPN(scope, expression, LOGIC_OPERATORS, getLogicalOperatorPriority)
    if (expressionRPN[0] != "Error") {
        expressionRPN.forEach {
            when (it) {
                "||" -> stack.add(logicalOperators.or(stack.removeLast(), stack.removeLast()))
                "&&" -> stack.add(logicalOperators.and(stack.removeLast(), stack.removeLast()))
                "<" -> stack.add(logicalOperators.more(stack.removeLast(), stack.removeLast()))
                ">" -> stack.add(logicalOperators.less(stack.removeLast(), stack.removeLast()))
                "<=" -> stack.add(logicalOperators.moreOrEqual(stack.removeLast(), stack.removeLast()))
                ">=" -> stack.add(logicalOperators.lessOrEqual(stack.removeLast(), stack.removeLast()))
                "==" -> stack.add(logicalOperators.equal(stack.removeLast(), stack.removeLast()))
                "!=" -> stack.add(logicalOperators.notEqual(stack.removeLast(), stack.removeLast()))
                "!" -> stack.add(logicalOperators.nott(stack.removeLast()))
                else ->  {
                    if (it.toDoubleOrNull() == null) {
                        if (scope.variables.containsKey(it)) {
                            stack.add(scope.variables[it]!!)
                        } else {
                            scope.setLog("Invalid value: $it")
                            // return "Exception" // TODO
                        }
                    }
                    else {
                        stack.add(it.toDouble())
                    }
                }
            }
        }
    }
    return stack.removeLast()
}