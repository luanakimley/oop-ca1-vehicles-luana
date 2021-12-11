package org.example;

public class VehicleNumOfSeatsFilter implements IFilter
{
    private int numOfSeats;

    public VehicleNumOfSeatsFilter(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    @Override
    public boolean matches(Object other) {
        Vehicle v = (Vehicle) other;

        if (v instanceof Car) {
            return ((Car) v).getNumOfSeats() == numOfSeats;
        }
        return false;

    }
}
