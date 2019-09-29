package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.TickCount
import org.firstinspires.ftc.teamcode.math.TickCountPerAngle

interface OutputMotor {
    fun increaseTargetPosition(change: TickCount)
    fun setDirection(value: DcMotorSimple.Direction)
    fun setMode(value: DcMotor.RunMode)
    fun setPower(value: Double)
    fun setSpeed(value: AngularVelocity)
    fun setTargetPosition(value: TickCount)
    fun setZeroPowerBehavior(value: DcMotor.ZeroPowerBehavior)

    fun setGearing(value: Double)
    fun setMaxAchievableSpeedFraction(value: Double)
    fun setMaxSpeed(value: AngularVelocity)
    fun setTickCountPerAngle(value: TickCountPerAngle)

    fun resetDeviceConfigurationForOpMode()
    fun close()
}