INSERT INTO settings (blog_name, blog_description, publicity) VALUES ('default_name', 'default_description', FALSE);

INSERT INTO users (name, password, email, role) VALUES ('admin','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','admin@emamotor.org', 'ADMIN');
INSERT INTO users (name, password, email, role) VALUES ('author','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','author@emamotor.org', 'AUTHOR');

INSERT INTO entries (title, content, permalink, author_id, created_date, created_time, state, format) VALUES ('title1', 'content1', 'permalink1', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');

INSERT INTO entries (title, content, permalink, author_id, created_date, created_time, state, format) VALUES ('title2', 'content2', 'permalink2', 1, '2013-12-31', now(), 'DRAFT', 'HTML');
INSERT INTO tags (entry_id, value) VALUES (2, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (2, 'tag2');

INSERT INTO entries (title, content, permalink, author_id, created_date, created_time, state, format) VALUES ('title3', 'content3', 'permalink3', 2, '2014-03-16', now(), 'DRAFT', 'MARKDOWN');
INSERT INTO tags (entry_id, value) VALUES (3, 'tag2');
INSERT INTO tags (entry_id, value) VALUES (3, 'tag3');