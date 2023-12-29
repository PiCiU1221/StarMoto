-- CREATE TABLE statements for every table

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(20)  NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    enabled    BOOLEAN                  DEFAULT TRUE
);

CREATE TABLE car_color
(
    color_id SERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL
);

CREATE TABLE car_condition
(
    condition_id   SERIAL PRIMARY KEY,
    condition_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_door_count
(
    door_count_id SERIAL PRIMARY KEY,
    door_count    INTEGER UNIQUE
);

CREATE TABLE car_drivetrain_type
(
    drivetrain_type_id   SERIAL PRIMARY KEY,
    drivetrain_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_feature
(
    feature_id   SERIAL PRIMARY KEY,
    feature_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE car_fuel_type
(
    fuel_type_id   SERIAL PRIMARY KEY,
    fuel_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_make
(
    make_id   SERIAL PRIMARY KEY,
    make_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_model
(
    model_id   SERIAL PRIMARY KEY,
    model_name VARCHAR(255) NOT NULL,
    make_id    INTEGER REFERENCES car_make ON DELETE CASCADE,
    UNIQUE (model_name, make_id)
);

CREATE TABLE car_seat_count
(
    seat_count_id SERIAL PRIMARY KEY,
    seat_count    INTEGER UNIQUE
);

CREATE TABLE car_transmission_type
(
    transmission_type_id   SERIAL PRIMARY KEY,
    transmission_type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_type
(
    type_id   SERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE car_images
(
    image_id   SERIAL PRIMARY KEY,
    image_url  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cars
(
    car_id          SERIAL PRIMARY KEY,
    seller_id       INTEGER REFERENCES users ON DELETE CASCADE,
    model_id        INTEGER REFERENCES car_model ON DELETE CASCADE,
    type_id         INTEGER REFERENCES car_type,
    condition_id    INTEGER REFERENCES car_condition,
    color_id        INTEGER REFERENCES car_color,
    fuel_type_id    INTEGER REFERENCES car_fuel_type,
    transmission_id INTEGER REFERENCES car_transmission_type,
    production_year INTEGER,
    price           INTEGER,
    mileage         INTEGER,
    doors_id        INTEGER REFERENCES car_door_count,
    seats_id        INTEGER REFERENCES car_seat_count,
    horsepower      INTEGER,
    engine_capacity INTEGER,
    description     TEXT,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    active          BOOLEAN                  DEFAULT true,
    drivetrain_id   INTEGER REFERENCES car_drivetrain_type
);

CREATE TABLE car_features_junction
(
    car_id     INTEGER NOT NULL REFERENCES cars ON DELETE CASCADE,
    feature_id INTEGER NOT NULL REFERENCES car_features ON DELETE CASCADE,
    PRIMARY KEY (car_id, feature_id)
);

CREATE TABLE car_images_junction
(
    car_id   INTEGER REFERENCES cars (car_id) ON DELETE CASCADE,
    image_id INTEGER REFERENCES car_images (image_id) ON DELETE CASCADE,
    PRIMARY KEY (car_id, image_id)
);