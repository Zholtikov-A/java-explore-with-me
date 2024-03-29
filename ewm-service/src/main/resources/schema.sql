DROP TABLE IF EXISTS events, locations, users, categories, requests, compilation_event, compilations, moderation CASCADE;

CREATE TABLE IF NOT EXISTS categories(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar(254) NOT NULL UNIQUE,
    user_name varchar(250));

CREATE TABLE IF NOT EXISTS locations(
    location_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL);

CREATE TABLE IF NOT EXISTS events(
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation varchar(2000),
    category BIGINT REFERENCES categories(category_id),
    confirmed_requests INT,
    created_on timestamp NOT NULL,
    description VARCHAR(7000) NOT NULL,
    event_date timestamp,
    initiator BIGINT REFERENCES users(user_id) ON DELETE SET NULL,
    location BIGINT REFERENCES locations(location_id) ON DELETE SET NULL,
    paid BOOLEAN NOT NULL,
    participant_limit INT DEFAULT 0,
    published_on timestamp,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR(10),
    title VARCHAR(120) NOT NULL,
    views INT DEFAULT 0);

CREATE TABLE IF NOT EXISTS requests(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created timestamp,
    event BIGINT REFERENCES events(event_id) ON DELETE CASCADE,
    requester BIGINT REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(10),
    UNIQUE (event, requester));

CREATE TABLE IF NOT EXISTS compilations(
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    events BIGINT REFERENCES events(event_id),
    pinned BOOLEAN,
    title VARCHAR(50) NOT NULL);

CREATE TABLE IF NOT EXISTS compilation_event (
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    PRIMARY KEY(compilation_id, event_id),
    FOREIGN KEY(compilation_id) REFERENCES compilations(compilation_id) ON DELETE CASCADE,
    FOREIGN KEY(event_id) REFERENCES events(event_id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS moderation(
        moderation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE,
        comment VARCHAR(500) NOT NULL,
        created TIMESTAMP NOT NULL);