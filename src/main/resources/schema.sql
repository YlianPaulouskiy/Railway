DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users_passengers;
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS wagons;
DROP TABLE IF EXISTS routes_stations;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS routes;


CREATE TABLE routes
(
    id serial PRIMARY KEY
);

CREATE TABLE trains
(
    id bigserial PRIMARY KEY,
    code       varchar(256) NOT NULL UNIQUE ,
    type       varchar(256) NOT NULL, -- можно вынести в отдельную таблицу,
    route_id bigint NOT NULL UNIQUE REFERENCES routes (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE stations
(
    id   serial PRIMARY KEY,
    name varchar(256) NOT NULL UNIQUE
);

CREATE TABLE routes_stations
(
    id             bigserial PRIMARY KEY,
    route_id       bigint NOT NULL REFERENCES routes (id) ON DELETE CASCADE,
    station_id     int    NOT NULL REFERENCES stations (id) ON DELETE CASCADE,
    departure_time timestamp,
    arrival_time   timestamp
);

CREATE TABLE wagons
(
    id         bigserial PRIMARY KEY,
    no         smallint     NOT NULL,
    type       varchar(128) NOT NULL, -- можно вынести в отдельную таблицу
    train_code varchar(256) NOT NULL REFERENCES trains (code) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (no, train_code)
);

CREATE TABLE roles
(
    id   serial PRIMARY KEY,
    role varchar(128) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id        bigserial PRIMARY KEY,
    name      varchar(128) NOT NULL,
    last_name varchar(128) NOT NULL,
    email     varchar(256) NOT NULL UNIQUE,
    password  varchar(256) NOT NULL,
    role_id   int          NOT NULL REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE passengers
(
    id          bigserial PRIMARY KEY,
    name        varchar(128) NOT NULL,
    last_name   varchar(128) NOT NULL,
    middle_name varchar(128) NOT NULL,
    gender      char         NOT NULL,
    document    varchar(256) NOT NULL, -- можно вынести в отдельную таблицу
    document_no varchar(128) NOT NULL,
    UNIQUE (document, document_no)
);

create TABLE users_passengers
(
    id           bigserial PRIMARY KEY,
    user_id      bigint NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    passenger_id bigint NOT NULL REFERENCES passengers (id) ON DELETE CASCADE
);

CREATE TABLE orders
(
    id bigserial PRIMARY KEY,
    no      varchar(15) NOT NULL UNIQUE,
    status  varchar(128) NOT NULL, -- можно вынести в отдельную таблицу
    registration_time timestamp NOT NULL DEFAULT now(),
    payed_time timestamp,
    user_id bigint       NOT NULL REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tickets
(
    id       bigserial PRIMARY KEY,
    cost     decimal(8, 2) NOT NULL,
    order_id bigint REFERENCES orders (id) ON UPDATE CASCADE ON DELETE CASCADE,
    passenger_id bigint REFERENCES passengers(id)
);

CREATE TABLE seats
(
    id        bigserial PRIMARY KEY,
    no        smallint      NOT NULL,
    type      varchar(256)  NOT NULL, -- можно вынести в отдельную таблицу
    wagon_id  bigint        NOT NULL REFERENCES wagons (id) ON UPDATE CASCADE ON DELETE CASCADE,
    ticket_id bigint UNIQUE REFERENCES tickets (id) ON UPDATE CASCADE ON DELETE SET NULL
);

