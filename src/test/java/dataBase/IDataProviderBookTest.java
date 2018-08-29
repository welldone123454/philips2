package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IDataProviderBookTest {

	 IDataProviderBook dataProviderBook;
	 Book book1; 
	 Book book2; 
	 

	 /**
	  * to initialize two books objects and a dataProvider object which can save these books.
	  */
	@BeforeEach
	void setUp() throws Exception 
	{
		System.out.println("-----------------initialising------------------");
		
		 dataProviderBook = new ArrayListData();
		 book1 = new Book("firstBook","firstAuthor");
		 book2 = new Book("SecondBook","SecondAuthor");

		 
		 System.out.println("-----------------End of initialising------------------");
	}	

	/**
	 * test the saveBook method which should receive a Book object and save it using dataProvider in  a bookList 
	 */
	 @Test
	    public final void saveBookTest() 
	 {
		 
		 System.out.println("-----------------SaveBookTest------------------");
		 
	    assertTrue(dataProviderBook.saveBook(book1));
	    
	  //  assertEquals(1,dataProvider.numberOfBooksInListe());
	    assertThat(1, is(equalTo(dataProviderBook.numberOfBooksInListe())));
	    
	    
	    
	    System.out.println("-----------------End of SaveBookTest------------------");
	   
	 }
	 

	 /**
	  * test the getBook() method which receives a book ID as an integer number and return the book object of that book id if available 
	  */
	 @Test
	 public final void getBookTest()
	 {
		
		 System.out.println("-----------------getBookTest------------------");
		 
		 dataProviderBook.saveBook(book1);
		 dataProviderBook.saveBook(book2);
		 
		 System.out.println("hello "+dataProviderBook.getBook(1).getBookName());
		 
		// assertEquals(book1, dataProvider.getBook(1));
		 assertThat(dataProviderBook.getBook(1), is(equalTo(book1)));
		 
		 System.out.println("-----------------End of getBookTest------------------");
	 }
	 
	
	
	
	

}
