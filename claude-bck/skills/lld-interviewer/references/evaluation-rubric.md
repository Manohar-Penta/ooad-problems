# Evaluation Rubric

Use this for checkpoint questions (what to probe) and for the final review (how to score). Score from evidence in the session only — what the candidate actually said, wrote, or ran.

## Point signals (the candidate's own priorities — apply these first)

These eight signals are what the candidate cares about most. Track each one in your private log throughout the interview, with the evidence (a quote, a class name, a method), and apply its adjustment to the stage where it happened. Adjustments stack with the stage's base score but a stage stays between 1 and 4.

### Earns points

| Signal | What counts | Stage | Adjustment |
|---|---|---|---|
| **E1. Clarifying questions that drive class design** | A question whose answer visibly changes the design: "Will pricing rules change?" → pricing behind an interface; "Can two gates assign spots at once?" → locking in the allocator. Generic questions that never reach the design don't count. | Requirements (and credit again in Class design if the link is explicit) | +0.25 each, max +0.75 |
| **E2. One clear responsibility per class** | Every class can be described in one sentence without "and". Storage, business rules, and orchestration are in separate places. | Class design | +0.5 if it holds for the whole design |
| **E3. Right rationale behind each pattern** | For every pattern used, the candidate names the concrete problem it solves in *this* problem ("Strategy because pricing differs by vehicle type and weekends, and new rules must not edit `Ticket`"). | Class design | +0.5 if every pattern used has a correct reason |
| **E4. Working demo** | Machine coding: the driver runs and shows the core flows end to end, plus at least one failure path. LLD discussion: a correct dry-run of a core flow across the classes, method by method. | Implementation | +0.5 |

### Loses points

| Signal | What counts | Stage | Adjustment |
|---|---|---|---|
| **L1. Little discussion** | Silent designing or coding; decisions made without saying why; one-line answers to "why?" questions. Prompt once with "Talk me through your thinking"; if it continues, apply the deduction. | Wherever it happened (each stage at most once) | −0.5 per stage |
| **L2. Patterns without reason** | A pattern added with no problem to solve, or a reason that doesn't hold ("Singleton because there is one parking lot" with no further justification; Factory for a class with one variant). | Class design | −0.5 per pattern, max −1 |
| **L3. One big manager class / tight coupling** | A `XManager` / `XService` / `System` class holding most of the logic; classes constructing their own concrete dependencies with `new`; callers depending on concrete classes where variation was asked about; changing one rule requires edits in several classes. | Class design (and Implementation if it shows up in code) | −1, and **Class design is capped at 2** |
| **L4. Half-built classes** | Classes declared in the design but never implemented or used; methods left as stubs, `TODO`, or `return null` in core flows; fields with no behaviour that uses them. | Implementation (Class design in LLD discussion mode) | −0.5 per class, max −1.5; in machine coding mode, a half-built class in a **core** flow **caps Implementation at 2** |

### How the signals affect the interview itself
- **E1:** when the candidate asks a design-driving question, answer it normally. Don't signal that it earned points.
- **E3 / L2:** whenever a pattern appears without a stated reason, ask "Why that pattern here?" once. Score the answer. Don't suggest the reason.
- **L1:** if the candidate goes quiet for a whole stage or gives only one-line answers, prompt once with "Talk me through your thinking." Don't prompt a second time; record it.
- **L3:** probe with a change request ("We add a new pricing rule — which classes change?"). If the answer touches the manager class or several classes, that's your evidence.
- **L4:** in machine coding, when running the code, note every class or method that the demo never reaches or that is a stub.

## The bar: bare minimum and good-to-have

The candidate has defined a hiring bar. The **bare minimum** items are pass/fail gates: missing one caps the overall signal however good the rest is. The **good-to-have** items only add a bonus, and only once the bare minimum is met. Missing a good-to-have never costs points.

### Bare minimum (gates)

Rate each one **Met**, **Partly met** or **Not met**, with evidence.

