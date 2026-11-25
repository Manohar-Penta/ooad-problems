package parking_lot;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import parking_lot.enums.VehicleType;
import parking_lot.interfaces.IFeeStrategy;
import parking_lot.interfaces.IParkingStrategy;

public class ParkingLotSystem {
    private final List<Floor> floors;
    private final Map<String, Ticket> activeTickets;
    private final IParkingStrategy parkingStrategy;
    private final IFeeStrategy feeStrategy;
    private final Clock clock;

    public ParkingLotSystem(IParkingStrategy parkingStrategy, IFeeStrategy feeStrategy) {
        this(parkingStrategy, feeStrategy, Clock.systemDefaultZone());
    }

    public ParkingLotSystem(IParkingStrategy parkingStrategy, IFeeStrategy feeStrategy, Clock clock) {
        this.parkingStrategy = Objects.requireNonNull(parkingStrategy);
        this.feeStrategy = Objects.requireNonNull(feeStrategy);
        this.clock = Objects.requireNonNull(clock);
        this.floors = new CopyOnWriteArrayList<>();
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public ParkingLotSystem addFloor(Floor floor) {
        this.floors.add(floor);
        return this;
    }

    public Optional<Ticket> parkVehicle(VehicleType type, String id) {
        while (true) {
            Optional<Spot> foundSpot = parkingStrategy.findSpot(floors, type);
            if (foundSpot.isEmpty()) {
                return Optional.empty();
            }
            // A concurrent request may claim this spot between find and occupy,
            // so the claim is atomic; if we lose the race, retry a fresh search.
            Spot spot = foundSpot.get();
            if (spot.tryOccupy()) {
                Ticket ticket = new Ticket(new Vehicle(type, id), spot, clock.instant());
                activeTickets.put(ticket.getTicketId(), ticket);
                return Optional.of(ticket);
            }
        }
    }

    public Optional<Double> exitVehicle(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            // Unknown ticket, or one that was already used to exit.
            return Optional.empty();
        }
        ticket.setExitTime(clock.instant());
        ticket.getSpot().release();
        return Optional.of(feeStrategy.calculateFee(ticket));
    }
}
