package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.geometry.LengthVector2D
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelByPosition

class MecanumDrivetrainByPosition(private val distancePerAngleRot: LengthPerAngle, private val rf: MotorWithWheelByPosition, private val rb: MotorWithWheelByPosition, private val lf: MotorWithWheelByPosition, private val lb: MotorWithWheelByPosition) : OmniDrivetrainByPosition {
    override fun isMoving() = rf.isMoving() || rb.isMoving() || lf.isMoving() || lb.isMoving()

    override fun move(vec: LengthVector2D, rot: Angle) {
        val lenRot = rot * distancePerAngleRot
        rf.startMoving(vec.y - vec.x + lenRot)
        rb.startMoving(vec.y + vec.x + lenRot)
        lf.startMoving(vec.y - vec.x - lenRot)
        lb.startMoving(vec.y + vec.x - lenRot)
    }

    override fun stop() {
        rf.stop()
        rb.stop()
        lf.stop()
        lb.stop()
    }
}