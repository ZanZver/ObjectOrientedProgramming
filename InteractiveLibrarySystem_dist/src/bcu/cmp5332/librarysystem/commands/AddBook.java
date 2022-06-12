package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class AddBook implements  Command {

    private final String title; //example: The Art of War
    private final String author; //example: Sun Tzu
    private final String publicationYear;//example: 1618. This can be an intiger (small int) or a date. 
    private final String publisher;//example: amazon
    private final boolean visible; //example: false. If you put in false, book will be hidden from the system. Change it to true for visibility.

    public AddBook(String title, String author, String publicationYear, String publisher, boolean visible) {
		this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.visible = visible;
    }
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int lastIndex = library.getBooks().size() - 1;
        System.out.println(lastIndex);
        int maxId = library.getBooks().get(lastIndex).getId();
        Book book = new Book(++maxId, title, author, publicationYear,publisher, visible);
        library.addBook(book);

        try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/books.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(++maxId + "::" + title + "::" + author + "::" + publicationYear + "::" + publisher + "::" + visible + "::" );
			    System.out.println("Book #" + book.getId() + " added.");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Adding record has faild, please try again");
		}
        
    }
}
