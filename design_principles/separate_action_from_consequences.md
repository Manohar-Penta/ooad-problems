# Separate the Action from Its Consequences

## The core idea

> An action (vote, accept, park) and the things that happen *because of it* (reputation, badges,
> notifications, billing) are different responsibilities.

If the action hard-codes every consequence, adding a new one means editing the action again: an
Open/Closed violation.

## Three levels of coupling

| Option | How | Trade-off |
|--------|-----|-----------|
| 1. Direct | Entity mutates the other entity (`author.updateReputation(...)`) | Simplest; entity knows every rule |
| 2. Service | Entity or facade calls a service (`reputationService.apply(...)`) | Rules in one place; caller still knows the service exists |
| 3. Events | Action publishes "voted", listeners react ([Observer](../design_patterns/observer.md)) | Most extensible; more indirection, harder to trace |

None is always right. In an interview, option 2 done **consistently** is fine; option 3 is the answer
to "how would you add badges later?"

## The real rule

> Pick one approach on purpose and use it everywhere.

Mixing approaches is worse than any single one: readers can't predict where a rule lives.

## Seen in this repo

`stackover_flow`: `Question` updated reputation directly while `Answer` went through a service.
