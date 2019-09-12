package com.github.rmitsubayashi.customlintexample.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import java.util.*

class LongMethodNameDetectorTest: LintDetectorTest() {
    override fun getDetector(): Detector = LongMethodNameDetector()
    override fun getIssues(): MutableList<Issue> = Collections.singletonList(LongMethodNameDetector.ISSUE)

    private val headerJava =
                "package test.pkg;\n" +
                "public class TestClass {\n" +
                "    public void "

    private val footerJava =
                                    "(){\n" +
                "        return;\n" +
                "    }\n" +
                "}"
    private val shortMethodName = "shorty"
    private val longMethodName = "looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"

    private val headerKotlin =
                "package com.github.rmitsubayashii.customlintexample\n" +
                "class TestClass {\n" +
                "    fun "
    private val footerKotlin =
                            "(){\n" +
                "        return\n" +
                "    }\n" +
                "}"

    fun testShortJava() {
        lint().files(
            java(headerJava + shortMethodName + footerJava)
        ).run()
            .expectClean()
    }

    fun testLongJava() {
        lint().files(
            java(headerJava + longMethodName + footerJava)
        ).run()
            .expectWarningCount(1).expectErrorCount(0)
    }

    fun testShortKotlin() {
        lint().files(
            kotlin(headerKotlin + shortMethodName + footerKotlin)
        ).run()
            .expectClean()
    }

    fun testLongKotlin() {
        lint().files(
            kotlin(headerKotlin + longMethodName + footerKotlin)
        ).run()
            .expectWarningCount(1).expectErrorCount(0)
    }
}