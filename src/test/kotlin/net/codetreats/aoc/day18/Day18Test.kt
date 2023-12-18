package net.codetreats.aoc.day18

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day18Test {
    private lateinit var classUnderTest : Day18

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day18()
    }

    @Test
    fun testPart01() {
        assertEquals("74074", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("62", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("112074045986829", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("952408144115", classUnderTest.run2(true))
    }
}
