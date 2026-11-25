# Desing a Parking Lot System

## Questions

1. Do we have multiple floors on which we have multiple parking spots?
2. Is a parking spot is specific to a vehicle type?
3. If yes, what is the type dependent on? Two wheeler, four wheeler, etc
4. Are we assigning a parking spot when a vehicle enters the lot?
5. Or vehicle has to find the parking spot?
6. Are the number of floors and parking spots fixed for this system?
7. Or are we adding them dynamically? \*\*
8.

## Requirements

1. We have Parking spots on each floor.
2. The parking lot should support different types of vehicles, such as cars, motorcycles, and trucks.
3. Each parking spot should be able to accommodate a specific type of vehicle.
4. The vehicle when enters should register itself(vehicle number, type), a parking spot is searched for it and alloted a ticket for the same.
5. The vehicle when exiting, should share the ticket to calculate the cost. Vehicle pays the cost and exits the system. Only then the spot is released.
6. The system should handle multiple entry and exit points and support concurrent access.
7. The system should track the availability of parking spots and provide real-time information to customers.

## Entities

- spots(class)
  - data: occupied
  - rules: allocate when free
- vehicles(class)
- floor(data)
- ticket(cost)
  - data: entry time, exit time, cost, vehicle
  - rules: cost calculation logic
- cost(int)
- ParkingLot System(class)

## Relations

- System has floors
- Floor has spots
- vehicle uses spots
- vehicle uses system
