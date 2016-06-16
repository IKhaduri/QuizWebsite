package sourcePackage;

import java.sql.Timestamp;
import java.util.Date;

public class Factory_User {


	public static User getUser(String username, String passwordHash) {
		return new User(username, passwordHash);
	}
	
	/**
	 * Constructs a message
	 * @param to - receiver
	 * @param from - sender
	 * @param message - message text
	 * @param date - delivery date
	 * @param seen - true, if the message is seen
	 * @return Message object
	 */
	public static Message getMessage(String to, String from, String message, Timestamp date, boolean seen){
		return new Message(from, to, message, date, seen);
	}
	
	/**
	 * Creates new message object, that was sent "now" and is not seen yet
	 * @param to - receiver
	 * @param from - sender
	 * @param message - message text
	 * @return Message object
	 */
	public static Message getNewMassege(String to, String from, String message){
		return new Message(from, to, message, new Timestamp(new Date().getTime()), false);
	}

	public static Submission getSubmission(QuizBase quiz, Timestamp timeStart, Timestamp timeEnd, int score, String userName){
		return new Submission(quiz, timeStart, timeEnd, score,userName);
	}
	
}
