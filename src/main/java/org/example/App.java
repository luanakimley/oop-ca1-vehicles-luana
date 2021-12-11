package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.DateTimeException;

/**
 * Luana Kimley
 * This Vehicle Bookings Management Systems manages the booking of Vehicles
 * by Passengers.
 *
 * This program reads from 3 text files:
 * "vehicles.txt", "passengers.txt", and "next-id-store.txt"
 * You should be able to see them in the project pane.
 * You will create "bookings.txt" at a later stage, to store booking records.
 *
 * "next-id-store.txt" contains one number ("201"), which will be the
 * next auto-generated id to be used to when new vehicles, passengers, or
 * bookings are created.  The value in the file will be updated when new objects
 * are created - but not when objects are recreated from records in
 * the files - as they already have IDs.  Dont change it - it will be updated by
 * the IdGenerator class.
 */


public class App
{
    // Components (objects) used in this App
    PassengerStore passengerStore;  // encapsulates access to list of Passengers
    VehicleManager vehicleManager;  // encapsulates access to list of Vehicles
    BookingManager bookingManager;  // deals with all bookings

    public static void main(String[] args)
    {
        org.example.App app = new org.example.App();
        app.start();
    }

    public void start()
    {
        // create PassengerStore and load all passenger records from text file
        passengerStore = new PassengerStore("passengers.txt");

        // create VehicleManager, and load all vehicles from text file
        vehicleManager = new VehicleManager("vehicles.txt");

        // Create BookingManager and load all bookings from file
        bookingManager = new BookingManager(vehicleManager, passengerStore, "bookings.txt");


        try
        {
            displayMainMenu();        // User Interface - Menu
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        // Write Passenger to text file
        System.out.println("\nSaving passengers, vehicles, bookings data to text file...");
        passengerStore.savePassengerDataToFile("passengers.txt");
        vehicleManager.saveVehicleDataToFile("vehicles.txt");
        bookingManager.saveBookingDataToFile("bookings.txt");



        System.out.println("\nProgram ending, Goodbye");
    }


    private void displayMainMenu() throws IOException
    {

        final String MENU_ITEMS = "\n*** MAIN MENU OF OPTIONS ***\n"
                + "1. Passengers\n"
                + "2. Vehicles\n"
                + "3. Bookings\n"
                + "4. Exit\n"
                + "Enter Option [1,4]";

        final int PASSENGERS = 1;
        final int VEHICLES = 2;
        final int BOOKINGS = 3;
        final int EXIT = 4;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do
        {
            System.out.println("\n" + MENU_ITEMS);
            try
            {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option)
                {
                    case PASSENGERS:
                        System.out.println("Passengers option chosen");
                        displayPassengerMenu();
                        break;
                    case VEHICLES:
                        System.out.println("Vehicles option chosen");
                        displayVehicleMenu();
                        break;
                    case BOOKINGS:
                        System.out.println("Bookings option chosen");
                        displayBookingMenu();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid input - please enter number in range");
                        break;
                }

            } catch (InputMismatchException | NumberFormatException e)
            {
                System.out.print("Invalid input - please enter number in range");
            }
        } while (option != EXIT);

        System.out.println("\nExiting Main Menu, goodbye.");

    }

