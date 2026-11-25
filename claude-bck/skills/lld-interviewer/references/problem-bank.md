# Problem Bank (interviewer's eyes only)

Each entry is a **hidden spec**. Show the candidate only the "Prompt" line. Everything else is your private sheet for answering clarifying questions, judging coverage, and choosing follow-ups. You may also run a problem not listed here — build a hidden spec in the same shape before giving the prompt.

"Modes" says which round type the problem suits: **MC** = machine coding, **LLD** = design discussion.

## Contents
Easy: 1. Snake & Ladder · 2. Vending Machine · 3. LRU Cache · 4. Logger Library
Medium: 5. Parking Lot · 6. Splitwise · 7. Library Management · 8. Rate Limiter · 9. In-memory Key-Value Store · 10. Tic-Tac-Toe / Chess
Hard: 11. Elevator System · 12. Movie Ticket Booking (BookMyShow) · 13. Ride Sharing (Uber/Ola) · 14. Pub-Sub Message Queue · 15. Food Delivery (Swiggy/Zomato) · 16. Task Scheduler

---

## 1. Snake & Ladder — Easy — MC, LLD
**Prompt:** "Design a Snake and Ladder game."
**Core:** N×N board (default 100 cells); configurable snakes and ladders (input as start→end); 2+ players taking turns; one die (1–6); a player wins on reaching the last cell exactly — an overshoot means the player doesn't move; game ends at the first winner.
**Good-to-have:** multiple dice; continue until all but one finish; extra turn on a 6, and three 6s in a row cancel the turn.
**Constraints:** single process, no concurrency.
**Key entities:** Game, Board, Cell/Jump (Snake, Ladder as one Jump type with start/end), Player, Dice, (Turn manager).
**Patterns worth seeing:** Strategy for dice/movement rules; no inheritance explosion for Snake vs Ladder.
**Failure scenarios:** snake and ladder starting on the same cell; a snake whose head is below its tail; cycles; zero players.
**Follow-ups:** add a "crocodile" that sends you back 5 cells; replay a game from its log; make the board non-square; support undoing the last move.

## 2. Vending Machine — Easy — MC, LLD
**Prompt:** "Design a vending machine."
**Core:** products in slots with price and quantity; user selects a product, inserts coins/notes, gets the product and change; cancel returns the money inserted; admin restocks and collects cash.
**Good-to-have:** card/UPI payments; per-product discounts; low-stock alerts.
**Constraints:** one user at a time; finite coin inventory for change.
**Key entities:** VendingMachine, Slot/Inventory, Product, Coin/Denomination enum, Payment, State (Idle, HasMoney, Dispensing, OutOfService).
**Patterns worth seeing:** State pattern for the machine's lifecycle; Strategy for payment.
**Failure scenarios:** cannot make exact change; product sold out after money was inserted; invalid coin; power cut mid-dispense.
**Follow-ups:** add a new payment mode; machine-level telemetry to a central server; support combo products.

## 3. LRU Cache — Easy — MC, LLD
**Prompt:** "Design a cache."
**Core (reveal when asked):** fixed capacity; get/put in O(1); evicts the least recently used entry when full; generic key/value types.
**Good-to-have:** TTL per entry; pluggable eviction (LFU, FIFO); hit/miss stats.
**Constraints:** must be thread-safe if asked about concurrency (answer: yes, multiple threads).
**Key entities:** Cache interface, EvictionPolicy, Storage, DoublyLinkedList/Node.
**Patterns worth seeing:** Strategy for eviction; separation of storage from policy.
**Failure scenarios:** capacity 0 or negative; null keys; concurrent put of the same key.
**Follow-ups:** switch to LFU without touching Cache code; add write-through to a DB; distributed across nodes (LLD level: consistent hashing idea).

