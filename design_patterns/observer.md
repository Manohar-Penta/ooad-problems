# Observer Pattern

## What it is

A behavioural pattern where a subject announces that something happened, and any number of
registered observers react, without the subject knowing who they are.

```java
public interface PostEventListener {
    void onEvent(PostEvent event);   // e.g. VOTED, ANSWER_ACCEPTED
}
```

## When to use

- One action has several consequences that belong to different parts of the system (reputation,
  badges, notifications). See
  [separate the action from its consequences](../design_principles/separate_action_from_consequences.md).
- New consequences are likely to be added later and you don't want to edit the action each time
  (Open/Closed).

## When to avoid

- There is exactly one consequence and it's unlikely to grow: a direct service call is simpler and
  easier to trace.
- The consequence must succeed or fail **together** with the action. Events make that atomicity harder
  to see and to guarantee.

## Costs to name in an interview

- Control flow is indirect: reading the subject doesn't show what happens next.
- Ordering between observers and error handling in observers must be decided explicitly.
- Under concurrency, decide whether observers run inside the subject's lock or after it.

## In this repo

`stackover_flow`: reputation is a consequence of voting and accepting. Observer is the extensible
option; a single `ReputationService` used consistently is the simpler one.
