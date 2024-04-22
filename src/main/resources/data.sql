INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MODERATOR'),
       ('ROLE_USER');

INSERT INTO users (name, surname, login, password, is_banned, role_id)
VALUES ('Nikita', 'Shmatkov', 'admin', '12345', false, 1),
       ('Andrei', 'Nikolaev', 'moderator', '12345', false, 2),
       ('Pacan', 'Chushpanov', 'user', '12345', false, 3);
VALUES ('Nikita', 'Shmatkov', 'bildin', '123', false, 1),
       ('Andrei', 'Nikolaev', 'gen5', '154', false, 2),
       ('Pacan', 'Chushpanov', 'gop470', '470', false, 3);