package org.example;

import java.io.*;
import java.util.*;

public class PassengerStore implements Serializable
{

    private final List<Passenger> passengerList;

    // Comparators
    private final PassengerNameComparator passengerNameComparator = new PassengerNameComparator();

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    public List<Passenger> getAllPassengers() {
        return this.passengerList;
    }

    /**
     * Read Passenger records from a text file and create and add Passenger
     * objects to the PassengerStore.
     */
    private void loadPassengerDataFromFile(String filename) {

        try {
            Scanner sc = new Scanner(new File(filename));
//           Delimiter: set the delimiter to be a comma character ","
//                    or a carriage-return '\r', or a newline '\n'
            sc.useDelimiter("[,\r\n]+");

            while (sc.hasNext()) {
                int id = sc.nextInt();
                String name = sc.next();
                String email = sc.next();
                String phone = sc.next();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();

                // construct a Passenger object and add it to the passenger list
                passengerList.add(new Passenger(id, name, email, phone, latitude, longitude));
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Exception thrown. " + e);
        }
    }

    // TODO - see functional spec for details of code to add
    public void displayAllPassengers() {
        passengerList.sort(passengerNameComparator);
        System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n","ID", "Name", "Email", "Phone", "Home Latitude", "Home Longitude");
        System.out.println("====    ========================    ================================    ====================    ================    ===============");
        for (Passenger p : this.passengerList) {
            System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n",
                    p.getId(),
                    p.getName(),
                    p.getEmail(),
                    p.getPhone(),
                    p.getLocation().getLatitude(),
                    p.getLocation().getLongitude()
            );
        }
    }

    public void addPassenger(String name, String email, String phone, double latitude, double longitude) {
        Passenger passenger = new Passenger(name, email, phone, latitude, longitude);

        // test to see if Passenger already exists
        boolean found = passengerList.contains(passenger);

        if (!found)
        {
            passengerList.add(passenger);
            System.out.println("Passenger added to the system");
        }
        else
        {
            System.out.println("Passenger not added - passenger already exists");
        }
    }

    /**
     * Write Passenger records to text file
     */
    public void savePassengerDataToFile(String fileName)
    {
        try
        {
            PrintWriter out = new PrintWriter(fileName);
            for (Passenger p : passengerList) {
                out.println(p.getId()+","+p.getName()+","+p.getEmail()+","+p.getPhone()+","+p.getLocation().getLatitude()+","+p.getLocation().getLongitude());
            }
            out.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException caught." + exception);
            exception.printStackTrace();
        }
    }

    public Passenger findPassengerByName(String name) {
        for (Passenger p : passengerList) {
            if(p.getName().equalsIgnoreCase(name.toLowerCase()))
                return p;
        }
        return null;
    }

    public Passenger findPassengerById(int id) {
        for (Passenger p : passengerList) {
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    public void displayPassengerById(int id) {
        Passenger foundPassenger = findPassengerById(id);
        System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n","ID", "Name", "Email", "Phone", "Home Latitude", "Home Longitude");
        System.out.println("====    ========================    ================================    ====================    ================    ===============");
        System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n",
                foundPassenger.getId(),
                foundPassenger.getName(),
                foundPassenger.getEmail(),
                foundPassenger.getPhone(),
                foundPassenger.getLocation().getLatitude(),
                foundPassenger.getLocation().getLongitude()
        );
    }

    public void displayPassengerByName(String name) {
        Passenger foundPassenger = findPassengerByName(name);
        if (foundPassenger!=null)
        {
            System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n", "ID", "Name", "Email", "Phone", "Home Latitude", "Home Longitude");
            System.out.println("====    ========================    ================================    ====================    ================    ===============");
            System.out.printf("%-8s%-28s%-36s%-24s%-20s%-1s\n",
                    foundPassenger.getId(),
                    foundPassenger.getName(),
                    foundPassenger.getEmail(),
                    foundPassenger.getPhone(),
                    foundPassenger.getLocation().getLatitude(),
                    foundPassenger.getLocation().getLongitude()
            );
        }
        else {
            System.out.println("Passenger not found");
        }
    }

    // Edit Methods

    public void editAllPassengerDetails(int id, String name, String email, String phone,
                                        double latitude, double longitude) {
        Passenger newPassenger = new Passenger(name, email, phone, latitude, longitude);
        Passenger foundPassenger = findPassengerById(id);
        boolean duplicate = false;

        if (passengerList.contains(newPassenger)) {
            duplicate = true;
            System.out.println("Passenger not edited - passenger already exists in the system");
        }
        if (!duplicate) {
            foundPassenger.setName(name);
            foundPassenger.setEmail(email);
            foundPassenger.setPhone(phone);
            foundPassenger.setLocation(latitude, longitude);
            System.out.println("Passenger edited, here is your updated passenger details:");
            displayPassengerById(id);
        }


    }

    public void editPassengerName(int id, String name) {
        Passenger foundPassenger = findPassengerById(id);
        Passenger newPassenger = new Passenger(name, foundPassenger.getEmail(), foundPassenger.getPhone(), foundPassenger.getLocation().getLatitude(), foundPassenger.getLocation().getLongitude());
        if (passengerList.contains(newPassenger))
        {
            System.out.println("Passenger not edited - passenger already exists in the system");
        }
        else
        {
            foundPassenger.setName(name);
            System.out.println("Passenger edited, here is your updated passenger details:");
            displayPassengerById(id);
        }
    }


    public void editPassengerEmail (int id, String email) {
        Passenger foundPassenger = findPassengerById(id);
        Passenger newPassenger = new Passenger(foundPassenger.getName(), email, foundPassenger.getPhone(), foundPassenger.getLocation().getLatitude(), foundPassenger.getLocation().getLongitude());
        if (passengerList.contains(newPassenger))
        {
            System.out.println("Passenger not edited - passenger already exists in the system");
        }
        else
        {
            foundPassenger.setEmail(email);
            System.out.println("Passenger edited, here is your updated passenger details:");
            displayPassengerById(id);
        }
    }

    public void editPassengerPhone (int id,String phone) {
        Passenger foundPassenger = findPassengerById(id);
        foundPassenger.setPhone(phone);
        System.out.println("Passenger edited, here is your updated passenger details:");
        displayPassengerById(id);
    }

    public void editPassengerLocation (int id, double latitude, double longitude) {
        Passenger foundPassenger = findPassengerById(id);
        foundPassenger.setLocation(latitude, longitude);
        System.out.println("Passenger edited, here is your updated passenger details:");
        displayPassengerById(id);
    }


    // Delete Method

    public void deletePassengerById(int id) {
        passengerList.removeIf(p -> p.equals(findPassengerById(id)));
    }

    public void deletePassengerByName(String name) {
        passengerList.removeIf(p -> p.equals(findPassengerByName(name)));
    }


} // end class
