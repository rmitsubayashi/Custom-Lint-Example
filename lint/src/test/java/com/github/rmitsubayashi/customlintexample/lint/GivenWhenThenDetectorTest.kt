package com.github.rmitsubayashi.customlintexample.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import java.util.*

class GivenWhenThenDetectorTest: LintDetectorTest() {
    override fun getDetector(): Detector = GivenWhenThenDetector()

    override fun getIssues(): MutableList<Issue> = Collections.singletonList(GivenWhenThenDetector.ISSUE)

    private val header =
                "package test.pkg;\n" +
                "import org.junit.Test;\n" +
                "public class TestClass {\n"

    private val correctMethodName = "    public void GIVEN_a_WHEN_b_THEN_c(){\n"
    private val incorrectMethodName = "    public void test(){\n"

    private val correctAnnotation = "    @Test\n"
    private val incorrectAnnotation = "    @RandomAnnotation\n"

    private val footer =
                "        return;\n" +
                "    }\n" +
                "}"

    fun testAnnotated_correct() {
        lint().files(
            java(header + correctAnnotation + correctMethodName + footer)
        ).run()
        .expectWarningCount(0).expectErrorCount(0)
    }

    fun testAnnotated_incorrect() {
        lint().files(
            java(header + correctAnnotation + incorrectMethodName + footer)
        ).run()
            .expectWarningCount(1).expectErrorCount(0)
    }

    fun testNotAnnotated_incorrect() {
        lint().files(
            java(header + incorrectMethodName + footer)
        ).run()
        .expectWarningCount(0).expectErrorCount(0)
    }

    fun testWrongAnnotation_incorrect() {
        lint().files(
            java(header + incorrectAnnotation + incorrectMethodName + footer)
        ).run()
            .expectWarningCount(0).expectErrorCount(0)
    }

    fun testKotlinCorrect() {
        lint().files(
            kotlin("package com.github.rmitsubayashi.customlintexample\n" +
                    "\n" +
                    "import org.junit.Test\n" +
                    "\n" +
                    "import org.junit.Assert.*\n" +
                    "\n" +
                    "class ExampleUnitTest {\n" +
                    "    @Test\n" +
                    "    fun GIVEN_a_WHEN_b_THEN_c() {\n" +
                    "        assertEquals(4, 2 + 2)\n" +
                    "    }\n" +
                    "}\n")
        ).run()
            .expectWarningCount(0)
    }

    fun testKotlinIncorrect() {
        lint().files(
            kotlin("package com.github.rmitsubayashi.customlintexample\n" +
                    "\n" +
                    "import org.junit.Test\n" +
                    "\n" +
                    "import org.junit.Assert.*\n" +
                    "\n" +
                    "class ExampleUnitTest {\n" +
                    "    @Test\n" +
                    "    fun test() {\n" +
                    "        assertEquals(4, 2 + 2)\n" +
                    "    }\n" +
                    "}\n")
        ).run()
            .expectWarningCount(1)
    }
}