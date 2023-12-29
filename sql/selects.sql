-- Select cars without features
SELECT c.car_id,
       u.username                AS seller_username,
       m.make_name               AS make,
       mdl.model_name            AS model,
       t.type_name               AS type,
       cc.condition_name         AS condition,
       cr.color_name             AS color,
       ft.fuel_type_name         AS fuel_type,
       tt.transmission_type_name AS transmission_type,
       c.production_year,
       c.price,
       c.mileage,
       dc.door_count             AS doors,
       sc.seat_count             AS seats,
       c.horsepower,
       c.engine_capacity,
       c.description,
       c.created_at,
       c.updated_at,
       c.active,
       dt.drivetrain_type_name   AS drivetrain_type
FROM cars c
         JOIN
     users u ON c.seller_id = u.id
         JOIN
     car_model mdl ON c.model_id = mdl.model_id
         JOIN
     car_make m ON mdl.make_id = m.make_id
         JOIN
     car_type t ON c.type_id = t.type_id
         JOIN
     car_condition cc ON c.condition_id = cc.condition_id
         JOIN
     car_color cr ON c.color_id = cr.color_id
         JOIN
     car_fuel_type ft ON c.fuel_type_id = ft.fuel_type_id
         JOIN
     car_transmission_type tt ON c.transmission_id = tt.transmission_type_id
         JOIN
     car_door_count dc ON c.doors_id = dc.door_count_id
         JOIN
     car_seat_count sc ON c.seats_id = sc.seat_count_id
         JOIN
     car_drivetrain_type dt ON c.drivetrain_id = dt.drivetrain_type_id;

-- Select cars with features
SELECT c.car_id,
       u.username                 AS seller_username,
       m.make_name                AS make,
       mdl.model_name             AS model,
       t.type_name                AS type,
       cc.condition_name          AS condition,
       cr.color_name              AS color,
       ft.fuel_type_name          AS fuel_type,
       tt.transmission_type_name  AS transmission_type,
       c.production_year,
       c.price,
       c.mileage,
       dc.door_count              AS doors,
       sc.seat_count              AS seats,
       c.horsepower,
       c.engine_capacity,
       c.description,
       c.created_at,
       c.updated_at,
       c.active,
       dt.drivetrain_type_name    AS drivetrain_type,
       array_agg(cf.feature_name) AS features
FROM cars c
         JOIN
     users u ON c.seller_id = u.id
         JOIN
     car_model mdl ON c.model_id = mdl.model_id
         JOIN
     car_make m ON mdl.make_id = m.make_id
         JOIN
     car_type t ON c.type_id = t.type_id
         JOIN
     car_condition cc ON c.condition_id = cc.condition_id
         JOIN
     car_color cr ON c.color_id = cr.color_id
         JOIN
     car_fuel_type ft ON c.fuel_type_id = ft.fuel_type_id
         JOIN
     car_transmission_type tt ON c.transmission_id = tt.transmission_type_id
         JOIN
     car_door_count dc ON c.doors_id = dc.door_count_id
         JOIN
     car_seat_count sc ON c.seats_id = sc.seat_count_id
         JOIN
     car_drivetrain_type dt ON c.drivetrain_id = dt.drivetrain_type_id
         LEFT JOIN
     car_features_junction cfj ON c.car_id = cfj.car_id
         LEFT JOIN
     car_features cf ON cfj.feature_id = cf.feature_id
GROUP BY c.car_id, u.username, m.make_name, mdl.model_name, t.type_name, cc.condition_name, cr.color_name,
         ft.fuel_type_name, tt.transmission_type_name, c.production_year, c.price, c.mileage, dc.door_count,
         sc.seat_count, c.horsepower, c.engine_capacity, c.description, c.created_at, c.updated_at, c.active,
         dt.drivetrain_type_name;