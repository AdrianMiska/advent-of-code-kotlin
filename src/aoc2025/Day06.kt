package aoc2025

import println
import readInput
import transpose

enum class Operator {
    ADD, MULTIPLY;

    companion object {
        fun from(char: Char) = when (char) {
            '+' -> ADD
            '*' -> MULTIPLY
            else -> throw IllegalArgumentException("Unknown operator $char")
        }
    }
}

fun main() {

    fun Pair<Operator, List<Long>>.solve(): Long {
        val operator = this.first
        val operands = this.second
        return operands.reduce { acc, operand ->
            when (operator) {
                Operator.ADD -> acc + operand
                Operator.MULTIPLY -> acc * operand
            }
        }
    }

    fun part1(input: List<String>): Long {
        val problems = input.last().toCharArray()
            .filter { !it.isWhitespace() }
            .map { c ->
                Operator.from(c) to listOf<Long>()
            }.toMutableList()

        input.dropLast(1).forEach { inputs ->
            inputs.split(" ").filter { it.isNotBlank() }.forEachIndexed { problemIndex, it ->
                problems[problemIndex] = problems[problemIndex].first to problems[problemIndex].second + it.toLong()
            }
        }

        return problems.sumOf { it.solve() }
    }

    fun part2(input: List<String>): Long {
        val problems = input.last().toCharArray()
            .filter { !it.isWhitespace() }
            .map { c ->
                Operator.from(c) to listOf<Long>()
            }.reversed().toMutableList()

        var problemIndex = 0

        input.dropLast(1)
            .map { it.split("") }
            .transpose().map { it.joinToString("").trim() }
            .reversed().drop(1).dropLast(1)
            .forEach {
                if(it.isEmpty()) problemIndex++
                else problems[problemIndex] = problems[problemIndex].first to problems[problemIndex].second + it.toLong()
            }

        return problems.sumOf { it.solve() }
    }

    val testInput = readInput("2025", "Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("2025", "Day06")
    part1(input).println()
    part2(input).println()
}