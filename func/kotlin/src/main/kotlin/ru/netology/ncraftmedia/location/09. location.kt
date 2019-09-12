package ru.netology.ncraftmedia.location

class Location(val lat: Double, val lng: Double) {
    operator fun plus(other: Location) = Location(
        lat + other.lat, lng + other.lng
    )
    operator fun component1() = lat
    operator fun component2() = lng
}

infix fun Double.x(that: Double) = Location(this, that)
infix fun Double.x(that: Int) = Location(this, 0.toDouble())

fun main() {
    run {
        val location = 55.75.x(37.61)
        println(location.lat)
    }

    run {
        val location = 55.75 x 37.61
        println(location.lat)
    }

    run {
        val origin = 55.75 x 37.61
        val target = 1.0 x 1.0

        val (lat, lng) = origin + target
        println(lat)
        println(lng)
    }

    run {
        val overloaded = 1.0 x 0
    }
}