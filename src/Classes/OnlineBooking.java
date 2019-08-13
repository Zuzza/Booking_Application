
package Classes;

import java.util.Date;

/**
 * OnlineBooking class stores details of online booking. Inherits from booking class
 * @author Zuzanna Kanafa
 */
public class OnlineBooking extends Booking{
    public double prepaymentRate;
    // booking source as enum?
    private Source bookingSourceName;
    private int bookingSource;
    
    public OnlineBooking(int refNo, Apartment apBooked, String guestName, String guestSurname, 
            String guestPhone, String guestEmail, Date checkIn, int days, int sourceNo) {
        super(refNo, apBooked, guestName, guestSurname, guestPhone, guestEmail, checkIn, days);
        this.bookingSource = sourceNo;
        if(sourceNo == 1){
             this.bookingSourceName = Source.BOOKING;
             this.prepaymentRate = 0;
        }
        if(sourceNo == 2){
            this.bookingSourceName = Source.AIRBNB;
            this.prepaymentRate = 0.1;
        }
        if(sourceNo == 3){
            this.bookingSourceName = Source.EXPEDIA;
            this.prepaymentRate = 0.14;
        }
        super.setQuoted(calcPrice());
    }

    @Override
    public Double calcPrice() {
        return this.getApBooked().getDayPrice() * this.getDays() * (1 - prepaymentRate);
    }
    
    enum Source {
        BOOKING,
        AIRBNB,
        EXPEDIA
    }

    public int getBookingSource() {
        return bookingSource;
    }
}
