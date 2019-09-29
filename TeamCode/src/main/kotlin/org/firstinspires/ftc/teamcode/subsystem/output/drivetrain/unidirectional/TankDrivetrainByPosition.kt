package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.math.Angle
import org.firstinspires.ftc.teamcode.math.Angle.Companion.RADIAN
import org.firstinspires.ftc.teamcode.math.Length
import org.firstinspires.ftc.teamcode.math.LengthPerAngle
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelByPosition
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelByPositionGroup
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.MotorWithWheelByPositionImpl

class TankDrivetrainByPosition public constructor(private val distancePerAngle: LengthPerAngle, private val left: MotorWithWheelByPosition, private val right: MotorWithWheelByPosition) : DrivetrainByPosition {
    public constructor(hardwareMap: HardwareMap, leftName: String, leftDirection: DcMotorSimple.Direction, rightName: String, rightDirection: DcMotorSimple.Direction, wheelDiameter: Length, gearRatio: Double, robotDiameter: Length) : this(
            distancePerAngle = robotDiameter / 2.0 / RADIAN,
            left = MotorWithWheelByPositionImpl(hardwareMap = hardwareMap, deviceName = leftName, distancePerAngle = wheelDiameter / 2.0 / RADIAN * gearRatio, direction = leftDirection),
            right = MotorWithWheelByPositionImpl(hardwareMap = hardwareMap, deviceName = rightName, distancePerAngle = wheelDiameter / 2.0 / RADIAN * gearRatio, direction = rightDirection)
    )

    private val all = MotorWithWheelByPositionGroup(left, right)

    override fun isMoving(): Boolean {
        return left.isMoving() && right.isMoving()
    }

    override fun startMoving(fwd: Length, rot: Angle) {
        all.startMoving(fwd)
        val lenRot = rot * distancePerAngle
        left.startMoving(-lenRot)
        right.startMoving(lenRot)
    }

    override fun stop() {
        left.stop()
        right.stop()
    }
}
