package org.example;

public class BookingPassengerIdFilter implements IFilter
{
    private int id;

    public BookingPassengerIdFilter(int id) {
        this.id = id;
    }

    @Override
    public boolean matches(Object other) {
        Booking b = (Booking) other;
        return b.getPassengerId() == id;
    }
}
