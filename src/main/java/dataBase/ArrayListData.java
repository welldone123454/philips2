package dataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//public class ArrayListData implements IDataProviderBook, IDataProviderAuthor
public class ArrayListData implements IDataProvidable
{	

	//to save books in a list locally
	private static HashMap<Integer,Book> bookList;
	
	//to save authors in a list locally
	private static HashMap<Integer,Author> authorList;
	
	//to give an author an id
	private static int authorId;
	
	//to give a book an id
	private static int bookId;
	
	private static final Logger LOG = LoggerFactory.getLogger(ArrayListData.class);
	
	//############################################################################
	//Constructors:
	
	/**
	 * Creates a new object and initialize a bookListe to save books and an authorListe to save authors
	 */
	public ArrayListData() 
	{
		 bookList = new HashMap<Integer,Book>();
		 authorList = new  HashMap<Integer,Author>();
	}

	
	
	//############################################################################
	//Overridden methods:
			
	//################################
	//Book's methods :
	
	
	
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
			
			
			
			if(!saveAuthor(book.getAuthor()))
			{						
					for(int i = 1; i<= authorList.size(); i++) 
					{
						if(authorList.get(i).getAuthorName().equals(book.getAuthor().getAuthorName())) 
						{
						authorList.get(i).addBookToAuthorList(book);		
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

	
	//##########################################################
	//Author's methods: 
	
	
	/**
	 * 
	 * @param author : a author object
	 * @return true if author is saved successfully and false if not
	 */
	@Override
	public boolean saveAuthor(Author author) {
		boolean b=false;
		if(!authorList.containsValue(author))
		{
			authorId=authorList.size()+1;
			author.setId(authorId);
			
			authorList.put(author.getId(), author);
			//System.out.println("Author Saved!!");
			LOG.info(" Author: \"{}\" saved!!",author.getAuthorName());
			b=true;
		}
		else 
		{			
			//System.out.println("Author: \""+author.getAuthorName()+"\" existes in database!!");
			LOG.warn(" Author: \"{}\" exists in database!!",author.getAuthorName());
		}
		
		return b;
		
	}


	
	
	/**
	 * 
	 * @param authorId : print a book information of a given book id number
	 */
	@Override
	public void printAuthor(int authorId) 
	{	
		if(authorList.containsKey(authorId)) 
		{
			System.out.println( authorList.get(authorId));
		}
		else 
		{
			//System.out.println("Author: \""+authorList.get(authorId).getAuthorName()+"\" is not in AuthorsList!!");
			LOG.warn(" Author: \"{}\" is not in authorsList!!",authorId);
		}
	
	}
	
	
	
	
	/**
	 * Prints all authors saved in the authors list 
	 */
	@Override
	public void printAuthorListe() 
	{	
		//Iterator mapIt = authorListe.entrySet().iterator();
		Iterator<Entry<Integer, Author>> mapIt = authorList.entrySet().iterator();
		System.out.println("Authors in the List: "+authorList.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	
	
	
	/**
	 * @param authorId : an integer value of author id which is already been saved in the authors list
	 * @return if the author id is in the authors list it return an author object of the given id else return null
	 */
	@Override
	public Author getAuthor(int authorId) 
	{	
		if(authorList.containsKey(authorId)) 
		{
			return authorList.get(authorId);
		}
		else 
		{
			//System.out.println("Author: \""+authorList.get(authorId).getAuthorName()+"\" is not in AuthorsListe!!");
			LOG.info(" Author: \"{}\" is not in authorsList!!",authorId);
		}

		return null;
		
	}





	
	/**
	 * 
	 * @return return integer of the total number of saved authors
	 */
	@Override
	public int numberOfAuthorsInListe() 
	{
		return authorList.size();
	}
	
	
	//##############################################################
	//Other methods
	
	/**
	 * 
	 * @return returns a HashMap list of the books which been saved
	 */
	public static HashMap<Integer, Book> getBookListe() {
		return bookList;
	}



	/**
	 * 
	 * @return returns a HashMap list of the authors which been saved
	 */
	public static HashMap<Integer, Author> getAuthorListe() {
		return authorList;
	}

	
	
	/**
	 * 
	 * @return Integer value of the author's Id
	 */
	public static int getAuthorId() {
		return authorId;
	}


/**
 * sets author's id to the given authorId
 * @param authorId : integer value of the wished new author id 
 */
	public static void setAuthorId(int authorId) {
		ArrayListData.authorId = authorId;
	}


	/**
	 * 
	 * @return Integer value of the book's Id
	 */
	public static int getBookId() {
		return bookId;
	}


	/**
	 * sets book's id to the given authorId
	 * @param bookId : integer value of the wished new book id 
	 */
	public static void setBookId(int bookId) {
		ArrayListData.bookId = bookId;
	}
	
	

	
	
}
