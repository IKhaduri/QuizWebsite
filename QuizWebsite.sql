
# Quiz Website Database:
CREATE SCHEMA IF NOT EXISTS quiz_website;

USE quiz_website;

# categories table contains all the possible categories(group names) for the quizes.
drop table if exists categories;
create table categories(
	id int(11) not null,			# unique identifier for the category
    category_name varchar(256),		# name of the category
    
    primary key(id)
);

# quizes table contains headers and statistical informaition for the individual quizes.
drop table if exists quizes;
create table quizes(
	id int(11) not null,					# unique identifier for the quiz
    quiz_name varchar(256) unique,			# unique name for the quiz
    creation_date timestamp,				# date of creation
    total_score int(32) default 0,			# sum of all participants' scores
    total_submittions int(11) default 0,	# number of submittions
    random_shaffle bool,					# true, if the questions can be randomly shaffled before displaying
    question_cap int(11) default 1024000,	# maximal number of questions that can be asked in this quiz(useful, if and only if there are more questions, than the site needs to ask the user)
    time_limit int(11) default 1800,		# time limit for the test
    autor_id int(11) default null,			# identifier of the autor
    
    primary key(id)
);

# quizes_to_categories table links the categories and quizes tables, letting us filter the data.
drop table if exists quizes_to_categories;
create table quizes_to_categories(
	quiz_id int(11) not null,		# identifier of the quiz
    category_id int(11) not null	#identifier of the category
);

# questions table stores the individual questions
drop table if exists questions;
create table questions(
	id int(11) not null,				# unique identifier for the question
    quiz_id int(11) not null,			# identifier of the quiz, the question "belongs" to
    serialized_object varchar(16384),	# the serialized object, containig the question to be asked
    score int(11) default 1,			# score for the question
    
    primary key(id)
);

# users table contains user information
drop table if exists users;
create table users(
	id int(11) not null,			# unique identifier for the user
    username varchar(256) unique,	# unique user name
    password_hash varchar(4096),	# hashed password(+salt)
    
    primary key(id)
);

# event_log table contains information about completed quizes
drop table if exists event_log;
create table event_log(
	quiz_id int(11) not null,	# quiz identifier
    user_id int(11) not null,	# user identifier
    score int(11) default 0,	# user's final score
    start_time timestamp,		# date, when the user started the quiz
    end_time timestamp			# date, when the user finished the quiz
);
