package com.example.gitsearch.ui.compose

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(notParsed: String): String = try {
    val parsedDate = LocalDateTime.parse(notParsed, DateTimeFormatter.ISO_DATE_TIME)
    parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
} catch (e: Throwable) { "" }