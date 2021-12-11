package org.example;

public class VehicleTypeFilter implements IFilter
{
    private String type;

    public VehicleTypeFilter(String type) {
        this.type = type;
    }

    @Override
    public boolean matches(Object other) {
        Vehicle v = (Vehicle) other;
        return v.getType().equalsIgnoreCase(type);
    }
}
