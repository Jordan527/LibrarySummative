package users;

public class Admin extends Users{
	public Admin(int ID, String forename, String surname, String username, String password)
	{
		super(ID, forename, surname, username, password);
		setAccess(2);
	}
}
