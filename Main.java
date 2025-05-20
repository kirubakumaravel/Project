import java.util.*;

class Flight {
    String flightNumber;
    String source;
    String destination;
    String date;
    int seatsAvailable;

    public Flight(String flightNumber, String source, String destination, String date, int seatsAvailable) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + " | " + source + " -> " + destination + " | Date: " + date + " | Seats: " + seatsAvailable;
    }
}

class Booking {
    String bookingId;
    String passengerName;
    String flightNumber;

    public Booking(String bookingId, String passengerName, String flightNumber) {
        this.bookingId = bookingId;
        this.passengerName = passengerName;
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + " | Passenger: " + passengerName + " | Flight: " + flightNumber;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Flight> flights = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        addSampleFlights();

        while (true) {
            System.out.println("\n===== Flight Management System =====");
            System.out.println("1. View Flights");
            System.out.println("2. Book a Flight");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1 -> viewFlights();
                case 2 -> bookFlight();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addSampleFlights() {
        flights.add(new Flight("AI101", "Delhi", "Mumbai", "2025-06-01", 10));
        flights.add(new Flight("AI202", "Chennai", "Bangalore", "2025-06-02", 5));
        flights.add(new Flight("AI303", "Kolkata", "Delhi", "2025-06-03", 8));
    }

    static void viewFlights() {
        System.out.println("\n--- Available Flights ---");
        for (Flight f : flights) {
            System.out.println(f);
        }
    }

    static void bookFlight() {
        System.out.print("\nEnter Flight Number: ");
        String flightNum = sc.nextLine();
        Flight selectedFlight = null;

        for (Flight f : flights) {
            if (f.flightNumber.equalsIgnoreCase(flightNum)) {
                selectedFlight = f;
                break;
            }
        }

        if (selectedFlight == null) {
            System.out.println("Flight not found.");
            return;
        }

        if (selectedFlight.seatsAvailable <= 0) {
            System.out.println("No seats available on this flight.");
            return;
        }

        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        String bookingId = "BKG" + (bookings.size() + 1);
        bookings.add(new Booking(bookingId, name, flightNum));
        selectedFlight.seatsAvailable--;

        System.out.println("Booking Successful! Your Booking ID: " + bookingId);
    }

    static void cancelBooking() {
        System.out.print("\nEnter Booking ID to cancel: ");
        String bookingId = sc.nextLine();
        Booking bookingToRemove = null;

        for (Booking b : bookings) {
            if (b.bookingId.equalsIgnoreCase(bookingId)) {
                bookingToRemove = b;
                break;
            }
        }

        if (bookingToRemove == null) {
            System.out.println("Booking ID not found.");
            return;
        }

        bookings.remove(bookingToRemove);

        for (Flight f : flights) {
            if (f.flightNumber.equalsIgnoreCase(bookingToRemove.flightNumber)) {
                f.seatsAvailable++;
                break;
            }
        }

        System.out.println("Booking cancelled successfully.");
    }

    static void viewBookings() {
        System.out.println("\n--- All Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }
}