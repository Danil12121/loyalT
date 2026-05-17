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

INSERT INTO client_loyalty_data (client_id, partner_id, loyalty_type, curr_value, max_value_or_percent) VALUES
(1, 'Кофейня 1', 'CASHBACK', 0, 10),
(1, 'Фитнес-клуб', 'STAMP_CARD', 0, 10),
(2, 'Кофейня 1', 'CASHBACK', 0, 10);
