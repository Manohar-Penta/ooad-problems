package parking_lot.strategies.parking;

import java.util.List;
import java.util.Optional;

import parking_lot.Floor;
import parking_lot.Spot;
import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IParkingStrategy;

public class NearestFitParkingStrategy implements IParkingStrategy {

    @Override
    public Optional<Spot> findSpot(List<Floor> floors, VehicleType type) {
        for (Floor floor : floors) {
            Optional<Spot> spot = floor.findAvailableSpot(type);
            if (spot.isPresent()) {
                return spot;
            }
        }
        return Optional.empty();
    }

}
