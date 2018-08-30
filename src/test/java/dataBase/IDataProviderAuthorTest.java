package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IDataProviderAuthorTest {

	IDataProvidable dataProvider;
	 Author author1; 
	 Author author2; 
	 

	 /**
	  * to initialize two books objects with there authors and a dataProvider object which can save these books author.
	  */
	@BeforeEach
	void setUp() throws Exception 
	{
		System.out.println("-----------------initialising------------------");
		
		dataProvider = new ArrayListData();
		 author1 = new Author("FirstAuthor","FirstBook");
		 author2 = new Author("SecondAuthor","SecondBook");

		 
		 System.out.println("-----------------End of initialising------------------");
	}


	 /**
	  * test the saveBook method which should receive an author object and save it using dataProvider in  an authorList 
	  */
	 @Test
	    public final void saveAuthorTest() 
	 {
		 System.out.println("-----------------SaveAuthorTest------------------");
		
	    assertTrue(dataProvider.saveAuthor(author1));
	    // assertEquals(1,dataProvider.numberOfAuthorsInListe());
	    assertThat(1, is(equalTo(dataProvider.numberOfAuthorsInListe())));
	    
	    //assertEquals(1,book1.getAuthor().getNumberOfBooks());
	    assertThat(1, is(equalTo(author1.getNumberOfAuthorsBooks())));
	    
	    System.out.println("-----------------End of SaveAuthorTest------------------");
	    
	   
	 }
	 
	 
	 /**
	  * test the getAuthor() method which receives an author ID as an integer number and return the author object of that author id if available 
	  */
	 @Test
	 public final void getAuthorTest()
	 {
		
		 System.out.println("-----------------getAuthorTest------------------");
		 
		 dataProvider.saveAuthor(author1);
		 dataProvider.saveAuthor(author2);

		// assertEquals(book1, dataProvider.getBook(1));
		 assertThat(dataProvider.getAuthor(1), is(equalTo(author1)));
		 
		 System.out.println("-----------------End of getBookTest------------------");
	 }
	 
	 
}
