package aoc2025

import println
import readInput

fun main() {

    fun String.asRange(): LongRange {
        val ends = this.split("-")
        if (ends.size != 2) throw IllegalArgumentException("$this is not a valid range")
        return ends.first().toLong()..ends.last().toLong()
    }

    fun Number.isRepeated(times: Int): Boolean {

        val stringRep = this.toString()
        if (stringRep.length % times != 0) return false

        val seqLen = stringRep.length / times

        val chunked = stringRep.chunked(seqLen)
        return chunked.all { it == chunked.first() }
    }

    fun Number.isRepeatedAtLeast(min: Int): Boolean {
        return (min..this.toString().length).any { times -> this.isRepeated(times) }
    }

    fun part1(input: String): Number {
        val ranges = input.split(",").map { it.asRange() }
        val invalidIds = ranges.flatMap { range -> range.filter { it.isRepeated(2) } }

        return invalidIds.sum()
    }

    fun part2(input: String): Number {
        val ranges = input.split(",").map { it.asRange() }
        val invalidIds = ranges.flatMap { range -> range.filter { it.isRepeatedAtLeast(2) } }

        return invalidIds.sum()
    }


    val testInput = readInput("2025", "Day02_test").first()
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("2025", "Day02").first()
    part1(input).println()
    part2(input).println()
}