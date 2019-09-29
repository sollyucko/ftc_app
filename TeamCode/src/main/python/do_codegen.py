import json
import sys
from collections import defaultdict
from itertools import product
from typing import Any, Callable, Collection, Mapping, TypeVar, Union, Tuple

from mypy_extensions import TypedDict

K = TypeVar('K')
V = TypeVar('V')


def get_or_call(mapping: Mapping[K, V], key: K, default: Callable[[], V]):
    try:
        return mapping[key]
    except KeyError:
        return default()


def multiply_components(components1: Mapping[str, int], components2: Mapping[str, int]):
    result = defaultdict(int)
    for key, value in components1.items():
        result[key] += value
    for key, value in components2.items():
        result[key] += value
    return result


def compare_components(components1: Mapping[str, int], components2: Mapping[str, int]):
    for key, value in components1.items():
        if components2[key] != value:
            return False
    for key, value in components2.items():
        if components1[key] != value:
            return False
    return True


class RawClassData(TypedDict):
    units: Mapping[str, Union[str, float]]
    components: Mapping[str, int]
    extra: str
    companion_extra: str
    __total__ = False


class RawData(TypedDict):
    units_package: str
    geometry_package: str
    prefixes: Mapping[str, float]
    classes: Mapping[str, RawClassData]
    __total__ = False


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


def main():
    input_filepath: str = sys.argv[1]
    gen_root: str = sys.argv[2]

    with open(input_filepath) as input_file:
        data: RawData = json.load(input_file)

    classes: Collection[ClassData] = [
        ClassData(name, class_data)
        for name, class_data
        in data['classes'].items()
    ]
    class_products: Mapping[Tuple[str, str], str] = {
        (class1.name, class2.name):
            class3.name
        for class1, class2, class3
        in product(classes, classes, classes)
        if compare_components(multiply_components(class1.components, class2.components), class3.components)
    }
    units_package = data['units_package']
    # reveal_type(units_package)
    units_filepath = f'{gen_root}/{units_package.replace(".", "/")}/units.kt'
    geometry_package: str = data['geometry_package']
    geometry_2d_filepath = f'{gen_root}/{geometry_package.replace(".", "/")}/2d.kt'
    geometry_3d_filepath = f'{gen_root}/{geometry_package.replace(".", "/")}/3d.kt'


if __name__ == '__main__':
    main()
