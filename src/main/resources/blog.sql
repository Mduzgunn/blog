CREATE TABLE users
(
    uid          SERIAL PRIMARY KEY,
    username     VARCHAR(255) UNIQUE,
    email        VARCHAR(255),
    date_created DATE
);

CREATE TABLE posts
(
    pid          SERIAL PRIMARY KEY,
    title        VARCHAR(255),
    body         VARCHAR,
    user_id      INT REFERENCES users (uid),
    author       VARCHAR REFERENCES users (username),
    date_created TIMESTAMP,
    like_user_id INT[] DEFAULT ARRAY[]:: INT [],
    likes        INT DEFAULT 0
);

CREATE TABLE comments
(
    cid          SERIAL PRIMARY KEY,
    comment      VARCHAR(255),
    author       VARCHAR REFERENCES users (username),
    user_id      INT REFERENCES users (uid),
    post_id      INT REFERENCES posts (pid),
    date_created TIMESTAMP
);
#################################################################################

INSERT INTO 'users'('uid', 'username', 'email', 'date_created')
VALUES (1, 'melih1', 'deneme1@gmail.com', '2021-10-30'),
       (2, 'melih2', 'deneme2@gmail.com', '2021-10-30'),
       (3, 'melih3', 'deneme3@gmail.com', '2021-10-30');

INSERT INTO `posts` (`pid`, `title`, `body`, `user_id`, `author`, `date_created`)
VALUES (1, 'ilkpost', 'postbody1', '1', 'melih1', '2021-10-30'),
       (2, 'post', 'postbody2', '2', 'melih2', '2021-10-30'),
       (3, 'sonpost', 'postbody3', '3', 'melih3', '2021-10-30');

INSERT INTO 'comments'('cid', 'comment', 'author', 'user_id', 'post_id', 'date_created')
VALUES (1, 'yorum1', 'melih1', 1, 1, '2021-10-30'),
       (2, 'yorum2', 'melih2', 2, 2, '2021-10-30'),
       (3, 'yorum3', 'melih3', 3, 3, '2021-10-30');

####################################################################################

SELECT uid, username, email, date_created
FROM users;

SELECT pid, title, body, user_id, author, date_created
FROM posts;

SELECT cid, comment, author, user_id, post_id, date_created
FROM comments;



#ÖDEV 3 blog ve bloglara ait yorumları çekecek sql sorgusu
SELECT p.*, c.* FROM post p
                         LEFT JOIN comment c ON c.post_id = p.pid;

# ek olarak bu verilerle beraber user çekme komutu
SELECT p.*, c.*, u.* FROM post p
                              LEFT JOIN comment c ON c.post_id = p.pid
                              LEFT JOIN user u ON u.uid = p.user_id