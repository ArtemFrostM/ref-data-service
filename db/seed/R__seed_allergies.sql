-- Reference list of allergies. Repeatable migration: re-applied whenever this file changes.
INSERT INTO allergy (id, name, created_at, created_by, updated_at, updated_by)
VALUES ('8f1c0a6e-0b1a-4c1e-9a01-000000000001', 'Peanuts',    now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000002', 'Tree nuts',  now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000003', 'Milk',       now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000004', 'Eggs',       now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000005', 'Shellfish',  now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000006', 'Penicillin', now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000007', 'Latex',      now(), 'flyway', now(), 'flyway'),
       ('8f1c0a6e-0b1a-4c1e-9a01-000000000008', 'Pollen',     now(), 'flyway', now(), 'flyway')
    ON CONFLICT DO NOTHING;