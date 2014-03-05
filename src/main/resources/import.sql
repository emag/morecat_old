INSERT INTO settings (blog_name, blog_description, publicity) VALUES ('default_name', 'default_description', false);
INSERT INTO users (name, email) VALUES ('user1', 'user1@emamotor.org');
INSERT INTO entries (title, content, author_id, created_at, state, format) VALUES ('title1', 'content1', 1, now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO tags (entry_id, value) VALUES (1, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (1, 'tag2');