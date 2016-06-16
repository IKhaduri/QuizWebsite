package sourcePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class Factory_Quiz {
		
	
	/**
	 * Builds and returns a Quiz object
	 * @param name - name of the quiz
	 * @param date - date of creation
	 * @param author - user name of the author
	 * @param description - description of the quiz
	 * @param totalScore - combined score the users got from their submissions
	 * @param numSubmissions - number of submissions
	 * @param shouldShuffle - true, if the questions should be shuffled
	 * @param questionCap -  maximal number of questions that will be asked during a single session (restriction; user of the class can feel free to ignore this parameter)
	 * @param timeLimit - time limit of the given quiz (restriction; user of the class can feel free to ignore this parameter)
	 * @param isSinglePage - true, if the quiz should be displayed on a single page
	 * @param questions - the questions
	 * @return Quiz object
	 */
	public static Quiz getQuiz(
			String name, Timestamp date, String author, String description,
			int totalScore, int numSubmissions, int quizScore,
			boolean shouldShuffle, int questionCap, int timeLimit, boolean isSinglePage,
			List<Question> questions){
		Quiz.Header header = new Quiz.Header(name, date, author, description);
		Quiz.Statistics statistics = new Quiz.Statistics(totalScore, numSubmissions);
		Quiz.Parameters parameters = new Quiz.Parameters(shouldShuffle, questionCap, timeLimit, isSinglePage);
		return new Quiz(header, statistics, quizScore, parameters, questions);
	}
	
	/**
	 * Builds and returns a new Quiz object ("new" means without statistics, and creation date set to this moment)
	 * @param name - name of the quiz
	 * @param author - user name of the author
	 * @param description - description of the quiz
	 * @param shouldShuffle - true, if the questions should be shuffled
	 * @param questionCap -  maximal number of questions that will be asked during a single session (restriction; user of the class can feel free to ignore this parameter)
	 * @param timeLimit - time limit of the given quiz (restriction; user of the class can feel free to ignore this parameter)
	 * @param isSinglePage - true, if the quiz should be displayed on a single page
	 * @param questions - the questions
	 * @return Quiz object
	 */
	public static Quiz getNewQuiz(
			String name, String author, String description, int quizScore,
			boolean shouldShuffle, int questionCap, int timeLimit, boolean isSinglePage,
			List<Question> questions){
		Quiz.Header header = new Quiz.Header(name, author, description);
		Quiz.Parameters parameters = new Quiz.Parameters(shouldShuffle, questionCap, timeLimit, isSinglePage);
		return new Quiz(header, new Quiz.Statistics(), quizScore, parameters, questions);
	}
	
	private static int getTotalScore(List<Question> questions){
		int total = 0;
		for(Question question : questions)
			total += question.getScore();
		return total;
	}
	
	/**
	 * Convenience method for creating quizzes that display all the questions they contain during any session
	 * @param name - name of the quiz
	 * @param author - user name of the author
	 * @param description - description of the quiz
	 * @param shouldShuffle - true, if the questions should be shuffled
	 * @param timeLimit - time limit of the given quiz (restriction; user of the class can feel free to ignore this parameter)
	 * @param isSinglePage - true, if the quiz should be displayed on a single page
	 * @param questions - the questions
	 * @return Quiz object
	 */
	public static Quiz getNewQuiz(String name, String author, String description, boolean shouldShuffle, int timeLimit, boolean isSinglePage, List<Question> questions){
		Quiz.Header header = new Quiz.Header(name, author, description);
		Quiz.Parameters parameters = new Quiz.Parameters(shouldShuffle, questions.size(), timeLimit, isSinglePage);
		return new Quiz(header, new Quiz.Statistics(), getTotalScore(questions), parameters, questions);
	}
	
	/**
	 * Constructs and returns a Quiz object (uses already constructed QuizBase)
	 * @param base - header of the quiz
	 * @param shouldShuffle - true, if the questions should be shuffled
	 * @param questionCap - maximal number of questions that will be asked during a single session (restriction; user of the class can feel free to ignore this parameter)
	 * @param timeLimit - time limit of the given quiz (restriction; user of the class can feel free to ignore this parameter)
	 * @param isSinglePage - true, if the quiz should be displayed on a single page
	 * @param questions - the questions
	 * @return Quiz object
	 */
	public static Quiz getQuiz(QuizBase base, boolean shouldShuffle, int questionCap, int timeLimit, boolean isSinglePage, List<Question> questions){
		Quiz.Header header = new Quiz.Header(base.getName(), base.getCreationDate(), base.getAuthor(), base.getDescription());
		Quiz.Statistics statistics = new Quiz.Statistics(base.getTotalScore(), base.getSubmissionCount());
		Quiz.Parameters parameters = new Quiz.Parameters(shouldShuffle, questionCap, timeLimit, isSinglePage);
		return new Quiz(header, statistics, base.getQuizScore(), parameters, questions);
	}
	
	/**
	 * Builds and returns a QuizBase object for lists
	 * @param name - name of the quiz
	 * @param date - date of creation
	 * @param author - user name of the author
	 * @param description - description of the quiz
	 * @param totalScore - combined score the users got from their submissions
	 * @param numSubmissions - number of submissions
	 * @param quizScore - maximal score the user can get thought this quiz
	 * @return QuizBase object
	 */
	public static QuizBase getQuizBase(
			String name,Timestamp date,String author, String description,
			int totalScore, int numSubmissions,
			int quizScore){
		return new QuizBase(new QuizBase.Header(name, date, author, description), new QuizBase.Statistics(numSubmissions, totalScore), quizScore);
	}
	
	/**
	 * Builds and returns a new QuizBase object ("new" means without statistics, and creation date set to this moment)
	 * @param name - name of the quiz
	 * @param author - user name of the author
	 * @param description - description of the quiz
	 * @param quizScore - maximal score the user can get thought this quiz
	 * @return QuizBase object
	 */
	public static QuizBase getNewQuizBase(String name, String author, String description, int quizScore){
		return new QuizBase(new QuizBase.Header(name, author, description), new QuizBase.Statistics(), quizScore);
	}

	
	public static<First, Second, Third> Touple<First, Second, Third> makeTouple(First first, Second second,Third third){
		return new Touple<First, Second,Third>(first, second,third);
	}
	
}
