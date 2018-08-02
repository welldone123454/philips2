package dataBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Author 
{

	private String authorName;
	private String nationality;
	private int birthYear,numberOfBooks,id;
	
	//HashSet to save the authorsBooklist
	private  HashMap<Integer,Book> authorsBookListe;

	
	
	
	//######################
	//constructor

	public Author(String authorName, String nationality, int birthYear, int numberOfBooks, Book book) {
		super();
		this.authorName = authorName;
		this.nationality = nationality;
		this.birthYear = birthYear;
		this.setNumberOfBooks(numberOfBooks);
		this.authorsBookListe = new HashMap<Integer,Book>();	
		addBookToAuthorList(book);

	}

	public Author(String authorName,Book book) {
		super();
		this.authorName = authorName;
		this.authorsBookListe = new HashMap<Integer,Book>();
		addBookToAuthorList(book);

	}
	
	
	//######################
	//other methods
	
	//to add a book to the author bookList
	public void addBookToAuthorList(Book book) 
	{	
		this.authorsBookListe.put(book.getId(),book);
		System.out.println("book: "+book.getBookName()+" added to author:"+this.authorName+" and list size: "+authorsBookListe.size());
			
	}
	
	
	//to print authorsBookList
	public void printauthorsBookListe() 
	{
		//Iterator mapIt = authorsBookListe.entrySet().iterator();
		Iterator<Entry<Integer, Book>> mapIt = authorsBookListe.entrySet().iterator();
		//System.out.println("List: ");
		System.out.println("Author: "+this.id+" [authorName=" + authorName +
							", nationality=" + nationality + ", birthYear=" + birthYear
							+ ", numberOfBooks=" + authorsBookListe.size()+ "]");
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
	}
	

	//######################
	//setter und getter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public int getBirthYear() {
		return birthYear;
	}
	
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public int getNumberOfBooks() 
	{
		return this.authorsBookListe.size();
	}


	@Override
	public String toString() {
		return "Author: "+this.id+"[authorName=" + authorName + ", nationality=" + nationality + ", birthYear=" + birthYear
				+ ", numberOfBooks=" + getNumberOfBooks()+ "]" ;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		return true;
	}

	public void setNumberOfBooks(int numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}


	
	
}
