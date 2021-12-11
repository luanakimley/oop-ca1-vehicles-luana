package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingManager implements Serializable
{
    private final List<Booking> bookingList;
    private final PassengerStore passengerStore;
    private final VehicleManager vehicleManager;

    // Comparators
    private final BookingDateTimeComparator bookingDateTimeComparator = new BookingDateTimeComparator();


    // Constructor
    public BookingManager(VehicleManager vehicleManager, PassengerStore passengerStore, String fileName) {
        this.vehicleManager = vehicleManager;
        this.passengerStore = passengerStore;
        this.bookingList = new ArrayList<>();


        loadBookingsFromFile(fileName);
    }

    public void displayAllBookings() {
        System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-16s\n", "Booking ID",
                "Passenger ID",
                "Vehicle ID",
                "Booking Date Time",
                "Start Location Latitude",
                "Start Location Longitude",
                "End Location Latitude",
                "End Location Longitude",
                "Cost");
        System.out.println("================    ================    ================    ========================    ============================    ============================    ============================    =========================    ===========");

        for (Booking b : bookingList) {
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-10.2f\n",
                    b.getBookingId(),
                    b.getPassengerId(),
                    b.getVehicleId(),
                    b.getBookingDateTime(),
                    b.getStartLocation().getLatitude(),
                    b.getStartLocation().getLongitude(),
                    b.getEndLocation().getLatitude(),
                    b.getEndLocation().getLongitude(),
                    b.getCost()
            );
        }
    }

    public List<Booking> getBookingList() { return this.bookingList; }


    public Booking makeBooking(int passengerId, int vehicleId, int year, int month,
                           int day, int hour, int minute,
                           double startLocationLatitude, double startLocationLongitude, double endLocationLatitude, double endLocationLongitude) {


        if (passengerStore.findPassengerById(passengerId) != null) {
            if (findVehicleById(vehicleId) != null) {
                if (checkAvailability(vehicleId, LocalDateTime.of(year, month, day, hour, minute))) {
                    double cost = calculateCost(vehicleId, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude);
                    Booking newBooking = new Booking(passengerId, vehicleId, year, month, day, hour, minute, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude, cost);
                    bookingList.add(newBooking);
                    return newBooking;
                }
                else {
                    System.out.println("Booking unsuccessful - Vehicle not available");
                }
            }
            else {
                System.out.println("Booking unsuccessful - No such vehicle");
            }
        }
        else
        {
            System.out.println("Booking unsuccessful - No such passenger");
        }
        return null;

    }

    public void addPassenger(String name, String email, String phone, double latitude, double longitude) {
        passengerStore.addPassenger(name, email, phone, latitude, longitude);
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
                double startLocationLatitude = sc.nextDouble();
                double startLocationLongitude = sc.nextDouble();
                double endLocationLatitude = sc.nextDouble();
                double endLocationLongitude = sc.nextDouble();
                double cost = calculateCost(vehicleId, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude);

                // construct a Booking object and add it to the booking list
                bookingList.add(new Booking(bookingId, passengerId, vehicleId, year, month, day, hour, minute, startLocationLatitude,startLocationLongitude, endLocationLatitude, endLocationLongitude, cost));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    /**
     * Write Passenger records to text file
     */
    public void saveBookingDataToFile(String fileName)
    {
        try
        {
            PrintWriter out = new PrintWriter(fileName);
            for (Booking b : bookingList) {
                out.println(b.getBookingId()+","+b.getPassengerId()+","+b.getVehicleId()+","
                        +b.getBookingDateTime().getYear()+","+b.getBookingDateTime().getMonthValue()
                        +","+b.getBookingDateTime().getDayOfMonth() +","
                        +b.getBookingDateTime().getHour()+":"+b.getBookingDateTime().getMinute()
                        +","+b.getStartLocation().getLatitude()+","+b.getStartLocation().getLongitude()
                        +","+b.getEndLocation().getLatitude()+","+b.getEndLocation().getLongitude());
            }
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException caught." + exception);
            exception.printStackTrace();
        }
    }


    //TODO implement functionality as per specification
    public List<Booking> findCurrentBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (Booking b : bookingList) {
            if (b.getBookingDateTime().isAfter(LocalDateTime.now())) {
                bookings.add(b);
            }
        }
        return bookings;
    }

    public void displayCurrentBookings() {
        bookingList.sort(bookingDateTimeComparator);
        System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-16s\n", "Booking ID",
                "Passenger ID",
                "Vehicle ID",
                "Booking Date Time",
                "Start Location Latitude",
                "Start Location Longitude",
                "End Location Latitude",
                "End Location Longitude",
                "Cost");
        System.out.println("================    ================    ================    ========================    ============================    ============================    ============================    =========================    ===========");
        for (Booking b : findCurrentBookings()) {
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-10.2f\n",
                    b.getBookingId(),
                    b.getPassengerId(),
                    b.getVehicleId(),
                    b.getBookingDateTime(),
                    b.getStartLocation().getLatitude(),
                    b.getStartLocation().getLongitude(),
                    b.getEndLocation().getLatitude(),
                    b.getEndLocation().getLongitude(),
                    b.getCost()
            );
        }
    }

    public List<Booking> findBookingByPassengerId(int passengerId) {
        return filterBy(new BookingPassengerIdFilter(passengerId));
    }

    public List<Booking> filterBy(IFilter filter) {
        List<Booking> filteredList = new ArrayList<>();
        for (Booking b : this.bookingList) {
            if (filter.matches(b))    // use matches() method of the filter to match products
                filteredList.add(b);
        }

        return filteredList;
    }

    public void displayBookingByPassengerId(int passengerId) {
        List<Booking> bookings = findBookingByPassengerId(passengerId);
        bookingList.sort(bookingDateTimeComparator);
        if (!bookings.isEmpty()){
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-16s\n", "Booking ID",
                    "Passenger ID",
                    "Vehicle ID",
                    "Booking Date Time",
                    "Start Location Latitude",
                    "Start Location Longitude",
                    "End Location Latitude",
                    "End Location Longitude",
                    "Cost");
            System.out.println("================    ================    ================    ========================    ============================    ============================    ============================    =========================    ===========");

            for (Booking b : bookings) {
                System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-10.2f\n",
                        b.getBookingId(),
                        b.getPassengerId(),
                        b.getVehicleId(),
                        b.getBookingDateTime(),
                        b.getStartLocation().getLatitude(),
                        b.getStartLocation().getLongitude(),
                        b.getEndLocation().getLatitude(),
                        b.getEndLocation().getLongitude(),
                        b.getCost()
                );
        }}
        else {
            System.out.println("Booking not found");
        }
    }

    public List<Booking> findBookingByPassengerName(String passengerName) {
        return filterBy(new BookingPassengerNameFilter(passengerName));
    }

    public void displayBookingByPassengerName(String passengerName) {
        bookingList.sort(bookingDateTimeComparator);
        List<Booking> bookings = findBookingByPassengerName(passengerName);

        if (!bookings.isEmpty())
        {
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-16s\n", "Booking ID",
                    "Passenger ID",
                    "Vehicle ID",
                    "Booking Date Time",
                    "Start Location Latitude",
                    "Start Location Longitude",
                    "End Location Latitude",
                    "End Location Longitude",
                    "Cost");
            System.out.println("================    ================    ================    ========================    ============================    ============================    ============================    =========================    ===========");

            for (Booking b : bookings)
            {
                System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-10.2f\n",
                        b.getBookingId(),
                        b.getPassengerId(),
                        b.getVehicleId(),
                        b.getBookingDateTime(),
                        b.getStartLocation().getLatitude(),
                        b.getStartLocation().getLongitude(),
                        b.getEndLocation().getLatitude(),
                        b.getEndLocation().getLongitude(),
                        b.getCost()
                );
            }
        } else {
            System.out.println("Booking not found");
        }
    }


    public void displayBookingByBookingId(int bookingId) {
        Booking b = findBookingById(bookingId);
        if (b != null)
        {
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-16s\n", "Booking ID",
                    "Passenger ID",
                    "Vehicle ID",
                    "Booking Date Time",
                    "Start Location Latitude",
                    "Start Location Longitude",
                    "End Location Latitude",
                    "End Location Longitude",
                    "Cost");
            System.out.println("================    ================    ================    ========================    ============================    ============================    ============================    =========================    ===========");
            System.out.printf("%-20s%-20s%-20s%-28s%-32s%-32s%-32s%-29s%-10.2f\n",
                    b.getBookingId(),
                    b.getPassengerId(),
                    b.getVehicleId(),
                    b.getBookingDateTime(),
                    b.getStartLocation().getLatitude(),
                    b.getStartLocation().getLongitude(),
                    b.getEndLocation().getLatitude(),
                    b.getEndLocation().getLongitude(),
                    b.getCost()
            );
        } else {
            System.out.println("Booking not found");
        }
    }


    public double calculateAverageLengthOfBookingJourneys() {
        double total = 0;

        for (Booking b : bookingList) {
            total += b.getCost();
        }

        return total / bookingList.size();
    }

    public double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
        final int R = 6371; // Radius of the earth

        double latitudeDistance = Math.toRadians(endLat - startLat);
        double longitudeDistance = Math.toRadians(endLong - startLong);

        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = Math.pow((R * c ), 2);

        return Math.sqrt(distance) /  1.609;
    }

    public double calculateCost(int vehicleId, double startLat, double startLong, double endLat, double endLong) {
        Vehicle v = findVehicleById(vehicleId);

        double totalDistance = calculateDistance(v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(), startLat, startLong)
                + calculateDistance(startLat, startLong, endLat, endLong)
                + calculateDistance(endLat, endLong, v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude());

        double cost = totalDistance * v.getCostPerMile();

        return (double) Math.round(cost*100)/100;

    }


    public Booking findBookingById(int bookingId) {
        for (Booking b : bookingList) {
            if(b.getBookingId() == bookingId)
                return b;
        }
        return null;
    }

    public void deleteBookingById(int bookingId) {
        bookingList.removeIf(b -> b.equals(findBookingById(bookingId)));
    }

    public boolean checkAvailability(int vehicleId, LocalDateTime bookingDateTime) {
        // 1 booking is for 24 hours
        boolean available = true;

        for (Booking b : bookingList) {
            if (vehicleId == b.getVehicleId())
            {
                if (bookingDateTime.isAfter(b.getBookingDateTime()))
                {
                    if (bookingDateTime.isBefore(b.getBookingDateTime().plusDays(1)))
                        available = false;
                }
                else if (bookingDateTime.isBefore(b.getBookingDateTime()))
                {
                    if (bookingDateTime.plusDays(1).isAfter(b.getBookingDateTime()))
                    {
                        available = false;
                    }
                }
                else
                {
                    available = false;
                }
            }
        }

        return available;
    }

    public void editAllBookingDetails(int bookingId, int passengerId, int vehicleId, int year, int month,
                                      int day, int hour, int minute,
                                      double startLocationLatitude, double startLocationLongitude, double endLocationLatitude, double endLocationLongitude)
    {
        double cost = calculateCost(vehicleId, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude);
        Booking newBooking = new Booking(passengerId, vehicleId, year, month, day, hour, minute, startLocationLatitude, startLocationLongitude, endLocationLatitude, endLocationLongitude, cost);
        Booking foundBooking = findBookingById(bookingId);

        boolean duplicate = false;

        if (bookingList.contains(newBooking)) {
            duplicate = true;
            System.out.println("Booking not edited - booking already exists in the system");
        }
        if (!duplicate) {
            if (checkAvailability(vehicleId, LocalDateTime.of(year,month,day,hour,minute)))
            {
                foundBooking.setPassengerId(passengerId);
                foundBooking.setVehicleId(vehicleId);
                foundBooking.setBookingDateTime(year, month, day, hour, minute);
                foundBooking.setStartLocation(startLocationLatitude, startLocationLongitude);
                foundBooking.setEndLocation(endLocationLatitude, endLocationLongitude);
                foundBooking.setCost(cost);
                System.out.println("Booking edited, here is your updated booking details:");
                displayBookingByBookingId(bookingId);
            }
            else {
                System.out.println("Booking not edited - vehicle not available");
            }
        }

    }

    public void editPassengerId(int bookingId, int passengerId) {
        Booking foundBooking = findBookingById(bookingId);
        Booking newBooking = new Booking(passengerId, foundBooking.getVehicleId(),
                foundBooking.getBookingDateTime().getYear(), foundBooking.getBookingDateTime().getMonthValue(), foundBooking.getBookingDateTime().getDayOfMonth(),
                foundBooking.getBookingDateTime().getHour(), foundBooking.getBookingDateTime().getMinute(), foundBooking.getStartLocation().getLatitude(), foundBooking.getStartLocation().getLongitude(),
                foundBooking.getEndLocation().getLatitude(), foundBooking.getEndLocation().getLongitude(), foundBooking.getCost());

        if (passengerStore.findPassengerById(passengerId) == null)
        {
            System.out.println("Booking not edited - passenger doesn't exist in the system");
            if (bookingList.contains(newBooking))
            {
                System.out.println("Booking not edited - booking already exists in the system");
            }
        }
        else
        {
            foundBooking.setPassengerId(passengerId);
            System.out.println("Booking edited, here is your updated booking details:");
            displayBookingByBookingId(bookingId);
        }
    }

    public void editVehicleId(int bookingId, int vehicleId) {
        Booking foundBooking = findBookingById(bookingId);
        Booking newBooking = new Booking(foundBooking.getPassengerId(), vehicleId,
                foundBooking.getBookingDateTime().getYear(), foundBooking.getBookingDateTime().getMonthValue(), foundBooking.getBookingDateTime().getDayOfMonth(),
                foundBooking.getBookingDateTime().getHour(), foundBooking.getBookingDateTime().getMinute(), foundBooking.getStartLocation().getLatitude(), foundBooking.getStartLocation().getLongitude(),
                foundBooking.getEndLocation().getLatitude(), foundBooking.getEndLocation().getLongitude(), foundBooking.getCost());

        if (findVehicleById(vehicleId) != null) {
            if(checkAvailability(vehicleId, foundBooking.getBookingDateTime())) {
                if (!bookingList.contains(newBooking)) {
                    foundBooking.setVehicleId(vehicleId);
                    System.out.println("Booking edited, here is your updated booking details:");
                    displayBookingByBookingId(bookingId);
                }
                else {
                    System.out.println("Booking not edited - booking already exists in the system");
                }
            }
            else {
                System.out.println("Booking not edited - vehicle is not available");

            }
        }
        else {
            System.out.println("Booking not edited - vehicle doesn't exist in the system");
        }
    }

    public void editBookingDateTime(int bookingId, int year, int month, int day, int hour, int minute) {
        Booking foundBooking = findBookingById(bookingId);

        if(checkAvailability(foundBooking.getVehicleId(), LocalDateTime.of(year, month, day, hour, minute))) {
            foundBooking.setBookingDateTime(year, month, day, hour, minute);
            System.out.println("Booking edited, here is your updated booking details:");
            displayBookingByBookingId(bookingId);
        }
        else {
            System.out.println("Booking not edited - vehicle is not available");
        }
    }



    public void editBookingStartLocation(int bookingId, double startLatitude, double startLongitude) {
        Booking foundBooking = findBookingById(bookingId);
        foundBooking.setStartLocation(startLatitude, startLongitude);
        foundBooking.setCost(calculateCost(foundBooking.getVehicleId(), startLatitude, startLongitude,
                foundBooking.getEndLocation().getLatitude(), foundBooking.getEndLocation().getLongitude()));
        System.out.println("Booking edited, here is your updated booking details:");
        displayBookingByBookingId(bookingId);
    }

    public void editBookingEndLocation(int bookingId, double endLatitude, double endLongitude) {
        Booking foundBooking = findBookingById(bookingId);
        foundBooking.setEndLocation(endLatitude, endLongitude);
        foundBooking.setCost(calculateCost(foundBooking.getVehicleId(), foundBooking.getStartLocation().getLatitude(), foundBooking.getStartLocation().getLongitude(),
                endLatitude, endLongitude));
        System.out.println("Booking edited, here is your updated booking details:");
        displayBookingByBookingId(bookingId);

    }

    public void displayAllPassengers() {
        passengerStore.displayAllPassengers();
    }

    public void displayAllVehicles() {
        vehicleManager.displayAllVehicles();
    }

    public Vehicle findVehicleById(int id) {
        return vehicleManager.findVehicleById(id);
    }

    public void displayVehicleById(int id) {
        vehicleManager.displayVehicleById(id);
    }


    public void displayVehicleByRegistration(String reg) {
        vehicleManager.displayVehicleByRegistration(reg);
    }


    public void displayVehiclesByType(String type) {
        vehicleManager.displayVehiclesByType(type);
    }

    public void displayVehiclesByNumOfSeats(int numOfSeats) {
        vehicleManager.displayVehiclesByNumOfSeats(numOfSeats);
    }


    public void displayVehiclesByLoadSpaceLessThan(double loadSpace) {
        vehicleManager.displayVehiclesByLoadSpaceLessThan(loadSpace);
    }

    public void displayVehiclesByLoadSpaceMoreThan(double loadSpace) {
        vehicleManager.displayVehiclesByLoadSpaceMoreThan(loadSpace);
    }

    public Passenger findPassengerByName(String name) {
        return passengerStore.findPassengerByName(name);
    }

    public Passenger findPassengerById(int id) {
        return passengerStore.findPassengerById(id);
    }

    public void displayPassengerById(int id) {
        passengerStore.displayPassengerById(id);
    }




    public void editAllPassengerDetails(int id, String name, String email, String phone,
                                        double latitude, double longitude) {
        passengerStore.editAllPassengerDetails(id, name, email, phone, latitude, longitude);
    }

    public void editPassengerName(int id, String name) {
        passengerStore.editPassengerName(id, name);
    }


    public void editPassengerEmail (int id, String email) {
        passengerStore.editPassengerEmail(id, email);
    }

    public void editPassengerPhone (int id,String phone) {
        passengerStore.editPassengerPhone(id, phone);
    }

    public void editPassengerLocation (int id, double latitude, double longitude) {
        passengerStore.editPassengerLocation(id, latitude, longitude);
    }


    public void deletePassengerById(int id) {
        passengerStore.deletePassengerById(id);
    }

    public void deletePassengerByName(String name) {
        passengerStore.deletePassengerByName(name);
    }



}
