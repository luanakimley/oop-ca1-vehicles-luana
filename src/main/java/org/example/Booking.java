package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

class Booking implements Serializable
{
    private int bookingId;
    private int passengerId;
    private int vehicleId;
    private LocalDateTime bookingDateTime;
    private LocationGPS startLocation;
    private LocationGPS endLocation;

    private double cost;  //Calculated at booking time

    private IdGenerator idGenerator = IdGenerator.getInstance("next-id-store.txt");  // get access to the id Generator

    public Booking(int passengerId, int vehicleId, int year, int month,
                   int day, int hour, int minute, int second,
                   double startLocationLatitude, double startLocationLongitude, double endLocationLatitude, double endLocationLongitude) {

        this.bookingId = idGenerator.getNextId();
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        this.startLocation = new LocationGPS(startLocationLatitude, startLocationLongitude);
        this.endLocation = new LocationGPS(endLocationLatitude, endLocationLongitude);

    }

    public Booking(int bookingId, int passengerId, int vehicleId, int year, int month,
                   int day, int hour, int minute, int second,
                   double startLocationLatitude, double startLocationLongitude, double endLocationLatitude, double endLocationLongitude) {

        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.vehicleId = vehicleId;
        this.bookingDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        this.startLocation = new LocationGPS(startLocationLatitude, startLocationLongitude);
        this.endLocation = new LocationGPS(endLocationLatitude, endLocationLongitude);

    }

    public int getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(int bookingId)
    {
        this.bookingId = bookingId;
    }

    public int getPassengerId()
    {
        return passengerId;
    }

    public void setPassengerId(int passengerId)
    {
        this.passengerId = passengerId;
    }

    public int getVehicleId()
    {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId)
    {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getBookingDateTime()
    {
        return bookingDateTime;
    }

    public void setBookingDateTime(int year, int month, int day, int hour, int minute, int second)
    {
        this.bookingDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    public LocationGPS getStartLocation()
    {
        return startLocation;
    }

    public void setStartLocation(double startLatitude, double startLongitude)
    {
        this.startLocation = new LocationGPS(startLatitude, startLongitude);
    }

    public LocationGPS getEndLocation()
    {
        return endLocation;
    }

    public void setEndLocation(double endLatitude, double endLongitude)
    {
        this.endLocation = new LocationGPS(endLatitude, endLongitude);
    }

    public double getCost()
    {
        return cost;
    }


    //TODO: see specification


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId == booking.bookingId && passengerId == booking.passengerId && vehicleId == booking.vehicleId && Objects.equals(bookingDateTime, booking.bookingDateTime);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(bookingId, passengerId, vehicleId, bookingDateTime);
    }

    @Override
    public String toString()
    {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", vehicleId=" + vehicleId +
                ", bookingDateTime=" + bookingDateTime +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", cost=" + cost +
                '}';
    }
}
