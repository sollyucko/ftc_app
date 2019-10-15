package org.firstinspires.ftc.teamcode.math.geometry

import kotlin.math.PI
import kotlin.math.hypot
import kotlin.math.sqrt

const val TAU = 2 * PI

fun mod(a: Double, b: Double) = ((a % b) + b) % b // If only "%" meant "modulus", not "remainder"...

fun hypot(x: Double) = x
fun hypot(x: Double, y: Double) = hypot(x, y)
fun hypot(x: Double, y: Double, z: Double) = sqrt(x * x + y * y + z * z)
fun hypot(vararg l: Double) = sqrt(l.map { it * it }.sum())

infix fun Int.isMultipleOf(other: Int) = this % other == 0
val Int.even get() = this isMultipleOf 2
val Int.odd get() = !even

infix fun Long.isMultipleOf(other: Long) = this % other == 0L
val Long.even get() = this isMultipleOf 2L
val Long.odd get() = !even
