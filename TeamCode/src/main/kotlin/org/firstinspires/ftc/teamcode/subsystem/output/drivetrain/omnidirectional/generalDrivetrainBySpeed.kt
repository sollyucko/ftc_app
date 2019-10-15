package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import kotlin.math.*
import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.geometry.*
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.Motor
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.WheelWithPosition
import no.uib.cipr.matrix.DenseVector
import no.uib.cipr.matrix.DenseMatrix
import lpsolve.*

fun generalDrivetrainBySpeed(motorsAndWheels: Map<Motor, Set<WheelWithPosition>>): OmniDrivetrainBySpeed {
    val motorVecRots = motorsAndWheels.mapValues { (motor, wheels) -> wheels.map { w -> w.vecRot }.reduce { a, b -> a + b } * motor.getMaxSpeed() }
    return when(motorVecRots.size) { in 0..2 -> Lt3(motorVecRots); 3 -> Eq3(motorVecRots); in 4..Int.MAX_VALUE -> Gt3(motorVecRots); else -> throw RuntimeException()  }
}
//    = generalDrivetrainBySpeed(motorsAndWheels.mapValues { (motor, wheels) -> wheels.map { w -> w.vecRot }.reduce { a, b -> a + b } * motor.getMaxSpeed() })
//fun generalDrivetrainBySpeed(motorVecRots: Map<Motor, VelocityVelocityAngularVelocityVector3D>): OmniDrivetrainBySpeed
//    = when(motorVecRots.size) { in 0..2 -> Lt3(motorVecRots); 3 -> Eq3(motorVecRots); in 4..Int.MAX_VALUE -> Gt3(motorVecRots); else -> throw RuntimeException()  }

private open class _GeneralDrivetrainBySpeed public constructor(protected val motorVecRots: Map<Motor, VelocityVelocityAngularVelocityVector3D>) : OmniDrivetrainBySpeed {
    public override val maxSid = motorVecRots.values.map { abs(it.x) }.sum()
    public override val maxFwd = motorVecRots.values.map { abs(it.y) }.sum()
    public override val maxRot = motorVecRots.values.map { abs(it.z) }.sum()
}

private class Lt3(motorVecRots: Map<Motor, VelocityVelocityAngularVelocityVector3D>) : _GeneralDrivetrainBySpeed(motorVecRots) {
    // Pseudoinverse

    val matrix = DenseMatrix(motorVecRots.values.map { doubleArrayOf(it.x._value, it.y._value, it.z._value) }.toTypedArray<DoubleArray>())

    override fun setSpeed(vecRot: VelocityVelocityAngularVelocityVector3D) {      
        for((s, m) in (matrix.transSolve(DenseVector(doubleArrayOf(vecRot.x._value, vecRot.y._value, vecRot.z._value)), DenseVector(motorVecRots.size)) as DenseVector).data.zip(motorVecRots.keys.toTypedArray<Motor>())) {
            m.setSpeed(AngularVelocity(s))
        }
    }
}

private class Eq3(motorVecRots: Map<Motor, VelocityVelocityAngularVelocityVector3D>) : _GeneralDrivetrainBySpeed(motorVecRots) {
    // Cramer's rule

    // val ((motor0, vecRot0), (motor1, vecRot1), (motor2, vecRot2)) = motorVecRots.entries
    val _mvr = motorVecRots.entries.toTypedArray<Map.Entry<Motor, VelocityVelocityAngularVelocityVector3D>>()
    val motor0: Motor = _mvr.component1().component1()
    val vecRot0: VelocityVelocityAngularVelocityVector3D = _mvr.component1().component2()
    val motor1: Motor = _mvr.component2().component1()
    val vecRot1: VelocityVelocityAngularVelocityVector3D = _mvr.component2().component2()
    val motor2: Motor = _mvr.component3().component1()
    val vecRot2: VelocityVelocityAngularVelocityVector3D = _mvr.component3().component2()

    val denom = vecRot0 cross vecRot1 dot vecRot2

    override fun setSpeed(vecRot: VelocityVelocityAngularVelocityVector3D) {
        motor0.setPower((vecRot cross vecRot1 dot vecRot2) / denom)
        motor1.setPower((vecRot0 cross vecRot dot vecRot2) / denom)
        motor2.setPower((vecRot0 cross vecRot1 dot vecRot) / denom)
    }}

private class Gt3(motorVecRots: Map<Motor, VelocityVelocityAngularVelocityVector3D>) : _GeneralDrivetrainBySpeed(motorVecRots) {
    // Linear programming

    val modelTemplate = LpSolve.makeLp(2 * motorVecRots.size + 3, motorVecRots.size)

    init {
        modelTemplate.setMinim()
        modelTemplate.setObjFn(DoubleArray(motorVecRots.size) { 1.0 })
        for(entry in motorVecRots) {
            // absolute value -> need to handle positive & negative cases
            modelTemplate.addConstraint(motorVecRots.entries.map { if(it == entry) 1.0 else 0.0 }.toDoubleArray(), LpSolve.LE, 1.0)
            modelTemplate.addConstraint(motorVecRots.entries.map { if(it == entry) -1.0 else 0.0 }.toDoubleArray(), LpSolve.LE, 1.0)
        }
    }

    override fun setSpeed(vecRot: VelocityVelocityAngularVelocityVector3D) {
        val modelCopy = modelTemplate.copyLp()

        modelCopy.addConstraint(motorVecRots.entries.map { it.value.x._value }.toDoubleArray(), LpSolve.EQ, vecRot.x._value)
        modelCopy.addConstraint(motorVecRots.entries.map { it.value.y._value }.toDoubleArray(), LpSolve.EQ, vecRot.y._value)
        modelCopy.addConstraint(motorVecRots.entries.map { it.value.z._value }.toDoubleArray(), LpSolve.EQ, vecRot.z._value)

        for((s, m) in modelCopy.getPtrVariables().zip(motorVecRots.keys)) {
            m.setPower(s)
        }
    }
}

