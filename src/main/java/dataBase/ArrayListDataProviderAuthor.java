package dataBase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayListDataProviderAuthor implements IDataProviderAuthor 
{

	
	//to save authors in a list locally
	private static HashMap<Integer,Author> authorList;
	
	//to give an author an id
	private static int authorId;
	
	private static final Logger LOG = LoggerFactory.getLogger(ArrayListDataProviderAuthor.class);
	
//############################################################################
	//Constructors:
	
	/**
	 * Creates a new object and initialize a bookListe to save books and an authorListe to save authors
	 */
	public ArrayListDataProviderAuthor() 
	{
		 authorList = new  HashMap<Integer,Author>();
	}

//############################################################################
	//Overridden methods:
	
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
	

	@Override
	public Author getAuthor(Author author)
	{
		if(!authorList.isEmpty() && author !=null) 
		{
			for(int i=1; i<=authorList.size(); i++) 
			{
				if(authorList.get(i).getAuthorName().equals(author.getAuthorName())) 
				{
					return authorList.get(i);
				}
				/*
				else 
				{
					LOG.warn("author Name: \"{}\" is not available in the authorList",author.getAuthorName());
				}
				*/
			}
		}
		
		return null;
		
	}
	

}
