package items;

import java.util.*;

public class ItemManager {
	private ArrayList<Items> itemsList;
	
 	public ItemManager() 
	{
		itemsList = new ArrayList<>();
	}
	
	
	public void addItem(Items items)
	{
		itemsList.add(items);
	}
	public int itemIndex(int match)
	{
		Iterator<Items> it = itemsList.iterator();
		Items currentItem;
		int index = 0;
		Boolean found = false;
		
		while (!found && it.hasNext()) 
		{
			currentItem = it.next();
			if(match == currentItem.getID())
			{
				found = true;
			} else
			{
				index += 1;
			}
		};
		
		if(!found)
		{
			index = -1;
		}
		
		return index;
	}
	public void addCopy(Item item)
	{
		int match = (int) item.getID();
		int index = itemIndex(match);
		
		if(index != -1)
		{
			Items items = itemsList.get(index);
			items.addItem(item);
			itemsList.set(index, items);
		}
		else
		{
			System.out.println("Item not found \n");
		}
	}
	
	public void sort()
	{
        Collections.sort(itemsList, Comparator.comparing(Items::getID));
	}
	public ArrayList<Items> getBooks()
	{
		ArrayList<Items> bookList = new ArrayList<>();
		
		for(Items items : itemsList)
		{
			Item item = items.itemList.get(0);
			
			Boolean book = item instanceof Book;

			if(book)
			{
				bookList.add(items);
			}
		}
		
		return bookList;
	}
	public ArrayList<Items> getMovies()
	{
		ArrayList<Items> movieList = new ArrayList<>();
		
		for(Items items : itemsList)
		{
			Item item = items.itemList.get(0);
			
			Boolean movie = item instanceof Movie;

			if(movie)
			{
				movieList.add(items);
			}
		}
		
		return movieList;
	}
	
	
	public void displayAll()
	{
		Iterator<Items> it = itemsList.iterator();
		Items currentItem;
		
		String output;
		
		while(it.hasNext())
		{
			currentItem = it.next();
			
			output = currentItem.displayInfo();
			
			System.out.println(output + "\n");
		}
	}
}
