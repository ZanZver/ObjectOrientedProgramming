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

import bcu.cmp5332.librarysystem.model.Book;

public class DeliteBook extends JFrame implements ActionListener {
    private MainWindow mw; //create new window
    private JTextField idText = new JTextField(); //declare new text box for id of the book
    private JTextField visText = new JTextField(); //declare new text box for visibility of the book

    private JButton updateBtn = new JButton("Update"); //declare update button 
    private JButton cancelBtn = new JButton("Cancel"); //declare cancel button

    public DeliteBook(MainWindow mw) {
        this.mw = mw; //set main window
        initialize(); //call main code
    }

    private void initialize() { //main code
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set ui manager
        } catch (Exception ex) {
        	System.out.println(ex); //inform about error
        }

        setTitle("Delite/Update book");

        setSize(300, 200); //set the size of the frame
        JPanel topPanel = new JPanel(); //declare top panel
        topPanel.setLayout(new GridLayout(5, 2)); //set grid layout
        topPanel.add(new JLabel("ID : ")); //add a label for id
        topPanel.add(idText); //add text box for the id
        topPanel.add(new JLabel("Visible : ")); //add a label for the visibility
        topPanel.add(visText); //add text box for the visibility

        JPanel bottomPanel = new JPanel(); //declare bottom panel
        bottomPanel.setLayout(new GridLayout(1, 3)); //set the grid for the bottom panel
        bottomPanel.add(new JLabel("     ")); //add label with empty space to bottom panel
        bottomPanel.add(updateBtn); //insert update button to bottom panel 
        bottomPanel.add(cancelBtn); //add cancel button to bottom panel

        updateBtn.addActionListener(this); //add action listener for update
        cancelBtn.addActionListener(this); //add action listener for cancel

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //add top panel as center to main frame
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //insert bottom panel to south into main frame
        setLocationRelativeTo(mw); //set the location of main window

        setVisible(true); //set main window visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateBtn) { //check if update button has been clicked
            updateBook(); //call update book function
        } else if (ae.getSource() == cancelBtn) { //check if cancel button has been clicked
            this.setVisible(false); //hide the pop up
        }
    }

    private void updateBook() {
        String id = idText.getText(); //get the id from text box
        String visibe = visText.getText(); //get the visibility of the book form text box

		Book bk = new Book(); //create new object of the book
		bk.removeBook(id, visibe); //call remove book to update status of the visibility
		
		mw.displayBooks(); // refresh the view with the list of books
		this.setVisible(false);// hide (close) the AddBookWindow
    }
}