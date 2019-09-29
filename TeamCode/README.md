# Style guide

The `.idea/codeStyles/Project.xml` and `.idea/inspectionProfiles
/Project_Default.xml` files for IntelliJ and Android Studio should
 implement the rules listed here.

## Properties and methods of `this`

`this.` should always be explicit, even if it is redundant, for clarity
 and consistency.
 
## Indentation

Four spaces should be used for indentation.

## Brace usage & placement

Braces should always be used to delemit blocks (after control flow
statements such as `if`, `while`, and `for`) except in the following
cases:

- `if(shortCondition) break;`
- `if(shortCondition) continue;`
- `if(shortCondition) return;`
- `if(shortCondition) return shortValue;`

For empty blocks, both braces should go on the same line. For example: `if
(shortCondition) {}`.

## Spacing

No space should be used after a control flow statement. For example:
`if(condition) return;`.
