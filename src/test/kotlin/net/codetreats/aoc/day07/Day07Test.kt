package net.codetreats.aoc.day07

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day07Test {
    private lateinit var classUnderTest : Day07

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day07()
    }

    @Test
    fun testPart01() {
        assertEquals("250347426", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("6440", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("251224870", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("5905", classUnderTest.run2(true))
    }
}
