package ru.netology.kotlin

open class Phone(val number: String) {
    fun sendSms(to: String, message: String) {
        println("Send sms to $to: $message")
    }
    open fun call(to: String) {
        println("Call to $to")
    }
}
