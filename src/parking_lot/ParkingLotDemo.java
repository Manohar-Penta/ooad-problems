package parking_lot;

import java.util.Optional;

import parking_lot.enums.VehicleType;
import parking_lot.strategies.fee.VehicleBaseFeeStrategy;
import parking_lot.strategies.parking.NearestFitParkingStrategy;

public class ParkingLotDemo {
    public static void main(String[] args) {
        // Composition root: create the one system, injecting its collaborators.
        ParkingLotSystem parkingLot = new ParkingLotSystem(
                new NearestFitParkingStrategy(),
                new VehicleBaseFeeStrategy());

        // 1. Build the parking lot with floors and spots
        Floor floor1 = Floor.builder(1)
                .addVehicle(VehicleType.TWO_WHEELER_VEHICLE, 1)
                .addVehicle(VehicleType.FOUR_WHEELER_VEHICLE, 1)
                .addVehicle(VehicleType.HEAVY_VEHICLE, 1)
                .build();

        Floor floor2 = Floor.builder(2)
                .addVehicle(VehicleType.TWO_WHEELER_VEHICLE, 1)
                .addVehicle(VehicleType.FOUR_WHEELER_VEHICLE, 2)
                .build();

        parkingLot.addFloor(floor1).addFloor(floor2);

        System.out.println("\n--- Availability before parking ---");
        floor1.displayAvailability();
        floor2.displayAvailability();

        // 2. Exact-fit parking fills Floor 1 (one spot of each type)
        System.out.println("\n--- Vehicle Entries ---");
        Optional<Ticket> bikeTicket = parkingLot.parkVehicle(VehicleType.TWO_WHEELER_VEHICLE, "B-123");
        printTicket("Bike B-123", bikeTicket);
        printTicket("Car C-456", parkingLot.parkVehicle(VehicleType.FOUR_WHEELER_VEHICLE, "C-456"));
        printTicket("Truck T-789", parkingLot.parkVehicle(VehicleType.HEAVY_VEHICLE, "T-789"));

        // 3. Floor 1 is full, so the next bike overflows to Floor 2's bike spot
        printTicket("Bike B-456", parkingLot.parkVehicle(VehicleType.TWO_WHEELER_VEHICLE, "B-456"));

        // 4. All bike spots are now taken, so this bike falls back to a larger
        // (four-wheeler) spot instead of being rejected.
        printTicket("Bike B-000", parkingLot.parkVehicle(VehicleType.TWO_WHEELER_VEHICLE, "B-000"));

        // 5. No free spot can fit a heavy vehicle anymore, so it is rejected.
        printTicket("Truck T-000", parkingLot.parkVehicle(VehicleType.HEAVY_VEHICLE, "T-000"));

        System.out.println("\n--- Availability after parking ---");
        floor1.displayAvailability();
        floor2.displayAvailability();

        // 6. Exit a vehicle, calculate the fee, and reject a re-used ticket
        System.out.println("\n--- Vehicle Exits ---");
        if (bikeTicket.isPresent()) {
            String ticketId = bikeTicket.get().getTicketId();

            parkingLot.exitVehicle(ticketId)
                    .ifPresent(fee -> System.out.printf("Bike B-123 unparked. Fee: $%.2f\n", fee));

            // Re-submitting the same ticket is rejected instead of double-charging.
            if (parkingLot.exitVehicle(ticketId).isEmpty()) {
                System.out.println("Bike B-123 re-exit rejected: ticket is not active.");
            }
        }

        // 7. The freed bike spot on Floor 1 is reused (exact fit) by the next bike
        System.out.println("\n--- Re-entry after a spot frees up ---");
        printTicket("Bike B-777", parkingLot.parkVehicle(VehicleType.TWO_WHEELER_VEHICLE, "B-777"));
    }

    private static void printTicket(String label, Optional<Ticket> ticket) {
        if (ticket.isPresent()) {
            Spot spot = ticket.get().getSpot();
            System.out.printf("%s parked in a %s spot. Ticket: %s\n",
                    label, spot.getSpotType(), ticket.get().getTicketId());
        } else {
            System.out.printf("%s could not be parked: no spot available that fits.\n", label);
        }
    }
}
