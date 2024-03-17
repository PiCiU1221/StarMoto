-- Initial data insertions and some sample data


-- Users
INSERT INTO users (username, email, password)
VALUES ('testowanie1', 'testowanie1@test.pl', '$2a$10$Eh.pdFOLVr6j9YUUMLrnFePxOuNLWC9dMdAk5LbftKEwJzt.O7mAG'),
       ('testowanie2', 'testowanie2@test.pl', '$2a$10$HfJsLZn.JMUubD.4ms0oze.ZlRI8T.ZrQLysWPPAKmeekPRcMJiAS'),
       ('testowanie3', 'testowanie3@test.pl', '$2a$10$BY10WEly8ipWszzYQY0uPuSYNghf0pUfnscTGCXy/B/VhtxVZmIR6');


-- Car attributes
INSERT INTO car_makes (make_name)
VALUES ('Volkswagen'),
       ('Ford'),
       ('Skoda'),
       ('Opel'),
       ('Toyota'),
       ('Renault'),
       ('Fiat'),
       ('Peugeot'),
       ('Audi'),
       ('BMW'),
       ('Mercedes-Benz'),
       ('Kia'),
       ('Hyundai'),
       ('Nissan'),
       ('Citroen');

INSERT INTO car_models (model_name, make_id)
VALUES ('Golf', 1),
       ('Polo', 1),
       ('Passat', 1),
       ('Focus', 2),
       ('Fiesta', 2),
       ('Kuga', 2),
       ('Octavia', 3),
       ('Fabia', 3),
       ('Superb', 3),
       ('Astra', 4),
       ('Corsa', 4),
       ('Insignia', 4),
       ('Corolla', 5),
       ('Yaris', 5),
       ('RAV4', 5),
       ('Clio', 6),
       ('Megane', 6),
       ('Captur', 6),
       ('500', 7),
       ('Panda', 7),
       ('Tipo', 7),
       ('208', 8),
       ('308', 8),
       ('3008', 8),
       ('A3', 9),
       ('A4', 9),
       ('Q5', 9),
       ('3 Series', 10),
       ('5 Series', 10),
       ('X3', 10),
       ('C-Class', 11),
       ('E-Class', 11),
       ('GLC', 11),
       ('Sportage', 12),
       ('Ceed', 12),
       ('Sorento', 12),
       ('i30', 13),
       ('Tucson', 13),
       ('Kona', 13),
       ('Qashqai', 14),
       ('Juke', 14),
       ('X-Trail', 14),
       ('C3', 15),
       ('C4', 15),
       ('Berlingo', 15);

INSERT INTO car_body_types (body_type_name)
VALUES ('Small Car'),
       ('City Car'),
       ('Coupe'),
       ('Convertible'),
       ('Estate Car'),
       ('Compact Car'),
       ('Minivan'),
       ('Sedan'),
       ('SUV');

INSERT INTO car_colors (color_name)
VALUES ('Beige'),
       ('White'),
       ('Blue'),
       ('Maroon'),
       ('Brown'),
       ('Black'),
       ('Red'),
       ('Purple'),
       ('Navy'),
       ('Other color'),
       ('Orange'),
       ('Silver'),
       ('Gray'),
       ('Green'),
       ('Gold'),
       ('Yellow');

INSERT INTO car_fuel_types (fuel_type_name)
VALUES ('Petrol'),
       ('Petrol + CNG'),
       ('Petrol + LPG'),
       ('Diesel'),
       ('Electric'),
       ('Ethanol'),
       ('Hybrid'),
       ('Hydrogen');

INSERT INTO car_transmission_types (transmission_type_name)
VALUES ('Automatic'),
       ('Manual');

INSERT INTO car_drivetrain_types (drivetrain_type_name)
VALUES ('Rear-Wheel Drive'),
       ('Front-Wheel Drive'),
       ('All-Wheel Drive');

INSERT INTO car_door_counts (door_count)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6);

INSERT INTO car_seat_counts (seat_count)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9);

INSERT INTO car_features (feature_name)
VALUES ('Air Conditioning'),
       ('Power Windows'),
       ('Sunroof'),
       ('Cruise Control'),
       ('Bluetooth Connectivity'),
       ('Leather Seats'),
       ('Navigation System');


-- Cars

-- First car
WITH inserted_car AS (
    INSERT INTO cars (vin, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, mileage, engine_power, engine_capacity, is_damaged)
        VALUES
            ('ABC12345678901234', 1, 3, 8, 6, 4, 1, 2, 4, 5, 2017, 40000, 180, 2200, FALSE)
            RETURNING car_id
    ),

    image_collection AS (
        INSERT INTO car_image_collections (car_id)
            SELECT car_id FROM inserted_car
            RETURNING collection_id
    ),

    inserted_images AS (
        INSERT INTO car_image_urls (collection_id, image_url)
            SELECT image_collection.collection_id, images.image_url
            FROM image_collection,
            (VALUES
                ('https://iili.io/Ja9nYej.jpg'),
                ('https://iili.io/Jad72se.jpg')
            ) AS images(image_url)
    )
INSERT INTO car_features_junction (car_id, feature_id)
SELECT inserted_car.car_id, features.feature_id
FROM inserted_car, (VALUES (1), (2), (3)) AS features(feature_id);


