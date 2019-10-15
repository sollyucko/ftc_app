package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.geometry.*

class WheelWithPosition(val position: LengthVector2D, val heading: LengthPerAngleVector2D) {
    val vecRot: LengthPerAngleLengthPerAngleDoubleVector3D = LengthPerAngleLengthPerAngleDoubleVector3D(heading, (heading cross position.directionVector) / LengthPerAngle.fromRadius(position.magnitude))
    fun withCenterShiftedBy(shift: LengthVector2D) =
        WheelWithPosition(position + shift, heading)
}
