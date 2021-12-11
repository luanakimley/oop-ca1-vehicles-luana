package org.example;

import java.util.Comparator;

public class VehicleLoadSpaceComparator implements Comparator<Vehicle>
{
    public int compare(Vehicle v1, Vehicle v2) {
        return Double.compare(((Van) v1).getLoadSpace(), (((Van) v2).getLoadSpace()));
    }
}
