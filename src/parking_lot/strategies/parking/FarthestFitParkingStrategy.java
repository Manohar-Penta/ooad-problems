package parking_lot.strategies.parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import parking_lot.Floor;
import parking_lot.Spot;
import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IParkingStrategy;

public class FarthestFitParkingStrategy implements IParkingStrategy {

    @Override
    public Optional<Spot> findSpot(List<Floor> floors, VehicleType type) {
        List<Floor> reversedFloors = new ArrayList<>(floors);
        Collections.reverse(reversedFloors);

        for (Floor floor : reversedFloors) {
            Optional<Spot> spot = floor.findAvailableSpot(type);
            if (spot.isPresent()) {
                return spot;
            }
        }
        return Optional.empty();
    }

}
