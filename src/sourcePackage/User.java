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
		
		String status = base.getUserStatus(this.userName, connection);
		return (status == null || status.length() <= 0) ? ServletConstants.NO_STATUS : status;
	}
	
	public void setStatus(String newStatus, Connection connection, Database base) {
		if (base != null && newStatus != null) 
			base.setUserStatus(this.userName, (newStatus.length() <= 15 ? newStatus : newStatus.substring(0, 15)), connection);
	}
	
	public int getNumOfUnreadMessages(Connection connection, Database base) {
		if (base == null) return Database.FAIL_EXPECTED_INT;
		
		return base.getNumOfUnreadMessages(this.userName, connection) + base.getNumOfFriendRequests(this.userName, connection);
	}
	
	public boolean sharesQuizzes(Connection connection, Database base) {
		if (base == null) return false;
		
		return base.userSharesQuizzes(this.userName, connection);
	}
	
	public boolean changeQuizSharing(Connection connection, Database base, boolean newValue) {
		if (base == null) return false;
		
		return base.changeQuizSharing(this.userName, this.passwordHash, newValue, connection);
	}
	
	public String getProfilePictureLink(Connection connection, Database base) {
		String url;
		if (connection == null || base == null || (url = base.getUserImage(this.userName, connection)) == null)
			return "http://sites.nicholas.duke.edu/clarklab/files/2011/01/default_profile-d80441a6f25a9a0aac354978c65c8fa9.jpg";
	
		return url;
	}
	
	public boolean setProfilePictureLink(String url, Connection connection, Database base) {
		if (url == null || url.length() <= 0 || connection == null || base == null)
			return false;
		
		return base.setUserImage(this.userName, url, connection);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof User && this.userName.equals(((User)obj).getName());
	}
	
}


