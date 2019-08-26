fun main() {
    val userLevel = "normal"
    val levelDiscount = when (userLevel) {
        "gold" -> 0.1
        "silver" -> 0.05
        else -> 0.0
    }
    println(levelDiscount)
}