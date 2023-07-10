INSERT INTO users (uuid, email, password)
VALUES
    ('123e4567-e89b-77d3-a456-426614174001', 'user-role@mail.com', '$2a$12$nU3NTASAvpso5NuAr..2UOQL15kPiJ.IiwFDVZVMP86TUvZrSrf0m'),
    ('123e4567-e89b-77d3-a456-426614174002', 'user-api-role@mail.com', '$2a$12$Xd6uSdDeGUktWdcVm7MT7e.N/RGEkwK58XaiFuDTUw4FUqe/fpMN6');

INSERT INTO roles (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_USER_API');

INSERT INTO users_roles (user_id, role_id)
VALUES
    ((SELECT u.id FROM users u WHERE u.email = 'user-role@mail.com'), (SELECT r.id FROM roles r WHERE r.name = 'ROLE_USER')),
    ((SELECT u.id FROM users u WHERE u.email = 'user-api-role@mail.com'), (SELECT r.id FROM roles r WHERE r.name = 'ROLE_USER_API'));
