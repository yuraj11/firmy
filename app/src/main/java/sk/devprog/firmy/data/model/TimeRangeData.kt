package sk.devprog.firmy.data.model

import java.time.LocalDate

interface TimeRangeData {
    val validFrom: LocalDate
    val validTo: LocalDate?
}

fun <T: TimeRangeData> List<T>.sortByDate(): List<T> =
    sortedByDescending { it.validFrom }
