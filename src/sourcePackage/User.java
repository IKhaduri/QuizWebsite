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
	
	public void setPasswordHash(String newPasswordHash, Database base, Connection connection) {
		base.updateUserPassword(this.userName, newPasswordHash, connection);
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
	
	public int getNumOfCreatedQuizzes(Database base, Connection connection) {
		if (base == null) return 0;
		
		return base.getNumOfCreatedQuizzes(this.userName, connection);
	}
	
	public List<QuizBase> getCreatedQuizzes(Database base, Connection connection, int limit) {
		if (base == null || limit <= 0) return null;
		
		return base.getUserCreatedQuizzes(this.userName, limit, connection);
	}
	
	public String getStatus(Database base, Connection connection) {
		if (base == null) return null;
		
		return base.getUserStatus(this.userName, connection);
	}
	
	public void setStatus(String newStatus, Connection connection, Database base) {
		if (base != null && newStatus != null) base.setUserStatus(this.userName, newStatus, connection);
	}
	
	public int getNumOfUnreadMessages(Connection connection, Database base) {
		if (base == null) return Database.FAIL_EXPECTED_INT;
		
		return base.getNumOfUnreadMessages(this.userName, connection);
	}
	
	public boolean sharesQuizzes(Connection connection, Database base) {
		if (base == null) return false;
		
		return base.userSharesQuizzes(this.userName, connection);
	}
	
	public boolean changeQuizSharing(Connection connection, Database base, boolean newValue) {
		if (base == null) return false;
		
		return base.changeQuizSharing(this.userName, this.passwordHash, newValue, connection);
	}
	
}