## 4. Logger Library — Easy — MC, LLD
**Prompt:** "Design a logging library that applications can use."
**Core:** log levels (DEBUG, INFO, WARN, ERROR, FATAL); configurable minimum level; multiple sinks (console, file); message format with timestamp and level.
**Good-to-have:** async logging; per-sink level; custom formatters; rotation of log files.
**Constraints:** many threads log concurrently; logging must not block the app noticeably.
**Key entities:** Logger, LogLevel enum, LogMessage, Appender/Sink interface, Formatter, LoggerConfig.
**Patterns worth seeing:** Chain of Responsibility for levels or Observer for sinks; Strategy for formatters; Singleton/factory for the logger, with justification.
**Failure scenarios:** file sink unavailable; disk full; queue full in async mode.
**Follow-ups:** add a DB sink; sample DEBUG logs at 10%; structured JSON logs; make the config hot-reloadable.

## 5. Parking Lot — Medium — MC, LLD
**Prompt:** "Design a parking lot system."
**Core:** multiple floors; spot types (bike, car/compact, large/truck); a vehicle gets the nearest suitable free spot; ticket issued at entry gate; fee calculated at exit by duration and vehicle type; display free-spot count per floor and type.
**Good-to-have:** multiple entry/exit gates; EV charging spots; monthly passes; different pricing for weekends.
**Constraints:** multiple gates work concurrently — two gates must never assign the same spot.
**Key entities:** ParkingLot, Floor, ParkingSpot (+ SpotType), Vehicle (+ VehicleType), Ticket, Gate (Entry/Exit), PricingStrategy, Payment, SpotAllocationStrategy.
**Patterns worth seeing:** Strategy for pricing and allocation; Factory for vehicle/spot; avoid `Car extends Vehicle` explosion if type is just data.
**Failure scenarios:** lot full; lost ticket; same vehicle enters twice; payment fails at exit; concurrent assignment of the last spot.
**Follow-ups:** add EV charging billed per kWh; allow a car to take a large spot when car spots are full; support reservations in advance; how to scale to 100 lots under one company.

## 6. Splitwise (Expense Sharing) — Medium — MC, LLD
**Prompt:** "Design an expense-sharing app like Splitwise."
**Core:** users; add an expense paid by one user, split among users by EQUAL, EXACT amounts, or PERCENT; show balances per user and between pairs; validation (exact amounts sum to total, percentages sum to 100).
**Good-to-have:** groups; simplify debts (minimise transactions); expense history; settle up.
**Constraints:** money must not lose paise (rounding remainder goes to someone deterministically).
**Key entities:** User, Expense (+ ExpenseType), Split (EqualSplit, ExactSplit, PercentSplit), BalanceSheet, ExpenseService, Group.
**Patterns worth seeing:** Strategy/Factory for split types; separate validation per split type.
**Failure scenarios:** ₹100 split 3 ways (rounding); percentages totalling 99; user not in group; negative amount.
**Follow-ups:** add a "shares" split (2:1:1); multi-currency; simplify debts across a group; undo an expense.

## 7. Library Management — Medium — MC, LLD
**Prompt:** "Design a library management system."
**Core:** books with multiple copies; members borrow and return copies; max 5 books per member; 14-day loan period; fine per day late; search by title/author/subject.
**Good-to-have:** reservations/hold queue on unavailable books; notifications when a held book is available; librarian vs member roles.
**Constraints:** two members can't borrow the same copy at the same time.
**Key entities:** Book (metadata), BookItem (physical copy), Member, Librarian, Loan/Lending, Fine, Catalog/SearchService, Reservation.
**Patterns worth seeing:** Book vs BookItem separation; Observer for notifications; Strategy for fine calculation.
**Failure scenarios:** borrowing beyond the limit; returning a book not borrowed; reserving an available book; member with unpaid fines borrowing.
**Follow-ups:** e-books with concurrent licence limits; fines differ by membership tier; branches across a city.

## 8. Rate Limiter — Medium — MC, LLD
**Prompt:** "Design a rate limiter."
**Core:** limit requests per client (user ID / API key) to N per time window; configurable per client or per API; reject over-limit requests with a clear result.
**Good-to-have:** multiple algorithms (fixed window, sliding window log, sliding window counter, token bucket); per-endpoint tiers (free vs paid).
**Constraints:** high concurrency within one service instance; can be asked about distributed later.
**Key entities:** RateLimiter interface, algorithm implementations, RateLimitConfig, ClientIdentifier, RequestContext, RateLimiterFactory/Registry.
**Patterns worth seeing:** Strategy for algorithms; Factory/registry per rule.
**Failure scenarios:** burst at window boundary (fixed window flaw); config missing for a client; clock skew.
**Follow-ups:** switch algorithm without code changes in callers; distributed across instances (shared store, atomic increments); return "retry after" info.

