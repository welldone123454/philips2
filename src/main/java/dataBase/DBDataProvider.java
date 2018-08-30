package dataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//public class DBDataProvider implements IDataProviderBook, IDataProviderAuthor
public class DBDataProvider  implements IDataProvidable
{
	
	/**
	 * Sql class : saves data to sql server
	 * 
	 */
	
//#################################################
//Variables :
	
	
	//to get the result from sql database
	private ResultSet ausgabe;
	
	//to form sql command
	private Statement statement;
	//private PreparedStatement preSteatement;

	// to saves books temporary locally in a list 
	private HashMap<Integer,Book> bookListe;
	
	// to saves authors temporary locally in a list 
	private  HashMap<Integer,Author> authorsListe;
	
	//if sql command is executed or not
	private boolean excuted= false;
	
	//if there's connection to sql sever or not
	private static boolean connected= false;
	
	//if data updated to sql sever or not
	private static boolean updated= false;
	
	
	//to give a book an id 
	private static int bookId=1;
	
	//to give an author an id 
	private static int authorId=1;
	    
	private Connection connection;
	
	private String connectionDriver;
	
	private String connectionAddress;
	
	//the database name
	private String dataBaseName="book";
	
	//the name of the books table
	private String booksTable="booksListe";
	
	//the name of the authors table
	private String authorsTable ="authorsListe";
	
	private static final Logger LOG = LoggerFactory.getLogger(DBDataProvider.class);
	
	
//#################################################
//Constructors :	
	
	/**
	 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
	 *  and initialize a new bookListe and a new authorsListe
	 */
	 public DBDataProvider()
	 {
		 super();
	        bookListe = new HashMap<Integer,Book>();
	        authorsListe= new HashMap<Integer,Author>();
	        createDataBase();
	 }
	 
	 /**
	  * 
	  * @param connectionDriver : the address of database to be connected
	  */
	 public DBDataProvider(String connectionAddress)
	 {
		 this.connectionAddress = connectionAddress;
		 bookListe = new HashMap<Integer,Book>();
	     authorsListe= new HashMap<Integer,Author>();
	     createDataBase();
	        
    
	 }
	   
//#################################################
//Overridden methods :	

