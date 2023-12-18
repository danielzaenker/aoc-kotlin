package net.codetreats.aoc.day17

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.*
import net.codetreats.aoc.util.Logger
import java.util.PriorityQueue

class Day17 : Day<List<String>>(17) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    data class Node(val pos: SinglePoint, val dir: SinglePoint, val steps: Int)

    private fun dijkstra(data: List<String>, minSteps: Int, maxSteps: Int, printPath: Boolean = false): Int {
        val board = Board(data.first().length, data.size, 0)
        data.forEachIndexed { y, line ->
            line.forEachIndexed { x, weight ->
                board.set(x, y, weight.digitToInt())
            }
        }

        val starts = listOf(Node(SinglePoint(0, 0), SinglePoint(1, 0), 0), Node(SinglePoint(0, 0), SinglePoint(0, 1), 0))
        val distances = mutableMapOf<Node, Int>()
        val queue = PriorityQueue<Pair<Int, Node>>(16, compareBy { it.first })
        starts.forEach { queue.add(Pair(0, it)) }
        starts.forEach { distances[it] = 0 }
        val prev = mutableMapOf<Node, Node>()

        while (queue.isNotEmpty()) {
            val (distance, node) = queue.poll()
            if (node.pos == SinglePoint(board.width - 1, board.height - 1)) {
                if (printPath) {
                    val path = Board(board.width, board.height, '.')
                    for (y in 0 until board.height) {
                        for (x in 0 until board.width) {
                            path.set(x, y, board.get(x, y).value.toString().first())
                        }
                    }
                    var p = prev[node]
                    while (p != null) {
                        println(p)
                        path.set(p.pos.x, p.pos.y, 'X')
                        p = prev[p]
                    }
                    println(path)
                }

                return distance
            }

            board.neighbors(node.pos.x, node.pos.y, withDiag = false, withSelf = false).forEach { n ->
                val nextDir = SinglePoint(n.x - node.pos.x, n.y - node.pos.y)
                if (node.steps < minSteps && node.dir != nextDir) {
                    return@forEach
                }
                val nextSteps = if (node.dir == nextDir) {
                    node.steps + 1
                } else {
                    1
                }

                if (nextSteps > maxSteps) {
                    return@forEach
                }

                if (nextDir == SinglePoint(-node.dir.x, -node.dir.y)) {
                    return@forEach
                }
                val nextNode = Node(SinglePoint(n.x, n.y), nextDir, nextSteps)
                if (distance + n.value < distances.getOrDefault(nextNode, Int.MAX_VALUE)) {
                    distances[nextNode] = distance + n.value
                    queue.add(Pair(distance + n.value, nextNode))
                    if (printPath) {
                        prev[nextNode] = node
                    }
                }
            }
        }
        return 0
    }

    override fun run1(data: List<String>): String {
        return dijkstra(data, 0, 3).toString()
    }

    override fun run2(data: List<String>): String {
        return dijkstra(data, 4, 10).toString()
    }
}