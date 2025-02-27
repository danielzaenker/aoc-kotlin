package net.codetreats.aoc.day16

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day16Test {
    private lateinit var classUnderTest : Day16

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day16()
    }

    @Test
    fun testPart01() {
        assertEquals("7236", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("46", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("7521", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("51", classUnderTest.run2(true))
    }
}
