
package Utilities;

import Classes.Apartment;
import Classes.Booking;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Static class containing methods to Read and write apartments and bookings
 * from and to binary file
 * @author Zuzanna Kanafa
 */
public class ReadWrite {
    
    /**
     * Static method reading all Apartments saved in Apartment.bin file
     * @return ArrayList of Apartments
     */
    public static ArrayList<Apartment> readApartments(){
        
        ArrayList<Apartment> list = new ArrayList<>();
        try{
            FileInputStream fin = new FileInputStream("Apartment.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);
            
            list = (ArrayList<Apartment>) oin.readObject();
            
            oin.close();
            
            return list;
        }
        catch(FileNotFoundException fnfEx){
            System.err.println("Issues with the file");
        }
        catch(IOException ioEx){
            System.err.println("Issues reading and writing");
        }
        catch(ClassNotFoundException cnfEx){
            System.err.println("Apartment class missing/corrupt");
        }
        return list;
    }
   
    /**
     * Static method saving all apartments from ArrayList to binary file Apartment.bin
     * @param list ArrayList of Apartments to save
     */
    public static void writeApartments(ArrayList<Apartment> list){
        try{
            FileOutputStream fos = new FileOutputStream("Apartment.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        }
        catch(FileNotFoundException fnfEx){
            System.err.println("Issues with the file");
        }
        catch(IOException ioEx){
            System.err.println("Issues with the file");
        }
    }

    /**
     * Static method reading all Bookings saved in Bookings.bin file
     * @return ArrayList of bookings restored from file
     */
    public static ArrayList<Booking> readBookings(){
        
        ArrayList<Booking> list = new ArrayList<>();
        try{
            FileInputStream fin = new FileInputStream("Bookings.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);
            
            list = (ArrayList<Booking>) oin.readObject();
            
            oin.close();
            
            return list;
        }
        catch(FileNotFoundException fnfEx){
            System.err.println("Issues with the file");
        }
        catch(IOException ioEx){
            System.err.println("Issues reading and writing");
        }
        catch(ClassNotFoundException cnfEx){
            System.err.println("Booking class missing/corrupt");
        }
        return list;
    }
   
    /**
     * Static method saving all bookings from ArrayList to binary file Bookings.bin
     * @param list ArrayList of Bookings to save in file
     */
    public static void writeBookings(ArrayList<Booking> list){
        try{
            FileOutputStream fos = new FileOutputStream("Bookings.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        }
        catch(FileNotFoundException fnfEx){
            System.err.println("Issues with the file");
        }
        catch(IOException ioEx){
            System.err.println("Issues with the file");
        }
    }
}
