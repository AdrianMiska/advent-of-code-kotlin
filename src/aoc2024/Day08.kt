package aoc2024

import isInBounds
import minus
import pairwise
import plus
import println
import readInput

class Antenna(private val coordinates: Pair<Int, Int>, val frequency: Char) {

    fun getAntinode(other: Antenna): Pair<Int, Int> {
        val distance = this.coordinates - other.coordinates
        return this.coordinates + distance
    }

    fun getHarmonicAntinodes(other: Antenna, maxX: Int, maxY: Int): Sequence<Pair<Int, Int>> {
        val distance = this.coordinates - other.coordinates
        var nextAntinode = this.coordinates

        return generateSequence {
            if(!(nextAntinode).isInBounds(maxX, maxY)) return@generateSequence null
            return@generateSequence nextAntinode.also { nextAntinode += distance }
        }

    }

}

fun main() {


    fun part1(input: List<String>): Int {
        val maxHeight = input.size
        val maxWidth = input.first().count()


        val antennae = input.flatMapIndexed { x, line ->
            line.mapIndexedNotNull { y, char ->
                if (char.isLetterOrDigit()) {
                    Antenna(Pair(x, y), char)
                } else null
            }
        }

        val antinodes = antennae.groupBy { it.frequency }.values.flatMap {
            it.pairwise().map { pair ->
                pair.first.getAntinode(pair.second)
            }
        }.filter {
            it.isInBounds(maxHeight - 1, maxWidth - 1)
        }.distinct()

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val maxHeight = input.size
        val maxWidth = input.first().count()

        val antennae = input.flatMapIndexed { x, line ->
            line.mapIndexedNotNull { y, char ->
                if (char.isLetterOrDigit()) {
                    Antenna(Pair(x, y), char)
                } else null
            }
        }

        val antinodes = antennae.groupBy { it.frequency }.values.flatMap {
            it.pairwise().flatMap { pair ->
                pair.first.getHarmonicAntinodes(pair.second, maxHeight - 1, maxWidth - 1)
            }
        }.distinct()

        return antinodes.size
    }


    val testInput = readInput("2024", "Day08_test")
    val input = readInput("2024", "Day08")

    check(part1(testInput) == 14)
    part1(input).println()

    check(part2(testInput) == 34)
    part2(input).println()
}
