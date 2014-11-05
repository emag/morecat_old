INSERT INTO settings (version, blog_name, blog_description, publicity) VALUES (0, 'default_name', 'default_description', FALSE);

INSERT INTO users (version, name, password, email, role) VALUES (0, 'admin','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','admin@morec.at', 'ADMIN');
INSERT INTO users (version, name, password, email, role) VALUES (0, 'author','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','author@morec.at', 'AUTHOR');

INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title1', 'content1', 'permalink1', 1, '2014-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title2', 'content2', 'permalink2', 1, '2014-02-02', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title3', 'content3', 'permalink3', 1, '2014-03-03', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title4', 'content4', 'permalink4', 1, '2014-04-04', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title5', 'content5', 'permalink5', 1, '2014-05-05', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title6', 'content6', 'permalink6', 1, '2014-06-06', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title7', 'content7', 'permalink7', 1, '2014-07-07', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title8', 'content8', 'permalink8', 1, '2014-08-08', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title9', 'content9', 'permalink9', 1, '2014-09-09', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title10', 'content10', 'permalink10', 1, '2014-10-10', now(), 'PUBLIC', 'MARKDOWN');

INSERT INTO tags (entry_id, value) VALUES (1, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (2, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (3, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (4, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (5, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (6, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (7, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (8, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (9, 'tag1');
INSERT INTO tags (entry_id, value) VALUES (10, 'tag1');