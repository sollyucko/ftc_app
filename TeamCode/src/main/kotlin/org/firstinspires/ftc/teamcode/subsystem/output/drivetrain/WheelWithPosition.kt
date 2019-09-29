package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.LengthPerAngle
import org.firstinspires.ftc.teamcode.math.geometry.Direction2D
import org.firstinspires.ftc.teamcode.math.geometry.LengthPerAngleVector2D
import org.firstinspires.ftc.teamcode.math.geometry.LengthVector2D

interface WheelWithPosition {
    val position: LengthVector2D
    val heading: LengthPerAngleVector2D
}
