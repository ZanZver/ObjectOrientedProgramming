package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class ViewMembersWindow extends JFrame implements ActionListener {
    private JMenuBar menuBar; //main menu
    private JMenu membersMenu;//button on main menu
    private JMenuItem memView;//side button 
    private Library library;//library
    List<Patron> patronsList;//list of patrons
    
    public ViewMembersWindow(Library library) {
        initialize(); //call function
        this.library = library; //declare library
	}
    
    public Library getLibrary() {
        return library;//return library
    }
    private void initialize() { //main function to load
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Set UI manager
        } catch (Exception ex) {
        	System.out.println(ex); //print out exception
        }

        setTitle("View Members"); //set the title of frame
        
        menuBar = new JMenuBar(); //declare main menu bar 
        setJMenuBar(menuBar); //set main menu bar
        
        membersMenu = new JMenu("Members"); //add text to the button
        menuBar.add(membersMenu); //add button the the menu bar

        memView = new JMenuItem("View"); //add a text to sub button 
 
        membersMenu.add(memView); //add a sub button to to members button
        memView.addActionListener(this); //add an action listener to button

        setSize(800, 500); //set the size of the frame

        setVisible(true); //set the frame visible
        setAutoRequestFocus(true); //set the frame as focused one
        toFront(); //bring frame to the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); //close it when pressed button
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);   //finished it on close
    }	

    @Override
    public void actionPerformed(ActionEvent ae) { //action listener
    	if (ae.getSource() == memView) { //check if the source is correct 
        	this.displayMembers(); // call function to display members
        } 
    }

    public void displayMembers() {
        patronsList = library.getPatrons(); //get the list of patrons
        String[] columns = new String[]{"User Id", "Name and Surname", "Book name", "Borrow date", "Return date"}; //set the titles for columns
        
        int counter = 0; //set the counter for number of loans
        try {//get lenght of loans 
    		BufferedReader buffReadLoan = new BufferedReader( //set the reader
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt") //set the loacation of the reader
				);
			 for(String st; (st = buffReadLoan.readLine()) != null; ) {
				 counter++; //count the reader
			 }
			 buffReadLoan.close();
        	} catch (FileNotFoundException e) {
        		System.out.println(e); //print the error
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e); //print the error
				e.printStackTrace();
			}    
        
        Object[][] data = new Object[counter][8]; //set the double array for data
    
        	try {//go through loans line by line
        		BufferedReader buffReadLoan = new BufferedReader( //set the reader for loans
    					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt") //set the file location
    				);
        		String st; //declare a string to store data
    			 for(int i = 0; (st = buffReadLoan.readLine()) != null; i++) { //read line of loans
    				 String[] parts = st.split("::"); //split the line by "::" for loans
    				 try {
    		        		BufferedReader buffReadPatrons = new BufferedReader( //set the reader for patrons
    		    					new FileReader("../InteractiveLibrarySystem_dist/resources/data/patrons.txt") //set the reader file location
    		    				);
    		    			 for(String stPatrons; (stPatrons = buffReadPatrons.readLine()) != null; ) { ////read line for patrons
    		    				 String[] partsPatrons = stPatrons.split("::"); //split line for patrons
    		    				 if(parts[0].contentEquals(partsPatrons[0])) { //check if loan item[0] (aka patron id) is the same as patron id that we are going through
    		    					 data[i][0] = partsPatrons[0]; //set the patron id to 0 data spot
    		    					 data[i][1] = partsPatrons[1]; //set the patron name to 1 data spot
    		    				 	}
    		    				 }
    		            	} catch (FileNotFoundException e) {
    		            		System.out.println(e); //print the error
    		    				e.printStackTrace();
    		    			} catch (IOException e) {
    		    				System.out.println(e); //print the error
    		    				e.printStackTrace();
    		    			}
    				 
    				 try {
 		        		BufferedReader buffReadBooks = new BufferedReader( //set the reader for books
 		    					new FileReader("../InteractiveLibrarySystem_dist/resources/data/books.txt") //set the file location
 		    				);
 		    			 for(String stBooks; (stBooks = buffReadBooks.readLine()) != null; ) { //read the books
 		    				 String[] partsBooks = stBooks.split("::"); //split books line by "::"
 		    				 if(parts[1].contentEquals(partsBooks[0])) { //check if item[0] (aka book id) is the same as book id from the line that we are going through 
 		    					 data[i][2] = partsBooks[1]; //set the name of the book to 2 data spot
 		    				 	}
 		    				 }
 		            	} catch (FileNotFoundException e) {
 		            		System.out.println(e); //print the error
 		    				e.printStackTrace();
 		    			} catch (IOException e) {
 		    				System.out.println(e); //print the error
 		    				e.printStackTrace();
 		    			}
    				 data[i][3] = parts[2];//borrow date from loans to 3 data spot
    				 data[i][4] = parts[3];//return date from loans to 4 data spot
    			 }
            	} catch (FileNotFoundException e) {
            		System.out.println(e); //print the error
    				e.printStackTrace();
    			} catch (IOException e) {
    				System.out.println(e); //print the error
    				e.printStackTrace();
    			}
        
        JTable table = new JTable(data, columns); //create the table
        this.getContentPane().removeAll(); //clear the frame
        this.getContentPane().add(new JScrollPane(table)); //add a table to the frame
        this.revalidate();//submit it
    }
}