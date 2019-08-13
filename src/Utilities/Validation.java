
package Utilities;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * Class containing static validate methods
 * @author Zuzanna Kanafa
 */
public class Validation {
    /**
     * Method validating unit number, checks if value can be converted to int
     * @param unit number as a String
     * @return Integer as a valid unit No or -1 as invalid value
     */
    public static int validateUnit(String unit){
        try{
            return Integer.parseInt(unit);
        }catch(NumberFormatException ex){
            return -1;
        }
    }
    
    /**
     * Method Validating Street name and number
     * @param street Street name as a string
     * @return validated Street name as a string, returns null if not valid
     */
    public static String validateStreet(String street){
        if(street.length() < 6){
            JOptionPane.showMessageDialog(null, "Address must be at least 6 letters long");
            return null;
        }
        else
            return street;
    }
    
    /** 
     * Method validating Post code
     * @param code Post code as 4 digit String
     * @return String with 4 digits as valid post code or null if not valid
     */
    public static String validatePostCode(String code){
        try{
            Integer.parseInt(code);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Post Code must be 4 numbers long");
            return null;
        }
        if(((code.length() == 4)) && (Integer.parseInt(code) > 1999) && (Integer.parseInt(code) <= 9999)){
            return code;
        }
        else{
            JOptionPane.showMessageDialog(null, "Post Code must be between 2000 and 9999");
            return null;
        }
    }
    
    /**
     * Validates string value. Must be just letters or $ and at lest 4 letters long
     * @param str value to validate
     * @return String as valid value or null if value was invalid
     */
    public static String validateString(String str){
        String regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        if(str.length() < 4)
            return null;
        if (str.matches(regex)) 
            return str;
        else
            return null;
    }
    
    /**
     * Method validating string. Can contain only letters and Spaces
     * @param str String to validate
     * @param valueName Name of the value to show in error message
     * @return validated String or null if the value was not valid
     */
    public static String validateString(String str, String valueName){
        String regex = "^[a-zA-Z\\s]*$";
        if(str.length() < 3){
            JOptionPane.showMessageDialog(null, valueName + " has to be at "
                    + "least 3 characters long");
            return null;
        }
        if (str.matches(regex)) {
            return str;
        }
        else{
            JOptionPane.showMessageDialog(null, valueName + " cannot contain "
                    + "numbers or special characters");
            return null;
        }
    }
    
    /**
     * Method validating price
     * @param price String value to validate
     * @return Double value if correct or -1.0 if value is not correct
     */
    public static Double validatePrice(String price){
        Double temp;
        try{
            temp = Double.parseDouble(price);
            if(temp.toString().length() > 7){
                JOptionPane.showMessageDialog(null, "price is to high");
                return -1.0;
            }
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Enter correct price value");
            return -1.0;
        }
        if(temp <= 0){
            JOptionPane.showMessageDialog(null, "Enter correct price value");
            return -1.0;
        }
        else 
            return temp;
    }
    
    /**
     * Method validating Email address in correct form with @ and domain name
     * @param email String to validate
     * @return validated String or null if value not valid
     */
    public static String validateEmail(String email){
        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) { 
            return email;    
        }
        else{
            JOptionPane.showMessageDialog(null, "Please enter valid email address");
            return null;
        }
    }
    
    /**
     * Method validating phone number. Number must be 9 or 10 numbers only.
     * @param phone String to validate
     * @return Validated phone number as a String or null if value not valid
     */
    public static String validatePhone(String phone){
        String regex = "^[0-9]+$";
        if (phone.length()>8 && phone.length()<11 && phone.matches(regex)) { 
            return phone;
        }
        else{
            JOptionPane.showMessageDialog(null, "Please enter valid phone number."
                    + "\nAt least 9 digits long, without special characters or breaks");
            return null;
        }
    }
    
    /**
     * Method validating number of days. Value must be over 0 and not longer than 31.
     * @param unit String value to validate
     * @return validated value as an int or -1 if value is not valid
     */
    public static int validateDays(String unit){
        int temp;
        try{
            temp = Integer.parseInt(unit);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Please enter numeric "
                    + "value for number of days");
            return -1;
        }
        if(temp <= 0){
            JOptionPane.showMessageDialog(null, "Please Enter correct number of days");
            return -1;
        }
        if(temp>31){
            JOptionPane.showMessageDialog(null, "The maximum booking length is 31 days");
            return -1;
        }
        else
            return temp;
    }
    
    /**
     * Method validating date. Checks if date is not in the past
     * @param d Date object to validate
     * @return validated Date object or null if value not valid
     */
    public static Date validateDate(Date d){
        Date currentDate = new Date();
        try{
            if(d.compareTo(currentDate) < 0){
                JOptionPane.showMessageDialog(null, "You cannot make booking for past");
                return null;
            }else{
                return d;
            }
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Please select date");
        }
        return null;
    }

    /**
     * Method validating reference number of the booking
     * @param ref String value to validate
     * @return Validated int value or -1 if value was not valid
     */
    public static int validateRefNo(String ref){
        int temp;
        try{
            temp = Integer.parseInt(ref);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Reference number must contain only numbers");
            return -1;
        }
        if(temp < 0){
            JOptionPane.showMessageDialog(null, "Enter correct Reference number value");
            return -1;
        }
        else 
            return temp;
    }
}
