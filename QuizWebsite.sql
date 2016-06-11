
# Quiz Website Database:
CREATE SCHEMA IF NOT EXISTS quiz_website;

USE quiz_website;

# categories table contains all the possible categories(group names) for the quizes.
create table if not exists categories(
	id int(11) not null auto_increment,		# unique identifier for the category
    category_name varchar(256),				# name of the category
    
    primary key(id)
);

# quizes table contains headers and statistical informaition for the individual quizes.
create table if not exists quizes(
	id int(11) not null auto_increment,		# unique identifier for the quiz
    quiz_name varchar(128) unique,			# unique name for the quiz
    creation_date timestamp,				# date of creation
    total_score int(32) default 0,			# sum of all participants' scores
    total_submittions int(11) default 0,	# number of submittions
    random_shuffle bool,					# true, if the questions can be randomly shaffled before displaying
    question_cap int(11) default 1024000,	# maximal number of questions that can be asked in this quiz(useful, if and only if there are more questions, than the site needs to ask the user)
    time_limit int(11) default 1800,		# time limit for the test
    author_id int(11) default null,			# identifier of the author
    quiz_score int(11),						# total score, one can earn in this quiz
    description varchar(4096),				# description of the quiz
    is_single_page boolean,					# true, if the quiz is of a single page type
    
    primary key(id)
);

# quizes_to_categories table links the categories and quizes tables, letting us filter the data.
create table if not exists quizes_to_categories(
	quiz_id int(11) not null,				# identifier of the quiz
    category_id int(11) not null			# identifier of the category
);

# questions table stores the individual questions
create table if not exists questions(
	id int(11) not null auto_increment,		# unique identifier for the question
    quiz_id int(11) not null,				# identifier of the quiz, the question "belongs" to
    index_in_quiz int(11),					# index of a question in the quiz
    serialized_object varchar(16384),		# the serialized object, containig the question to be asked
    
    primary key(id)
);

# users table contains user information
create table if not exists users(
	id int(11) not null auto_increment,		# unique identifier for the user
    username varchar(128) unique,			# unique user name
    password_hash varchar(4096),			# something generated from the password(we're not exactly willing to reveal the underlying process here)
    total_score int(11) default 0,			# sum of all quizzes' scores
	max_score decimal(5, 2) default 0,		# maximum score reached in some quiz, specified in percentage terms
    user_status varchar(144) default "",	# user's status
    
    primary key(id)
);

# event_log table contains information about completed quizes
create table if not exists event_log(
	quiz_id int(11) not null,				# quiz identifier
    user_id int(11) not null,				# user identifier
    score int(11) default 0,				# user's final score
    start_time timestamp,					# date, when the user started the quiz
    end_time timestamp						# date, when the user finished the quiz
);

# messages table contains information about messages
create table if not exists messages(
	sender_id int(11) not null,				# sender identifier
    receiver_id int(11) not null,			# receiver identifier
    message_string varchar(16384),			# message
    delivery_date timestamp,				# date of delivery
    message_seen bool						# true, if the message is seen
);


select * from users;

