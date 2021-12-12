package org.example;


import java.io.Serializable;
import java.time.LocalDateTime;


public class Email implements Serializable
{
    private String to;
    private final String from = "bookings@tesla.com";
    private final String subject = "Your Tesla Co. Booking Details";
    private final LocalDateTime dateTime = LocalDateTime.now();
    private String text;

    public Email(String to, String text) {
        this.to = to;
        this.text = text;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getFrom()
    {
        return from;
    }

    public String getSubject()
    {
        return subject;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "Email{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", subject='" + subject + '\'' +
                ", dateTime=" + dateTime +
                ", text='" + text + '\'' +
                '}';
    }
}
