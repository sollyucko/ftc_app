package org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.omnidirectional

import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.LengthPerAngle
import org.firstinspires.ftc.teamcode.math.Velocity
import org.firstinspires.ftc.teamcode.math.div
import org.firstinspires.ftc.teamcode.math.geometry.*
import org.firstinspires.ftc.teamcode.merge
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.OutputMotor
import org.firstinspires.ftc.teamcode.subsystem.output.drivetrain.WheelWithPosition

class GeneralDrivetrainBySpeed(val motorsAndWheels: Map<OutputMotor, Set<WheelWithPosition>>) : OmniDrivetrainBySpeed {
    val motorVectors = motorsAndWheels.mapValues { it.value.map { w -> w.heading }.reduce { a, b -> a + b } }
    val motorAngleRatios = motorsAndWheels.mapValues { it.value.map { w -> (w.heading cross w.position.direction.vector) / LengthPerAngle.fromRadius(w.position.magnitude) }.reduce { a, b -> a + b } }

    override fun setSpeed(vec: VelocityVector2D, rot: AngularVelocity) {
        for(entry in getTargetSpeeds(vec, rot)) {
            entry.key.setSpeed(entry.value)
        }
    }

    /**
     * NaN is indeterminate, infinite is undefined.
     * If the situation is impossible or always works, return an empty map.
     */
    private fun getTargetSpeeds(vec: VelocityVector2D, rot: AngularVelocity): Map<OutputMotor, AngularVelocity> =
            when(motorsAndWheels.size) {
                0 -> mapOf()
                1 -> {
                    val result = merge(rot / motorAngleRatios.values.single(), vec / motorVectors.values.single())
                    if(result.isFinite()) mapOf(Pair(motorsAndWheels.keys.single(), result)) else mapOf()
                }
                2 -> {
                    val m = motorsAndWheels.keys.map { it }
                    val v = m.map { motorVectors.getValue(it) }
                    val denom = v[0] cross v[1]
                    mapOf(Pair(m[0], (vec cross v[1]) / denom), Pair(m[1], (v[0] cross vec) / denom))
                    TODO()
                }
                3 -> {
                    val motorList = motorsAndWheels.keys.map { it }
                    /** It's unitless to make it easier to deal with. */
                    val lhs = motorList.map {
                        DoubleVector3D(
                                x = motorVectors.getValue(it).x._value,
                                y = motorVectors.getValue(it).y._value,
                                z = motorAngleRatios.getValue(it)
                        )
                    }
                    val denom = lhs[0] cross lhs[1] dot lhs[2]
                    val rhs = DoubleVector3D(vec.x._value, vec.y._value, rot._value)
                    motorList.withIndex().associateBy(IndexedValue<OutputMotor>::value) { (i, _) ->
                        val l = lhs.mapIndexed { j, v ->
                            if(i == j) rhs else v
                        }
                        AngularVelocity((l[0] cross l[1] dot l[2]) / denom)
                    }
                }
                else -> TODO()
            }

    override fun getMaxSpeedInDirection(direction: Direction2D): Velocity {
        TODO("This is a linear programming problem.")
    }
}