 //###############################
 //Book's methods:
/**
 *if theres connection to sql server then 
 *first synchronizes sql data with local data.
 *saves the book if its not available in the sql data locally and to sql.
 *if the book we trying to save is already available in sql server then it only updates the data in sql server and locally with the new book data
 * 
 * and as book has also information of author, so the method calls saveAuthor() to save authors data to authorsList in sql,but if author is already
 * in the sql server then it only add the book to the authorsBookList
 * 
 * @param book : a Book object
 * @return true if book is saved successfully and false if not
 */
@Override
public boolean saveBook(Book book) 
{

	boolean result=false;
	if(connectToDataBase()!=null) 
	{	
		if(!bookListe.containsValue(book)) 
		{
			bookId=bookListe.size()+1;
			book.setId(bookId);		
			if(!authorsListe.containsValue(book.getAuthor())) 
			{
				saveAuthor(book.getAuthor());
			}
			else 
			{
				for(int i = 1; i<= authorsListe.size(); i++)    //search in the authorslist for the author of the book we wanted to save
				{
					if(authorsListe.get(i).getAuthorName().equals(book.getAuthor().getAuthorName())) 
					{
						authorsListe.get(i).addBookToAuthorList(book);	 //update the booknumber of the existing author
						book.setAuthor(authorsListe.get(i));			//give the value of the existing author to the booksauthor we wanted to save
						update(book.getAuthor());
					}
				}
			}
				
			
			String command= String.format("insert into %s.%s (`id`,`bookName`,`authorName`,`authorId`,`category`,`releaseYear`,"
											+ "`Preis`,`discount`,`discountAmount`,`description`)VALUES("
											+ book.getId()+  ",\""+book.getBookName()+"\","+
											"\""+book.getAuthorName()+"\","+book.getAuthor().getId()+",\""+ book.getCategory()+"\","+ book.getReleaseYear()
											+","+book.getPreis()+",\""+ book.isDiscount()+"\","+book.getDiscountAmount()+",\""+
											book.getDescription()+"\");"	,dataBaseName,booksTable);
	
			applyCommandToSql(command);
			

			bookListe.put(bookId, book);

			if(excuted) 
			{
				//System.out.println(" book: \""+book.getBookName()+ "\" saved to Sql!!");
				LOG.info(" book: \"{}\" saved to Sql!!",book.getBookName());
			}
			
	
		}
		else 
		{
			update(book);
		}		
		result=true;

				
	}
	else 
	{
		//System.out.println("No connection to Sql!!");
		LOG.error("No connection to Sql!!");
	}			

	return result;

}


/**
 * 
 * @param bookId : print a book information of a given book id number
 */
@Override
public void printBook(int bookId) 
{
	if(bookListe.containsKey(bookId)) 
	{
		System.out.println(bookId+"="+bookListe.get(bookId));
	}
	else 
	{
		//System.out.println("Book id: "+bookId+" ist nicht auf dem bookListe");
		LOG.warn("Book id: \"{}\" is not available in the bookList",bookId);
	}			
}


/**
 * Prints all books saved in the books list 
 */
@Override
public void printBookListe() 
{
	/*
	for(int i=1; i<=bookListe.size();i++) 
	{
		System.out.println(bookListe.get(i).getBookName()+bookListe.get(i).getAuthorName());
	}
	*/
	if(bookListe.isEmpty()) 
	{
		System.out.println("Book list is empty !!");
		LOG.warn("Book list is empty !!");
	}
	else 
	{
		Iterator<Entry<Integer, Book>> mapIt = bookListe.entrySet().iterator();
		System.out.println("number of Books in the Liste: "+bookListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
	}
	
	
	
}

		
		

/**
 * @param bookId : an integer value of book id which is already been saved in the books list
 * @return if the book id is in the books list it return a book object of the given id else return null
 */
@Override
public Book getBook(int bookId) 
{
	if(bookId>0 && bookListe.containsKey(bookId)) 
	{
		return bookListe.get(bookId);
	}
	/*
	for(Book bookTemp : bookListe.values()) 
	{
		if(bookTemp.getId() == bookId) 
		{
			return bookTemp;
		}
		else 
		{
			//System.out.println("Book id: "+bookId+" ist nicht auf dem bookListe");
			LOG.warn("Book id: \"{}\" is not available in the bookList",bookId);
		}
		
	}
	*/
	return null;
}

/**
 * @param book : a book object 
 * @return if the book  is in the books list it return that book else return null
 */
public Book getBook(Book book) 
{
	for(Book bookTemp : bookListe.values()) 
	{
		if(bookTemp.equals(book)) 
		{
			return bookTemp;
		}
		else 
		{
			//System.out.println("Book id: "+bookId+" ist nicht auf dem bookListe");
			LOG.warn("Book id: \"{}\" is not available in the bookList",bookId);
		}
		
	}
	
	return null;
}
	   
		
/**
 * @return an integer number of the total books of an author
 */
@Override
public int numberOfBooksInListe() 
{
	return bookListe.size();
}

 //###############################
 //Authors's methods:	 
		
		
/**
 * 
 * if theres connection to sql server then 
 *first synchronizes sql data with local data.
 *saves the author if its not available in the sql data locally and to sql.
 *if the author we trying to save is already available in sql server then it only updates the data in sql server and locally with the new author data
 *
 * @param author : a author object
 * @return true if author is saved successfully and false if not
 */
@Override
public boolean saveAuthor(Author author) 
{
	boolean result=false;
	if(connectToDataBase()!=null) 
	{

		if(!authorsListe.containsValue(author) )
		{
			authorId=authorsListe.size()+1;
			author.setId(authorId);
			
			String command= String.format("insert into %s.%s (`id`,`authorName`,`nationality`,`birthYear`,`numberOfBooks`)VALUES("
											+ author.getId()+  ",\""+author.getAuthorName()+"\","+
											"\""+author.getNationality()+"\","+author.getBirthYear()+","+ author.getNumberOfAuthorsBooks()+");"
											,dataBaseName,authorsTable);

			applyCommandToSql(command);
			authorsListe.put(author.getId(), author);
					
			if(excuted)
			{
				//System.out.println(" author: \""+author.getAuthorName()+ "\" saved to Sql!!");
				LOG.info("Author: \"{}\" saved to Sql!!",author.getAuthorName());
			}
			result=true;
		}
		else 
		{
			update(author);
			//System.out.println("Author: \""+author.getAuthorName()+"\" existes in database!!");
			//LOG.warn("Author: \"{}\" existes in database!!!!",author.getAuthorName());
		}			
	}
	else 
	{
		//System.out.println("No connection to Sql!!");
		LOG.warn("No connection to Sql!!");
	}
	return result;	
}


		
/**
 * 
 * @param authorId : print a book information of a given book id number
 */
@Override
public void printAuthor(int authorId) 
{
	if(authorsListe.containsKey(authorId)) 
	{
		System.out.println(authorId+"="+authorsListe.get(authorId));
	}
	else 
	{
		//System.out.println("Author id: "+authorId+" ist nicht auf dem authorsListe");
		LOG.warn("Author id: \"{}\" is not available in the authorsList!!",authorId);
	}
}


		
/**
 * Prints all authors saved in the authors list 
 */	
@Override
public void printAuthorListe() 
{
	if(authorsListe.isEmpty()) 
	{
		System.out.println("AuthorList is empty !!");
		LOG.warn("AuthorsList is empty !!");
	}
	else 
	{
		Iterator<Entry<Integer, Author>> mapIt = authorsListe.entrySet().iterator();
		System.out.println("number of Authors in the Liste: "+authorsListe.size());
		while(mapIt.hasNext()) 
		{
			System.out.println(mapIt.next());
		}
	}
}


		
/**
 * @param authorId : an integer value of author id which is already been saved in the authors list
 * @return if the author id is in the authors list it return an author object of the given id else return null
 */
@Override
public Author getAuthor(int authorId) 
{
	if(authorsListe.containsKey(authorId)) 
	{
		return authorsListe.get(authorId);
	}
	else 
	{
		//System.out.println("Author id: "+authorId+" ist nicht auf dem bookListe");
		LOG.warn("Author id: \"{}\" is not available in the authorsList!!",authorId);
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
	return authorsListe.size();
}

				
//#################################################
//Other methods :	

/**
 * synchronizes books saved in sql database with the local database saved as a list of books object.
 * and copys the missing books from sql server to the book list to be available  locally in the book list.
 */
public void synchronizeBooksWithSql() 
{
	Book temp;
	Statement statement2;
	try
	{
		String command = String.format("select * from %s.%s;",dataBaseName,booksTable);
		//String befehl = "select * from book.booksListe where id= '?*' ";
		//preSteatement.setString(1, UserEingabe);
		Connection connection = connectToDataBase();
		if(connection!=null) 
		{
			statement2 = connection.createStatement();
			
			ausgabe = statement2.executeQuery(command);
			
				while(ausgabe.next())
				{
					bookId++;	 
					//if book not available locally then save it to the local booksList
					if(!bookListe.containsKey(ausgabe.getInt("id")) )
						{
							temp = new Book(ausgabe.getString("bookName"),ausgabe.getString("authorName"),
											ausgabe.getString("category"), ausgabe.getInt("releaseYear"),ausgabe.getDouble("Preis"),
											ausgabe.getString("discount"),ausgabe.getInt("discountAmount"),ausgabe.getString("Description"));
								
							bookListe.put(ausgabe.getInt("id"), temp);
						}
						
					}
						
				}	

	}
	catch(SQLException abbruch)
	{
	excuted = false;
	//System.out.println(abbruch.getMessage());
	LOG.error("SQLException : {}",abbruch.getMessage());
	}		       
	
}


/**
 * synchronizes authors saved in sql database with the local database saved as a list of authors object.
 * and copys the missing authors from sql server to the authors list to be available  locally in the authors list.
 */
public void synchronizeAuthorsWithSql() 
{

	Author temp;
	Statement statement2;
	try
	{
		String command = String.format("select * from %s.%s;",dataBaseName,authorsTable);
				
		Connection verbindung = connectToDataBase();
		if(connection!=null) 
		{
			statement2 = verbindung.createStatement();
			ausgabe = statement2.executeQuery(command);
			
			
				while(ausgabe.next())
				{
					authorId++;
						//String authorName, String nationality, int birthYear, int numberOfBooks, Book book
						//if author not available locally then save it to the local authorsList
						if(!authorsListe.containsKey(ausgabe.getInt("id")) )
						{
							temp = new Author(ausgabe.getString("authorName"),ausgabe.getString("nationality"),
											ausgabe.getInt("birthYear"), ausgabe.getInt("numberOfBooks"));
							//	System.out.println("Synchronize author "+ausgabe.getInt("numberOfBooks") );
							authorsListe.put(ausgabe.getInt("id"), temp);
						}	
					}

				}	
			
	}
	catch(SQLException abbruch)
	{
	excuted = false;
	//System.out.println(abbruch.getMessage());
	LOG.error("SQLException : {}",abbruch.getMessage());
	}		       
	
}




/**
 * updates existing book in the database
 * @param book : the book to be updated to database
 * @return : true if update success
 */
public boolean update(Book book) 
{
	boolean result= false;
	String updateCommand=String.format("update %s.%s set authorName=\""+book.getAuthorName()+"\","
								+ "authorId="+book.getAuthor().getId()+",category= \""+book.getCategory()+"\","
								+ "releaseYear="+book.getReleaseYear()+",Preis="+book.getPreis()+","
								+ "discount=\""+book.isDiscount()+"\",discountAmount ="+book.getDiscountAmount()
								+ ",description=\""+book.getDescription()+"\" "
								+ "where bookName=\""+book.getBookName()+"\";"   
								,dataBaseName,booksTable);

	applyCommandToSql(updateCommand);
    if(excuted)
    {
    	//System.out.println("book: \""+book.getBookName()+"\"  update success!!"); 
    	LOG.info("book: \"{}\"  update success!!",book.getBookName());
    	result=true;
    }
    return result;
}

/**
 * updates existing author in the database
 * @param author : the author to be updated to database
 * @return : true if update success
 */
public boolean update(Author author) 
{
	boolean result= false;
	
	String updateCommand=String.format("update %s.%s set nationality=\""+author.getNationality()+"\","
								+ "birthYear="+author.getBirthYear()+",numberOfBooks="+author.getNumberOfAuthorsBooks()
								+" where authorName=\" "+author.getAuthorName()+"\";"
								,dataBaseName,authorsTable);
	applyCommandToSql(updateCommand);
    if(excuted)
    {
    	//System.out.println("author: \""+author.getAuthorName()+"\" update success!!"); 
    	LOG.info("author: \"{}\"  update success!!",author.getAuthorName());
    	result=true;
    }
    return result;
}

		    
	    
/**
 * Creates dataBase "book" and its tables "booksListe, authorsListe" if sql server is connected
 * booksListe with fields : "id PRIMARY KEY,bookName, authorName, authorId, category, releaseYear, Preis,
 * discount, discountAmount,description".
 * 
 * authorsListe with fields : "id PRIMARY KEY, authorName, nationality, birthYear,numberOfBooks ".
 */
public void createDataBase() 
{
	if(connectToDataBase()!=null) 
	{
		
	    String command,command2,command3,command4;
		
	    command = String.format("CREATE DATABASE IF NOT EXISTS %s CHARACTER SET UTF8;",dataBaseName);
		command2 = String.format("use %s;",dataBaseName) ;
		command3 = String.format("CREATE TABLE if not EXISTS %s.%s(id int PRIMARY KEY,bookName varchar(50), authorName varchar(50),"+
		                            "authorId int,"+"category varchar(50) , releaseYear int,"+"Preis double,discount varchar(5),"+
		                            "discountAmount int,description varchar(255) );", dataBaseName,booksTable);
		        
		command4=String.format("CREATE TABLE if not EXISTS %s.%s (id int not null, PRIMARY KEY(id),authorName varchar(50),"
								+ " nationality varchar(50),birthYear int,numberOfBooks int);",dataBaseName,authorsTable);
	         
		applyCommandToSql(command);
		applyCommandToSql(command2);
		applyCommandToSql(command3);
		applyCommandToSql(command2);
		applyCommandToSql(command4);
		
		
        synchronizeBooksWithSql();
        synchronizeAuthorsWithSql();
		if(excuted)
		{
			//System.out.println("DataBase creation success!!");
			LOG.info("DataBase creation success!!");
		}       
	}     
		        
}
		    
/**
 * checks if there's connection to sql server then open the connection to the dataBase
 * @return return an open database connection if sql server is connected else return null
 */
public Connection connectToDataBase()
{
	
    try
	{
    	connectionDriver = "com.mysql.cj.jdbc.Driver";
		//  driver = "com.mysql.jdbc.Driver";
		connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
		//Aufruf des Treibers innerhalb der Klasse
		Class.forName(connectionDriver);
		//Instanz einer Verbinfung über den Treibermanager und Funktion getConnection() mit Vorgabe des DB-Pfades
		connection = DriverManager.getConnection(connectionAddress,"root","");
		connected=true;
		return connection;            
	}
	catch(ClassNotFoundException e) 
	{
		//     abbruch.printStackTrace();
		//System.err.println("Caught ClassNotFoundException: " + e.getMessage());
		//System.out.println("No Connection to Sql DataBase");
		LOG.error("Caught ClassNotFoundException: {} ", e.getMessage());
		connected=false;
	}
	catch(SQLException e) 
	{
		//      abbruch.printStackTrace();
		//System.err.println("Caught SQLException: " + e.getMessage());
		//System.out.println("No Connection to Sql DataBase");
		LOG.error("Caught SQLException: {} ", e.getMessage());
		connected=false;
    }
	return null;
}
		    

/**
 * Writes data to sql dataBase
 * @param command : receives a string of sql command  
 * @return 1: if command executed. and 0: if not.
 * and change the variable "executed" to true if command executed and false if not
 */
public int applyCommandToSql(String command)
{
	excuted = false;
	int result=0;
	try
	{
		Connection connection = connectToDataBase();
		PreparedStatement preparedStatement = connection.prepareStatement(command);
		result=  preparedStatement.executeUpdate();
		excuted = true; 
	}
	catch(SQLException e)
	{
		excuted = false;
		//System.out.println("command not executed!!");
		//e.printStackTrace();
		LOG.error("command not executed!! \n Caught SQLException: {} ", e.getMessage());
	}
	System.out.println("applycommand"+result);
	return result;	           
}



public static boolean isConnected() 
{
	return connected;
}


public static void setConnected(boolean connected) 
{
	DBDataProvider.connected = connected;
}

 


	
		
	
	
}