| # | Requirement | Judged mainly in | Met means |
|---|---|---|---|
| B1 | **Demoable and functionally correct code** | Implementation | Machine coding: compiles, runs, and every core flow gives correct results through the driver. LLD discussion: a correct method-by-method dry-run of each core flow with no contradictions. |
| B2 | **Code readability** | Implementation | Clear names, small focused methods, consistent style, no copy-paste blocks, no magic numbers or strings. Someone new could follow a core flow without the candidate explaining it. In LLD discussion, judge the skeletons and key method bodies. |
| B3 | **Proper entity modelling** | Entities & relationships | Every core domain concept is an entity, with no fake entities that are really attributes. Relationships and cardinality are right, state lives on the right object, and composition is chosen over inheritance where the variation is just data. |
| B4 | **Modularity and extensibility** | Class design, Follow-ups | Adding a new variant (vehicle type, split type, pricing rule, payment mode) means adding a class, not editing existing ones. Follow-up change requests touch few classes. |
| B5 | **Separation of concerns and abstractions** | Class design, Implementation | Models, services, storage (repository) and the driver are separate. Callers depend on interfaces where variation exists. No business logic in the driver or in data classes that should be plain. |
| B6 | **Corner case handling** | Error handling | The failure scenarios in the hidden spec are handled: invalid input, not-found, capacity exhausted, illegal state transitions, and concurrent conflicts where the spec has concurrency. Machine coding: handled in code, with at least one shown in the demo. |

**Gate rule for the overall signal:**
- Any bare-minimum item **Not met** → signal capped at **Lean No Hire**.
- Two or more items **Partly met** (and none Not met) → signal capped at **Lean Hire**.
- All Met, or only one Partly met → no cap.

These caps combine with the other caps in this file (the manager-class cap and the non-running-code cap); the lowest cap wins.

### Good-to-have (bonuses)

Award these only if **no bare-minimum item is Not met**. Each adds **+0.1** to the weighted overall score (max +0.3 total) and can never lift the signal above a cap.

