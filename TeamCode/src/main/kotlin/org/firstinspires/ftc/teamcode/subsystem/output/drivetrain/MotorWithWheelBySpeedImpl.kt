package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.math.LengthPerAngle
import org.firstinspires.ftc.teamcode.math.Velocity
import org.firstinspires.ftc.teamcode.math.div
import org.firstinspires.ftc.teamcode.math.times

class MotorWithWheelBySpeedImpl private constructor(private val motor: Motor, public override val maxSpeed: Velocity, public val direction: DcMotorSimple.Direction, public val power: Double) : MotorWithWheelBySpeed {
    private constructor(motor: Motor, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction)
            : this(motor, distancePerAngle * motor.getMaxSpeed(), direction, 1.0)

    private constructor(motor: Motor, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction, power: Double, unused: Unit)
            : this(motor, distancePerAngle * motor.getMaxSpeed(), direction, power)

    public constructor(hardwareMap: HardwareMap, deviceName: String, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction, power: Double)
            : this(MotorImpl(hardwareMap, deviceName), distancePerAngle, direction, power, Unit)

    public constructor(hardwareMap: HardwareMap, deviceName: String, distancePerAngle: LengthPerAngle, direction: DcMotorSimple.Direction)
            : this(MotorImpl(hardwareMap, deviceName), distancePerAngle, direction)

    public constructor(hardwareMap: HardwareMap, deviceName: String, distancePerAngle: LengthPerAngle)
            : this(MotorImpl(hardwareMap, deviceName), distancePerAngle, DcMotorSimple.Direction.FORWARD)

    init {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        motor.setDirection(direction)
        motor.setPower(power)
    }

    override fun setSpeed(speed: Velocity) {
        motor.setPower(speed / maxSpeed)
    }
}
