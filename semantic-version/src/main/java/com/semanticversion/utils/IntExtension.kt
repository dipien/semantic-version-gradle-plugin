package com.semanticversion.utils

import kotlin.math.abs
import kotlin.math.log10

fun Int.countDigits() = when (this) {
    0 -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}
