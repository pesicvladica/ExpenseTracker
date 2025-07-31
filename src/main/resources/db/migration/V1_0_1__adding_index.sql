ALTER TABLE users
    ADD INDEX idx_users_username (username),
    ADD INDEX idx_users_email (email);

ALTER TABLE transactions
    ADD INDEX idx_transactions_id (id);