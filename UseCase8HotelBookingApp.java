import java.util.*;

/**
 * Reservation
 * Represents a confirmed reservation record.
 * @version 8.0
 */
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

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}


/**
 * BookingHistory
 * Stores confirmed bookings in insertion order.
 * @version 8.0
 */
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }
}


/**
 * BookingReportService
 * Generates reports from booking history.
 * @version 8.0
 */
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display booking history
    public void displayBookingHistory() {

        System.out.println("\n--- Booking History ---");

        List<Reservation> reservations = history.getReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }
    }

    // Generate simple report
    public void generateSummaryReport() {

        System.out.println("\n--- Booking Summary Report ---");

        List<Reservation> reservations = history.getReservations();

        System.out.println("Total Reservations: " + reservations.size());

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {

            String roomType = r.getRoomType();

            roomTypeCount.put(roomType,
                    roomTypeCount.getOrDefault(roomType, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}


public class UseCase8HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Welcome to Book My Stay App   ");
        System.out.println("   Hotel Booking System v8.1     ");
        System.out.println("=================================");

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("RES101", "Rahul", "Single Room", "SI201"));
        history.addReservation(new Reservation("RES102", "Priya", "Double Room", "DO305"));
        history.addReservation(new Reservation("RES103", "Arjun", "Suite Room", "SU501"));
        history.addReservation(new Reservation("RES104", "Sneha", "Double Room", "DO306"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayBookingHistory();

        reportService.generateSummaryReport();
    }
}