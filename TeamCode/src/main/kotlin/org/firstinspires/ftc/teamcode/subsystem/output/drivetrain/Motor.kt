package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import org.firstinspires.ftc.teamcode.math.TickCount
import org.firstinspires.ftc.teamcode.math.plus

interface Motor : InputMotor, OutputMotor {
    override fun increaseTargetPosition(change: TickCount) {
        setTargetPosition(getTargetPosition() + change)
    }
}