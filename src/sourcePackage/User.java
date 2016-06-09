package sourcePackage;

import java.sql.Connection;

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
	
	public double getMaxScorePercentage(Database base, Connection connection) {
		return base.getUserMaxScore(this.userName, connection);
	}
	
	public int getTotalScore(Database base, Connection connection) {
		return base.getUserTotalScore(this.userName, connection);
	}
}
