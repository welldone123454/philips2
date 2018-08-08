package dataBase;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;


import org.junit.Test;

import org.mockito.Mockito;

import org.junit.Before;


public class SqlTest {
	
	 Sql sqlDataProvider;
	 Book book1; 
	 Book book2; 

	@Before
	public void setUp() 
	{
		
		
		
		System.out.println("-----------------initialising------------------");
		
		
		
		sqlDataProvider = mock(Sql.class);
		
		
		 book1 = new Book("firstBook","firstAuthor");
		 book2 = new Book("SecondBook","SecondAuthor");

		 
		 System.out.println("-----------------End of initialising------------------");
	}
	
	
	@Test
	public void saveBookTest() 
	{
		
		System.out.println("-----------------Test SaveBook------------------");
		
		
		when(sqlDataProvider.saveBook(book1) ).thenReturn(true);
		
		when(sqlDataProvider.numberOfBooksInListe() ).thenReturn(1);
		
		assertEquals(1, sqlDataProvider.numberOfBooksInListe());
		boolean saveBookResult =sqlDataProvider.saveBook(book1);
		assertTrue(saveBookResult);
		
		
		System.out.println("-----------------End of SaveBookTest------------------");
	}
	
	
	@Test
	public void saveAuthorTest() 
	{
		
		System.out.println("-----------------Test SaveAuthor------------------");
		
		
		when(sqlDataProvider.saveAuthor(book1.getAuthor()) ).thenReturn(true);
		
		boolean saveAuthoResult =sqlDataProvider.saveAuthor(book1.getAuthor());
		assertTrue(saveAuthoResult);
		
		
		System.out.println("-----------------End of SaveAuthorTest------------------");
	}
	
	
	@Test
	public void getBookTest() 
	{
		System.out.println("-----------------Test getBook------------------");
		
		
		when(sqlDataProvider.getBook(1)).thenReturn(book1);

		assertEquals(book1, sqlDataProvider.getBook(1) );
		
		
		System.out.println("-----------------End of getBookTest------------------");
	}
	
	
	@Test
	public void getAuthorTest() 
	{
		System.out.println("-----------------Test getAuthor------------------");
		
		
		when(sqlDataProvider.getAuthor(1) ).thenReturn(book1.getAuthor());

		assertEquals(book1.getAuthor(), sqlDataProvider.getAuthor(1) );
		
		
		System.out.println("-----------------End of getAuthorTest------------------");
	}
	
	
	
	
	

}
