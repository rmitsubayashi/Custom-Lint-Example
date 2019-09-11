package com.github.rmitsubayashi.customlintexample.lint

import org.junit.Assert.assertTrue
import org.junit.Test

class GivenWhenThenMatcherTest {
    @Test
    fun baseCase() {
        val input = "GIVEN_a_WHEN_b_THEN_c"
        assertTrue(GivenWhenThenMatcher.abidesGivenWhenThen(input))
    }
}