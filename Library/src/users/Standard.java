package users;

public class Standard extends Users{
	public Standard() {}
	public Standard(int ID, String forename, String surname, String username, String password)
	{
		super(ID, forename, surname, username, password);
		setAccess(0);
	}
	public Standard(int ID, String forename, String surname, String username, String password, int access)
	{
		super(ID, forename, surname, username, password);
		setAccess(access);
	}
}
