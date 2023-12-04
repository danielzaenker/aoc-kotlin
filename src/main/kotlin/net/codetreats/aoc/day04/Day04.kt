package net.codetreats.aoc.day04

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

// only for numbers >= 0
private fun Int.power(x: Int): Int {
    var result = 1
    for (i in 0..<x) {
        result *= this
    }
    return result
}

class Day04 : Day<List<Int>>(4) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<Int> {
        return input.map { line ->
            val (winning, drawn) = line.split(":").last().split("|").map {
                it.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            }
            drawn.count { it in winning }
        }
    }

    override fun run1(data: List<Int>): String {
        return data.sumOf { won ->
            if (won == 0) {
                0
            } else {
                2.power(won-1)
            }
        }.toString()
    }

    override fun run2(data: List<Int>): String {
        val counts = MutableList(data.size) { 1 }
        data.forEachIndexed { idx, won ->
            val cardCount = counts[idx]
            if (won > 0) {
                for (countIdx in idx+1..idx+won) {
                   counts[countIdx] += cardCount
                }
            }
        }
        return counts.sum().toString()
    }
}