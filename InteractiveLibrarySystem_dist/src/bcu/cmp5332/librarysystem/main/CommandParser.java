package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.commands.LoadGUI;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.commands.ListBooks;
import bcu.cmp5332.librarysystem.commands.ListPatrons;
import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.Help;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class CommandParser {
    
    public static Command parse(String line) throws IOException, LibraryException {
        try { 
            System.out.println(line); //display entered line
            String[] parts = line.split(" ", 3); //separate inputed line by the space
            System.out.println(Arrays.toString(parts));//show the splitted line
            String cmd = parts[0];//save first bit to cmd
            
            if (cmd.equals("addbook")) { //check if cmd equals addbook
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//start console reader
                System.out.print("Title: "); //print title
                String title = br.readLine(); //read line of the title
                System.out.print("Author: "); //print author
                String author = br.readLine(); //read line of author
                System.out.print("Publication Year: "); //ask for the publication year
                String publicationYear = br.readLine(); //get the publication year
                System.out.print("Publisher: "); //ask for the publisher
                String publisher = br.readLine(); //read the line of publisher
                System.out.print("Visible: "); //print visible
                boolean visible = Boolean.parseBoolean(br.readLine()); //convert visible from string to boolean and save it
                
                return new AddBook(title, author, publicationYear, publisher, visible); //call addbook function
            } else if (cmd.equals("addpatron")) { //check if user wants to add patron
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //start input reader
                System.out.print("Name: "); //ask for name
                String name = br.readLine(); //get name
                System.out.print("Phone: "); //ask for phone
                String phone = br.readLine(); //get phone
                System.out.print("Visible: "); //print visible
                boolean visible = Boolean.parseBoolean(br.readLine()); //get visible, convert it to boolean and save it
                
                return new AddPatron(name, phone,visible); //call addPatron
            } else if (cmd.equals("loadgui")) { //check if user has requested gui
                return new LoadGUI(); //load gui
            } else if (parts.length == 1) {//check if cmd has just one part
                if (line.equals("listbooks")) { //check if that part is list books
                    return new ListBooks(); //show list of books
                } else if (line.equals("listpatrons")) { //check if user wants to see patrons
                	return new ListPatrons(); //load list of patrons
                } else if (line.equals("help")) { //check if user needs some help
                    return new Help(); //return help list with commands
                }
            } else if (parts.length == 2) { //check if cmd has 2 elements
                int id = Integer.parseInt(parts[1]); //convert first element to id

                if (cmd.equals("showbook")) { //check command equals show book
                    
                } else if (cmd.equals("showpatron")) { //check if command equals show patron
                    
                }
            } else if (parts.length == 3) { //check if length of parts is 3
                int patronID = Integer.parseInt(parts[1]); //store part 1 as patrons id
                int bookID = Integer.parseInt(parts[2]); //store part 2 as books id
                
                if (cmd.equals("borrow")) { //check if command equals to borrow
                	Patron patron = new Patron(); //create a new object patron
                	patron.setId(patronID); //set the patrons id to inserted one
                	
                	Book book = new Book(); //create a new instance of book
                	book.setId(bookID); //set the book id for entered one
                	
                	patron.borrowBook(book, LocalDate.now().plusDays(7)); //call function to borrow a book
                } else if (cmd.equals("renew")) {//check if command equals renew
                    
                } else if (cmd.equals("return")) { //check if command equals return
                	Patron patron = new Patron(); //create new instance of patron
                	patron.setId(patronID); //set id of patron
                	
                	Book book = new Book(); //create a new instance of book
                	book.setId(bookID); //set the id of book
                	
                	patron.returnBook(book); //return book with patron function
                }
            }
        } catch (NumberFormatException ex) {
        	System.out.println(ex);//inform an user of an mistake 
        }
        throw new LibraryException("Invalid command."); //throw invalid command
    }
}