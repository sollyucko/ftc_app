@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")
@file:JvmName("_2dKtExt")
package org.firstinspires.ftc.teamcode.math.geometry

import org.firstinspires.ftc.teamcode.math.*
import org.firstinspires.ftc.teamcode.math.Angle.Companion.REVOLUTION

inline class Direction2D(public val _angle: Angle) {
    public val x get() = sin(_angle)
    public val y get() = cos(_angle)
    public val vec get() = DoubleVector2D(x, y)

    companion object {
        public val POS_X = Direction2D(REVOLUTION * 0.0 / 4.0)
        public val POS_Y = Direction2D(REVOLUTION * 1.0 / 4.0)
        public val NEG_X = Direction2D(REVOLUTION * 2.0 / 4.0)
        public val NEG_Y = Direction2D(REVOLUTION * 3.0 / 4.0)
    }
}


inline class Rotation2D(public val angle: Angle)


operator fun Rotation2D.unaryPlus() = Rotation2D(angle.normalized)
operator fun Rotation2D.unaryMinus() = Rotation2D((-angle).normalized)
operator fun Direction2D.plus(other: Rotation2D) = Direction2D((_angle + other.angle).normalized)
operator fun Rotation2D.plus(other: Direction2D) = Direction2D((angle + other._angle).normalized)
operator fun Rotation2D.plus(other: Rotation2D) = Direction2D((angle + other.angle).normalized)
operator fun Direction2D.minus(other: Rotation2D) = Direction2D((_angle - other.angle).normalized)
operator fun Rotation2D.times(other: Double) = Rotation2D((angle * other).normalized)
operator fun Double.times(other: Rotation2D) = Rotation2D((this * other.angle).normalized)
operator fun Rotation2D.div(other: Double) = Rotation2D((angle / other).normalized)
