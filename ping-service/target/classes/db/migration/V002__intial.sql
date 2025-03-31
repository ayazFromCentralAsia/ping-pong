DROP TYPE IF EXISTS website_status;
DROP TYPE IF EXISTS logs_status;

DROP TABLE IF EXISTS monitoring_logs;

CREATE TYPE logs_status AS ENUM ('SUCCESS', 'FAILED');

CREATE TABLE monitoring_logs(
    id SERIAL PRIMARY KEY,
    website_id INTEGER NOT NULL,
    status_code INTEGER NOT NULL,
    response_time INTEGER NOT NULL,
    status logs_status NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

