package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PassengerStore {

    private final ArrayList<Passenger> passengerList;

    public PassengerStore(String fileName) {
        this.passengerList = new ArrayList<>();
        loadPassengerDataFromFile(fileName);
    }

    public List<Passenger> getAllPassengers() {
        return this.passengerList;
    }

    public void displayAllPassengers() {
        for (Passenger p : this.passengerList) {
            System.out.println(p.toString());
        }
    }

    public void addPassenger(String name, String email, String phone, double latitude, double longitude) {
        Passenger passenger = new Passenger(name, email, phone, latitude, longitude);
        boolean found = false;

        // test to see if Passenger already exists
        for (Passenger p : passengerList) {
            if (p.equals(passenger)) {
                found = true;
                break;
            }
        }

        if (!found)
            passengerList.add(passenger);
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
            if(p.getName().equalsIgnoreCase(name))
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

    public void editAllPassengerDetails(String email, String name, String newEmail, String phone,
                                        double latitude, double longitude) {
        for (Passenger p : passengerList) {
            if (p != null && p.getEmail().equalsIgnoreCase(email)) {
                p.setName(name);
                p.setEmail(newEmail);
                p.setPhone(phone);
                p.setLocation(latitude, longitude);
            }
        }
    }

    public void editPassengerName (String email, String name) {
        for (Passenger p : passengerList) {
            if (p != null && p.getEmail().equalsIgnoreCase(email))
            {
                p.setName(name);
            }
        }
    }

    public void editPassengerEmail (String email, String newEmail) {
        for (Passenger p : passengerList) {
            if (p.getEmail().equalsIgnoreCase(newEmail))
            {
                break;
            }

            if (p.getEmail().equalsIgnoreCase(email))
            {
                p.setEmail(newEmail);
            }

        }
    }

    public void editPassengerPhone (String email,String phone) {
        for (Passenger p : passengerList) {
            if (p != null && p.getEmail().equalsIgnoreCase(email))
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

    public void deletePassenger(int id) {
        passengerList.removeIf(p -> p.equals(findPassengerById(id)));
    }


    // Print Methods

    public void displayPassengerByName(String name) {
        for (Passenger p : passengerList) {
            if (p.getName().equalsIgnoreCase(name))
                System.out.println(p);
        }
    }

    public void displayPassengerByEmail(String email) {
        for (Passenger p : passengerList) {
            if (p.getEmail().equalsIgnoreCase(email))
                System.out.println(p);
        }
    }

    public void displayPassengerByPhone(String phone) {
        for (Passenger p : passengerList) {
            if (p.getPhone().equalsIgnoreCase(phone))
                System.out.println(p);
        }
    }

    public void displayPassengerByLocation(double latitude, double longitude) {
        for (Passenger p : passengerList) {
            if (p.getLocation().getLatitude() == latitude && p.getLocation().getLongitude() == longitude)
                System.out.println(p);
        }
    }





} // end class
