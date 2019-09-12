package ru.netology.ncraftmedia.receiver

class Intent() {
    var action: String = ""

    fun putExtra(name: String, value: Any) { }
}

// ------------------------------------------------------------------------
fun Intent.first(action: (Intent) -> Unit): Intent {
    action(this) // внутри лямбды this будет доступен как it
    return this
}
// ------------------------------------------------------------------------
fun Intent.second(action: Intent.() -> Unit): Intent {
    action() // внутри лямбды this будет доступен как this
    return this
}
// ------------------------------------------------------------------------

fun main() {
    run {
        val intent = Intent().first {
            it.action = "ACTION_SEND"
            it.putExtra("EXTRA_TEXT", "...")
        }
    }

    run {
        val intent = Intent().second {
            action = "ACTION_SEND" // аналог this.action
            putExtra("EXTRA_TEXT", "...") // аналог this.putExtra
        }
    }

}