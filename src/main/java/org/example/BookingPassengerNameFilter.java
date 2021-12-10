package org.example;

public class BookingPassengerNameFilter implements IFilter
{
    private String passengerName;
    PassengerStore passengerStore = new PassengerStore("passengers.txt");


    public BookingPassengerNameFilter(String name) {
        this.passengerName = name;
    }

    @Override
    public boolean matches(Object other) {
        Passenger foundPassenger = passengerStore.findPassengerByName(passengerName);
        if (foundPassenger!=null)
        {
            Booking b = (Booking) other;
            return b.getPassengerId() == foundPassenger.getId();
        }
        return false;
    }
}
