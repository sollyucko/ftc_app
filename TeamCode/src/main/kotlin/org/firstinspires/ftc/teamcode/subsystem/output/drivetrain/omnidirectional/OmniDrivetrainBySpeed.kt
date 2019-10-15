package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.geometry.*
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.DrivetrainBySpeed

interface OmniDrivetrainBySpeed : DrivetrainBySpeed {
    fun setSpeed(vecRot: VelocityVelocityAngularVelocityVector3D): Unit = setSpeed(vecRot.x, vecRot.y, vecRot.z)
    fun setSpeed(vec: VelocityVector2D, rot: AngularVelocity): Unit = setSpeed(VelocityVelocityAngularVelocityVector3D(vec, rot))
    fun setSpeed(side: Velocity, fwd: Velocity, rot: AngularVelocity): Unit = setSpeed(VelocityVector2D(side, fwd), rot)
    override fun setSpeed(fwd: Velocity, rot: AngularVelocity) = setSpeed(Velocity.ZERO, fwd, rot)

    fun setRelSpeed(vecRot: DoubleVector3D): Unit = setRelSpeed(vecRot.x, vecRot.y, vecRot.z)
    fun setRelSpeed(vec: DoubleVector2D, rot: Double): Unit = setRelSpeed(DoubleVector3D(vec, rot))
    fun setRelSpeed(sid: Double, fwd: Double, rot: Double) = setSpeed(maxSid * sid, maxFwd * fwd, maxRot * rot)
    override fun setRelSpeed(fwd: Double, rot: Double) = setRelSpeed(0.0, fwd, rot)

    public val maxSid: Velocity
    public override val maxFwd: Velocity
    public override val maxRot: AngularVelocity
}
