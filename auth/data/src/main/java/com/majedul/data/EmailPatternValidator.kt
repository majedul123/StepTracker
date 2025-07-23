package com.majedul.data

import com.majedul.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
     }
}