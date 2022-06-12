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
import java.util.ArrayList;
import java.util.List;

public class Patron {
    private int id; //declare the id of patron
    private String name; //declare the name of patron
    private String phone; //declare the phone of the patron
    private boolean visible; //declare the visibility of the patron
    private final List<Book> books = new ArrayList<>(); //declare the empty list of the book
    
    public Patron(){ //empty constructor
    	id = 0; //set the id to 0
    	name = "null"; //set the name to null
    	phone = "null"; //set the phone to null
    	visible = true; //set the patron to be visible
    }
    
    public Patron(int id, String name, String phone, boolean visible){ //normal constructor
    	this.id = id; //set the id 
    	this.name = name; //set the name
    	this.phone = phone; //set the phone
    	this.visible = visible; //set the visibility
    }
    
    public void setId(int id) {//set the id
    	this.id = id; //change the id
    }
    public int getId() { //get the id
        return id; //return the id
    }
    
    public String getName() { //get the name
        return name; //return the name
    }
    
    public String getPhone() { //get the phone
        return phone; //return the phone
    }
    
    public void setVisible(boolean visible) {// set new visibility
    	this.visible = visible; //update visible status
    }
    public boolean getVisible() { //get the visiblity
    	return visible; //return visible status
    }
    
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException { //borrow book is a function that adds a book to the list of books that I as a customer have borrowed  
    	if(checkIfTheBookIsTaken(book).contentEquals("False")) { //check if the book exists in the books.txt + check if it is free
    		int count = 0; //start a counter of the books that user has
    		//add it to the borrow list
    		try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/loans.txt", true); //open file writer for loans
    			    BufferedWriter bw = new BufferedWriter(fw);
    			    PrintWriter out = new PrintWriter(bw))
    		{
    			try {
    				BufferedReader buffReadLoan = new BufferedReader(//open file reader
    						new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt")); //set location of the data
    				
    				for(String st; (st = buffReadLoan.readLine()) != null; ) { //read line
 					   String[] parts = st.split("::");//split string by "::"
 					   
 					   if(id == Integer.parseInt(parts[0])) {//check if customer has book in loan
 						   count++; //increase book counter by 1
 					   }
    				}
    			}
    			catch(IOException e) {
    				System.out.println(e);//print out an error
    			}
    			
    			if(count <=2) {//check if user has max of 3 (including) books (count starts with 0, so we have 2)
    				out.println(this.id + "::" +book.getId() + "::" + LocalDate.now() + "::" + dueDate + "::");//add a book to loan list 
    				books.add(book);//add book to book list
    				System.out.println("Book has been borrowed to you");
    			}else {
   					System.out.println("You have borrowd max (3) books"); //inform user that he/she has max amount of books on them					
    			}
    		} catch (IOException e) {
    			    System.out.println(e);
    			    System.out.println("Borrowing record has faild, please try again");//inform about an error if occurs
    		}
    	}
    	
    	else {
    		System.out.println("book is taken");//let user know that book is taken
    	}
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException { //renew book is a function that renews the date of the book book to the list of books that I as a customer have borrowed
    	try {
			BufferedReader buffReadLoan = new BufferedReader( //open file reader
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt")); //set location for the file
			
			try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/loansTemp.txt", true); //open file writer with location 
    			    BufferedWriter bw = new BufferedWriter(fw);
    			    PrintWriter out = new PrintWriter(bw))
    			{
				
				for(String st; (st = buffReadLoan.readLine()) != null; ) { //read the line 
					   String[] parts = st.split("::"); //split the line by "::"
					   
					   if(parts[1].contentEquals(Integer.toString(book.getId()))) { //check if book exists. So if parts[1] (books id) is the same as book we want to extend do something
						   out.println(parts[0] + "::" + parts[1] + "::" + parts[2] + "::" + dueDate + "::"); //rewrite the file with new date to return the book
					   }else {
						   out.println(st);//write the string to the file
					   }
				 }
				//rename
				File oldName = new File("../InteractiveLibrarySystem_dist/resources/data/loansTemp.txt"); //set the old name
				File newName = new File("../InteractiveLibrarySystem_dist/resources/data/loans.txt"); //set the new name
				oldName.renameTo(newName); //rename file, old file is going to be removed automatically
    		} catch (IOException e) {
    			    System.out.println(e); //inform abut error
    		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Renew record has faild, please try again");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Renew record has faild, please try again");
		}
    }

    public void returnBook(Book book) throws LibraryException {//return book
    	try {
			BufferedReader buffReadLoan = new BufferedReader( //open file reader
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt")); //set the path to the file
			
			try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/loansTemp.txt", true); //open file writer and set the path to the file
    			    BufferedWriter bw = new BufferedWriter(fw);
    			    PrintWriter out = new PrintWriter(bw))
    			{
				
				for(String st; (st = buffReadLoan.readLine()) != null; ) { //read the name
					   String[] parts = st.split("::"); //split parts by "::"
					   
					   if(parts[0].contentEquals(Integer.toString(this.id)) && parts[1].contentEquals(Integer.toString(book.getId()))) {//check if the user has the book	
						   try(FileWriter fwlh = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/loanhistoryTemp.txt", true); //open file writer 
				    			    BufferedWriter bwlh = new BufferedWriter(fwlh);
				    			    PrintWriter outlh = new PrintWriter(bwlh))
						   {
							   outlh.println(parts[0] + "::" + parts[1] + "::" + LocalDate.now() + "::");//add book to history of borrowed books
						   }
						   
						   System.out.println("book returned"); //inform user that book has been returned
					   }else {
						   out.println(st); //rewrite the other books back
					   }
				 }
				//rename
				File oldName = new File("../InteractiveLibrarySystem_dist/resources/data/loansTemp.txt"); //set the old name for loans
				File newName = new File("../InteractiveLibrarySystem_dist/resources/data/loans.txt"); //set the new name for loans
				File oldNamelh = new File("../InteractiveLibrarySystem_dist/resources/data/loanhistoryTemp.txt"); //set the old name for loans history
				File newNamelh = new File("../InteractiveLibrarySystem_dist/resources/data/loanhistory.txt"); //set the new name for loans history
				oldName.renameTo(newName); //rename the file loans
				oldNamelh.renameTo(newNamelh); //rename the file loans history
    		} catch (IOException e) {
    			    System.out.println(e); //inform user about an error
    		}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Returning record has faild, please try again");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Returning record has faild, please try again 5");
		}
    	books.remove(book); //remove book from library
    }
    
