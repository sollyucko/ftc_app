package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.Length

class MotorWithWheelByPositionGroup(private vararg val motors: MotorWithWheelByPosition) : MotorWithWheelByPosition {
    override fun stop() = motors.forEach { it.stop() }

    override fun startMoving(distance: Length) = motors.forEach { it.startMoving(distance) }

    override fun isMoving() = motors.any { it.isMoving() }
}
