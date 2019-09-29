package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.Velocity

interface MotorWithWheelBySpeed {
    fun setSpeed(speed: Velocity)
    val maxSpeed: Velocity
}
