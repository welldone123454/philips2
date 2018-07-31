package dataBase;

import java.util.HashMap;
import java.util.Iterator;

public class ArrayListData implements IDataProvider
{	

	private static HashMap<Integer,Book> bookListe;
	private static HashMap<Integer,Author> authorListe;
	
	private static int authorId;
	private static int bookId;
	
	
	public ArrayListData() 
	{
		 bookListe = new HashMap<Integer,Book>();
		 authorListe = new  HashMap<Integer,Author>();
	}

	
	
	//@Override
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
	public void printBookListe() 
	{	
		Iterator mapIt = bookListe.entrySet().iterator();
		System.out.println("Books in the List: "+bookListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	//@Override
	public Book editBook(int bookId) 
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
	
	
	
	//##########################################################
	
	
	//@Override
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
	public void printAuthorListe() 
	{	
		Iterator mapIt = authorListe.entrySet().iterator();
		System.out.println("Authors in the List: "+authorListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
		
	}
	
	//@Override
	public Author editAuthor(int authorId) 
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



	public int numberOfBooksInListe() 
	{
		return bookListe.size();
	}

	public int numberOfAuthorsInListe() 
	{
		return authorListe.size();
	}
	
	
	//##############################################################
	
	public static HashMap<Integer, Book> getBookListe() {
		return bookListe;
	}



	public static void setBookListe(HashMap<Integer, Book> bookListe) {
		ArrayListData.bookListe = bookListe;
	}



	public static HashMap<Integer, Author> getAuthorListe() {
		return authorListe;
	}



	public static void setAuthorListe(HashMap<Integer, Author> authorListe) {
		ArrayListData.authorListe = authorListe;
	}



	public static int getAuthorId() {
		return authorId;
	}



	public static void setAuthorId(int authorId) {
		ArrayListData.authorId = authorId;
	}



	public static int getBookId() {
		return bookId;
	}



	public static void setBookId(int bookId) {
		ArrayListData.bookId = bookId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
