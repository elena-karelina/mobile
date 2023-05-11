package com.example.ohana.ui.theme
import com.example.ohana.ui.theme.BlockHolder

class MathOperation {

    val a: Double = 0.0
    val b: Double = 1.1

    fun mathAddition(a: Double, b: Double): Double {
        return a + b
    }

    fun mathSubtraction(a: Double, b: Double): Double {
        return a - b
    }

    fun mathMultiplication(a: Double, b: Double): Double {
        return a * b
    }

    fun mathDivision(a: Double, b: Double): Double {
        return a / b
    }

    fun mathDivisionWithRemainder(a: Double, b: Double): Double {
        return a % b
    }

    fun mathDivisionInteger(a: Double, b: Double): Double {
        return a // b
    }
}
