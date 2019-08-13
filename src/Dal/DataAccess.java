
package Dal;

import Classes.Apartment;
import Classes.Booking;
import Classes.DirectBooking;
import Classes.OnlineBooking;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Static class containing methods manipulating Data in db_booking Database
 * @author Zuzanna Kanafa
 */
public class DataAccess {
    
    /**
     * Method creating connection with database
     * @return Connection with db_booking Database
     */
    public static Connection getConnection(){
        Connection con = null;
        String url = ConnectionDetails.getUrl();
        String userName = ConnectionDetails.getUserName();
        String passWord = ConnectionDetails.getPassWord();
       try{
            Class.forName(ConnectionDetails.getDriver());
            con = DriverManager.getConnection(url, userName, passWord);
        }catch(SQLException ex) {
            ex.printStackTrace();
        }catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
       return con;
    }
    
    /**
     * Method Adding apartment to table appartments in db_bookings database
     * @param address String for address of the apartment
     * @param post string for post code of apartment
     * @param suburb String for suburb of the apartment
     * @param price Double value for the daily price of the apartment
     */
    public static void insertAppartment(String address, String post, 
            String suburb, double price, boolean showConfirmation){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "INSERT INTO appartments (address, postCode, suburb, dayPrice) "
                    + "Values('"+address+"','"+post+"','"+suburb+"','"+price+"')";
            stmt.executeUpdate(sql);
            con.close();
            if(showConfirmation)
                JOptionPane.showMessageDialog(null, "Appartment saved in Database");
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Apartment already exists");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method deleting apartment from appartments table from database
     * @param address of the apartment to delete
     */
    public static void deleteAppartment(String address){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM appartments WHERE address='"+address+"'";
            r = stmt.executeQuery(sql);
            if(!(r.next())){
                JOptionPane.showMessageDialog(null, "Booking not found in Database");
                return;
            }
            sql = "DELETE FROM appartments WHERE address='"+address+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Appartment deleted from Database");
            con.close();
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Cannot delete apartment with bookings");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Retrieves all apartments records from database and place them in ArrayList
     * @return ArrayList of the apartemets
     */
    public static ArrayList<Apartment> getAllApartments(){
        ArrayList<Apartment> a = new ArrayList<Apartment>();
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        a.clear();

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM appartments";
            r = stmt.executeQuery(sql);
            
            while(r.next()){
                a.add(new Apartment(r.getString("address"), r.getString("postCode"), 
                        r.getString("suburb"), r.getDouble("dayPrice")));
            }
            con.close();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    /**
     * Method extracting names of the apartments from arrayList and returning them 
     * as an Array of Strings
     * @param apList ArrayList of apartments
     * @return Array of Strings with apartment names
     */
    public static String[] getAppartmentsNames(ArrayList<Apartment> apList){
        String[] s = new String[apList.size()];
        for(int i = 0; i < apList.size(); i++){
           s[i] = apList.get(i).getStreet();
        }
        return s;
    }

    /**
     * Method searching for apartment in appartments table in Database
     * @param apName name of the apartment to search for
     * @return Apartment if found or null if not found
     */
    public static Apartment findApartmentInDb(String apName){
        ArrayList<Apartment> a = getAllApartments();
        for(Apartment apart : a){
            if(apart.getStreet().equalsIgnoreCase(apName))
                return apart;
        }
        return null;
    }
    
    /**
     * Retrieves all bookings from database and returns them as an ArrayList
     * @return ArrayList of bookings
     */
    public static ArrayList<Booking> getAllBookings(){
        ArrayList<Booking> b = new ArrayList<Booking>();
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        b.clear();

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM bookings";
            r = stmt.executeQuery(sql);
            
            while(r.next()){
                Apartment apart = findApartmentInDb(r.getString("address"));
                int sour = r.getInt("source");
                if(sour == 1 || sour == 2 || sour == 3){
                    b.add(new OnlineBooking(r.getInt("bookingID"), apart, r.getString("name"), 
                            r.getString("surname"), r.getString("phone"), r.getString("email"), 
                            r.getDate("checkin"), r.getInt("days"), sour));
                }
                else{
                    b.add(new DirectBooking(r.getInt("bookingID"), apart, r.getString("name"), 
                            r.getString("surname"), r.getString("phone"), r.getString("email"), 
                            r.getDate("checkin"), r.getInt("days"), r.getBoolean("returning")));
                }
            }
            con.close();
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * Method inserting OnlineBooking object to bookings table in database
     * @param b OnlineBooking to be inserted
     */
    public static void insertOnlineBooking(OnlineBooking b, boolean showConfirmation){
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null; 
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(b.getCheckIn()); 

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "INSERT INTO bookings (bookingID, address, name, surname, "
                    + "phone, email, checkin, days, quoted, source) "
                    + "Values('"+b.getRefNo()+"','"+b.getAddress()+"','"
                    + ""+b.getGuestName()+"','"+b.getGuestSurname()+"','"+b.getGuestPhone()
                    +"','"+b.getGuestEmail()+"','"+strDate+"','"+b.getDays()+"','"
                    +b.getQuoted()+"','"+b.getBookingSource()+"')";
            stmt.executeUpdate(sql);
            con.close();
            if(showConfirmation)
                JOptionPane.showMessageDialog(null, "Booking saved in Database");
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "ID already exists");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method inserting DirectBooking object to bookings table in database
     * @param b DirectBooking to be inserted
     */
    public static void insertDirectBooking(DirectBooking b, boolean showConfirmation){
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null; 
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(b.getCheckIn());
        String returning;
        if(b.isReturning())
            returning="TRUE";
        else
            returning="FALSE";
        
        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "INSERT INTO bookings (bookingID, address, name, surname, "
                    + "phone, email, checkin, days, quoted, returning) "
                    + "Values('"+b.getRefNo()+"','"+b.getAddress()+"','"
                    + ""+b.getGuestName()+"','"+b.getGuestSurname()+"','"+b.getGuestPhone()
                    +"','"+b.getGuestEmail()+"','"+strDate+"','"+b.getDays()+"','"
                    +b.getQuoted()+"',"+returning+")";
            stmt.executeUpdate(sql);
            con.close();
            if(showConfirmation)
                JOptionPane.showMessageDialog(null, "Booking saved in Database");
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "ID already exists");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method deleting selected booking from the database
     * @param id integer value of Booking reference number to be deleted
     */
    public static void deleteBooking(int id){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM bookings WHERE bookingID='"+id+"'";
            r = stmt.executeQuery(sql);
            if(!(r.next())){
                JOptionPane.showMessageDialog(null, "Please select booking first");
                return;
            }
            sql = "DELETE FROM bookings WHERE bookingID='"+id+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Booking deleted from Database");
            con.close();
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Issues with database");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method updating name and surname details of the booking
     * @param name String for new name of the booking 
     * @param last String for new last name of the booking 
     * @param id Integer value of ref number of the booking to be updated
     */
    public static void updateBooking(String name, String last, int id){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "UPDATE bookings SET name='"+name+"',surname='"+last+"' "
            + "WHERE bookingID='"+id+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Booking updated in Database");
            con.close();
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Issues with database");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method deleting all records from bookings table
     */
    public static void emptyBookingTable(){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "DELETE FROM bookings";
            stmt.executeUpdate(sql);
            con.close();
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Issues with database");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method deleting all records from apartments table
     */
    public static void emptyApartmentTable(){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;

        try{
            con = getConnection();
            stmt = con.createStatement();
            String sql = "DELETE FROM appartments";
            stmt.executeUpdate(sql);
            con.close();
        }
        catch(MySQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Issues with database");
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Method Adding all apartments from ArrayList to the appartments table in database
     * @param list ArrayList of apartments to be added to database
     */
    public static void insertAllAppartment(ArrayList<Apartment> list){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        for(Apartment a: list){
            insertAppartment(a.getStreet(), a.getPostCode(), 
            a.getSuburb(), a.getDayPrice(), false);
        }
    }

    /**
     * Method Adding all bookings from ArrayList to the bookings table in database
     * @param list ArrayList of bookings to be added to database
     */
    public static void insertAllBookings(ArrayList<Booking> list){
    
        Connection con = null;
        Statement stmt = null;
        ResultSet r = null;
        for(Booking b: list){
            if(b instanceof DirectBooking){
                insertDirectBooking((DirectBooking) b, false);
            }
            if(b instanceof OnlineBooking){
                insertOnlineBooking((OnlineBooking) b, false);
            }
        }
    }
}