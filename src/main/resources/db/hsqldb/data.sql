INSERT INTO users(username,password,enabled) VALUES ('josito','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'josito','consumer');

INSERT INTO users(username,password,enabled) VALUES ('eduito','4321',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'eduito','consumer');


INSERT INTO consumers(id, name, lastname, dni, email, username) VALUES (
    1,
    'Jose', 
    'Garcia',
    '00000000A', 
    'cosas@gmail.com', 
    'josito');
    
INSERT INTO consumers(id, name, lastname, dni, email, username) VALUES (
    2,
    'Eduardo', 
    'Esposito',
    '00000000B', 
    'eduespo@gmail.com', 
    'eduito');


INSERT INTO suppliers(id, name, lastName, dni, email, username) VALUES (
    1,
    'supplier_name_1', 
    'supplier_lastname_1',
    '1111111A', 
    'random@gmail.com', 
    'josito');
    
INSERT INTO suppliers(id, name, lastName, dni, email, username) VALUES (
    2,
    'supplier_name_2', 
    'supplier_lastname_2',
    '1111111B', 
    'random2@gmail.com', 
    'eduito');

INSERT INTO options(id, automated_Accept, limit_Automated, default_Deposit, deposit_Time_Limit) VALUES (
    1,
    true,
    3,
    0.7,
    5);

INSERT INTO business(id, supplier_id, option_id, name, address, type, automated) VALUES (
    1,
    1,
    1,
    'business_name_1',
    'address_1',
    'HAIRDRESSER',
    TRUE);
    
INSERT INTO business(id, supplier_id, option_id, name, address, type, automated) VALUES (
    2,
    2,
    1,
    'business_name_2',
    'address_2',
    'RESTAURANT',
    TRUE);
    
INSERT INTO business(id, supplier_id, option_id, name, address, type, automated) VALUES (
    3,
    1,
    1,
    'business_name_3',
    'address_2',
    'HAIRDRESSER',
    TRUE);



INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id) VALUES (
    1,
    'servise_name',
    'servise_description_1',
    20.3,
    2,
    2,
    40.7,
    0.05,
    1
);

INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status) VALUES (
    1,
    1,
    1,
    '2021-01-27 22:00:00',
    '2022-01-27 22:00:00',
    'IN_PROGRESS');

INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status) VALUES (
    2,
    1,
    null,
    '2021-01-27 22:00:00',
    '2023-01-27 22:00:00',
    'IN_PROGRESS');

