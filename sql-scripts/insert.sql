INSERT INTO _user(id, email, username, password, role, active, email_verification_token) VALUES
(1, 'mitko@mail.com', 'mitko', '$2a$12$6RfpBqeOGXyWmVs0Ph7xK.l3cUzrlCqYDZHaXEjtP1/5G/GlsnxVy', 'USER', true, null),
(2, 'mitko1@mail.com', 'mitko1', '$2a$12$6RfpBqeOGXyWmVs0Ph7xK.l3cUzrlCqYDZHaXEjtP1/5G/GlsnxVy', 'USER', true, null),
(3, 'mitko2@mail.com', 'mitko2', '$2a$12$6RfpBqeOGXyWmVs0Ph7xK.l3cUzrlCqYDZHaXEjtP1/5G/GlsnxVy', 'USER', true, null),
(4, 'mitko3@mail.com', 'mitko3', '$2a$12$6RfpBqeOGXyWmVs0Ph7xK.l3cUzrlCqYDZHaXEjtP1/5G/GlsnxVy', 'USER', true, null),
(5, 'mitko4@mail.com', 'mitko4', '$2a$12$6RfpBqeOGXyWmVs0Ph7xK.l3cUzrlCqYDZHaXEjtP1/5G/GlsnxVy', 'USER', true, null);

INSERT INTO make VALUES
(1, 'BMW'),
(2, 'Mercedes-Benz'),
(3, 'Porsche');

INSERT INTO base_vehicle(id, year, make_id, model) VALUES
(1, 2001, 1, '325i'),
(2, 2001, 1, '330i'),
(3, 2002, 1, '325i'),
(4, 2002, 1, '330i'),
(5, 2003, 1, '325i'),
(6, 2003, 1, '330i'),
(7, 2004, 1, '325i'),
(8, 2004, 1, '330i'),
(9, 2005, 1, '325i'),
(10, 2005, 1, '330i'),
(11, 2006, 1, '325i'),
(12, 2006, 1, '330i');

INSERT INTO vehicle(id, base_vehicle_id, sub_model) VALUES
(1, 1, 'Base'),
(2, 2, 'Base'),
(3, 3, 'Base'),
(4, 4, 'Base'),
(5, 5, 'Base'),
(6, 6, 'Base'),
(7, 7, 'Base'),
(8, 8, 'Base'),
(9, 9, 'Base'),
(10, 10, 'Base'),
(11, 11, 'Base'),
(12, 12, 'Base');

INSERT INTO body(id, body) VALUES
(1, '4-dr Sedan'),
(2, '2-dr Coupe');

