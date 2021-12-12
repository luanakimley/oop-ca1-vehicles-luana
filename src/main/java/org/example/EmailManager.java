package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailManager implements Serializable
{
    private final List<Email> emailList;
    private final BookingManager bookingManager;

    public EmailManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
        this.emailList = new ArrayList<>();
    }

    public List<Email> getAllEmails() {
        return emailList;
    }

    public void constructEmail(Booking b) {
        Passenger p = bookingManager.findPassengerById(b.getPassengerId());
        Vehicle v = bookingManager.findVehicleById(b.getVehicleId());
        String to = p.getEmail();
        String text = "Thank you for making a booking with us." +
                "\nHere are your booking details:" +
                "\nBooking ID: " + b.getBookingId() +
                "\nBooking Date Time: " + b.getBookingDateTime() +
                "\nStart location: " + b.getStartLocation().getLatitude() + ", " + b.getStartLocation().getLongitude() +
                "\nEnd location: " + b.getEndLocation().getLatitude() + ", " + b.getEndLocation().getLongitude() +
                "\nPassenger ID: " + p.getId() +
                "\nPassenger name: " + p.getName() +
                "\nVehicle ID: " + v.getId() +
                "\nVehicle registration: " + v.getRegistration() +
                "\nVehicle make & model:" + v.getMake() + ", " + v.getModel() +
                "\nCost: " + b.getCost()
                + "\nOnce again thank you and have a safe trip!";

        emailList.add(new Email(to, text));
    }

}
