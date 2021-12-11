package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleManager implements Serializable
{
    private final List<Vehicle> vehicleList;  // for Car and Van objects

    // Comparators
    VehicleRegistrationComparator vehicleRegistrationComparator = new VehicleRegistrationComparator();
    VehicleLoadSpaceComparator vehicleLoadSpaceComparator = new VehicleLoadSpaceComparator();

    public VehicleManager(String fileName) {
        this.vehicleList = new ArrayList<>();
        loadVehiclesFromFile(fileName);
    }

    public List<Vehicle> getVehicleList()
    {
        return vehicleList;
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

    /**
     * Write Passenger records to text file
     */
    public void saveVehicleDataToFile(String fileName)
    {
        try
        {
            PrintWriter out = new PrintWriter(fileName);
            for (Vehicle v : vehicleList) {
                if (v.getType().equalsIgnoreCase("Car") || v.getType().equalsIgnoreCase("4x4"))
                    out.println(v.getId()+","+v.getType()+","+v.getMake()+","
                            + v.getModel()+","+v.getMilesPerKwH()+","+v.getRegistration()+","
                            + v.getCostPerMile()+","+v.getLastServicedDate().getYear()+","
                            + v.getLastServicedDate().getMonthValue()+","+v.getLastServicedDate().getDayOfMonth()+","
                            + v.getMileage()+","+v.getDepotGPSLocation().getLatitude()+","+v.getDepotGPSLocation().getLongitude()
                            + "," + ((Car) v).getNumOfSeats());
                else
                    out.println(v.getId()+","+v.getType()+","+v.getMake()+","
                            + v.getModel()+","+v.getMilesPerKwH()+","+v.getRegistration()+","
                            + v.getCostPerMile()+","+v.getLastServicedDate().getYear()+","
                            + v.getLastServicedDate().getMonthValue()+","+v.getLastServicedDate().getDayOfMonth()+","
                            + v.getMileage()+","+v.getDepotGPSLocation().getLatitude()+","+v.getDepotGPSLocation().getLongitude()
                            +"," + ((Van) v).getLoadSpace());
            }
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException caught." + exception);
            exception.printStackTrace();
        }
    }

    public void displayAllVehicles()
    {
        System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                "ID",
                "Type",
                "Make",
                "Model",
                "Miles per KwH",
                "Registration",
                "Cost per Mile",
                "Last Serviced Date",
                "Mileage",
                "Depot Location Latitude",
                "Depot Location Longitude",
                "Load Space",
                "Number of Seats");
        System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============   ===============");
        for (Vehicle v : vehicleList)
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                    v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                    v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                    (v instanceof Van ? ((Van) v).getLoadSpace() : "-"),
                    (v instanceof Car ? ((Car) v).getNumOfSeats() : "-")
            );


    }

    public Vehicle findVehicleById(int id) {
        for (Vehicle v : vehicleList) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }

    public void displayVehicleById(int id) {
        Vehicle v = findVehicleById(id);
        if (v != null) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Load Space",
                    "Number of Seats");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============   ===============");
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                    v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                    v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                    (v instanceof Van ? ((Van) v).getLoadSpace() : "-"),
                    (v instanceof Car ? ((Car) v).getNumOfSeats() : "-"));
        }
        else {
            System.out.println("Vehicle not found");
        }
    }

    public Vehicle findVehicleByRegistration(String reg) {
        for (Vehicle v : vehicleList) {
            if(v.getRegistration().equalsIgnoreCase(reg))
                return v;
        }
        return null;
    }
    public void displayVehicleByRegistration(String reg) {
        Vehicle v = findVehicleByRegistration(reg);
        if (v != null) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Load Space",
                    "Number of Seats");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============   ===============");
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                    v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                    v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                    (v instanceof Van ? ((Van) v).getLoadSpace() : "-"),
                    (v instanceof Car ? ((Car) v).getNumOfSeats() : "-"));
        }
        else {
            System.out.println("Vehicle not found");
        }
    }


    public List<Vehicle> findVehiclesByType(String type)
    {
        return filterBy(new VehicleTypeFilter(type));
    }

    public void displayVehiclesByType(String type) {
        List<Vehicle> vehicles = findVehiclesByType(type);
        vehicles.sort(vehicleRegistrationComparator);
        if (!vehicles.isEmpty()) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Load Space",
                    "Number of Seats");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============   ===============");
            for (Vehicle v : vehicles)
            {
                System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s%-10s\n",
                        v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                        v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                        v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                        (v instanceof Van ? ((Van) v).getLoadSpace() : "-"),
                        (v instanceof Car ? ((Car) v).getNumOfSeats() : "-"));
            }
        }
        else {
            System.out.println("Vehicle not found");
        }

    }

    public List<Vehicle> findVehiclesByNumOfSeats(int numOfSeats) {
        return filterBy(new VehicleNumOfSeatsFilter(numOfSeats));
    }

    public void displayVehiclesByNumOfSeats(int numOfSeats) {
        List<Vehicle> vehicles = findVehiclesByNumOfSeats(numOfSeats);
        vehicles.sort(vehicleRegistrationComparator);
        if (!vehicles.isEmpty()) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-10s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Number of Seats");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ===============");
            for (Vehicle v : vehicles)
            {
                System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-10s\n",
                        v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                        v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                        v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                        ((Car) v).getNumOfSeats());
            }
        }
        else {
            System.out.println("Vehicle not found");
        }
    }

    public List<Vehicle> findVehiclesByLoadSpaceLessThan(double loadSpace) {
        return filterBy(new VehicleLoadSpaceLessThanFilter(loadSpace));
    }

    public void displayVehiclesByLoadSpaceLessThan(double loadSpace) {
        List<Vehicle> vehicles = findVehiclesByLoadSpaceLessThan(loadSpace);
        vehicles.sort(vehicleLoadSpaceComparator);
        if (!vehicles.isEmpty()) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Load Space");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============");
            for (Vehicle v : vehicles)
            {
                System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s\n",
                        v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                        v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                        v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                        ((Van) v).getLoadSpace());
            }
        }
        else {
            System.out.println("Vehicle not found");
        }
    }

    public List<Vehicle> findVehiclesByLoadSpaceMoreThan(double loadSpace) {
        return filterBy(new VehicleLoadSpaceMoreThanFilter(loadSpace));
    }

    public void displayVehiclesByLoadSpaceMoreThan(double loadSpace) {
        List<Vehicle> vehicles = findVehiclesByLoadSpaceMoreThan(loadSpace);
        vehicles.sort(vehicleLoadSpaceComparator);
        if (!vehicles.isEmpty()) {
            System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s\n",
                    "ID",
                    "Type",
                    "Make",
                    "Model",
                    "Miles per KwH",
                    "Registration",
                    "Cost per Mile",
                    "Last Serviced Date",
                    "Mileage",
                    "Depot Location Latitude",
                    "Depot Location Longitude",
                    "Load Space");
            System.out.println("=======   =========   ===============   ===============   =================   =================   ===============   ===================   ===========   ========================   =========================   ============");
            for (Vehicle v : vehicles)
            {
                System.out.printf("%-10s%-12s%-18s%-18s%-20s%-20s%-18s%-22s%-14s%-27s%-28s%-15s\n",
                        v.getId(), v.getType(), v.getMake(), v.getModel(), v.getMilesPerKwH(),
                        v.getRegistration(), v.getCostPerMile(), v.getLastServicedDate(),
                        v.getMileage(), v.getDepotGPSLocation().getLatitude(), v.getDepotGPSLocation().getLongitude(),
                        ((Van) v).getLoadSpace());
            }
        }
        else {
            System.out.println("Vehicle not found");
        }
    }


    public List<Vehicle> filterBy(IFilter filter) {
        List<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : this.vehicleList) {
            if (filter.matches(v))    // use matches() method of the filter to match products
                filteredList.add(v);
        }

        return filteredList;
    }
}
