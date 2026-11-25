# Design by Usage

## The core idea

> Write the line of code that **calls** an interface before you write the interface.

The caller's needs decide the parameters, the return type and the failure behaviour. A signature
designed in isolation is a guess.

## How to apply

1. For each use case, write the call on the facade in `Demo.java` first:
   ```java
   Question q = so.postQuestion(authorId, title, body, tags);
   so.vote(voterId, q.getId(), VoteType.UP);
   ```
2. Then answer: what does the caller need back? What does it already have (ids or objects)?
   What happens when it fails?
3. Only then write the method.

## Smells

- A method takes no parameters but must clearly depend on input (`search()`).
- A method returns `boolean` and the caller then needs the created object's id.
- Some facade methods take ids and others take objects for the same kind of thing.

## Seen in this repo

`stackover_flow`: `postQuestion` first returned `boolean`, so callers couldn't vote on what they had
posted. `SearchStrategy.search()` takes no arguments, so it has nothing to search with.
