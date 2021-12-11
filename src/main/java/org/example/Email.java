package org.example;


import java.io.Serializable;
import java.time.LocalDateTime;


public class Email implements Serializable
{
    private String to;
    private final String subject = "Your Booking Details";
    private final LocalDateTime dateTime = LocalDateTime.now();
    private String text;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }


    public Email(String to) {
        this.to = to;
    }

    public void constructEmail(Booking b) {
        this.text = "\nHere are your booking details:"
                + "\nVehicle ID: " + b.getVehicleId()
                + "\nBooking date time: " + b.getBookingDateTime()
                + "\nStart location: " + b.getStartLocation()
                + "\nEnd location: " + b.getEndLocation()
        ;
    }

    @Override
    public String toString()
    {
        return "Email{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", dateTime=" + dateTime +
                ", text='" + text + '\'' +
                '}';
    }
}
