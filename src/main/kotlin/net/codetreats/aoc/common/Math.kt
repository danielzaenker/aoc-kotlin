package net.codetreats.aoc.common

// https://rosettacode.org/wiki/Least_common_multiple#Kotlin
fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b
