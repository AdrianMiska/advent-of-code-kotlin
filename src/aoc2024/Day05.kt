package aoc2024

import println
import readInput
import java.util.Comparator

fun main() {

    fun <T> List<Pair<T, T>>.toComparator(): Comparator<T> = kotlin.Comparator { a: T, b: T ->
        this.firstOrNull { it.toList().containsAll(listOf(a, b)) }?.let {
            when (it.first) {
                a -> -1
                b -> 1
                else -> 0
            }
        } ?: 0
    }


    fun getRulesAndUpdates(input: List<String>): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val rules = input.takeWhile { it != "" }.flatMap { it.split("|").map { it.toInt() }.zipWithNext() }
        val updates = input.takeLastWhile { it != "" }.map { it.split(",").map { it.toInt() } }
        return Pair(rules, updates)
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)

        val correctUpdates = updates.filter { it.sortedWith(rules.toComparator()) == it }

        val middlePages = correctUpdates.map { it[(it.size / 2)] }
        return middlePages.sum()
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = getRulesAndUpdates(input)

        val incorrectUpdates = updates.filter { it.sortedWith(rules.toComparator()) != it }
        val correctedUpdates = incorrectUpdates.map { it.sortedWith(rules.toComparator()) }

        val middlePages = correctedUpdates.map { it[(it.size / 2)] }
        return middlePages.sum()
    }


    val testInput = readInput("2024", "Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("2024", "Day05")
    part1(input).println()
    part2(input).println()
}
