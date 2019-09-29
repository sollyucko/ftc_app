package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.Length

interface MotorWithWheelByPosition {
    fun startMoving(distance: Length)
    fun isMoving(): Boolean
    fun stop()
    suspend fun delayWhileMoving() {
        org.firstinspires.ftc.teamcode.delayWhile(::isMoving)
    }
    suspend fun move(distance: Length) {
        startMoving(distance)
        delayWhileMoving()
    }
}
