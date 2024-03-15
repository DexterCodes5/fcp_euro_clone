DROP TABLE IF EXISTS kit_part;
DROP TABLE IF EXISTS part_oe_number;
DROP TABLE IF EXISTS oe_number;
DROP TABLE IF EXISTS part_vehicle;
DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS part;
DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle(
    id serial PRIMARY KEY,
    year_from INT NOT NULL,
    year_to INT NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    sub_model VARCHAR(50) NOT NULL,
    body VARCHAR(50) NOT NULL,
    engine VARCHAR(250) NOT NULL,
    transmission VARCHAR(250)
);

CREATE TABLE brand(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    img VARCHAR
);

CREATE TABLE part(
    id serial PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    url VARCHAR(250) NOT NULL,
    brand_id INT NOT NULL,
    sku VARCHAR(250) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    quality VARCHAR(50) NOT NULL,
    mfg_numbers VARCHAR(50)[] NOT NULL,
    kit BOOLEAN NOT NULL,
    made_in VARCHAR(50),
    product_information TEXT,
    category1 VARCHAR(50) NOT NULL,
    category2 VARCHAR(50) NOT NULL,
    category3 VARCHAR(50) NOT NULL,
    img VARCHAR[],
    CONSTRAINT fk_part_brand FOREIGN KEY (brand_id) REFERENCES brand(id)
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

CREATE TABLE part_vehicle(
    part_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    PRIMARY KEY(part_id, vehicle_id),
    CONSTRAINT fk_part_vehicle FOREIGN KEY (part_id) REFERENCES part(id),
    CONSTRAINT fk_vehicle_part FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);

CREATE TABLE kit_part(
    kit_id INT NOT NULL,
    part_id INT NOT NULL,
    PRIMARY KEY(kit_id, part_id),
    CONSTRAINT fk_kit_part_kit FOREIGN KEY (kit_id) REFERENCES part(id),
    CONSTRAINT fk_kit_part_part FOREIGN KEY (part_id) REFERENCES part(id)
);

CREATE TABLE image(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    file_path VARCHAR NOT NULL,
    part_id INT,
    CONSTRAINT fk_image_part FOREIGN KEY (part_id) REFERENCES part(id)
);
