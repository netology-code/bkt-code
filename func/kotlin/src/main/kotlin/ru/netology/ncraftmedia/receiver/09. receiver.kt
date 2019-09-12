package ru.netology.ncraftmedia.receiver


class Intent() {
    var action: String = ""

    fun putExtra(name: String, value: Any) { }
}

// ------------------------- Упрощённый пример ----------------------------
// тип block - extension-function для Intent
fun withReceiver(receiver: Intent, block: Intent.() -> Unit): Intent {
    receiver.block()
    return receiver
}

// ------------------------------------------------------------------------
fun Intent.first(block: (Intent) -> Unit): Intent {
    block(this) // внутри лямбды this будет доступен как it
    return this
}
// ------------------------------------------------------------------------
fun Intent.second(block: Intent.() -> Unit): Intent {
    // this.block() фактически
    block() // внутри лямбды this будет доступен как this
    return this
}
// ------------------------------------------------------------------------

fun main() {
    run {
        // мы не можем написать Intent().withReceiver, потому что withReceiver не extension-function для Intent (а вот лямбда в аргументах - да)
        val intent = withReceiver(Intent()) {
            action = "ACTION_SEND" // аналог this.action
            putExtra("EXTRA_TEXT", "...") // аналог this.putExtra
        }
    }

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