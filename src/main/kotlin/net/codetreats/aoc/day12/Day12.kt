package net.codetreats.aoc.day12

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

class Day12 : Day<List<String>>(12) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = true

    override fun convert(input: List<String>): List<String> = input

    private fun solve(springs: String, brokenSizes: List<Int>, curIdx: Int, curBrokenIdx: Int, curBrokenSize: Int, dp: MutableMap<Triple<Int, Int, Int>, Long>): Long {
        val inBroken = curBrokenSize > 0
        if (curIdx >= springs.length) {
            if (inBroken) {
                if (curBrokenSize != brokenSizes[curBrokenIdx] || curBrokenIdx != brokenSizes.size - 1) {
                    return 0
                }
            } else {
                if (curBrokenIdx != brokenSizes.size) {
                    return 0
                }
            }
            return 1
        }

        val spring = springs[curIdx]
        val possible = when(spring) {
            '#' -> listOf('#')
            '.' -> listOf('.')
            '?' -> listOf('#', '.')
            else -> throw Exception("impossible")
        }

        if (dp[Triple(curIdx, curBrokenIdx, curBrokenSize)] == null) {
            var result = 0L
            for (s in possible) {
                if (s == '#' && curBrokenIdx < brokenSizes.size && curBrokenSize < brokenSizes[curBrokenIdx]) {
                    result += solve(springs, brokenSizes, curIdx + 1, curBrokenIdx, curBrokenSize + 1, dp)
                } else if (s == '.') {
                    if (inBroken && curBrokenIdx < brokenSizes.size && curBrokenSize == brokenSizes[curBrokenIdx]) {
                        result += solve(springs, brokenSizes, curIdx + 1, curBrokenIdx + 1, 0, dp)
                    } else if (!inBroken){
                        result += solve(springs, brokenSizes, curIdx + 1, curBrokenIdx, 0, dp)
                    }
                }
            }
            dp[Triple(curIdx, curBrokenIdx, curBrokenSize)] = result
        }
        return dp[Triple(curIdx, curBrokenIdx, curBrokenSize)]!!
    }

    private fun solvePretty(springs: String, brokenSizes: List<Int>, curIdx: Int, dp: MutableMap<String, Long>): Long {
        if (curIdx >= springs.length) {
            val groups = springs.split(".").filter { it.isNotBlank() }
            val valid = groups.zip(brokenSizes) { group, brokenSize ->
                group.length == brokenSize
            }.all { it } && groups.size == brokenSizes.size
            return if (valid) {
                1
            } else {
                0
            }
        }

        var result = 0L
        if (springs[curIdx] == '?') {
            result += solvePretty(
                springs.substring(0, curIdx) + '#' + springs.substring(curIdx + 1),
                brokenSizes,
                curIdx + 1,
                dp
            )
            result += solvePretty(
                springs.substring(0, curIdx) + '.' + springs.substring(curIdx + 1),
                brokenSizes,
                curIdx + 1,
                dp
            )
        } else {
            result += solvePretty(springs, brokenSizes, curIdx + 1, dp)
        }
        return result
    }


    private fun run(data: List<String>, unfoldBy: Int): Long {
        val results = data.filter { it.isNotBlank() }.parallelStream().map { line ->
            val (springsString, list) = line.split(" ")
            val springs = generateSequence { springsString }.take(unfoldBy).joinToString("?")
            val brokenSizes = list.split(",").map { it.toInt() }
            val brokenSizesRepeated = generateSequence { brokenSizes }.take(unfoldBy).flatten().toList()
            //solvePretty(springs, brokenSizesRepeated, 0, mutableMapOf())// too slow for part 2 :(
            solve(springs, brokenSizesRepeated, 0, 0, 0, mutableMapOf())
        }
        return results.toList().sum()
    }

    override fun run1(data: List<String>): String {
        return run(data, 1).toString()
    }

    override fun run2(data: List<String>): String {
        return run(data, 5).toString()
    }
}