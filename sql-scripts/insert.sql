INSERT INTO vehicle VALUES
(1, 2000, 2005, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 M54 Siemens MS 43 MFI 225HP', null),
(2, 2000, 2005, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 M54 Siemens MS 43 MFI 225HP', '5-speed Manual S5D310Z by ZF'),
(3, 2000, 2005, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 M54 Siemens MS 43 MFI 225HP', '5-speed Automatic 5HP-19 by ZF'),
(4, 2005, 2007, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 N52 Siemens MS 70 MFI 258HP', null),
(5, 2005, 2007, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 N52 Siemens MS 70 MFI 258HP', '6-speed Manual S6-37BZ by ZF'),
(6, 2005, 2007, 'BMW', '330i', 'Base', '4-dr Sedan', '3.0L L6 N52 Siemens MS 70 MFI 258HP', '6-speed Automatic A6HP19Z by ZF');

INSERT INTO brand(id, name, img) VALUES
(1, 'Bosch', 'http://localhost:8080/api/v1/images/download/bosch'),
(2, 'Mann', 'http://localhost:8080/api/v1/images/download/mann');

INSERT INTO part(id, title, url, brand_id, sku, quantity, price, quality, mfg_numbers, kit, made_in, product_information, category1,
category2, category3, img) VALUES
(1, 'BMW Mini Direct Ignition Coil - Bosch 0221504470', 'bmw-mini-direct-ignition-coil-bosch-0221504470', 1, 'BOS-0221504470', 10, 20.69, 'OE', ARRAY ['0221504470', '00044'],
false, 'Slovakia', 'Info', 'Engine', 'Ignition', 'Ignition Coil', ARRAY ['http://localhost:8080/api/v1/images/download/BOS-0221504470',
'http://localhost:8080/api/v1/images/download/BOS-0221504470-2', 'http://localhost:8080/api/v1/images/download/BOS-0221504470-3']),
(2, 'BMW Ignition Service Kit - 12138647689KT3', 'bmw-ignition-service-kit-12138647689KT3', 1, 'KIT-12138647689KT3', 10, 196.77, 'OE', ARRAY ['0221504470', '00044'],
true, null, 'Info', 'Engine', 'Ignition', 'Ignition Tune-Up Kit', ARRAY ['http://localhost:8080/api/v1/images/download/KIT-12138647689KT3',
'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-2', 'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-3',
'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-4', 'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-5']),
(3, 'BMW Air Filter - Mann 13721744869', 'bmw-air-filter-mann-13721744869', 2, 'MAN-C251141', 10, 16.89, 'OE', ARRAY ['C25114/1'], false, 'Germany',
'Info', 'Air Intake', 'Air Filter', 'Engine Air Filter', ARRAY ['http://localhost:8080/api/v1/images/download/MAN-C251141']),
(4, 'BMW 120 Amp Alternator - Bosch AL0703N', 'bmw-120-amp-alternator-bosch-AL0703N', 1, 'BOS-1986A01099', 10, 294.99, 'OE', ARRAY ['0124515050', '0124515052', 'AL0703N'],
false, 'South Africa', 'Info', 'Engine', 'Alternator', 'Alternator Unit', ARRAY ['http://localhost:8080/api/v1/images/download/BOS-1986A01099',
'http://localhost:8080/api/v1/images/download/BOS-1986A01099-2', 'http://localhost:8080/api/v1/images/download/BOS-1986A01099-3']);

INSERT INTO oe_number(id, oe_number) VALUES
(1, '12138647689'),
(2, '12137594937'),
(3, '12137594938'),
(4, '13721744869'),
(5, '12317501599');

INSERT INTO part_oe_number(part_id, oe_number_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 4),
(4, 5);

INSERT INTO part_vehicle(part_id, vehicle_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);

INSERT INTO kit_part(kit_id, part_id) VALUES
(2, 1);

INSERT INTO image(id, name, type, file_path, part_id) VALUES
(1, 'bosch', 'image/png', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\brands\\bosch.png', null),
(2, 'mann', 'image/png', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\brands\\mann.png', null),
(3, 'BOS-0221504470', 'image/webp', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-0221504470\\BOS-0221504470.webp', 1),
(4, 'BOS-0221504470-2', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-0221504470\\BOS-0221504470-2.jpg', 1),
(5, 'BOS-0221504470-3', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-0221504470\\BOS-0221504470-3.jpg', 1),
(6, 'KIT-12138647689KT3', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\KIT-12138647689KT3\\KIT-12138647689KT3.jpg', 2),
(7, 'KIT-12138647689KT3-2', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-2.jpg', 2),
(8, 'KIT-12138647689KT3-3', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-3.jpg', 2),
(9, 'KIT-12138647689KT3-4', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-4.jpg', 2),
(10, 'KIT-12138647689KT3-5', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-5.jpg', 2),
(11, 'MAN-C251141', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\MAN-C251141\\MAN-C251141.jpg', 3),
(12, 'BOS-1986A01099', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-1986A01099\\BOS-1986A01099.jpg', 4),
(13, 'BOS-1986A01099-2', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-1986A01099\\BOS-1986A01099-2.jpg', 4),
(14, 'BOS-1986A01099-3', 'image/jpeg', 'C:\\Users\\dimitar_tanchev\\Documents\\fcp-euro-full-stack\\images\\BOS-1986A01099\\BOS-1986A01099-3.jpg', 4);




