INSERT INTO settings (version, blog_name, blog_description, publicity) VALUES (0, 'default_name', 'default_description', FALSE);

INSERT INTO users (version, name, password, email, role) VALUES (0, 'admin','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','admin@morec.at', 'ADMIN');
INSERT INTO users (version, name, password, email, role) VALUES (0, 'author','e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e','author@morec.at', 'AUTHOR');

INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (0, 'title1', 'content1', 'permalink1', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (1, 'title2', 'content2', 'permalink2', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (2, 'title3', 'content3', 'permalink3', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (3, 'title4', 'content4', 'permalink4', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (4, 'title5', 'content5', 'permalink5', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (5, 'title6', 'content6', 'permalink6', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (6, 'title7', 'content7', 'permalink7', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (7, 'title8', 'content8', 'permalink8', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (8, 'title9', 'content9', 'permalink9', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
INSERT INTO entries (version, title, content, permalink, author_id, created_date, created_time, state, format) VALUES (9, 'title10', 'content10', 'permalink10', 1, '2013-01-01', now(), 'PUBLIC', 'MARKDOWN');
