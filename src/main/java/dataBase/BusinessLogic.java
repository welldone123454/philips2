package dataBase;

public class BusinessLogic 
{
	public static void main (String[] args) 
	{
	//	IDataProviderBook localdata = new ArrayListData();
		IDataProviderBook sqlDataProviderBook = new DBDataProvider();
		
	//	IDataProviderAuthor localdataAuthor = new ArrayListData();
	//	IDataProviderAuthor sqlDataProviderAuthor = new DBDataProvider();
		
		
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
		Book b3 = new Book("how","lolo3");
		Book b4 = new Book("wowo4","toto4");
		Book b5 = new Book("wowo5","toto5");
		

		//################################
		//with sql database if connected to sql
		
			sqlDataProviderBook.saveBook(b1);
		//	sqlDataProviderBook.saveBook(b2);
		//	sqlDataProviderBook.saveBook(b3);
		//	sqlDataProviderBook.saveBook(b4);
		//	sqlDataProviderBook.saveBook(b5);
			
		//	System.out.println(b1.getId());
		//	System.out.println(b2.getId());
		//	System.out.println(b3.getId());
		//	sqlData.printAuthorListe();
		//	sqlData.printBookListe();
		

		//sqlData.printBookListe();
		//sqlData.printBook(2);
		
		//##################################################
		// with local database
		
	//	localdata.saveBook(b1);
	//	localdata.saveBook(b2);
	//	localdata.saveBook(b3);
	//	localdata.saveBook(b4);
	//	localdata.saveBook(b5);
		
		//localdata.printBookListe();
	//	localdata.printBookListe();

	
		//localdata.printBook(2);
	
		

		
	}
	
	
	
	
}
