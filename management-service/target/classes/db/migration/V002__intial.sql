DROP TYPE IF EXISTS website_status;
DROP TYPE IF EXISTS logs_status;

DROP TABLE IF EXISTS monitoring_logs;
DROP TABLE IF EXISTS website;

CREATE TYPE website_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE logs_status AS ENUM ('SUCCESS', 'FAILED');

CREATE TABLE website(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    interval_time INTEGER NOT NULL,
    status website_status NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE monitoring_logs(
    id SERIAL PRIMARY KEY,
    website_id INTEGER NOT NULL,
    status_code INTEGER NOT NULL,
    response_time INTEGER NOT NULL,
    status logs_status NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

ALTER TABLE monitoring_logs ADD FOREIGN KEY (website_id) REFERENCES website(id);
