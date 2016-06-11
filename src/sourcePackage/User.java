package sourcePackage;

import java.sql.Connection;
import java.util.List;

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
		if (base == null) return 0.0;
		
		return base.getUserMaxScore(this.userName, connection);
	}
	
	public int getTotalScore(Database base, Connection connection) {
		if (base == null) return 0;
		
		return base.getUserTotalScore(this.userName, connection);
	}
	
	public int getNumOfSubmissions(Database base, Connection connection) {
		if (base == null) return 0;
		
		return base.getNumOfSubmissions(this.userName, connection);
	}
	
	public List<Submission> getSubmissions(Database base, Connection connection, int limit) {
		if (base == null || limit <= 0) return null;
		
		return base.getUserSubmissions(this.userName, limit, connection);
	}
	
	public List<QuizBase> getCreatedQuizzes(Database base, Connection connection, int limit) {
		if (base == null || limit <= 0) return null;
		
		return base.getUserCreatedQuizzes(this.userName, limit, connection);
	}
	
}


