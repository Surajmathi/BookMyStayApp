import java.util.*;

class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " : ₹" + price);
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Service '" + service.getServiceName() +
                "' added to Reservation ID: " + reservationId);
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for Reservation ID: " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation ID: " + reservationId);

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

public class UseCase7HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Welcome to Book My Stay App   ");
        System.out.println("   Hotel Booking System v7.1     ");
        System.out.println("=================================");


        String reservationId = "RES101";

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spaAccess = new AddOnService("Spa Access", 1500);

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, airportPickup);
        serviceManager.addService(reservationId, spaAccess);

        serviceManager.displayServices(reservationId);

        double totalCost = serviceManager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Service Cost: ₹" + totalCost);
    }
}