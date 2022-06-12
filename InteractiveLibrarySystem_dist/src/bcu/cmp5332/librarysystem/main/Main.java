package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.data.LibraryData;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException, LibraryException {
        
        Library library = LibraryData.load();//load library data to library
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //start reading from the user input
        
        System.out.println("Library system"); //display message that this is library system
        System.out.println("Enter 'help' to see a list of available commands."); //display message that help opens up command
        while (true) { //
            System.out.print("> ");//print symbol to indicate where to write line
            String line = br.readLine(); //read line that was written
            if (line.equals("exit")) { //check if user has typed in exit
                break; //exit the loop
            }
            try {
                Command command = CommandParser.parse(line); //create a command based on the written line
                command.execute(library, LocalDate.now()); //execute the command
            } catch (LibraryException ex) { 
                System.out.println(ex.getMessage()); //show an error
            }
        }
        LibraryData.store(library); //save things to library
        System.exit(0); //exit the system
    }
}
