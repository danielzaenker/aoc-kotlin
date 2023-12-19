package net.codetreats.aoc.day19

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day19Test {
    private lateinit var classUnderTest : Day19

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day19()
    }

    @Test
    fun testPart01() {
        assertEquals("368523", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("19114", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("124167549767307", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("167409079868000", classUnderTest.run2(true))
    }
}
