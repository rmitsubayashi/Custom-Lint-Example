package com.github.rmitsubayashi.customlintexample.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue
import java.util.*

class CustomIssueRegistry: IssueRegistry() {
    override val issues: List<Issue>
        get() = Collections.singletonList(GivenWhenThenDetector.ISSUE)

    override val api: Int =
        com.android.tools.lint.detector.api.CURRENT_API
}