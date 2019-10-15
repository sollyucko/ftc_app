package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional

import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.geometry.*

interface DrivetrainBySpeed {
    fun setSpeed(fwd: Velocity, rot: AngularVelocity): Unit = setSpeed(VelocityAngularVelocityVector2D(fwd, rot))
    fun setSpeed(vec: VelocityAngularVelocityVector2D): Unit = setSpeed(vec.x, vec.y)

    val maxFwd: Velocity
    val maxRot: AngularVelocity
    
    fun setRelSpeed(fwd: Double, rot: Double): Unit = setSpeed(fwd * maxFwd, rot * maxRot) 
    fun setRelSpeed(vec: DoubleVector2D): Unit = setRelSpeed(vec.x, vec.y)
}
