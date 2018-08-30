package dataBase;

public class BusinessLogic 
{
	public static void main (String[] args) 
	{
		IDataProvidable localdata = new ArrayListData();
	//	IDataProvidable sqlDataProvider = new DBDataProvider();

		
		
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
		Book b2 = new Book("baby32 Ever","jafaar");
	//	Book b3 = new Book("how","lolo3");
	//	Book b4 = new Book("wowo4","toto4");
	//	Book b5 = new Book("wowo5","toto5");
		
		//################################
		//with sql database if connected to sql
		
		//	sqlDataProvider.saveBook(b1);
		//	sqlDataProvider.saveBook(b2);
		//	sqlDataProvider.saveBook(b3);
		//	sqlDataProvider.saveBook(b4);
		//	sqlDataProvider.saveBook(b5);
			
			

		//	System.out.println(sqlDataProviderBook.getBook(1));
		//	sqlDataProvider.printBookListe();
		//	sqlDataProvider.printAuthorListe();


		//sqlDataProviderBook.printBookListe();
	//	sqlDataProviderBook.printBook(2);
		
	//	b2.setAuthorName("new author name");
		
		
		System.out.println("----------------------------");
		
	//	sqlDataProviderBook.printBook(2);
		
		
		
		//##################################################
		// with local database
		
		localdata.saveBook(b1);
		localdata.saveBook(b2);
	//	localdata.saveBook(b3);
	//	localdata.saveBook(b4);
	//	localdata.saveBook(b5);
		
		localdata.printBook(1);
		
	//	b1.setAuthorName("new author name");
		//localdata.printBook(1);
		
		
		//localdata.printBookListe();
	//	localdata.printBookListe();

	
		//localdata.printBook(2);
	
		

		
	}
	
	
	
	
}
