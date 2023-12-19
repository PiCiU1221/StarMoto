-- Initial data insertions and some sample data

INSERT INTO car_color (color_name)
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

INSERT INTO car_condition (condition_name)
VALUES ('Undamaged'),
       ('Damaged');

INSERT INTO car_door_count (door_count)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9);

INSERT INTO car_drivetrain_type (drivetrain_type_name)
VALUES ('Rear-Wheel Drive'),
       ('Front-Wheel Drive'),
       ('All-Wheel Drive');

INSERT INTO car_features (feature_name)
VALUES ('Air Conditioning'),
       ('Power Windows'),
       ('Sunroof'),
       ('Cruise Control'),
       ('Bluetooth Connectivity'),
       ('Leather Seats'),
       ('Navigation System');

INSERT INTO car_fuel_type (fuel_type_name)
VALUES ('Gasoline'),
       ('Gasoline + CNG'),
       ('Gasoline + LPG'),
       ('Diesel'),
       ('Electric'),
       ('Ethanol'),
       ('Hybrid'),
       ('Hydrogen');

INSERT INTO car_make (make_name)
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

INSERT INTO car_model (model_name, make_id)
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

INSERT INTO car_seat_count (seat_count)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9);

INSERT INTO car_transmission_type (transmission_type_name)
VALUES ('Automatic'),
       ('Manual');

INSERT INTO car_type (type_name)
VALUES ('Small Car'),
       ('City Car'),
       ('Coupe'),
       ('Convertible'),
       ('Estate Car'),
       ('Compact Car'),
       ('Minivan'),
       ('Sedan'),
       ('SUV');

-- Sample cars insertions with features

-- First car
WITH inserted_cars AS (
    INSERT INTO cars (seller_id, model_id, type_id, condition_id, color_id, fuel_type_id, transmission_id, production_year, price, mileage, doors_id, seats_id, horsepower, engine_capacity, description, drivetrain_id)
    VALUES
        (1, 5, 2, 1, 8, 2, 2, 2019, 30000.00, 40000, 2, 2, 180, 2200, 'Luxury sedan with advanced features', 2)
    RETURNING car_id
)

-- Insert car features
INSERT INTO car_features_junction (car_id, feature_id)
SELECT ic.car_id, feature_id
FROM inserted_cars ic
CROSS JOIN (VALUES (1), (2), (3)) AS features(feature_id);

-- Second car
WITH inserted_cars AS (
    INSERT INTO cars (seller_id, model_id, type_id, condition_id, color_id, fuel_type_id, transmission_id, production_year, price, mileage, doors_id, seats_id, horsepower, engine_capacity, description, drivetrain_id)
    VALUES
        (1, 9, 5, 1, 12, 3, 2, 2022, 40000.00, 20000, 4, 4, 250, 3000, 'SUV for off-road adventures', 3)
    RETURNING car_id
)

-- Insert car features
INSERT INTO car_features_junction (car_id, feature_id)
SELECT ic.car_id, feature_id
FROM inserted_cars ic
CROSS JOIN (VALUES (2), (3), (4)) AS features(feature_id);

-- Third car
WITH inserted_cars AS (
    INSERT INTO cars (seller_id, model_id, type_id, condition_id, color_id, fuel_type_id, transmission_id, production_year, price, mileage, doors_id, seats_id, horsepower, engine_capacity, description, drivetrain_id)
    VALUES
        (2, 16, 3, 1, 1, 2, 1, 2021, 35000.00, 30000, 3, 3, 200, 2500, 'Compact car for city driving', 1)
    RETURNING car_id
)

-- Insert car features
INSERT INTO car_features_junction (car_id, feature_id)
SELECT ic.car_id, feature_id
FROM inserted_cars ic
CROSS JOIN (VALUES (2), (7)) AS features(feature_id);

-- Fourth car
WITH inserted_cars AS (
    INSERT INTO cars (seller_id, model_id, type_id, condition_id, color_id, fuel_type_id, transmission_id, production_year, price, mileage, doors_id, seats_id, horsepower, engine_capacity, description, drivetrain_id)
    VALUES
        (2, 20, 2, 2, 5, 3, 1, 2020, 28000.00, 45000, 5, 5, 160, 1800, 'Minivan with spacious interior', 2)
    RETURNING car_id
)

-- Insert car features
INSERT INTO car_features_junction (car_id, feature_id)
SELECT ic.car_id, feature_id
FROM inserted_cars ic
CROSS JOIN (VALUES (6), (7)) AS features(feature_id);