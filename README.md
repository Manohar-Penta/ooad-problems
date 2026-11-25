# LLD Practice

Low-level design problems in Java, with the principles and patterns learned along the way.

## Start here

- [LLD Process](lld_process.md): the step-by-step checklist to follow for every problem

## Design principles

| Principle | One-line idea |
|-----------|---------------|
| [Invariant ownership](design_principles/invariant_ownership.md) | The class that owns the data guards its rules |
| [Encapsulate what varies](design_principles/encapsulate_what_varies.md) | Isolate only the part that differs |
| [Valid by construction](design_principles/valid_by_construction.md) | An object never exists unable to do its job |
| [Separate action from consequences](design_principles/separate_action_from_consequences.md) | Side effects are their own responsibility |
| [Design by usage](design_principles/design_by_usage.md) | Write the call site before the signature |
| [Primitive obsession](design_principles/primitive_obsession.md) | Different concepts in one primitive type get mixed up |
| [Layer responsibilities](design_principles/layer_responsibilities.md) | Every check has one home; one failure contract |
| [Scenario-driven verification](design_principles/scenario_driven_verification.md) | Every requirement has a scenario that runs |

## Design patterns

| Pattern | Use when |
|---------|----------|
| [Strategy](design_patterns/strategy.md) | The whole algorithm is swappable |
| [Template Method](design_patterns/template_method.md) | The algorithm is fixed and one step varies |
| [Observer](design_patterns/observer.md) | An action has consequences that shouldn't be hard-wired |
| [Dependency Injection](design_patterns/dependency_injection.md) | Objects need collaborators supplied from outside |
| [Singleton](design_patterns/singleton.md) | Preventing a second instance is a hard requirement (rarely) |

## Problems

| Problem | Design | Notes |
|---------|--------|-------|
| Parking Lot | [design.md](src/parking_lot/design.md) | — |
| Elevator | [design.md](src/elevator_design/design.md) | — |
| Stack Overflow | [design.md](src/stackover_flow/design.md) | [review_notes.md](src/stackover_flow/review_notes.md) |
