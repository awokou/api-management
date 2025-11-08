CREATE TABLE entreprise (
    id SERIAL PRIMARY KEY,
    social_reason VARCHAR(255) NOT NULL,
    siret VARCHAR(255) NOT NULL,
    siren VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employe (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    social_security_number VARCHAR(50),
    hiring_date DATE,
    contract_type VARCHAR(50),
    salary NUMERIC(15, 2),
    entreprise_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_entreprise FOREIGN KEY (entreprise_id) REFERENCES entreprise(id) ON DELETE CASCADE
);
