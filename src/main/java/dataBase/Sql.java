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


public class Sql implements IDataProvider
{
	
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
	private HashMap<Integer,Author> authorsListe;
	
	//if sql command is executed or not
	private boolean excuted= false;
	
	//if there's connection to sql sever or not
	private static boolean connected= false;
	
	//to give a book an id 
	private static int bookId=1;
	
	//to give an author an id 
	private static int authorId=1;
	    
	
//#################################################
//Constructors :	
	
	/**
	 * Creates a new object, checks if there's connection with sql server then creates database and tables in the server 
	 *  and initialize a new bookListe and a new authorsListe
	 */
	 public Sql()
	 {
	    	verbind();
	        createDataBase();
	        bookListe = new HashMap<Integer,Book>();
	        authorsListe= new HashMap<Integer,Author>();
    
	 }
	   
//#################################################
//Overridden methods :	

	 //###############################
	 //Book's methods:
		/**
		 * 
		 * @param book : a Book object
		 * @return true if book is saved successfully and false if not
		 */
		//@Override
	public boolean saveBook(Book book) 
	{
			boolean b=false;
			if(connected) 
			{
				
				getBookFromSql();
				getAuthorFromSql();
				
				if(!bookListe.containsValue(book) )
				{
					bookId=bookListe.size()+1;
					book.setId(bookId);
					
					String befehl="insert into book.booksListe (`id`,`bookName`,`authorName`,`authorId`,`category`,`releaseYear`,"
						+ "`Preis`,`discount`,`discountAmount`,`description`)VALUES("
						+ book.getId()+  ",\""+book.getBookName()+"\","+
						"\""+book.getAuthorName()+"\","+book.getAuthor().getId()+",\""+ book.getCategory()+"\","+ book.getReleaseYear()
								+","+book.getPreis()+",\""+ book.isDiscount()+"\","+book.getDiscountAmount()+",\""+
								book.getDescription()+"\");";
		

					erstelleDatensatz(befehl);
					
					//saveAuthor(book.getAuthor());
					if(!saveAuthor(book.getAuthor()))
					{			
							for(int i = 1; i<= authorsListe.size(); i++) 
							{
								if(authorsListe.get(i).getAuthorName().equals(book.getAuthor().getAuthorName())) 
								{
								authorsListe.get(i).addBookToAuthorList(book);		
								}
							}

								
					}
					
					System.out.println("savebook size "+bookListe.size()+" bookid "+book.getId());
					bookListe.put(book.getId(), book);
					


					if(excuted) {System.out.println(" book saved to Sql!!");}
					b=true;

				}
				else 
				{
					
					System.out.println("book:\" "+book.getBookName()+" \" .existes in sql!!");
				}
				
				
				
			}
			else 
			{
				System.out.println("No connection to Sql!!");
			}
			
			return b;

	}




		/**
		 * 
		 * @param bookId : print a book information of a given book id number
		 */
		//@Override
	public void printBook(int bookId) 
		{
			
			if(bookListe.containsKey(bookId)) 
			{
				System.out.println(bookId+"="+bookListe.get(bookId));
			}
			else 
			{
				System.out.println("Book id: "+bookId+" ist nicht auf dem bookListe");
			}
			
			
		}




