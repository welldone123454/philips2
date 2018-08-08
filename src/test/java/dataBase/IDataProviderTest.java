package dataBase;

import static org.junit.Assert.*;

import org.junit.Before;

import dataBase.*;
import org.junit.Test;

public class IDataProviderTest 
{
	 
	 IDataProvider dataProvider;
	 Book book1; 
	 Book book2; 
	 

	@Before
	public final void initialisieren() 
	{
		System.out.println("-----------------initialising------------------");
		
		 dataProvider = new ArrayListData();
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
		 
	    assertTrue(dataProvider.saveBook(book1));
	    assertEquals(1,dataProvider.numberOfBooksInListe());
	    
	    System.out.println("-----------------End of SaveBookTest------------------");
	   
	 }
	 
	//saveAuthor method should receive an Author object and save it in AuthorsListe and add book to authorsBookListe
	 
	 @Test
	    public final void saveAuthorTest() 
	 {
		 System.out.println("-----------------SaveAuthorTest------------------");
		
	    assertTrue(dataProvider.saveAuthor(book1.getAuthor()));
	    assertEquals(1,dataProvider.numberOfAuthorsInListe());
	    assertEquals(1,book1.getAuthor().getNumberOfBooks());
	    
	    System.out.println("-----------------End of SaveAuthorTest------------------");
	    
	   
	 }
	 
	 
	 //printBook method should return a book object to be modified using the book idNumber 
	 
	 @Test
	 public final void editBookTest()
	 {
		
		 System.out.println("-----------------EditBookTest------------------");
		 
		 dataProvider.saveBook(book1);
		 dataProvider.saveBook(book2);
		 
		 System.out.println("hello "+dataProvider.getBook(1).getBookName());
		 
		 assertEquals(book1, dataProvider.getBook(1));
		 
		 System.out.println("-----------------End of EditBookTest------------------");
	 }
	 
	 
}
