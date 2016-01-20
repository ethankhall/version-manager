CREATE TABLE user_model (
    uuid          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR(255) NOT NULL,
    email_address VARCHAR(256) NOT NULL
);
