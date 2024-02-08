-- Entire database schema


-- Users table
CREATE TABLE users
(
    user_id    BIGSERIAL PRIMARY KEY,
    username   VARCHAR(20)                            NOT NULL UNIQUE,
    email      VARCHAR(100)                           NOT NULL UNIQUE,
    password   VARCHAR(255)                           NOT NULL,
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT NULL,
    is_enabled BOOLEAN      DEFAULT TRUE              NOT NULL
);


-- Cars table

CREATE TABLE car_makes
(
    make_id   BIGSERIAL PRIMARY KEY,
    make_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_models
(
    model_id   BIGSERIAL PRIMARY KEY,
    model_name VARCHAR(255)                          NOT NULL,
    make_id    BIGINT REFERENCES car_makes (make_id) NOT NULL,
    UNIQUE (model_name, make_id),
    UNIQUE (model_id, make_id)
);

CREATE TABLE car_body_types
(
    body_type_id   BIGSERIAL PRIMARY KEY,
    body_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_colors
(
    color_id   BIGSERIAL PRIMARY KEY,
    color_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_fuel_types
(
    fuel_type_id   BIGSERIAL PRIMARY KEY,
    fuel_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_transmission_types
(
    transmission_type_id   BIGSERIAL PRIMARY KEY,
    transmission_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_drivetrain_types
(
    drivetrain_type_id   BIGSERIAL PRIMARY KEY,
    drivetrain_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_door_counts
(
    door_count_id BIGSERIAL PRIMARY KEY,
    door_count    BIGINT NOT NULL UNIQUE
);

CREATE TABLE car_seat_counts
(
    seat_count_id BIGSERIAL PRIMARY KEY,
    seat_count    BIGINT NOT NULL UNIQUE
);

CREATE TABLE cars
(
    car_id               BIGSERIAL PRIMARY KEY,
    vin                  VARCHAR(17)                                                     NOT NULL UNIQUE,
    make_id              BIGINT REFERENCES car_makes (make_id)                           NOT NULL,
    model_id             BIGINT REFERENCES car_models (model_id)                         NOT NULL,
    body_type_id         BIGINT REFERENCES car_body_types (body_type_id)                 NOT NULL,
    color_id             BIGINT REFERENCES car_colors (color_id)                         NOT NULL,
    fuel_type_id         BIGINT REFERENCES car_fuel_types (fuel_type_id)                 NOT NULL,
    transmission_type_id BIGINT REFERENCES car_transmission_types (transmission_type_id) NOT NULL,
    drivetrain_id        BIGINT REFERENCES car_drivetrain_types (drivetrain_type_id)     NOT NULL,
    doors_id             BIGINT REFERENCES car_door_counts (door_count_id)               NOT NULL,
    seats_id             BIGINT REFERENCES car_seat_counts (seat_count_id)               NOT NULL,
    production_year      INTEGER                                                         NOT NULL,
    mileage              INTEGER                                                         NOT NULL,
    engine_power         INTEGER                                                         NOT NULL,
    engine_capacity      INTEGER                                                         NOT NULL,
    is_damaged           BOOLEAN                                                         NOT NULL,
    CONSTRAINT fk_make_model
        FOREIGN KEY (make_id, model_id)
            REFERENCES car_models (make_id, model_id)
);

CREATE TABLE car_features
(
    feature_id   BIGSERIAL PRIMARY KEY,
    feature_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_image_urls
(
    image_id   BIGSERIAL PRIMARY KEY,
    image_url  VARCHAR(255)                           NOT NULL UNIQUE,
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE car_features_junction
(
    car_id     BIGINT REFERENCES cars (car_id) ON DELETE CASCADE NOT NULL,
    feature_id BIGINT REFERENCES car_features (feature_id)       NOT NULL,
    PRIMARY KEY (car_id, feature_id)
);

CREATE TABLE car_image_urls_junction
(
    car_id   BIGINT REFERENCES cars (car_id) ON DELETE CASCADE NOT NULL,
    image_id BIGINT REFERENCES car_image_urls (image_id)       NOT NULL,
    PRIMARY KEY (car_id, image_id)
);


-- Adverts table

CREATE TABLE regions
(
    region_id   BIGSERIAL PRIMARY KEY,
    region_name VARCHAR(50) UNIQUE
);

CREATE TABLE locations
(
    location_id BIGSERIAL PRIMARY KEY,
    region_id   BIGINT REFERENCES regions (region_id) NOT NULL,
    postal_code CHAR(6)                               NOT NULL,
    city        VARCHAR(100)                          NOT NULL
);

CREATE TABLE advert_phone_numbers
(
    phone_number_id BIGSERIAL PRIMARY KEY,
    phone_number    CHAR(9)
);

CREATE TABLE adverts
(
    advert_id   BIGSERIAL PRIMARY KEY,
    seller_id   BIGINT REFERENCES users (user_id)                 NOT NULL,
    car_id      BIGINT REFERENCES cars (car_id) ON DELETE CASCADE NOT NULL UNIQUE,
    title       VARCHAR(100)                                      NOT NULL,
    description TEXT                                              NOT NULL,
    location_id BIGINT REFERENCES locations (location_id)         NOT NULL,
    price       INTEGER                                           NOT NULL,
    created_at  TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP            NOT NULL,
    updated_at  TIMESTAMP(0) DEFAULT NULL
);

CREATE TABLE advert_phone_numbers_junction
(
    advert_id            BIGINT REFERENCES adverts (advert_id) ON DELETE CASCADE  NOT NULL,
    advert_phone_numbers BIGINT REFERENCES advert_phone_numbers (phone_number_id) NOT NULL,
    PRIMARY KEY (advert_id, advert_phone_numbers)
);