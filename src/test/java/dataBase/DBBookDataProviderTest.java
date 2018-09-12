package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class DBBookDataProviderTest {

	private DBBookDataProvider dbBookDataProvider;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	String connectionDriver;
    String connectionAddress; 
    Book book1; 
	Book book2; 
	
	/**
	  * to initialize two books objects and a dataProvider object which can save these books.
	  */
	@BeforeEach
	public  final void setUp() 
	{
	    
	    dbBookDataProvider=mock(DBBookDataProvider.class);
	    book1 = new Book("firstBook","firstAuthor");
		book2 = new Book("SecondBook","SecondAuthor");
		connectionDriver="com.mysql.cj.jdbc.Driver";
	    connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC"; 
	    MockitoAnnotations.initMocks(this);
	}

		
		
	
	@Test
	public void iniConnectionTest() throws Exception 
	{		
		try 
		{
			Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
			Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
			Mockito.when(dbBookDataProvider.iniConnection(Mockito.any(), Mockito.any()) ).thenReturn(mockConnection);
			
			assertThat(mockConnection, is(equalTo(dbBookDataProvider.iniConnection(connectionDriver, connectionAddress) ) ) );
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
		/**
		 * test the saveBook method which should receive a Book object and save it using dataProvider in  a bookList 
		 * @throws Exception 
		 */
		 @Test
		 public final void saveBookTest() throws Exception 
		 {
			 
			 System.out.println("-----------------SaveBookTest------------------");
			
			 
			   Mockito.when(dbBookDataProvider.saveBook(book1)).thenReturn(true);
			    assertTrue(dbBookDataProvider.saveBook(book1));
			    
			    Mockito.when(dbBookDataProvider.numberOfBooksInListe()).thenReturn(1);
			    assertThat(1, is(equalTo(dbBookDataProvider.numberOfBooksInListe())));
	    
		    
		    System.out.println("-----------------End of SaveBookTest------------------");
		   
		 }
		 
		 
		 /**
		  * test the getBook() method which receives a book ID as an integer number and return the book object of that book id if available 
		  */
		 @Test
		 public final void getBookTest()
		 {
			
			 System.out.println("-----------------getBookTest------------------");
			 
			 Mockito.when(dbBookDataProvider.getBook(1)).thenReturn(book1);
			 Mockito.when(dbBookDataProvider.getBook(2)).thenReturn(book2);
			 
			 assertThat(dbBookDataProvider.getBook(1), is(equalTo(book1)));
			 
			 System.out.println("-----------------End of getBookTest------------------");
		 }
		

}
