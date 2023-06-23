INSERT INTO additional_information (cv_id, additional_information, hobby)
VALUES
    (2, 'Additional information', 'Photography');
INSERT INTO awards (additional_information_id, date, description, issuer, title, link)
VALUES
    (2, '2010-05-01', 'Very important award', 'Issuer1', 'Title1', 'https://example.com/link1'),
    (2, '2020-01-01', 'Another very important award', 'Issuer2', 'Title2', 'https://example.com/link2');
