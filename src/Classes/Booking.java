
package Classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Booking Abstract class. Stores common for OnlineBooking and DirectBooking
 * @author Zuzanna Kanafa
 */
public abstract class Booking implements Serializable{
    private int refNo;
    private Apartment apBooked;
    private String guestName;
    private String guestSurname;
    private String guestPhone;
    private String guestEmail;
    private Date checkIn;
    private int days;
    private double quoted;

    public Booking(int refNo, Apartment apBooked, String guestName, String guestSurname, String guestPhone, 
            String guestEmail, Date checkIn, int days) {
        this.refNo = refNo;
        this.apBooked = apBooked;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.checkIn = checkIn;
        this.days = days;
    }

    public int getRefNo() {
        return refNo;
    }

    public Apartment getApBooked() {
        return apBooked;
    }

    public void setApBooked(Apartment apBooked) {
        this.apBooked = apBooked;
    }
    public String getAddress(){
        return this.apBooked.getStreet();
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }
    
    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getQuoted() {
        return quoted;
    }
    public void setQuoted(Double quoted) {
        this.quoted = quoted;
    }
    
    /**
     * Abstract class calculating quoted price of the booking
     * @return Double as total quoted price of booking
     */
    public abstract Double calcPrice();
}
