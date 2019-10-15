@file:Suppress("NOTHING_TO_INLINE")

package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.math.Angle
import org.firstinspires.ftc.teamcode.math.AngularVelocity
import org.firstinspires.ftc.teamcode.math.Length
import kotlin.reflect.KProperty

inline fun merge() = Double.NaN

inline fun merge(a: Double) = a

fun merge(a: Double, b: Double) = when {
    a.isInfinite() || b.isInfinite() -> Double.POSITIVE_INFINITY
    a.isNaN() -> b
    b.isNaN() || a == b -> a
    else -> Double.POSITIVE_INFINITY
}

fun merge(vararg values: Double): Double {
    var first: Double? = null
    for(value in values) {
        when {
            value.isInfinite() -> return Double.POSITIVE_INFINITY
            value.isNaN() -> when {
                first == null -> first = value
                first != value -> return Double.POSITIVE_INFINITY
            }
        }
    }
    return first ?: Double.NaN
}

class LazyAssignable<T : Any>(val f: () -> T) {
    private var _value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(_value == null) _value = f()
        return _value!!
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        _value = value
    }
}
