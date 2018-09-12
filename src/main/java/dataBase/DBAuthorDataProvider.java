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

public class DBAuthorDataProvider implements IDataProviderAuthor
{
	
//#################################################
	//Variables :
	
	// to saves authors temporary locally in a list 
	private  HashMap<Integer,Author> authorsListe;
	
	//if sql command is executed or not
	private boolean executed= false;
	
	//to give an author an id 
	private static int authorId=1;
	
	//the connectionsDriver to be connected to database through it.
	private String connectionDriver;
	
	//the database address to be connected to.
	private String connectionAddress;
	
	//the database name
	//private String dataBaseName="book";
	private String dataBaseName;
	
	//the name of the books table
	//private String tableName="authorsListe";
	private String tableName;
	
	private static final Logger LOG = LoggerFactory.getLogger(DBAuthorDataProvider.class);
	
	
//#################################################
	//Constructors :	
		
		/**
		 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
		 *  and initialize a new AuthorList to save authors object..
		 * @param connectionDriver: the connectionsDriver to be connected to database through it.
		 *  						 example connectionDriver = "com.mysql.cj.jdbc.Driver";
		 *  @param connectionAddress: the database address to be connected to.
		 *  						  example connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC"
		 *  @param dataBaseName: the name of the dataBase to be created.
		 *  @param tableName: the name of the table in the dataBase to be created.
		 * @throws Exception: ClassNotFoundException thrown when "DB driver not found"while establishing the connection.
		 * @throws Exception: SQLException thrown when "DB connection couldn't be established,DB address might be wrong?"
		 */
		 public DBAuthorDataProvider(String connectionDriver,String connectionAddress,String dataBaseName,String tableName) 
		 throws ClassNotFoundException, SQLException{
		        this.connectionDriver=connectionDriver;
		        this.connectionAddress=connectionAddress;
			 	iniConnection(connectionDriver,connectionAddress);
			 	authorsListe = new HashMap<Integer,Author>();
			 	createDataBase(dataBaseName, tableName);
		 }

			/**
			 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
			 *  and initialize a new AuthorList to save authors object..
			 * @throws Exception: SQLException thrown when "DB connection couldn't be established,DB address might be wrong?"
			 */
			 public DBAuthorDataProvider()throws ClassNotFoundException, SQLException{
			        this.connectionDriver="com.mysql.cj.jdbc.Driver";
			        this.connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
				 	iniConnection(connectionDriver,connectionAddress);
				 	this.dataBaseName="book";
				 	this.tableName="authorsListe";
			        authorsListe = new HashMap<Integer,Author>();
				 	createDataBase(dataBaseName, tableName);
			 }

		 
//#################################################
	//Overridden methods :	
		 

