CREATE TABLE entries (
  id  serial NOT NULL,
  version int4 NOT NULL,
  content text NOT NULL,
  created_date date NOT NULL,
  created_time time NOT NULL,
  format varchar(255) NOT NULL,
  permalink varchar(255) NOT NULL,
  state varchar(255) NOT NULL,
  title varchar(255) NOT NULL,
  author int4 NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE settings (
  id  serial NOT NULL,
  version int4 NOT NULL,
  blog_description varchar(255) NOT NULL,
  blog_name varchar(255) NOT NULL,
  publicity boolean NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tags (
  entry int4 NOT NULL,
  value varchar(255)
);

CREATE TABLE users (
  id  serial NOT NULL,
  version int4 NOT NULL,
  email varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE entries ADD CONSTRAINT UK_tb3g11m5y7xcicx8j00dbcw00  UNIQUE (permalink, created_date);
ALTER TABLE users ADD CONSTRAINT UK_6dotkott2kjsp8vw4d0m25fb7  UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT UK_3g1j96g94xpk3lpxl2qbl985x  UNIQUE (name);
ALTER TABLE entries ADD CONSTRAINT FK_jlvbj3mie5nva1a9vt7qs3gv9 FOREIGN KEY (author) REFERENCES users;
ALTER TABLE tags ADD CONSTRAINT FK_ap7xdwu7utpd2iysc1ots6wes FOREIGN KEY (entry) REFERENCES entries;