-- Second car
WITH inserted_car AS (
INSERT INTO cars (vin, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, mileage, engine_power, engine_capacity, is_damaged)
VALUES
    ('ABC12345678901235', 3, 7, 5, 12, 4, 2, 2, 4, 5, 2008, 105000, 105, 1896, FALSE)
    RETURNING car_id
    ),

    inserted_images AS (
INSERT INTO car_image_urls (car_id, image_url)
SELECT inserted_car.car_id, images.image_url
FROM inserted_car, (VALUES
    ('https://iili.io/Ja9nYmn.jpg'),
    ('https://iili.io/Jad72vc.jpg'))
    AS images(image_url)
    )

INSERT INTO car_features_junction (car_id, feature_id)
SELECT inserted_car.car_id, features.feature_id
FROM inserted_car, (VALUES (1), (2)) AS features(feature_id);


-- Third car
WITH inserted_car AS (
INSERT INTO cars (vin, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, mileage, engine_power, engine_capacity, is_damaged)
VALUES
    ('ABC12345678901236', 10, 28, 8, 2, 1, 2, 1, 4, 5, 2013, 180000, 200, 1996, FALSE)
    RETURNING car_id
    ),

    inserted_images AS (
INSERT INTO car_image_urls (car_id, image_url)
SELECT inserted_car.car_id, images.image_url
FROM inserted_car, (VALUES
    ('https://iili.io/Ja9nYcs.jpg'),
    ('https://iili.io/Jad72tv.jpg'))
    AS images(image_url)
    )

INSERT INTO car_features_junction (car_id, feature_id)
SELECT inserted_car.car_id, features.feature_id
FROM inserted_car, (VALUES (1), (2), (5), (6), (7)) AS features(feature_id);


-- Fourth car
WITH inserted_car AS (
INSERT INTO cars (vin, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, mileage, engine_power, engine_capacity, is_damaged)
VALUES
    ('ABC12345678901237', 4, 12, 8, 3, 4, 1, 2, 4, 5, 2011, 150000, 167, 1996, FALSE)
    RETURNING car_id
    ),

    inserted_images AS (
INSERT INTO car_image_urls (car_id, image_url)
SELECT inserted_car.car_id, images.image_url
FROM inserted_car, (VALUES
    ('https://iili.io/Ja9nYis.jpg'),
    ('https://iili.io/Jad72qv.jpg'))
    AS images(image_url)
    )

INSERT INTO car_features_junction (car_id, feature_id)
SELECT inserted_car.car_id, features.feature_id
FROM inserted_car, (VALUES (1), (2), (3), (5), (7)) AS features(feature_id);


-- Advert attributes
INSERT INTO regions (region_name)
VALUES ('Zachodniopomorskie'),
       ('Pomorskie'),
       ('Wielkopolskie');

INSERT INTO locations (region_id, postal_code, city)
VALUES (1,'73-110', 'Stargard'),
       (2, '80-180', 'Gdańsk'),
       (3, '60-003', 'Poznań');


-- Adverts

-- First advert
WITH inserted_advert AS (
INSERT INTO adverts (seller_id, car_id, title, description, location_id, price)
VALUES
    (1, 1, 'Volkswagen Passat', 'Really nice Passat. Buy it pls.', 1, 99000)
    RETURNING advert_id
    )

INSERT INTO advert_phone_numbers (advert_id, phone_number)
SELECT inserted_advert.advert_id, phone_numbers.phone_number
FROM inserted_advert, (VALUES
                           ('123456789'),
                           ('987654321'))
    AS phone_numbers(phone_number);


-- Second advert
WITH inserted_advert AS (
INSERT INTO adverts (seller_id, car_id, title, description, location_id, price)
VALUES
    (3, 2, 'Skoda Octavia', 'Nice Skodunia.', 2, 35000)
    RETURNING advert_id
    )

INSERT INTO advert_phone_numbers (advert_id, phone_number)
SELECT inserted_advert.advert_id, phone_numbers.phone_number
FROM inserted_advert, (VALUES
                           ('123123123'))
    AS phone_numbers(phone_number);


-- Third advert
WITH inserted_advert AS (
INSERT INTO adverts (seller_id, car_id, title, description, location_id, price)
VALUES
    (2, 3, 'BMW 3 Series', 'BMW wruuuum', 3, 45000)
    RETURNING advert_id
    )

INSERT INTO advert_phone_numbers (advert_id, phone_number)
SELECT inserted_advert.advert_id, phone_numbers.phone_number
FROM inserted_advert, (VALUES
                           ('321321321'))
    AS phone_numbers(phone_number);


-- Fourth advert
WITH inserted_advert AS (
INSERT INTO adverts (seller_id, car_id, title, description, location_id, price)
VALUES
    (1, 4, 'Opel Insignia', 'Opel wow', 2, 43000)
    RETURNING advert_id
    )

INSERT INTO advert_phone_numbers (advert_id, phone_number)
SELECT inserted_advert.advert_id, phone_numbers.phone_number
FROM inserted_advert, (VALUES
                           ('432101234'))
    AS phone_numbers(phone_number);