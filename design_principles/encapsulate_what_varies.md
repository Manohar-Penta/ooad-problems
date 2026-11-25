# Encapsulate What Varies

## The core idea

> Find the part of the design that changes between cases, isolate only that, and keep everything
> else shared.

Extracting a base class is the first half. The second half is asking **what actually differs**
between the subclasses. Often the algorithm is identical and only one step or one value differs.

## How to apply

1. Put the two similar implementations side by side and mark every line that differs.
2. If only a step or value differs and the algorithm is fixed → the base class owns the algorithm and
   asks subclasses for the varying part: [Template Method](../design_patterns/template_method.md).
3. If the whole algorithm should be swappable at runtime → [Strategy](../design_patterns/strategy.md).
4. If nothing differs, it doesn't need a pattern at all.

## Question to ask

> Could the subclasses know *less*? If a subclass only supplies a number, could that number live in
> a lookup or an enum instead?

## Seen in this repo

- `parking_lot`: spot finding and fee calculation vary by policy → `IParkingStrategy`, `IFeeStrategy`.
- `stackover_flow`: `Question.vote` and `Answer.vote` run the same algorithm; only the reputation
  weights differ.
