package net.codetreats.aoc.day12

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day12Test {
    private lateinit var classUnderTest : Day12

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day12()
    }

    @Test
    fun testPart01() {
        assertEquals("6871", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("21", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("2043098029844", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("525152", classUnderTest.run2(true))
    }
}
