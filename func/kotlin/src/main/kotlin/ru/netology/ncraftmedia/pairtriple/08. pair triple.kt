package ru.netology.ncraftmedia.pairtriple

fun main() {
    run {
        val location = Pair(55.75, 37.61)
        val lat = location.first
        val lng = location.second
    }

    run {
        val location = 55.75 to 37.61
        val (lat, lng) = location
    }

    run {
        val location = Triple(55.75, 37.61, 300)
        val (_, _, accuracy) = location
        println(accuracy)
    }
}