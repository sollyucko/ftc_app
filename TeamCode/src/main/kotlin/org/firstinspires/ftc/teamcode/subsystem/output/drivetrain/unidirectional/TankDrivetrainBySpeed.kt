package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelBySpeed
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelBySpeedGroup
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelBySpeedImpl

class TankDrivetrainBySpeed public constructor(public val distancePerAngle: LengthPerAngle, private val left: MotorWithWheelBySpeed, private val right: MotorWithWheelBySpeed, val reverseLeft: Boolean, val reverseRight: Boolean) : DrivetrainBySpeed {
    public constructor(hardwareMap: HardwareMap, leftName: String, leftDirection: DcMotorSimple.Direction, rightName: String, rightDirection: DcMotorSimple.Direction, wheelDiameter: Length, gearRatio: Double, robotDiameter: Length) : this(
            distancePerAngle = robotDiameter / 2.0 / Angle.RADIAN,
            left = MotorWithWheelBySpeedImpl(hardwareMap = hardwareMap, deviceName = leftName, distancePerAngle = wheelDiameter / 2.0 / Angle.RADIAN * gearRatio, direction = leftDirection),
            right = MotorWithWheelBySpeedImpl(hardwareMap = hardwareMap, deviceName = rightName, distancePerAngle = wheelDiameter / 2.0 / Angle.RADIAN * gearRatio, direction = rightDirection),
            reverseLeft = false,
            reverseRight = false
    )

    private val all = MotorWithWheelBySpeedGroup(left, right)

    override fun setSpeed(fwd: Velocity, rot: AngularVelocity) {
        left.setSpeed(fwd - rot * distancePerAngle)
        right.setSpeed(fwd + rot * distancePerAngle)
    }

    override val maxSpeed: Velocity by lazy { minOf(left.maxSpeed, right.maxSpeed) }
}
