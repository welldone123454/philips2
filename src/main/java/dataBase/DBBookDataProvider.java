package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBBookDataProvider implements IDataProviderBook 
{

	/**
	 * Sql class : saves data to sql server
	 * 
	 */
	
//#################################################
//Variables :
	
	
	// to saves books temporary locally in a list 
	private HashMap<Integer,Book> bookListe;
	
	//if sql command is executed or not
	private boolean executed= false;
	

	//to give a book an id 
	private static int bookId=1;
	
	//the connectionsDriver to be connected to database through it.
	private String connectionDriver;
	
	//the database address to be connected to.
	private String connectionAddress;
	
	//the database name
	//private String dataBaseName="book";
	private String dataBaseName;
	
	//the name of the books table
	//private String tableName="booksListe";
	private String tableName;
	
	private DBAuthorDataProvider dbAuthorDataProvider;
	
	private static final Logger LOG = LoggerFactory.getLogger(DBBookDataProvider.class);
	
	
	//#################################################
	//Constructors :	
	

		/**
		 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
		 *  and initialize a new bookList..
		 *  @param connectionDriver: the connectionsDriver to be connected to database through it.
		 *  						 example connectionDriver = "com.mysql.cj.jdbc.Driver";
		 *  @param connectionAddress: the database address to be connected to.
		 *  						  example connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC"
		 *  @param dataBaseName: the name of the dataBase to be created.
		 *  @param tableName: the name of the table in the dataBase to be created.
		 * @throws Exception: Exception throw exception error while establishing the connection.
		 */
		 public DBBookDataProvider(String connectionDriver,String connectionAddress,String dataBaseName,String tableName) throws Exception
		 {
		        this.connectionDriver=connectionDriver;
		        this.connectionAddress=connectionAddress;
			 	iniConnection(connectionDriver,connectionAddress);
		        bookListe = new HashMap<Integer,Book>();
			 	createDataBase(dataBaseName, tableName);
			 	dbAuthorDataProvider = new DBAuthorDataProvider( connectionDriver,connectionAddress, dataBaseName, "authorsListe");
		 }
		 
		 
			/**
			 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
			 *  and initialize a new bookList..
			 */
			 public DBBookDataProvider() throws Exception
			 {
			        //this.connectionDriver="com.mysql.cj.jdbc.Driver";
				 	this.connectionDriver="com.mysql.cj.jdbc.Driver";
			        this.connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
				 	iniConnection(connectionDriver,connectionAddress);
				 	this.dataBaseName="book";
				 	this.tableName="booksListe";
			        bookListe = new HashMap<Integer,Book>();
				 	createDataBase(dataBaseName, tableName);
				 	dbAuthorDataProvider = new DBAuthorDataProvider();
			 }
			 
	
//#################################################
	//Overridden methods :	

	/**
	 *saves the book if its not available in the sql data locally and to sql.
	 *if the book we trying to save is already available in sql server then it
	 * only updates the data in sql server and locally with the new book data
	 * @param book : a Book object
	 * @return true if book is saved successfully and false if not
	 * @throws Exception  throw exception error while establishing the connection.
	 */
	@Override
	public boolean saveBook(Book book)
	{
		boolean result=false;
		dbAuthorDataProvider.saveAuthor(book.getAuthor());
		if(!bookListe.containsValue(book)) 
		{
			bookId=bookListe.size()+1;
			book.setId(bookId);	
			try 
			{
				result=applyCommandToSql(book,"insert");			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			bookListe.put(bookId, book);
			//System.out.println(" book: \""+book.getBookName()+ "\" saved to Sql!!");
			LOG.info(" book: \"{}\" saved to Sql!!",book.getBookName());
		}
		else 
		{
			try {
				update(getBook(book));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	

		return result;
	}
	

/**
 * 
 * @param bookId : print a book information of a given book id number
 */
	@Override
	public void printBook(int bookId) {
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
	public void printBookListe() {
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
	public Book getBook(int bookId) {
		if(bookId>0 && bookListe.containsKey(bookId)) 
		{
			return bookListe.get(bookId);
		}
		return null;
	}
	
	/**
	 * @param book : a book object 
	 * @return if the book  is in the books list it return that book else return null
	 */
	@Override
	public Book getBook(Book book) 
	{
		for(int i=1; i<=bookListe.size(); i++) 
		{
			if(bookListe.get(i).getBookName().equals(book.getBookName())) 
			{
				return bookListe.get(i);
			}
			/*
			else 
			{
				LOG.warn("book Name: \"{}\" is not available in the bookList",book.getBookName());
			}
			*/
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
	
	
//#################################################
	//Other methods :	
	
	 
	/**
	* checks if there's connection to sql server then open the connection to the dataBase
	* @return return an open database connection if sql server is connected else return null
	* @throws Exception throw exception error while establishing the connection
	* @param connectionDriver: the connectionsDriver to be connected to database through it
	* @param connectionAddress: the database address to be connected to.
	*/
	public Connection iniConnection(String connectionDriver,String connectionAddress)throws Exception
	{	 
		Connection connection;
		try
		{
			
	    //	connectionDriver = "com.mysql.cj.jdbc.Driver";
			//  driver = "com.mysql.jdbc.Driver";
		//	connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
			//Aufruf des Treibers innerhalb der Klasse
			Class.forName(connectionDriver);
			//Instanz einer Verbinfung über den Treibermanager und Funktion getConnection() mit Vorgabe des DB-Pfades
			connection = DriverManager.getConnection(connectionAddress,"root","");
			//return connection;            
		}
		catch(ClassNotFoundException e) 
		{
			//     abbruch.printStackTrace();
			//System.err.println("Caught ClassNotFoundException: " + e.getMessage());
			//System.out.println("No Connection to Sql DataBase");
			//LOG.error("Caught ClassNotFoundException: {} ", e.getMessage());
			throw new ClassNotFoundException("DB driver not found");
		}
		catch(SQLException e) 
		{
			//      abbruch.printStackTrace();
			//System.err.println("Caught SQLException: " + e.getMessage());
			//System.out.println("No Connection to Sql DataBase");
			//LOG.error("Caught SQLException: {} ", e.getMessage());
			throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
	    }
		//return null;
		return connection;
	}
	
	
	/**
	* checks if there's connection to sql server then open the connection to the dataBase
	* @return return an open database connection if sql server is connected else return null
	*/
	public Connection iniConnection()
	{	 
		Connection connection = null;
		try
		{
			
	    //	connectionDriver = "com.mysql.cj.jdbc.Driver";
			//  driver = "com.mysql.jdbc.Driver";
		//	connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
			//Aufruf des Treibers innerhalb der Klasse
			Class.forName(connectionDriver);
			//Instanz einer Verbinfung über den Treibermanager und Funktion getConnection() mit Vorgabe des DB-Pfades
			connection = DriverManager.getConnection(connectionAddress,"root","");
			//return connection;            
		}
		catch(ClassNotFoundException e) 
		{
			//     abbruch.printStackTrace();
			//System.err.println("Caught ClassNotFoundException: " + e.getMessage());
			//System.out.println("No Connection to Sql DataBase");
			LOG.error("Caught ClassNotFoundException: {} ", e.getMessage());
			//throw new ClassNotFoundException("DB driver not found");
		}
		catch(SQLException e) 
		{
			//      abbruch.printStackTrace();
			//System.err.println("Caught SQLException: " + e.getMessage());
			//System.out.println("No Connection to Sql DataBase");
			LOG.error("Caught SQLException: {} ", e.getMessage());
			//throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
	    }
		//return null;
		return connection;
	}
		 
		 
	/**
	 * Writes data to sql dataBase
	 * @param book : the book object to be saved to sql DB
	 * @param commandType : the type of command to be proceeded which are 2 commands "Insert" or "Update". 
	 * @return true: if command executed. and false: if not.
	 * 			  and change the variable "executed" to true if command executed and false if not
	 * @throws Exception throw exception error while establishing the connection.
	 */
	private boolean applyCommandToSql(Book book,String commandType) throws Exception
	{
		String command="";
		//int result=0;
		executed = false;
		if(commandType.equalsIgnoreCase("insert")) 
		{
			command=String.format("insert into %s.%s (`id`,`bookName`,`authorName`,`authorId`,`category`,`releaseYear`,"
									+ "`Preis`,`discount`,`discountAmount`,`description`)VALUES( %d , \"%s\" , \"%s\" , %s , \"%s\" ,"
									+ " %d , %s , \"%s\" , %d , \"%s\" );",dataBaseName,tableName,book.getId(),book.getBookName(),
									book.getAuthorName(),book.getAuthor().getId(),book.getCategory(),book.getReleaseYear(),book.getPreis(),book.isDiscount(),
									book.getDiscountAmount(),book.getDescription());
			
			
		}
		else if(commandType.equalsIgnoreCase("update")) 
		{
			command=String.format("update %s.%s set authorName= \"%s\" , category= \"%s\" , releaseYear= %d , Preis= %s ,"
									+ "discount= \"%s\" , discountAmount = %d , description=\"%s\" where bookName= \"%s\" ;"   
									,dataBaseName,tableName,book.getAuthorName(),book.getCategory(),book.getReleaseYear(),book.getPreis(),
									book.isDiscount(),book.getDiscountAmount(),book.getDescription(),book.getBookName());
		}
		
		
		
		try
		{
			Connection connection = iniConnection( connectionDriver, connectionAddress);
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.executeUpdate();
			executed= true;
			//executed = result!=0; 
			
		}
		catch(SQLException e)
		{
			executed = false;
			//System.out.println("command not executed!!");
			//e.printStackTrace();
			LOG.error("command not executed!! \n Caught SQLException: {} ", e.getMessage());
			throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
		    
		}
		//System.out.println("applycommand"+result);
		//return result;	
		//System.out.println();
		return executed;		           
	}
	 
	
	/**
	 * Writes data to sql dataBase
	 * @param command : receives a string of sql command  
	 * @return true: if command executed. and false: if not.
	 * 			  and change the variable "executed" to true if command executed and false if not
	 * @throws Exception throw exception error while establishing the connection.
	 */
	private boolean applyCommandToSql(String command) throws Exception
	{
		executed = false;
		///int result=0;
		try
		{
			Connection connection = iniConnection( connectionDriver, connectionAddress);
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			//result=  preparedStatement.executeUpdate();
			preparedStatement.executeUpdate();
			executed= true;
			//executed = result!=0; 
		}
		catch(SQLException e)
		{
			executed = false;
			//System.out.println("command not executed!!");
			//e.printStackTrace();
			LOG.error("command not executed!! \n Caught SQLException: {} ", e.getMessage());
			throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
		    
		}
		//System.out.println("applycommand"+result);
		//return result;	
		return executed;	
	}
	 
	
	/**
	 * updates existing book in the database
	 * @param book : the book to be updated to database
	 * @return : true if update success
	 * @throws Exception throw exception error while establishing the connection.
	 */
	private boolean update(Book book) throws Exception 
	{
		boolean result;
		/*
		String updateCommand=String.format("update %s.%s set authorName=\""+book.getAuthorName()+"\","
									+ "authorId="+book.getAuthor().getId()+",category= \""+book.getCategory()+"\","
									+ "releaseYear="+book.getReleaseYear()+",Preis="+book.getPreis()+","
									+ "discount=\""+book.isDiscount()+"\",discountAmount ="+book.getDiscountAmount()
									+ ",description=\""+book.getDescription()+"\" "
									+ "where bookName=\""+book.getBookName()+"\";"   
									,dataBaseName,tableName);
									
		*/
		result=applyCommandToSql(book,"update");
	/*    if(result)
	    {
	    	//System.out.println("book: \""+book.getBookName()+"\"  update success!!"); 
	    	LOG.info("book: \"{}\"  update success!!",book.getBookName());
	    }
	    
	   */ 
	    return result;
	}
	
	
	/**
	 * @param dataBaseName: the name of the dataBase to be created.
	 * @param tableName: the name of the table in the dataBase to be created.
	 * Creates dataBase "dataBaseName" and its table "tableName".
	 * "tableName" with fields : "id PRIMARY KEY,bookName, authorName, authorId, category, releaseYear, Preis,
	 * discount, discountAmount,description".
	 * runs the method synchronizeBooksWithSql() to get data buffer from sql server
	 * @throws Exception throw exception error while establishing the connection.
	 */
	private boolean createDataBase(String dataBaseName,String tableName) throws Exception 
	{

		    String command,command2,command3;
			
		    command = String.format("CREATE DATABASE IF NOT EXISTS %s CHARACTER SET UTF8;",dataBaseName);
			command2 = String.format("use %s;",dataBaseName) ;
			command3 = String.format("CREATE TABLE if not EXISTS %s.%s(id int PRIMARY KEY,bookName varchar(50), authorName varchar(50),"+
			                            "authorId int,"+"category varchar(50) , releaseYear int,"+"Preis double,discount varchar(5),"+
			                            "discountAmount int,description varchar(255) );", dataBaseName,tableName);
			        
  
			applyCommandToSql(command);
			applyCommandToSql(command2);
			applyCommandToSql(command3);
			if(executed)
			{
				//System.out.println("DataBase creation success!!");
				LOG.info("DataBase creation success!!");
				
			} 
			synchronizeBooksWithSql( dataBaseName, tableName);
			return executed;
	}

	
/**
 * @param dataBaseName: the name of the dataBase which been created.
 * @param tableName: the name of the table which been created.
 * synchronizes books saved in sql database with the local database saved as a list of books object.
 * and copys the missing books from sql server to the book list to be available  locally in the book list.
 * @throws Exception  throw exception error while establishing the connection.
 */
private void synchronizeBooksWithSql(String dataBaseName,String tableName) throws Exception 
{
	Book temp;
	Statement statement2;
	//to get the result from sql database
	ResultSet ausgabe;
	try
	{
		String command = String.format("select * from %s.%s;",dataBaseName,tableName);
		//String befehl = "select * from book.booksListe where id= '?*' ";
		//preSteatement.setString(1, UserEingabe);
		Connection connection = iniConnection( connectionDriver, connectionAddress);
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
	catch(SQLException abbruch)
	{
	executed = false;
	//System.out.println(abbruch.getMessage());
	//LOG.error("SQLException : {}",abbruch.getMessage());
	throw new SQLException("SQLException : {}",abbruch.getMessage());
	}		       
	
}

	
	

}
