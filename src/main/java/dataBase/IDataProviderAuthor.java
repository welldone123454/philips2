package dataBase;

public interface IDataProviderAuthor 
{
	/**
	 * 
	 * @param author : a author object
	 * @return true if author is saved successfully and false if not
	 */
	boolean saveAuthor(Author author);
	
	/**
	 * 
	 * @param authorId : print a author information of a given author id number
	 */
	void printAuthor(int authorId);

	/**
	 * 
	 * @param author : print an Author information of a given author object
	 */
	void printAuthor(Author author);

	/**
	 * Prints all authors saved in the authors list 
	 */
	void printAuthorListe();
	

	/**
	 * @param authorId : an integer value of author id which is already been saved in the authors list
	 * @return if the author id is in the authors list it return an author object of the given id else return null
	 */
	Author getAuthor(int authorId);
	
	/**
	 * 
	 * @return return integer of the total number of saved authors
	 */
	int numberOfAuthorsInListe();
	
	/**
	 * @param author : a author object 
	 * @return if the author  is in the authors list it return that author else return null
	 */
	Author getAuthor(Author author);

}
