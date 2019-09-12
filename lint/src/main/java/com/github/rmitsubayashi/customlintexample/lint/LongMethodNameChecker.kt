package com.github.rmitsubayashi.customlintexample.lint

object LongMethodNameChecker {
    fun isTooLong(className: String, methodName: String): Boolean {
        //compiled class w/ method
        val s = "$className\$$methodName\$1.class"
        val bytes = s.toByteArray()
        return bytes.size > 255
    }
}