package sourcePackage;

import java.sql.*;

public class Submission {
	private QuizBase quiz;
	private Timestamp startTime;
	private Timestamp endTime;
	private int score;
	private String userName;
	/**
	 * Constructor
	 * @param quiz - quiz that was taken
	 * @param timeStart - date, when the user started the quiz
	 * @param timeEnd - final submission date
	 * @param score - score, the user got
	 */
	public Submission(QuizBase quiz, Timestamp timeStart, Timestamp timeEnd, int score, String userName){
		this.quiz = quiz;
		this.startTime = timeStart;
		this.endTime = timeEnd;
		this.score = score;
		this.userName = userName;
	}
	/**
	 * @return username
	 * 
	 * */
	public String getName(){
		return (userName);
	}
	
	/**
	 * @return the quiz taken
	 */
	public QuizBase getQuiz(){
		return(quiz);
	}
	
	/**
	 * @return submission date
	 */
	public Timestamp getSubmissionTime(){
		return(endTime);
	}
	
	/**
	 * @return time it took the user to complete the quiz (in milliseconds)
	 */
	public long getSessionDuration(){
		return(endTime.getTime() - startTime.getTime());
	}
	
	/**
	 * @return submission score
	 */
	public int getScore(){
		return(score);
	}
}
