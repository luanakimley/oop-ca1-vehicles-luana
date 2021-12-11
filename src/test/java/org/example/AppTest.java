package org.example;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for Vehicles App.
 */
public class AppTest 
{
    PassengerStore passengerStore = new PassengerStore("passengers.txt");
    VehicleManager vehicleManager = new VehicleManager("vehicles.txt");
    BookingManager bookingManager = new BookingManager(vehicleManager, passengerStore, "bookings.txt");

    // Test construction of Van object.
    @Test
    public void createVan()
    {
        System.out.println("Van Constructor Test");
        Van van = new Van("Truck","Nissan","Urvan",4,"181MN6538107",
                6.00,2021,05,24,126000,53.2543,-6.4444,240);
        assertEquals("Nissan", van.getMake());
        assertEquals("Urvan", van.getModel());
        assertEquals("181MN6538107", van.getRegistration());
        assertEquals(6.00, van.getCostPerMile(),0.005);
        assertEquals(2021, van.getLastServicedDate().getYear());
        assertEquals(5, van.getLastServicedDate().getMonthValue());
        assertEquals(24, van.getLastServicedDate().getDayOfMonth());
        assertEquals(126000,van.getMileage());
        assertEquals(53.2543, van.getDepotGPSLocation().getLatitude(),0.00005);
        assertEquals(-6.4444, van.getDepotGPSLocation().getLongitude(),0.00005);
        assertEquals(240,van.getLoadSpace(),0.05);
    }

    // Test construction of car object
    @Test
    public void createCar()
    {
        System.out.println("Car Constructor Test");
        Car car = new Car("4x4", "Volkswagen", "Polo", 4, "181DL6138107",
                6.00, 2021, 05, 24, 16000, 53.2543, -6.4444, 5);
        assertEquals("4x4", car.getType());
        assertEquals("Volkswagen", car.getMake());
        assertEquals("Polo", car.getModel());
        assertEquals(4, car.getMilesPerKwH(), 0.01);
        assertEquals("181DL6138107", car.getRegistration());
        assertEquals(6.00, car.getCostPerMile(), 0.01);
        assertEquals(2021, car.getLastServicedDate().getYear());
        assertEquals(05, car.getLastServicedDate().getMonthValue());
        assertEquals(24, car.getLastServicedDate().getDayOfMonth());
        assertEquals(16000, car.getMileage());
        assertEquals(53.2543, car.getDepotGPSLocation().getLatitude(), 0.01);
        assertEquals(-6.4444, car.getDepotGPSLocation().getLongitude(), 0.01);
        assertEquals(5, car.getNumOfSeats());
    }

    @Test
    public void createPassenger()
    {
        System.out.println("Passenger Constructor Test");
        Passenger p = new Passenger("Luana Kimley", "lkimley@gmail.com", "0827162638", 51.7418, -5.988737);

        assertEquals("Luana Kimley", p.getName());
        assertEquals("lkimley@gmail.com", p.getEmail());
        assertEquals("0827162638", p.getPhone());
        assertEquals(51.7418, p.getLocation().getLatitude(), 0.01);
        assertEquals(-5.988737, p.getLocation().getLongitude(), 0.01);
    }

    // Test find vehicle by registration
    @Test
    public void findVehicleByRegistrationTest()
    {
        System.out.println("Find Vehicle by Registration Test");

        Vehicle vehicle = vehicleManager.findVehicleByRegistration("181MN6538107");

        assertEquals("181MN6538107", vehicle.getRegistration());
    }

    // Test make booking
    @Test
    public void makeBookingTest()
    {
        System.out.println("Make Booking Test");
        int prevSize = bookingManager.getBookingList().size();
        bookingManager.makeBooking(101,105,2022,8,22,12,12,51.2132312,-6.21312,51.831283, -6.4123221);
        Booking b = bookingManager.getBookingList().get(prevSize);

        assertEquals(prevSize + 1, bookingManager.getBookingList().size());
        assertEquals(101, b.getPassengerId());
        assertEquals(105, b.getVehicleId());
        assertEquals(2022, b.getBookingDateTime().getYear());
        assertEquals(8, b.getBookingDateTime().getMonthValue());
        assertEquals(22, b.getBookingDateTime().getDayOfMonth());
        assertEquals(12, b.getBookingDateTime().getHour());
        assertEquals(12, b.getBookingDateTime().getMinute());
        assertEquals(51.2132312, b.getStartLocation().getLatitude(), 0.01);
        assertEquals(-6.21312, b.getStartLocation().getLongitude(), 0.01);
        assertEquals(51.831283, b.getEndLocation().getLatitude(), 0.01);
        assertEquals(-6.4123221, b.getEndLocation().getLongitude(), 0.01);
    }

