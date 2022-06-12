package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons(); //create a list of patrons
        for (Patron patron : patrons) {//for every patron do this:
            if(!patron.getDetailsShort().contentEquals("null")) { //check if a list is full
        		System.out.println(patron.getDetailsShort()); //print current patron details. 
        	}
        }
        System.out.println(patrons.size() + " patron(s)"); //print number of patrons in the list
    }
}