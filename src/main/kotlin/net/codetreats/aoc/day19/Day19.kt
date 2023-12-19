package net.codetreats.aoc.day19

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

private data class Rule(val name: Int, val greater: Boolean, val number: Int, val next: String)

private data class Workflow(val rules: List<Rule>, val other: String) {
    fun next(part: Part): String {
        val ruleMatch = rules.firstOrNull { rule ->
            val cat = part[rule.name]
            if (rule.greater) {
                cat > rule.number
            } else {
                cat < rule.number
            }
        }

        return ruleMatch?.next ?: other
    }
}

typealias PartRange = List<IntRange>
typealias Part = List<Int>

class Day19 : Day<List<String>>(19) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private val categories = mapOf(
        "x" to 0,
        "m" to 1,
        "a" to 2,
        "s" to 3,
    )
    private fun parseWorkflows(data: List<String>): Map<String, Workflow> {
        val workflows = mutableMapOf<String, Workflow>()

        data.takeWhile { it.isNotBlank() }.forEach { line ->
            val (name, ruleString) = line.split("{")
            val rules = ruleString.filter { it != '}' }.split(",")
            val workflowRules = mutableListOf<Rule>()
            rules.take(rules.size - 1).map { r ->
                val (exp, next) = r.split(":")
                if (exp.contains('>')) {
                    val (ruleName, number) = exp.split(">")
                    workflowRules.add(Rule(categories[ruleName]!!, true, number.toInt(), next))
                } else {
                    val (ruleName, number) = exp.split("<")
                    workflowRules.add(Rule(categories[ruleName]!!, false, number.toInt(), next))
                }
            }
            val other = rules.last()
            workflows[name] = Workflow(workflowRules, other)
        }

        return workflows
    }

    private fun parseParts(data: List<String>): List<Part> {
        return data.subList(data.indexOfFirst { it.isBlank() } + 1, data.size).map { line ->
            val cats = line.filter { it != '{' && it != '}' }.split(",").map {
                val (name, number) = it.split("=")
                number.toInt()
            }
            cats
        }
    }

    override fun run1(data: List<String>): String {
        val workflows = parseWorkflows(data)
        val parts = parseParts(data)

        val sum = parts.sumOf { part ->
            var cur = "in"
            while (cur != "A" && cur != "R") {
                cur = workflows[cur]!!.next(part)
            }
            if (cur == "A") {
                part.sumOf { it }
            } else {
                0
            }
        }
        return sum.toString()
    }

    private fun solve(partRange: PartRange, cur: String, workflows: Map<String, Workflow>): Long {
        if (cur == "A") {
            return partRange.map { if (it.isEmpty()) 0 else it.last - it.first + 1 }.fold(1L) { acc, i -> acc * i }
        }
        if (cur == "R") {
            return 0L
        }

        var count = 0L
        val workflow = workflows[cur]!!
        var curRange = partRange.toMutableList()

        for (rule in workflow.rules) {
            val cat = curRange[rule.name]

            if (rule.number in cat) {
                val (newInnerRange, newOuterRange) = if (rule.greater) {
                    Pair(rule.number+1..cat.last, cat.first..rule.number)
                } else {
                    Pair(cat.first..rule.number-1, rule.number..cat.last)
                }
                val nextPartRange = curRange.toMutableList()
                nextPartRange[rule.name] = newInnerRange
                count += solve(nextPartRange, rule.next, workflows)
                curRange[rule.name] = newOuterRange
            }
        }

        count += solve(curRange, workflow.other, workflows)
        return count
    }

    override fun run2(data: List<String>): String {
        val workflows = parseWorkflows(data)
        return solve("xmas".map { 1..4000 }, "in", workflows).toString()
    }
}