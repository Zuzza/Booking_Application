
package gui;

import Classes.Apartment;
import Classes.Booking;
import Dal.DataAccess;
import Utilities.BookingTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * JFrame allowing to search for bookings stored in data base by name, 
 * surname or by apartment booked. Results are showed in Table. From this JFram 
 * is also possible to update or delete bookings.
 * @author Zuzanna Kanafa
 */
public class SearchBookingGui extends JFrame implements ActionListener, MouseListener,ItemListener{
    static int selRow= -1;
    private ArrayList<Apartment> apList = DataAccess.getAllApartments();
    private String[] apNames = DataAccess.getAppartmentsNames(apList);
    
    JPanel pnlCenter = new JPanel(new GridLayout(3, 4, 20, 20));
    JPanel pnlMiddle = new JPanel(new GridLayout(2, 1, 20, 20));
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JButton btnSearch = new JButton("Search");
    JButton btnUpdate = new JButton("Update");
    JButton btnBack = new JButton("Back to menu");
    JButton btnDelete = new JButton("Delete");
    JTextField txfName = new JTextField(20);
    JTextField txfLast = new JTextField(20);
    JComboBox cmbAppartment = new JComboBox(apNames);
    JLabel title = new JLabel("Saerch for booking", JLabel.CENTER);
    JTable tblBookings = new JTable();
    JScrollPane scroll = new JScrollPane(tblBookings, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    BookingTableModel bookModel;
    
    public SearchBookingGui(){
        this.setTitle("Search for booking");
        this.setVisible(true);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(65, 15));
        this.add(pnlMiddle, BorderLayout.CENTER);
        pnlMiddle.add(pnlCenter);
        pnlMiddle.add(scroll);
        bookModel = new BookingTableModel();
        tblBookings.setModel(bookModel);
        this.add(pnlBottom, BorderLayout.SOUTH);
        this.add(pnlTop, BorderLayout.NORTH);
        pnlTop.add(title);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        title.setForeground(Color.gray);
        this.add(new JLabel(""), BorderLayout.WEST);
        this.add(new JLabel(""), BorderLayout.EAST);
        pnlCenter.add(new JLabel(""));
        pnlCenter.add(new JLabel("Guest Name:", JLabel.TRAILING));
        pnlCenter.add(txfName);
        pnlCenter.add(new JLabel(""));
        pnlCenter.add(new JLabel(""));
        pnlCenter.add(new JLabel("Guest Last Name:", JLabel.TRAILING));
        pnlCenter.add(txfLast);
        pnlCenter.add(new JLabel(""));
        pnlCenter.add(new JLabel(""));
        pnlCenter.add(new JLabel("Appartment:", JLabel.TRAILING));
        pnlCenter.add(cmbAppartment);
        pnlCenter.add(new JLabel(""));
        
        pnlBottom.add(btnSearch);
        pnlBottom.add(btnUpdate);
        pnlBottom.add(btnDelete);
        pnlBottom.add(btnBack);
        
        btnBack.addActionListener(this);
        btnSearch.addActionListener(this);
        btnDelete.addActionListener(this);
        btnUpdate.addActionListener(this);
        cmbAppartment.addItemListener(this);
        tblBookings.addMouseListener(this);
        
        validateRepaint();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        if(ev.getSource() == btnBack){
            this.dispose();
            MainMenu app = new MainMenu();
        }
        if(ev.getSource() == btnUpdate){
            selRow = tblBookings.getSelectedRow();
            if(selRow>=0){
            Booking temp = bookModel.getRow(selRow);
            UpdateBookingGui uBGui = new UpdateBookingGui(
                    temp.getGuestName(), temp.getGuestSurname(), temp.getRefNo());
            this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Please select booking to update");
            }
        }
        if(ev.getSource() == btnSearch){
            if((txfName.getText().isEmpty())&&(txfLast.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Please enter data to search");
            }
            else{
                bookModel.showOnlyByName(txfName.getText().toString(),txfLast.getText().toString());
                validateRepaint();
            }
        }
        if(ev.getSource() == btnDelete){
            selRow = tblBookings.getSelectedRow();
            if(selRow<0){
                JOptionPane.showMessageDialog(null, "Please select booking to delete");
            }else{
                int sure = JOptionPane.showConfirmDialog(null, "Are you sure you wanna delete this booking?",
                        "Are you for sure sure?",JOptionPane.YES_NO_OPTION);
                if(sure == JOptionPane.YES_OPTION){
                    Booking temp = bookModel.getRow(selRow);
                    int i = temp.getRefNo();
                    DataAccess.deleteBooking(i);
                    bookModel.reload();
                    validateRepaint();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent evm) {
        if(evm.getClickCount() == 2){
            selRow = tblBookings.getSelectedRow();
            Booking temp = bookModel.getRow(selRow);
            txfName.setText(temp.getGuestName().toString());
            txfLast.setText(temp.getGuestSurname());
        }
    }
    
    public void refreshFields(){
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          Object item = event.getItem();
          bookModel.showOnlyByAppartment(apNames[cmbAppartment.getSelectedIndex()]);
          validateRepaint();
       }
    }
    /**
     * Method validating and repainting JFrame
     */
    public void validateRepaint(){
            this.validate();
            this.repaint();
    }
}
