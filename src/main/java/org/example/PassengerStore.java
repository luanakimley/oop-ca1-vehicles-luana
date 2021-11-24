package org.example;

import java.io.File;
import java.io.IOException;
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

    // Edit Methods

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

    // Delete Methods

    public void deleteAllPassengers() {
        Iterator<Passenger> it = passengerList.iterator();
        while (it.hasNext())
        {
            it.next();
            it.remove();
        }
    }

    public void deletePassengerByName(String name) {
        passengerList.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    public void deletePassengerByEmail(String email) {
        passengerList.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
    }

    public void deletePassengerByPhone(String phone) {
        passengerList.removeIf(p -> p.getPhone().equalsIgnoreCase(phone));
    }

    public void deletePassengerByLocation(double latitude, double longitude) {
        passengerList.removeIf(p -> p.getLocation().getLatitude() == latitude && p.getLocation().getLongitude() == longitude);
    }

    // Print Methods

    public void displayPassengerByName(String name) {
        for (Passenger p : this.passengerList) {
            if (p.getName().equalsIgnoreCase(name))
                System.out.println(p);
        }
    }

    public void displayPassengerByEmail(String email) {
        for (Passenger p : this.passengerList) {
            if (p.getEmail().equalsIgnoreCase(email))
                System.out.println(p);
        }
    }

    public void displayPassengerByPhone(String phone) {
        for (Passenger p : this.passengerList) {
            if (p.getPhone().equalsIgnoreCase(phone))
                System.out.println(p);
        }
    }

    public void displayPassengerByLocation(double latitude, double longitude) {
        for (Passenger p : this.passengerList) {
            if (p.getLocation().getLatitude() == latitude && p.getLocation().getLongitude() == longitude)
                System.out.println(p);
        }
    }





} // end class
