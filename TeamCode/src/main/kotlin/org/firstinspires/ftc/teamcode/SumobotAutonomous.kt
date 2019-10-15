package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.math.Angle.Companion.DEGREE
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.Length.Companion.METER
import org.firstinspires.ftc.teamcode.math.Prefix.CENTI
import org.firstinspires.ftc.teamcode.math.Prefix.MILLI
import org.firstinspires.ftc.teamcode.math.Velocity
import org.firstinspires.ftc.teamcode.math.times
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.TankDrivetrainByPosition

@Autonomous(name = "SumobotAutonomous")
class SumobotAutonomous : OpMode() {
    private lateinit var drivetrain: TankDrivetrainByPosition
    private var maxSpeedLinear: Velocity = Velocity.ZERO
    private var maxSpeedAngular: AngularVelocity = AngularVelocity.ZERO

    override fun init() {
        drivetrain = TankDrivetrainByPosition(
                hardwareMap = hardwareMap,
                leftName = "left",
                leftDirection = DcMotorSimple.Direction.FORWARD,
                rightName = "right",
                rightDirection = DcMotorSimple.Direction.REVERSE,
                wheelDiameter = 9.0 * CENTI * METER,
                gearRatio = 1.0,
                robotDiameter = 19.0 * CENTI * METER
        )
        drivetrain.stop()
    }

    override fun loop() {
        drivetrain.startMoving(MILLI * METER, DEGREE)
    }
}
