package aoc2024

import println
import readInput

fun main() {

    fun String.executeInstruction(): Int {
        val matches = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").matchEntire(this)?.groupValues?.drop(1) ?: emptyList()

        if (matches.count() != 2) {
            throw IllegalStateException("Not a valid instruction: $this")
        }

        val operands = matches.first().toString().toInt() to matches.last().toString().toInt()
        return operands.first * operands.second

    }

    fun part1(input: List<String>): Int {
        val memory = input.reduce { acc, s -> acc + s }

        val instructions = Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(memory).map { it.value }

        return instructions.sumOf { it.executeInstruction() }
    }

    fun part2(input: List<String>): Int {
        val memory = input.reduce { acc, s -> acc + s }

        val instructions = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)").findAll(memory).map { it.value }

        var enabled = true

        val results = mutableListOf<Int>()

        for (instruction in instructions) {
            when (instruction) {
                "do()" -> {
                    enabled = true
                    continue
                }

                "don't()" -> {
                    enabled = false
                    continue
                }
            }

            if (enabled) {
                results.add(instruction.executeInstruction())
            }
        }

        return results.sum()
    }


    val testInput1 = readInput("2024", "Day03_test1")
    val testInput2 = readInput("2024", "Day03_test2")
    check(part1(testInput1) == 161)
    check(part2(testInput2) == 48)

    val input = readInput("2024", "Day03")
    part1(input).println()
    part2(input).println()
}
