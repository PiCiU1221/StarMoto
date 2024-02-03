-- Initial data insertions and some sample data
-- Example cars won't be inserted if there are fewer than 3 users registered

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
VALUES ('Gasoline'),
       ('Gasoline + CNG'),
       ('Gasoline + LPG'),
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
       (6),
       (7),
       (8),
       (9);

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

-- Sample cars insertions with features

-- First car

-- Insert the first car and get its ID
WITH inserted_cars AS (
INSERT INTO cars (vin, seller_id, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, price, mileage, engine_power, engine_capacity, is_damaged, created_at, updated_at)
VALUES
    ('ABC12345678901234', 1, 1, 5, 8, 6, 1, 1, 2, 4, 5, 2019, 30000, 40000, 180, 2200, FALSE, CURRENT_TIMESTAMP, NULL)
    RETURNING car_id
    ),

-- Insert car features
    inserted_features AS (
INSERT INTO car_features_junction (car_id, feature_id)
SELECT car_id, feature_id
FROM inserted_cars
    CROSS JOIN (VALUES (1), (2), (3)) AS features(feature_id)
    ),

-- Insert images into car_image_urls table and associate them with the car
    inserted_images AS (
INSERT INTO car_image_urls (image_url)
VALUES
    ('https://iili.io/Ja9nYej.jpg'),
    ('https://iili.io/Jad72se.jpg')
    RETURNING image_id
    )

INSERT INTO car_image_urls_junction (car_id, image_id)
SELECT ic.car_id, ii.image_id
FROM inserted_cars ic
         CROSS JOIN inserted_images ii
WHERE ii.image_id IN (SELECT image_id FROM inserted_images);


-- Second car
-- Insert the second car and get its ID
WITH inserted_cars AS (
INSERT INTO cars (vin, seller_id, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, price, mileage, engine_power, engine_capacity, is_damaged, created_at, updated_at)
VALUES
    ('XYZ98765432109876', 2, 2, 9, 9, 11, 4, 1, 3, 5, 6, 2022, 40000, 20000, 250, 3000, FALSE, CURRENT_TIMESTAMP, NULL)
    RETURNING car_id
    ),

-- Insert car features
    inserted_features AS (
INSERT INTO car_features_junction (car_id, feature_id)
SELECT car_id, feature_id
FROM inserted_cars
    CROSS JOIN (VALUES (1), (2), (3)) AS features(feature_id)
    ),

-- Insert images into car_image_urls table and associate them with the car
    inserted_images AS (
INSERT INTO car_image_urls (image_url)
VALUES
    ('https://iili.io/Ja9nYea.jpg'),
    ('https://iili.io/Jad72sb.jpg')
    RETURNING image_id
    )

INSERT INTO car_image_urls_junction (car_id, image_id)
SELECT ic.car_id, ii.image_id
FROM inserted_cars ic
         CROSS JOIN inserted_images ii
WHERE ii.image_id IN (SELECT image_id FROM inserted_images);


-- Third car
-- Insert the third car and get its ID
WITH inserted_cars AS (
INSERT INTO cars (vin, seller_id, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, price, mileage, engine_power, engine_capacity, is_damaged, created_at, updated_at)
VALUES
    ('PQR56789012345678', 3, 5, 16, 6, 2, 1, 2, 1, 3, 4, 2021, 35000, 30000, 200, 2500, FALSE, CURRENT_TIMESTAMP, NULL)
    RETURNING car_id
    ),

-- Insert car features
    inserted_features AS (
INSERT INTO car_features_junction (car_id, feature_id)
SELECT car_id, feature_id
FROM inserted_cars
    CROSS JOIN (VALUES (1), (2), (3)) AS features(feature_id)
    ),

-- Insert images into car_image_urls table and associate them with the car
    inserted_images AS (
INSERT INTO car_image_urls (image_url)
VALUES
    ('https://iili.io/Ja9nYec.jpg'),
    ('https://iili.io/Jad72sd.jpg')
    RETURNING image_id
    )

INSERT INTO car_image_urls_junction (car_id, image_id)
SELECT ic.car_id, ii.image_id
FROM inserted_cars ic
         CROSS JOIN inserted_images ii
WHERE ii.image_id IN (SELECT image_id FROM inserted_images);


-- Fourth car
-- Insert the fourth car and get its ID
WITH inserted_cars AS (
INSERT INTO cars (vin, seller_id, make_id, model_id, body_type_id, color_id, fuel_type_id, transmission_type_id, drivetrain_id, doors_id, seats_id, production_year, price, mileage, engine_power, engine_capacity, is_damaged, created_at, updated_at)
VALUES
    ('LMN45678901234567', 3, 7, 20, 7, 4, 2, 1, 2, 5, 7, 2020, 28000, 45000, 160, 1800, FALSE, CURRENT_TIMESTAMP, NULL)
    RETURNING car_id
    ),

-- Insert car features
    inserted_features AS (
INSERT INTO car_features_junction (car_id, feature_id)
SELECT car_id, feature_id
FROM inserted_cars
    CROSS JOIN (VALUES (1), (2), (3)) AS features(feature_id)
    ),

-- Insert images into car_image_urls table and associate them with the car
    inserted_images AS (
INSERT INTO car_image_urls (image_url)
VALUES
    ('https://iili.io/Ja9nYee.jpg'),
    ('https://iili.io/Jad72sf.jpg')
    RETURNING image_id
    )

INSERT INTO car_image_urls_junction (car_id, image_id)
SELECT ic.car_id, ii.image_id
FROM inserted_cars ic
         CROSS JOIN inserted_images ii
WHERE ii.image_id IN (SELECT image_id FROM inserted_images);