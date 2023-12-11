package net.codetreats.aoc.day11

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Point
import net.codetreats.aoc.common.SinglePoint
import net.codetreats.aoc.util.Logger

class Day11 : Day<List<String>>(11) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = true

    override fun convert(input: List<String>): List<String> = input

    private fun solve(data: List<String>, expandTo: Long): Long {
        val emptyRowIndices: MutableList<Int> = mutableListOf()
        val colIsEmpty = MutableList(data.first().length) { true }
        val galaxies = mutableListOf<Point>()

        data.forEachIndexed { y, line ->
            val galaxyIndices = line.mapIndexed { idx, c -> if (c == '#') idx else null }.filterNotNull()
            galaxyIndices.forEach { x ->
                colIsEmpty[x] = false
                galaxies.add(SinglePoint(x, y))
            }
            if (galaxyIndices.isEmpty()) {
                emptyRowIndices.add(y)
            }
        }

        val emptyColIndices = colIsEmpty.mapIndexed { col, empty ->
            if (empty) col else null
        }.filterNotNull()

        val pairs = mutableSetOf<Pair<Point, Point>>()
        galaxies.forEach { a ->
            galaxies.forEach { b ->
                if (Pair(a, b) !in pairs && Pair(b, a) !in pairs && a != b) {
                    pairs.add(Pair(a, b))
                }
            }
        }

        return pairs.sumOf { pair ->
            val (x1, x2) = listOf(pair.first.x, pair.second.x).sorted()
            val (y1, y2) = listOf(pair.first.y, pair.second.y).sorted()
            val expandX = emptyColIndices.count { it in x1..<x2 }
            val expandY = emptyRowIndices.count { it in y1..<y2 }
            val distX = x2 - x1 + expandX * (expandTo - 1)
            val distY = y2 - y1 + expandY * (expandTo - 1)
            distX + distY
        }
    }

    override fun run1(data: List<String>): String {
        return solve(data, 2).toString()
    }

    override fun run2(data: List<String>): String {
        return solve(data, 1_000_000).toString()
    }
}