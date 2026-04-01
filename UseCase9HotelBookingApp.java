import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}


class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        // Initialize sample inventory
        availability.put("Single Room", 2);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Error: Invalid room type '" + roomType + "'.");
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        if (availability.get(roomType) <= 0) {
            throw new InvalidBookingException("Error: No available rooms for type '" + roomType + "'.");
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        checkAvailability(roomType);
        availability.put(roomType, availability.get(roomType) - 1);
        System.out.println("Room booked successfully: " + roomType);
    }

    public void displayAvailability() {
        System.out.println("\n--- Current Room Availability ---");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


public class UseCase9HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Welcome to Book My Stay App   ");
        System.out.println("   Hotel Booking System v9.1     ");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter room type to book (Single Room / Double Room / Suite Room) or 'exit' to quit:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting booking system...");
                break;
            }

            try {
                inventory.bookRoom(input);
            } catch (InvalidBookingException e) {
                System.out.println(e.getMessage());
            }

            inventory.displayAvailability();
        }

        scanner.close();
    }
}