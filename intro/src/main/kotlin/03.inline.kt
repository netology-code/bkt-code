fun main() {
    val itemPrice = 100
    val itemCount = 11
    val discount = 100
    val discountStart = 1_000
    val totalPrice = itemPrice * itemCount
    val result = if (totalPrice > discountStart) totalPrice - discount else totalPrice
    println("Total Price: $result")
}