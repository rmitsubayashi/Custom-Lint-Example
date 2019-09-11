package com.github.rmitsubayashi.customlintexample.lint

object GivenWhenThenMatcher {
    fun abidesGivenWhenThen(methodName: String): Boolean {
        val regex = Regex("^GIVEN_.*_WHEN_.*_THEN_.*")
        return regex.matches(methodName)
    }
}