    // Sub-Menu for Passenger operations
    //
    private void displayPassengerMenu()
    {
        final String MENU_ITEMS = "\n*** PASSENGER MENU ***\n"
                + "1. Show all passengers\n"
                + "2. Find passenger by Name\n"
                + "3. Add passenger\n"
                + "4. Delete passenger\n"
                + "5. Edit passenger\n"
                + "6. Exit\n"
                + "Enter Option [1,6]";

        final int SHOW_ALL_PASSENGER = 1;
        final int FIND_BY_NAME = 2;
        final int ADD_PASSENGER = 3;
        final int DELETE_PASSENGER = 4;
        final int EDIT_PASSENGER = 5;
        final int EXIT = 6;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do
        {
            System.out.println("\n" + MENU_ITEMS);
            try
            {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option)
                {
                    case SHOW_ALL_PASSENGER:
                        System.out.println("\nDisplay ALL Passengers");
                        bookingManager.displayAllPassengers();
                        break;
                    case FIND_BY_NAME:
                        findPassengerByNameOption();
                        break;
                    case ADD_PASSENGER:
                        addPassengerOption();
                        break;
                    case DELETE_PASSENGER:
                        deletePassengerMenuOption();
                        break;
                    case EDIT_PASSENGER:
                        editPassengerMenuOption();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid input - please enter number in range");
                        break;
                }
            } catch (InputMismatchException | NumberFormatException e)
            {
                System.out.print("Invalid input - please enter number in range");
            }
        } while (option != EXIT);

    }

    private void deletePassengerMenuOption()
    {
        final String MENU_ITEMS = "\n*** PASSENGER DELETE MENU ***\n"
                + "1. Delete Passenger by ID\n"
                + "2. Delete Passenger by Name\n"
                + "3. Exit\n"
                + "Enter Option [1,3]";

        final int DELETE_BY_ID = 1;
        final int DELETE_BY_NAME = 2;
        final int EXIT = 3;

        Scanner keyboard = new Scanner(System.in);
        int option = 0;
        do
        {
            System.out.println("\n" + MENU_ITEMS);
            try
            {
                String usersInput = keyboard.nextLine();
                option = Integer.parseInt(usersInput);
                switch (option)
                {
                    case DELETE_BY_ID:
                        deletePassengerByIdOption();
                        break;
                    case DELETE_BY_NAME:
                        deletePassengerByNameOption();
                        break;
                    case EXIT:
                        System.out.println("Exit Menu option chosen");
                        break;
                    default:
                        System.out.print("Invalid input - please enter number in range");
                        break;
                }
            } catch (InputMismatchException | NumberFormatException e)
            {
                System.out.print("Invalid input - please enter number in range");
            }
        } while (option != EXIT);
    }


        // Sub-Menu for Vehicle operations
        //
        private void displayVehicleMenu()
        {
            final String MENU_ITEMS = "\n*** VEHICLE MENU ***\n"
                    + "1. Show all vehicles\n"
                    + "2. Find vehicle options\n"
                    + "3. Exit\n"
                    + "Enter Option [1,3]";

            final int SHOW_ALL_VEHICLE = 1;
            final int FIND_VEHICLE = 2;
            final int EXIT = 3;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case SHOW_ALL_VEHICLE:
                            System.out.println("\nDisplay ALL Vehicles");
                            bookingManager.displayAllVehicles();
                            break;
                        case FIND_VEHICLE:
                            findVehicleMenuOption();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);


        }

        private void findVehicleMenuOption() {
            final String MENU_ITEMS = "\n*** FIND VEHICLE MENU ***\n"
                    + "1. Find Vehicle by ID\n"
                    + "2. Find Vehicle by Registration\n"
                    + "3. Find Vehicle by Type\n"
                    + "4. Find Vehicle by Number of Seats\n"
                    + "5. Find Vehicle by Load Space less than...\n"
                    + "6. Find Vehicle by Load Space more than...\n"
                    + "7. Exit\n"
                    + "Enter Option [1,7]";

            final int FIND_BY_ID = 1;
            final int FIND_BY_REG = 2;
            final int FIND_BY_TYPE = 3;
            final int FIND_BY_NUMOFSEATS = 4;
            final int FIND_BY_LOADSPACE_LESS = 5;
            final int FIND_BY_LOADSPACE_MORE = 6;
            final int EXIT = 7;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case FIND_BY_ID:
                            findVehicleByIdOption();
                            break;
                        case FIND_BY_REG:
                            findVehicleByRegistrationOption();
                            break;
                        case FIND_BY_TYPE:
                            findVehicleByTypeOption();
                            break;
                        case FIND_BY_NUMOFSEATS:
                            findVehiclesByNumOfSeatsOption();
                            break;
                        case FIND_BY_LOADSPACE_LESS:
                            findVehicleByLoadSpaceLessOption();
                            break;
                        case FIND_BY_LOADSPACE_MORE:
                            findVehicleByLoadSpaceMoreOption();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);
        }

        // Sub-Menu for Booking operations
        //
        private void displayBookingMenu()
        {
            final String MENU_ITEMS = "\n*** BOOKING MENU ***\n"
                    + "1. Display Booking Options\n"
                    + "2. Make Booking\n"
                    + "3. Show Average Length of Booking Journeys \n"
                    + "4. Delete Booking\n"
                    + "5. Edit Booking\n"
                    + "6. Exit\n"
                    + "Enter Option [1,6]";

            final int DISPLAY_MENU = 1;
            final int MAKE_BOOKING = 2;
            final int CALCULATE_AVERAGE_JOURNEY_LENGTH = 3;
            final int DELETE_BOOKING = 4;
            final int EDIT_BOOKING = 5;
            final int EXIT = 6;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case DISPLAY_MENU:
                            bookingDisplayMenuOption();
                            break;
                        case MAKE_BOOKING:
                            makeBookingOption();
                            break;
                        case CALCULATE_AVERAGE_JOURNEY_LENGTH:
                            System.out.println("\nAverage Length of Booking Journey\n" +
                                    bookingManager.calculateAverageLengthOfBookingJourneys());
                            break;
                        case DELETE_BOOKING:
                            deleteBookingOption();
                            break;
                        case EDIT_BOOKING:
                            editBookingMenuOption();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);


        }

        private void bookingDisplayMenuOption()
        {
            final String MENU_ITEMS = "\n*** BOOKING DISPLAY MENU ***\n"
                    + "1. Show all Bookings\n"
                    + "2. Show Bookings by Passenger ID\n"
                    + "3. Show Bookings by Passenger Name\n"
                    + "4. Show Bookings by Booking ID\n"
                    + "5. Show current Bookings\n"
                    + "6. Exit\n"
                    + "Enter Option [1,6]";

            final int SHOW_ALL_BOOKING = 1;
            final int SHOW_BOOKING_PASSENGER_ID = 2;
            final int SHOW_BOOKING_PASSENGER_NAME = 3;
            final int SHOW_BOOKING_BOOKING_ID = 4;
            final int SHOW_CURRENT_BOOKINGS = 5;
            final int EXIT = 6;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case SHOW_ALL_BOOKING:
                            System.out.println("\nDisplay ALL Bookings");
                            bookingManager.displayAllBookings();
                            break;
                        case SHOW_BOOKING_PASSENGER_ID:
                            displayBookingByPassengerIdOption();
                            break;
                        case SHOW_BOOKING_PASSENGER_NAME:
                            displayBookingByPassengerNameOption();
                            break;
                        case SHOW_BOOKING_BOOKING_ID:
                            displayBookingByBookingIdOption();
                            break;
                        case SHOW_CURRENT_BOOKINGS:
                            System.out.println("Display Current Bookings");
                            bookingManager.displayCurrentBookings();
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);
        }

        /*
         *  Passenger options
         */
        private void findPassengerByNameOption() {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nFind Passenger by Name");
            System.out.println("Enter passenger name:");
            String name = keyboard.nextLine();
            bookingManager.displayPassengerByName(name);
        }

        private void addPassengerOption() {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nAdding new passenger");

            System.out.println("Enter passenger name:");
            String name = keyboard.nextLine();

            while (!name.matches("^[A-Za-z' ]+$")) {
                System.out.println("Invalid name");
                System.out.println("Enter passenger name:");
                name = keyboard.nextLine();
            }

            System.out.println("Enter passenger email:");
            String email = keyboard.nextLine();

            while (!email.matches("^(.+)@(\\S+)$")) {
                System.out.println("Invalid email");
                System.out.println("Enter passenger email:");
                email = keyboard.nextLine();
            }

            System.out.println("Enter passenger phone number:");
            String phone = keyboard.nextLine();
            while(!phone.matches("^[0-9-]{8,15}$")) {
                System.out.println("Invalid phone number");
                System.out.println("Enter passenger phone number:");
                phone = keyboard.nextLine();
            }

            System.out.println("Enter home location latitude:");
            double latitude = keyboard.nextDouble();
            while (latitude < -90 || latitude > 90) {
                System.out.println("Invalid latitude");
                System.out.println("Enter home location latitude:");
                latitude = keyboard.nextDouble();
            }

            System.out.println("Enter home location longitude:");
            double longitude = keyboard.nextDouble();
            while (longitude < -180 || longitude > 90) {
                System.out.println("Invalid longitude");
                System.out.println("Enter home location longitude:");
                longitude = keyboard.nextDouble();
            }

            bookingManager.addPassenger(name, email, phone, latitude, longitude);

        }

        private void deletePassengerByIdOption() {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nPassengers List:");
            bookingManager.displayAllPassengers();
            System.out.println("Enter ID of the passenger that you want to delete:");
            int passengerId = keyboard.nextInt();
            if (bookingManager.findPassengerById(passengerId) != null) {
                bookingManager.deletePassengerById(passengerId);
                System.out.println("Passenger deleted");
            }
            else {
                System.out.println("Passenger not found");
            }
        }

        private void deletePassengerByNameOption() {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nPassengers List:");
            bookingManager.displayAllPassengers();
            System.out.println("Enter name of the passenger that you want to delete:");
            String name = keyboard.nextLine();
            if (bookingManager.findPassengerByName(name) != null) {
                bookingManager.deletePassengerByName(name);
                System.out.println("Passenger deleted");
            }
            else {
                System.out.println("Passenger not found");
            }
        }

        private void editPassengerMenuOption() {
            Scanner keyboard = new Scanner(System.in);
            bookingManager.displayAllPassengers();
            System.out.println("Enter passenger ID of the passenger to edit:");
            int passengerId = keyboard.nextInt();

            Passenger found = bookingManager.findPassengerById(passengerId);
            if (found != null) {
                System.out.println("\nPassenger found, here are the details:");
                bookingManager.displayPassengerById(found.getId());
                editPassengerOption(passengerId);
            }
            else {
                System.out.println("\nPassenger not found.");
            }

        }

        private void editPassengerOption(int id) {

            final String MENU_ITEMS = "*** EDIT PASSENGER MENU ***\n"
                    + "1. Edit all details\n"
                    + "2. Edit name\n"
                    + "3. Edit email\n"
                    + "4. Edit phone\n"
                    + "5. Edit location\n"
                    + "6. Exit\n"
                    + "Enter Option [1,6]";

            final int EDIT_ALL = 1;
            final int EDIT_NAME = 2;
            final int EDIT_EMAIL = 3;
            final int EDIT_PHONE = 4;
            final int EDIT_LOCATION = 5;
            final int EXIT = 6;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case EDIT_ALL:
                            editAllPassengerDetails(id);
                            break;
                        case EDIT_NAME:
                            editPassengerName(id);
                            break;
                        case EDIT_EMAIL:
                            editPassengerEmail(id);
                            break;
                        case EDIT_PHONE:
                            editPassengerPhone(id);
                            break;
                        case EDIT_LOCATION:
                            editPassengerLocation(id);
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);

        }

        private void editAllPassengerDetails(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit all passenger details");

            System.out.println("Enter new name:");
            String name = keyboard.nextLine();
            while (!name.matches("^[A-Za-z' ]+$")) {
                System.out.println("Invalid name");
                System.out.println("Enter passenger name:");
                name = keyboard.nextLine();
            }
            System.out.println("Enter new email:");
            String email = keyboard.nextLine();
            while (!email.matches("^(.+)@(\\S+)$")) {
                System.out.println("Invalid email");
                System.out.println("Enter passenger email:");
                email = keyboard.nextLine();
            }
            System.out.println("Enter new phone:");
            String phone = keyboard.nextLine();
            while(!phone.matches("^[0-9-]{8,15}$")) {
                System.out.println("Invalid phone number");
                System.out.println("Enter passenger phone number:");
                phone = keyboard.nextLine();
            }
            System.out.println("Enter new home location latitude:");
            double latitude = keyboard.nextDouble();
            while (latitude < -90 || latitude > 90) {
                System.out.println("Invalid latitude");
                System.out.println("Enter home location latitude:");
                latitude = keyboard.nextDouble();
            }

            System.out.println("Enter new home location longitude:");
            double longitude = keyboard.nextDouble();
            while (longitude < -180 || longitude > 90) {
                System.out.println("Invalid longitude");
                System.out.println("Enter home location longitude:");
                longitude = keyboard.nextDouble();
            }

            bookingManager.editAllPassengerDetails(id, name, email, phone, latitude, longitude);
        }

        private void editPassengerName(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit passenger name");
            System.out.println("Enter new name:");
            String name = keyboard.nextLine();
            while (!name.matches("^[A-Za-z' ]+$")) {
                System.out.println("Invalid name");
                System.out.println("Enter new name:");
                name = keyboard.nextLine();
            }

            bookingManager.editPassengerName(id, name);
        }

        private void editPassengerEmail(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit passenger email");
            System.out.println("Enter new email:");
            String email = keyboard.nextLine();
            while (!email.matches("^(.+)@(\\S+)$")) {
                System.out.println("Invalid email");
                System.out.println("Enter passenger email:");
                email = keyboard.nextLine();
            }

            bookingManager.editPassengerEmail(id, email);
        }

        private void editPassengerPhone(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit passenger phone");
            System.out.println("Enter new phone:");
            String phone = keyboard.nextLine();
            while(!phone.matches("^[0-9-]{8,15}$")) {
                System.out.println("Invalid phone number");
                System.out.println("Enter passenger phone number:");
                phone = keyboard.nextLine();
            }

            bookingManager.editPassengerPhone(id, phone);
        }

        private void editPassengerLocation(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit passenger location");
            System.out.println("Enter new home location latitude:");
            double latitude = keyboard.nextDouble();
            while (latitude < -90 || latitude > 90) {
                System.out.println("Invalid latitude");
                System.out.println("Enter home location latitude:");
                latitude = keyboard.nextDouble();
            }
            System.out.println("Enter new home location longitude:");
            double longitude = keyboard.nextDouble();
            while (longitude < -180 || longitude > 90) {
                System.out.println("Invalid longitude");
                System.out.println("Enter home location longitude:");
                longitude = keyboard.nextDouble();
            }

            bookingManager.editPassengerLocation(id, latitude, longitude);
        }

        /*
         *  Vehicle options
         */

        private void findVehicleByIdOption()
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nDisplay Vehicle by ID");
            System.out.println("Enter vehicle ID:");
            int id = keyboard.nextInt();
            bookingManager.displayVehicleById(id);
        }

        private void findVehicleByRegistrationOption()
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nDisplay Vehicle by Registration");
            System.out.println("Enter vehicle registration:");
            String reg = keyboard.nextLine();
            bookingManager.displayVehicleByRegistration(reg);
        }

        private void findVehicleByTypeOption()
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nDisplay Vehicle by Type");
            System.out.println("Enter vehicle type:");
            String type = keyboard.nextLine();
            bookingManager.displayVehiclesByType(type);
        }

        private void findVehiclesByNumOfSeatsOption()
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nDisplay Vehicle by Number of Seats");
            System.out.println("Enter vehicle number of seats:");
            int numOfSeats = keyboard.nextInt();
            bookingManager.displayVehiclesByNumOfSeats(numOfSeats);
        }

        private void findVehicleByLoadSpaceLessOption() {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nDisplay Vehicle by Load Space less than...");
            System.out.println("Enter vehicle load space:");
            double loadSpace = keyboard.nextDouble();
            bookingManager.displayVehiclesByLoadSpaceLessThan(loadSpace);
        }

    private void findVehicleByLoadSpaceMoreOption() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nDisplay Vehicle by Load Space more than...");
        System.out.println("Enter vehicle load space:");
        double loadSpace = keyboard.nextDouble();
        bookingManager.displayVehiclesByLoadSpaceMoreThan(loadSpace);
    }

        /*
         *  Booking options
         */
        private void makeBookingOption()
        {
            try
            {
                Scanner keyboard = new Scanner(System.in);

                System.out.println("\nMaking new booking\n");

                System.out.println("Vehicles List:");
                bookingManager.displayAllVehicles();
                System.out.println("Enter Vehicle ID:");
                int vehicleId = keyboard.nextInt();

                bookingManager.displayAllPassengers();
                System.out.println("Enter Passenger ID:");
                int passengerId = keyboard.nextInt();

                System.out.println("Enter date of booking:");
                int date = keyboard.nextInt();
                while (date < 1 || date > 31) {
                    System.out.println("Invalid date input");
                    System.out.println("Enter date of booking:");
                    date = keyboard.nextInt();
                }

                System.out.println("Enter month of booking (1-12):");
                int month = keyboard.nextInt();
                while (month < 1 || month > 12) {
                    System.out.println("Invalid month input");
                    System.out.println("Enter month of booking (1-12):");
                    month = keyboard.nextInt();
                }

                System.out.println("Enter year of booking:");
                int year = keyboard.nextInt();
                while (year < LocalDateTime.now().getYear()) {
                    System.out.println("Invalid year input");
                    System.out.println("Enter year of booking:");
                    year = keyboard.nextInt();
                }

                System.out.println("Enter hour of booking (0-23):");
                int hour = keyboard.nextInt();
                while (hour < 0 || hour > 23) {
                    System.out.println("Invalid hour input");
                    System.out.println("Enter hour of booking (0-23):");
                    hour = keyboard.nextInt();
                }

                System.out.println("Enter minute of booking (0-59):");
                int minute = keyboard.nextInt();
                while (minute < 0 || minute > 59) {
                    System.out.println("Invalid minute input");
                    System.out.println("Enter minute of booking (0-59):");
                    minute = keyboard.nextInt();
                }

                System.out.println("Enter start location latitude:");
                double startLat = keyboard.nextDouble();
                while (startLat < -90 || startLat > 90) {
                    System.out.println("Invalid latitude input");
                    System.out.println("Enter start location latitude:");
                    startLat = keyboard.nextDouble();
                }

                System.out.println("Enter start location longitude:");
                double startLong = keyboard.nextDouble();
                while (startLong < -180 || startLong > 180) {
                    System.out.println("Invalid longitude input");
                    System.out.println("Enter start location longitude:");
                    startLong = keyboard.nextDouble();
                }

                System.out.println("Enter end location latitude:");
                double endLat = keyboard.nextDouble();
                while (endLat < -90 || endLat > 90) {
                    System.out.println("Invalid latitude input");
                    System.out.println("Enter end location latitude:");
                    endLat = keyboard.nextDouble();
                }

                System.out.println("Enter end location longitude:");
                double endLong = keyboard.nextDouble();
                while (endLong < -180 || endLong > 180) {
                    System.out.println("Invalid longitude input");
                    System.out.println("Enter end location longitude:");
                    endLong = keyboard.nextDouble();
                }

                Booking newBooking = bookingManager.makeBooking(passengerId, vehicleId, year, month, date, hour, minute, startLat, startLong, endLat, endLong);
                if (newBooking != null) {
                    System.out.println("\nBooking made. Here are your booking details:");
                    bookingManager.displayBookingByBookingId(newBooking.getBookingId());
                    System.out.println("\nDo you want us to send an email about your booking details? (Y/N)");
                    String ans = keyboard.next();
                    while (!ans.matches("[Y|N|y|n]")) {
                        System.out.println("Invalid input");
                        System.out.println("Enter input (Y/N)");
                        ans = keyboard.next();
                    }
                    if (ans.equalsIgnoreCase("y"))
                    {
                        Email email = new Email(bookingManager.findPassengerById(passengerId).getEmail());
                        email.constructEmail(newBooking);
                        System.out.println("\nEmail sent.");
                        System.out.println(email);
                    }
                    if (ans.equalsIgnoreCase("n"))
                    {
                        System.out.println("\nEmail will not be sent.");
                    }
                }
            } catch (DateTimeException e)
            {
                System.out.println("\nInvalid date / time - please enter number in range");
            }
        }

        private void displayBookingByPassengerIdOption() {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nDisplay Bookings by Passenger ID");
            System.out.println("Enter Passenger ID:");
            int passengerId = keyboard.nextInt();
            bookingManager.displayBookingByPassengerId(passengerId);
        }

        private void displayBookingByPassengerNameOption() {

            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nDisplay Bookings by Passenger Name");
            System.out.println("Enter Passenger Name:");
            String passengerName = keyboard.nextLine();
            bookingManager.displayBookingByPassengerName(passengerName);

        }

        private void displayBookingByBookingIdOption() {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nDisplay Bookings by Booking ID");
            System.out.println("Enter Booking ID:");
            int bookingId = keyboard.nextInt();
            bookingManager.displayBookingByBookingId(bookingId);
        }

        private void deleteBookingOption() {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nDisplaying all Bookings...");
            bookingManager.displayAllBookings();
            System.out.println("Enter booking ID of the booking you want to delete:");
            int bookingId = keyboard.nextInt();
            if (bookingManager.findBookingById(bookingId) != null)
            {
                bookingManager.deleteBookingById(bookingId);
                System.out.println("Booking deleted");
            }
            else
            {
                System.out.println("Booking not found");
            }
        }

        private void editBookingMenuOption() {
            Scanner keyboard = new Scanner(System.in);
            bookingManager.displayAllBookings();
            System.out.println("Enter booking ID of the booking to edit:");
            int bookingId = keyboard.nextInt();

            Booking found = bookingManager.findBookingById(bookingId);
            if (found != null) {
                System.out.println("\nBooking found, here are the details:");
                bookingManager.displayBookingByBookingId(bookingId);
                editBookingOption(bookingId);
            }
            else {
                System.out.println("Booking not found.");
            }
        }

        private void editBookingOption(int id) {
            final String MENU_ITEMS = "*** EDIT BOOKING MENU ***\n"
                    + "1. Edit all details\n"
                    + "2. Edit passenger ID\n"
                    + "3. Edit vehicle ID\n"
                    + "4. Edit booking date time\n"
                    + "5. Edit start location\n"
                    + "6. Edit end location\n"
                    + "7. Exit\n"
                    + "Enter Option [1,7]";

            final int EDIT_ALL = 1;
            final int EDIT_PASSENGERID = 2;
            final int EDIT_VEHICLEID = 3;
            final int EDIT_DATETIME = 4;
            final int EDIT_STARTLOCATION = 5;
            final int EDIT_ENDLOCATION = 6;
            final int EXIT = 7;

            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            do
            {
                System.out.println("\n" + MENU_ITEMS);
                try
                {
                    String usersInput = keyboard.nextLine();
                    option = Integer.parseInt(usersInput);
                    switch (option)
                    {
                        case EDIT_ALL:
                            editAllBookingDetails(id);
                            break;
                        case EDIT_PASSENGERID:
                            editBookingPassengerId(id);
                            break;
                        case EDIT_VEHICLEID:
                            editBookingVehicleId(id);
                            break;
                        case EDIT_DATETIME:
                            editBookingDateTime(id);
                            break;
                        case EDIT_STARTLOCATION:
                            editBookingStartLocation(id);
                            break;
                        case EDIT_ENDLOCATION:
                            editBookingEndLocation(id);
                            break;
                        case EXIT:
                            System.out.println("Exit Menu option chosen");
                            break;
                        default:
                            System.out.print("Invalid input - please enter number in range");
                            break;
                    }

                } catch (InputMismatchException | NumberFormatException e)
                {
                    System.out.print("Invalid input - please enter number in range");
                }
            } while (option != EXIT);
        }

        private void editAllBookingDetails(int id) {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("\nEdit all booking details");

            System.out.println("Enter new Passenger ID:");
            int passengerId = keyboard.nextInt();

            System.out.println("Enter new Vehicle ID:");
            int vehicleId = keyboard.nextInt();

            System.out.println("Enter new date of booking:");
            int date = keyboard.nextInt();
            while (date < 1 || date > 31) {
                System.out.println("Invalid date input");
                System.out.println("Enter new date of booking:");
                date = keyboard.nextInt();
            }

            System.out.println("Enter new month of booking (1-12):");
            int month = keyboard.nextInt();
            while (month < 1 || month > 12) {
                System.out.println("Invalid month input");
                System.out.println("Enter new month of booking (1-12):");
                month = keyboard.nextInt();
            }

            System.out.println("Enter new year of booking:");
            int year = keyboard.nextInt();
            while (year < LocalDateTime.now().getYear()) {
                System.out.println("Invalid year input");
                System.out.println("Enter new year of booking:");
                year = keyboard.nextInt();
            }

            System.out.println("Enter new hour of booking (0-23):");
            int hour = keyboard.nextInt();
            while (hour < 0 || hour > 23) {
                System.out.println("Invalid hour input");
                System.out.println("Enter new hour of booking (0-23):");
                hour = keyboard.nextInt();
            }

            System.out.println("Enter new minute of booking (0-59):");
            int minute = keyboard.nextInt();
            while (minute < 0 || minute > 59) {
                System.out.println("Invalid minute input");
                System.out.println("Enter new minute of booking (0-59):");
                minute = keyboard.nextInt();
            }

            System.out.println("Enter new start location latitude:");
            double startLat = keyboard.nextDouble();
            while (startLat < -90 || startLat > 90) {
                System.out.println("Invalid latitude input");
                System.out.println("Enter new start location latitude:");
                startLat = keyboard.nextDouble();
            }

            System.out.println("Enter new start location longitude:");
            double startLong = keyboard.nextDouble();
            while (startLong < -180 || startLong > 180) {
                System.out.println("Invalid longitude input");
                System.out.println("Enter new start location longitude:");
                startLong = keyboard.nextDouble();
            }

            System.out.println("Enter new end location latitude:");
            double endLat = keyboard.nextDouble();
            while (endLat < -90 || endLat > 90) {
                System.out.println("Invalid latitude input");
                System.out.println("Enter new end location latitude:");
                endLat = keyboard.nextDouble();
            }

            System.out.println("Enter new end location longitude:");
            double endLong = keyboard.nextDouble();
            while (endLong < -180 || endLong > 180) {
                System.out.println("Invalid longitude input");
                System.out.println("Enter new end location longitude:");
                endLong = keyboard.nextDouble();
            }

            bookingManager.editAllBookingDetails(id, passengerId, vehicleId, year, month, date, hour, minute, startLat,startLong, endLat, endLong);

        }

        private void editBookingPassengerId(int id) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nEdit booking passenger ID");
            System.out.println("Enter new passenger ID:");
            int passengerId = keyboard.nextInt();
            bookingManager.editPassengerId(id, passengerId);

        }

        private void editBookingVehicleId(int id) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nEdit booking vehicle ID");
            System.out.println("Enter new vehicle ID:");
            int vehicleId = keyboard.nextInt();
            bookingManager.editVehicleId(id, vehicleId);
        }

        private void editBookingDateTime(int id) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter new date of booking:");
            int date = keyboard.nextInt();
            while (date < 1 || date > 31) {
                System.out.println("Invalid date input");
                System.out.println("Enter new date of booking:");
                date = keyboard.nextInt();
            }

            System.out.println("Enter new month of booking (1-12):");
            int month = keyboard.nextInt();
            while (month < 1 || month > 12) {
                System.out.println("Invalid month input");
                System.out.println("Enter new month of booking (1-12):");
                month = keyboard.nextInt();
            }

            System.out.println("Enter new year of booking:");
            int year = keyboard.nextInt();
            while (year < LocalDateTime.now().getYear()) {
                System.out.println("Invalid year input");
                System.out.println("Enter new year of booking:");
                year = keyboard.nextInt();
            }

            System.out.println("Enter new hour of booking (0-23):");
            int hour = keyboard.nextInt();
            while (hour < 0 || hour > 23) {
                System.out.println("Invalid hour input");
                System.out.println("Enter new hour of booking (0-23):");
                hour = keyboard.nextInt();
            }

            System.out.println("Enter new minute of booking (0-59):");
            int minute = keyboard.nextInt();
            while (minute < 0 || minute > 59) {
                System.out.println("Invalid minute input");
                System.out.println("Enter new minute of booking (0-59):");
                minute = keyboard.nextInt();
            }
            bookingManager.editBookingDateTime(id, year, month, date, hour, minute);
        }

        private void editBookingStartLocation(int id) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nEdit booking start location");
            System.out.println("Enter new start location latitude:");
            double startLat = keyboard.nextDouble();
            while (startLat < -90 || startLat > 90) {
                System.out.println("Invalid latitude input");
                System.out.println("Enter new start location latitude:");
                startLat = keyboard.nextDouble();
            }

            System.out.println("Enter new start location longitude:");
            double startLong = keyboard.nextDouble();
            while (startLong < -180 || startLong > 180) {
                System.out.println("Invalid longitude input");
                System.out.println("Enter new start location longitude:");
                startLong = keyboard.nextDouble();
            }
            bookingManager.editBookingStartLocation(id, startLat, startLong);
        }

        private void editBookingEndLocation(int id) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("\nEdit booking end location");
            System.out.println("Enter new end location latitude:");
            double endLat = keyboard.nextDouble();
            while (endLat < -90 || endLat > 90) {
                System.out.println("Invalid latitude input");
                System.out.println("Enter new end location latitude:");
                endLat = keyboard.nextDouble();
            }

            System.out.println("Enter new end location longitude:");
            double endLong = keyboard.nextDouble();
            while (endLong < -180 || endLong > 180) {
                System.out.println("Invalid longitude input");
                System.out.println("Enter new end location longitude:");
                endLong = keyboard.nextDouble();
            }
            bookingManager.editBookingEndLocation(id, endLat, endLong);
        }

    }