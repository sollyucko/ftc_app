package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.math.Length
import org.firstinspires.ftc.teamcode.math.LengthPerAngle
import org.firstinspires.ftc.teamcode.math.LengthPerTickCount

class MotorWithWheelByPositionImpl private constructor(private val motor: Motor, private var distancePerTickCount: LengthPerTickCount, direction: DcMotorSimple.Direction, power: Double) : MotorWithWheelByPosition {
    private constructor(motor: Motor, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction)
            : this(motor, distancePerAngle / motor.getTickCountPerAngle(), direction, 1.0)
    
    public constructor(hardwareMap: HardwareMap, deviceName: String, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction)
            : this(MotorImpl(hardwareMap, deviceName), distancePerAngle, direction)
    
    init {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        motor.setDirection(direction)
        motor.setPower(power)
    }
    
    fun addGear(gearing: Double) {
        distancePerTickCount *= gearing
    }
    
    override fun startMoving(distance: Length) {
        motor.increaseTargetPosition(distance / distancePerTickCount)
    }

    override fun isMoving() = motor.isBusy()

    override fun stop() {
        motor.setTargetPosition(motor.getCurrentPosition())
    }
}
