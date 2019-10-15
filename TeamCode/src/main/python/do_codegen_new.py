import json
from collections import defaultdict
from itertools import product
from mypy_extensions import TypedDict
from sys import argv
from typing import Any, Callable, Mapping, MutableMapping, Tuple, TypeVar, Union

K = TypeVar('K')
V = TypeVar('V')


def get_or_call(mapping: Mapping[K, V], key: K, default: Callable[[], V]) -> V:
    try:
        return mapping[key]
    except KeyError:
        return default()


def multiply_components(components1: Mapping[str, int], components2: Mapping[str, int]) -> Mapping[str, int]:
    result: MutableMapping[str, int] = defaultdict(int)
    for key, value in components1.items():
        result[key] += value
    for key, value in components2.items():
        result[key] += value
    return result


def compare_components(components1: Mapping[str, int], components2: Mapping[str, int]) -> bool:
    for key, value in components1.items():
        if components2[key] != value:
            return False
    for key, value in components2.items():
        if components1[key] != value:
            return False
    return True


class RawClassData(TypedDict, total=False):
    units: Mapping[str, Union[str, float]]
    components: Mapping[str, int]
    extra: str
    companion_extra: str


class RawData(TypedDict, total=False):
    units_package: str
    geometry_package: str
    prefixes: MutableMapping[str, float]
    classes: MutableMapping[str, RawClassData]


class ClassData:
    name: str
    companion_extra: str
    components: Mapping[str, int]
    extra: str
    units: Mapping[str, str]

    def __init__(self, name: str, mapping: Mapping[str, Any]):
        self.name = name
        self.companion_extra = get_or_call(mapping, 'companion_extra', lambda: '')
        self.components = defaultdict(int, get_or_call(mapping, 'components', lambda: {name: 1}))
        self.extra = get_or_call(mapping, 'extra', lambda: '')
        self.units = get_or_call(mapping, 'units', lambda: {})


