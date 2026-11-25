# Dependency Injection (DI)

The preferred alternative to a [singleton](singleton.md) for a stateful core object.

## The core idea

> Give an object its dependencies from the outside instead of letting it create or fetch
> them itself.

Create each shared object **once, at the composition root**, and hand it to whoever needs
it. This is the same principle whether you do it by hand or with a framework:

- **Passing the object around** — construct once in `main`, thread it through constructors.
- **A DI container (Spring, Guice, Dagger)** — the framework constructs objects, resolves
  the graph, and manages lifetimes/scopes for you.

They are two levels of formality on one idea. Manual DI is perfect for interviews and small
systems; a container earns its keep once the object graph is large.

The opposite — an object reaching out for its own dependencies (`X.getInstance()`, `new
ConcreteThing()` inside business logic) — is what couples code and breaks tests.

## The composition root

The **composition root** is the single place, as close to the program entry point as
possible, where the concrete object graph is wired together. Everywhere else depends only on
interfaces and receives what it needs.

In this repo the composition root is `ParkingLotDemo.main`:

```java
// The ONE place that names concrete strategies and constructs the system.
ParkingLotSystem parkingLot = new ParkingLotSystem(
        new NearestFitParkingStrategy(),
        new VehicleBaseFeeStrategy());
```

"There is one parking lot" is expressed by *creating one here and sharing it* — not by the
class forbidding a second. (See the key insight in [`singleton.md`](singleton.md).)

## Forms of injection

1. **Constructor injection — prefer this.** Mandatory collaborators are parameters; fields
   become `final`; the object is **valid the moment it exists**. Dependencies are visible in
   the signature.
2. **Setter injection** — for genuinely *optional* or *runtime-swappable* collaborators
   (e.g. "switch to night pricing" at runtime). Costs: the object has a window where the
   dependency is unset, and the field must be mutable (and `volatile` if shared across
   threads). Use deliberately, not as the default.
3. **Method / parameter injection** — pass the dependency to the one method that needs it,
   when it isn't part of the object's long-lived state.

Rule of thumb: **mandatory → constructor; truly optional/reconfigurable → setter.**

## Worked example: the singleton → DI refactor in this repo

### Before (singleton)
```java
public class ParkingLotSystem {
    private volatile IParkingStrategy parkingStrategy;   // mutable, defaulted
    private volatile IFeeStrategy feeStrategy;
    private volatile Clock clock;

    private ParkingLotSystem() {                          // no args -> can't inject
        this.parkingStrategy = new NearestFitParkingStrategy();  // couples to concretes
        this.feeStrategy = new FlatRateFeeStrategy();
        this.clock = Clock.systemDefaultZone();
    }
    public static ParkingLotSystem getInstance() { ... }  // global access point

    public void setParkingStrategy(IParkingStrategy s) { ... }  // reconfigurable by anyone
    public void setFeeStrategy(IFeeStrategy s) { ... }
    public void setClock(Clock c) { ... }
}
```

### After (constructor injection)
```java
public class ParkingLotSystem {
    private final IParkingStrategy parkingStrategy;      // set once, immutable
    private final IFeeStrategy feeStrategy;
    private final Clock clock;

    public ParkingLotSystem(IParkingStrategy parkingStrategy, IFeeStrategy feeStrategy) {
        this(parkingStrategy, feeStrategy, Clock.systemDefaultZone());  // convenience default
    }
    public ParkingLotSystem(IParkingStrategy parkingStrategy,
                            IFeeStrategy feeStrategy, Clock clock) {
        this.parkingStrategy = Objects.requireNonNull(parkingStrategy);
        this.feeStrategy = Objects.requireNonNull(feeStrategy);
        this.clock = Objects.requireNonNull(clock);
        // ...concurrent collections...
    }
    // no getInstance(), no Holder, no setters
}
```

### What the refactor bought
1. **Valid by construction.** You cannot build the system without its collaborators, so the
   "unconfigured → NullPointerException" bug becomes *impossible*, enforced by the type.
2. **`final` instead of `volatile`.** Fields set once in the constructor are `final`, and the
   Java Memory Model safely publishes `final` fields to other threads — so the `volatile`
   the setters required simply went away. DI made concurrency *simpler*.
3. **Better Dependency Inversion.** `ParkingLotSystem` no longer imports the concrete
   strategy classes; it depends only on `IParkingStrategy` / `IFeeStrategy`. The composition
   root owns the choice of concretes.
4. **Testability.** Tests do `new ParkingLotSystem(nearestFit, flatFees, fixedClock)` — fresh
   isolated state per instance, no JVM-wide leakage, no `reset()` hack. Time is controlled by
   injecting a clock test double (a `MutableClock` advanced by the test) instead of a setter
   on the system.

## Injecting a controllable dependency (test double)

DI's payoff for testing is that you can substitute a collaborator. For time, inject a clock
you can advance — no setter on the production class required:

```java
MutableClock clock = new MutableClock(entry, ZoneOffset.UTC);
var lot = new ParkingLotSystem(new NearestFitParkingStrategy(), new FlatRateFeeStrategy(), clock);
var ticket = lot.parkVehicle(FOUR_WHEELER_VEHICLE, "C-1").orElseThrow();
clock.setInstant(entry.plus(Duration.ofHours(3)));      // advance time deterministically
double fee = lot.exitVehicle(ticket.getTicketId()).orElseThrow();  // -> (3+1) * rate
```

## Trade-offs / when to be careful

DI is not free of downsides — name them honestly:

- **Wiring boilerplate.** Something must construct the graph. Manual DI pushes that into the
  composition root; a container hides it behind configuration/annotations.
- **Over-injection smell.** A constructor with 8+ dependencies usually signals the class does
  too much — DI surfaces the design problem rather than causing it.
- **Framework "magic."** Containers add reflection, annotations, and startup indirection that
  can obscure control flow and slow debugging. Don't reach for a container before the graph
  justifies it.
- **You still choose lifetime.** "One instance" becomes a *scoping* decision (create one at
  the root / a container singleton scope) rather than a class-enforced rule — which is the
  point: it stays your decision, testable and overridable.

## Relationship to the singleton

DI gives you the *one useful thing* a singleton offered — a single shared instance — without
the costs, because the "single" lives in **how you wire it**, not in the class. Need one?
Create one at the composition root and inject it. Reach for the Singleton pattern only when
*preventing* a second instance is itself a hard requirement.
