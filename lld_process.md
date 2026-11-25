# LLD Process

A repeatable checklist for every problem in `src/`. Each step links to the principle behind it.

| # | Step | Output in `design.md` | Principle |
|---|------|-----------------------|-----------|
| 1 | Requirements, **including abuse cases** (double vote, self vote, wrong user acts) | `## Requirements` | — |
| 2 | Use cases written as **calls on the facade** | `## Use Cases` | [Design by usage](design_principles/design_by_usage.md) |
| 3 | Entities and relationships | `## Entities & Relationships` | — |
| 4 | **Invariants per entity, and who owns each one** | `## Invariants` | [Invariant ownership](design_principles/invariant_ownership.md) |
| 5 | **Responsibilities per layer** (facade / entity / service) and the failure contract | `## Layers` | [Layer responsibilities](design_principles/layer_responsibilities.md) |
| 6 | Patterns, **only where something varies** | `## Patterns` | [Encapsulate what varies](design_principles/encapsulate_what_varies.md) |
| 7 | Class design: constructors, `final` fields, types for ids | `## Class Design` | [Valid by construction](design_principles/valid_by_construction.md), [Primitive obsession](design_principles/primitive_obsession.md) |
| 8 | **One demo scenario per requirement**, abuse cases included | `<Problem>Demo.java` | [Scenario-driven verification](design_principles/scenario_driven_verification.md) |
| 9 | Concurrency pass: list every read-then-write sequence and how it is protected | `## Concurrency` | — |

## Self-review questions before calling a design done

- Can any code outside a class break one of its invariants?
- Is any rule written in two places?
- Can any object exist without a dependency it needs to work?
- Did I write the call site before each public signature?
- Is there a side effect hard-wired into an action that it doesn't belong to?
- Does every requirement have a scenario that runs?

## Patterns reference

- [Strategy](design_patterns/strategy.md): swap an algorithm (search, pricing, spot finding)
- [Template Method](design_patterns/template_method.md): fixed algorithm, one varying step
- [Observer](design_patterns/observer.md): react to an action without the action knowing
- [Dependency Injection](design_patterns/dependency_injection.md), [Singleton](design_patterns/singleton.md)
