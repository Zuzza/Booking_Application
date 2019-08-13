
package gui;

import Dal.DataAccess;
import static Utilities.Validation.validateString;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Update booking Gui class
 * @author Zuzanna Kanafa
 */
public class UpdateBookingGui extends JFrame implements ActionListener{
    private int id;
    JButton btnUpdate = new JButton("Update");
    JButton btnBack = new JButton("Back");
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel pnlCenter = new JPanel(new GridLayout(2, 2, 20, 20));
    JLabel title = new JLabel("Update booking details", JLabel.CENTER);
    JTextField txfName = new JTextField(20);
    JTextField txfSurname = new JTextField(20);
    
    public UpdateBookingGui(String name, String surname, int id){
        this.id=id;
        this.setTitle("Update booking");
        this.setVisible(true);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setLayout(new BorderLayout(65, 15));
        this.add(pnlBottom, BorderLayout.SOUTH);
        this.add(title, BorderLayout.NORTH);
        this.add(pnlCenter, BorderLayout.CENTER);
        this.add(new JLabel(""), BorderLayout.WEST);
        this.add(new JLabel(""), BorderLayout.EAST);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        title.setForeground(Color.gray);
        pnlCenter.add(new JLabel("Name:", JLabel.TRAILING));
        pnlCenter.add(txfName);
        pnlCenter.add(new JLabel("Last name:", JLabel.TRAILING));
        pnlCenter.add(txfSurname);
        pnlBottom.add(btnUpdate);
        txfName.setText(name);
        txfSurname.setText(surname);
        
        pnlBottom.add(btnUpdate);
        pnlBottom.add(btnBack);
        btnBack.addActionListener(this);
        btnUpdate.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == btnBack){
            SearchBookingGui gu = new SearchBookingGui();
            this.dispose();
        }
        if(ev.getSource() == btnUpdate){
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
            DataAccess.updateBooking(txfName.getText(), txfSurname.getText(), id);
            SearchBookingGui gu = new SearchBookingGui();
            this.dispose();
        }
    }
}
