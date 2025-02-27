package net.codetreats.aoc.day14

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day14Test {
    private lateinit var classUnderTest : Day14

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day14()
    }

    @Test
    fun testPart01() {
        assertEquals("108918", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("136", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("100310", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("64", classUnderTest.run2(true))
    }
}
