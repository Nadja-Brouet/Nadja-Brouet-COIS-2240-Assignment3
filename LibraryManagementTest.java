import static org.junit.Assert.*;
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

}
