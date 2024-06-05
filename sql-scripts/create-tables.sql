DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS access_token_seq;
DROP SEQUENCE IF EXISTS refresh_token_seq;
DROP SEQUENCE IF EXISTS review_seq;

DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS customer_order;
DROP TABLE IF EXISTS delivery;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS kit_part;
DROP TABLE IF EXISTS part_oe_number;
DROP TABLE IF EXISTS oe_number;
DROP TABLE IF EXISTS engine_part;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS vehicle_part;
DROP TABLE IF EXISTS part;
DROP TABLE IF EXISTS category_bot;
DROP TABLE IF EXISTS category_mid;
DROP TABLE IF EXISTS category_top;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS vehicle_body_engine_transmission;
DROP TABLE IF EXISTS transmission;
DROP TABLE IF EXISTS vehicle_body_engine;
DROP TABLE IF EXISTS engine;
DROP TABLE IF EXISTS vehicle_body;
DROP TABLE IF EXISTS body;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS base_vehicle;
DROP TABLE IF EXISTS make;
DROP TABLE IF EXISTS access_token;
DROP TABLE IF EXISTS refresh_token;
DROP TABLE IF EXISTS reset_password_token;
DROP TABLE IF EXISTS _user;

CREATE TABLE _user(
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(68) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    email_verification_token VARCHAR
);

