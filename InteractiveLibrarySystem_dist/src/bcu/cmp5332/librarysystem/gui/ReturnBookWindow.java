package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ReturnBookWindow extends JFrame implements ActionListener {
    private MainWindow mw; //main window frame
    private JTextField idPatronText = new JTextField(); //text field for patrons id
    private JTextField idBookText = new JTextField(); //text field for books id
    private JButton returnBtn = new JButton("Return"); //button for returning
    private JButton cancelBtn = new JButton("Cancel"); //button for cancelling

    public ReturnBookWindow(MainWindow mw) {
        this.mw = mw; //set this frame as main one
        initialize(); //run the main function
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set the ui manager for frame
        } catch (Exception ex) {
        	System.out.println(ex); //print error message
        }

        setTitle("Return book"); //set the title of frame
        setSize(300, 200); //set the size of frame
        
        JPanel topPanel = new JPanel(); //set the new panel
        topPanel.setLayout(new GridLayout(5, 2)); //create a grid layout to new panel
        topPanel.add(new JLabel("Patron ID : ")); //add new label to display text as "patron id:"
        topPanel.add(idPatronText); //set the text box for reading patrons id
        topPanel.add(new JLabel("Book ID : ")); //add new label to display text as "book id:"
        topPanel.add(idBookText); //set the text box for reading books id

        JPanel bottomPanel = new JPanel();//set the new panel
        bottomPanel.setLayout(new GridLayout(1, 3));//create a new grid layout
        bottomPanel.add(new JLabel("     "));//add empty label
        bottomPanel.add(returnBtn);//add add button
        bottomPanel.add(cancelBtn);//add cancel button

        returnBtn.addActionListener(this); //add listener for return
        cancelBtn.addActionListener(this); //add listener for cancer

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //add top panel to center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //add bottom panel to south
        setLocationRelativeTo(mw); //add new location of the pop up to current location

        setVisible(true); //set the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnBtn) { //check if button return has been clicked
            returnBook(); //call return book function 
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); //set frame not to visible
        }
    }

    private void returnBook() {
        try {
            String idPatron = idPatronText.getText(); //create a temp. variable for patrons id and get the data from text box
            String idBook = idBookText.getText(); //create a temp. variable to store id of book and get data from text box

        	Patron patron = new Patron(); //create a new object of patron
        	patron.setId(Integer.parseInt(idPatron)); //insert patrons id to new object
        	
        	Book book = new Book(); //create a new object of class book
        	book.setId(Integer.parseInt(idBook)); //set the book id from inserted id
        	
        	patron.returnBook(book); //call main function return book to return book
            
            mw.displayBooks();// refresh the view with the list of books
            
            this.setVisible(false);// hide (close) the AddBookWindow
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);//display a message for error
        }
    }

}