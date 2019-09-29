package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import org.firstinspires.ftc.teamcode.math.geometry.LengthVector2D
import org.firstinspires.ftc.teamcode.math.Angle
import org.firstinspires.ftc.teamcode.math.Length
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.DrivetrainByPosition

interface OmniDrivetrainByPosition : DrivetrainByPosition {
    fun move(vec: LengthVector2D, rot: Angle)
    override fun startMoving(fwd: Length, rot: Angle) {
        this.move(LengthVector2D(Length.ZERO, fwd), rot)
    }
}
