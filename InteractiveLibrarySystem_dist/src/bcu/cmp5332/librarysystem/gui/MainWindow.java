package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Book;

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

public class MainWindow extends JFrame implements ActionListener {
    private JMenuBar menuBar; //create new menu bar
    private JMenu adminMenu; //create admin sub menu
    private JMenu booksMenu; //create books sub menu
    private JMenu membersMenu; //create members sub menu

    private JMenuItem adminExit; //create button for admins to exit

    private JMenuItem booksView; //create button to view books
    private JMenuItem booksAdd; //create button to add books
    private JMenuItem booksDel;	 //create button to delete books
    private JMenuItem booksIssue; //create button to issue (borrow) book
    private JMenuItem booksReturn; //create button to return book
    private JMenuItem booksRenew; //create button to renew (extend) book

    private JMenuItem memView; //create button to view members 
    private JMenuItem memAdd; //create button to add members
    private JMenuItem memDel; //create button to remove members 

    private Library library; //declare library

    List<Patron> patronsList; //create a list of patrons
    
    public MainWindow(Library library) {
        initialize();//call main code
        this.library = library; //set the library
    }
    
    public Library getLibrary() {
        return library; //return the library
    }
    
    private void initialize() { //main code
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set the ui manager
        } catch (Exception ex) {
        	System.out.println(ex); //inform about error
        }

        setTitle("Library Management System"); //set the title

        menuBar = new JMenuBar(); //set the menu bar
        setJMenuBar(menuBar); //add menue bar

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin"); //put down a text for button (admin)
        menuBar.add(adminMenu); //add button to menu

        adminExit = new JMenuItem("Exit"); //put down the text for Exit button
        adminMenu.add(adminExit); //add sub button (exit) to admin menu
        adminExit.addActionListener(this); //add action lister to sub button

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");//create a text for button books
        menuBar.add(booksMenu); //add books button to menu

        booksView = new JMenuItem("View"); //create new sub button view
        booksAdd = new JMenuItem("Add"); //create new sub button add
        booksDel = new JMenuItem("Delete"); //create new sub button delete
        booksIssue = new JMenuItem("Issue"); //create new sub button issue
        booksReturn = new JMenuItem("Return"); //create new sub button return
        booksRenew = new JMenuItem("Renew"); //create new sub button renew
        booksMenu.add(booksView); //add view button as sub button 
        booksMenu.add(booksAdd); //insert add button as sub button
        booksMenu.add(booksDel); //insert del button as sub button
        booksMenu.add(booksIssue); //add issue button to sub menu
        booksMenu.add(booksReturn); //add return button as sub button
        booksMenu.add(booksRenew); //insert renew button to sub menu
        for (int i = 0; i < booksMenu.getItemCount(); i++) { //do something for every item in books menu
            booksMenu.getItem(i).addActionListener(this); //add action lister for every item
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members"); //set the title for menu item members
        menuBar.add(membersMenu); //add members to menu

        memView = new JMenuItem("View"); //create view sub button
        memAdd = new JMenuItem("Add"); //create add sub button
        memDel = new JMenuItem("Delete"); //create delete sub button

        membersMenu.add(memView); //add view button to sub menu
        membersMenu.add(memAdd); //insert add button to sub menu
        membersMenu.add(memDel); //insert del button to sub menu 

        memView.addActionListener(this); //add action lister for view
        memAdd.addActionListener(this); //add action lister for add
        memDel.addActionListener(this); //add action lister for del

        setSize(800, 500); //declare size of the frame

        setVisible(true); //set the frame visible
        setAutoRequestFocus(true); //set the frame on focus (for the mouse)
        toFront(); //bring frame on to front
        setDefaultCloseOperation(EXIT_ON_CLOSE); //close program
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
    }	

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) { //check if exit button has been clicked
            System.exit(0); //close program
        } else if (ae.getSource() == booksView) { //check if view button has been called
            displayBooks(); //call display books function
        } else if (ae.getSource() == booksAdd) { //check if button for adding book has been click
            new AddBookWindow(this); //call add book window
        } else if (ae.getSource() == booksDel) { //check if delete books has been clicked
        	new DeliteBook(this); //call delite book window
        } else if (ae.getSource() == booksIssue) { //check if issue book has been clicked
        	new IssueBookWindow(this); //call issue book window 
        } else if (ae.getSource() == booksReturn) { //check if button return has been called
            new ReturnBookWindow(this); //open return book window
        } else if (ae.getSource() == booksRenew) { //check if renew books button has been clicked
        	new RenewBookWindow(this);//call renew book window
        } else if (ae.getSource() == memView) { //check if view members has been clicked
        	this.displayMembers(); //call display members
        	new ViewMembersWindow(library); //show a pop up of members that have books 
        } else if (ae.getSource() == memAdd) { //check if add members button has been clicked
            new AddMemberWindow(this); //call add member window
        } else if (ae.getSource() == memDel) { //check if delete members has been clicked
        	new DeliteMemberWindow(this); //open delite members
        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks(); //fill the book list with books
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status", "Publisher"}; //set the headers

        Object[][] data = new Object[booksList.size()][6];//set the the two dimensional array of data
        int j = 0; //set the counter to count for data 
        for (int i = 0; i < booksList.size(); i++) { //do something for book list size
            Book book = booksList.get(i); //get the book
            
            try {
        		BufferedReader buffReadLoan = new BufferedReader( //open the reader
    					new FileReader("../InteractiveLibrarySystem_dist/resources/data/books.txt") //set the path of data
    				);
    			 for(String st; (st = buffReadLoan.readLine()) != null; ) { //read line
    				 String[] parts = st.split("::"); //split line for "::"
    				 if(parts[0].contentEquals(Integer.toString(book.getId()))) { //check if part 0 (books id) is equal to current id)
    					 if(parts[5].contentEquals("true")) { //check if book is visible
    				            data[j][0] = book.getTitle(); //set the title of book to spot 0 in array
    				            data[j][1] = book.getAuthor(); //set the author of book to spot 1 in data
    				            data[j][2] = book.getPublicationYear(); //set the publication year to spot 4 in data
    				            data[j][3] = book.getStatus(); //set the status to spot 3 in array
    				            data[j][4] = book.getPublisher(); //set the publisher of book to spot 4 in array
    				            j++;//increase the size of j by 1
    					 }
    				 }
    			 }
            	} catch (FileNotFoundException e) {
    				System.out.println(e); //show error message
    				e.printStackTrace();
    			} catch (IOException e) {
    				System.out.println(e); //inform user of an error
    				e.printStackTrace();
    			}
        }

        JTable table = new JTable(data, columns); //create the table 
        this.getContentPane().removeAll(); //clear the frame
        this.getContentPane().add(new JScrollPane(table)); //show the table
        this.revalidate(); //push the frame
    }	
    
    public void displayMembers() {
        patronsList = library.getPatrons(); //fill the list with the patrons
        // headers for the table
        String[] columns = new String[]{"Id", "Name and Surname", "Phone", "Borrowed books"}; //set the headers for the table

        Object[][] data = new Object[patronsList.size()][6]; //set the two dimensional array of data
        
        int j = 0; //set the j counter
        for (int i = 0; i < patronsList.size(); i++) { //do something for every patron
        	Patron patron = patronsList.get(i); //create new object of patron
        	
        	 try {
         		BufferedReader buffReadPatrons = new BufferedReader( //open new reader
     					new FileReader("../InteractiveLibrarySystem_dist/resources/data/patrons.txt") //set the location of the reader
     				);
     			 for(String stPatrons; (stPatrons = buffReadPatrons.readLine()) != null; ) { //read line by line
     				 String[] partsPatrons = stPatrons.split("::"); //split line by "::"
     				 if(partsPatrons[0].contentEquals(Integer.toString(patron.getId()))) { //check if patrons id is the same from read and from library
     					 if(partsPatrons[3].contentEquals("true")) { //check if patron is visible
     						data[j][0] = patron.getId(); //set id at location 0 
     			            data[j][1] = patron.getName(); //set name at location 1
     			            data[j][2] = patron.getPhone(); //set phone at location 2
     				            
     				        int counter = 0; //set counter for counting books that patron has borrowed
     				        try {
     				        		BufferedReader buffReadLoan = new BufferedReader( //create new reader
     				    					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt") //set location of the data
     				    				);
     				    			 for(String st; (st = buffReadLoan.readLine()) != null; ) { //read line
     				    				 String[] parts = st.split("::");//split the line by "::"
     				    				 
     				    				 if(patron.getId() == Integer.parseInt(parts[0])) { //check if id of patrons is the same of borrowed 
     				    					 counter++; //increase the counter by 1
     				    				 }
     				    			 }
     				            	} catch (FileNotFoundException e) {
     				            		System.out.println(e);//inform user of an error
     				    				e.printStackTrace();
     				    			} catch (IOException e) {
     				    				System.out.println(e);//set out an information about error
     				    				e.printStackTrace();
     				    			}
     				           data[j][3] = counter; //set the counter to 3 data spot
     				           j++; //increase j by 1
     					 }
     				 }
     			 }
             	} catch (FileNotFoundException e) {
             		System.out.println(e); //print out the error
     				e.printStackTrace();
     			} catch (IOException e) {
     				System.out.println(e); //show the error
     				e.printStackTrace();
     			}
        }
        
        JTable table = new JTable(data, columns); //insert the table to the frame
        this.getContentPane().removeAll(); //clear the frame
        this.getContentPane().add(new JScrollPane(table)); //add table to the frame
        this.revalidate(); //submit the frame
    }
}