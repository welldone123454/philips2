package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayListDataProviderAuthorTest {

	private IDataProviderAuthor arrayListDataProviderAuthor;
    Author author1; 
	Author author2; 
	
	/**
	  * to initialize two books objects and a dataProvider object which can save these books.
	  */
	@BeforeEach
	public  final void setUp() 
	{
	    arrayListDataProviderAuthor= new ArrayListDataProviderAuthor();
	    author1 = new Author("firstAuthor","firstBook");
		author2 = new Author("SecondAuthor","SecondBook");

	}

	/**
	 * test the saveAuthor method which should receive an Author object and save it using dataProvider in  an AuthorList 
	 * @throws Exception 
	 */
	 @Test
	 public final void saveAuthorTest() throws Exception 
	 {
		 
		 System.out.println("-----------------SaveAuthorTest------------------");
		
		    assertTrue(arrayListDataProviderAuthor.saveAuthor(author1));
		    assertTrue(arrayListDataProviderAuthor.saveAuthor(author2));
		    
		    assertThat(2, is(equalTo(arrayListDataProviderAuthor.numberOfAuthorsInListe())));
    
	    
	    System.out.println("-----------------End of SaveAuthorTest------------------");
	   
	 }
	 
 
	 /**
	  * test the getAuthor() method which receives an Author ID as an integer number and return the Author object of that Author id if available 
	  */
	 @Test
	 public final void getAuthorTest()
	 {
		
		 System.out.println("-----------------getAuthorTest------------------");
		 
		 arrayListDataProviderAuthor.saveAuthor(author1);
		 arrayListDataProviderAuthor.saveAuthor(author2);

		 assertThat(arrayListDataProviderAuthor.getAuthor(1), is(equalTo(author1)));
		 
		 System.out.println("-----------------End of getAuthorTest------------------");
	 }
	
	 
}
