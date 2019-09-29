package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional

import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.Velocity

interface DrivetrainBySpeed {
    fun setSpeed(fwd: Velocity, rot: AngularVelocity)
    val maxSpeed: Velocity
}
