INSERT INTO roles (name)
VALUES ('admin'),
       ('moderator'),
       ('user');

INSERT INTO users (name, surname, login, password, is_banned, role_id)
VALUES ('Nikita', 'Shmatkov', 'bildin', '123', false, 1),
       ('Andrei', 'Nikolaev', 'gen5', '154', false, 2),
       ('Pacan', 'Chushpanov', 'gop470', '470', false, 3);