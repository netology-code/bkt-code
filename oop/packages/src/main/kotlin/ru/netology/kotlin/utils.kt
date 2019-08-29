package ru.netology.kotlin

import kotlin.io.println as stdPrintln

fun isWorkingDay(day: String): Boolean = when (day) {
    "Пн", "Вт", "Ср", "Чт", "Пт" -> true
    "Сб", "Вс" -> false
    else -> false
}

fun println(message: String) {
    stdPrintln("APP: $message")
}
