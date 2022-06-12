package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>(); //create a new list for data managers 
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new BookDataManager());//add a book manager
        dataManagers.add(new PatronDataManager()); //add a patron manager
        //dataManagers.add(new LoanDataManager()); //add a loan manager, but it is not in use in this program. I am using it from other way around
    }
    
    public static Library load() throws LibraryException, IOException {
        Library library = new Library(); //create a new object library
        for (DataManager dm : dataManagers) { //for every data manager do something
            dm.loadData(library); //load library to data manager
        }
        return library; //return library
    }

    public static void store(Library library) throws IOException {
        for (DataManager dm : dataManagers) { //for every data manager do something
            dm.storeData(library); //store library in the data
        }
    }
    
}
