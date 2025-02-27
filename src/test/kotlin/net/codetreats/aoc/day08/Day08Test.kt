package net.codetreats.aoc.day08

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day08Test {
    private lateinit var classUnderTest : Day08

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day08()
    }

    @Test
    fun testPart01() {
        assertEquals("21883", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("2", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("12833235391111", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("6", classUnderTest.run2(true))
    }
}
