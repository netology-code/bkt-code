fun userLevelToDiscount(level: String = "normal") = when (level) {
    "gold" -> 0.1
    "silver" -> 0.05
    else -> 0.0
}

fun calculatePrice(
    itemCount: Int,
    itemPrice: Int = 100,
    discount: Int = 100,
    discountStart: Int = 1_000,
    userLevel: String = "normal"
): Int {
    val total = itemPrice * itemCount
    val discounted = if (total > discountStart) total - discount else total
    return (discounted - discounted * userLevelToDiscount(userLevel)).toInt()
}

fun main() {
    println("Total Price: ${calculatePrice(11, userLevel = "gold")}")
    // или
    println("Total Price: ${calculatePrice(userLevel = "gold", itemCount = 11)}")
}