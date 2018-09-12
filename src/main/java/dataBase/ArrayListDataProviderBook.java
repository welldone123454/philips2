package dataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayListDataProviderBook implements IDataProviderBook
{
	
	
	
	
	
	//to save books in a list locally
	private static HashMap<Integer,Book> bookList;
	
	//to give a book an id
	private static int bookId;
	
	private IDataProviderAuthor arrayListDataProviderAuthor;
	
	private static final Logger LOG = LoggerFactory.getLogger(ArrayListDataProviderBook.class);
	
//############################################################################
	//Constructors:
	
	/**
	 * Creates a new object and initialize a bookListe to save books and an authorListe to save authors
	 */
	public ArrayListDataProviderBook() 
	{
		 bookList = new HashMap<Integer,Book>();
		 arrayListDataProviderAuthor = new ArrayListDataProviderAuthor();
	}

	
	//############################################################################
	//Overridden methods:
			
	
	
	

	/**
	 * 
	 * @param book : a Book object
	 * @return true if book is saved successfully and false if not
	 */
	@Override
	public boolean saveBook(Book book) 
	{
		boolean b=false;
		if(!bookList.containsValue(book))
		{
			bookId=bookList.size()+1;
			book.setId(bookId);
			
			
			
			if(!arrayListDataProviderAuthor.saveAuthor(book.getAuthor()))
			{						
					for(int i = 1; i<= arrayListDataProviderAuthor.numberOfAuthorsInListe(); i++) 
					{
						if(arrayListDataProviderAuthor.getAuthor(i).getAuthorName().equals(book.getAuthor().getAuthorName())) 
						{
							arrayListDataProviderAuthor.getAuthor(i).addBookToAuthorList(book);		
						}
					}
						
			}
			bookList.put(book.getId(), book);
			//System.out.println("Data Saved!!");
			LOG.info(" book: \"{}\" saved to Sql!!",book.getBookName());
			b=true;
		}
		else 
		{		
			//System.out.println("book id "+book.getId()+" existes in database!!");
			LOG.warn(" book: \"{}\" exists in database!!",book.getBookName());
		}
		
		return b;
	}


	/**
	 * 
	 * @param bookId : print a book information of a given book id number
	 */
	@Override
	public void printBook(int bookId) 
	{	
		if(bookList.containsKey(bookId)) 
		{
			System.out.println(bookId+"="+bookList.get(bookId));
			
		}
		else 
		{
			//System.out.println("Book: \""+bookList.get(bookId).getBookName()+"\" is not in BooksList!!");
			LOG.warn(" book: \"{}\" in not in BooksList!!",bookId);
		}
		
		
	}
	

	/**
	 * Prints all books saved in the books list 
	 */
	@Override
	public void printBookListe() 
	{	
		//Iterator mapIt = bookListe.entrySet().iterator();
		Iterator<Entry<Integer, Book>> mapIt = bookList.entrySet().iterator();
		System.out.println("Books in the List: "+bookList.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	/**
	 * @param bookId : an integer value of book id which is already been saved in the books list
	 * @return if the book id is in the books list it return a book object of the given id else return null
	 */
	@Override
	public Book getBook(int bookId) 
	{	
		if(bookList.containsKey(bookId)) 
		{
			return bookList.get(bookId);
		}
		else 
		{
			//System.out.println("BookId: \""+bookId+"\" is not in BooksListe!!");
			LOG.info(" book: \"{}\" is not in the booklist!!",bookId);
		}
		return null;
		
	}
	
	/**
	 * 
	 * @return return integer of the total number of saved books
	 */
	@Override
	public int numberOfBooksInListe() 
	{
		return bookList.size();
	}

	

	@Override
	public Book getBook(Book book) {
		for(int i=1; i<=bookList.size(); i++) 
		{
			if(bookList.get(i).getBookName().equals(book.getBookName())) 
			{
				return bookList.get(i);
			}
			/*
			else 
			{
				LOG.warn("book Name: \"{}\" is not available in the bookList",book.getBookName());
			}
			*/
		}
		return null;
	}
	

}
