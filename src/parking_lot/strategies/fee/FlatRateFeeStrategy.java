package parking_lot.strategies.fee;

import parking_lot.Ticket;
import parking_lot.interfaces.IFeeStrategy;

public class FlatRateFeeStrategy implements IFeeStrategy {

    private static final double RATE_PER_HOUR = 10.0;

    @Override
    public double calculateFee(Ticket ticket) {
        return billableHours(ticket) * RATE_PER_HOUR;
    }

}
