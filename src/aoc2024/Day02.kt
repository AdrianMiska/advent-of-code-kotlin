package aoc2024

import allDecreasing
import allIncreasing
import dropIndex
import println
import readInput
import kotlin.math.abs

fun main() {

    /**
     * Splits the text into a lists of integers.
     */
    fun String.splitToReport(): List<Int> = split(Regex("\\s")).map { it.toInt() }


    fun Iterable<Int>.safeAdjacency(): Boolean {
        return zipWithNext { a, b -> abs(a - b) in 1..3 }.all { it }
    }

    fun List<Int>.isSafeReport(): Boolean {
        if (!this.allIncreasing() && !this.allDecreasing()) {
            return false
        }
        return this.safeAdjacency()
    }

    fun List<Int>.isSafeReportWithDampener(): Boolean {
        if (this.isSafeReport()) {
            return true
        }

        //safe if any dropped value results in a safe report
        return indices.any { this.dropIndex(it).isSafeReport() }
    }

    fun part1(input: List<String>): Int {
        val reports = input.map { it.splitToReport() }

        val safeReports = reports.filter { it.isSafeReport() }

        return safeReports.size
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.splitToReport() }

        val safeReports = reports.filter { it.isSafeReportWithDampener() }

        return safeReports.size
    }


    val testInput = readInput("2024", "Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("2024", "Day02")
    part1(input).println()
    part2(input).println()
}
