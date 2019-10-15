package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.*

class MotorWithWheelBySpeedGroup public constructor(private vararg val motors: MotorWithWheelBySpeed) : MotorWithWheelBySpeed {
    override fun setSpeed(speed: Velocity) {
        for(motor in motors) {
            motor.setSpeed(speed)
        }
    }

    override val maxSpeed: Velocity by lazy { motors.map(MotorWithWheelBySpeed::maxSpeed).min() ?: Velocity.ZERO }
}
