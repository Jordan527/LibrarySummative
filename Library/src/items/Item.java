package items;

public class Item extends Items {
	private Boolean available = true;
	private String date;
	private int ID;
	
	public Item() {}

	public Item(int ID) 
	{
		setID(ID);
	}
	public Item(int ID, Boolean available) 
	{
		setID(ID);
		setAvailable(available);
	}
	public Item(int ID, Boolean available, String date) 
	{
		setAvailable(available);
		setDate(date);
		setID(ID);
	}

	public Boolean getAvailable() {
		return available;
	}
	private void setAvailable(Boolean available) {
		this.available = available;
	}
	public String getDate() {
		return date;
	}
	private void setDate(String date) {
		this.date = date;
	}
	public int getID() {
		return ID;
	}
	private void setID(int ID) {
		this.ID = ID;
	}
	
	public String displayExtra() 
	{
		String output = "";
		return output;
	}
}
