package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.model.Patron;

public class DeliteMemberWindow extends JFrame implements ActionListener {
    private MainWindow mw; //declare new main window
    private JTextField idText = new JTextField(); //declare new text box for members id
    private JTextField visText = new JTextField(); //declare new text box for members status 

    private JButton updateBtn = new JButton("Update"); //declare new update button 
    private JButton cancelBtn = new JButton("Cancel"); //declare new cancel button

    public DeliteMemberWindow(MainWindow mw) {
        this.mw = mw; //set main window as this one
        initialize(); //call main code
    }
    
    private void initialize() { //main code function
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //load ui manager
        } catch (Exception ex) {
        	System.out.println(ex);
        }

        setTitle("Delite/Update member");//set the title of the frame

        setSize(300, 200); //set the window size
        JPanel topPanel = new JPanel(); //create new top panel
        topPanel.setLayout(new GridLayout(5, 2)); //add a grid layout for top panel
        topPanel.add(new JLabel("Patron ID : "));// add label for patrons id to top panel
        topPanel.add(idText); //insert text box to the top panel for patrons id
        topPanel.add(new JLabel("Visible : ")); //add label for patrons visibility to top panel
        topPanel.add(visText); //insert text box for visibility to top panel

        JPanel bottomPanel = new JPanel(); //declare new bottom panel
        bottomPanel.setLayout(new GridLayout(1, 3)); //set the id of bottom panel
        bottomPanel.add(new JLabel("     ")); //add empty space to the layout
        bottomPanel.add(updateBtn); //add update button to bottom panel
        bottomPanel.add(cancelBtn); //insert cancel button to bottom panel

        updateBtn.addActionListener(this); //add action listener for update button
        cancelBtn.addActionListener(this); //add action listener for cancel button

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //place top panel into center to main frame
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //put bottom panel to south into main frame
        setLocationRelativeTo(mw); //set location of main frame

        setVisible(true); //set main frame as visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateBtn) { //check if update button has been clicked
            updateMember(); //call update function
        } else if (ae.getSource() == cancelBtn) { //check if cancel button has been clicked
            this.setVisible(false); //hide the frame
        }
    }

    private void updateMember() { 
        String id = idText.getText();//get the members id from text box
        String visibe = visText.getText(); //get the visibility status from the text box
		Patron pt = new Patron(); //create a object of patron
		pt.removePatron(id, visibe); //call remove patron to change the status of visibility
		
		mw.displayMembers(); //show the members in previous frame
		this.setVisible(false); //close the pop up
    }
}