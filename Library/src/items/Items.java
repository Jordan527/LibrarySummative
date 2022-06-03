package items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

import javax.print.attribute.standard.Destination;

public class Items {	
	protected ArrayList<Item> itemList;
	private String name, genre, image;
	private Double cost;
	private int ID, available, release;
	
	
	public Items () {}
	public Items (int ID, String name, int release, String image, String genre, double cost) throws IOException 
	{
		itemList = new ArrayList<>();
		setID(ID);
		setName(name);
		setRelease(release);
		setImage(image);
		setGenre(genre);
		setCost(cost);

	}

	public int getID() {
		return ID;
	}
	private void setID(int iD) {
		this.ID = iD;
	}
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	private void setGenre(String genre) {
		this.genre = genre;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) throws IOException {
		String source = image;
		String destination = "img/" + this.name + ".jpg";
		saveImage(source, destination);
		this.image = destination;
	}
	public double getCost() {
		return cost;
	}
	private void setCost(double cost) {
		this.cost = cost;
	}
	public void addItem(Item item)
	{
		itemList.add(item);
	}
	public Item getItem()
	{
		return itemList.get(0);
	}
	public int getRelease() {
		return release;
	}
	private void setRelease(int release) {
		this.release = release;
	}
	public void totalAvailable()
	{
		int available = 0;
		Iterator<Item> it = itemList.iterator();
		Item currentItem;
		
		while(it.hasNext())
		{
			currentItem = it.next();
			if(currentItem.getAvailable())
			{
				available += 1;
			}
		}
		
		this.available = available;
	}
	public int getTotalAvailable()
	{
		totalAvailable();
		return this.available;
	}
	public String getCostOutput()
	{
		String output = "£" + String.format("%.2f", cost);
		return output;
	}
	public String getTotalCostOutput()
	{
		double totalCost = cost * getQuantity();
		String output = "£" + String.format("%.2f", totalCost);
		return output;
	}
	public int getQuantity()
	{
		return itemList.size();
	}
	
	public int totalLoaned()
	{
		int total = itemList.size() - available;
		return total;
	}
	

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		File f = new File(destinationFile);
		if(!f.exists())
		{
		    URL url = new URL(imageUrl);
		    InputStream is = url.openStream();
		    FileOutputStream os = new FileOutputStream(destinationFile);
	
		    int b = 0;
		    while ((b=is.read()) != -1) {
		        os.write(b);
		    }
	
		    is.close();
		    os.close();
		}
	}
	
	public String earliestDate()
	{
		String output;
		Item copy = itemList.get(0);
		Boolean available = copy.getAvailable();
		
		if(!available)
		{
			String date = copy.getDate();
			output = date;
		} else
		{
			output = "N/A";
		}

		return output;
	}
	
	public String displayInfo()
	{
		Item copy = itemList.get(0);
		
		totalAvailable();
		
		String output = "ID: " + getID() +
				"\nName: " + getName() +
				"\nGenre: " + getGenre() +
				"\nCost: £" + getCost();
		
		output += "\n" + copy.displayExtra();
		
		output += "\nAvailable: " + available + 
				"\nLoaned: " + totalLoaned() +
				"\nEarliest Return: " + earliestDate() +
				"\nTotal: " + itemList.size();
		
		return output;
	}
	
}
