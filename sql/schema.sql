-- CREATE TABLE statements for every table

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(20)                            NOT NULL,
    email      VARCHAR(100)                           NOT NULL,
    password   VARCHAR(255)                           NOT NULL,
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    is_enabled BOOLEAN      DEFAULT TRUE              NOT NULL
);

-- Reference tables for the cars table

CREATE TABLE car_makes
(
    make_id   SERIAL PRIMARY KEY,
    make_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_models
(
    model_id   SERIAL PRIMARY KEY,
    model_name VARCHAR(255)                                   NOT NULL,
    make_id    INTEGER REFERENCES car_makes ON DELETE CASCADE NOT NULL,
    UNIQUE (model_name, make_id)
);

CREATE TABLE car_body_types
(
    body_type_id   SERIAL PRIMARY KEY,
    body_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_colors
(
    color_id   SERIAL PRIMARY KEY,
    color_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_fuel_types
(
    fuel_type_id   SERIAL PRIMARY KEY,
    fuel_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_transmission_types
(
    transmission_type_id   SERIAL PRIMARY KEY,
    transmission_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_drivetrain_types
(
    drivetrain_type_id   SERIAL PRIMARY KEY,
    drivetrain_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_door_counts
(
    door_count_id SERIAL PRIMARY KEY,
    door_count    INTEGER NOT NULL UNIQUE
);

CREATE TABLE car_seat_counts
(
    seat_count_id SERIAL PRIMARY KEY,
    seat_count    INTEGER NOT NULL UNIQUE
);

-- Main cars table

CREATE TABLE cars
(
    car_id               SERIAL PRIMARY KEY,
    vin                  VARCHAR(17)                                     NOT NULL UNIQUE,
    seller_id            INTEGER REFERENCES users ON DELETE CASCADE      NOT NULL,
    make_id              INTEGER REFERENCES car_makes ON DELETE CASCADE  NOT NULL,
    model_id             INTEGER REFERENCES car_models ON DELETE CASCADE NOT NULL,
    body_type_id         INTEGER REFERENCES car_body_types               NOT NULL,
    color_id             INTEGER REFERENCES car_colors                   NOT NULL,
    fuel_type_id         INTEGER REFERENCES car_fuel_types               NOT NULL,
    transmission_type_id INTEGER REFERENCES car_transmission_types       NOT NULL,
    drivetrain_id        INTEGER REFERENCES car_drivetrain_types         NOT NULL,
    doors_id             INTEGER REFERENCES car_door_counts              NOT NULL,
    seats_id             INTEGER REFERENCES car_seat_counts              NOT NULL,
    production_year      INTEGER                                         NOT NULL,
    price                INTEGER                                         NOT NULL,
    mileage              INTEGER                                         NOT NULL,
    engine_power         INTEGER                                         NOT NULL,
    engine_capacity      INTEGER                                         NOT NULL,
    is_damaged           BOOLEAN                                         NOT NULL,
    created_at           TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP          NOT NULL,
    updated_at           TIMESTAMP(0) DEFAULT NULL
);

-- Tables that have their junction tables for the cars table

CREATE TABLE car_features
(
    feature_id   SERIAL PRIMARY KEY,
    feature_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_image_urls
(
    image_id   SERIAL PRIMARY KEY,
    image_url  VARCHAR(255)                           NOT NULL UNIQUE,
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Junction tables for the cars table

CREATE TABLE car_features_junction
(
    car_id     INTEGER REFERENCES cars ON DELETE CASCADE         NOT NULL,
    feature_id INTEGER REFERENCES car_features ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (car_id, feature_id)
);

CREATE TABLE car_image_urls_junction
(
    car_id   INTEGER REFERENCES cars ON DELETE CASCADE           NOT NULL,
    image_id INTEGER REFERENCES car_image_urls ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (car_id, image_id)
);