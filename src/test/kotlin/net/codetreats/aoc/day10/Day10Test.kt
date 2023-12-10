package net.codetreats.aoc.day10

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day10Test {
    private lateinit var classUnderTest : Day10

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day10()
    }

    @Test
    fun testPart01() {
        assertEquals("7102", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("8", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("363", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("10", classUnderTest.run2(true))
    }
}
