package parking_lot.interfaces;

import java.util.List;
import java.util.Optional;

import parking_lot.Floor;
import parking_lot.Spot;
import parking_lot.enums.VehicleType;

public interface IParkingStrategy {
    Optional<Spot> findSpot(List<Floor> floors, VehicleType type);
}
