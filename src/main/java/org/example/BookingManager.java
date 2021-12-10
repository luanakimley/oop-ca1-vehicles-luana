package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BookingManager implements Serializable
{
    private final ArrayList<Booking> bookingList;
    private PassengerStore passengerStore;
    private VehicleManager vehicleManager;

    // Constructor
    public BookingManager(String fileName) {
        this.bookingList = new ArrayList<>();
        loadBookingsFromFile(fileName);
    }

    public void displayAllBookings() {
        for (Booking b : bookingList) {
            System.out.println(b.toString());
        }
    }

    public void addBooking(int passengerId, int vehicleId, int year, int month,
                           int day, int hour, int minute, int second,
                           double startLocationLatitude, double startLocationLongitude, double endLocationLatitude, double endLocationLongitude) {

        Booking booking = new Booking(passengerId, vehicleId, year, month, day, hour, minute, second, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude);
        boolean found = false;

        // test to see if Booking already exists
        for (Booking b : bookingList) {
            if (b.equals(booking)) {
                found = true;
                break;
            }
        }

        if (!found)
            bookingList.add(booking);

    }

    /**
     * Read Bookings from a text file and create and add Booking
     * objects to the booking list.
     */
    private void loadBookingsFromFile(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,:\r\n]+");

            while (sc.hasNext()) {
                int bookingId = sc.nextInt();
                int passengerId = sc.nextInt();
                int vehicleId = sc.nextInt();
                int year = sc.nextInt();
                int month = sc.nextInt();
                int day = sc.nextInt();
                int hour = sc.nextInt();
                int minute = sc.nextInt();
                int second = sc.nextInt();
                double startLocationLatitude = sc.nextDouble();
                double startLocationLongitude = sc.nextDouble();
                double endLocationLatitude = sc.nextDouble();
                double endLocationLongitude = sc.nextDouble();

                // construct a Booking object and add it to the booking list
                bookingList.add(new Booking(bookingId, passengerId, vehicleId, year, month, day, hour, minute, second, startLocationLatitude,startLocationLongitude, endLocationLatitude, endLocationLongitude));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }


    //TODO implement functionality as per specification
    public void displayCurrentBookings() {
        for (Booking b : bookingList) {
            if (b.getBookingDateTime().isAfter(LocalDateTime.now()))
                System.out.println(b);
        }
    }

}
