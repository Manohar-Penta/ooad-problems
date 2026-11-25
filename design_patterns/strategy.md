# Strategy Pattern

## What it is

A behavioural pattern that puts each variant of an algorithm behind a common interface, so the
caller can swap variants without changing its own code.

```java
public interface IParkingStrategy {
    Optional<Spot> findSpot(List<Floor> floors, VehicleType type);
}
```

## When to use

- Several interchangeable ways to do the same job (pricing, spot finding, search).
- The choice is made by configuration or at runtime, not by subclassing the caller.
- A growing `if/else` or `switch` on "which kind" inside one method.

## Designing the interface

Write the call site first ([design by usage](../design_principles/design_by_usage.md)). The interface
must receive everything a variant needs to do its job: the query and the data to search over, or the
ticket to price. A strategy method with no parameters usually can't do anything useful.

Decide **who chooses** the strategy:
- Injected once at construction (`parking_lot`: chosen at the composition root, see
  [Dependency Injection](dependency_injection.md)).
- Picked per call by the facade (e.g. one strategy per search type).

## When to avoid

- Only one variant exists and no second one is planned: a plain method is simpler.
- The algorithm is fixed and only one step varies: [Template Method](template_method.md) fits better.

## In this repo

- `parking_lot`: `IParkingStrategy`, `IFeeStrategy` with implementations under `strategies/`.
- `stackover_flow`: `SearchStrategy` (tag / keyword / user search).
