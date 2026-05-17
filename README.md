Инструкция по запуску

Стек технологий

Backend Java, Spring Boot
Базы данных PostgreSQL, ClickHouse
Кэш Redis
Брокер сообщений Apache Kafka
Контейнеризация Docker, Docker Compose
Сборка Maven 3.8+

Быстрый запуск
 1. Серверная часть
# 1. Запуск инфраструктуры
docker-compose up -d

# 2. Инициализация PostgreSQL
docker exec -i loyalty-postgres psql -U loyalAdmin -d loyalty < migrations/0001.sql

# 3. Инициализация ClickHouse
docker exec -i loyalty-clickhouse clickhouse-client --user admin --password password --database loyalty < migrations/0002.sql

# 4. Запуск приложения
mvn spring-boot:run

 2. Клиентская часть
В мобильном приложении
object RetrofitClient {
 private const val BASE_URL = "ссылка на бэкенд"

В веб-приложении
const API_BASE = "ссылка на бэкенд";
