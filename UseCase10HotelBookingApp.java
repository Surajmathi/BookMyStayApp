import java.util.*;

class InvalidCancellationException extends Exception {
    public InvalidCancellationException(String message) {
        super(message);
    }
}

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
    private Stack<String> rollbackStack; // Tracks released room IDs

    public RoomInventory() {
        availability = new HashMap<>();
        rollbackStack = new Stack<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 1);
    }

    public void bookRoom(String roomType, String roomId) throws InvalidCancellationException {
        checkUseCase10HotelBookingApp
        availability.put(roomType, availability.get(roomType) - 1);
        System.out.println("Room booked successfully: " + roomType + " (Room ID: " + roomId + ")");
    }

    public void checkAvailability(String roomType) throws InvalidCancellationException {
        if (!availability.containsKey(roomType)) {
            throw new InvalidCancellationException("Invalid room type '" + roomType + "'.");
        }
        if (availability.get(roomType) <= 0) {
            throw new InvalidCancellationException("No rooms available for type '" + roomType + "'.");
        }
    }

    public void cancelRoom(String roomType, String roomId) throws InvalidCancellationException {
        if (!availability.containsKey(roomType)) {
            throw new InvalidCancellationException("Cannot cancel. Invalid room type '" + roomType + "'.");
        }
        // Restore inventory
        availability.put(roomType, availability.get(roomType) + 1);
        rollbackStack.push(roomId);
        System.out.println("Booking cancelled successfully: " + roomType + " (Room ID: " + roomId + ")");
    }

    public void displayAvailability() {
        System.out.println("\n--- Current Room Availability ---");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }

    public Stack<String> getRollbackStack() {
        return rollbackStack;
    }
}

class BookingHistory {

    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation res) {
        reservations.put(res.getReservationId(), res);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        reservations.remove(reservationId);
    }

    public void displayAllReservations() {
        System.out.println("\n--- Booking History ---");
        if (reservations.isEmpty()) {
            System.out.println("No reservations.");
            return;
        }
        for (Reservation res : reservations.values()) {
            res.displayReservation();
        }
    }
}

public class UseCase10HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Welcome to Book My Stay App   ");
        System.out.println("   Hotel Booking System v10.1    ");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        Scanner scanner = new Scanner(System.in);

        Reservation res1 = new Reservation("RES101", "Rahul", "Single Room", "SI201");
        Reservation res2 = new Reservation("RES102", "Priya", "Double Room", "DO305");
        history.addReservation(res1);
        history.addReservation(res2);

        try {
            inventory.bookRoom(res1.getRoomType(), res1.getRoomId());
            inventory.bookRoom(res2.getRoomType(), res2.getRoomId());
        } catch (InvalidCancellationException e) {
            System.out.println(e.getMessage());
        }

        inventory.displayAvailability();
        history.displayAllReservations();

        while (true) {
            System.out.println("\nEnter Reservation ID to cancel or 'exit' to quit:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting cancellation system...");
                break;
            }

            Reservation res = history.getReservation(input);
            if (res == null) {
                System.out.println("Error: Reservation ID '" + input + "' does not exist or already cancelled.");
                continue;
            }

            try {
                inventory.cancelRoom(res.getRoomType(), res.getRoomId());
                history.removeReservation(input);
            } catch (InvalidCancellationException e) {
                System.out.println(e.getMessage());
            }

            inventory.displayAvailability();
            history.displayAllReservations();
        }

        scanner.close();
    }
}