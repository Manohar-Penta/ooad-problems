# Valid by Construction

## The core idea

> An object should never exist in a state where it can't do its job.

If an object needs a collaborator or a value to work, require it in the **constructor** and make the
field `final`. Then the failure moves from "crashes at runtime, maybe" to "doesn't compile".

## How to apply

- Mandatory dependency → constructor parameter, `final` field, `Objects.requireNonNull`.
- Truly optional or runtime-swappable → setter, used deliberately.
- Go through every non-`final` field and ask: *can this legitimately change after construction?*
  If not, make it `final`.
- Required domain data (e.g. a user's unique email) belongs in the constructor too, not a setter.

Full treatment, with the `parking_lot` refactor as a worked example:
[Dependency Injection](../design_patterns/dependency_injection.md).

## Seen in this repo

`stackover_flow`: `Answer` received its `ReputationService` through a setter nobody called, so every
answer vote threw a `NullPointerException`.