    public void addBook(Book book) {
    	//add a book to the book text 
    	//books.add(book); //adds book to the array
    	//checkTheBook(book);
    }
    
    public boolean checkTheBook(Book book) { //check if book is in the file
    	try {
			BufferedReader buffRead = new BufferedReader( //open up the reader
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/books.txt") //set reader location
				);
			
			 for(String st; (st = buffRead.readLine()) != null; ) { //read first line
				 String[] parts = st.split("::"); //split the line by "::"
				 if(parts[0].contentEquals(Integer.toString(book.getId()))) { //check if book exists
					 return true; //return true as book exists
				 }
			 }
			
		} catch (FileNotFoundException e) {
			System.out.println("Exception: " + e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Checking record has faild, please try again");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Checking record has faild, please try again");
		}
    	return false; //return false as book does not exist
    }
    
    public String checkIfTheBookIsTaken(Book book) {
    	if(checkTheBook(book) == false) {//check if book exists 
    		return ("Book does not exists");
    	}
    	//check if the book is taken + see who has it and for how long
    	try {
    		BufferedReader buffReadLoan = new BufferedReader( //open file reader for loan
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/loans.txt") //set file location for loan
				);
    		
    		BufferedReader buffReadPatron = new BufferedReader( //open file reader for patron
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/patrons.txt") //set file location for loan
				);
			 
    		String idTaken = ""; //for getting id of user who has the book
    		String returnDate = ""; //for getting the return date when book is available
    		//checks if it is taken
			 for(String st; (st = buffReadLoan.readLine()) != null; ) { //read loans line
				 String[] parts = st.split("::"); //split line by "::"
				 if(parts[1].contentEquals(String.valueOf(book.getId()))) { //check if our book is in the line
					System.out.println("Date of return: " + parts[3]); //inform in command when book is going to be available
					idTaken = parts[0]; //set the id of who has the book
					returnDate = parts[3]; //set the return date
				 }	 
			 }
			 //return the result
			 if (idTaken.contentEquals("")) {
				 return "False"; //book is not taken
			 }else {
				 for(String st; (st = buffReadPatron.readLine()) != null; ) { //read the line of patron
					 String[] parts = st.split("::"); //split the line by "::"
					 if(parts[0].contentEquals(idTaken)) { //check if parts[0] (id in the read line) is the same as id taken
						System.out.println("------TAKEN--------");  //print out that book is taken
						return ("It is taken by: " + parts[0] + " " + parts[1] + " untill: " + returnDate); //return that book is taken, by id, who, and until when. This is used in GUI
					 }
				 }
			 }

    	} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Checking record has faild, please try again");
    	} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Checking record has faild, please try again");
		}
    	return null;
    }
    
    public void removePatron(String id, String visibe2) {
    	try {
			BufferedReader buffReadPatrons = new BufferedReader( //set out the reader 
					new FileReader("../InteractiveLibrarySystem_dist/resources/data/patrons.txt") //set the file location
				);
			
			 try {
				for(String st; (st = buffReadPatrons.readLine()) != null; ) { //read line
					 String[] parts = st.split("::"); //split line by "::"
						 try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/patronstmp.txt", true); //set the file writer and location
				    			    BufferedWriter bw = new BufferedWriter(fw);
				    			    PrintWriter out = new PrintWriter(bw))
				    		{
							 if(id.contentEquals(parts[0])) { //check if line has id that needs to be updated
								 out.println(parts[0] +  "::" + parts[1] +  "::" + parts[2] +  "::" + visibe2 + "::"); //write to the file all of the old data but replace the visibility status
							 } else {
								 out.println(parts[0] +  "::" + parts[1] +  "::" + parts[2] +  "::" + parts[3] + "::"); //write everything back to old file
							 }
				    		}
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
				File oldName = new File("../InteractiveLibrarySystem_dist/resources/data/patronstmp.txt"); //set old name of the file
				File newName = new File("../InteractiveLibrarySystem_dist/resources/data/patrons.txt"); //set new name of the file
				oldName.renameTo(newName); //rename the file
			
		} catch (FileNotFoundException e) {
			System.out.println("Exception: " + e.getMessage()); //print out the exception
		}
    }

    public String getDetailsShort() { //print out the details
    	if(visible == true) { //check if patron is visible
    		return "Patron #" + id + " - " + name + " Phone: "+ phone; //return patron details
    	}else {
    		return "null"; //return null as patron is not visible
    	}
    }
}