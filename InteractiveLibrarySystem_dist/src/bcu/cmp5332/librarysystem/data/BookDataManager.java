package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/books.txt"; //data source
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {//scan the data from the source
            int line_idx = 1; //set the counter of the lines
            while (sc.hasNextLine()) { //check if there is a next line
                String line = sc.nextLine(); //get the next line
                String[] properties = line.split(SEPARATOR, -1); //separator line with the separator "::" and save it to the array
                try {
                    int id = Integer.parseInt(properties[0]); //get the item at the place 0 and change it from string to the int
                    String title = properties[1]; //get the item at the position 1
                    String author = properties[2];//get the item at the position 2
                    String publicationYear = properties[3]; //get the item at the position 3
                    String publisher = properties[4]; //get the item at the position 4
                    boolean visible = Boolean.parseBoolean(properties[5]); //get the item at the position 5 and change it from string to int
                    Book book = new Book(id, title, author, publicationYear, publisher,visible); //create a new object book with variables. New object could be created just with properties (ex: new Book(Integer.parseInt(properties[0]), properties[1],...))
                    library.addBook(book);//add new book to the library
                } catch (NumberFormatException ex) { //catch an error if it occurs and add show it 
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;//increase line by 1
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {//write to the resource
            for (Book book : library.getBooks()) { //for every book do this
                out.print(book.getId() + SEPARATOR); //print id and separator, example: "1::"
                out.print(book.getTitle() + SEPARATOR); //print title and separator, example: "My Book::"
                out.print(book.getAuthor() + SEPARATOR); //print author and separator, example: "Zan::"
                out.print(book.getPublicationYear() + SEPARATOR); //print publication year and separator, example: "2019::"
                out.print(book.getPublisher() + SEPARATOR); //print publisher and separator, example: "Amazon::"
                out.println();//since this is all printed in one line, we are going to new line here.
            }
        }
    }
}
