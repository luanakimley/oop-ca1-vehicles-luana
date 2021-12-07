package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;

    // Comparators
    private final PassengerNameComparator passengerNameComparator = new PassengerNameComparator();

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    public ArrayList<Passenger> getAllPassengers() {
        return this.passengerList;
    }


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
                    p.getLocation().getLatitude()
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
            if(p.getName().toLowerCase().contains(name.toLowerCase()))
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


    // Edit Methods

    public void editAllPassengerDetails(int id, String name, String email, String phone,
                                        double latitude, double longitude) {
        Passenger newPassenger = new Passenger(name, email, phone, latitude, longitude);
        Passenger foundPassenger = findPassengerById(id);
        boolean duplicate = false;

        for (Passenger p : passengerList)
        {
            if (p.equals(newPassenger))
            {
                duplicate = true;
                break;
            }
        }

        if (!duplicate) {
            foundPassenger.setName(name);
            foundPassenger.setEmail(email);
            foundPassenger.setPhone(phone);
            foundPassenger.setLocation(latitude, longitude);
        }


    }

    public void editPassengerName (int id, String name) {

    }

    public void editPassengerEmail (int id, String email) {
        for (Passenger p : passengerList) {
            if (p.getEmail().equalsIgnoreCase(email))
            {
                break;
            }

            if (p.getId() == id)
            {
                p.setEmail(email);
            }

        }
    }

    public void editPassengerPhone (int id,String phone) {
        for (Passenger p : passengerList) {
            if (p != null && p.getEmail().equalsIgnoreCase(phone))
            {
                p.setEmail(phone);
            }
        }
    }

    public void editPassengerLocation (String email, double latitude, double longitude) {
        for (Passenger p : passengerList) {
            if (p != null && p.getEmail().equalsIgnoreCase(email))
            {
                p.setLocation(latitude, longitude);
            }
        }
    }

    // Delete Method

    public void deletePassengerById(int id) {
        passengerList.removeIf(p -> p.equals(findPassengerById(id)));
    }

    public void deletePassengerByName(String name) {
        passengerList.removeIf(p -> p.equals(findPassengerByName(name)));
    }










} // end class
