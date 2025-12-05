package aoc2025

import get
import padded
import println
import readInput
import set
import windowed2D

fun main() {

    class Shelf(val levels: List<MutableList<Char>>) {

        fun countAccessibleItems(): Int {
            return this.levels.padded('.').windowed2D(3)
                .filter { it.hasItem() }
                .count { it.isAccessible() }
        }

        fun removeAccessibleItems(): Int {

            val accessibleCoordinates = this.levels.padded('.').windowed2D(3)
                .chunked(levels.size)
                .flatMapIndexed { y, windows ->
                    windows.mapIndexed { x, window ->
                        if (window.hasItem() && window.isAccessible()) {
                            x to y
                        } else {
                            null
                        }
                    }
                }
                .filterNotNull()

            return accessibleCoordinates
                .map { this.levels.set(it, '.') }
                .count()
        }

        private fun List<List<Char>>.hasItem(): Boolean = this.get(Pair(1, 1)) == '@'
        private fun List<List<Char>>.isAccessible(): Boolean = this.flatten().count { item -> item == '@' } <= 4

    }

    fun part1(input: List<String>): Number {
        return Shelf(input.map { it.toMutableList() }).countAccessibleItems()
    }

    fun part2(input: List<String>): Number {
        val shelf = Shelf(input.map { it.toMutableList() })
        var removed = 0
        while (shelf.countAccessibleItems() > 0) {
            removed += shelf.removeAccessibleItems()
        }
        return removed
    }

    val testInput = readInput("2025", "Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("2025", "Day04")
    part1(input).println()
    part2(input).println()
}