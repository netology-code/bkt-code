package ru.netology.kotlin

import kotlin.math.sqrt

class Smartphone(
    val manufacturer: String,
    val model: String,
    var locked: Boolean = true,
    voiceLevel: Int = 50, // это уже не property
    val height: Double,
    val width: Double
) {
    // а вот это property со своим get/set
    var voiceLevel: Int = voiceLevel
        get // указываем, что должен быть getter, который просто отдаёт значение
        set(value) {
            if (value >= 0 && value <= 100) {
                // field специальное имя для поля
                field = value
            }
        }
    val diagonal: Double = sqrt(height * height + width * width)
}
