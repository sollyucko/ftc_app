package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.Angle.Companion.REVOLUTION
import org.firstinspires.ftc.teamcode.math.TickCount.Companion.TICK
import org.firstinspires.ftc.teamcode.math.Duration.Companion.MINUTE
import kotlin.math.roundToInt

class MotorImpl(private val motor: DcMotor) : Motor {
    constructor(hardwareMap: HardwareMap, deviceName: String) : this(hardwareMap[DcMotor::class.java, deviceName])

    override fun getDirection(): DcMotorSimple.Direction = motor.direction
    override fun getPower(): Double = motor.power
    override fun getMode(): DcMotor.RunMode = motor.mode
    override fun getZeroPowerBehavior(): DcMotor.ZeroPowerBehavior = motor.zeroPowerBehavior
    override fun getCurrentPosition(): TickCount = motor.currentPosition.toDouble() * TICK
    override fun getTargetPosition(): TickCount = motor.targetPosition.toDouble() * TICK
    override fun getSpeed(): AngularVelocity = this.getPower() * this.getMaxSpeed()

    override fun getGearing() = motor.motorType.gearing
    override fun getMaxAchievableSpeedFraction() = motor.motorType.achieveableMaxRPMFraction
    override fun getMaxSpeed() = motor.motorType.maxRPM * REVOLUTION / MINUTE
    override fun getTickCountPerAngle() = motor.motorType.ticksPerRev * TICK / REVOLUTION
    override fun isBusy() = motor.isBusy

    //@formatter:off
    override fun setDirection(value: DcMotorSimple.Direction) { motor.direction = value }
    override fun setPower(value: Double) { motor.power = value }
    override fun setMode(value: DcMotor.RunMode) { motor.mode = value }
    override fun setZeroPowerBehavior(value: DcMotor.ZeroPowerBehavior) { motor.zeroPowerBehavior = value }
    override fun setTargetPosition(value: TickCount) { motor.targetPosition = (value / TICK).roundToInt() }
    override fun setSpeed(value: AngularVelocity) { this.setPower(value / this.getMaxSpeed()) }

    override fun setGearing(value: Double) { motor.motorType.gearing = value }
    override fun setMaxAchievableSpeedFraction(value: Double) { motor.motorType.achieveableMaxRPMFraction = value }
    override fun setMaxSpeed(value: AngularVelocity) { motor.motorType.maxRPM = value / (REVOLUTION / MINUTE) }
    override fun setTickCountPerAngle(value: TickCountPerAngle) { motor.motorType.ticksPerRev = value / (TICK / REVOLUTION) }
    //@formatter:on

    override fun resetDeviceConfigurationForOpMode() = motor.resetDeviceConfigurationForOpMode()

    override fun close() = motor.close()
}