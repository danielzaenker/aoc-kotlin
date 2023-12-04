package net.codetreats.aoc.day04

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day04Test {
    private lateinit var classUnderTest : Day04

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day04()
    }

    @Test
    fun testPart01() {
        assertEquals("17782", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("13", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("8477787", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("30", classUnderTest.run2(true))
    }
}
