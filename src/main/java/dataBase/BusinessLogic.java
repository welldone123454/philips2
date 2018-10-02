package dataBase;

import java.util.HashMap;

public class BusinessLogic 
{
	public static void main (String[] args) 
	{
		
		//1. First create instances of the classes which helps saving the data locally and/or to sql server
		
			//connectionDriver = "com.mysql.cj.jdbc.Driver";
			//connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
				
			//create instance of IDataProviderBook class to save book locally or to sql server 
			IDataProviderBook dataProviderBook;
			
			//create instance of IDataProviderAuthor class to save author locally or to sql server 
			IDataProviderAuthor dataProviderAuthor;
		
			//#########################################
			
			
		//2. Create the data we want to save. example creating books to be saved to a bookslist 
				/**
				 * to create a new book :
				 * there are two constructors to create a new Book :
				 * 
				 *  public Book(String bookName, String authorName, String category,  int releaseYear, double preis,
						 String discount,int discountAmount, String description) 
				 * 
				 * 
				 * or:
				 * Book b1 = new Book("bookName");
				 */
				
				/*
				 * Book b3 = new Book("Dream of the Red Chamber", "Cao Xueqin", "family",  1791 , 23 , "ja", 3 ,"Chinese language about family");
				 */
	
					Book b1 = new Book("The Hobbit","J.Tolkien");
					Book b2 = new Book("And Then There Were None","Agatha Christie");
					Book b3 = new Book("Dream of the Red Chamber", "Cao Xueqin", "family",  1791 , 23 , "ja", 3 ,"Chinese language about family");
					Book b4 = new Book("Alice's Adventures in Wonderland", "Lewis Carroll", "fantasy ",  1865 , 43 , "ja", 15 ,"fantasy book about alice in wonderland");
					Book b5 = new Book("The Da Vinci Code", "Dan Brown", "mystery, thriller ",  2003 , 33 , "ja", 8 ,"mystery book about Da Vinci Code");
			
					
					
					/**
					 * 
					 * to create a new author:
					 * 	
					 *	public Author(String authorName, String nationality, int birthYear, int numberOfBooks, Book book) 
					 * 
					 * or 
					 * 
					 * 	public Author(String authorName, String nationality, int birthYear, int numberOfBooks)
					 * 
					 * or 
					 * 
					 * 	public Author(String authorName,String bookName) 
					 */
					
					
					Author a1 = new Author("Anna Sewell","british", 1820, 42);
					Author a2 = new Author("Agatha Christie","british", 1890, 50, b2); //adding the book b2 to the authors bookslist
					Author a3 = new Author("Dan Brown", b5);

		

//################################
		//with sql database if connected to sql
/*		
		try
		{
		//Initialize the dataProviders to save data to Sql server
				dataProviderBook = new DBBookDataProvider();
				dataProviderAuthor = new DBAuthorDataProvider();
					
		
					//save book
					dataProviderBook.saveBook(b1);
					dataProviderBook.saveBook(b2);
					dataProviderBook.saveBook(b3);
					dataProviderBook.saveBook(b4);
					dataProviderBook.saveBook(b5);

					
					//Edit Book information 
					dataProviderBook.getBook(b1).setBookName("The Hobbit 2");    //change book name
					dataProviderBook.getBook(b5).setDiscount("nein");			//change discount status 
					
					
					
					//Print book informations
					dataProviderBook.printBook(b3);
					dataProviderBook.printBookListe();
					
					//####################################
					//save author
					dataProviderAuthor.saveAuthor(a1);
					dataProviderAuthor.saveAuthor(a2);
					dataProviderAuthor.saveAuthor(a3);
					
					
					//Edit author information 
					dataProviderAuthor.getAuthor(a1).setAuthorName("Anna Sewell 2");   //change Author name
					dataProviderAuthor.getAuthor(a1).setNationality("Hindi");			//change authors nationality
					
					//Print author informations
					dataProviderAuthor.printAuthor(a3);
					dataProviderAuthor.printAuthorListe();

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----------------------------");
*/
					
//##################################################
	// with local database
		


		
				//Initialize the dataProviders to save data to Sql server
					dataProviderBook = new ArrayListDataProviderBook();
					dataProviderAuthor = new ArrayListDataProviderAuthor();
			
						//save book
						dataProviderBook.saveBook(b1);
						dataProviderBook.saveBook(b2);
						dataProviderBook.saveBook(b3);
						dataProviderBook.saveBook(b4);
						dataProviderBook.saveBook(b5);

						
						//Edit Book information 
							//dataProviderBook.getBook(b1).setBookName("The Hobbit 2");    //change book name
							//dataProviderBook.getBook(b5).setDiscount("nein");			//change discount status 
						
						
						
						//Print book informations
							//dataProviderBook.printBook(b3);
							dataProviderBook.printBookListe();
						
						//####################################
						//save author
						dataProviderAuthor.saveAuthor(a1);
						dataProviderAuthor.saveAuthor(a2);
						dataProviderAuthor.saveAuthor(a3);
						
						
						//Edit author information 
							//dataProviderAuthor.getAuthor(a1).setAuthorName("Anna Sewell 2");   //change Author name
							//dataProviderAuthor.getAuthor(a1).setNationality("Hindi");			//change authors nationality
						
						//Print author informations						
						dataProviderAuthor.printAuthorListe();
						dataProviderAuthor.printAuthor(a1);
						dataProviderAuthor.printAuthor(a3);
						dataProviderAuthor.printAuthor(4); //print author info using author id
					
		
	}
	
	
	
	
}
