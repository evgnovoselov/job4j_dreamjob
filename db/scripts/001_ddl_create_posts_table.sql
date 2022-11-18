CREATE TABLE post {
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    city_id INT,
    visible BOOLEAN,
    created TIMESTAMP
    };