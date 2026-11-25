# Layer Responsibilities

## The core idea

> Every check and every rule should have exactly one home. Duplicated validation usually means the
> boundary between layers was never decided.

## A common split

| Layer | Responsible for | Example |
|-------|-----------------|---------|
| Facade (`StackOverFlow`, `ParkingLotSystem`) | Resolve ids to objects, check they exist, coordinate several objects | "Does this user exist?" |
| Entity (`Question`, `Spot`) | Enforce its own invariants | "Only the asker can accept" |
| Service | Rules that span several entities | Reputation, pricing |

For each check in the code, ask: *which row does this belong to? Does it appear anywhere else?*

## Decide the failure contract in the same pass

The caller must be able to tell **what** went wrong. Choose one convention per facade and use it
everywhere:

- Exceptions (`IllegalArgumentException`, a domain `NotFoundException`): clearest for rule violations.
- `Optional<T>`: fine for "not found" lookups (used in `parking_lot`).
- Avoid mixing `null`, `false` and `System.err.println`: the caller can't distinguish failures, and
  printing is a side effect, not a contract.

## Seen in this repo

`stackover_flow`: "does the answer exist" was checked both in `StackOverFlow.acceptAnswer` and in
`Question.acceptAnswer`; failures were a mix of `null` and `System.err`.
