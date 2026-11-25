# Singleton Pattern

## What it is

A creational pattern that ensures a class has **exactly one instance** and provides a
single global access point to it. The class itself enforces the "only one" rule by
hiding its constructor.

```java
ParkingLotSystem lot = ParkingLotSystem.getInstance(); // always the same object
```

## Correct implementation in Java

There are several ways to write one; most have sharp edges. Ranked from best to worst:

### 1. Bill Pugh / initialization-on-demand holder (what this repo uses)
Lazy **and** thread-safe with no synchronization, because class loading is itself
thread-safe. The `Holder` class isn't loaded until `getInstance()` is first called.

```java
public class ParkingLotSystem {
    private ParkingLotSystem() { ... }

    private static class Holder {
        private static final ParkingLotSystem INSTANCE = new ParkingLotSystem();
    }

    public static ParkingLotSystem getInstance() {
        return Holder.INSTANCE;
    }
}
```

### 2. Enum singleton (Effective Java's recommendation)
Concise, and free from serialization/reflection attacks that can otherwise create a
second instance. Downside: an enum can't extend a class, and it reads oddly for a
stateful service.

```java
public enum ParkingLotSystem { INSTANCE; ... }
```

### Approaches to avoid
- **Eager `static final`** — simple, but constructs at class-load even if never used, and
  can't handle construction that needs runtime arguments or that can fail.
- **`synchronized` getInstance()** — correct but locks on *every* read; needless contention.
- **Double-checked locking** — works only with a `volatile` field and is easy to get
  subtly wrong. The holder idiom gives the same benefit with none of the risk.

> Implementing the idiom correctly is not the hard part. The hard question is whether the
> class should be a singleton **at all** — see below.

## When to use

- **Stateless helpers / pure-function holders** — no shared mutable state, so none of the
  testing or coupling costs apply.
- **Truly process-global, immutable-after-init infrastructure** — a logger, a metrics
  registry, a config loaded once at startup — where you knowingly accept the test tax.
- **Constants** via `enum`.
- When the cost of accidentally creating two is genuinely dangerous (e.g. a hardware
  device handle) *and* the object is configured once, not per-use.

Common thread: **little or no mutable state, and you never need a second one — not even
in a test.**

## When to avoid

Avoid it for the **central, stateful domain object** — which is exactly what
`ParkingLotSystem` is. Concrete costs (several observed while building this repo):

1. **Testability breaks.** The instance is `static` and lives for the whole JVM, so state
   accumulates across uses. In this repo the demo/tests only look clean because each runs
   in a **fresh JVM**; multiple JUnit methods in one process would share one test's floors
   and tickets, making tests **order-dependent**. There's no clean per-test reset without
   bolting on a `reset()` method — itself a smell and a concurrency hazard.
2. **It's a global variable in disguise.** Anyone can call `getInstance()`, so dependencies
   are **invisible in method signatures**. You can't tell what a method touches → hidden
   coupling.
3. **You can't run two configurations.** One process = one lot, one policy set. No second
   lot with different pricing, no A/B of strategies, no parallel test isolation. The
   pattern bakes *"there is exactly one"* into the **type**, when that's really a
   deployment/testing decision.
4. **Lifecycle rigidity.** The no-arg constructor can't take mandatory collaborators. In
   this repo that *forced* `ParkingLotSystem`'s strategies/clock to be mutable `volatile`
   fields with setters + defaults instead of `final` injected dependencies — widening the
   mutability surface and needing `volatile` just to keep runtime reconfiguration safe.
   (Since refactored to constructor injection — see the worked example in
   [`dependency_injection.md`](dependency_injection.md), which made those fields `final` and
   let the `volatile` go away entirely.)

## The key insight

> There being **one parking lot in the domain** does not require the **class to enforce
> singleton-ness.**

You model "one lot" by *creating one instance at the composition root* (`main`) and passing
it where needed — not by making the class forbid a second instance. The Singleton pattern
conflates *"there is one"* with *"the class makes more than one impossible"*, and it's that
second half that causes every cost listed above. A DI container gives you "one instance"
scoping without any of the downsides.

**Rule of thumb:** need one instance? Prefer *creating* one and sharing it. Reach for the
Singleton pattern only when preventing a second instance is itself a hard requirement.

## Alternatives

The preferred alternative for a stateful core object is **dependency injection**: create the
single instance at the composition root and hand it to whoever needs it, rather than letting
the class enforce "only one." "Injecting via a framework" and "passing the object around by
hand" are the same principle at two levels of formality.

`ParkingLotSystem` in this repo has been migrated from a singleton to constructor injection.
See **[`dependency_injection.md`](dependency_injection.md)** for the principle, the forms of
injection, and the before/after of that exact refactor.
