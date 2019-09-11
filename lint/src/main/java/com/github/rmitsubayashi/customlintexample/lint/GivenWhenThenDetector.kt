package com.github.rmitsubayashi.customlintexample.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*
import java.util.*

class GivenWhenThenDetector: Detector(), SourceCodeScanner {
    companion object {
        @JvmField
        val ISSUE = Issue.Companion.create(
            "GivenWhenThen",
            "method name not following GIVEN_WHEN_THEN format",
            "mikan Android uses the Given When Then style for structuring tests. To make your tests easy to read, rename test methods to GIVEN_~_WHEN_~_THEN_~",
            Implementation(GivenWhenThenDetector::class.java, Scope.JAVA_FILE_SCOPE.apply { add(Scope.TEST_SOURCES) }),
            "https://martinfowler.com/bliki/GivenWhenThen.html#targetText=Given%2DWhen%2DThen%20is%20a,%2DDriven%20Development%20(BDD).&targetText=You%20can%20think%20of%20it,pre%2Dconditions%20to%20the%20test.",
            Category.CORRECTNESS
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return Collections.singletonList(UMethod::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object: UElementHandler() {
            override fun visitMethod(node: UMethod) {
                if (!node.annotationExists("Test")) {
                    return
                }
                if (!GivenWhenThenMatcher.abidesGivenWhenThen(node.name)) {
                    val location = context.getLocation(node.identifyingElement ?: node)
                    context.report(ISSUE, node, location, ISSUE.getBriefDescription(TextFormat.TEXT))
                }
            }
        }
    }

    // KotlinだとfindAnnotationが空になるから、ごり押し。。
    private fun UMethod.annotationExists(annotation: String): Boolean {
        return if (isKotlin(this)) {
            this.text.startsWith("@$annotation\n")
        } else {
            this.findAnnotation(annotation) != null
        }
    }
}