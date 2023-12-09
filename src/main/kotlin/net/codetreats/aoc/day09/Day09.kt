package net.codetreats.aoc.day09

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

class Day09 : Day<List<List<Long>>>(9) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<List<Long>> {
        return input.map { it.split(" ").map { it.toLong() } }
    }

    private fun getNextRow(cur: List<Long>): List<Long> {
        return cur.windowed(2) { (first, second) ->
            second - first
        }
    }

    override fun run1(data: List<List<Long>>): String {
        return data.sumOf { nums ->
            var cur = nums
            val ends = mutableListOf(cur.last())
            while (!cur.all { it == 0L }) {
                cur = getNextRow(cur)
                ends.add(cur.last())
            }
            ends.reduceRight { end, acc -> acc + end }
        }.toString()
    }

    override fun run2(data: List<List<Long>>): String {
        return data.sumOf { nums ->
            var cur = nums
            val starts = mutableListOf(cur.first())
            while (!cur.all { it == 0L }) {
                cur = getNextRow(cur)
                starts.add(cur.first())
            }
            starts.reduceRight { start, acc -> start - acc }
        }.toString()
    }
}