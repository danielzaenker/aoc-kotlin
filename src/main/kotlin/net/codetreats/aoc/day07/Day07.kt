package net.codetreats.aoc.day07

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

class Day07 : Day<List<String>>(7) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input
    private fun convertToHex(input: List<String>, joker: Boolean): List<String> {
        val mapping = if (joker) {
            mapOf(
                'J' to '1',
                'T' to 'a',
                'Q' to 'b',
                'K' to 'c',
                'A' to 'd'
            )
        } else {
            mapOf(
                'T' to 'a',
                'J' to 'b',
                'Q' to 'c',
                'K' to 'd',
                'A' to 'e'
            )
        }
        return input.filter { it.isNotBlank() }.map { line ->
            var newLine = line
            mapping.forEach { (k, v) ->
                newLine = newLine.replace(k, v)
            }
            newLine
        }
    }

    private fun rank(hand: String, withJoker: Boolean): Int {
        val cardCount = mutableMapOf<Char, Int>()
        hand.forEach { cardCount[it] = cardCount.getOrDefault(it, 0) + 1 }
        if (withJoker) {
            val joker = hand.count { it == '1' }
            if (joker in 1..4) {
                val maxKey = cardCount.keys.sortedByDescending { cardCount[it]!! }.first { it != '1' }
                cardCount[maxKey] = cardCount[maxKey]!! + joker
                cardCount['1'] = 0
            }
        }
        if (cardCount.values.sum() != 5) {
            throw Exception("impossible")
        }
        return when(cardCount.values.max()) {
            5 -> 7
            4 -> 6
            3 -> if (2 in cardCount.values) 5 else 4
            2 -> if (cardCount.values.count { it == 2 } == 2) 3 else 2
            1 -> 1
            else -> throw Exception("Impossible")
        }
    }

    override fun run1(data: List<String>): String {
        return run(data, false)
    }
    private fun run(data: List<String>, joker: Boolean): String {
        val data = convertToHex(data, joker)
        val ranks = mutableMapOf(
            1 to mutableListOf<String>(),
            2 to mutableListOf<String>(),
            3 to mutableListOf<String>(),
            4 to mutableListOf<String>(),
            5 to mutableListOf<String>(),
            6 to mutableListOf<String>(),
            7 to mutableListOf<String>(),
        )
        data.forEach { line ->
            val hand = line.split(" ").first()
            val rank = rank(hand, joker)
            ranks[rank]!!.add(line)
        }
        var count = 0L

        return ranks.keys.sorted().sumOf { rank ->
            ranks[rank]!!.sortedBy { it.split(" ").first().toInt(16) }.sumOf { bid ->
                count += 1
                bid.split(" ").last().toLong() * count
            }
        }.toString()
    }

    override fun run2(data: List<String>): String {
        return run(data, true)
    }
}