def main() -> None:
    print("Hi!")

    input_filepath: str = argv[1]
    gen_root: str = argv[2]

    with open(input_filepath) as input_file:
        data: RawData = json.load(input_file)
        data['classes']['Double'] = {'components': {}}

    classes: Mapping[str, ClassData] = {
        name: ClassData(name, class_data)
        for name, class_data
        in data['classes'].items()
    }
    products: Mapping[Tuple[str, str], str] = {
        (a, b):
            z
        for (a, cdata1), (b, cdata2), (z, cdata3)
        in product(classes.items(), classes.items(), classes.items())
        if compare_components(multiply_components(cdata1.components, cdata2.components), cdata3.components)
    }
    triplets: Sequence[Tuple[str, str, str]] = data['triplets'] + [[c, c, c] for c in classes]
    print(triplets)
    units_package = data['units_package']
    units_filepath = f'{gen_root}/{units_package.replace(".", "/")}/units.kt'
    geometry_package = data['geometry_package']
    geometry_2d_filepath = f'{gen_root}/{geometry_package.replace(".", "/")}/2d.kt'
    geometry_3d_filepath = f'{gen_root}/{geometry_package.replace(".", "/")}/3d.kt'
    with open(units_filepath, 'w') as units_file, \
            open(geometry_2d_filepath, 'w') as geometry_2d_file, \
            open(geometry_3d_filepath, 'w') as geometry_3d_file:
        units_file.write(f"""
// This file was automatically generated. Changes may be overwritten at any time.
// DO NOT EDIT THIS FILE. This file was generated from TeamCode/src/main/codegen.json by TeamCode/src/main/python/do_codegen.py

@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package {units_package}

import {units_package}.Angle.Companion.RADIAN
import {units_package}.Angle.Companion.REVOLUTION
import {geometry_package}.TAU
import {geometry_package}.hypot
import {geometry_package}.mod
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.math.abs

object Prefix {'{'}
    {';'.join(f'const val {pair[0]} = {pair[1]}' for pair in data['prefixes'].items())}
{'}'}

fun sin(angle: Angle) = sin(angle._value)
fun cos(angle: Angle) = cos(angle._value)

val Double.Companion.ZERO get() = 0.0
val Double._value get() = this
fun Double(_value: Double) = _value
""")
        geometry_2d_file.write(f"""
// This file was automatically generated. Changes may be overwritten at any time.
// DO NOT EDIT THIS FILE. This file was generated from TeamCode/src/main/codegen.json by TeamCode/src/main/python/do_codegen.py

@file:JvmMultifileClass

package {geometry_package}

import {units_package}.*
import {units_package}.Angle.Companion.REVOLUTION
""")
        geometry_3d_file.write(f"""
// This file was automatically generated. Changes may be overwritten at any time.
// DO NOT EDIT THIS FILE. This file was generated from TeamCode/src/main/codegen.json by TeamCode/src/main/python/do_codegen.py

@file:JvmMultifileClass

package {geometry_package}

import {units_package}.*
import {geometry_package}.*
""")

        for c, cdata in classes.items():
            if c != "Double":
                units_file.write(f"""
    inline class {c}(val _value: Double) : Comparable<{c}> {'{'}
        companion object {'{'}
            val ZERO = {c}(0.0)
            val POSITIVE_INFINITY = {c}(Double.POSITIVE_INFINITY)
            val NaN = {c}(Double.NaN)
            {';'.join(f'val {unit_name} = {f"{c}({unit_value})" if isinstance(unit_value, float) else unit_value}' for unit_name, unit_value in cdata.units.items())}
            {cdata.companion_extra}
        {'}'}

        override infix operator fun compareTo(other: {c}) = _value.compareTo(other._value)
        fun isInfinite() = _value.isInfinite()
        fun isFinite() = _value.isFinite()
        fun isNaN() = _value.isNaN()
        {cdata.extra}
    {'}'}
    operator fun {c}.unaryPlus() = this
    operator fun {c}.unaryMinus() = {c}(-_value)
    infix operator fun {c}.plus(other: {c}) = {c}(_value + other._value)
    infix operator fun {c}.minus(other: {c}) = {c}(_value - other._value)
    @JvmName("sum{c}s")
    fun List<{c}>.sum() = reduce {'{'} a, b -> a + b {'}'}
    fun hypot(x: {c}) = x
    fun hypot(x: {c}, y: {c}) = {c}(hypot(x._value, y._value))
    fun hypot(x: {c}, y: {c}, z: {c}) = {c}(hypot(x._value, y._value, z._value))
    fun abs(x: {c}) = {c}(abs(x._value))
""")
            units_file.write(f"""
    fun atan2(y: {c}, x: {c}) = kotlin.math.atan2(y._value, x._value) * RADIAN
    fun merge(a: {c}, b: {c}) = when {'{'}
        a.isInfinite() || b.isInfinite() -> {c}.POSITIVE_INFINITY
        a.isNaN() -> b
        b.isNaN() || a == b -> a
        else -> {c}.POSITIVE_INFINITY
    {'}'}
    fun merge(a: {c}, b: {c}, c: {c}) = when {'{'}
        a.isInfinite() || b.isInfinite() || c.isInfinite() -> {c}.POSITIVE_INFINITY
        a.isNaN() -> merge(b, c)
        b.isNaN() || a == b -> merge(a, c)
        else -> {c}.POSITIVE_INFINITY
    {'}'}
    """)
            geometry_2d_file.write(f"""
typealias {c}Vector2D = {c}{c}Vector2D
val {c}Vector2D.magnitude get() = hypot(x, y)
val {c}Vector2D.directionVector get() =
    DoubleVector2D(x / magnitude, y / magnitude)
""")
            geometry_3d_file.write(f"""
typealias {c}Vector3D = {c}{c}{c}Vector3D
val {c}Vector3D.magnitude get() = hypot(x, y, z)
val {c}Vector3D.directionVector get() =
    DoubleVector3D(x / magnitude, y / magnitude, z / magnitude)
""")

        for a, b in product(classes, classes):
            geometry_2d_file.write(f"""
class {a}{b}Vector2D public constructor(public val x: {a}, public val y: {b}) {'{'}
    public companion object {'{'}
        @JvmStatic
        public val ZERO = {a}{b}Vector2D({a}.ZERO, {b}.ZERO)
    {'}'}

    public operator fun component1() = x
    public operator fun component2() = y
{'}'}
public operator fun {a}{b}Vector2D.unaryPlus() = this
public operator fun {a}{b}Vector2D.unaryMinus() = {a}{b}Vector2D(-x, -y)
public infix operator fun {a}{b}Vector2D.plus(other: {a}{b}Vector2D) = {a}{b}Vector2D(x + other.x, y + other.y)
public infix operator fun {a}{b}Vector2D.minus(other: {a}{b}Vector2D) = {a}{b}Vector2D(x - other.x, y - other.y)
""")

        for a, b, c in product(classes, classes, classes):
            if [a, b, c] in triplets:
                geometry_3d_file.write(f"""
class {a}{b}{c}Vector3D public constructor(public val x: {a}, public val y: {b}, public val z: {c}) {'{'}
    public constructor(xy: {a}{b}Vector2D, z: {c}) : this(xy.x, xy.y, z)
    public constructor(x: {a}, yz: {b}{c}Vector2D) : this(x, yz.x, yz.y)

    public val xy = {a}{b}Vector2D(x, y)
    public val yz = {b}{c}Vector2D(y, z)

    public operator fun component1() = x
    public operator fun component2() = y

    companion object {'{'}
        @JvmStatic
        public val ZERO = {a}{b}{c}Vector3D({a}.ZERO, {b}.ZERO, {c}.ZERO)
    {'}'}
{'}'}
operator fun {a}{b}{c}Vector3D.unaryPlus() = this
operator fun {a}{b}{c}Vector3D.unaryMinus() = {a}{b}{c}Vector3D(-x, -y, -z)
infix operator fun {a}{b}{c}Vector3D.plus(other: {a}{b}{c}Vector3D) = {a}{b}{c}Vector3D(x + other.x, y + other.y, z + other.z)
infix operator fun {a}{b}{c}Vector3D.minus(other: {a}{b}{c}Vector3D) = {a}{b}{c}Vector3D(x - other.x, y - other.y, z - other.z)
""")

        for (c1, c2), c3 in products.items():
            # This works whether or not c1 == c2:
            #   If c1 == c2, this is executed once with (a, a).
            #    No conflict occurs.
            #   If c1 != c2, this is execute once with (a, b) and once with (b, a).
            #    Both permutations of each method are defined, one each time.
            units_file.write(f"""
infix operator fun {c1}.times(other: {c2}) = {c3}(this._value * other._value)
""")
            units_file.write(f"""
infix operator fun {c3}.div(other: {c1}) = {c2}(this._value / other._value)
""")

        for ((a1, a2), a), ((b1, b2), b) in product(products.items(), products.items()):
            if a1 == b1:
                geometry_2d_file.write(f"""
infix operator fun {a1}.times(other: {a2}{b2}Vector2D) = {a}{b}Vector2D(this * other.x, this * other.y)
infix operator fun {a}{b}Vector2D.div(other: {a1}) = {a2}{b2}Vector2D(this.x / other, this.y / other)
""")
            if a2 == b2:
                geometry_2d_file.write(f"""
infix operator fun {a1}{b1}Vector2D.times(other: {a2}) = {a}{b}Vector2D(x * other, y * other)
""")
            if a == b:
                geometry_2d_file.write(f"""
infix fun {a1}{b1}Vector2D.dot(other: {a2}{b2}Vector2D) = this.x * other.x + this.y * other.y
infix fun {a1}{b1}Vector2D.cross(other: {b2}{a2}Vector2D) = this.x * other.y - other.x * this.y
""")

        for ((a1, a2), a), ((b1, b2), b), ((c1, c2), c) in product(products.items(), products.items(),
                                                                   products.items()):
            if a1 == b1 == c1 and [a2, b2, c2] in triplets and [a, b, c] in triplets:
                geometry_3d_file.write(f"""
infix operator fun {a1}.times(other: {a2}{b2}{c2}Vector3D) = {a}{b}{c}Vector3D(this * other.x, this * other.y, this * other.z)
infix operator fun {a}{b}{c}Vector3D.div(other: {a1}) = {a2}{b2}{c2}Vector3D(this.x / other, this.y / other, this.z / other)
""")
            if a2 == b2 == c2 and [a1, b1, c1] in triplets and [a, b, c] in triplets:
                geometry_3d_file.write(f"""
infix operator fun {a1}{b1}{c1}Vector3D.times(other: {a2}) = {a}{b}{c}Vector3D(x * other, y * other, z * other)
""")
            if a == b == c and [a1, b1, c1] in triplets and [a2, b2, c2] in triplets:
                geometry_3d_file.write(f"""
infix fun {a1}{b1}{c1}Vector3D.dot(other: {a2}{b2}{c2}Vector3D) = x * other.x + y * other.y + z * other.z
""")
        print("Almost done")
        for ((a1, a2), a), ((b1, b2), b), ((c1, c2), c) in product(products.items(), products.items(), products.items()):
            # a1 b1 c1
            # c2 a2 b2
            # b  c  a
            if ((b1, c2), a) in products.items() and ((c1, a2), b) in products.items() and ((a1, b2), c) in products.items() and [a1, b1, c1] in triplets and [a1, b1, c1] in triplets and [c2, a2, b2] in triplets and [b, c, a] in triplets:
                geometry_3d_file.write(f"""
infix fun {a1}{b1}{c1}Vector3D.cross(other: {c2}{a2}{b2}Vector3D) = {b}{c}{a}Vector3D(y * other.z - z * other.y, x * other.z - z * other.x, x * other.y - y * other.x)
""")


if __name__ == '__main__':
    main()
