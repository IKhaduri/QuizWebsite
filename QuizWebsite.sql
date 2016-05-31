
CREATE SCHEMA IF NOT EXISTS quiz_website;

USE quiz_website;

drop table if exists categories;
create table categories(
	id int(11) not null,
    category_name varchar(256),
    
    primary key(id)
);

drop table if exists quizes;
create table quizes(
	id int(11) not null,
    quiz_name varchar(256) unique,
    creation_date timestamp,
    total_score int(32) default 0,
    total_submittions int(11) default 0,
    random_shaffle bool,
    question_cap int(11) default 1024000,
    
    primary key(id)
);

drop table if exists quizes_to_categories;
create table quizes_to_categories(
	quiz_id int(11) not null,
    category_id int(11) not null
);

drop table if exists questions;
create table questions(
	id int(11) not null,
    quiz_id int(11) not null,
    serialized_object varchar(16384),
    score int(11) default 1,
    
    primary key(id)
);

drop table if exists users;
create table users(
	id int(11) not null,
    username varchar(256) unique,
    password_hash varchar(4096),
    
    primary key(id)
);

drop table if exists event_log;
create table event_log(
	quiz_id int(11) not null,
    user_id int(11) not null,
    score int(11) default 0,
    submission_date timestamp
);
