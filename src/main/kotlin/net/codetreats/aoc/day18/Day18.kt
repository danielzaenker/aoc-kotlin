package net.codetreats.aoc.day18

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.SinglePoint
import net.codetreats.aoc.util.Logger
import java.util.stream.IntStream
import java.util.stream.LongStream
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.streams.toList

class Day18 : Day<List<String>>(18) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private fun scanLine(borders: List<List<Long>>): Long {
        //var count = 0L
        return LongStream.range(0, borders.size.toLong()).parallel().map { l ->
            val y = l.toInt()
            val line = borders[y]
            var inside = false
            var edge = false
            var yOffset = 0
            var count = 0L
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
            count
        }.sum()
    }

    private fun polyArea(poly: List<SinglePoint>): Long {
        val n = poly.size
        var area = 0L
        for (i in 1..n) {
            val d = poly[i - 1].x.toLong() * poly[i % n].y.toLong() - poly[i % n].x.toLong() * poly[i - 1].y.toLong()
            area += d
        }

        return abs(area) / 2
    }

    private fun solve(data: List<String>, part1: Boolean, scanLine: Boolean): String {
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
        val poly = mutableListOf<SinglePoint>()

        data.forEach { line ->
            val (dirString, stepsString, color) = line.split(" ")
            val code = color.filter { it !in listOf('#', '(', ')') }.toInt(16)
            val dir = if (part1) dirString else code % 16
            val steps = if (part1) stepsString.toInt() else code / 16
            val direction = dirMap[dir]!!
            poly.add(cur)
            count += steps
            cur = SinglePoint(cur.x + direction.x * steps, cur.y + direction.y * steps)
            max = SinglePoint(max(max.x, cur.x), max(max.y, cur.y))
            min = SinglePoint(min(min.x, cur.x), min(min.y, cur.y))
        }

        if (scanLine) {
            cur = SinglePoint(-min.x, -min.y)
            val borders = MutableList(max.y - min.y + 2) { mutableListOf<Long>() }
            data.forEach { line ->
                val (dirString, stepsString, color) = line.split(" ")
                val code = color.filter { it !in listOf('#', '(', ')') }.toInt(16)
                val dir = if (part1) dirString else code % 16
                val steps = if (part1) stepsString.toInt() else code / 16
                val direction = dirMap[dir]!!
                for (step in 1..steps) {
                    cur = SinglePoint(cur.x + direction.x, cur.y + direction.y)
                    borders[cur.y].add(cur.x.toLong())
                }
            }

            borders.map { it.sort() }
            count += scanLine(borders)
            return count.toString()
        } else {
            // pick's theorem:
            // innerArea = polyArea - polyLength / 2 + 1
            // totalArea = innerArea + polyLength = polyArea + polyLength / 2 + 1
            val total =  polyArea(poly) + count / 2L + 1L // picks theorem
            return total.toString()
        }
    }

    override fun run1(data: List<String>): String {
        return solve(data, part1 = true, scanLine = false)
    }

    override fun run2(data: List<String>): String {
        return solve(data, part1 = false, scanLine = false)
    }
}