package gui;

import Classes.Apartment;
import Classes.DirectBooking;
import Classes.OnlineBooking;
import Dal.DataAccess;
import static Utilities.Validation.validateDate;
import static Utilities.Validation.validateDays;
import static Utilities.Validation.validateEmail;
import static Utilities.Validation.validatePhone;
import static Utilities.Validation.validateRefNo;
import static Utilities.Validation.validateString;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Add booking Gui
 * @author Zuzanna Kanafa
 */
public class AddBookingGui extends JFrame implements ActionListener{
    private ArrayList<Apartment> apList = DataAccess.getAllApartments();
    private String[] apNames = DataAccess.getAppartmentsNames(apList);
    
    JPanel pnlCenter = new JPanel(new GridLayout(10, 2, 20, 20));
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JButton btnAddBooking = new JButton("Add");
    JButton btnBack = new JButton("Back to menu");
    JTextField txfRefNo = new JTextField(10);
    JTextField txfName = new JTextField(40);
    JTextField txfSurname = new JTextField(40);
    JTextField txfPhone = new JTextField(40);
    JTextField txfEmail = new JTextField(40);
    JTextField txfDays = new JTextField(40);
    JTextField txfTotal = new JTextField(40);
    JLabel title = new JLabel("Add new Booking", JLabel.CENTER);
    JComboBox jcbAppartment = new JComboBox(apNames);
    JRadioButton radOnline = new JRadioButton("Online");
    JRadioButton radDir = new JRadioButton("Direct");
    ButtonGroup group = new ButtonGroup();
    JPanel radioPanel = new JPanel(new GridLayout(1, 2));
    
    //Radio buttons apperaing for the returning type of booking
    JPanel radioPanelReturning = new JPanel(new GridLayout(2, 1, 0, 5));
    JRadioButton radReturning = new JRadioButton("Returning");
    JRadioButton radNew = new JRadioButton("New customer");
    ButtonGroup returningGroup = new ButtonGroup();
    
    //Source ComboBox
    String[] sourceStr = {"Booking.com", "AirBnb", "Expedia"};
    JComboBox jcbSource = new JComboBox(sourceStr);
    
    //Date Panel
    JDateChooser dtcDate = new JDateChooser();

    public AddBookingGui() {
        this.setTitle("Melbourne Add Booking");
        this.setVisible(true);
        this.setSize(500, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(65, 15));
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnlBottom, BorderLayout.SOUTH);
        this.add(pnlTop, BorderLayout.NORTH);
        pnlTop.add(title);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        title.setForeground(Color.gray);
        this.add(new JLabel(""), BorderLayout.WEST);
        this.add(new JLabel(""), BorderLayout.EAST);
        txfTotal.setEnabled(false);

        pnlCenter.add(new JLabel("Appartment:", JLabel.TRAILING));
        pnlCenter.add(jcbAppartment);
        pnlCenter.add(new JLabel("Reference Number:", JLabel.TRAILING));
        pnlCenter.add(txfRefNo);
        pnlCenter.add(new JLabel("Guest Name:", JLabel.TRAILING));
        pnlCenter.add(txfName);
        pnlCenter.add(new JLabel("Guest Last Name:", JLabel.TRAILING));
        pnlCenter.add(txfSurname);
        pnlCenter.add(new JLabel("Guest Phone number:", JLabel.TRAILING));
        pnlCenter.add(txfPhone);
        pnlCenter.add(new JLabel("Guest Email:", JLabel.TRAILING));
        pnlCenter.add(txfEmail);
        pnlCenter.add(new JLabel("Check in Date:", JLabel.TRAILING));
        pnlCenter.add(dtcDate);
        pnlCenter.add(new JLabel("Length of stay in days:", JLabel.TRAILING));
        pnlCenter.add(txfDays);
        pnlCenter.add(new JLabel("Booking Source:", JLabel.TRAILING));
        
        //Add RadioPanel
        pnlCenter.add(radioPanel);
        group.add(radOnline);
        group.add(radDir);
        radioPanel.add(radOnline);
        radioPanel.add(radDir);
        pnlCenter.add(new JLabel(""));
        
        //Add RadioPanelReturning
        pnlBottom.add(btnAddBooking);
        pnlBottom.add(btnBack);

        btnAddBooking.addActionListener(this);
        btnBack.addActionListener(this);
        radOnline.addActionListener(this);
        radDir.addActionListener(this);
        
        this.validate();
        this.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == radDir){
            pnlCenter.remove(jcbSource);
            pnlCenter.add(radioPanelReturning);
            returningGroup.add(radReturning);
            returningGroup.add(radNew);
            radioPanelReturning.add(radReturning);
            radioPanelReturning.add(radNew);
            this.validate();
            this.repaint();
        }
        if(ev.getSource() == radOnline){
            pnlCenter.remove(radioPanelReturning);
            pnlCenter.add(jcbSource);
            this.validate();
            this.repaint();
        }
        
        if (ev.getSource() == btnBack) {
            this.dispose();
            MainMenu app = new MainMenu();
        }
        if (ev.getSource() == btnAddBooking) {
            if(apNames.length == 0){
                JOptionPane.showMessageDialog(null, "No apartments in database");
                return;
            }
            Apartment apartmentBooked = apList.get(jcbAppartment.getSelectedIndex());
            if(txfRefNo.getText().equals("") || txfName.getText().equals("")|| 
                    txfSurname.getText().equals("")|| txfPhone.getText().equals("")
                    || txfEmail.getText().equals("") || txfDays.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                return;
            }
            int ref = validateRefNo(txfRefNo.getText());
            if(ref == -1){
                txfRefNo.setText("");
                return;
            }
            String gName = validateString(txfName.getText(), "Name");
            if(gName == null){
                txfName.setText("");
                return;
            }
            String gSurname = validateString(txfSurname.getText(), "Last Name");
            if(gSurname == null){
                txfSurname.setText("");
                return;
            }
            String gPhone = validatePhone(txfPhone.getText());
            if(gPhone == null){
                txfPhone.setText("");
                return;
            }
            String gEmail = validateEmail(txfEmail.getText());
            if(gEmail == null){
                txfEmail.setText("");
                return;
            }
            Date date = validateDate(dtcDate.getDate());
            if(date == null){
                return;
            }
            int days = validateDays(txfDays.getText());
            if(days == -1){
                txfDays.setText("");
                return;
            }
            if((!radOnline.isSelected()) && (!radDir.isSelected())){
                JOptionPane.showMessageDialog(null, "Select Booking type");
                return;
            }
            if(radOnline.isSelected()){
                int source = (jcbSource.getSelectedIndex())+1;
                OnlineBooking onB = new OnlineBooking(ref, apartmentBooked, gName, 
                        gSurname, gPhone, gEmail, date, days, source);
                DataAccess.insertOnlineBooking(onB, true);
                clearTextboxes();
            }
            if(radDir.isSelected()){
                boolean returning=false;
                if((!radReturning.isSelected()) && (!radNew.isSelected())){
                    JOptionPane.showMessageDialog(null, "Select customer type");
                    return;
                }
                if(radReturning.isSelected())
                    returning = true;
                if(radNew.isSelected())
                    returning = false;
                
                DirectBooking diB = new DirectBooking(ref, apartmentBooked, gName, 
                        gSurname, gPhone, gEmail, date, days, returning);
                DataAccess.insertDirectBooking(diB, true);
                clearTextboxes();
            }
        }
    }
    public void clearTextboxes(){
        txfRefNo.setText("");
        txfName.setText("");
        txfSurname.setText("");
        txfPhone.setText("");
        txfEmail.setText("");
        txfDays.setText("");
    }
}
