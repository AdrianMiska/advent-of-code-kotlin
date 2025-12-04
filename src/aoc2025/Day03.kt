package aoc2025

import println
import readInput

fun main() {

    fun String.findMaximumJoltage(length: Int): Long {
        val remainingLength = length - 1

        //find the highest digit that can still fit the remaining length after
        val highestDigit = this.substring(0, this.length - remainingLength).map { it.toString().toInt() }.max()

        if(length == 1) return highestDigit.toLong()

        val remainingJoltage = this.substring(this.indexOf(highestDigit.toString()) + 1).findMaximumJoltage(remainingLength)
        return (highestDigit.toString() + remainingJoltage.toString()).toLong()

    }

    fun part1(input: List<String>): Number {
        return input.sumOf { it.findMaximumJoltage(2) }
    }

    fun part2(input: List<String>): Number {
        return input.sumOf { it.findMaximumJoltage(12) }
    }


    val testInput = readInput("2025", "Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("2025", "Day03")
    part1(input).println()
    part2(input).println()
}