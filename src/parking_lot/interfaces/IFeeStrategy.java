package parking_lot.interfaces;

import parking_lot.Ticket;

public interface IFeeStrategy {
    double calculateFee(Ticket ticket);

    /**
     * Whole hours parked, plus one, so any partial hour (and the minimum stay)
     * is billed as a full hour. Shared by all rate strategies.
     */
    default long billableHours(Ticket ticket) {
        return ticket.getParkedDuration().toHours() + 1;
    }
}
