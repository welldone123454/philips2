package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class DBAuthorDataProviderTest {




	private DBAuthorDataProvider dbAuthorDataProvider;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	String connectionDriver;
    String connectionAddress; 
	Author author1; 
	Author author2; 

	
	@BeforeEach
	public  final void setUp() 
	{
	    MockitoAnnotations.initMocks(this);
	    dbAuthorDataProvider=mock(DBAuthorDataProvider.class);
	    author1 = new Author("firstAuthor","firstBook");
	    author2 = new Author("SecondAuthor","SecondBook");
		connectionDriver="com.mysql.cj.jdbc.Driver";
	    connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC"; 

	}

	
	@Test
	public void iniConnectionTest() throws Exception 
	{		
		
		
		Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
		Mockito.when(dbAuthorDataProvider.iniConnection(Mockito.any(), Mockito.any()) ).thenReturn(mockConnection);
		
		assertThat(mockConnection, is(equalTo(dbAuthorDataProvider.iniConnection(connectionDriver, connectionAddress) ) ) );

	}

	
	/**
	 * test the saveAuthor method which should receive an Author object and save it using dbAuthorDataProvider in  an AuthorList 
	 * @throws Exception 
	 */
	
	 @Test
	 public void saveAuthorTest() throws Exception 
	 {
		 
		 System.out.println("-----------------SaveAuthorTest------------------");

			 	Mockito.when(dbAuthorDataProvider.saveAuthor(author1)).thenReturn(true);
			    assertTrue(dbAuthorDataProvider.saveAuthor(author1));
			    
			    Mockito.when(dbAuthorDataProvider.numberOfAuthorsInListe()).thenReturn(1);
			    assertThat(1, is(equalTo(dbAuthorDataProvider.numberOfAuthorsInListe())));
	    
	    System.out.println("-----------------End of SaveAuthorTest------------------");
	   
	 }
	 
	 
	 /**
	  * test the getAuthor() method which receives an author ID as an integer number and return the author object of that author id if available 
	  */
	
	 @Test
	 public final void getAuthorTest()
	 {
		
		 System.out.println("-----------------getAuthorTest------------------");
		 
		 Mockito.when(dbAuthorDataProvider.getAuthor(1)).thenReturn(author1);
		 Mockito.when(dbAuthorDataProvider.getAuthor(2)).thenReturn(author2);
		 

		 assertThat(dbAuthorDataProvider.getAuthor(1), is(equalTo(author1)));
		 
		 System.out.println("-----------------End of getBookTest------------------");
	 }
	 

}
