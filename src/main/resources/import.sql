INSERT INTO settings (blog_name, blog_description, publicity) VALUES ('default_name', 'default_description', false);
INSERT INTO users (name, email) VALUES ('user1', 'user1@emamotor.org');

INSERT INTO entries (title, content, permalink, author_id, created_at, state, format) VALUES ('title1', 'content1', 'permalink1', 1, now(), 'PUBLIC', 'MARKDOWN');

INSERT INTO entries (title, content, permalink, author_id, created_at, state, format) VALUES ('title2', 'content2', 'permalink2', 1, now(), 'DRAFT', 'HTML');
INSERT INTO tags (entry_id, value) VALUES (2, 'tag1');

INSERT INTO entries (title, content, permalink, author_id, created_at, state, format) VALUES ('title3', 'content3', 'permalink3', 1, now(), 'DRAFT', 'MARKDOWN');
INSERT INTO tags (entry_id, value) VALUES (3, 'tag2');
INSERT INTO tags (entry_id, value) VALUES (3, 'tag3');