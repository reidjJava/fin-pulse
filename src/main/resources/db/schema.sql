CREATE DATABASE clickhousedb;
CREATE USER dbuser IDENTIFIED WITH sha256_password BY 'password';
CREATE ROLE clickhousedb_role;
GRANT SELECT, INSERT, ALTER, CREATE, UPDATE, DROP, TRUNCATE, OPTIMIZE ON clickhousedb.* to clickhousedb_role;
GRANT clickhousedb_role to dbuser;

CREATE TABLE clickhousedb.currency
(
    currency          String,
    price             Decimal(19, 4),
    date              Date,
    time_last_updated DateTime
)
    ENGINE = MergeTree()
        ORDER BY (date);

CREATE TABLE clickhousedb.stocks
(
    name String,
    price        Decimal(18, 2)
) ENGINE = MergeTree()
      ORDER BY name;

CREATE TABLE clickhousedb.etf
(
    name String,
    price        Decimal(18, 2)
) ENGINE = MergeTree()
      ORDER BY name;

CREATE TABLE clickhousedb.ofz
(
    name String,
    price        Decimal(18, 2)
) ENGINE = MergeTree()
      ORDER BY name;

CREATE TABLE clickhousedb.bounds
(
    name String,
    price        Decimal(18, 2)
) ENGINE = MergeTree()
      ORDER BY name;