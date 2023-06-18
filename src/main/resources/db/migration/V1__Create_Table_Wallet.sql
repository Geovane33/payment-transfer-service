CREATE TABLE transfer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wallet_origin BIGINT,
    wallet_destiny BIGINT,
    amount DECIMAL(10, 2),
    status VARCHAR(255),
    status_description VARCHAR(255),
    external_account BOOLEAN,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_update DATETIME
);
