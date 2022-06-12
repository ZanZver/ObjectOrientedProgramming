package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PatronDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/patrons.txt"; //path to the data
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	try (Scanner sc = new Scanner(new File(RESOURCE))) { //scan the file
            int line_idx = 1; //count the lines
            while (sc.hasNextLine()) { //check if it needs to scan for new items
                String line = sc.nextLine(); //scan the new line
                String[] properties = line.split(SEPARATOR, -1); //split the line by the "::". Items are going to be in the array, we can access them by element.
                try {
                    int id = Integer.parseInt(properties[0]); //this is an element at position 0. Example: 32
                    String name = properties[1]; //this is an element at position 1. Example: Mike Brown Jr.
                    String phone = properties[2]; //this is an element at position 2. Example: 04125 1235
                    boolean visible = Boolean.parseBoolean(properties[3]); //this is an element at position 3. Example: true. If it is set to true, element is visible
                    Patron patron = new Patron(id, name, phone, visible); //a new patron is created by the elements. If we want we can create new object based on the items in the array without additional itmes(ex: new Patron(Integer.parseInt(properties[0]), properties[1]...)
                    library.addPatron(patron);//patron is added to the system
                } catch (NumberFormatException ex) {//exception is thrown where something went wrong
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;//line counter is increased
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {//function for storing data
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) { //write to the file
            for (Patron patron : library.getPatrons()) {//do this for every patron in the library 
                out.print(patron.getId() + SEPARATOR); //Print it and separator, example: "2::"
                out.print(patron.getName() + SEPARATOR);//print name and separator, example: "Mike Brown Jr.::"
                out.print(patron.getPhone() + SEPARATOR);//Print phone and separator, example:"0123 4123::"
                out.println();//since this was all printed on one line this goes to new line
            }
        }
    }
}