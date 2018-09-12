package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ArrayListDataProviderBookTest {

	private IDataProviderBook arrayListDataProviderBook;
    Book book1; 
	Book book2; 
	
	/**
	  * to initialize two books objects and a dataProvider object which can save these books.
	  */
	@BeforeEach
	public  final void setUp() 
	{
	    arrayListDataProviderBook= new ArrayListDataProviderBook();
	    book1 = new Book("firstBook","firstAuthor");
		book2 = new Book("SecondBook","SecondAuthor");

	}
	
	/**
	 * test the saveBook method which should receive a Book object and save it using dataProvider in  a bookList 
	 * @throws Exception 
	 */
	 @Test
	 public final void saveBookTest() throws Exception 
	 {
		 
		 System.out.println("-----------------SaveBookTest------------------");
		
		    assertTrue(arrayListDataProviderBook.saveBook(book1));
		    assertTrue(arrayListDataProviderBook.saveBook(book2));
		    
		    assertThat(2, is(equalTo(arrayListDataProviderBook.numberOfBooksInListe())));
    
	    
	    System.out.println("-----------------End of SaveBookTest------------------");
	   
	 }
	 
	 
	 /**
	  * test the getBook() method which receives a book ID as an integer number and return the book object of that book id if available 
	  */
	 @Test
	 public final void getBookTest()
	 {
		
		 System.out.println("-----------------getBookTest------------------");
		 
		 arrayListDataProviderBook.saveBook(book1);
		 arrayListDataProviderBook.saveBook(book2);

		 assertThat(arrayListDataProviderBook.getBook(1), is(equalTo(book1)));
		 
		 System.out.println("-----------------End of getBookTest------------------");
	 }
	
	 
	 
	


}
