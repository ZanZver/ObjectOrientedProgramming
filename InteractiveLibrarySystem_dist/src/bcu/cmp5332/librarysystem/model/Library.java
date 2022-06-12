package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;//number of days that book is on loan
    private final Map<Integer, Patron> patrons = new TreeMap<>(); //create new dictionary for the patrons
    private final Map<Integer, Book> books = new TreeMap<>(); //create new dictionary for the books
    
    public int getLoanPeriod() { //get the loan period
        return loanPeriod;// return loan period
    }
    
	public List<Patron> getPatrons() { //get the list of patrons
		List<Patron> out = new ArrayList<>(patrons.values());//save all of the patrons to the array
        return Collections.unmodifiableList(out); //return list of patrons
	}
    
    public List<Book> getBooks() {//get the list of books 
        List<Book> out = new ArrayList<>(books.values());//save the books to the list
        return Collections.unmodifiableList(out); //return the list of the books
    }
    

    public Book getBookByID(int id) throws LibraryException {//check book by id
        if (!books.containsKey(id)) { //check if a book exists with that id
            throw new LibraryException("There is no such book with that ID.");//inform the user that book does not exist
        }
        return books.get(id);//return the book
    }

    public Patron getPatronByID(int id) throws LibraryException { //check patrons by id
    	if (!patrons.containsKey(id)) {//see if id does not match
            throw new LibraryException("There is no such patrons with that ID."); //return that there is no such patron
        }
        return patrons.get(id);//return patron &id
    }

    public void addBook(Book book) { //add book to the system
        if (books.containsKey(book.getId())) {//check if book has duplicate id
            throw new IllegalArgumentException("Duplicate book ID."); //inform user about the error
        }
        books.put(book.getId(), book);//add book to the system
    }

    public void addPatron(Patron patron) {//add book to the system
        if (patrons.containsKey(patron.getId())) { //check if patrons have duplicate keys
            throw new IllegalArgumentException("Duplicate patron ID.");//inform user about the error
        }
        patrons.put(patron.getId(), patron); //add patron to the system
    }
}