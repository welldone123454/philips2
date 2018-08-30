package dataBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.junit.Before;


public class DBDataProviderTest {
	
	IDataProvidable sqlDataProvider;
	 Book book1; 
	 Book book2; 
	 Author author1;
	 Author author2;

	 /**
	  * to initialize two books objects with there authors and Sql dataProvider object which can save these books and thier authors.
	  */
	@BeforeEach
	public  void setUp() throws Exception 
	{
		
		
		System.out.println("-----------------initialising------------------");	
		
		book1 = new Book("firstBook","firstAuthor");
		book2 = new Book("SecondBook","SecondAuthor");
		
		author1 = new Author("firstAuthor","firstBook");
		author2 = new Author("SecondAuthor","SecondBook");
		

		 System.out.println("-----------------End of initialising------------------");
	}
	
	/**
	 * test the saveBook method which should receive a Book object and save it using DBDataProvider in  a sql server 
	 */
	@Test
	public void saveBookTest() 
	{
		
		System.out.println("-----------------Test SaveBook------------------");
		
		sqlDataProvider = mock(DBDataProvider.class);
		
		when(sqlDataProvider.saveBook(book1) ).thenReturn(true);
		assertThat(true, is(equalTo(sqlDataProvider.saveBook(book1) ) ) );
		
		
		when(sqlDataProvider.numberOfBooksInListe() ).thenReturn(1);		
		assertThat(1, is(equalTo(sqlDataProvider.numberOfBooksInListe() ) ) );		
		
		System.out.println("-----------------End of SaveBookTest------------------");
	}
	
	
	/**
	 * test the saveAuthor method which should receive an author object and save it using DBDataProvider in  a sql server 
	 */
	
	@Test
	public void saveAuthorTest() 
	{
		
		System.out.println("-----------------Test SaveAuthor------------------");
		
		sqlDataProvider = mock(DBDataProvider.class);
		
		when(sqlDataProvider.saveAuthor(author1) ).thenReturn(true);
		assertThat(true, is(equalTo(sqlDataProvider.saveAuthor(author1) ) ) );	
		
		when(sqlDataProvider.numberOfAuthorsInListe() ).thenReturn(1);	
		
		assertThat(1, is(equalTo(sqlDataProvider.numberOfAuthorsInListe() ) ) );		
		
		System.out.println("-----------------End of SaveAuthorTest------------------");
	}
	
	
	 /**
	  * test the getBook() method which receives a book ID as an integer number and return the book object of that book id if available 
	  */
	
	@Test
	public void getBookTest() 
	{
		System.out.println("-----------------Test getBook------------------");
		
		sqlDataProvider = mock(DBDataProvider.class);
		
		when(sqlDataProvider.getBook(1)).thenReturn(book1);
		assertThat(book1, is(equalTo(sqlDataProvider.getBook(1) ) ) );	
		
		
		System.out.println("-----------------End of getBookTest------------------");
	}

	
	 /**
	  * test the getAuthor() method which receives an author ID as an integer number and return the author object of that author id if available 
	  */
	
	@Test
	public void getAuthorTest() 
	{
		System.out.println("-----------------Test getAuthor------------------");

		sqlDataProvider = mock(DBDataProvider.class);
		
		when(sqlDataProvider.getAuthor(1)).thenReturn(author1);
		assertThat(author1, is(equalTo(sqlDataProvider.getAuthor(1) ) ) );	
		
		System.out.println("-----------------End of getAuthorTest------------------");
	}
	
	
	
	
	

}
