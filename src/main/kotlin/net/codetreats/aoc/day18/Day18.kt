package net.codetreats.aoc.day18

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.SinglePoint
import net.codetreats.aoc.util.Logger
import kotlin.math.max
import kotlin.math.min

class Day18 : Day<List<String>>(18) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private fun scanLine(borders: List<List<Long>>): Long {
        var count = 0L
        borders.forEachIndexed { y, line ->
            var inside = false
            var edge = false
            var yOffset = 0
            line.forEachIndexed { x, idx ->
                if (!edge) {
                    if (inside) {
                        val fill = idx - line[x - 1] - 1
                        count += fill
                    }
                    if (x < line.size - 1 && line[x + 1] == idx + 1) {
                        edge = true
                    }
                    inside = !inside
                    if (y > 0 && borders[y - 1].contains(idx)) {
                        yOffset = -1
                    }

                    if (y < borders.size - 1 && borders[y + 1].contains(idx)) {
                        yOffset = 1
                    }


                } else {
                    if (x < line.size - 1 && line[x + 1] != idx + 1) {
                        edge = false
                        var curYOffset = 0
                        if (y > 0 && borders[y - 1].contains(idx)) {
                            curYOffset = -1
                        }

                        if (y < borders.size - 1 && borders[y + 1].contains(idx)) {
                            curYOffset = 1
                        }
                        if (curYOffset == yOffset) {
                            inside = !inside
                        }
                    }
                }
            }
        }
        return count
    }

    private fun solve(data: List<String>, part1: Boolean): String {
        val dirMap = if (part1) {
            mapOf(
                "U" to SinglePoint(0, -1),
                "D" to SinglePoint(0, 1),
                "R" to SinglePoint(1, 0),
                "L" to SinglePoint(-1, 0),
            )
        } else {
            mapOf(
                0 to SinglePoint(1, 0),
                1 to SinglePoint(0, 1),
                2 to SinglePoint(-1, 0),
                3 to SinglePoint(0, -1),
            )

        }
        var cur = SinglePoint(0, 0)
        var max = SinglePoint(0, 0)
        var min = SinglePoint(0, 0)
        var count = 0L

        data.forEach { line ->
            val (dirString, stepsString, color) = line.split(" ")
            val code = color.filter { it !in listOf('#', '(', ')') }.toInt(16)
            val dir = if (part1) dirString else code % 16
            val steps = if (part1) stepsString.toInt() else code / 16
            val direction = dirMap[dir]!!
            cur = SinglePoint(cur.x + direction.x * steps, cur.y + direction.y * steps)
            max = SinglePoint(max(max.x, cur.x), max(max.y, cur.y))
            min = SinglePoint(min(min.x, cur.x), min(min.y, cur.y))
        }

        cur = SinglePoint(-min.x, -min.y)
        val borders = MutableList(max.y - min.y + 2) { mutableListOf<Long>() }
        data.forEach { line ->
            val (dirString, stepsString, color) = line.split(" ")
            val code = color.filter { it !in listOf('#', '(', ')') }.toInt(16)
            val dir = if (part1) dirString else code % 16
            val steps = if (part1) stepsString.toInt() else code / 16
            val direction = dirMap[dir]!!
            for (step in 1.. steps) {
                cur = SinglePoint(cur.x + direction.x, cur.y + direction.y)
                borders[cur.y].add(cur.x.toLong())
                count += 1
            }
        }

        borders.map { it.sort() }
        count += scanLine(borders)
        return count.toString()

    }

    override fun run1(data: List<String>): String {
        return solve(data, true)
    }

    override fun run2(data: List<String>): String {
        return solve(data, false)
    }
}