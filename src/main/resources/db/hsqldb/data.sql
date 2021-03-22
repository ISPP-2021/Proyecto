INSERT INTO users(username,password,enabled) VALUES ('josito','1234',TRUE);

INSERT INTO consumers(id, name, lastname, dni, email, username) VALUES (
    1,
    'Jose', 
    'Garcia',
    '00000000A', 
    'cosas@gmail.com', 
    'josito');

INSERT INTO suppliers(id, name, lastName, dni, email, username) VALUES (
    1,
    'supplier_name_1', 
    'supplier_lastname_1',
    '1111111A', 
    'random@gmail.com', 
    'josito');

INSERT INTO business(id, supplier_id, name, address, type, automated) VALUES (
    1,
    1,
    'business_name_1',
    'address_1',
    'HAIRDRESSER',
    TRUE);
