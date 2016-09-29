DROP TABLE posts;
DROP TABLE users;

CREATE TABLE users (
    username VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(16) NOT NULL,
    joindate DATE DEFAULT CURRENT_DATE NOT NULL,
    userid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY
);

CREATE TABLE posts (
    content VARCHAR(200) NOT NULL,
    postdate TIMESTAMP NOT NULL,
    userid INTEGER NOT NULL,
    postid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    FOREIGN KEY (userid) REFERENCES users(userid)
);

INSERT INTO users (username, password, joindate) VALUES
('johndoe', 'javauser', '2016-09-05'),
('jilljack', 'javauser', '2016-09-13');

INSERT INTO posts (content, postdate, userid) VALUES
('This site totally rocks!', '2016-09-05 15:21:18', 1),
('I think I''m going to like it here.', '2016-09-13 1:04:04', 2),
('Hey! My friend John Doe is a member, too!', '2016-09-15 10:09:45', 2);


