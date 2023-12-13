package net.codetreats.aoc.day13

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

class Day13 : Day<List<String>>(13) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private fun getVerticalPattern(pattern: List<String>): List<String> {
        val patternVertical = MutableList(pattern.first().length) {""}
        pattern.forEach { line ->
            line.forEachIndexed { x, c ->
                patternVertical[x] = patternVertical[x] + c
            }
        }
        return patternVertical
    }

    private fun solve1(pattern: List<String>): Long {

        val patternVertical = getVerticalPattern(pattern)

        val rowStarts = pattern.windowed(2).mapIndexed { index, (a, b) -> if (a==b) index else null }.filterNotNull()
        val colStarts = patternVertical.windowed(2).mapIndexed { index, (a, b) -> if (a == b) index else null }.filterNotNull()
        val rowValid = rowStarts.firstOrNull { row -> pattern.subList(row + 1, pattern.size).zip(pattern.subList(0, row + 1).reversed()) { a, b -> a == b }.all { it } }
        val colValid = colStarts.firstOrNull { col -> patternVertical.subList(col + 1, patternVertical.size).zip(patternVertical.subList(0, col + 1).reversed()) { a, b -> a == b }.all { it } }

        return if (rowValid != null) {
            (rowValid + 1L) * 100L
        } else if (colValid != null) {
            colValid + 1L
        } else {
            throw Exception("impossible")
        }
    }

    private fun String.diff(other: String): Int {
        return this.zip(other) { a, b -> if (a == b) 0 else 1 }.sum()
    }

    private fun filterValid(pattern: List<String>, startRows: List<Pair<Boolean, Int>>): List<Pair<Boolean, Int>> {
        return startRows.filter { row ->
            var rowChanged = false
            pattern.subList(row.second+2, pattern.size).zip(pattern.subList(0, row.second).reversed()) { a, b ->
                if (!row.first && !rowChanged && a.diff(b) == 1) {
                    rowChanged = true
                    true
                } else {
                    a == b
                }
            }.all { it } && (rowChanged || row.first)}
    }

    private fun solve2(pattern: List<String>): Long {
        val patternVertical = getVerticalPattern(pattern)

        val (firstRows, firstCols) = listOf(pattern, patternVertical).map {  p -> p.windowed(2).mapIndexed { index, (a, b) ->
            if (a == b) {
                Pair(false, index)
            } else if (a.diff(b) == 1) {
                Pair(true, index)
            } else {
                null
            }
        }.filterNotNull() }

        val rowEdge = firstRows.filter {it.first && (it.second == 0 || it.second == pattern.size - 2)}
        if (rowEdge.isNotEmpty()) {
            return 100L * (rowEdge.first().second + 1L)
        }
        val colEdge = firstCols.filter {it.first && (it.second == 0 || it.second == patternVertical.size - 2)}
        if (colEdge.isNotEmpty())  {
            return 1L + colEdge.first().second
        }
        val validRows = filterValid(pattern, firstRows)
        val validCols = filterValid(patternVertical, firstCols)

        // should be only one possibility left
        return if (validRows.isNotEmpty()) {
            (validRows.first().second.toLong() + 1L) * 100L
        } else if (validCols.isNotEmpty()) {
            validCols.first().second.toLong() + 1L
        } else {
            pattern.forEach { println(it) }
            throw Exception("impossible")
        }
    }

    private fun getPattern(data: List<String>): MutableList<List<String>> {
        val cur = mutableListOf<String>()
        val patterns = mutableListOf<List<String>>()
        data.forEach { line ->
            if (line.isBlank()) {
                patterns.add(cur.toList())
                cur.clear()
            } else {
                cur.add(line)
            }
        }
        patterns.add(cur.toList())
        return patterns
    }

    override fun run1(data: List<String>): String {
        return getPattern(data).sumOf { solve1(it) }.toString()
    }

    override fun run2(data: List<String>): String {
        return getPattern(data).sumOf { solve2(it) }.toString()
    }
}