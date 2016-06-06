package sourcePackage;


public class User {
	private String userName;
	private String passwordHash;
	
	public User(String userName, String passwordHash) {
		this.userName = userName;
		this.passwordHash = passwordHash;
	}
	
	public String getName(){
		return(userName);
	}
	
	public String getPasswordHash(){
		return(passwordHash);
	}
}
