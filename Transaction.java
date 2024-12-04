import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Transaction {

	 // Private static instance of the Transaction class for Singleton pattern
    private static Transaction instance;
    
    // Private constructor 
    private Transaction() {}
    
    // Public accessor method 
    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }
    
    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            
            // Save the transaction to a file
            saveTransaction(transactionDetails);
            
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            
            // Save the transaction to a file
            saveTransaction(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }

    // Get the current date and time in a readable format
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    // Save transaction details
    private void saveTransaction(String transactionDetails) {
    	
    	// write saved transactions to transactions.txt file using
    	 try (BufferedWriter writeDetails = new BufferedWriter(new FileWriter("transactions.txt", true))) {// open buffered writer and file writer when opening fileWriter if file does not already exist create file
    		 
             writeDetails.write(transactionDetails +"/n"); // Write the transaction details to the file
     
         } 
    	 // Catch any IOException
    	 catch (IOException e) {
             System.out.println("An error occurred while saving the transaction.");
             e.printStackTrace();
         }
    	
    }
    // Display transaction history 
    public void displayTransactionHistory() {
    	   try (BufferedReader readDetails = new BufferedReader(new FileReader("transactions.txt"))) {
    		   String details;// string to store what is written in Transaction.txt file
    		   System.out.println("\n--- Transaction History ---");
    		   boolean hasTransactions = false;// declare no transactions until transaction details are checked and read in while loop
    		   
    		// Read each line from the file store in details String
               while ((details = readDetails.readLine()) != null) {
                   System.out.println(details);// print transaction detail 
                   hasTransactions = true;// transaction.txt has saved transactions
               }
               // No Transactions
               if (!hasTransactions) {
                   System.out.println("No transactions found.");
               }
               System.out.println("----------------------------");
    	   }
    	   // Catch any IOException
      	 catch (IOException e) {
               System.out.println("An error occurred while reading  transaction history. File may not exist.");
               e.printStackTrace();
           }
    	   
    	
    }
    
}