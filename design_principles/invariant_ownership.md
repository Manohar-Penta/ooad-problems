# Invariant Ownership

## The core idea

> An **invariant** is a rule that must hold no matter which methods are called, in which order.
> The class that **owns the data** behind a rule is the only class that can guarantee it.

This is the *Information Expert* principle (GRASP), and it is the real reason encapsulation exists:
private fields don't only hide data, they make the owning class the single gatekeeper of its rules.

## How to apply

1. For each entity, write 3–5 invariants in `design.md` **before** writing code.
2. Put each rule in the class that holds the data it constrains.
3. Make that data `private`. If a subclass or caller can mutate it directly, the rule can be bypassed.
4. For each invariant ask: *"Can any code outside this class break it?"* If yes, the field is too
   visible or the rule is in the wrong class.

## Smells

- A collection is `protected`/`public` and several classes write to it.
- The same check appears in two classes, and the copies have started to differ.
- A subclass has a `// TODO` for logic that is really the parent's rule.

## Seen in this repo

`stackover_flow`: `Post` holds `votes`, but the voting rules sat in `Question` and `Answer`, and
`votes` was `protected`. Three classes shared one rule, so `Question` never recorded votes while
`Answer` did. See [`src/stackover_flow/review_notes.md`](../src/stackover_flow/review_notes.md).
