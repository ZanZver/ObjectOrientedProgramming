package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddMemberWindow extends JFrame implements ActionListener {
    private MainWindow mw; //declare new window/frame
    private JTextField nameText = new JTextField(); //declare new text field for name
    private JTextField phoneText = new JTextField(); //declare new text field for phone
    private JTextField visText = new JTextField(); //declare new text field for visibility

    private JButton addBtn = new JButton("Add"); //declare add button
    private JButton cancelBtn = new JButton("Cancel"); //declare cancel button

    public AddMemberWindow(MainWindow mw) {
        this.mw = mw; //set window as this window
        initialize(); //call main function 
    }
    private void initialize() { //main code function
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//set ui manager
        } catch (Exception ex) {
        	System.out.println(ex); //inform about error if occurs
        }

        setTitle("Add new member"); //set title of the frame

        setSize(300, 200); //set size of the frame
        JPanel topPanel = new JPanel(); //create new top panel
        topPanel.setLayout(new GridLayout(5, 2)); //create grid layout for top panel
        topPanel.add(new JLabel("Name : ")); //add label name to top panel
        topPanel.add(nameText); //add text box for name to top panel
        topPanel.add(new JLabel("Phone : ")); //add label phone to top panel
        topPanel.add(phoneText); //add text box to top panel for phone
        topPanel.add(new JLabel("Visible : ")); //add label visible to top panel
        topPanel.add(visText); //add text box for visible to top panel
        
        JPanel bottomPanel = new JPanel(); //create new bottom panel
        bottomPanel.setLayout(new GridLayout(1, 3)); //create new grid layout
        bottomPanel.add(new JLabel("     ")); //add empty space
        bottomPanel.add(addBtn); //place add button to bottom panel
        bottomPanel.add(cancelBtn);  //add cancel button to bottom panel

        addBtn.addActionListener(this); //set action listener for add button
        cancelBtn.addActionListener(this); //set action listener for button cancel

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //put top panel to main frame into center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);//put bottom panel to main frame to south
        setLocationRelativeTo(mw);//set location of the main frame

        setVisible(true);//make main frame visible
    }

    private void addMember() {//add member code
       try {
    	Command addPatron = new AddPatron(nameText.getText(), phoneText.getText(), Boolean.parseBoolean(visText.getText()));//declare command addPatron and insert data from text boxes into it
		addPatron.execute(mw.getLibrary(), LocalDate.now());//execute command 
	    mw.displayMembers(); //show members
	    this.setVisible(false); //hide the pop up
       } catch (LibraryException e) {
    	System.out.println(e); //inform about error 
		e.printStackTrace(); 
       }
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		 if (ae.getSource() == addBtn) { //check if add button has been called
			 addMember(); //call add member function
	        } else if (ae.getSource() == cancelBtn) { //check if cancel button has been called
	            this.setVisible(false); //hide pop up
	        }	
	}
}