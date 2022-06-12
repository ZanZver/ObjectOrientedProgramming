package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class IssueBookWindow extends JFrame implements ActionListener{
    private MainWindow mw; //create new main window
    private JTextField patIDText = new JTextField(); //declare a text field for patrons id
    private JTextField bookIDText = new JTextField(); //declare a text field for books id
    
    private JButton issueBtn = new JButton("Issue"); //declare issue button
    private JButton cancelBtn = new JButton("Cancel"); //declare cancel button

    public IssueBookWindow(MainWindow mw) {
        this.mw = mw; //set the frame
        initialize(); //call main function
    }

    private void initialize() { //main function
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set ui manager
        } catch (Exception ex) {
        	System.out.println(ex); //inform abut an error if occurs
        }

        setTitle("Issue book"); //set the title of the frame

        setSize(300, 200); //set the size of the frame
        JPanel topPanel = new JPanel(); //declare a new top panel
        topPanel.setLayout(new GridLayout(5, 2)); //add grid layout to top panel
        topPanel.add(new JLabel("Patron ID: ")); //add a label patron id to top panel
        topPanel.add(patIDText); //insert text box for patrons id to top panel
        topPanel.add(new JLabel("Book ID: "));//insert label book id to top panel
        topPanel.add(bookIDText);  //add book id text box to top panel

        JPanel bottomPanel = new JPanel(); //create new panel as bottom panel
        bottomPanel.setLayout(new GridLayout(1, 3)); //add grid layout to bottom panel
        bottomPanel.add(new JLabel("     ")); //add spacing to bottom panel
        bottomPanel.add(issueBtn); //add issue button to bottom panel
        bottomPanel.add(cancelBtn); //add cancel button to bottom panel

        issueBtn.addActionListener(this); //add action listener for issue book button
        cancelBtn.addActionListener(this); //add action listener for cancel button

        this.getContentPane().add(topPanel, BorderLayout.CENTER); //put top panel as center to main frame
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH); //put bottom panel as south to main frame
        setLocationRelativeTo(mw); //set the location of main frame

        setVisible(true); //set main frame as visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == issueBtn) { //check if issue book has been pressed
            try { 
				issueBook(); //call function
			} catch (LibraryException e) {
				System.out.println(e); //inform about library exception
				e.printStackTrace();
			}
        } else if (ae.getSource() == cancelBtn) { //check if cancel button has been clicked
            this.setVisible(false); //hide the frame
        }
    }

    private void issueBook() throws LibraryException {
        String patronID = patIDText.getText();//get the patrons id from text box to the string
		String bookID = bookIDText.getText(); //set books id string from text box
		// create and execute the AddBook Command
		
		Patron patron = new Patron(); //declare new patron
		patron.setId(Integer.parseInt(patronID)); //set id of patron and parse it as int
		
		Book book = new Book(); //declare new book
		book.setId(Integer.parseInt(bookID)); //parse it as int and set the id
		
		if(patron.checkIfTheBookIsTaken(book).contentEquals("False")) { //check if book is available
			JOptionPane.showMessageDialog(this, "You have borrowd the book"); //inform user that he has borrowed the book
			patron.borrowBook(book, LocalDate.now().plusDays(7));
		}else {
			JOptionPane.showMessageDialog(this, patron.checkIfTheBookIsTaken(book)); //tell user that book is taken and until when
		}
		//patron.borrowBook(book, LocalDate.now().plusDays(7));
		
		mw.displayBooks();//show books
		this.setVisible(false); //hide the frame
    }
}