CREATE TABLE access_token(
    id SERIAL PRIMARY KEY,
    access_token VARCHAR NOT NULL,
    revoked BOOLEAN NOT NULL,
    expired BOOLEAN NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_access_token_user FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE refresh_token(
    id SERIAL PRIMARY KEY,
    refresh_token VARCHAR NOT NULL,
    revoked BOOLEAN NOT NULL,
    expired BOOLEAN NOT NULL,
    user_id INT NOT NULL,
    remember_me BOOLEAN NOT NULL,
    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE reset_password_token(
    token VARCHAR NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    active BOOLEAN NOT NULL,
    PRIMARY KEY(token, user_id),
    CONSTRAINT fk_reset_password_token_user FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE make(
    id SERIAL PRIMARY KEY,
    make VARCHAR(200) NOT NULL
);

CREATE TABLE base_vehicle(
    id SERIAL PRIMARY KEY,
    year INT NOT NULL,
    make_id INT NOT NULL,
    model VARCHAR(200) NOT NULL,
    CONSTRAINT fk_base_vehicle_make FOREIGN KEY (make_id) REFERENCES make(id)
);

CREATE TABLE vehicle(
    id SERIAL PRIMARY KEY,
    base_vehicle_id INT NOT NULL,
    sub_model VARCHAR(200) NOT NULL,
    CONSTRAINT fk_vehicle_base_vehicle FOREIGN KEY (base_vehicle_id) REFERENCES base_vehicle(id)
);

CREATE TABLE body(
    id SERIAL PRIMARY KEY,
    body VARCHAR(200) NOT NULL
);

CREATE TABLE vehicle_body(
    vehicle_id INT NOT NULL,
    body_id INT NOT NULL,
    PRIMARY KEY(vehicle_id, body_id),
    CONSTRAINT fk_vehicle_body_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_vehicle_body_body FOREIGN KEY (body_id) REFERENCES body(id)
);

CREATE TABLE engine(
    id SERIAL PRIMARY KEY,
    engine VARCHAR(200) NOT NULL
);

CREATE TABLE vehicle_body_engine(
    vehicle_id INT NOT NULL,
    body_id INT NOT NULL,
    engine_id INT NOT NULL,
    PRIMARY KEY(vehicle_id, body_id, engine_id),
    CONSTRAINT fk_vehicle_body_engine_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_vehicle_body_engine_body FOREIGN KEY (body_id) REFERENCES body(id),
    CONSTRAINT fk_vehicle_body_engine_engine FOREIGN KEY (engine_id) REFERENCES engine(id)
);

CREATE TABLE transmission(
    id SERIAL PRIMARY KEY,
    transmission VARCHAR(200)
);

CREATE TABLE vehicle_body_engine_transmission(
    vehicle_id INT NOT NULL,
    body_id INT NOT NULL,
    engine_id INT NOT NULL,
    transmission_id INT NOT NULL,
    PRIMARY KEY(vehicle_id, body_id, engine_id, transmission_id),
    CONSTRAINT fk_vehicle_body_engine_transmission_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_vehicle_body_engine_transmission_body FOREIGN KEY (body_id) REFERENCES body(id),
    CONSTRAINT fk_vehicle_body_engine_transmission_engine FOREIGN KEY (engine_id) REFERENCES engine(id),
    CONSTRAINT fk_vehicle_body_engine_transmission_transmission FOREIGN KEY (transmission_id) REFERENCES transmission(id)
);

CREATE TABLE brand(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    img VARCHAR,
    about_html VARCHAR
);

CREATE TABLE category_top(
    id SERIAL PRIMARY KEY,
    category_top VARCHAR(50),
    UNIQUE(category_top)
);

CREATE TABLE category_mid(
    id SERIAL PRIMARY KEY,
    category_top_id INT NOT NULL,
    category_mid VARCHAR(50),
    UNIQUE(category_mid),
    CONSTRAINT fk_category_mid_top FOREIGN KEY (category_top_id) REFERENCES category_top(id)
);

CREATE TABLE category_bot(
    id SERIAL PRIMARY KEY,
    category_mid_id INT NOT NULL,
    category_bot VARCHAR(50),
    UNIQUE(category_bot),
    CONSTRAINT fk_category_bot_mid FOREIGN KEY (category_mid_id) REFERENCES category_mid(id)
);

CREATE TABLE part(
    id serial PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    url VARCHAR(250) NOT NULL,
    brand_id INT NOT NULL,
    sku VARCHAR(250) NOT NULL,
    fcp_euro_id INT,
    quantity INT NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    quality VARCHAR(50) NOT NULL,
    mfg_numbers VARCHAR(50)[] NOT NULL,
    kit BOOLEAN NOT NULL,
    made_in VARCHAR(50),
    category_bot_id INT NOT NULL,
    img VARCHAR[],
    universal BOOLEAN NOT NULL,
    product_information_html VARCHAR,
    CONSTRAINT fk_part_brand FOREIGN KEY (brand_id) REFERENCES brand(id),
    CONSTRAINT fk_part_category_bot FOREIGN KEY (category_bot_id) REFERENCES category_bot(id)
);

CREATE TABLE vehicle_part(
    vehicle_id INT NOT NULL,
    part_id INT NOT NULL,
    comment VARCHAR,
    PRIMARY KEY(vehicle_id, part_id),
    CONSTRAINT fk_vehicle_part_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_vehicle_part_part FOREIGN KEY (part_id) REFERENCES part(id)
);

CREATE TABLE engine_part(
    engine_id INT NOT NULL,
    part_id INT NOT NULL,
    PRIMARY KEY(engine_id, part_id),
    CONSTRAINT fk_engine_part_engine FOREIGN KEY (engine_id) REFERENCES engine(id),
    CONSTRAINT fk_engine_part_part FOREIGN KEY (part_id) REFERENCES part(id)
);

CREATE TABLE oe_number(
    id serial PRIMARY KEY,
    oe_number VARCHAR(50) NOT NULL
);

CREATE TABLE part_oe_number(
    part_id INT NOT NULL,
    oe_number_id INT NOT NULL,
    PRIMARY KEY(part_id, oe_number_id),
    CONSTRAINT fk_part_oe_number_part FOREIGN KEY (part_id) REFERENCES part(id),
    CONSTRAINT fk_part_oe_number_oe_number FOREIGN KEY (oe_number_id) REFERENCES oe_number(id)
);

CREATE TABLE kit_part(
    id SERIAL PRIMARY KEY,
    kit_id INT NOT NULL,
    part_id INT NOT NULL,
    quantity INT NOT NULL,
    UNIQUE(kit_id, part_id),
    CONSTRAINT fk_kit_part_kit FOREIGN KEY (kit_id) REFERENCES part(id),
    CONSTRAINT fk_kit_part_part FOREIGN KEY (part_id) REFERENCES part(id)
);

CREATE TABLE image(
    id serial PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    type VARCHAR(20) NOT NULL,
    file_path VARCHAR UNIQUE NOT NULL,
    part_id INT,
    CONSTRAINT fk_image_part FOREIGN KEY (part_id) REFERENCES part(id)
);

CREATE TABLE review(
    id SERIAL PRIMARY KEY,
    part_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    name VARCHAR NOT NULL,
    rating INT NOT NULL,
    title VARCHAR NOT NULL,
    text VARCHAR NOT NULL,
    CONSTRAINT fk_review_part FOREIGN KEY (part_id) REFERENCES part(id),
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE address(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    street_address VARCHAR(100) NOT NULL,
    street_address_contd VARCHAR(100),
    company_name VARCHAR(50),
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    zip_code VARCHAR(25) NOT NULL,
    phone_number VARCHAR(25) NOT NULL
);

CREATE TABLE delivery(
    id SERIAL PRIMARY KEY,
    receive_text_notification BOOLEAN NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    receive_date DATE NOT NULL,
    address_id INT NOT NULL,
    CONSTRAINT fk_delivery_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE customer_order(
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    payment VARCHAR(50) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    shipping DECIMAL(12, 2) NOT NULL,
    tax DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    address_id INT NOT NULL,
    delivery_id INT NOT NULL,
    user_id INT,
    CONSTRAINT fk_customer_order_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_customer_order_delivery FOREIGN KEY (delivery_id) REFERENCES delivery(id),
    CONSTRAINT fk_customer_order_user FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE cart_item(
    id SERIAL PRIMARY KEY,
    customer_order_id INT NOT NULL,
    part_id INT NOT NULL,
    qty INT NOT NULL,
    CONSTRAINT fk_cart_item_customer_order FOREIGN KEY (customer_order_id) REFERENCES customer_order(id),
    CONSTRAINT fk_cart_item_part FOREIGN KEY (part_id) REFERENCES part(id)
);

-- SEQUENCES
CREATE SEQUENCE user_seq INCREMENT 1 START 6;
CREATE SEQUENCE access_token_seq INCREMENT 1 START 1;
CREATE SEQUENCE refresh_token_seq INCREMENT 1 START 1;
CREATE SEQUENCE review_seq INCREMENT 1 START 6;
