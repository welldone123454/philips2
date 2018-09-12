package dataBase;

import java.util.HashMap;

public class BusinessLogic 
{
	public static void main (String[] args) 
	{

		//connectionDriver = "com.mysql.cj.jdbc.Driver";
		//connectionAddress="jdbc:mysql://localhost/mysql?useSSL=false&serverTimezone=UTC";
			
		IDataProviderBook dataProviderBook;
		IDataProviderAuthor dataProviderAuthor;
		
		
		/**
		 * two constructors to create a new Book :
		 * 
		 * Book b1 = new Book(String bookName, String authorName, String category,  int releaseYear, double preis,
		 * boolean discount,int discountAmount,double finalPreis, String description) 
		 * 
		 * 
		 * or:
		 * Book b1 = new Book("bookName");
		 */
		
	//	Book b = new Book("book1", "Author1", "category",  2000 , 23 , true, 3 ,"description");
		

		Book b1 = new Book("baby23","jafaara");
		Book b2 = new Book("baby32_Ever","jafaar");
		Book b3 = new Book("wowo4","lolo3");
		Book b4 = new Book("wowo4","toto4");
		Book b5 = new Book("wowo5","toto4");
		Book b6 = new Book("wowo6","toto6");
		
		//################################
		//with sql database if connected to sql
		
		try
		{
			//dataProviderBook = new DBBookDataProvider();
			//dataProviderAuthor = new DBAuthorDataProvider();
			
			dataProviderBook = new ArrayListDataProviderBook();
			dataProviderAuthor = new ArrayListDataProviderAuthor();

			dataProviderBook.saveBook(b1);
			dataProviderBook.saveBook(b2);
		//	dataProviderBook.saveBook(b3);
		//	dataProviderBook.saveBook(b4);
		//	dataProviderBook.saveBook(b5);
		//	dataProviderBook.saveBook(b6);
			
		//	dataProviderBook.printBook(3);
			//dataProviderAuthor.printAuthorListe();
			System.out.println(dataProviderBook.getBook(b2));
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("----------------------------");
		

		
		//##################################################
		// with local database
		


		
	}
	
	
	
	
}
