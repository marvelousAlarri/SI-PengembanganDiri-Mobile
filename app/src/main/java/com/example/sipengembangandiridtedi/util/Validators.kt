package com.example.sipengembangandiridtedi.util

private val EMAIL_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

const val MIN_PASSWORD = 6

fun isValidEmail(value: String): Boolean = EMAIL_REGEX.matches(value.trim())

fun isValidPassword(value: String): Boolean = value.length >= MIN_PASSWORD
