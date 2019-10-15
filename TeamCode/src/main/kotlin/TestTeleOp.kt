import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import kotlin.math.sqrt
import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.Length.Companion.METER
import org.firstinspires.ftc.teamcode.math.Prefix.CENTI
import org.firstinspires.ftc.teamcode.math.geometry.*
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.*
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional.*

@TeleOp(name = "TestTeleOp")
class TestTeleOp : OpMode() {
    private lateinit var drivetrain: OmniDrivetrainBySpeed

    override fun init() {
        drivetrain = generalDrivetrainBySpeed(mapOf(
            Pair(MotorImpl(hardwareMap, "left_front"), setOf(WheelWithPosition(DoubleVector2D(sqrt(2.0)/2.0, sqrt(2.0)/2.0) * (TODO() as Length), DoubleVector2D(-1.0, 1.0) * LengthPerAngle.fromDiameter(TODO() as Length)))),
            Pair(MotorImpl(hardwareMap, "right_front"), setOf(WheelWithPosition(DoubleVector2D(sqrt(2.0)/2.0, sqrt(2.0)/2.0) * (TODO() as Length), DoubleVector2D(1.0, 1.0) * LengthPerAngle.fromDiameter(TODO() as Length)))),
            Pair(MotorImpl(hardwareMap, "left_back"), setOf(WheelWithPosition(DoubleVector2D(sqrt(2.0)/2.0, sqrt(2.0)/2.0) * (TODO() as Length), DoubleVector2D(1.0, -1.0) * LengthPerAngle.fromDiameter(TODO() as Length)))),
            Pair(MotorImpl(hardwareMap, "right_back"), setOf(WheelWithPosition(DoubleVector2D(sqrt(2.0)/2.0, sqrt(2.0)/2.0) * (TODO() as Length), DoubleVector2D(-1.0, -1.0) * LengthPerAngle.fromDiameter(TODO() as Length))))
        ))
        drivetrain.setSpeed(Velocity.ZERO, Velocity.ZERO, AngularVelocity.ZERO)
        /*
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
        */
    }

    override fun loop() {
        drivetrain.setRelSpeed(gamepad1.right_stick_x.toDouble(),
                               -gamepad1.right_stick_y.toDouble(),
                               gamepad1.left_stick_x.toDouble())
    }
}
