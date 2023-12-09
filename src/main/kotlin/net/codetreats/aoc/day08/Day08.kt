package net.codetreats.aoc.day08

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

data class Node(var left: Node?, var right: Node?, val content: String) {
    override fun toString(): String {
        return "Node(left=${left?.content}, right=${right?.content}, content='$content')"
    }
}

class Day08 : Day<MutableMap<String, Node>>(8) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    private var steps: String = ""

    override fun convert(input: List<String>): MutableMap<String, Node> {
        val nodeMap = mutableMapOf<String, Node>()
        steps = input.first().trim()
        input.drop(2).filter { it.isNotBlank() }.forEach { line ->
            val (key, children) = line.split("=").map { it.trim() }
            val (left, right) = children.filter { it != '(' && it != ')' }.split(",").map { it.trim() }
            val leftNode = nodeMap.getOrDefault(left, Node(null, null, left))
            nodeMap[left] = leftNode
            val rightNode = nodeMap.getOrDefault(right, Node(null, null, right))
            nodeMap[right] = rightNode
            val node = nodeMap.getOrDefault(key, Node(null, null, key))
            node.left = leftNode
            node.right = rightNode
            nodeMap[key] = node
        }

        return nodeMap
    }

    private fun stepsNeeded(start: Node, end: String): Long {
        var curNode = start
        var nSteps = 0L
        while (true) {
            steps.forEach { step ->
                if (curNode.content.endsWith(end)) {
                    return nSteps
                }
                curNode = if (step == 'L') {
                    curNode.left!!
                } else {
                    curNode.right!!
                }
                nSteps += 1
            }
        }
    }

    override fun run1(data: MutableMap<String, Node>): String {
        return stepsNeeded(data["AAA"]!!, "ZZZ").toString()
    }

    // https://rosettacode.org/wiki/Least_common_multiple#Kotlin
    private fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    private fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

    private fun List<Long>.toLcm() : Long {
        return this.reduce { cur, value -> lcm(cur, value) }
    }

    override fun run2(data: MutableMap<String, Node>): String {
        val starts = data.filter { (k, v) -> v.content.endsWith("A") }.values
        val destinations = starts.map { start ->
            stepsNeeded(start, "Z")
        }
        return destinations.toLcm().toString()
    }
}