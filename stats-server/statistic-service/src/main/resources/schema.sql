DROP TABLE IF EXISTS hits;

CREATE TABLE IF NOT EXISTS hits(
    hit_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(100) NOT NULL,
    uri VARCHAR(100) NOT NULL,
    ip VARCHAR(15) NOT NULL,
    created timestamp);
