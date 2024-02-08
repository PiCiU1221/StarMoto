-- Example selects for the database


-- Select every car
SELECT c.car_id,
       c.vin,
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
       c.mileage,
       c.engine_power,
       c.engine_capacity,
       c.is_damaged,
       -- Get features
       array_agg(DISTINCT f.feature_name) AS features,
       -- Get image URLs
       array_agg(DISTINCT i.image_url)    AS image_urls
FROM cars c
         INNER JOIN car_makes make ON c.make_id = make.make_id
         INNER JOIN car_models model ON c.model_id = model.model_id
         INNER JOIN car_body_types body_type ON c.body_type_id = body_type.body_type_id
         INNER JOIN car_colors color ON c.color_id = color.color_id
         INNER JOIN car_fuel_types fuel_type ON c.fuel_type_id = fuel_type.fuel_type_id
         INNER JOIN car_transmission_types transmission ON c.transmission_type_id = transmission.transmission_type_id
         INNER JOIN car_drivetrain_types drivetrain ON c.drivetrain_id = drivetrain.drivetrain_type_id
         INNER JOIN car_door_counts doors ON c.doors_id = doors.door_count_id
         INNER JOIN car_seat_counts seats ON c.seats_id = seats.seat_count_id
         INNER JOIN car_features_junction cf ON c.car_id = cf.car_id
         INNER JOIN car_features f ON cf.feature_id = f.feature_id
         INNER JOIN car_image_urls_junction ci ON c.car_id = ci.car_id
         INNER JOIN car_image_urls i ON ci.image_id = i.image_id
GROUP BY c.car_id, make.make_name, model.model_name, body_type.body_type_name,
         color.color_name, fuel_type.fuel_type_name, transmission.transmission_type_name,
         drivetrain.drivetrain_type_name, doors.door_count, seats.seat_count;


-- Select every advertisement
SELECT  a.advert_id,
        u.username,
        a.title,
        a.description,
        l.city,
        a.price,
        -- Get phone numbers
        array_agg(DISTINCT phone_numbers.phone_number) AS phone_numbers,
        c.vin,
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
        c.mileage,
        c.engine_power,
        c.engine_capacity,
        c.is_damaged,
        -- Get features
        array_agg(DISTINCT f.feature_name) AS features,
        -- Get image URLs
        array_agg(DISTINCT i.image_url)    AS image_urls
FROM adverts a
        INNER JOIN users u ON a.seller_id = u.user_id
        INNER JOIN cars c ON a.car_id = c.car_id
        INNER JOIN locations l ON a.location_id = l.location_id
        INNER JOIN advert_phone_numbers_junction pnj ON a.advert_id = pnj.advert_id
        INNER JOIN advert_phone_numbers phone_numbers ON pnj.advert_phone_numbers = phone_numbers.phone_number_id
        INNER JOIN car_makes make ON c.make_id = make.make_id
        INNER JOIN car_models model ON c.model_id = model.model_id
        INNER JOIN car_body_types body_type ON c.body_type_id = body_type.body_type_id
        INNER JOIN car_colors color ON c.color_id = color.color_id
        INNER JOIN car_fuel_types fuel_type ON c.fuel_type_id = fuel_type.fuel_type_id
        INNER JOIN car_transmission_types transmission ON c.transmission_type_id = transmission.transmission_type_id
        INNER JOIN car_drivetrain_types drivetrain ON c.drivetrain_id = drivetrain.drivetrain_type_id
        INNER JOIN car_door_counts doors ON c.doors_id = doors.door_count_id
        INNER JOIN car_seat_counts seats ON c.seats_id = seats.seat_count_id
        INNER JOIN car_features_junction fj ON c.car_id = fj.car_id
        INNER JOIN car_features f ON fj.feature_id = f.feature_id
        INNER JOIN car_image_urls_junction ij ON c.car_id = ij.car_id
        INNER JOIN car_image_urls i ON ij.image_id = i.image_id
GROUP BY a.advert_id, u.username, a.title, a.description, l.city, a.price, c.car_id, make.make_name, model.model_name,
        body_type.body_type_name, color.color_name, fuel_type.fuel_type_name, transmission.transmission_type_name,
        drivetrain.drivetrain_type_name, doors.door_count, seats.seat_count;