		/**
		 * Prints all books saved in the books list 
		 */
		//@Override
		public void printBookListe() 
		{
		
			if(bookListe.isEmpty()) 
			{
				System.out.println("Book list is empty !!");
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

		
		
		//@Override
/**
 * @param bookId : an integer value of book id which is already been saved in the books list
 * @return if the book id is in the books list it return a book object of the given id else return null
 */
		public Book editBook(int bookId) 
		{
			
			if(bookListe.containsKey(bookId)) 
			{
				return bookListe.get(bookId);
			}
			else 
			{
				System.out.println("Book id: "+bookId+" ist nicht auf dem bookListe");
			}
			return null;
			
		}
	   
		
		//@Override
		/**
		 * @return an integer number of the total books of an author
		 */
		public int numberOfBooksInListe() 
		{
			return bookListe.size();
		}

		
		 //###############################
		 //Authors's methods:	 
		
		//@Override
		/**
		 * 
		 * @param author : a author object
		 * @return true if author is saved successfully and false if not
		 */
		public boolean saveAuthor(Author author) {
			
			boolean b=false;
			if(connected) 
			{
				getAuthorFromSql();
				
				
				
				if(!authorsListe.containsValue(author) )
				{
					authorId=authorsListe.size()+1;
					author.setId(authorId);
					
					
					
					String befehl= "insert into book.authorsListe (`id`,`authorName`,`nationality`,`birthYear`,`numberOfBooks`)VALUES("
								+ author.getId()+  ",\""+author.getAuthorName()+"\","+
								"\""+author.getNationality()+"\","+author.getBirthYear()+","+ author.getNumberOfBooks()+");";
					
					
			
					
					erstelleDatensatz(befehl);

					authorsListe.put(author.getId(), author);
					
					if(excuted) {System.out.println(" Author saved to Sql!!");}
					b=true;
				}
				else 
				{
					System.out.println("Author: \""+author.getAuthorName()+"\" existes in database!!");
				}
				
				
				
			}
			else 
			{
				System.out.println("No connection to Sql!!");
			}
			
			
			return b;
			
			
			
		}


		//@Override
		/**
		 * 
		 * @param authorId : print a book information of a given book id number
		 */
		public void printAuthor(int authorId) 
		{

			if(authorsListe.containsKey(authorId)) 
			{
				System.out.println(bookId+"="+authorsListe.get(authorId));
			}
			else 
			{
				System.out.println("Book id: "+authorId+" ist nicht auf dem authorsListe");
			}
			
			
		}


		//@Override
		/**
		 * Prints all authors saved in the authors list 
		 */
		public void printAuthorListe() {
			if(authorsListe.isEmpty()) 
			{
				System.out.println("Book list is empty !!");
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


		//@Override
		/**
		 * @param authorId : an integer value of author id which is already been saved in the authors list
		 * @return if the author id is in the authors list it return an author object of the given id else return null
		 */
		public Author editAuthor(int authorId) {
			if(authorsListe.containsKey(authorId)) 
			{
				return authorsListe.get(authorId);
			}
			else 
			{
				System.out.println("Author id: "+authorId+" ist nicht auf dem bookListe");
			}
			return null;
			
		}
		   
		//@Override
		/**
		 * 
		 * @return return integer of the total number of saved authors
		 */
		public int numberOfAuthorsInListe() 
		{
			return authorsListe.size();
		}

				
//#################################################
//Other methods :	

/**
 * Reads the books information saved in sql database and saves it to local books list
 */
public void getBookFromSql() 
{
	
	Book temp;
			
	try
	{
		
		
			String befehl = "select * from book.booksListe;";
			//String befehl = "select * from book.booksListe where id= '?*' ";
			
			//preSteatement.setString(1, UserEingabe);
				
			Connection verbindung = verbind();
			statement = verbindung.createStatement();
			
			ausgabe = statement.executeQuery(befehl);

		while(ausgabe.next())
		{
			bookId++;	 
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
	excuted = false;
	System.out.println(abbruch.getMessage());
	}		       
	
}


/**
 * Reads the authors information saved in sql database and saves it to local authors list
 */
public void getAuthorFromSql() 
{

	Author temp;
			
	try
	{

			String befehl = "select * from book.authorsliste;";
				
			Connection verbindung = verbind();
			statement = verbindung.createStatement();
			ausgabe = statement.executeQuery(befehl);
		while(ausgabe.next())
		{
			
			authorId++;
				 
			if(!authorsListe.containsKey(ausgabe.getInt("id")) )
			{
			temp = new Author(ausgabe.getString("authorName"),ausgabe.getString("nationality"),
			ausgabe.getInt("birthYear"), ausgabe.getInt("numberOfBooks"),null);
					
			authorsListe.put(ausgabe.getInt("id"), temp);
			
			}
				
		}		
	}
	catch(SQLException abbruch)
	{
	excuted = false;
	System.out.println(abbruch.getMessage());
	}		       
	
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
	if(connected) 
	{
		        String command,command2,command3,command4;
		         command = "CREATE DATABASE IF NOT EXISTS book CHARACTER SET UTF8;";
		         command2 = "use book;" ;
		         command3 = "CREATE TABLE if not EXISTS book.booksListe(id int PRIMARY KEY,bookName varchar(50), authorName varchar(50),"+
		                            "authorId int,"+"category varchar(50) , releaseYear int,"+"Preis double,discount varchar(5),"+
		                            "discountAmount int,description varchar(255) );";
		        
		         command4="CREATE TABLE if not EXISTS book.authorsListe (id int not null, PRIMARY KEY(id),authorName varchar(50),"
		         		+ " nationality varchar(50),birthYear int,numberOfBooks int);";
	         
		        erstelleDatensatz(command);
		        erstelleDatensatz(command2);
		        erstelleDatensatz(command3);
		        
		        erstelleDatensatz(command2);
		        erstelleDatensatz(command4);
		        if(excuted){System.out.println("DataBase creation success!!");}
		        
	}     
		        
}
		    
/**
 * checks if there's connection to sql server then open the connection
 * @return return an open connection if sql server is connected else return null
 */
public Connection verbind()
		    {
		        
		        try
		        {
		        	String driver = "com.mysql.cj.jdbc.Driver";
		           //  driver = "com.mysql.jdbc.Driver";
		            
		            //Aufruf des Treibers innerhalb der Klasse
		            Class.forName(driver);
		            //Instanz einer Verbinfung über den Treibermanager und Funktion getConnection() mit Vorgabe des DB-Pfades
		            Connection verbindung = DriverManager.getConnection("jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC","root","");
		            connected=true;
		            return verbindung;
		            
		        }
		        catch(ClassNotFoundException abbruch) 
		        {
		       //     abbruch.printStackTrace();
		            System.out.println("No Connection to Sql DataBase");
		            connected=false;
		        }
		        catch(SQLException abbruch) 
		        {
		      //      abbruch.printStackTrace();
		            System.out.println("No Connection to Sql DataBase");
		            connected=false;
		        }
		        return null;
		    }
		    

/**
 * Writes data to sql dataBase
 * @param befehl : receives a string of sql command  
 */
public void erstelleDatensatz(String befehl){
		               excuted = false;

	 try
		{
		               Connection verbindung = verbind();
		               PreparedStatement erstelleEintrag = verbindung.prepareStatement(befehl);
		               erstelleEintrag.executeUpdate();
		               excuted = true;
		 }
		           catch(SQLException abbruch)
		                   {
		                       excuted = false;
		                       System.out.println("befehl not excuted!!");
		                       abbruch.printStackTrace();
		                   }
		           
		    
}



 


	
		
	
	
}
