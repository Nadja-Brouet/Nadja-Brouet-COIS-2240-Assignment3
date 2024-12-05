import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

public class LibraryManagementTest {

	private Library library;
	private Transaction transaction;

	@Before
	public void setUp() {
		// Create a new Library instance before each test
		library = new Library();
		transaction = Transaction.getTransaction();
	
	}
	
	@Test
	public void testBookId() {
		// Valid test cases: 100 and 999
		try {
			Book validBook1 = new Book(100, "Valid Book 100");
			assertNotNull(validBook1); // Check if book was created
			assertEquals(100, validBook1.getId()); // Verify ID is correct
			assertTrue(validBook1.isAvailable()); // check if book is available

			Book validBook2 = new Book(999, "Valid Book 999");
			assertNotNull(validBook2);
			assertEquals(999, validBook2.getId());
			assertTrue(validBook2.isAvailable());

		} catch (Exception e) {
			fail("Exception should not be thrown for valid IDs: " + e.getMessage());
		}
		
		// Invalid test case ID less than 100
		try {
			new Book(90, "Invalid Book 90");
            fail("Exception should be thrown for invalid book ID 90");
            
			} catch (Exception e) {
          assertEquals("Invalid book ID.", e.getMessage());
      }
		
		// Invalid test case: ID greater than 999 
        try {
            new Book(1000, "Invalid Book 1000");
            fail("Exception should be thrown for invalid book ID 1000");
        } catch (Exception e) {
            assertEquals("Invalid book ID.", e.getMessage());
        }		
}
	@Test
	public void testBorrowReturn() {
		
		try {
			Book validBook3 = new Book(200, "valid book 200");
			Member validMember1 = new Member(1000, "Jane Smith");
			assertNotNull(validBook3); // Check if book was created
			assertTrue(validBook3.isAvailable()); // check if book is available
			
			 // Borrow book
	        boolean bookBorrow = transaction.borrowBook(validBook3, validMember1);
	        assertTrue("Borrowed book sucessfully", bookBorrow); // Check if book was created
			assertFalse(validBook3.isAvailable()); // book is unavailable after transaction
			
			// Borrow book again
	        boolean bookBorrowAgain = transaction.borrowBook(validBook3, validMember1);
			assertFalse("Cannot borrow same book twice", bookBorrowAgain); // book can't be borrowed again
			
			 //Return book
            boolean bookReturn = transaction.returnBook(validBook3, validMember1);
            assertTrue("Returned book sucessfully", bookReturn); // Check if book was created
			assertFalse(validBook3.isAvailable()); // book is unavailable after transaction
           
			 //Return book again
            boolean bookReturnAgain = transaction.returnBook(validBook3, validMember1);
			assertFalse("Cannot return the same book twice",bookReturnAgain); // book cannot be returned again
           
		}
		catch (Exception e){
			
			fail("Exception should not be thrown: " + e.getMessage());	
		}
	}
	
	@Test
	public void testSingletonTransaction() {
		
		
		try {
			Constructor<Transaction>  constructor  =  Transaction.class.getDeclaredConstructor();
			
			int modifier = constructor.getModifiers();
			
		assertTrue("Modifier is private", Modifier.isPrivate(modifier));
			
		}
		catch (Exception e) {
			  fail("Exception occurred during reflection: " + e.getMessage());
		}
	}

}


}
