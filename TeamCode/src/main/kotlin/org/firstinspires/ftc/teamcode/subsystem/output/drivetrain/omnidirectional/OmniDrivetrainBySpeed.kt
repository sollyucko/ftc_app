package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import org.firstinspires.ftc.teamcode.math.geometry.LengthVector2D
import org.firstinspires.ftc.teamcode.math.Angle
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.Length
import org.firstinspires.ftc.teamcode.math.Velocity
import org.firstinspires.ftc.teamcode.math.geometry.Direction2D
import org.firstinspires.ftc.teamcode.math.geometry.VelocityVector2D
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.DrivetrainByPosition
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.DrivetrainBySpeed

interface OmniDrivetrainBySpeed : DrivetrainBySpeed {
    fun setSpeed(vec: VelocityVector2D, rot: AngularVelocity)
    override fun setSpeed(fwd: Velocity, rot: AngularVelocity) {
        this.setSpeed(VelocityVector2D(Velocity.ZERO, fwd), rot)
    }

    fun getMaxSpeedInDirection(direction: Direction2D): Velocity
    override val maxSpeed: Velocity
        get() = getMaxSpeedInDirection(Direction2D.POS_Y)
}
