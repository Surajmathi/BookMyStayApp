import java.util.*;
import java.util.concurrent.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}

class RoomInventory {

    private Map<String, Integer> availability;
    private Set<String> allocatedRooms;

    public RoomInventory() {
        availability = new HashMap<>();
        allocatedRooms = new HashSet<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 2);
        availability.put("Suite Room", 1);
    }

    public synchronized boolean bookRoom(String roomType, String roomId) {
        if (!availability.containsKey(roomType)) return false;
        int available = availability.get(roomType);
        if (available <= 0) return false;

        // Allocate room
        availability.put(roomType, available - 1);
        allocatedRooms.add(roomId);
        return true;
    }

    public synchronized void displayAvailability() {
        System.out.println("\n--- Room Availability ---");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " rooms available");
        }
    }
}


class BookingTask implements Runnable {
    private RoomInventory inventory;
    private Reservation reservation;

    public BookingTask(RoomInventory inventory, Reservation reservation) {
        this.inventory = inventory;
        this.reservation = reservation;
    }

    @Override
    public void run() {
        boolean success = inventory.bookRoom(reservation.getRoomType(), reservation.getRoomId());
        if (success) {
            System.out.println("SUCCESS: " + reservation.getGuestName() +
                    " booked " + reservation.getRoomType() +
                    " (Room ID: " + reservation.getRoomId() + ")");
        } else {
            System.out.println("FAILED: " + reservation.getGuestName() +
                    " could not book " + reservation.getRoomType() + " - No availability");
        }
    }
}


public class UseCase11HotelBookingApp {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Book My Stay App v11.0 ===");
        RoomInventory inventory = new RoomInventory();

        List<Reservation> requests = Arrays.asList(
                new Reservation("RES201", "Alice", "Single Room", "S101"),
                new Reservation("RES202", "Bob", "Single Room", "S102"),
                new Reservation("RES203", "Charlie", "Double Room", "D201"),
                new Reservation("RES204", "David", "Suite Room", "SU301"),
                new Reservation("RES205", "Eva", "Suite Room", "SU302") // Should fail
        );

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (Reservation res : requests) {
            executor.execute(new BookingTask(inventory, res));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        inventory.displayAvailability();
        System.out.println("=== Simulation Complete ===");
    }
}