    // Test edit all passenger details
    @Test
    public void editAllPassengerDetailsTest()
    {
        System.out.println("Edit all passenger details test");
        Passenger p = passengerStore.findPassengerById(101);
        passengerStore.editAllPassengerDetails(101, "Luana Kimley", "lkimley@gmail.com", "0827162638", 51.7418, -5.988737);

        assertEquals("Luana Kimley", p.getName());
        assertEquals("lkimley@gmail.com", p.getEmail());
        assertEquals("0827162638", p.getPhone());
        assertEquals(51.7418, p.getLocation().getLatitude(), 0.01);
        assertEquals(-5.988737, p.getLocation().getLongitude(), 0.01);
    }

    @Test
    public void addPassengerTest()
    {
        System.out.println("Add passenger test");
        int prevSize = passengerStore.getAllPassengers().size();
        passengerStore.addPassenger("Luana Kimley", "lkimley@gmail.com", "0812937172", 51.23781, -6.172371);
        Passenger p = passengerStore.getAllPassengers().get(prevSize);

        assertEquals(prevSize + 1, passengerStore.getAllPassengers().size());
        assertEquals("Luana Kimley", p.getName());
        assertEquals("lkimley@gmail.com", p.getEmail());
        assertEquals("0812937172", p.getPhone());
        assertEquals(51.23781, p.getLocation().getLatitude(), 0.01);
        assertEquals(-6.172371, p.getLocation().getLongitude(), 0.01);
    }

    @Test
    public void editBookingTest()
    {
        System.out.println("Edit Booking Test");
        bookingManager.editAllBookingDetails(1002, 101,105,2022,8,22,12,12,51.2132312,-6.21312,51.831283, -6.4123221);
        Booking b = bookingManager.findBookingById(1002);

        assertEquals(101, b.getPassengerId());
        assertEquals(105, b.getVehicleId());
        assertEquals(2022, b.getBookingDateTime().getYear());
        assertEquals(8, b.getBookingDateTime().getMonthValue());
        assertEquals(22, b.getBookingDateTime().getDayOfMonth());
        assertEquals(12, b.getBookingDateTime().getHour());
        assertEquals(12, b.getBookingDateTime().getMinute());
        assertEquals(51.2132312, b.getStartLocation().getLatitude(), 0.01);
        assertEquals(-6.21312, b.getStartLocation().getLongitude(), 0.01);
        assertEquals(51.831283, b.getEndLocation().getLatitude(), 0.01);
        assertEquals(-6.4123221, b.getEndLocation().getLongitude(), 0.01);
    }

    @Test
    public void findVehicleByIdTest()
    {
        System.out.println("Find Vehicle by Id Test");
        Vehicle v = vehicleManager.findVehicleById(105);

        assertEquals(105, v.getId());

    }

    @Test
    public void findVehiclesByNumOfSeatsTest()
    {
        System.out.println("Find Vehicle by Number of Seats Test");

        List<Vehicle> vList = vehicleManager.findVehiclesByNumOfSeats(5);

        for (Vehicle v : vList) {
            if (v instanceof Car)
                assertEquals(5, ((Car) v).getNumOfSeats());
        }
    }

    @Test
    public void findVehiclesByTypeTest()
    {
        System.out.println("Find Vehicle by Type Test");

        List<Vehicle> vList = vehicleManager.findVehiclesByType("Van");

        for (Vehicle v : vList) {
            assertEquals("Van", v.getType());
        }
    }

    @Test
    public void findPassengerByNameTest()
    {
        System.out.println("Find Passenger by Name Test");

        Passenger p = passengerStore.findPassengerByName("Susan Boyle");

        assertEquals("Susan Boyle", p.getName());
    }

    @Test
    public void findPassengerByIdTest()
    {
        System.out.println("Find Passenger by ID Test");

        Passenger p = passengerStore.findPassengerById(102);

        assertEquals(102, p.getId());
    }

    @Test
    public void findBookingByPassengerIdTest()
    {
        System.out.println("Find Booking by Passenger by ID Test");

        List<Booking> bList = bookingManager.findBookingByPassengerId(101);

        for (Booking b : bList) {
            assertEquals(101, b.getPassengerId());
        }
    }

    @Test
    public void findBookingByIdTest()
    {
        System.out.println("Find Booking by ID Test");

        Booking b = bookingManager.findBookingById(1001);

        assertEquals(1001, b.getBookingId());
    }


    @Test
    public void deleteBookingByIdTest()
    {
        System.out.println("Delete Booking by ID Test");

        int prevSize = bookingManager.getBookingList().size();

        bookingManager.deleteBookingById(1001);

        assertEquals(prevSize-1, bookingManager.getBookingList().size());
    }

    @Test
    public void deletePassengerByIdTest()
    {
        System.out.println("Delete Passenger by ID Test");

        int prevSize = passengerStore.getAllPassengers().size();

        passengerStore.deletePassengerById(101);

        assertEquals(prevSize-1, passengerStore.getAllPassengers().size());
    }


}