| # | Bonus | Earned when |
|---|---|---|
| G1 | **Optimal time complexity** | Core operations use suitable data structures (e.g., O(1) or O(log n) spot lookup via per-type free lists or heaps, not a scan of every spot), and the candidate states the complexity correctly when asked. |
| G2 | **Optimality** | Beyond time complexity: no wasted work or memory, sensible lock granularity (lock a spot or a show's seats, not the whole system), no redundant passes or duplicate state that must be kept in sync. |
| G3 | **Cleverness** | A neat, non-obvious idea that makes the design simpler or faster and that the candidate can justify: O(1) Tic-Tac-Toe win check with row/column/diagonal counters, debt simplification with two heaps, a unified Jump for snakes and ladders. Cleverness that hurts readability doesn't count. |

### Probing for the bar during the interview
- **B1:** in machine coding, run the code against the core flows and your own edge inputs.
- **B3:** at the entity checkpoint, ask about one entity's ownership or cardinality.
- **B4:** give at least one "add a new variant" change request, at the class-design checkpoint or in follow-ups.
- **B5:** ask "Where does the storage live?" or "What does the driver know about?" if it isn't clear.
- **B6:** use the hidden spec's failure scenarios at the error-handling checkpoint.
- **G1:** at the implementation checkpoint, ask "What's the time complexity of <core operation>?" for one or two core operations. Don't hint at a better structure.
- **G2 / G3:** don't ask for these; note them when they show up.

## Scoring scale (per stage)

| Score | Meaning |
|---|---|
| 4 — Strong | Would clearly pass this part at a top product company. Little or no prompting needed. |
| 3 — Solid | Passes. Minor gaps that they fixed when probed, or small misses that don't hurt the design. |
| 2 — Weak | Noticeable gaps; needed significant prompting; some core aspect missing or wrong. |
| 1 — Poor | Stage largely missing or fundamentally flawed. |
| — | Skipped or not reached. |

## What each stage is judged on

### 1. Requirements discovery
- Asked about actors, core use cases, and scope boundaries before designing.
- Uncovered the **core** requirements from the hidden spec (each missed core requirement is a significant deduction; missed good-to-haves are minor).
- Asked about non-functional aspects: concurrency, scale, single vs multi-instance, persistence expectations.
- Stated assumptions explicitly and wrote a clear in-scope / out-of-scope list.
- Red flags: started drawing classes in the first minute; asked nothing; asked so many questions that design never started.

### 2. Entities & relationships
- Entities match the domain; no missing core entity; no unnecessary ones.
- Correct relationship types (composition vs aggregation vs association vs inheritance) and cardinality.
- State lives in the right place; clear ownership of lifecycles.
- Red flags: inheritance used where composition fits ("Car extends ParkingSpot"); god entity holding everything; entities that are really just attributes.

### 3. Class design
- **SOLID**: single responsibility per class; open for extension (new variant = new class, not edits to if-else chains); depends on interfaces, not concrete classes.
- Sensible use of patterns *where they solve a real problem* — Strategy for swappable rules (pricing, splitting, allocation), State for lifecycle-heavy objects (elevator, vending machine, order), Factory for creation by type, Observer for notifications, Singleton only with justification, Command for undo/queued actions, Chain of Responsibility for pipelines/filters, Decorator for layered add-ons.
- Clean separation of model / service / repository (storage) layers.
- Encapsulation: no public mutable fields; enums for fixed sets; immutability where natural.
- Red flags: pattern stuffing (patterns with no problem to solve); anemic models with all logic in one "Manager"; primitive obsession (strings for money, status, IDs everywhere); a design that needs to be edited in 5 places to add one new type.

### 4. Implementation
**Machine coding mode** (weighted most heavily in this mode):
- Runs; core flows work end to end through the driver.
- Code follows the design from stage 3 (or deviations are explained).
- Readable names, small methods, no copy-paste blocks, sensible package/class layout.
- Concurrency handled where the spec needs it (synchronized/locks/concurrent collections/atomic ops) and placed at the right granularity.
- Good-to-haves attempted only after core flows work.
- Red flags: doesn't compile/run; core flow broken; everything in `Main`; storage hard-wired into business logic with no repository abstraction.

**LLD discussion mode:**
- Key method logic is correct and consistent with the class design.
- Can walk through a core flow across classes without contradictions.

### 5. Error handling
- Covers invalid input, illegal state transitions, not-found cases, capacity/availability exhaustion, and concurrent conflicts.
- Uses meaningful custom exceptions or result types — not `return null` / `-1` / swallowing exceptions.
- Validation is done at the right layer and not duplicated everywhere.
- Machine coding: at least one failure path demonstrated in the driver.

### 6. Follow-ups
- Answers extend the existing design rather than redesigning from scratch — a sign the original design was extensible.
- Identifies exactly which classes change and which don't.
- Reasonable on concurrency/scale questions (locking strategy, idempotency, sharding of state) at an LLD level.
- Honest about trade-offs; doesn't hand-wave.

## Hint deduction
- 0 hints: no deduction.
- Each level-1 hint: −0.25 from that stage's score. Level 2: −0.5. Level 3: −0.75.
- A stage can't go below 1. Note hint use in the stage notes.
- Sessions with `coach mode` used get a "coached" label; still score them, but say the score is not interview-representative.

## Overall signal
Weight the stages by round type:

| Stage | Machine coding | LLD discussion |
|---|---|---|
| Requirements | 15% | 20% |
| Entities & relationships | 10% | 15% |
| Class design | 20% | 30% |
| Implementation | 35% | 10% |
| Error handling | 10% | 10% |
| Follow-ups | 10% | 15% |

Weighted average → signal (re-normalise weights over the stages actually reached; mention if the session was cut short):
- ≥ 3.5 → **Strong Hire**
- 3.0–3.49 → **Hire**
- 2.5–2.99 → **Lean Hire / borderline**
- 2.0–2.49 → **Lean No Hire**
- < 2.0 → **No Hire**

A non-running implementation in machine coding mode caps the signal at **Lean No Hire**, whatever the other scores, because that's how most real machine coding rounds are judged.

**Order of computation:**
1. Stage scores (base, then point-signal and hint adjustments, kept within 1–4).
2. Weighted average of the stage scores.
3. Add good-to-have bonuses (max +0.3), only if no bare-minimum item is Not met.
4. Map to a signal using the thresholds above.
5. Apply every cap that triggers (bare-minimum gate, manager-class cap on Class design already applied in step 1, non-running code); the lowest cap wins.

## Final review template

Use this structure exactly:

```
# Interview Debrief — <Problem> (<Machine coding | LLD discussion>, <Difficulty>)

**Signal: <Strong Hire | Hire | Lean Hire | Lean No Hire | No Hire>** · Weighted score <x.xx>/4 (incl. +<x> bonus) · Caps applied: <none | which> · Hints used: <n> <· coached, if applicable>

## Summary
<One honest paragraph: how a real interviewer would describe this candidate in the feedback form.>

## Stage scores
| Stage | Base | Signals & hints | Final | Key evidence |
|---|---|---|---|---|
| Requirements | x | ±x | x/4 | ... |
| Entities & relationships | x | ±x | x/4 | ... |
| Class design | x | ±x | x/4 | ... |
| Implementation | x | ±x | x/4 | ... |
| Error handling | x | ±x | x/4 | ... |
| Follow-ups | x | ±x | x/4 | ... |

## The bar
**Bare minimum** — <All met | n partly met | n not met> → <cap applied, or "no cap">
| # | Requirement | Result | Evidence |
|---|---|---|---|
| B1 | Demoable & functionally correct | Met / Partly / Not met | ... |
| B2 | Code readability | Met / Partly / Not met | ... |
| B3 | Proper entity modelling | Met / Partly / Not met | ... |
| B4 | Modularity & extensibility | Met / Partly / Not met | ... |
| B5 | Separation of concerns & abstractions | Met / Partly / Not met | ... |
| B6 | Corner case handling | Met / Partly / Not met | ... |

**Good-to-have** — +<x> bonus <or "not awarded: a bare-minimum item was not met">
| # | Bonus | Earned? | Evidence |
|---|---|---|---|
| G1 | Optimal time complexity | ✅ / ❌ | <operation → complexity> |
| G2 | Optimality | ✅ / ❌ | ... |
| G3 | Cleverness | ✅ / ❌ | ... |

## Point signals
| Signal | Result | Evidence | Adjustment |
|---|---|---|---|
| E1 Clarifying questions that drove design | ✅ / partly / ❌ | <the questions and what they changed> | +x |
| E2 One responsibility per class | ✅ / partly / ❌ | <classes that hold or break it> | +x |
| E3 Rationale behind patterns | ✅ / partly / ❌ / no patterns used | <pattern → stated reason> | +x |
| E4 Working demo | ✅ / partly / ❌ | <what ran, what failed> | +x |
| L1 Little discussion | not seen / seen | <stage and example> | −x |
| L2 Patterns without reason | not seen / seen | <pattern and why it's unjustified> | −x |
| L3 Manager class / tight coupling | not seen / seen | <class, and the change request that exposed it> | −x |
| L4 Half-built classes | not seen / seen | <class/method names> | −x |

## What went well
<2–4 specific points with references to their own classes/methods/statements.>

## What hurt
<The most important gaps, highest impact first, each tied to evidence.>

## Requirements you missed
<Core and good-to-have, labelled; plus the questions that would have uncovered them.>

## Follow-up answers — feedback
<For each follow-up question: what was strong, what was missing, a stronger answer in 1–3 lines.>

## Reference design (for comparison)
<Compact: key entities, interfaces, patterns, and why. A few lines or a short Mermaid class diagram. Not full code unless asked.>

## Practise next
<2–3 targeted drills, and one recommended problem from the bank that hits the weakest stage.>
```
