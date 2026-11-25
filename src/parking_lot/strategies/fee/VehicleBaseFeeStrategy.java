package parking_lot.strategies.fee;

import java.util.Map;

import parking_lot.Ticket;
import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IFeeStrategy;

public class VehicleBaseFeeStrategy implements IFeeStrategy {

    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
            VehicleType.TWO_WHEELER_VEHICLE, 10.0,
            VehicleType.FOUR_WHEELER_VEHICLE, 20.0,
            VehicleType.HEAVY_VEHICLE, 30.0,
            VehicleType.SUPER_HEAVY_VEHICLE, 40.0);

    @Override
    public double calculateFee(Ticket parkingTicket) {
        return billableHours(parkingTicket) * HOURLY_RATES.get(parkingTicket.getVehicle().getType());
    }
}
