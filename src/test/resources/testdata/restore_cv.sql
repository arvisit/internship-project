UPDATE cvs
SET
    id = 2,
    user_id = 1,
    name = 'testName2',
    surname = 'testSurname2',
    country_id = 1,
    position_id = 1,
    city = 'Gomel',
    is_ready_to_relocate = true,
    is_ready_for_remote_work = true,
    image_id = null,
    status = 'DRAFT'
WHERE uuid = '123e4567-e89b-12d3-a456-426614174001';
