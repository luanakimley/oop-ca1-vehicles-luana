package org.example;

public class VehicleLoadSpaceLessThanFilter implements IFilter
{
    private double loadSpace;

    public VehicleLoadSpaceLessThanFilter(double loadSpace) {
        this.loadSpace = loadSpace;
    }

    @Override
    public boolean matches(Object other) {
        Vehicle v = (Vehicle) other;

        if (v instanceof Van) {
            return ((Van) v).getLoadSpace() < loadSpace;
        }
        return false;

    }
}
