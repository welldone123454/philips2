package dataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ArrayListData implements IDataProvider
{	

	//to save books in a list locally
	private static HashMap<Integer,Book> bookListe;
	
	//to save authors in a list locally
	private static HashMap<Integer,Author> authorListe;
	
	//to give an author an id
	private static int authorId;
	
	//to give a book an id
	private static int bookId;
	
	//############################################################################
	//Constructors:
	
	/**
	 * Creates a new object and initialize a bookListe to save books and an authorListe to save authors
	 */
	public ArrayListData() 
	{
		 bookListe = new HashMap<Integer,Book>();
		 authorListe = new  HashMap<Integer,Author>();
	}

	
	
	//############################################################################
	//Overridden methods:
			
	//################################
	//Book's methods :
	
	
	//@Override
	/**
	 * 
	 * @param book : a Book object
	 * @return true if book is saved successfully and false if not
	 */
	public boolean saveBook(Book book) 
	{
		boolean b=false;
		if(!bookListe.containsValue(book))
		{
			bookId=bookListe.size()+1;
			book.setId(bookId);
			
			
			
			if(!saveAuthor(book.getAuthor()))
			{
				
//							for (int key : authorListe.keySet()) {
							//    String value = map.get(key);
							
					for(int i = 1; i<= authorListe.size(); i++) 
					{
						if(authorListe.get(i).getAuthorName().equals(book.getAuthor().getAuthorName())) 
						{
						authorListe.get(i).addBookToAuthorList(book);		
						}
					}

						
			}
			bookListe.put(book.getId(), book);
			System.out.println("Data Saved!!");
			b=true;
		}
		else 
		{		
			System.out.println("book id "+book.getId()+" existes in database!!");
		}
		
		return b;
	}

	
	
	//@Override
	/**
	 * 
	 * @param bookId : print a book information of a given book id number
	 */
	public void printBook(int bookId) 
	{	
		if(bookListe.containsKey(bookId)) 
		{
			System.out.println(bookId+"="+bookListe.get(bookId));
		}
		else 
		{
			System.out.println("Book: \""+bookListe.get(bookId).getBookName()+"\" is not in BooksListe!!");
		}
		
		
	}
	
	
	
	//@Override
	/**
	 * Prints all books saved in the books list 
	 */
	public void printBookListe() 
	{	
		//Iterator mapIt = bookListe.entrySet().iterator();
		Iterator<Entry<Integer, Book>> mapIt = bookListe.entrySet().iterator();
		System.out.println("Books in the List: "+bookListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	
	
	//@Override
	/**
	 * @param bookId : an integer value of book id which is already been saved in the books list
	 * @return if the book id is in the books list it return a book object of the given id else return null
	 */
	public Book getBook(int bookId) 
	{	
		if(bookListe.containsKey(bookId)) 
		{
			return bookListe.get(bookId);
		}
		else 
		{
			System.out.println("BookId: \""+bookId+"\" is not in BooksListe!!");
		}
		return null;
		
	}
	
	
	
	//@Override
	/**
	 * 
	 * @return return integer of the total number of saved books
	 */
	public int numberOfBooksInListe() 
	{
		return bookListe.size();
	}

	
	//##########################################################
	//Author's methods: 
	
	//@Override
	/**
	 * 
	 * @param author : a author object
	 * @return true if author is saved successfully and false if not
	 */
	public boolean saveAuthor(Author author) {
		boolean b=false;
		if(!authorListe.containsValue(author))
		{
			authorId=authorListe.size()+1;
			author.setId(authorId);
			
			authorListe.put(author.getId(), author);
			System.out.println("Author Saved!!");
			b=true;
		}
		else 
		{			
			System.out.println("Author: \""+author.getAuthorName()+"\" existes in database!!");
		}
		
		return b;
		
	}


	
	//@Override
	/**
	 * 
	 * @param authorId : print a book information of a given book id number
	 */
	public void printAuthor(int authorId) 
	{	
		if(authorListe.containsKey(authorId)) 
		{
			System.out.println( authorListe.get(authorId));
		}
		else 
		{
			System.out.println("Author: \""+authorListe.get(authorId).getAuthorName()+"\" is not in AuthorsListe!!");
		}
	
	}
	
	
	
	//@Override
	/**
	 * Prints all authors saved in the authors list 
	 */
	public void printAuthorListe() 
	{	
		//Iterator mapIt = authorListe.entrySet().iterator();
		Iterator<Entry<Integer, Author>> mapIt = authorListe.entrySet().iterator();
		System.out.println("Authors in the List: "+authorListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	
	
	//@Override
	/**
	 * @param authorId : an integer value of author id which is already been saved in the authors list
	 * @return if the author id is in the authors list it return an author object of the given id else return null
	 */
	public Author getAuthor(int authorId) 
	{	
		if(authorListe.containsKey(authorId)) 
		{
			return authorListe.get(authorId);
		}
		else 
		{
			System.out.println("Author: \""+authorListe.get(authorId).getAuthorName()+"\" is not in AuthorsListe!!");
		}

		return null;
		
	}





	//@Override
	/**
	 * 
	 * @return return integer of the total number of saved authors
	 */
	public int numberOfAuthorsInListe() 
	{
		return authorListe.size();
	}
	
	
	//##############################################################
	//Other methods
	
	/**
	 * 
	 * @return returns a HashMap list of the books which been saved
	 */
	public static HashMap<Integer, Book> getBookListe() {
		return bookListe;
	}



	/**
	 * 
	 * @return returns a HashMap list of the authors which been saved
	 */
	public static HashMap<Integer, Author> getAuthorListe() {
		return authorListe;
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
