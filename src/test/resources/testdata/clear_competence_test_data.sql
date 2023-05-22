DELETE FROM cvs_languages WHERE cv_id IN (SELECT id FROM cvs);
DELETE FROM cvs_skills WHERE cv_id IN (SELECT id FROM cvs);
DELETE FROM cvs WHERE id=1;