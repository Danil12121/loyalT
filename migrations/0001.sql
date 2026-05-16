DROP TABLE IF EXISTS partner_statistics CASCADE;
DROP TABLE IF EXISTS client_loyalty_data CASCADE;
DROP TABLE IF EXISTS partner_loyalty_rules CASCADE;

CREATE TABLE partner_loyalty_rules (
    id VARCHAR(255) PRIMARY KEY,
    type VARCHAR(50) NOT NULL CHECK (type IN ('CASHBACK', 'STAMP_CARD')),
    value DECIMAL(10, 2) NOT NULL
);

CREATE TABLE client_loyalty_data (
    client_id INT NOT NULL,
    partner_id VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.00,
    loyalty_type VARCHAR(50) NOT NULL CHECK (loyalty_type IN ('CASHBACK', 'STAMP_CARD')),
    curr_value INT NOT NULL DEFAULT 0,
    max_value_or_percent INT NOT NULL,
    PRIMARY KEY (client_id, partner_id),
    FOREIGN KEY (partner_id) REFERENCES partner_loyalty_rules(id) ON DELETE CASCADE
);

CREATE TABLE partner_statistics (
    partner_id VARCHAR(255) PRIMARY KEY,
    total_clients INT DEFAULT 0,
    total_transactions INT DEFAULT 0,
    new_clients INT DEFAULT 0,
    date DATE NOT NULL,
    FOREIGN KEY (partner_id) REFERENCES partner_loyalty_rules(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    partner_id VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX idx_users_login ON users(login);

CREATE INDEX idx_client_loyalty_client ON client_loyalty_data(client_id);
CREATE INDEX idx_client_loyalty_partner ON client_loyalty_data(partner_id);
CREATE INDEX idx_partner_stats_date ON partner_statistics(date);
CREATE INDEX idx_partner_stats_total_clients ON partner_statistics(total_clients DESC);

INSERT INTO partner_loyalty_rules (id, type, value) VALUES
('Кофейня 1', 'CASHBACK', 10.0),
('Кофейня 2', 'CASHBACK', 15.0),
('Барбершоп 2', 'STAMP_CARD', 1.0),
('Суши-маркет', 'CASHBACK', 5.0),
('Фитнес-клуб', 'STAMP_CARD', 1.0),
('Аптека 24', 'CASHBACK', 3.0),
('Пиццерия', 'STAMP_CARD', 1.0),
('Химчистка', 'CASHBACK', 7.0),
('Книжный магазин', 'STAMP_CARD', 1.0),
('Зоомагазин', 'CASHBACK', 8.0);