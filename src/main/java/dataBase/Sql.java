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
	private ResultSet ausgabe;
	private Statement statement;
	private PreparedStatement preSteatement;

	private HashMap<Integer,Book> bookListe;
	private HashMap<Integer,Author> authorsListe;
	private boolean excuted= false;
	private static boolean connected= false;
	private static int bookId=1;
	private static int authorId=1;
	    
	
	
	
	    public Sql()
	    {
	    	verbind();
	        createDataBase();
	        bookListe = new HashMap<Integer,Book>();
	        authorsListe= new HashMap<Integer,Author>();
    
	    }
	   
	     
	    // saves book if connected to sql into sql database and locally 
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




		//prints  book in bookliste usind book id
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




		//prints all books in bookliste
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

		
		
//to get data from sql database and save it as a Book object into a Hashmap
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






		// search booksListe using book id and return a book that can be modified
		//@Override
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
	   
	    

		    
	    
	//create sql database and table list   
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
		    
//connect to sql database
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
		    

//write data to sql Database
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


//save author to sql
//@Override
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

//print author using authorId
//@Override
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

//print authorsliste
//@Override
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

//return an author object using an authorid
//@Override
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
    


public int numberOfBooksInListe() 
{
	return bookListe.size();
}

public int numberOfAuthorsInListe() 
{
	return authorsListe.size();
}

		
		
	
	
}
