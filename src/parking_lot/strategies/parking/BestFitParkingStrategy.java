package parking_lot.strategies.parking;

import java.util.List;
import java.util.Optional;

import parking_lot.Floor;
import parking_lot.Spot;
import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IParkingStrategy;

public class BestFitParkingStrategy implements IParkingStrategy {

    @Override
    public Optional<Spot> findSpot(List<Floor> floors, VehicleType type) {
        Optional<Spot> bestSpot = Optional.empty();

        for (Floor floor : floors) {
            Optional<Spot> spotOnThisFloor = floor.findAvailableSpot(type);

            if (spotOnThisFloor.isPresent()) {
                if (bestSpot.isEmpty()) {
                    // If this is the first spot we've found, it's the best one so far.
                    bestSpot = spotOnThisFloor;
                } else {
                    // A smaller spot size enum ordinal means a tighter fit.
                    if (spotOnThisFloor.get().getSpotType().ordinal() < bestSpot.get().getSpotType().ordinal()) {
                        bestSpot = spotOnThisFloor;
                    }
                }
            }
        }
        return bestSpot;
    }
}
