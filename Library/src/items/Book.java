package items;

public class Book extends Item{
	private String author;
	private int pages;
	
	public Book() {}
	public Book(int ID, String author, int pages)
	{
		super(ID);
		setAuthor(author);
		setPages(pages);
	}
	public Book(int ID, String author, int pages, Boolean available)
	{
		super(ID, available);
		setAuthor(author);
		setPages(pages);
	}
	public Book(int ID, String author, int pages, Boolean available, String date)
	{
		super(ID, available, date);
		setAuthor(author);
		setPages(pages);
	}


	public String getAuthor() {
		return author;
	}
	private void setAuthor(String author) {
		this.author = author;
	}
	public int getPages() {
		return pages;
	}
	private void setPages(int pages) {
		this.pages = pages;
	}

	@Override
	public String displayExtra()
	{
		String output = "Author: " + author + 
							"\nPages: " + pages;
		return output;
	}
}
