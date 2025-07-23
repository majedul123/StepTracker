package com.majedul.auth.domain

interface PatternValidator {

    fun matches(value: String) : Boolean
}