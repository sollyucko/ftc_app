package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional

import org.firstinspires.ftc.teamcode.math.Angle
import org.firstinspires.ftc.teamcode.math.Length

interface DrivetrainByPosition {
    fun startMoving(fwd: Length, rot: Angle)
    fun isMoving(): Boolean
    fun stop()
    suspend fun delayWhileMoving() {
        org.firstinspires.ftc.teamcode.delayWhile(::isMoving)
    }
    suspend fun move(fwd: Length, rot: Angle) {
        startMoving(fwd, rot)
        delayWhileMoving()
    }
}
