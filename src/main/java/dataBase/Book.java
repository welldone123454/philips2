package dataBase;

public class Book 
{
	private String bookName,description,category;
	private int discountAmount,releaseYear;
	private int id;
	private double preis;
	private double finalPreis = preis - (preis * discountAmount /100);
	private String discount;
	private Author author;
	
	
	
	
	//#########################################
	//constructor
	
	
	public Book(String bookName, String authorName) 
	{
		
		this.bookName = bookName;
		this.author =  new Author(authorName,this);
	}


	public Book(String bookName, String authorName, String category,  int releaseYear, double preis,
			 String discount,int discountAmount, String description) 
	{
		super();
		this.bookName = bookName;
		this.description = description;
		this.category = category;
		this.discountAmount = discountAmount;
		this.releaseYear = releaseYear;
		this.preis = preis;
		this.discount = discount;		
	
	}
	
	//######################################
	//other methods
	
	@Override
	public String toString() {
		return "BookName: " + bookName + ", Author: "+getAuthorName()+", category: " + category + ", releaseYear= " + releaseYear + ", preis= " + preis
				+", isDiscount ?: " + discount + ", discountAmount= " + discountAmount + ", finalPreis= " + finalPreis +", description: " + description +   " ]";
	}


	
	

	@Override
	public boolean equals(Object obj) 
	{
		boolean b=false;
		if (obj == null)
			b=false;
		Book other = (Book) obj;

		if(this.bookName.equals(other.getBookName())  )
				b= true; 
		//System.out.println("equal after comapring "+b);
		return b;
	}
	
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		return result;
	}


	
	//######################################
	//setter und getter



	public Author getAuthor() 
	{
		return author;
	}


	public void setAuthor(Author author) {
		this.author = author;
	}


	public String getAuthorName() {
		return 	this.author.getAuthorName();
	}


	public void setAuthorName(String authorName) 
	{
			this.author.setAuthorName(authorName);
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	



	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String isDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	



	
}