## 9. In-memory Key-Value Store — Medium — MC
**Prompt:** "Design an in-memory key-value store."
**Core:** put/get/delete; values are attribute maps (e.g., `{name: "x", age: 25}`); attribute types fixed by first insert (later conflicting type → error); search keys by attribute value.
**Good-to-have:** TTL; transactions (begin/commit/rollback); secondary index for search.
**Constraints:** thread-safe for concurrent reads and writes.
**Key entities:** KVStore, Value/AttributeMap, AttributeType registry, Index, TransactionManager.
**Patterns worth seeing:** separating type registry and index from store; Command/undo log for transactions.
**Failure scenarios:** type mismatch on put; get of missing key; nested transactions; rollback after partial writes.
**Follow-ups:** add persistence with a write-ahead log; range queries; snapshot isolation for reads.

## 10. Tic-Tac-Toe / Chess — Medium (Tic-Tac-Toe Easy, Chess Hard) — MC, LLD
**Prompt:** "Design a Tic-Tac-Toe game." (or "Design a chess game.")
**Core (TTT):** N×N board; 2 players; alternate moves; detect win (row/col/diagonal) or draw; reject invalid moves.
**Core (Chess):** standard board and pieces with their move rules; turns; check, checkmate, stalemate; captures; move validation.
**Good-to-have:** undo; move history; castling/en passant/promotion (chess); human vs bot.
**Key entities:** Game, Board, Cell, Player, Piece (+ subclasses or MoveStrategy), Move, GameStatus.
**Patterns worth seeing:** Strategy for piece movement or win checking; Command for moves (undo); O(1) win check for TTT using counters.
**Failure scenarios:** move outside the board; move on an occupied cell; moving the opponent's piece; move that leaves own king in check.
**Follow-ups:** add an AI player; play over network (what moves to the server); save and resume a game.

## 11. Elevator System — Hard — MC, LLD
**Prompt:** "Design an elevator system for a building."
**Core:** M elevators, N floors; external requests (floor + direction) and internal requests (destination floor); a dispatcher assigns external requests to elevators; each elevator moves floor by floor, stops at requested floors, and has states (idle, moving up, moving down, maintenance); door open/close.
**Good-to-have:** capacity/weight limit; VIP/express elevators serving only some floors; emergency stop.
**Constraints:** requests arrive concurrently while elevators are moving.
**Key entities:** ElevatorController/Dispatcher, Elevator, ElevatorState, Request (External/Internal), Direction, Door, Display, SchedulingStrategy.
**Patterns worth seeing:** State for elevator; Strategy for scheduling (nearest car, SCAN/LOOK); Observer for displays.
**Failure scenarios:** elevator under maintenance with pending requests; request for a non-existent floor; overload; all elevators busy.
**Follow-ups:** change the scheduling algorithm at rush hour; add a destination-dispatch lobby panel; odd/even floor elevators.

## 12. Movie Ticket Booking (BookMyShow) — Hard — MC, LLD
**Prompt:** "Design a movie ticket booking system."
**Core:** cities → theatres → screens → shows; movies; seat layout per screen with seat types (regular/premium) and prices; search shows by city/movie; select seats, hold them temporarily, pay, confirm booking; cancel booking.
**Good-to-have:** coupons/offers; seat hold expiry (e.g., 10 min); notifications; dynamic pricing.
**Constraints:** many users select seats for the same show at the same time — no double booking.
**Key entities:** City, Theatre, Screen, Seat (+ SeatType), Show, ShowSeat (seat state per show), Movie, Booking, Payment, SeatLockProvider, BookingService.
**Patterns worth seeing:** ShowSeat separate from Seat; a lock/hold abstraction with expiry; Strategy for pricing and payment.
**Failure scenarios:** two users booking the same seat; payment fails after the hold; the hold expires during payment; show cancelled after booking.
**Follow-ups:** distributed seat locking across app servers; waitlists; group bookings that must sit together; refunds policy by time to show.

