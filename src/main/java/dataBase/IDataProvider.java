package dataBase;

public interface IDataProvider 
{
	/**
	 * 
	 * @param book : a Book object
	 * @return true if book is saved successfully and false if not
	 */
	boolean saveBook(Book book);
	
	/**
	 * 
	 * @param bookId : print a book information of a given book id number
	 */
	void printBook(int bookId);

	/**
	 * Prints all books saved in the books list 
	 */
	void printBookListe();
	
	/**
	 * @param bookId : an integer value of book id which is already been saved in the books list
	 * @return if the book id is in the books list it return a book object of the given id else return null
	 */
	Book editBook(int bookId);
	
	/**
	 * 
	 * @return return integer of the total number of saved books
	 */
	int numberOfBooksInListe();
	
	//#######################################
	/**
	 * 
	 * @param author : a author object
	 * @return true if author is saved successfully and false if not
	 */
	boolean saveAuthor(Author author);
	
	/**
	 * 
	 * @param authorId : print a book information of a given book id number
	 */
	void printAuthor(int authorId);

	/**
	 * Prints all authors saved in the authors list 
	 */
	void printAuthorListe();
	

	/**
	 * @param authorId : an integer value of author id which is already been saved in the authors list
	 * @return if the author id is in the authors list it return an author object of the given id else return null
	 */
	Author editAuthor(int authorId);
	
	/**
	 * 
	 * @return return integer of the total number of saved authors
	 */
	int numberOfAuthorsInListe();
	

	
}
