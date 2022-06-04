package users;

import java.util.*;

public class UserManager {
	private ArrayList<Users> userList;
	
	public UserManager() 
	{
		userList = new ArrayList<>();
	}
	
	public ArrayList<Users> getUserList() 
	{
		return userList;
	}
	
	public void addUser(Users user)
	{
		userList.add(user);
	}
	
	public int userIndex(Users user)
	{
		Iterator<Users> it = userList.iterator();
		Users currentUser;
		int index = 0;
		Boolean found = false;
		
		while (!found && it.hasNext()) 
		{
			currentUser = it.next();
			Boolean passMatch = user.getPassword() == currentUser.getPassword();
			Boolean userMatch = user.getUsername() == currentUser.getUsername();

			if(userMatch && passMatch)
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
	public int getIndex(String username, String password)
	{
		Iterator<Users> it = userList.iterator();
		Users currentUser;
		int index = 0;
		Boolean found = false;
		
		while (!found && it.hasNext()) 
		{
			currentUser = it.next();
			Boolean userMatch = currentUser.getUsername().equals(username);
			Boolean passMatch = currentUser.getPassword().equals(password);

			if(userMatch && passMatch)
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
	
	public String existingUser(String forename, String surname, String username, String password)
	{
		Iterator<Users> it = userList.iterator();
		Users currentUser;
		String exists = "";
		
		while(it.hasNext())
		{
			currentUser = it.next();
			
			Boolean nameMatch = (currentUser.getForename().equals(forename) && currentUser.getSurname().equals(surname));
			Boolean userMatch = currentUser.getUsername().equals(username);
			Boolean passMatch = currentUser.getPassword().equals(password);
			
			if(nameMatch)
			{
				exists += "1";
			} 
			if(userMatch)
			{
				exists += "2";
			} 
			if(passMatch)
			{
				exists += "3";
			}
			
		}

		return exists;
	}
}
	