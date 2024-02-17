CREATE TABLE IF NOT EXISTS roles
(
    id          SERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS users
(
    id          SERIAL PRIMARY KEY,
    name       VARCHAR NOT NULL,
    surname  VARCHAR NOT NULL,
    login VARCHAR NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role_id INT REFERENCES roles (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS books
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR NOT NULL,
    author VARCHAR NOT NULL,
    description TEXT NOT NULL,
    writing_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    pages INT NOT NULL,
    owner_id INT REFERENCES users (id)
);