INSERT INTO vehicle_body(vehicle_id, body_id) VALUES
(2, 1),
(4, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(12, 1);

INSERT INTO engine(id, engine) VALUES
(1, '3.0L L6 M54 Siemens MS 43 MFI 225HP'),
(2, '3.0L L6 M54 Siemens MS 45 MFI 225HP'),
(3, '3.0L L6 N52 Siemens MS 70 MFI 258HP'),
(4, '2.5L L6 M54 Siemens MS MFI 184HP');

INSERT INTO vehicle_body_engine(vehicle_id, body_id, engine_id) VALUES
(2, 1, 1),
(4, 1, 1),
(6, 1, 1),
(7, 1, 4),
(8, 1, 2),
(9, 1, 4),
(10, 1, 2),
(12, 1, 3);

INSERT INTO transmission(id, transmission) VALUES
(1, '5-speed Manual S5D310Z by ZF'),
(2, '5-speed Automatic 5HP-19 by ZF'),
(3, '5-speed Automatic 5L40-E by General Motors'),
(4, '6-speed Manual S6S37BZ by ZF'),
(5, '6-speed Manual S6-37BZ by ZF'),
(6, '6-speed Automatic A6HP19Z by ZF');

INSERT INTO vehicle_body_engine_transmission(vehicle_id, body_id, engine_id, transmission_id) VALUES
(2, 1, 1, 1),
(2, 1, 1, 2),
(4, 1, 1, 1),
(4, 1, 1, 2),
(6, 1, 1, 3),
(6, 1, 1, 1),
(6, 1, 1, 2),
(7, 1, 4, 3),
(7, 1, 4, 2),
(8, 1, 2, 3),
(8, 1, 2, 4),
(8, 1, 2, 5),
(9, 1, 4, 3),
(9, 1, 4, 2),
(10, 1, 2, 3),
(10, 1, 2, 4),
(10, 1, 2, 5),
(12, 1, 3, 5),
(12, 1, 3, 6);

INSERT INTO brand(id, name, img, about_html) VALUES
(1, 'Bosch', 'http://localhost:8080/api/v1/images/download/bosch', '
<h4>About Bosch</h4>
<p>
    <a>Bosch</a> is one of the worlds leading suppliers of engine electronics and sensors. Their sensors and modules can be found in almost every modern car on the planet.
</p>
<p>
    Robert Bosch founded the “Workshop for Precision Mechanics and Electrical Engineering” in Stuttgart, Germany in 1886. Since the beginning Bosch has been a leader in innovation and development. They introduced fuel injection into mass production in 1967 laying the foundation for modern ignition electronics. Bosch brought lambda sensors to market in 1976, paving the way for cleaner exhaust gases and better fuel economy.
</p>
<p>
    With millions of parts and products in their product line, <a>Bosch</a> offers everything from brake pads to household tools and medical supplies. FCP Euro has partnered with <a>Bosch</a> for the 2019 TC America Championship. They are a sponsor on our 2018 Volkswagen GTI TCR race cars. We offer <a>Bosch''s</a> full line of OE and OEM components for your European vehicle.
</p>
'
),
(2, 'Mann', 'http://localhost:8080/api/v1/images/download/mann', null),
(3, 'NGK', 'http://localhost:8080/api/v1/images/download/ngk', null),
(4, 'Permatex', 'http://localhost:8080/api/v1/images/download/permatex', null),
(5, 'Denso', 'http://localhost:8080/api/v1/images/download/denso', null),
(6, 'LuK', 'http://localhost:8080/api/v1/images/download/luk', null),
(7, 'Hepu', 'http://localhost:8080/api/v1/images/download/hepu', null),
(8, 'Delphi', 'http://localhost:8080/api/v1/images/download/delphi', null),
(9, 'Odyssey Battery', null, null),
(10, 'Continental', 'http://localhost:8080/api/v1/images/download/continental', null);

INSERT INTO category_top(id, category_top) VALUES
(1, 'AC and Climate Control'),
(2, 'Air Intake'),
(3, 'Brake'),
(4, 'Clutch and Flywheel'),
(5, 'Cooling System'),
(6, 'Engine'),
(7, 'Supplies and Hardware');

INSERT INTO category_mid(id, category_top_id, category_mid) VALUES
(1, 1, 'AC Compressor'),
(2, 2, 'Air Filter'),
(3, 3, 'Brake Pads'),
(4, 4, 'Clutch Kits'),
(5, 5, 'Water Pump'),
(6, 6, 'Alternator'),
(7, 6, 'Battery'),
(8, 6, 'Drive Belts'),
(9, 6, 'Ignition'),
(10, 6, 'Spark Plugs'),
(11, 7, 'Lubricant and Grease');

INSERT INTO category_bot(id, category_mid_id, category_bot) VALUES
(1, 1, 'Compressor'),
(2, 2, 'Engine Air Filter'),
(3, 3, 'Disk Brake Pad'),
(4, 4, 'Clutch Kit'),
(5, 5, 'Engine Water Pump'),
(6, 6, 'Alternator Unit'),
(7, 7, 'Car Battery'),
(8, 8, 'Serpentine Belt'),
(9, 9, 'Ignition Coil'),
(10, 9, 'Ignition Coil Kits'),
(11, 9, 'Ignition Tune-Up Kit'),
(12, 10, 'Spark Plug'),
(13, 11, 'Dielectric Grease');

INSERT INTO part(id, title, url, brand_id, sku, fcp_euro_id, quantity, price, quality, mfg_numbers, kit, made_in,
category_bot_id, img, universal, product_information_html) VALUES
(1, 'BMW Mini Direct Ignition Coil - Bosch 0221504470', 'bmw-mini-direct-ignition-coil-bosch-0221504470', 1, 'BOS-0221504470',
509845, 10, 20.69, 'OE', ARRAY ['0221504470', '00044'],
false, 'Slovakia', 9, ARRAY ['http://localhost:8080/api/v1/images/download/BOS-0221504470',
'http://localhost:8080/api/v1/images/download/BOS-0221504470-2', 'http://localhost:8080/api/v1/images/download/BOS-0221504470-3'],
false, '
<div class="extended__brandDetails">
    <p>
        <strong>OEM replacement direct ignition coil</strong>
    </p>
    <p>
        Your BMW''s direct ignition coil provides the power in order for your spark plug to "spark". If your BMW is suffering from a misfire issue it is likely due to a worn ignition coil or bad spark plug. One way to test for a bad ignition coil is to swap the ignition coils and see if the misfire follows the misfire.
    </p>
    <p>
        We also recommend using dielectric grease when installing new ignition coils. It will protect the spark plug boot and create a water/dust tight seal. Great for added ignition performance.
    </p>
    <p>
        <strong>Note:</strong> This newly designed Bosch coil does not have a "snap" when pressing into place. The new design uses a contact spring on the spark plug tip that grips the spark plug just as good or better than the original design. When installing a new coil make sure each spark plug connector has a dab of dielectric grease to make sure the coil slips all the way down on to the plug and are firmly in place.
    </p>
    <p>
        <strong>Please Read:</strong>&nbsp;
        <a href="">New Coil Design (PDF)</a>
    </p>
    <hr />
</div>
<strong>Direct Ignition Coil</strong>
<p>
    Direct ignition coils are located directly over the top of the spark plug. In this ignition setup there is one ignition coil per spark plug and there are no spark plug wires. This ignition setup is often referred to as a coil on plug ignition system.
</p>
<p>
    Coil on plug or direct ignition systems are pretty much used on all modern cars due to packaging, performance, emissions, and maintenance reasons. This ignition system is essentially maintenance free with the exception of spark plug replacements and the occasional coil replacement when compared to a traditional ignition system.
</p>
<strong>
    Direct Ignition Coil Failure Symptoms
</strong>
<ul>
    <li>Reduced fuel efficiency</li>
    <li>Reduced power output</li>
    <li>Misfires</li>
</ul>
'
),
(2, 'BMW Ignition Service Kit - 12138647689KT3', 'bmw-ignition-service-kit-12138647689kt3', 1, 'KIT-12138647689KT3', null,
10, 196.77, 'OE', ARRAY ['0221504470', '00044'],
true, null, 11, ARRAY ['http://localhost:8080/api/v1/images/download/KIT-12138647689KT3',
'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-2', 'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-3',
'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-4', 'http://localhost:8080/api/v1/images/download/KIT-12138647689KT3-5'],
false, '
<div class="extended__brandDetails">
    <p>
        <strong>Ignition service kit featuring OEM Bosch ignition coils, NGK 3199 spark plugs, and dielectric grease</strong>
    </p>
    <p>
        Spark plugs and ignition coils are a pretty common overlooked item. This occurs because the components are out of site, out of mind so to speak: that is until your engine starts to have misfire codes.
    </p>
    <p>
        Spark plugs wear out over countless of thousands of ignition cycles. The wear results in misfires and poor ignition performance because the gap between the center electrode and ground electrode becomes too big. Once this occurs with a spark plug it will need to be replaced.
    </p>
    <p>
        Ignition coils provide the energy required for the spark plug to ignite the fuel mixtures. Over countless of thousands of ignition cylces the ignition coils will wear out. We typically see ignition coil boots wear out causing poor insulation between the spark plug and coil.
    </p>
</div>
'
),
(3, 'BMW Air Filter - Mann 13721744869', 'bmw-air-filter-mann-13721744869', 2, 'MAN-C251141', 72804, 10, 16.89, 'OE',
ARRAY ['C25114/1'], false, 'Germany', 2, ARRAY ['http://localhost:8080/api/v1/images/download/MAN-C251141'], false, '
<div class="extended__brandDetails">
    <p>
        Your BMW''s air filter is intended to clean the air entering the intake before it gets into the engine. Over the course of thousands of hours of operation the air filter can become clogged up by dust and other debris which will significantly decrease air flow into the engine. In addition, a clogged air filter can also allow smaller particles through into the engine causing additional wear and tear. This OEM replacement filter will drop into place and fit just like the original.
    </p>
</div>
<strong>
    Engine Air Filter
</strong>
<p>
    Like your home''s air filter, an engine''s air filter is there to filter out dirt, dust and other foreign matter. Did you know that most of the dirt in your engine enters in through a dirty air filter? Your engine''s air filter plays an important role as it prevents dirt from entering into the combustion chamber thus reducing the chances of internal damage and wear. A new air filter can increase airflow, improve overall engine performance, and in some cases add additional horsepower. Considering most of us drive in the harshest of conditions such as heavy idling in traffic, driving on dusty roads, and heavy towing, be sure to replace the air filter in your vehicle regularly.
</p>
<strong>
    Engine Air Filter Failure Symptoms
</strong>
<ul>
    <li>
        Clogged filter media
    </li>
    <li>
        Reduced engine performance
    </li>
    <li>
        Internal Engine Damage
    </li>
</ul>
'
),
(4, 'BMW 120 Amp Alternator - Bosch AL0703N', 'bmw-120-amp-alternator-bosch-al0703n', 1, 'BOS-1986A01099', 221800,
 10, 294.99, 'OE', ARRAY ['0124515050', '0124515052', 'AL0703N'],
false, 'South Africa', 6, ARRAY ['http://localhost:8080/api/v1/images/download/BOS-1986A01099',
'http://localhost:8080/api/v1/images/download/BOS-1986A01099-2', 'http://localhost:8080/api/v1/images/download/BOS-1986A01099-3'],
false, '
<div class="extended__brandDetails">
    <p>
        <strong>
            120 amp brand new alternator with rectangular 2 pin plug socket, referred to as a compact alternator
        </strong>
    </p>
    <p>
        If your BMW is running out of power (literally) it''s likely due to a worn or damaged alternator. The battery on board the car stores enough power to start the car while the alternator is responsible for powering the car while its running as well as maintaining the batter charge. If the alternator is not charging the battery you will run out of power since the battery alone can''t support all of your BMW''s functions.
    </p>
</div>
<strong>Alternator</strong>
<p>
    If your car is running out of power (literally) it''s likely due to a worn or failing alternator. Your vehicles car battery is responsible for storing enough energy to start the car while the alternator powers all the vehicles systems with the engine running while maintaining a battery charge. If your car dies while driving and the battery has little to no charge it''s most likely due to a failed or failing alternator. Before condemning an alternator you can perform a simple test with a DVOM (digital volt meter). Also make sure the belt which drives the alternator is properly tensioned as well.
</p>
<strong>Alternator Failure Symptoms</strong>
<ul>
    <li>
        Battery drains while car is running
    </li>
    <li>
        Low voltage generated while car is running
    </li>
    <li>
        Dim or flickering headlights
    </li>
    <li>
        Dim or flickering interior lights
    </li>
    <li>
        Electrical systems such as the radio, power windows, sunroof do not function
    </li>
</ul>
'
),
(5, 'BMW BKR6EQUP Spark Plug - NGK 3199', 'audi-bmw-mini-porsche-vw-spark-plug-ngk-bkr6equp', 3, 'NGK-3199', 19183, 10,
10.09, 'OE', ARRAY ['3199', 'BKR6EQUP'], false, 'Japan', 12,
ARRAY ['http://localhost:8080/api/v1/images/download/NGK-3199', 'http://localhost:8080/api/v1/images/download/NGK-3199-2'],
false, null),
(6, 'Permatex Dielectric Grease - Permatex 81150', 'dielectric-tune-up-grease-81150', 4, 'PMX-81150', 204666, 10, 6.59,
'Aftermarket', ARRAY ['81150'], false, 'United States', 13, ARRAY ['http://localhost:8080/api/v1/images/download/PMX-81150'],
true, null),
(7, 'BMW A/C Compressor - Denso 64526916232', 'bmw-a-c-compressor-oem-64526936883', 5, 'DEN-4711528', 98814, 10, 509.99,
'OE', ARRAY ['471-1528'], false, 'United States', 1, ARRAY ['http://localhost:8080/api/v1/images/download/DEN-4711528'],
false, null),
(8, 'BMW Disc Brake Pad - Bosch Quietcast BC683', 'bmw-brake-pad-set-bosch-quietcast-bc683', 1, 'BOS-BC683', 543289, 10, 40.79,
'Aftermarket', ARRAY ['BC683'], false, 'Mexico', 3, ARRAY ['http://localhost:8080/api/v1/images/download/BOS-BC683',
'http://localhost:8080/api/v1/images/download/BOS-BC683-2', 'http://localhost:8080/api/v1/images/download/BOS-BC683-3'],
false, null),
(9, 'BMW Clutch Kit - LuK 6243575000', 'bmw-clutch-kit-luk-oem-21207531556', 6, 'LUK-6243575000', 150212, 10, 334.99, 'OE',
ARRAY ['6243575000', '03-059'], true, 'Hungary', 4, ARRAY ['http://localhost:8080/api/v1/images/download/LUK-6243575000',
'http://localhost:8080/api/v1/images/download/LUK-6243575000-2', 'http://localhost:8080/api/v1/images/download/LUK-6243575000-3',
'http://localhost:8080/api/v1/images/download/LUK-6243575000-4'], false, null),
(10, 'BMW Water Pump - Hepu P472', 'bmw-water-pump-11517527910h', 7, 'HEP-P472', 98657, 10, 52.99, 'OEM',
ARRAY ['P472'], false, 'Germany', 5, ARRAY ['http://localhost:8080/api/v1/images/download/HEP-P472',
'http://localhost:8080/api/v1/images/download/HEP-P472-2', 'http://localhost:8080/api/v1/images/download/HEP-P472-3'], false, null),
(11, 'BMW Ignition Coil Kit (Set of 6) - Bosch 00044X6', 'bmw-ignition-coil-12131712219x6', 1, 'KIT-00044X6', null,
10, 148.74, 'OE', ARRAY ['0221504470', '00044', '0221504464', '0221504100'], true, null, 10,
ARRAY ['http://localhost:8080/api/v1/images/download/KIT-00044X6', 'http://localhost:8080/api/v1/images/download/KIT-00044X6-2',
'http://localhost:8080/api/v1/images/download/KIT-00044X6-3', 'http://localhost:8080/api/v1/images/download/KIT-00044X6-4'],
false, null),
(12, 'BMW Direct Ignition Coil - Delphi 12138616153', 'bmw-direct-ignition-coil-12138616153-1', 8, 'DEL-GN10571', 232386,
10, 22.29, 'OE', ARRAY ['GN1057112B1', 'GN1057111B1'], false, 'Portugal', 9,
ARRAY ['http://localhost:8080/api/v1/images/download/DEL-GN10571', 'http://localhost:8080/api/v1/images/download/DEL-GN10571-2',
'http://localhost:8080/api/v1/images/download/DEL-GN10571-3'], false, null),
(13, 'BMW Ignition Service Kit - 12138616153KT4', 'bmw-ignition-service-kit-12138616153kt4', 8, 'KIT-12138616153KT4', null,
10, 204.47, 'OE', ARRAY ['GN1057112B1', 'GN1057111B1', '3199', 'BKR6EQUP', '81150'], true, null, 11,
ARRAY ['http://localhost:8080/api/v1/images/download/KIT-12138616153KT4', 'http://localhost:8080/api/v1/images/download/KIT-12138616153KT4-2',
'http://localhost:8080/api/v1/images/download/KIT-12138616153KT4-3', 'http://localhost:8080/api/v1/images/download/KIT-12138616153KT4-4'],
false, null),
(14, 'Group 94R H7 Battery - Odyssey Performance 94R-850', 'automotive-battery-group-94r-odyssey-94r-850', 9, 'ODY-94R850',
539061, 10, 255.99, 'Performance', ARRAY ['94R850'], false, 'United States', 7,
ARRAY ['http://localhost:8080/api/v1/images/download/ODY-94R850', 'http://localhost:8080/api/v1/images/download/ODY-94R850-2',
'http://localhost:8080/api/v1/images/download/ODY-94R850-3', 'http://localhost:8080/api/v1/images/download/ODY-94R850-4',
'http://localhost:8080/api/v1/images/download/ODY-94R850-5'], false, null),
(15, 'BMW Accessory Belt - Continental 6PK1538', 'bmw-belt-alternator-water-pump-power-steering-continental-6pk1538',
10, 'CON-6K1538', 94827, 10, 16.39, 'OE', ARRAY ['6K1538'], false, 'Germany', 8,
ARRAY ['http://localhost:8080/api/v1/images/download/CON-6K1538', 'http://localhost:8080/api/v1/images/download/CON-6K1538-2',
'http://localhost:8080/api/v1/images/download/CON-6K1538-3', 'http://localhost:8080/api/v1/images/download/CON-6K1538-4'],
false, null),
(16, 'BMW A/C Belt - Continental 4PK863', 'bmw-a-c-compressor-belt-4pk863', 10, 'CON-4K863', 67403, 10, 9.59, 'OEM',
ARRAY ['4K863'], false, 'Romania', 8, ARRAY ['http://localhost:8080/api/v1/images/download/CON-4K863',
'http://localhost:8080/api/v1/images/download/CON-4K863-2', 'http://localhost:8080/api/v1/images/download/CON-4K863-3'],
false, null);

INSERT INTO vehicle_part(vehicle_id, part_id, comment) VALUES
(7, 1, 'Direct Ignition Coil'),
(7, 2, null),
(7, 3, null),
(7, 4, null),
(7, 5, null),
(7, 7, null),
(7, 8, null),
(7, 9, 'w/o Sequential manual Transmission (S206A)'),
(7, 10, null),
(7, 11, null),
(7, 12, null),
(7, 13, null),
(7, 14, null),
(7, 15, null),
(7, 16, null),
(8, 1, null),
(8, 2, null),
(8, 3, null),
(8, 4, null),
(8, 5, null),
(8, 7, null),
(8, 8, null),
(8, 9, 'w/o Sequential manual Transmission (S206A)'),
(8, 10, null),
(8, 11, null),
(8, 12, null),
(8, 13, null),
(8, 14, null),
(8, 15, null),
(8, 16, null),
(9, 1, null),
(9, 2, null),
(9, 3, null),
(9, 4, null),
(9, 5, null),
(9, 7, null),
(9, 8, null),
(9, 9, 'w/o Sequential manual Transmission (S206A)'),
(9, 10, null),
(9, 11, null),
(9, 12, null),
(9, 13, null),
(9, 14, null),
(9, 15, null),
(9, 16, null),
(10, 1, null),
(10, 2, null),
(10, 3, null),
(10, 4, null),
(10, 5, null),
(10, 7, null),
(10, 8, null),
(10, 9, 'w/o Sequential manual Transmission (S206A)'),
(10, 10, null),
(10, 11, null),
(10, 12, null),
(10, 13, null),
(10, 14, null),
(10, 15, null),
(10, 16, null);

INSERT INTO oe_number(id, oe_number) VALUES
(1, '12138647689'),
(2, '12137594937'),
(3, '12137594938'),
(4, '13721744869'),
(5, '12317501599'),
(6, '12120037607'),
(7, '12120037608'),
(8, '12129069877'),
(9, '12129070999'),
(10, '64526916232'),
(11, '64526936883'),
(12, '34216761250'),
(13, '34213403241'),
(14, '21207531556'),
(15, '21217523622'),
(16, '21217523622'),
(17, '11517509985'),
(18, '11517527910'),
(19, '11517503884'),
(20, '12138616153'),
(21, 'A0009822108'),
(22, 'A0009823804'),
(23, 'A0009824601'),
(24, '11287512762'),
(25, '11287636379'),
(26, '11281706545'),
(27, '11281437475');

INSERT INTO part_oe_number(part_id, oe_number_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(5, 7),
(5, 8),
(5, 9),
(7, 10),
(7, 11),
(8, 12),
(8, 13),
(9, 14),
(9, 15),
(9, 16),
(10, 17),
(10, 18),
(10, 19),
(11, 1),
(11, 2),
(11, 3),
(12, 20),
(12, 2),
(12, 3),
(13, 20),
(13, 2),
(13, 3),
(14, 21),
(14, 22),
(14, 23),
(15, 25),
(15, 26),
(15, 27),
(16, 24);

-- I have to add id so I can sort the kit-parts when I retrieve them in the order I have inserted them
INSERT INTO kit_part(id, kit_id, part_id, quantity) VALUES
(1, 2, 1, 6),
(2, 2, 5, 6),
(3, 2, 6, 1),
(4, 11, 1, 6),
(5, 13, 12, 6),
(6, 13, 5, 6),
(7, 13, 6, 1);

INSERT INTO image(id, name, type, file_path, part_id) VALUES
(1, 'bosch', 'image/png', 'YOUR_PATH\\images\\brands\\bosch.png', null),
(2, 'mann', 'image/png', 'YOUR_PATH\\images\\brands\\mann.png', null),
(3, 'BOS-0221504470', 'image/jpg', 'YOUR_PATH\\images\\BOS-0221504470\\BOS-0221504470.jpg', 1),
(4, 'BOS-0221504470-2', 'image/jpeg', 'YOUR_PATH\\images\\BOS-0221504470\\BOS-0221504470-2.jpg', 1),
(5, 'BOS-0221504470-3', 'image/jpeg', 'YOUR_PATH\\images\\BOS-0221504470\\BOS-0221504470-3.jpg', 1),
(6, 'KIT-12138647689KT3', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138647689KT3\\KIT-12138647689KT3.jpg', 2),
(7, 'KIT-12138647689KT3-2', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-2.jpg', 2),
(8, 'KIT-12138647689KT3-3', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-3.jpg', 2),
(9, 'KIT-12138647689KT3-4', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-4.jpg', 2),
(10, 'KIT-12138647689KT3-5', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138647689KT3\\KIT-12138647689KT3-5.jpg', 2),
(11, 'MAN-C251141', 'image/jpeg', 'YOUR_PATH\\images\\MAN-C251141\\MAN-C251141.jpg', 3),
(12, 'BOS-1986A01099', 'image/jpeg', 'YOUR_PATH\\images\\BOS-1986A01099\\BOS-1986A01099.jpg', 4),
(13, 'BOS-1986A01099-2', 'image/jpeg', 'YOUR_PATH\\images\\BOS-1986A01099\\BOS-1986A01099-2.jpg', 4),
(14, 'BOS-1986A01099-3', 'image/jpeg', 'YOUR_PATH\\images\\BOS-1986A01099\\BOS-1986A01099-3.jpg', 4),
(15, 'ngk', 'image/png', 'YOUR_PATH\\images\\brands\\ngk.png', null),
(16, 'NGK-3199', 'image/jpeg', 'YOUR_PATH\\images\\NGK-3199\\NGK-3199.jpg', 5),
(17, 'NGK-3199-2', 'image/jpeg', 'YOUR_PATH\\images\\NGK-3199\\NGK-3199-2.jpg', 5),
(18, 'permatex', 'image/png', 'YOUR_PATH\\images\\brands\\permatex.png', null),
(19, 'PMX-81150', 'image/jpeg', 'YOUR_PATH\\images\\PMX-81150\\PMX-81150.jpg', 6),
(20, 'denso', 'image/png', 'YOUR_PATH\\images\\brands\\denso.png', null),
(21, 'DEN-4711528', 'image/jpeg', 'YOUR_PATH\\images\\DEN-4711528\\DEN-4711528.jpg', 7),
(22, 'BOS-BC683', 'image/jpeg', 'YOUR_PATH\\images\\BOS-BC683\\BOS-BC683.jpg', 8),
(23, 'BOS-BC683-2', 'image/jpeg', 'YOUR_PATH\\images\\BOS-BC683\\BOS-BC683-2.jpg', 8),
(24, 'BOS-BC683-3', 'image/jpeg', 'YOUR_PATH\\images\\BOS-BC683\\BOS-BC683-3.jpg', 8),
(25, 'luk', 'image/png', 'YOUR_PATH\\images\\brands\\luk.png', null),
(26, 'LUK-6243575000', 'image/jpeg', 'YOUR_PATH\\images\\LUK-6243575000\\LUK-6243575000.jpg', 9),
(27, 'LUK-6243575000-2', 'image/jpeg', 'YOUR_PATH\\images\\LUK-6243575000\\LUK-6243575000-2.jpg', 9),
(28, 'LUK-6243575000-3', 'image/jpeg', 'YOUR_PATH\\images\\LUK-6243575000\\LUK-6243575000-3.jpg', 9),
(29, 'LUK-6243575000-4', 'image/jpeg', 'YOUR_PATH\\images\\LUK-6243575000\\LUK-6243575000-4.jpg', 9),
(30, 'hepu', 'image/png', 'YOUR_PATH\\images\\brands\\hepu.png', null),
(31, 'HEP-P472', 'image/jpeg', 'YOUR_PATH\\images\\HEP-P472\\HEP-P472.jpg', 10),
(32, 'HEP-P472-2', 'image/jpeg', 'YOUR_PATH\\images\\HEP-P472\\HEP-P472-2.jpg', 10),
(33, 'HEP-P472-3', 'image/jpeg', 'YOUR_PATH\\images\\HEP-P472\\HEP-P472-3.jpg', 10),
(34, 'KIT-00044X6', 'image/jpeg', 'YOUR_PATH\\images\\KIT-00044X6\\KIT-00044X6.jpg', 11),
(35, 'KIT-00044X6-2', 'image/jpeg', 'YOUR_PATH\\images\\KIT-00044X6\\KIT-00044X6-2.jpg', 11),
(36, 'KIT-00044X6-3', 'image/jpeg', 'YOUR_PATH\\images\\KIT-00044X6\\KIT-00044X6-3.jpg', 11),
(37, 'KIT-00044X6-4', 'image/jpeg', 'YOUR_PATH\\images\\KIT-00044X6\\KIT-00044X6-4.jpg', 11),
(38, 'delphi', 'image/png', 'YOUR_PATH\\images\\brands\\delphi.png', null),
(39, 'DEL-GN10571', 'image/jpeg', 'YOUR_PATH\\images\\DEL-GN10571\\DEL-GN10571.jpg', 12),
(40, 'DEL-GN10571-2', 'image/jpeg', 'YOUR_PATH\\images\\DEL-GN10571\\DEL-GN10571-2.jpg', 12),
(41, 'DEL-GN10571-3', 'image/jpeg', 'YOUR_PATH\\images\\DEL-GN10571\\DEL-GN10571-3.jpg', 12),
(42, 'KIT-12138616153KT4', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138616153KT4\\KIT-12138616153KT4.jpg', 13),
(43, 'KIT-12138616153KT4-2', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138616153KT4\\KIT-12138616153KT4-2.jpg', 13),
(44, 'KIT-12138616153KT4-3', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138616153KT4\\KIT-12138616153KT4-3.jpg', 13),
(45, 'KIT-12138616153KT4-4', 'image/jpeg', 'YOUR_PATH\\images\\KIT-12138616153KT4\\KIT-12138616153KT4-4.jpg', 13),
(46, 'ODY-94R850', 'image/jpeg', 'YOUR_PATH\\images\\ODY-94R850\\ODY-94R850.jpg', 14),
(47, 'ODY-94R850-2', 'image/jpeg', 'YOUR_PATH\\images\\ODY-94R850\\ODY-94R850-2.jpg', 14),
(48, 'ODY-94R850-3', 'image/jpeg', 'YOUR_PATH\\images\\ODY-94R850\\ODY-94R850-3.jpg', 14),
(49, 'ODY-94R850-4', 'image/jpeg', 'YOUR_PATH\\images\\ODY-94R850\\ODY-94R850-4.jpg', 14),
(50, 'ODY-94R850-5', 'image/jpeg', 'YOUR_PATH\\images\\ODY-94R850\\ODY-94R850-5.jpg', 14),
(51, 'continental', 'image/png', 'YOUR_PATH\\images\\brands\\continental.png', null),
(52, 'CON-6K1538', 'image/jpeg', 'YOUR_PATH\\images\\CON-6K1538\\CON-6K1538.jpg', 15),
(53, 'CON-6K1538-2', 'image/jpeg', 'YOUR_PATH\\images\\CON-6K1538\\CON-6K1538-2.jpg', 15),
(54, 'CON-6K1538-3', 'image/jpeg', 'YOUR_PATH\\images\\CON-6K1538\\CON-6K1538-3.jpg', 15),
(55, 'CON-6K1538-4', 'image/jpeg', 'YOUR_PATH\\images\\CON-6K1538\\CON-6K1538-4.jpg', 15),
(56, 'CON-4K863', 'image/jpeg', 'YOUR_PATH\\images\\CON-4K863\\CON-4K863.jpg', 16),
(57, 'CON-4K863-2', 'image/jpeg', 'YOUR_PATH\\images\\CON-4K863\\CON-4K863-2.jpg', 16),
(58, 'CON-4K863-3', 'image/jpeg', 'YOUR_PATH\\images\\CON-4K863\\CON-4K863-3.jpg', 16);

INSERT INTO review(id, part_id, user_id, created_at, name, rating, title, text) VALUES
(1, 1, 1, '2024-05-20 12:35:10', 'Dexter', 1, 'Great coil', 'What an amazing coil'),
(2, 1, 2, '2024-05-20 12:35:10', 'Dexter', 5, 'Great coil', 'What an amazing coil'),
(3, 1, 3, '2024-05-20 12:35:10', 'Dexter', 2, 'Great coil', 'What an amazing coil'),
(4, 1, 4, '2024-05-20 12:35:10', 'Dexter', 3, 'Great coil', 'What an amazing coil'),
(5, 1, 5, '2024-05-20 12:35:10', 'Dexter', 4, 'Great coil', 'What an amazing coil');
