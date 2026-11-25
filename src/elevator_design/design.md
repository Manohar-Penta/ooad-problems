# Design Scenario

Design an elevator control system for a building. The system should handle multiple elevators, floor requests, and move elevators efficiently to service requests.

## Requirements

### Questions

1. How many floors are we operating on? Fixed or Dynamic?
2. Number of elevators should be dynamic? Remove one(under maintanence) or Add one?
3.

### conclusion

0. Number of floors are 10, numbered from 0 to 9. Number of elevators are fixed at 3.
1. User on a floor will make a Hall request to go UP/DOWN.
2. User is assigned a Elevator for the Hall request.
3. User will get on the elevator when the elevator assigned stops at his floor.
4. User can request floors from inside the Cabin. Cabin requests are just a floor number — no direction is specified (unlike Hall requests).
5. User gets off when his floor is reached.
6. Elevator stops at every floor for the requests in the current direction.
7. Elevator will complete all the requests for the floors in the current direction before it changes direction to complete requests for the floors in the other direction.
8. Elevator stays still when no requests are present.
9. Simulation advances in discrete time steps via a step() function. Each call to step() moves an elevator one floor closer to its next destination. The main loop drives the simulation by repeatedly calling step().

Error handling:

1. User cannot request floors outside the floor number range.
2. User cannot enter the elevator which is moving in opposite direction of his requested direction.
