package bcu.cmp5332.librarysystem.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.main.LibraryException;

class PatronTest {
	Patron patron3 = new Patron(7,"Test3", "345", true);
	
	@Test
	final void testPatron() {//test constructor
		Patron patron1 = new Patron();
		Patron patron2 = new Patron(22,"Test2", "456", true);
	}

	@Test
	final void testSetId() { //test setter for id
		Patron patron1 = new Patron();
		patron1.setId(11);
		assertEquals(11, patron1.getId(), 0.0);
	}

	@Test
	final void testGetId() { //test getter for id
		assertEquals(7, patron3.getId(), 0.0);
	}

	@Test
	final void testGetName() { //test getter for name
		assertTrue(patron3.getName().contentEquals("Test3"));
	}

	@Test
	final void testGetPhone() { //test getter for phone
		assertTrue(patron3.getPhone().contentEquals("345"));
	}

	@Test
	final void testBorrowBook() throws LibraryException, IOException { //test borrow book
		Book bk  = new Book(); //create instance of book
		bk.setId(48); //set id of book
		patron3.borrowBook(bk, LocalDate.now()); //borrow the book
		assertTrue(patron3.checkIfTheBookIsTaken(bk).contentEquals("It is taken by: 7 adgdf untill: 2020-01-11")); //get the resoult
	}

	@Test
	final void testRenewBook() { //void function
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testReturnBook() { //void function
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddBook() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testCheckTheBook() { //check if if book is in the system
		Book bk = new Book(23, "Nineteen Eighty-Four", "George Orwell", "1949", "publisher3", true);
		assertEquals(true, patron3.checkTheBook(bk));
	}

	@Test
	final void testCheckIfTheBookIsTaken() { //test if book is available
		Book bk = new Book(23, "Nineteen Eighty-Four", "George Orwell", "1949", "publisher3",true);
		assertTrue(patron3.checkIfTheBookIsTaken(bk).contentEquals("It is taken by: 3 Kostas Vlachos untill: 2019-09-30"));
	}

	@Test
	final void testGetDetailsShort() { //test on getting details
		assertTrue(patron3.getDetailsShort().contentEquals("Patron #33 - Test3 Phone: 345"));
	}

}