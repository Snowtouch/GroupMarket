package com.snowtouch.groupmarket.common.ext

import android.util.Patterns
import java.util.regex.Pattern

private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() && Pattern.compile(PASS_PATTERN).matcher(this).matches()
}
fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}