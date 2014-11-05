CREATE TABLE media (
  id SERIAL NOT NULL,
  version INT4 NOT NULL,
  content OID NOT NULL,
  name VARCHAR(255) NOT NULL,
  uuid VARCHAR(255) NOT NULL,
  author_id INT4 NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE media ADD CONSTRAINT FK_hc5qaec4f4l9u7amoqibeet54 FOREIGN KEY (author_id) REFERENCES users;