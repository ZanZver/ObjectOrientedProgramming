package bcu.cmp5332.librarysystem.data;

//import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;

public class LoanDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) { //throws IOException, LibraryException {
        // TODO: implementation here
    	//my application is not using this data manager. It can be used the same way Patron and Book managers are.
    }

    @Override
    public void storeData(Library library) {//throws IOException {
        // TODO: implementation here
    }
    
}
