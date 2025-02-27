package net.codetreats.aoc.day05

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day05Test {
    private lateinit var classUnderTest : Day05

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day05()
    }

    @Test
    fun testPart01() {
        assertEquals("993500720", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("35", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("4917124", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("46", classUnderTest.run2(true))
    }
}
