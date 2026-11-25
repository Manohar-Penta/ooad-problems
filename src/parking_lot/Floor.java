package parking_lot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IFloorSpotsBuilder;

public class Floor {
    private int number;
    private Map<VehicleType, List<Spot>> spots;

    private Floor(int number, Map<VehicleType, List<Spot>> spots) {
        this.spots = spots;
        this.number = number;
    }

    public Optional<Spot> findAvailableSpot(VehicleType type) {
        // Best fit: among all free spots that can fit the vehicle, pick the
        // smallest, so larger spots stay open for vehicles that truly need them.
        // This is a lock-free read; the actual claim is atomic (see Spot.tryOccupy).
        return spots.values().stream()
                .flatMap(List::stream)
                .filter(spot -> !spot.isOccupied() && spot.canFitVehicle(type))
                .min(Comparator.comparingInt(spot -> spot.getSpotType().ordinal()));
    }

    public void displayAvailability() {
        System.out.printf("--- Floor %d Availability ---\n", number);

        for (VehicleType type : VehicleType.values()) {
            long freeSpots = spots.getOrDefault(type, List.of()).stream()
                    .filter(spot -> !spot.isOccupied())
                    .count();
            System.out.printf("  %s spots: %d\n", type, freeSpots);
        }
    }

    public static IFloorSpotsBuilder builder(int number) {
        return new FloorBuilder(number);
    }

    static class FloorBuilder implements IFloorSpotsBuilder {
        private Map<VehicleType, List<Spot>> spots;
        private int number;

        public FloorBuilder(int number) {
            this.number = number;
            spots = new HashMap<>();
        }

        public Floor build() {
            return new Floor(number, this.spots);
        }

        public FloorBuilder addVehicle(VehicleType type, int count) {
            if (spots.get(type) == null) {
                spots.put(type, new ArrayList<>());
            }
            for (int i = 0; i < count; i++) {
                spots.get(type).add(new Spot(type));
            }
            return this;
        }
    }

}
