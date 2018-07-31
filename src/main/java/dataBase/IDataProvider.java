package dataBase;

import java.util.HashMap;

public interface IDataProvider 
{
	
	boolean saveBook(Book book);
	
	void printBook(int BookId);

	void printBookListe();
	
	Book editBook(int bookId);
	
	int numberOfBooksInListe();
	
	//#######################################
	
	boolean saveAuthor(Author author);
	
	void printAuthor(int authorId);

	void printAuthorListe();
	
	
	Author editAuthor(int authorId);
	
	int numberOfAuthorsInListe();
	
}
