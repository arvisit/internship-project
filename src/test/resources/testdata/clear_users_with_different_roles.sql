DELETE FROM users_roles WHERE user_id = (SELECT u.id FROM users u WHERE u.email = 'user-role@mail.com');
DELETE FROM users_roles WHERE user_id = (SELECT u.id FROM users u WHERE u.email = 'user-api-role@mail.com');

DELETE FROM roles WHERE name = 'ROLE_USER';
DELETE FROM roles WHERE name = 'ROLE_USER_API';

DELETE FROM users WHERE email = 'user-role@mail.com';
DELETE FROM users WHERE email = 'user-api-role@mail.com';