
package Classes;

import java.io.Serializable;

/**
 * Apartment class. Stores details of apartments
 * @author Zuzanna Kanafa
 */
public class Apartment implements Serializable{

    private String street;
    private String postCode;
    private String suburb;
    private Double dayPrice;
    
    public Apartment(){
        
    }

    public Apartment(String street, String postCode, String suburb, Double dayPrice) {
        this.street = street;
        this.postCode = postCode;
        this.suburb = suburb;
        this.dayPrice = dayPrice;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public Double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(Double dayPrice) {
        this.dayPrice = dayPrice;
    } 
}
