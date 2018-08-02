
import static org.junit.Assert.*;

import org.junit.Before;

import dataBase.*;
import org.junit.Test;

public class IDataProviderTest 
{
	 
	 IDataProvider instance;
	 Book book1; 
	 Book book2; 
	 

	@Before
	public final void initialisieren() 
	{
		System.out.println("-----------------initialising------------------");
		
		 instance = new ArrayListData();
		 book1 = new Book("firstBook","firstAuthor");
		 book2 = new Book("SecondBook","SecondAuthor");

		 
		 System.out.println("-----------------End of initialising------------------");
	}
		
	/*
	 *
	 */
	//saveBook method should receive a Book object and save it in bookListe and booksAuthor in authorsListe
	 @Test
	    public final void saveBookTest() 
	 {
		 
		 System.out.println("-----------------SaveBookTest------------------");
		 
	    assertTrue(instance.saveBook(book1));
	    assertEquals(1,instance.numberOfBooksInListe());
	    
	    System.out.println("-----------------End of SaveBookTest------------------");
	   
	 }
	 
	//saveAuthor method should receive an Author object and save it in AuthorsListe and add book to authorsBookListe
	 
	 @Test
	    public final void saveAuthorTest() 
	 {
		 System.out.println("-----------------SaveAuthorTest------------------");
		
	    assertTrue(instance.saveAuthor(book1.getAuthor()));
	    assertEquals(1,instance.numberOfAuthorsInListe());
	    assertEquals(1,book1.getAuthor().getNumberOfBooks());
	    
	    System.out.println("-----------------End of SaveAuthorTest------------------");
	    
	   
	 }
	 
	 
	 //printBook method should return a book object to be modified using the book idNumber 
	 
	 @Test
	 public final void editBookTest()
	 {
		
		 System.out.println("-----------------EditBookTest------------------");
		 
		 instance.saveBook(book1);
		 instance.saveBook(book2);
		 
		 System.out.println("hello "+instance.editBook(1).getBookName());
		 
		 assertEquals(book1, instance.editBook(1));
		 
		 System.out.println("-----------------End of EditBookTest------------------");
	 }
	 
	 
}
