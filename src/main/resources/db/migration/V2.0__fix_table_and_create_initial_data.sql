ALTER TABLE customer
ADD CONSTRAINT name_UN UNIQUE (name);

ALTER TABLE customer
ALTER COLUMN active SET DEFAULT TRUE;

INSERT INTO customer (name)
VALUES ('Globant');

INSERT INTO customer (name)
VALUES ('UdeA');

-- REVERT
-- ALTER TABLE customer DROP CONSTRAINT name_UN;
-- ALTER TABLE customer ALTER COLUMN active SET DEFAULT TRUE;
-- DELETE FROM customer WHERE name = "Globant";
-- DELETE FROM customer WHERE name = "UdeA";
-- DELETE FROM flyway_schema_history WHERE version = '2.0';