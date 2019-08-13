
package gui;

import Classes.Apartment;
import Dal.DataAccess;
import Utilities.ApartmentTableModel;
import Utilities.Validation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Add apartment JFrame Gui
 * @author Zuzanna Kanafa
 */
public class AddApartmentGui extends JFrame implements ActionListener, MouseListener{
    JPanel pnlCenter = new JPanel(new GridLayout(4, 2, 20, 20));
    JPanel pnlMiddle = new JPanel(new GridLayout(2, 1, 20, 20));
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JButton btnAddApartment = new JButton("Add");
    JButton btnBack = new JButton("Back to menu");
    JButton btnDelete = new JButton("Delete");
    //JTextField txfAppNo = new JTextField(40);
    JTextField txfStreet = new JTextField(40);
    JTextField txfPostCode = new JTextField(40);
    JTextField txfSuburb = new JTextField(40);
    JTextField txfDailyPrice = new JTextField(40);
    JLabel title = new JLabel("Add new Apartment", JLabel.CENTER);
    JTable tblApartments = new JTable();
    JScrollPane scroll = new JScrollPane(tblApartments, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    ApartmentTableModel aptModel;
    
    public AddApartmentGui(){
        this.setTitle("Melbourne Add Apartment");
        this.setVisible(true);
        this.setSize(700, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(65, 15));
        this.add(pnlMiddle, BorderLayout.CENTER);
        pnlMiddle.add(pnlCenter);
        pnlMiddle.add(scroll);
        aptModel = new ApartmentTableModel();
        tblApartments.setModel(aptModel);
        this.add(pnlBottom, BorderLayout.SOUTH);
        this.add(pnlTop, BorderLayout.NORTH);
        pnlTop.add(title);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        title.setForeground(Color.gray);
        this.add(new JLabel(""), BorderLayout.WEST);
        this.add(new JLabel(""), BorderLayout.EAST);
        pnlCenter.add(new JLabel("Full Address:", JLabel.TRAILING));
        pnlCenter.add(txfStreet);
        pnlCenter.add(new JLabel("Post Code:", JLabel.TRAILING));
        pnlCenter.add(txfPostCode);
        pnlCenter.add(new JLabel("Suburb:", JLabel.TRAILING));
        pnlCenter.add(txfSuburb);
        pnlCenter.add(new JLabel("Daily Price in AUD:", JLabel.TRAILING));
        pnlCenter.add(txfDailyPrice);
        pnlBottom.add(btnAddApartment);
        pnlBottom.add(btnDelete);
        pnlBottom.add(btnBack);
        
        btnBack.addActionListener(this);
        btnDelete.addActionListener(this);
        btnAddApartment.addActionListener(this);
        tblApartments.addMouseListener(this);
        
        this.validate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        if(ev.getSource() == btnBack){
            this.dispose();
            MainMenu app = new MainMenu();
        }
        if(ev.getSource() == btnDelete){
            if(txfStreet.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Please select apartment to delete");
            }else{
                int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you wanna delete this apartment?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if(dialogButton == JOptionPane.YES_OPTION){
                    DataAccess.deleteAppartment(txfStreet.getText());
                    refreshFields();
                }
            }
        }
        if(ev.getSource() == btnAddApartment){
            if((txfStreet.getText().equals("")) || (txfPostCode.getText().equals("")) 
                    || (txfSuburb.getText().equals("")) || (txfDailyPrice.getText().equals(""))){
                JOptionPane.showMessageDialog(null, "Please fill in all necessary fields");
            }else{
                String adr = Validation.validateStreet(txfStreet.getText());
                if(adr == null){
                    txfStreet.setText("");
                    return;
                }
                String post = Validation.validatePostCode(txfPostCode.getText());
                if(post == null){
                    txfPostCode.setText("");
                    return;
                }
                String sub = Validation.validateString(txfSuburb.getText(), "Suburb name");
                if(sub == null){
                    txfSuburb.setText("");
                    return;
                }
                Double pri = Validation.validatePrice(txfDailyPrice.getText());
                if(pri < 0){
                    txfDailyPrice.setText("");
                    return;
                }
                DataAccess.insertAppartment(adr, post, sub, pri, true);
                refreshFields();
            }
        }
 
    }

    @Override
    public void mouseClicked(MouseEvent evm) {
        if(evm.getClickCount() == 2){
            int selRow = tblApartments.getSelectedRow();
            Apartment temp = aptModel.getRow(selRow);
            txfDailyPrice.setText(temp.getDayPrice().toString());
            txfPostCode.setText(temp.getPostCode());
            txfSuburb.setText(temp.getSuburb());
            txfStreet.setText(temp.getStreet());
        }
    }
    
    /**
     * Method clearing textboxes in JFrame
     */
    public void refreshFields(){
        txfDailyPrice.setText("");
        txfPostCode.setText("");
        txfSuburb.setText("");
        txfStreet.setText("");
        aptModel.reload();
        aptModel.fireTableDataChanged();
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

}