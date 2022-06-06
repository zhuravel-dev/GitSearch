package com.example.gitsearch.ui.extensions

import android.os.Build
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseData(notParsed: String): String = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val parsedDate = LocalDateTime.parse(notParsed, DateTimeFormatter.ISO_DATE_TIME)
        parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'")
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val output = formatter.format(parser.parse(notParsed))
        output.toString()
    }
} catch (e: Throwable) {
    Timber.e(e.localizedMessage.orEmpty())
    ""
}