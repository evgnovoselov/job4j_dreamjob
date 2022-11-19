CREATE TABLE IF NOT EXISTS candidate
(
    id SERIAL PRIMARY KEY,
    photo BYTEA,
    name TEXT,
    description TEXT,
    city_id INT,
    created TIMESTAMP
);