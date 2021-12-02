package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VehicleManager {
    private final ArrayList<Vehicle> vehicleList;  // for Car and Van objects

    public VehicleManager(String fileName) {
        this.vehicleList = new ArrayList<>();
        loadVehiclesFromFile(fileName);
    }

    public void displayAllVehicles() {
        for (Vehicle v : vehicleList)
            System.out.println(v.toString());
    }

    public void loadVehiclesFromFile(String fileName) {
        double loadSpace = 0;
        int numOfSeats = 0;
        try {
            Scanner sc = new Scanner(new File(fileName));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String type = sc.next();  // vehicle type
                String make = sc.next();
                String model = sc.next();
                double milesPerKwH = sc.nextDouble();
                String registration = sc.next();
                double costPerMile = sc.nextDouble();
                int year = sc.nextInt();   // last service date
                int month = sc.nextInt();
                int day = sc.nextInt();
                int mileage = sc.nextInt();
                double latitude = sc.nextDouble();  // Depot GPS location
                double longitude = sc.nextDouble();

                if (type.equalsIgnoreCase("Van") ||
                        type.equalsIgnoreCase("Truck")) {
                    loadSpace = sc.nextDouble();
                }
                else {
                    numOfSeats = sc.nextInt();
                }


                if (type.equalsIgnoreCase("Van") ||
                        type.equalsIgnoreCase("Truck")) {
                    // construct a Van object and add it to the vehicle list
                    vehicleList.add(new Van(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            loadSpace));
                }
                else if (type.equalsIgnoreCase("Car") ||
                        type.equalsIgnoreCase("4x4")) {
                    // construct a Car object and add it to the vehicle list
                    vehicleList.add(new Car(id, type, make, model, milesPerKwH,
                            registration, costPerMile,
                            year, month, day,
                            mileage, latitude, longitude,
                            numOfSeats));
                }
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    //TODO add more functionality as per spec.

    public Vehicle findVehicleById(int id) {
        for (Vehicle v : vehicleList) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }

    public ArrayList<Vehicle> findAllVehicles()
    {
        return vehicleList;
    }

    public Vehicle findVehicleByRegistration(String reg) {
        for (Vehicle v : vehicleList) {
            if(v.getRegistration().equalsIgnoreCase(reg))
                return v;
        }
        return null;
    }

    public ArrayList<Vehicle> findVehiclesByType(String type)
    {
        ArrayList<Vehicle> list = new ArrayList<>();

        for (Vehicle v : vehicleList) {
            if (v.getType().equalsIgnoreCase(type))
                list.add(v);
        }

        return list;
    }
}