	@Override
	public boolean saveAuthor(Author author) {
		
		boolean result=false;
		if(!authorsListe.containsValue(author) )
		{
			authorId=authorsListe.size()+1;
			author.setId(authorId);
			/*
			String command= String.format("insert into %s.%s (`id`,`authorName`,`nationality`,`birthYear`,`numberOfBooks`)VALUES("
											+ author.getId()+  ",\""+author.getAuthorName()+"\","+
											"\""+author.getNationality()+"\","+author.getBirthYear()+","+ author.getNumberOfAuthorsBooks()+");"
											,dataBaseName,authorsTable);
			*/
			
			result=applyCommandToSql(author,"insert");
			authorsListe.put(author.getId(), author);
					
			if(executed)
			{
				//System.out.println(" author: \""+author.getAuthorName()+ "\" saved to Sql!!");
				LOG.info("Author: \"{}\" saved to Sql!!",author.getAuthorName());
			}
			result=true;
		}
		else 
		{
			update(getAuthor(author));
			//System.out.println("Author: \""+author.getAuthorName()+"\" existes in database!!");
			//LOG.warn("Author: \"{}\" existes in database!!!!",author.getAuthorName());
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
	if(authorId>0 && authorsListe.containsKey(authorId)) 
	{
		return authorsListe.get(authorId);
	}
	/*
	else 
	{
		//System.out.println("Author id: "+authorId+" ist nicht auf dem bookListe");
		LOG.warn("Author id: \"{}\" is not available in the authorsList!!",authorId);
	}
	*/
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


/**
 * @param book : a book object 
 * @return if the book  is in the books list it return that book else return null
 */
@Override
public Author getAuthor(Author author) 
{
	if(!authorsListe.isEmpty() && author !=null) 
	{
		for(int i=1; i<=authorsListe.size(); i++) 
		{
			if(authorsListe.get(i).getAuthorName().equals(author.getAuthorName())) 
			{
				return authorsListe.get(i);
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
	
	//return authorsListe.get(author);
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
		public Connection iniConnection(String connectionDriver,String connectionAddress)
		{	 
			Connection connection=null;
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
				e.printStackTrace();
			}
			catch(SQLException e) 
			{
				//      abbruch.printStackTrace();
				//System.err.println("Caught SQLException: " + e.getMessage());
				//System.out.println("No Connection to Sql DataBase");
				//LOG.error("Caught SQLException: {} ", e.getMessage());
				//throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
				e.printStackTrace();
		    }
			//return null;
			return connection;
		}
			 
		/**
		 * Writes data to sql dataBase
		 * @param author : the author object to be saved to sql DB
		 * @param commandType : the type of command to be proceeded which are 2 commands "Insert" or "Update". 
		 * @return true: if command executed. and false: if not.
		 * 			  and change the variable "executed" to true if command executed and false if not
		 */
		private boolean applyCommandToSql(Author author,String commandType)
		{
			String command="";
			//int result=0;
			executed=false;
			if(commandType.equalsIgnoreCase("insert")) 
			{
				command=String.format("insert into %s.%s (`id`,`authorName`,`nationality`,`birthYear`,`numberOfBooks`)VALUES("
										+" %d , \"%s\" , \"%s\" , %d , %d );"
										,dataBaseName,tableName,author.getId(),author.getAuthorName(),author.getNationality(),
										author.getBirthYear(),author.getNumberOfAuthorsBooks());
				
				
			}
			else if(commandType.equalsIgnoreCase("update")) 
			{
				command=String.format("update %s.%s set nationality= \"%s\" , birthYear= %d , numberOfBooks= %d "
										+ "where authorName= \"%s\";",dataBaseName,tableName,author.getNationality(),
										author.getBirthYear(),author.getNumberOfAuthorsBooks(),author.getAuthorName());
			}
			
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
				//throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
			    
			}
			//System.out.println("applycommand"+result);
			//return result;	         
			return executed;
		}
		 	 
		/**
		 * Writes data to sql dataBase
		 * @param command : receives a string of sql command  
		 * @return 1: if command executed. and 0: if not.
		 * 			  and change the variable "executed" to true if command executed and false if not
		 */
		private int applyCommandToSql(String command)
		{
			//executed = false;
			int result=0;
			try
			{
				Connection connection = iniConnection( connectionDriver, connectionAddress);
				PreparedStatement preparedStatement = connection.prepareStatement(command);
				result=  preparedStatement.executeUpdate();
				//executed= true;
				executed = result!=0; 
			}
			catch(SQLException e)
			{
				//executed = false;
				//System.out.println("command not executed!!");
				//e.printStackTrace();
				LOG.error("command not executed!! \n Caught SQLException: {} ", e.getMessage());
				//throw new SQLException("DB connection couldn't be established,DB address might be wrong?");
			    
			}
			//System.out.println("applycommand"+result);
			return result;	           
		}
		 
	

		/**
		 * updates existing author in the database
		 * @param author : the author to be updated to database
		 * @return : true if update success
		 */
		private boolean update(Author author)
		{
			boolean result;
			result=applyCommandToSql(author,"update");
		 /*   if(result)
		    {
		    	//System.out.println("author: \""+author.getAuthorName()+"\"  update success!!"); 
		    	LOG.info("author: \"{}\"  update success!!",author.getAuthorName());
		    }
		   */ 
		    return result;
		}
		
		
		/**
		 * @param dataBaseName: the name of the dataBase to be created.
		 * @param tableName: the name of the table in the dataBase to be created.
		 * Creates dataBase "dataBaseName" and its table "tableName".
		 * "tableName" with fields :"id PRIMARY KEY, authorName, nationality, birthYear,numberOfBooks ".
		 * runs the method synchronizeAuthorsWithSql() to get data buffer from sql server
		 */
		private boolean createDataBase(String dataBaseName,String tableName) 
		{

			    String command,command2,command3;
				
			    command = String.format("CREATE DATABASE IF NOT EXISTS %s CHARACTER SET UTF8;",dataBaseName);
				command2 = String.format("use %s;",dataBaseName) ;
				command3 = String.format("CREATE TABLE if not EXISTS %s.%s (id int not null, PRIMARY KEY(id),authorName varchar(50),"
						+ " nationality varchar(50),birthYear int,numberOfBooks int);",dataBaseName,tableName);
				
	  
				applyCommandToSql(command);
				applyCommandToSql(command2);
				applyCommandToSql(command3);
				if(executed)
				{
					//System.out.println("DataBase creation success!!");
					LOG.info("DataBase creation success!!");
					
				} 
				synchronizeAuthorsWithSql( dataBaseName, tableName);
				return executed;
		}
		
		
		/**
		 * synchronizes authors saved in sql database with the local database saved as a list of authors object.
		 * and copys the missing authors from sql server to the authors list to be available  locally in the authors list.
		 */
		public void synchronizeAuthorsWithSql(String dataBaseName,String tableName) 
		{

			Author temp;
			Statement statement2;
			ResultSet ausgabe;
			try
			{
				String command = String.format("select * from %s.%s;",dataBaseName,tableName);
						
				Connection connection = iniConnection( connectionDriver, connectionAddress);
				statement2 = connection.createStatement();
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
			catch(SQLException abbruch)
			{
			executed = false;
			//System.out.println(abbruch.getMessage());
			LOG.error("SQLException : {}",abbruch.getMessage());
			}		       
			
		}



		
}
