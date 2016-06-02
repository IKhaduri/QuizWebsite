package sourcePackage;

import java.util.List;

public class Database {
	/**
	 * Adds a user in database
	 * @param  user - user that must be added in database
	 * */
	public void addUser(User user){
		
	}
	/**
	 * Adds a quiz in database
	 * @param quiz - quiz that must be added in database
	 * */
	public void addQuiz(Quiz quiz){
		
	}
	/**
	 * @param name - name of quiz, unique
	 * @return Quiz type object which you're searching for or null if doesn't exist
	 * 
	 * */
	public Quiz getQuiz(String name){ 
		return null;
	}
	
	/**
	 * @param name - username for user we're searching for
	 * @return matching user or null if not found
	 * */
	public User getUser(String name, String password_hash){
		
		
		return null;
	}
	
	public List<User> getTopUsers(){
		
		
		return null;
	}
	/**
	 * @param num - number of top popular quizzes you want
	 * @return list of popular quizzes, size is num or all quizzes available, whichever bigger
	 * 
	 * */
	public List<Quiz> getPopularQuizzes(int num){
		
		
		return null;
	}
	/**
	 * @param num - number of recently added quizzes you want
	 * @return list of recently added quizzes, size is num or all quizzes available, whichever bigger
	 * 
	 * */
	public List<Quiz> recentlyAddedQuizzes(int num){
		
		return null;
	}
	
	
	private Question getQuestions( String id){
		
		
		return null;
	}
	
}
