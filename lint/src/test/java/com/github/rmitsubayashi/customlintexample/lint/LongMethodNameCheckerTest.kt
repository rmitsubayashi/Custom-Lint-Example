package com.github.rmitsubayashi.customlintexample.lint

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class LongMethodNameCheckerTest {
    @Test
    fun isTooLong() {
        //256 bytes
        val className = /*C*/"ommentViewModelTest"
        val methodName = "GIVEN_二日連続で勉強して次の日になっている_WHEN_昨日の「今日からの成績表示」時のコメントと今日の「昨日からの成績表示」時のコメントを取得する_THEN_同じコメント"
        val result = LongMethodNameChecker.isTooLong(className, methodName)
        assertTrue(result)
    }

    @Test
    fun isNotTooLong() {
        //255 bytes
        val className = /*Co*/"mmentViewModelTest"
        val methodName = "GIVEN_二日連続で勉強して次の日になっている_WHEN_昨日の「今日からの成績表示」時のコメントと今日の「昨日からの成績表示」時のコメントを取得する_THEN_同じコメント"
        val result = LongMethodNameChecker.isTooLong(className, methodName)
        assertFalse(result)
    }

    @Test
    fun empty() {
        val input = ""
        val result = LongMethodNameChecker.isTooLong(input, input)
        assertFalse(result)
    }

    @Test
    fun somehowThisThrowsErrorOnLintTest() {
        val className = "TestClass"
        val methodName = "short"
        val result = LongMethodNameChecker.isTooLong(className, methodName)
        assertFalse(result)
    }
}