## 13. Ride Sharing (Uber/Ola) — Hard — LLD (MC with reduced scope)
**Prompt:** "Design a ride-hailing system like Uber."
**Core:** riders request a ride (source, destination); nearby available drivers are matched; driver accepts/rejects; trip lifecycle (requested → accepted → in progress → completed/cancelled); fare by distance and vehicle type; ratings.
**Good-to-have:** surge pricing; ride pooling; scheduled rides; driver earnings.
**Constraints:** a driver can't be assigned two rides at once; many requests at once in one area.
**Key entities:** Rider, Driver, Vehicle (+ type), Location, Trip (+ TripStatus), MatchingStrategy, PricingStrategy, TripService, Rating.
**Patterns worth seeing:** State for trip; Strategy for matching and pricing; Observer for trip updates.
**Failure scenarios:** no drivers nearby; driver cancels after accepting; rider cancels mid-way; payment fails at the end.
**Follow-ups:** add pooling; surge pricing by demand/supply ratio; how to find nearby drivers efficiently (geo-hashing/quad-tree at a conceptual level).

## 14. Pub-Sub Message Queue — Hard — MC, LLD
**Prompt:** "Design a message queue."
**Core:** topics; publishers publish messages to a topic; multiple subscribers per topic consume independently; each subscriber tracks its own offset; consumers process in parallel.
**Good-to-have:** consumer groups; retries with dead-letter queue; resetting offset; message retention/TTL.
**Constraints:** concurrent publishers and subscribers; ordering per topic.
**Key entities:** Queue/Broker, Topic, Message, Publisher, Subscriber interface, SubscriberWorker, Offset tracking.
**Patterns worth seeing:** Observer/pub-sub; producer-consumer with proper wait/notify or blocking queues.
**Failure scenarios:** subscriber throws while processing; slow subscriber; publishing to a missing topic.
**Follow-ups:** at-least-once vs exactly-once delivery; partitions; persistence and recovery after a crash.

## 15. Food Delivery (Swiggy/Zomato) — Hard — LLD (MC with reduced scope)
**Prompt:** "Design a food delivery system."
**Core:** restaurants with menus and availability; customers browse, add to cart (one restaurant per cart), place orders; payment; order lifecycle (placed → accepted → preparing → picked up → delivered/cancelled); assign a delivery partner.
**Good-to-have:** coupons; ratings; live tracking; restaurant-side rejection with refund.
**Constraints:** a delivery partner gets one order at a time (unless batching is asked); item stock can run out mid-checkout.
**Key entities:** Customer, Restaurant, Menu, MenuItem, Cart, Order (+ OrderStatus), Payment, DeliveryPartner, AssignmentStrategy, NotificationService.
**Patterns worth seeing:** State for order; Strategy for assignment, pricing and payment; Observer for notifications.
**Failure scenarios:** item goes out of stock after the order is placed; no delivery partner available; restaurant closes; payment timeout.
**Follow-ups:** batch two orders to one partner; scheduled orders; multi-restaurant carts.

## 16. Task Scheduler — Hard — MC, LLD
**Prompt:** "Design a task scheduler."
**Core:** submit tasks to run once at a given time, after a delay, or at a fixed recurring interval; a pool of worker threads executes them; cancel a scheduled task; query status.
**Good-to-have:** priorities; retries with backoff; cron expressions; task dependencies (DAG).
**Constraints:** many tasks; workers must not busy-wait; tasks must not run twice.
**Key entities:** Scheduler, Task/Job, Schedule (OneTime, Delayed, Recurring), TaskStatus, WorkerPool, DelayQueue/PriorityQueue by next run time.
**Patterns worth seeing:** Strategy for schedule types; Command for tasks; correct producer-consumer coordination.
**Failure scenarios:** task throws an exception; recurring task takes longer than its interval; cancel while running; scheduler shutdown with pending tasks.
**Follow-ups:** run across multiple machines without double execution; persist tasks across restarts; DAG dependencies.
