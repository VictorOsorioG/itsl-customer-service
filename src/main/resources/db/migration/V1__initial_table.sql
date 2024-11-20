CREATE TABLE customer (
    id BIGSERIAL NOT NULL,
    name VARCHAR(64) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

-- REVERT
-- DROP TABLE customer
-- DELETE FROM flyway_schema_history WHERE version = '1.0';