INSERT INTO main_educations (id, cv_id, sequence_number, period_from, period_to, present_time, institution, faculty, specialty)
VALUES (1, 2, 1, '2010-01-01', '2015-01-01', false, 'GSU', 'Faculty Name', 'Specialty Name'),
       (2, 2, 2, '2015-01-01', '2020-01-01', false, 'Institution Name', 'Faculty Name', 'Specialty Name');


INSERT INTO courses (id, cv_id, sequence_number, period_from, period_to, present_time, school, course_name, description, certificate_url)
VALUES (1, 2, 1, '2020-01-01', '2020-05-01', false, 'IT-ACADEMY', 'Course Name', 'Description', 'http://example.com/link'),
       (2, 2, 2, '2021-01-01', '2021-05-01', false, 'School Name', 'Course Name', 'Description', 'http://example.com/link');
