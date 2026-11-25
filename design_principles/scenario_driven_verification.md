# Scenario-Driven Verification

## The core idea

> A design isn't finished until every requirement has a scenario that runs.

Reading a design rarely reveals that a flow can never succeed. Running it does, in seconds.

## How to apply

1. In `<Problem>Demo.java` (or JUnit, see `lib/`), write one scenario per requirement in `design.md`.
2. Add the **abuse cases** next to each happy path:
   - the same user acting twice
   - a user acting on their own content
   - the wrong user performing an owner-only action
   - unknown ids, empty input, unknown search terms
3. Print or assert the observable state (counts, reputation, returned objects), not only "no crash".
4. If you can't write a scenario for a requirement, the design for it isn't done.

## Seen in this repo

`stackover_flow`: accepting an answer never succeeded and every answer vote crashed. A ten-line
`main` showed both immediately.
