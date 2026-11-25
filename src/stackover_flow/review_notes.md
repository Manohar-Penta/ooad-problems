# Stack Overflow: Review Notes

Lessons from reviewing this design against
[awesome-low-level-design / stack-overflow](https://github.com/ashishps1/awesome-low-level-design/blob/main/problems/stack-overflow.md).
Each item names the habit, where it showed up, and a question to work through. Fixes are left as
exercises on purpose.

## Progress so far

- Round 1 → 2: `Post` base class extracted, collections initialised, posts return created objects,
  user registration added, `getComments()` returns a copy, facade fields `private final`.

## Lessons

| Habit | Where it showed up | Question to work through |
|-------|--------------------|--------------------------|
| [Invariant ownership](../../design_principles/invariant_ownership.md) | `votes` in `Post` is `protected`; the vote rules sit in `Question`/`Answer` | Which class is the only one that can guarantee "one vote per user"? |
| [Encapsulate what varies](../../design_principles/encapsulate_what_varies.md) | The two `vote` methods run the same algorithm | Line by line, what actually differs? |
| [Valid by construction](../../design_principles/valid_by_construction.md) | `Answer.setReputationService` never called; `User` email set by setter | Which non-`final` fields really change after construction? |
| [Action vs consequences](../../design_principles/separate_action_from_consequences.md) | Reputation applied directly in one class, via a service in the other | Which approach will you use everywhere, and why? |
| [Design by usage](../../design_principles/design_by_usage.md) | `SearchStrategy.search()` takes no input | What does the call site look like? Who picks the strategy? |
| [Primitive obsession](../../design_principles/primitive_obsession.md) | User id compared with question id in `acceptAnswer` | Where else are two different ids both plain `String`s? |
| [Layer responsibilities](../../design_principles/layer_responsibilities.md) | "Answer exists" checked in two layers; `null`/`System.err` failures | Which layer owns each check? What should a caller get on failure? |
| [Scenario-driven verification](../../design_principles/scenario_driven_verification.md) | Accepting an answer never succeeded; answer votes crashed | Does every requirement have a scenario, abuse cases included? |

## Next iteration

1. Add `## Invariants` and `## Layers` sections to `design.md`
   ([LLD process](../../lld_process.md) steps 4–5) before changing code.
2. Rework voting so each invariant is enforced in exactly one place.
3. Write `StackOverFlowDemo.java` with one scenario per requirement.
4. Concurrency pass: list the read-then-write sequences (vote, accept, reputation).
