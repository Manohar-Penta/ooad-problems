# Primitive Obsession

## The core idea

> When different concepts share one primitive type, the compiler can't tell them apart, so mixing
> them up is easy and silent.

A user id, a question id and an answer id are all `String`. Passing one where another is expected
compiles fine and fails at runtime, or worse, quietly returns the wrong result.

## How to apply (with judgment)

- Prefer passing the domain object (`User`) over its id inside the model.
- At the facade, ids are fine, but keep parameter lists short so order mistakes are unlikely.
- Wrap a primitive in a small value type (`record UserId(String value)`) when it carries rules
  (format, uniqueness) or is mixed up often. For interview LLD this is optional; knowing the trade-off
  is what matters.

## Smell

> The more `String`/`int` parameters a method has, the easier it is to pass them in the wrong order.

## Seen in this repo

`stackover_flow`: `Question.acceptAnswer` compared the accepter's **user** id against the
**question's** id, so the owner could never accept an answer.
