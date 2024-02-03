-- Select every car with names not ids with features and image urls

SELECT c.car_id,
       c.vin,
       c.seller_id,
       make.make_name                      AS make,
       model.model_name                    AS model,
       body_type.body_type_name            AS body_type,
       color.color_name                    AS color,
       fuel_type.fuel_type_name            AS fuel_type,
       transmission.transmission_type_name AS transmission_type,
       drivetrain.drivetrain_type_name     AS drivetrain_type,
       doors.door_count                    AS doors,
       seats.seat_count                    AS seats,
       c.production_year,
       c.price,
       c.mileage,
       c.engine_power,
       c.engine_capacity,
       c.is_damaged,
       c.created_at,
       c.updated_at,
       -- Get features
       array_agg(f.feature_name)           AS features,
       -- Get image URLs
       array_agg(i.image_url)              AS image_urls
FROM cars c
         LEFT JOIN car_makes make ON c.make_id = make.make_id
         LEFT JOIN car_models model ON c.model_id = model.model_id
         LEFT JOIN car_body_types body_type ON c.body_type_id = body_type.body_type_id
         LEFT JOIN car_colors color ON c.color_id = color.color_id
         LEFT JOIN car_fuel_types fuel_type ON c.fuel_type_id = fuel_type.fuel_type_id
         LEFT JOIN car_transmission_types transmission ON c.transmission_type_id = transmission.transmission_type_id
         LEFT JOIN car_drivetrain_types drivetrain ON c.drivetrain_id = drivetrain.drivetrain_type_id
         LEFT JOIN car_door_counts doors ON c.doors_id = doors.door_count_id
         LEFT JOIN car_seat_counts seats ON c.seats_id = seats.seat_count_id
         LEFT JOIN car_features_junction cf ON c.car_id = cf.car_id
         LEFT JOIN car_features f ON cf.feature_id = f.feature_id
         LEFT JOIN car_image_urls_junction ci ON c.car_id = ci.car_id
         LEFT JOIN car_image_urls i ON ci.image_id = i.image_id
GROUP BY c.car_id, make.make_name, model.model_name, body_type.body_type_name,
         color.color_name, fuel_type.fuel_type_name, transmission.transmission_type_name,
         drivetrain.drivetrain_type_name, doors.door_count, seats.seat_count;