package dataBase;

public class BusinessLogic 
{
	public static void main (String[] args) 
	{
	//	IDataProvider Localdata = new ArrayListData();
		IDataProvider sqlData = new Sql();
		
		
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
		

		Book b1 = new Book("baby","jafaar");
		Book b2 = new Book("how3 Ever","lolo");
		Book b3 = new Book("how","lolo");
		Book b4 = new Book("wowo4","toto4");
		Book b5 = new Book("wowo5","toto5");
		
		//Author 
		
		//################################
		//with sql database if connected to sql
		
			sqlData.saveBook(b1);
			sqlData.saveBook(b2);
			sqlData.saveBook(b3);
		//	sqlData.saveBook(b4);
		//	sqlData.saveBook(b5);
			
		//	System.out.println(b1.getId());
		//	System.out.println(b2.getId());
		//	System.out.println(b3.getId());
		//	sqlData.printAuthorListe();
		//	sqlData.printBookListe();
		
		//sqlData.editBook(2).setBookName("go to hill");
		//sqlData.printBookListe();
		//sqlData.printBook(2);
		
		//##################################################
		// with local database
		
		//Localdata.saveBook(b1);
		//Localdata.saveBook(b2);
		//Localdata.saveBook(b3);
		//Localdata.saveBook(b4);
		//Localdata.saveBook(b5);
		
		//Localdata.printBookListe();
		//Localdata.printAuthorListe();
		//System.out.println(b2.equals(b3));
		
	//	Localdata.editBook(2).getAuthor().printauthorsBookListe();
		
		//Localdata.editBook(2).setBookName("willkommen");
		//Localdata.printBook(2);
	
		

		
	}
	
	
	
	
}
