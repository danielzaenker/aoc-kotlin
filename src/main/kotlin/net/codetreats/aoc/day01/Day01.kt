package net.codetreats.aoc.day01

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

class Day01 : Day<List<String>>(1) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    override fun run1(data: List<String>): String {
        val digits = data.map { line -> line.filter { it.isDigit() } }
        val numbers = digits.map { it.first().digitToInt() * 10 + it.last().digitToInt() }
        return numbers.sum().toString()
    }

    override fun run2(data: List<String>): String {
        val numberWords = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

        val numbers = data.map {line ->
            val first = line.findAnyOf(numberWords.keys)
            val last = line.findLastAnyOf(numberWords.keys)
            var firstDigitIdx = line.indexOfFirst { it.isDigit() }
            if (firstDigitIdx < 0) {
                firstDigitIdx = line.length + 10
            }
            val lastDigitIdx = line.indexOfLast { it.isDigit() }
            var number = if (first != null && first.first < firstDigitIdx) {
                10 * numberWords[first.second]!!
            } else {
                10 * line[firstDigitIdx].digitToInt()
            }
            number += if (last != null && last.first > lastDigitIdx) {
                numberWords[last.second]!!
            } else {
                line[lastDigitIdx].digitToInt()
            }
            number
        }
        return numbers.sum().toString()
    }
}