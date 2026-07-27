DROP TABLE IF EXISTS peoples;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS articles;
CREATE TABLE IF NOT EXISTS peoples (
    people_id bigint auto_increment primary key,
    age       int          null,
    name      varchar(255) null
);

CREATE TABLE IF NOT EXISTS addresses (
    address_id bigint auto_increment primary key,
    people bigint null,
    street varchar(255) null,
    city varchar(255) null
);

CREATE TABLE IF NOT EXISTS articles (
    article_id bigint auto_increment primary key,
    people_id bigint null,
    title varchar(255) null,
    content text null
);