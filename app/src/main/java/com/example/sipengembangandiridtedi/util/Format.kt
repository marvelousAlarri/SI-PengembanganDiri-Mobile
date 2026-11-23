package com.example.sipengembangandiridtedi.util

import java.text.NumberFormat
import java.util.Locale

/** 2500000 -> "Rp2.500.000" */
fun Long.toRupiah(): String =
    "Rp" + NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID")).format(this)

/** "Ahmad Subakti" -> "AS" */
fun String.inisial(): String =
    trim().split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }