package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Book {
    private int id; //id of book. It can be small int if our library is going to be small or we might need to increase it to big int for bigger number of books
    private String title; //title of the book
    private String author; //author of the book
    private String publicationYear;//publication year, it can be as a date
    private String publisher;//who is publisher of the book
    private Loan loan; //create an object of the loan
    private boolean visible; //visibility status of the book to people
    
    public Book() { //empty constructor
        this.id = 0; //set the id number to 0 as stock. Another option might be "null" but I have opt out due to the fact that we might do some calculations or anything and code can freak out
        this.title = ""; //set the title as empty string
        this.author = ""; //set the author as empty string
        this.publicationYear = ""; //set the publication year as empty string
        this.publisher = ""; //set the publisher as empty string
        this.visible = true; //set the visibility to true
    }
    
    public Book(int id, String title, String author, String publicationYear, String publisher, boolean visible) { //regular constructor
        this.id = id; //set the id
        this.title = title; //set the title
        this.author = author; //set the author
        this.publicationYear = publicationYear; //set the publication year
        this.publisher = publisher; //set the publisher
        this.visible = visible; //set the visibility
    }

    public int getId() { //get the id from the book
        return id; //return id
    }

    public void setId(int id) { //change/set the id
        this.id = id; //update id
    }

    public String getTitle() {//get title
        return title; //return title
    }

    public void setTitle(String title) { //change or set the title
        this.title = title; //set the new title
    }
    
    public String getAuthor() {//get the author
        return author; //return the author
    }
    
    public void setAuthor(String author) { //change the author
        this.author = author; //update the author
    }

    public String getPublicationYear() { //get the publication year
        return publicationYear; //return publication year
    }

    public void setPublicationYear(String publicationYear) { //set publication year
        this.publicationYear = publicationYear; //update publication year
    }
    
    public String getPublisher() { //get the publisher
    	return publisher; //return the publisher
    }
    
    public void setPublisher(String publisher) { //change the publisher
    	this.publisher = publisher; //set the new publisher
    }
	
    public String getDetailsShort() { //get the details
    	if(visible == true) { //check if book is visible 
    		return "Book #" + id + " - " + title; //return the book
    	}else {
    		return "null"; //return null, as book is not visible
    	}
        
    }

    public String getDetailsLong() {
        // TODO: implementation here
        return null;
    }
    
    public boolean isOnLoan() { //check if book is on loan
        return (loan != null);
    }
    
    public String getStatus() {
        // TODO: implementation here
        return null;
    }

    public LocalDate getDueDate() { 
        // TODO: implementation here
        return null;
    }
    
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    	
    }
    
    public void removeBook(String id, String visibe2) {//remove book from the system
    	try {
			BufferedReader buffReadBook = new BufferedReader( //open up the reader
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/books.txt")
				);
			 try {
				for(String st; (st = buffReadBook.readLine()) != null; ) { //read the line
					 String[] parts = st.split("::"); //spit the line by "::"
						 try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/bookstmp.txt", true); //open file writer and create a file if it does not exist
				    			    BufferedWriter bw = new BufferedWriter(fw);
				    			    PrintWriter out = new PrintWriter(bw))
				    		{
							 if(id.contentEquals(parts[0])) {//check if book id equals id that we are currently on
								 out.println(parts[0] +  "::" + parts[1] +  "::" + parts[2] +  "::" + parts[3] +  "::" + parts[4] +  "::" + visibe2 + "::"); //rewrite to new file and update to visible 2
							 } else {
								 out.println(parts[0] +  "::" + parts[1] +  "::" + parts[2] +  "::" + parts[3] +  "::" + parts[4] +  "::" + parts[5]+ "::"); //rewrite the whole book 
							 }
				    		}
				 }
			} catch (IOException e) {
				System.out.println(e); //inform about the error
				e.printStackTrace();
			}
				File oldName = new File("../InteractiveLibrarySystem_dist/resources/data/bookstmp.txt"); //set the old name
				File newName = new File("../InteractiveLibrarySystem_dist/resources/data/books.txt"); //set the new name
				oldName.renameTo(newName); //rename from bookstemp to books. Old file is going to be removed
			
		} catch (FileNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());//inform about an error if occurs
		}
    }

    public Loan getLoan() {//get loan
        return loan;//return loan
    }

    public void setLoan(Loan loan) { //set the loan
        this.loan = loan; //this is new loans
    }

    public void returnToLibrary() {
        loan = null;
    }
}
