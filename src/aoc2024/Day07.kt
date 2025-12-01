package aoc2024

import dropIndex
import println
import readInput
import java.math.BigInteger

class Equation(input: String, private val operators: CharArray) {
    val result = input.substringBefore(":").toBigInteger()
    private val operands = input.substringAfter(": ").split(" ").map { it.toBigInteger() }

    private fun String.replaceOperators(operators: CharArray): String {
        var new = this
        operators.forEachIndexed { index, operator -> new = new.replace(index.toString(), operator.toString()) }
        return new
    }

    fun isValid(): Boolean {

        val bitCount = operands.size - 1
        val maxBase = operators.size.toBigInteger().pow(bitCount).toInt() - 1
        val permutations = (0..maxBase)
            .map { it.toString(operators.size).padStart(bitCount, '0').replaceOperators(operators).toCharArray() }

        permutations.forEach { operators ->
            var resultCandidate = operands.first()
            operands.dropIndex(0).forEachIndexed { index, operand ->
                when (operators[index]) {
                    '+' -> resultCandidate += operand
                    '*' -> resultCandidate *= operand
                    '|' -> resultCandidate = resultCandidate.toString().plus(operand.toString()).toBigInteger()
                    else -> throw IllegalStateException("Invalid operator ${operators[index]}")
                }
            }
            if (resultCandidate == result) return true
        }

        return false

    }
}

fun main() {


    fun part1(input: List<String>): BigInteger {
        return input.map { Equation(it, charArrayOf('+', '*')) }.filter { it.isValid() }.sumOf { it.result }
    }

    fun part2(input: List<String>): BigInteger {
        return input.map { Equation(it, charArrayOf('+', '*', '|')) }.filter { it.isValid() }.sumOf { it.result }
    }


    val testInput = readInput("2024", "Day07_test")
    val input = readInput("2024", "Day07")

    check(part1(testInput) == "3749".toBigInteger())
    part1(input).println()

    check(part2(testInput) == "11387".toBigInteger())
    part2(input).println()
}
