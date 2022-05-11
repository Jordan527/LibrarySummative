package users;

public class Users {
	private String forename, surname, username, password;
	private int ID, access;
	
	public Users() {}
	public Users(String forename, String surname, String username, String password)
	{
		setUsername(username);
		setPassword(password);
		setForename(forename);
		setSurname(surname);
		setUsername(username);
		setPassword(password);
	}
	public Users(int ID, String forename, String surname, String username, String password)
	{
		setID(ID);
		setForename(forename);
		setSurname(surname);
		setUsername(username);
		setPassword(password);
	}

	public int getID() {
		return ID;
	}
	private void setID(int iD) {
		this.ID = iD;
	}
	public String getForename() {
		return forename;
	}
	private void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	private void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	private void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	
	public void displayAll()
	{
		System.out.println("ID: " + getID() + 
				"\nForename: " + getForename() +
				"\nSurname: " + getSurname() +
				"\nUsername: " + getUsername() +
				"\nPassword: " + getPassword() +
				"\nType: " + this.getClass().getSimpleName() +
				"\nAccess: " + this.getAccess());
	}
}
