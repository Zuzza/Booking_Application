
package Utilities;

import Classes.Apartment;
import Dal.DataAccess;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Apartment Table model class
 * @author Zuzanna Kanafa
 */
public class ApartmentTableModel extends AbstractTableModel{

    private ArrayList<Apartment> apartmentList = new ArrayList<>();
    String[] columnNames = {"address", "postCode", "suburb", "dayPrice"};
    
    public ApartmentTableModel(){
        apartmentList.clear();
        apartmentList = DataAccess.getAllApartments();
    }
    
    public void reload(){
        apartmentList.clear();
        apartmentList = DataAccess.getAllApartments();
    }
    
    @Override
    public int getRowCount() {
        return apartmentList.size();
    }

    @Override
    public int getColumnCount() {
       return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Apartment apt = apartmentList.get(row);
        switch(column){
            case 0:
                return apt.getStreet();
            case 1:
                return apt.getPostCode();
            case 2:
                return apt.getSuburb();
            case 3:
                return apt.getDayPrice();
        }
        return null;
    }
    @Override
    public String getColumnName(int column) {
       return columnNames[column];
    }
    
    public Apartment getRow(int row) {
       Apartment apt = apartmentList.get(row);
       return apt;
    }
}
