package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddBook;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddBookWindow extends JFrame implements ActionListener {
    private MainWindow mw; //create new window
    private JTextField titleText = new JTextField(); //declare new text field for title
    private JTextField authText = new JTextField(); //declare new text field for author
    private JTextField pubDateText = new JTextField(); //declare new text field for publication date
    private JTextField pubText = new JTextField(); //create new text field for publication 
    private JTextField visText = new JTextField(); //create new text field to declare if text is visible or not

    private JButton addBtn = new JButton("Add"); //declare add button for adding new book
    private JButton cancelBtn = new JButton("Cancel"); //declare new cancel button for cancelling this operation

    public AddBookWindow(MainWindow mw) {
        this.mw = mw; //set this window 
        initialize(); //call main function
    }
    private void initialize() { //main code function
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set the UI manager
        } catch (Exception ex) {
        	System.out.println(ex); //inform user if error occurs
        }

        setTitle("Add a New Book"); //set the title of the frame

        setSize(300, 200); //set the size of the frame
        JPanel topPanel = new JPanel(); //create new top panel
        topPanel.setLayout(new GridLayout(5, 2)); //create new grid layout
        topPanel.add(new JLabel("Title : ")); //create a new label for title
        topPanel.add(titleText); //put down the text box for title
        topPanel.add(new JLabel("Author : ")); //create new label for author
        topPanel.add(authText); //put down the text box for author 
        topPanel.add(new JLabel("Publishing Date : ")); //create new label for publishing date
        topPanel.add(pubDateText); //put down new publishing date text box
        topPanel.add(new JLabel("Publisher : ")); //create new publisher label
        topPanel.add(pubText); //put down new publisher text box
        topPanel.add(new JLabel("Visible : ")); //create new label for visibility 
        topPanel.add(visText); //put down new text box for visibility

        JPanel bottomPanel = new JPanel(); //set the size of the frame
        bottomPanel.setLayout(new GridLayout(1, 3)); //create new grid layout
        bottomPanel.add(new JLabel("     ")); //create new label with some space
        bottomPanel.add(addBtn); //add down add button
        bottomPanel.add(cancelBtn); //add down new cancel button

        addBtn.addActionListener(this); //add new action lister for add button
        cancelBtn.addActionListener(this); //add new cancel button for cancel button

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //put top panel to main frame as center
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //put bottom panel as south to main frame
        setLocationRelativeTo(mw); //set location of main frame

        setVisible(true); //set frame as visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) { //check if button has been clicked
            addBook(); //trigger add book function
        } else if (ae.getSource() == cancelBtn) { //check if cancel button has been called
            this.setVisible(false); //set frame as hidden
        }
    }

    private void addBook() { //add book function
        try {
            String title = titleText.getText(); //get the title from text box
            String author = authText.getText(); //get the author from text box
            String publicationYear = pubDateText.getText(); //get the publication year from text box
            String publisher = pubText.getText(); //get the publisher from text box
            boolean visibe = Boolean.parseBoolean(visText.getText()); //get the visibility from text box and convert (parser) it to boolean
            // create and execute the AddBook Command
            Command addBook = new AddBook(title, author, publicationYear, publisher,visibe); //create command block. We could user .getText if we want 
            addBook.execute(mw.getLibrary(), LocalDate.now()); //execute the command
            // refresh the view with the list of books
            mw.displayBooks(); //display books
            this.setVisible(false); // hide (close) the AddBookWindow
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);//inform user about error that occurs 
        }
    }
}