package dataBase;

public interface IDataProviderBook 
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
	Book getBook(int bookId);
	
	/**
	 * 
	 * @return return integer of the total number of saved books
	 */
	int numberOfBooksInListe();
	
	/**
	 * @param book : a book object 
	 * @return if the book  is in the books list it return that book else return null
	 */
	Book getBook(Book book);
	

}
