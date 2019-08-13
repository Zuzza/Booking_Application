
package Classes;

import java.util.Date;

/**
 * Class describing DirectBooking. Inherits from Booking class
 * @author Zuzanna Kanafa
 */
public class DirectBooking extends Booking{
    private boolean returning;
    static final double DISCOUNT = 0.14;

    public DirectBooking(int refNo, Apartment apBooked, String guestName, String guestSurname, 
            String guestPhone, String guestEmail, Date checkIn, int days, boolean returning) {
        super(refNo, apBooked, guestName, guestSurname, guestPhone, guestEmail, checkIn, days);
        this.returning = returning;
        super.setQuoted(calcPrice());
    }

    @Override
    public Double calcPrice() {
        if(returning)
            return this.getApBooked().getDayPrice() * this.getDays() * (1-DISCOUNT);
        else
            return this.getApBooked().getDayPrice() * this.getDays();
    }
    
    public boolean isReturning() {
        return returning;
    }

    public void setReturning(boolean returning) {
        this.returning = returning;
    }
}

