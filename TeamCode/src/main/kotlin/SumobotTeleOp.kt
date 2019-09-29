import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.Length.Companion.METER
import org.firstinspires.ftc.teamcode.math.Prefix.CENTI
import org.firstinspires.ftc.teamcode.math.Velocity
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.unidirectional.TankDrivetrainBySpeed

@TeleOp(name = "SumobotTeleOp")
class SumobotTeleOp : OpMode() {
    private lateinit var drivetrain: TankDrivetrainBySpeed
    private var maxSpeedLinear: Velocity = Velocity.ZERO
    private var maxSpeedAngular: AngularVelocity = AngularVelocity.ZERO

    override fun init() {
        drivetrain = TankDrivetrainBySpeed(
                hardwareMap = hardwareMap,
                leftName = "left",
                leftDirection = DcMotorSimple.Direction.FORWARD,
                rightName = "right",
                rightDirection = DcMotorSimple.Direction.REVERSE,
                wheelDiameter = 9.0 * CENTI * METER,
                gearRatio = 1.0,
                robotDiameter = 19.0 * CENTI * METER
        )
        drivetrain.setSpeed(fwd = Velocity.ZERO, rot = AngularVelocity.ZERO)
        maxSpeedLinear = drivetrain.maxSpeed
        maxSpeedAngular = drivetrain.maxSpeed / drivetrain.distancePerAngle
    }

    override fun loop() {
        drivetrain.setSpeed(fwd = maxSpeedLinear * -gamepad1.left_stick_y.toDouble(),
                            rot = maxSpeedAngular * -gamepad1.left_stick_x.toDouble())
    }
}
