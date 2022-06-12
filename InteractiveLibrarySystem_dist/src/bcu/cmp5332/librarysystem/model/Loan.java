package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;

public class Loan {
    private Patron patron; //create an instance of the patron
    private Book book; //create an instance of the book
    private LocalDate startDate; //create start date
    private LocalDate dueDate; //create due date
    private boolean available; //create available date

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate, boolean available) {//normal constructor
    	this.patron = patron;//set the patron
    	this.book = book; //set the book
        this.startDate =startDate; //set the start date
        this.dueDate = this.startDate.plusDays(7); //set the due date, increase it by today + 7 days
        this.available = available; //set availability 
    }
    
    public String getPatron() {//get patron
    	return patron.getId() + patron.getName() + patron.getPhone(); //return patrons id, patron name, patrons phone
    }
    
    public String getBook() {//get the book
    	return book.getId() + book.getAuthor() + book.getTitle() + book.getPublicationYear(); //return books id, books author, books title and publication year
    }

	public int getId() {//get the id of the books
		return book.getId(); //return books id
	}
}