package dataBase;

public abstract class IDataProviderBook implements IDataProvidable
{
	/**
	 * 
	 * @param book : a Book object
	 * @return true if book is saved successfully and false if not
	 */
	public abstract boolean saveBook(Book book);
	
	/**
	 * 
	 * @param bookId : print a book information of a given book id number
	 */
	public abstract void printBook(int bookId);

	/**
	 * Prints all books saved in the books list 
	 */
	public abstract void printBookListe();
	
	/**
	 * @param bookId : an integer value of book id which is already been saved in the books list
	 * @return if the book id is in the books list it return a book object of the given id else return null
	 */
	public abstract Book getBook(int bookId);
	
	/**
	 * 
	 * @return return integer of the total number of saved books
	 */
	public abstract int numberOfBooksInListe();
	

}
