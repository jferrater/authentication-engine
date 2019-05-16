CREATE TABLE users (
  username varchar(128) PRIMARY KEY,
  email varchar(128),
  password varchar(128)
);

INSERT INTO users VALUES ('joffry', 'joffry.ferrater@gmail.com', 'password123');
