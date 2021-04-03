INSERT INTO users(username,password,enabled) VALUES ('josito','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('aug','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('dani','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('antonio','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('rodri','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('marcos','1234',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (1,'josito','user');
INSERT INTO authorities(id,username,authority) VALUES (2,'dani','user');
INSERT INTO authorities(id,username,authority) VALUES (3,'marcos','user');
INSERT INTO authorities(id,username,authority) VALUES (4,'aug','owner');
INSERT INTO authorities(id,username,authority) VALUES (5,'rodri','owner');
INSERT INTO authorities(id,username,authority) VALUES (6,'antonio','owner');


INSERT INTO consumers(id, name, lastname, dni, email, username)
VALUES (1,'Jose','Garcia','23487343A', 'cosas@gmail.com', 'josito');
INSERT INTO consumers(id, name, lastname, dni, email, username)
VALUES (2,'Daniel','San José','34542321D', 'cosas@gmail.com', 'dani');
INSERT INTO consumers(id, name, lastname, dni, email, username)
VALUES (3,'Marcos','Garcia','76865443F', 'cosas@gmail.com', 'marcos');


INSERT INTO suppliers(id, name, lastname, dni, email, username)
VALUES (1,'Augusto','Garcia','00000000A', 'cosas@gmail.com', 'aug');
INSERT INTO suppliers(id, name, lastname, dni, email, username)
VALUES (2,'Rodrigo','Garcia','45600000A', 'cosas@gmail.com', 'rodri');
INSERT INTO suppliers(id, name, lastname, dni, email, username)
VALUES (3,'Antonio','Garcia','00032400H', 'cosas@gmail.com', 'antonio');

INSERT INTO options(id, automated_Accept, limit_Automated, default_Deposit, deposit_Time_Limit)
VALUES (1,true,3,0.7,5);
INSERT INTO options(id, automated_Accept, limit_Automated, default_Deposit, deposit_Time_Limit)
VALUES (2,true,3,0.7,5);
INSERT INTO options(id, automated_Accept, limit_Automated, default_Deposit, deposit_Time_Limit)
VALUES (3,false,3,0.7,5);

INSERT INTO business(id, supplier_id, option_id, name, address, type, automated)
VALUES (1,1,1,'Pizzeria Gus','address_1','RESTAURANT',TRUE);
INSERT INTO business(id, supplier_id, option_id, name, address, type, automated)
VALUES (2,2,2,'Tattoos Rodri','address_1','GENERAL',TRUE);
INSERT INTO business(id, supplier_id, option_id, name, address, type, automated)
VALUES (3,3,3,'Peluquería Antonio','address_1','HAIRDRESSER',TRUE);

INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (1,'Comer','Ven a comer al restaurante y disfruta',20.3,2,2,40.7,0.05,1);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (2,'Beber','Beber con amigos y buenas tapas',20.3,2,2,40.7,0.05,1);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (3,'Tattoo pequeño','Precio y tiempo de un tattoo pequeño',20.3,2,2,40.7,0.05,2);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (4,'Consulta de precios','Reunión para preguntar dudas y presentar diseños',20.3,2,2,40.7,0.05,2);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (5,'corte caballero','Corte de pelo de caballero',20.3,2,2,40.7,0.05,3);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (6,'Corte + barba','Corte de pelo caballero + arreglo de barba',20.3,2,2,40.7,0.05,3);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (7,'Tattoo grande','Tatuaje mediano/grande',20.3,2,2,40.7,0.05,2);
INSERT INTO servises(id, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES (8,'Menú del día','Menú del día de la pizzería',20.3,2,2,40.7,0.05,1);



INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status)
VALUES (1,1,1,'2021-01-27 22:00:00','2022-01-27 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status)
VALUES (2,2,1,'2021-01-27 22:00:00','2022-01-27 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status)
VALUES (3,3,1,'2021-01-27 22:00:00','2022-01-27 22:00:00','COMPLETED');
INSERT INTO bookings(id, consumer_id, servise_id, book, emision, status)
VALUES (4,1,2,'2021-01-27 22:00:00','2022-01-27 22:00:00','IN_PROGRESS');