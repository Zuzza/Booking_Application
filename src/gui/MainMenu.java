
package gui;

import Dal.DataAccess;
import Utilities.ReadWrite;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main Menu GUI of the application.
 * @author Zuzanna Kanafa
 */
public class MainMenu extends JFrame implements ActionListener{
  
    
    JButton btnAddB = new JButton("Add Booking");
    JButton btnAddA = new JButton("Add Apartment");
    JButton btnSearch = new JButton("Search for booking");
    JButton btnHelp = new JButton("Help");
    JButton btnBackup = new JButton("Backup database");
    JButton btnRestore = new JButton("Restore from backup");
    JPanel pnlButtons = new JPanel(new GridLayout(4, 1, 20, 20));
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    BufferedImage image = null;
    JLabel lblHeader = null;
    
    public MainMenu(){
        this.setTitle("Melbourne Apartments");
        this.setVisible(true);
        this.setSize(450, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(75,75));
        
        // Load image
        try {
            image = ImageIO.read(new File("./Melbourne.png"));
            lblHeader = new JLabel(new ImageIcon(image));
        } catch (IOException ex) {
            System.err.println("Issues with picture");
        }

        this.add(new JLabel(""), BorderLayout.WEST);
        this.add(new JLabel(""), BorderLayout.EAST);
        this.add(pnlBottom, BorderLayout.SOUTH);
        this.add(lblHeader, BorderLayout.NORTH);
        this.add(pnlButtons);
        // Add buttons to panel
        pnlButtons.add(btnAddA);
        pnlButtons.add(btnAddB);
        pnlButtons.add(btnSearch);
        pnlButtons.add(btnHelp);
        pnlBottom.add(btnBackup);
        pnlBottom.add(btnRestore);
        
        btnAddA.addActionListener(this);
        btnAddB.addActionListener(this);
        btnSearch.addActionListener(this);
        btnBackup.addActionListener(this);
        btnRestore.addActionListener(this);
        btnHelp.addActionListener(this);
        
        this.validate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == btnAddA){
            this.dispose();
            AddApartmentGui aaG = new AddApartmentGui();
        }
        if(ev.getSource() == btnAddB){
            this.dispose();
            AddBookingGui abG = new AddBookingGui();
        }
        if(ev.getSource() == btnSearch){
            this.dispose();
            SearchBookingGui sbG = new SearchBookingGui();
        }
        if(ev.getSource() == btnHelp){
            String document = "UserDoc.pdf";
            try{
                File f = new File(document);
                Desktop.getDesktop().open(f);
            }
            catch(IOException ioE){
                System.err.print(ioE.getMessage());
            } 
        }
        if(ev.getSource() == btnBackup){
            // Back up Database to file
            int sure = JOptionPane.showConfirmDialog(null, "Are you sure you wanna backup database?",
                    "Database backup",JOptionPane.YES_NO_OPTION);
            if(sure == JOptionPane.YES_OPTION){
                ReadWrite.writeApartments(DataAccess.getAllApartments());
                ReadWrite.writeBookings(DataAccess.getAllBookings());
            }
        }
        if(ev.getSource() == btnRestore){
            //Restore Database from file
            int sure = JOptionPane.showConfirmDialog(null, "Are you sure you wanna restore database?",
                    "Database restore",JOptionPane.YES_NO_OPTION);
            if(sure == JOptionPane.YES_OPTION){
                DataAccess.emptyBookingTable();
                DataAccess.emptyApartmentTable();
                DataAccess.insertAllAppartment(ReadWrite.readApartments());
                DataAccess.insertAllBookings(ReadWrite.readBookings());
            }
        }
    }
}
