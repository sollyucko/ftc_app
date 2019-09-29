package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.TickCount
import org.firstinspires.ftc.teamcode.math.TickCountPerAngle

interface InputMotor {
    fun getCurrentPosition(): TickCount
    fun getDirection(): DcMotorSimple.Direction
    fun getMode(): DcMotor.RunMode
    fun getPower(): Double
    fun getTargetPosition(): TickCount
    fun getZeroPowerBehavior(): DcMotor.ZeroPowerBehavior

    fun getGearing(): Double
    fun getMaxAchievableSpeedFraction(): Double
    fun getMaxSpeed(): AngularVelocity
    fun getTickCountPerAngle(): TickCountPerAngle
    fun isBusy(): Boolean
    suspend fun delayWhileBusy() {
        org.firstinspires.ftc.teamcode.delayWhile(::isBusy)
    }

    fun getSpeed(): AngularVelocity
}
