package Utilities;

import Classes.Booking;
import Classes.DirectBooking;
import Classes.OnlineBooking;
import Dal.DataAccess;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * Booking table model class
 * @author Zuzanna Kanafa
 */
public class BookingTableModel extends AbstractTableModel{

    private ArrayList<Booking> bookingList = new ArrayList<>();
    String[] columnNames = {"RefNo", "address", "name", "surname", "phone", 
        "email", "checkIn", "days", "quoted", "source"};
    
    public BookingTableModel(){
        bookingList.clear();
        bookingList = DataAccess.getAllBookings();
        bookingList = sortByDate();
    }
    
    public void reload(){
        bookingList.clear();
        bookingList = DataAccess.getAllBookings();
        bookingList = sortByDate();
    }
    public void showOnlyByAppartment(String appartmentName){
        bookingList.clear();
        ArrayList<Booking> temp = DataAccess.getAllBookings();
        for(Booking b:temp){
            if((b.getAddress()).equals(appartmentName))
                bookingList.add(b);
        }
        bookingList = sortByDate();
    }
    public void showOnlyByName(String name, String surname){
        bookingList.clear();
        ArrayList<Booking> temp = DataAccess.getAllBookings();
        if(surname.isEmpty()){
            for(Booking b:temp){
                if((b.getGuestName()).equals(name))
                    bookingList.add(b);
            }
        }else if(name.isEmpty()){
            for(Booking b:temp){
                if((b.getGuestSurname()).equals(surname))
                    bookingList.add(b);
            }
        }
        else{
            for(Booking b:temp){
                if((b.getGuestSurname()).equals(surname)&&(b.getGuestName()).equals(name))
                    bookingList.add(b);
            }
        }
        bookingList = sortByDate();
    }
    @Override
    public int getRowCount() {
        return bookingList.size();
    }

    @Override
    public int getColumnCount() {
       return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Booking b = bookingList.get(row);
        switch(column){
            case 0:
                return b.getRefNo();
            case 1:
                return b.getApBooked().getStreet();
            case 2:
                return b.getGuestName();
            case 3:
                return b.getGuestSurname();
            case 4:
                return b.getGuestPhone();
            case 5:
                return b.getGuestEmail();
            case 6:
                return b.getCheckIn();
            case 7:
                return b.getDays();
            case 8:
                return b.getQuoted();
            case 9:
                if(b instanceof OnlineBooking){
                    return "online";
                }
                if(b instanceof DirectBooking){
                    return "direct";
                }
        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
       return columnNames[column];
    }
    
    public Booking getRow(int row) {
       Booking b = bookingList.get(row);
       return b;
    }
    public ArrayList<Booking> sortByDate(){
        int n = bookingList.size(); 
        for (int i = 0; i < n-1; i++){ 
            for (int j = 0; j < n-i-1; j++) 
                if (bookingList.get(j).getCheckIn().after(bookingList.get(j+1).getCheckIn())) 
                { 
                    // swap arr[j+1] and arr[i] 
                    Booking temp = bookingList.get(j); 
                    bookingList.set(j, bookingList.get(j+1)); 
                    bookingList.set((j+1), temp);
                } 
        }
        return bookingList;
    }
}

