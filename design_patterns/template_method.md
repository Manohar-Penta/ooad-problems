# Template Method Pattern

## What it is

A behavioural pattern where a base class defines the **fixed steps** of an algorithm and lets
subclasses supply only the steps that vary.

```java
public abstract class ReportExporter {
    public final String export(Report report) {      // the fixed algorithm
        String body = render(report);                // varying step
        return header(report) + body + footer();
    }

    protected abstract String render(Report report); // the only thing subclasses provide
}
```

`final` on the template method stops subclasses from changing the algorithm itself, which is what
keeps the base class's invariants safe.

## When to use

- Two or more classes run the **same sequence of steps** and differ in one step or one value.
- The base class must enforce rules around that sequence (validation, bookkeeping) that subclasses
  must not skip. See [invariant ownership](../design_principles/invariant_ownership.md).

## When to avoid

- The whole algorithm varies, or must be swapped at runtime → [Strategy](strategy.md).
- The varying part is only data (a number per type) → an enum or a lookup may be enough, with no
  abstract method at all.

## Template Method vs Strategy

| | Template Method | Strategy |
|---|---|---|
| Mechanism | Inheritance | Composition |
| What varies | One step | The whole algorithm |
| Chosen | When the class is written | At construction or at runtime |

## In this repo

`stackover_flow`: `Post` is a natural home for a template method. Work out which voting steps are
fixed and which single piece varies between `Question` and `Answer`.
