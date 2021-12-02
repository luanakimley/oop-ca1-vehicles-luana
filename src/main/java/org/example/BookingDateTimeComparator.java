package org.example;

import java.util.Comparator;

public class BookingDateTimeComparator implements Comparator<Booking>
{
    public int compare(Booking b1, Booking b2) {
        return b1.getBookingDateTime().compareTo(b2.getBookingDateTime());
    }
}
