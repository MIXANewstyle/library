INSERT INTO roles (name)
VALUES ('admin'),
       ('moderator'),
       ('user');

INSERT INTO users (name, surname, login, password, is_banned, role_id)
VALUES ('Nikita', 'Shmatkov', 'bildin', '123', false, 1),
       ('Andrei', 'Nikolaev', 'gen5', '154', false, 2),
       ('Pacan', 'Chushpanov', 'gop470', '470', false, 3);

INSERT INTO books (title, author, description, writing_date, pages, owner_id)
VALUES ('Gunslinger', 'Stephen King ',
        'It started in the desert', '2003-04-15', 150, 1),
       ('Air Seller', 'Brothers Strugackiy', 'Dont dare you', '1987-12-03',
        54, 2);

INSERT INTO books (title, author, description, writing_date, pages)
VALUES ('Teremok', 'people', 'Just a tale', '1000-01-01',
        10);