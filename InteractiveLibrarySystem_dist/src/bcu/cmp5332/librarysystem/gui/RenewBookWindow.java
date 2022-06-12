package bcu.cmp5332.librarysystem.gui;

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

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

public class RenewBookWindow extends JFrame implements ActionListener {
    private MainWindow mw; //create a new window 
    private JTextField idbookText = new JTextField(); //create a new text field for book id
    private JTextField extendText = new JTextField(); //create a new text field for renew
    private JButton renewBtn = new JButton("Renew"); //create a button for renew
    private JButton cancelBtn = new JButton("Cancel"); //create a cancel button

    public RenewBookWindow(MainWindow mw) {
        this.mw = mw; //set this as main window 
        initialize(); //call function with main code
    }
    
    private void initialize() { //main code
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set ui manager
        } catch (Exception ex) {
        	System.out.println(ex); //print error message
        }

        setTitle("Renew book"); //set title of frame

        setSize(300, 200); //set size of frame
        JPanel topPanel = new JPanel(); //create a new panel
        topPanel.setLayout(new GridLayout(5, 2)); //create a new grid layout
        topPanel.add(new JLabel("ID book: ")); //create a label for book id
        topPanel.add(idbookText); //add a text box for id of book
        topPanel.add(new JLabel("Extend for (number of weeks): ")); //create a label for getting number of weeks that user wants to extend
        topPanel.add(extendText); //add a text box for the extended weeks

        JPanel bottomPanel = new JPanel(); //create another panel
        bottomPanel.setLayout(new GridLayout(1, 3)); //create a new grid layout for bottom panel
        bottomPanel.add(new JLabel("     ")); //put down an empty space 
        bottomPanel.add(renewBtn); //add renew button to the bottom panel
        bottomPanel.add(cancelBtn); //add cancel panel to bottom panel

        renewBtn.addActionListener(this); //add event listener for renew button
        cancelBtn.addActionListener(this); //add event listener for cancel button

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //add top panel to main frame to center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //add bottom panel to main frame as south
        setLocationRelativeTo(mw); //set location of main frame

        setVisible(true); //put main frame as visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == renewBtn) { //check if button clicked was renew
            renewBook();//call renew book function
        } else if (ae.getSource() == cancelBtn) { //check if button clicked was "cancel"
            this.setVisible(false); //hide the pop up
        }
    }

    private void renewBook() { //renew book function
        String id = idbookText.getText(); //get the id of book from text box
        LocalDate duedate = LocalDate.now().plusWeeks(Integer.parseInt(extendText.getText())); //get the number of weeks to extend from text box and extend it for that number, save this as new date
        
        Book bk = new Book(); //create a new object of book
        bk.setId(Integer.parseInt(id)); //set the it of book
		Patron pt = new Patron(); //create a new patron
		try {
			pt.renewBook(bk, duedate); //call a patron function to renew book, pass book and date to extend
		} catch (LibraryException e) {
			e.printStackTrace();
			System.out.println(e); //print error
		}
		
		mw.displayBooks(); // refresh the view with the list of books
		this.setVisible(false); //set current screen not to be visible
    }
}