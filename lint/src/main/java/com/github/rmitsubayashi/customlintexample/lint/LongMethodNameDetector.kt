package com.github.rmitsubayashi.customlintexample.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import java.util.*

class LongMethodNameDetector: Detector(), SourceCodeScanner {
    companion object {
        @JvmField
        val ISSUE = Issue.create(
            "LongMethodName",
            "Method name above 255 bytes",
            "bitrise requires method names under 255 bytes (uses ext4 file system). Bitrise will fail build",
            Implementation(LongMethodNameDetector::class.java, Scope.JAVA_FILE_SCOPE.apply { add(Scope.TEST_SOURCES) }),
            "https://en.wikipedia.org/wiki/Comparison_of_file_systems#Limits",
            Category.CORRECTNESS

        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return Collections.singletonList(UMethod::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object: UElementHandler() {
            override fun visitMethod(node: UMethod) {
                print("tesst" +node.name + "\n")
                print(node.containingClass?.name + "\n\n")
                node.containingClass?.name?.let {
                    className ->
                    val methodName = node.name
                    //kotlin parses classes as well...
                    if (className == methodName) {
                        return
                    }
                    if (LongMethodNameChecker.isTooLong(className, methodName)) {
                        val location = context.getLocation(node.identifyingElement ?: node)
                        context.report(ISSUE, node, location, ISSUE.getBriefDescription(TextFormat.TEXT))
                    }
                }

            }
        }
    }
}