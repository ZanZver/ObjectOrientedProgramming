package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;     //name, ex: Jon
    private final String phone;    //phone, ex: 041245. Since we are not doing anything with numbers it is better to be int. Users can put in numbers like 04123-125612 and that can complicate things
    private final boolean visible; //variable for visiblity. If you want to add patron but it is hidden from the system it should be false. For patron to be visible, set it to true.

    public AddPatron(String name, String phone, boolean visible) {
        this.name = name;        
        this.phone = phone;
        this.visible = visible; 
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation here
    	int lastIndex = library.getPatrons().size() - 1;
    	int maxId = library.getPatrons().get(lastIndex).getId();
    	
    	Patron patron = new Patron(++maxId, name, phone,visible);
        library.addPatron(patron);
        
        //when patron is created, write it to the file
        try(FileWriter fw = new FileWriter("../InteractiveLibrarySystem_dist/resources/data/patrons.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(++maxId + "::" + name + "::" + phone + "::" + visible + "::"); //out is for writing to the patrons.txt file
			    System.out.println("Patron #" + patron.getId() + " added."); //inform that a patron is created
		} catch (IOException e) {
			    //exception handling left as an exercise for the reader
				System.out.println(e);
				System.out.println("Adding record has faild, please try again");
		}
        
        
    }
}
