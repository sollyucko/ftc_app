package org.firstinspires.ftc.teamcode

suspend fun delay() {}

suspend fun delayWhile(pred: () -> Boolean) {
    while(pred()) {
        delay()
    }
}
