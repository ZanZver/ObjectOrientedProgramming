package bcu.cmp5332.librarysystem.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookTest {
	Book bk3 = new Book(333, "Test Book", "Test Author", "1234", "Test publisher", true);
	@Test
	final void testBook() { //test constructors
		Book bk1 = new Book(123, "Test Book", "Test Author", "1234", "test publisher", true);
		Book bk2 = new Book();
	}

	@Test
	final void testGetId() {//test get id
		assertEquals(333, bk3.getId(), 0.0);
	}

	@Test
	final void testSetId() { //test setter for id
		Book bk2 = new Book();
		bk2.setId(111);
		assertEquals(111, bk2.getId(), 0.0);
	}

	@Test
	final void testGetTitle() {//test getter for title
		assertTrue(bk3.getTitle().contentEquals("Test Book"));
	}

	@Test
	final void testSetTitle() { //test setter for title
		Book bk2 = new Book();
		bk2.setTitle("New Title Here!");
		assertTrue(bk2.getTitle().contentEquals("New Title Here!"));
	}

	@Test
	final void testGetAuthor() { //test get author
		assertTrue(bk3.getAuthor().contentEquals("Test Author"));
	}

	@Test
	final void testSetAuthor() { //test set author
		Book bk2 = new Book();
		bk2.setAuthor("New Author Here!");
		assertTrue(bk2.getAuthor().contentEquals("New Author Here!"));
	}

	@Test
	final void testGetPublicationYear() { //test get publication year
		assertTrue(bk3.getPublicationYear().contentEquals("1234"));
	}

	@Test
	final void testSetPublicationYear() { //test set publication year
		Book bk2 = new Book();
		bk2.setPublicationYear("New Publication Year Here!");
		assertTrue(bk2.getPublicationYear().contentEquals("New Publication Year Here!"));
	}

	@Test
	final void testGetPublisher() { //test get publisher
		assertTrue(bk3.getPublisher().contentEquals("Test publisher"));
	}

	@Test
	final void testSetPublisher() { //test set publisher
		Book bk2 = new Book();
		bk2.setPublisher("New Publisher Here!");
		assertTrue(bk2.getPublisher().contentEquals("New Publisher Here!"));
	}

	@Test
	final void testGetDetailsShort() { //test get details
		assertTrue(bk3.getDetailsShort().contentEquals("Book #333 - Test Book")); 
	}

	@Test
	final void testGetDetailsLong() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testIsOnLoan() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetStatus() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetDueDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testSetDueDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetLoan() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testSetLoan() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testReturnToLibrary() {
		fail("Not yet implemented"); // TODO
	}

}
