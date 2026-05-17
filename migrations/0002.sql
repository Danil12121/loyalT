DROP TABLE IF EXISTS transactions;

-- Создание таблицы
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGINT PRIMARY KEY,
    partner_id VARCHAR(255) NOT NULL,
    client_id Int32,
    amount Int32,
    timestamp DateTime,
    date Date DEFAULT toDate(timestamp)
) 
ENGINE = MergeTree()
PARTITION BY toYYYYMM(date)
ORDER BY (transaction_id, timestamp);

-- Индексы
ALTER TABLE transactions ADD INDEX idx_date (date) TYPE minmax GRANULARITY 1;

ALTER TABLE transactions ADD INDEX idx_amount (amount) TYPE minmax GRANULARITY 1;

ALTER TABLE transactions MATERIALIZE INDEX idx_date;
ALTER TABLE transactions MATERIALIZE INDEX idx_amount;

INSERT INTO transactions (transaction_id, partner_id, client_id, amount, timestamp) VALUES
(3, 'Барбершоп 2', 1, 1500, '2024-01-16 14:20:00'),
(4, 'Суши-маркет', 3, 850, '2024-01-16 19:00:00'),
(5, 'Фитнес-клуб', 1, 3500, '2024-01-17 09:15:00'),
(6, 'Аптека 24', 2, 450, '2024-01-17 16:30:00');