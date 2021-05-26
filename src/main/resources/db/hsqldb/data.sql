INSERT INTO users(username,password,enabled) VALUES ('josito','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('aug','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('dani','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('antonio','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('rodri','1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('marcos','1234',TRUE);
-- USUARIOS PILOTO
INSERT INTO users(username,password,enabled) VALUES ('irenebed','ispp1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('pablofer','ispp1333',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('rafaelfres','ispp1135',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('carlosgui','ispp2224',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('irenebed2','ispp1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('pablofer2','ispp1333',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('rafaelfres2','ispp1135',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('carlosgui2','ispp2224',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('albaalc','q8289k8n',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('juancar','zcpfhjw1',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('joseenr','u9hxj5fh',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('fernandobel','zxqva4em',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('alejandrobon','b4nvx2vf',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('sarael','tqx8pdam',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('javierm','5gh9i83h',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('oscarda','k6rvfuqb',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('clarafer','8w3mqgmk',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('remediosfer','zh5kqir8',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('purisimaga','bzbq97m7',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('davidgil','94b7vnic',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('alejandrogom','tnajymu2',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('adriangon','i7q759qd',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('sobragon','87qcujq3',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('robertoher','pxrup7ph',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('miguelill','8a3amt7q',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('franciscoja','uamwkvk3',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('antoniomar','qbpzizj4',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('albanie','3f3hfu5g',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('erikanie','x2pnmn5i',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('virginianis','i3urx59r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('juanlu','mg6tufnc',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('elenapaz','amwxtwn5',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('mariapi','kzxm7qjr',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('cristinapol','65c9h2k7',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('ricardojo','kxfqbeu6',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('mariariv','7wbuuvxu',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('elenarod','437vz6d3',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('franciscoroj','7ken8jmq',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('aliciasal','rct5wayy',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('anasal','xryk2jaf',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('albasev','m732vgzj',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('pon','5678',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('josan','5678',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('adri','5678',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('chus','5678',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('pepe','5678',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('manu','5678',TRUE);


INSERT INTO authorities(id,index,username,authority) VALUES ('07f9c2e2-212b-49ed-86d8-629c78caf331',1,'josito','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('d835490f-073c-4406-a233-31606b349bfa',2,'dani','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('81ff310f-aab6-41f0-89ed-5dfaa051f005',3,'marcos','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('29b05251-702d-40ab-a1ec-dbb30b0d2891',4,'aug','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('fb84ab89-81bf-4c5a-8d54-198cc9184c49',5,'rodri','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('41020f72-f2ee-4919-83c2-165766111674',6,'antonio','owner');
-- USUARIOS PILOTO
INSERT INTO authorities(id,index,username,authority) VALUES ('1602921d-fecb-4ac4-8adb-e1fe0a934e11',7,'irenebed','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('cf6bce4c-1b46-470d-9bec-8bca3feb8f5b',8,'pablofer','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('50604329-a649-4cd5-bf33-d7d6f36c2865',9,'rafaelfres','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('50a6901e-28ee-4c3d-bf97-8b5a4fc97253',10,'carlosgui','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('6e133591-8145-4a68-bcda-1cd50ba770e2',11,'irenebed2','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('aa30d4f2-8f47-430c-9242-f9e0d40a9691',12,'pablofer2','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('36bd563d-1864-4407-8f8a-a502c3a1caf4',13,'rafaelfres2','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('5297f6b7-1544-4389-b35a-e3d31150c19c',14,'carlosgui2','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('fe9bda8f-91c0-4edd-b94d-a92b04d82580',15,'albaalc','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('0f9c5496-7658-45c7-99d8-3d3680e73f8d',16,'juancar','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('0f3d2a40-c3a6-4e62-8a53-2960c0b52657',17,'joseenr','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('63a7b01d-60b4-45ac-9407-c1461002b01e',18,'fernandobel','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('c8c282a0-5db3-441f-b12a-3f2d88f63b15',19,'alejandrobon','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('97d40ad4-18ad-4085-98a0-ef5f4634aa5e',20,'sarael','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('521b6d59-5731-417e-999d-dabb24626c79',21,'javierm','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('1ca59ae2-c82f-445b-b905-cfa8d0dd241d',22,'oscarda','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('2a2c4178-e3f7-424a-978f-f21615c1b598',23,'clarafer','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('4cc3ce01-6d56-4e24-8f66-6bfa7fdd5825',24,'remediosfer','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('995dcc3f-98fb-4fc1-bfd1-a54718bd023b',25,'purisimaga','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('430c0578-3985-449b-8acf-ccdb6814cfc7',26,'davidgil','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('6326653a-d3b4-4186-81a5-f80def5034a7',27,'alejandrogom','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('ae926335-c2e7-43bb-bf85-a0b30db5dce8',28,'adriangon','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('216fa1cc-eb11-4a8c-8654-7196ba2e35c1',29,'sobragon','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('b76ba7c3-b27c-4b27-bc1c-12f400f4f31d',30,'robertoher','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('7ecc63dd-4f07-47bb-964c-2bbcb3f07800',31,'miguelill','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('61083921-b329-41e0-bf58-f8ada02f8e34',32,'franciscoja','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('d16d4acd-9542-49ce-bc36-6e0c62409cb7',33,'antoniomar','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('74da552d-31ce-4af6-9555-3ec26e2fab4f',34,'albanie','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('8a096c16-2b92-4531-b750-3c386f919367',35,'erikanie','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('256c0648-a8a0-4ba2-aee1-515d93d427a8',36,'virginianis','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('4ea684a0-993a-4156-be38-c5cff16c98c8',37,'juanlu','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('5b2a90fd-6e4a-4bd3-b744-39f952a82bf8',38,'elenapaz','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('c88f78e7-c413-4376-b600-7690c1d6f07e',39,'mariapi','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('ac91cd69-6bd7-43cf-90fe-d97a8e4fbd77',40,'cristinapol','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('65cd2f3a-5de4-4c46-8149-e491d0fc4d3c',41,'ricardojo','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('0f928caf-6bea-4e8a-ab47-a0d09c92fc87',42,'mariariv','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('2dea6567-9e45-43b5-9249-133d61e86d96',43,'elenarod','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('7e9dc8ab-8020-401b-a4b6-7ecf673df057',44,'franciscoroj','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('eb2cd383-9498-4b26-85a3-d89015b286d1',45,'aliciasal','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('f03b11e6-f4b5-458e-b64a-b151c573fdf4',46,'anasal','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('ae1bcc0b-3243-4395-b560-2171016b1ec9',47,'albasev','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('8e606877-c99e-4df7-8e99-341ab6afbd9c',48,'pon','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('c4702ed2-4194-400f-8594-a3313d8f5edc',49,'josan','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('0dbe3375-881e-4e8e-8e21-f7db33642fc4',50,'adri','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('b579968d-5b0c-4f95-b26e-d6e021970bfc',51,'chus','owner');
INSERT INTO authorities(id,index,username,authority) VALUES ('a770069c-c704-456b-835d-8e8cd7bf1093',52,'pepe','user');
INSERT INTO authorities(id,index,username,authority) VALUES ('77f02e2a-68c9-498d-87e4-7981a5e30100',53,'manu','user');


INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('fd87da59-52c2-40a7-9fcd-3c3f5f748c46',1,'Augusto','García','00000000A', 'augusto@gmail.com', 'aug','PREMIUM');
INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('a9276917-d8f6-4c2e-bfb9-6f6b109cf9c4',2,'Rodrigo','García','45600000A', 'rodrigo@gmail.com', 'rodri', 'FREE');
INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('c334aa7a-18fa-4abf-9a49-22614acaf27b',3,'Antonio','García','00032400H', 'antonio@gmail.com', 'antonio', 'PREMIUM');
INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('087868c6-b3f2-4d21-9399-09f272d49598',4,'José Antonio','Guerrero','83435409B', 'josan@gmail.com', 'josan','PREMIUM');
INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('33ea6ed8-048d-4fc1-93c0-f62bd0359154',5,'Adrián','Fernández','21986318C', 'adri@gmail.com', 'adri', 'FREE');
INSERT INTO suppliers(id, index, name, lastname, dni, email, username, subscription)
VALUES ('21b36d00-dbca-4701-a5ab-f4ad9e066065',6,'María Jesús','García','69566679M', 'mariajesus@gmail.com', 'chus', 'FREE');


INSERT INTO options(id, index,automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('87d937c5-f916-47c3-a994-a834a71a3fd3',1,true,3,0.3,5);
INSERT INTO options(id, index,automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('df8838a0-fdbd-42d2-a653-9db0b23cc6fe',2,true,3,0.3,5);
INSERT INTO options(id, index,automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('62951182-29c9-418a-8653-24e954068206',3,false,3,0.3,5);
INSERT INTO options(id, index, automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('6a7cf7bd-bb13-454d-8f5f-5dec8ffdc037',4,false,3,0.3,5);
INSERT INTO options(id, index, automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('43399ccb-a7d8-422e-b34d-e00aef01a93a',5,true,3,0.3,5);
INSERT INTO options(id, index, automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('4098261f-31ee-4535-b7af-65424e129c48',6,true,3,0.3,5);
INSERT INTO options(id, index, automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('7ec05e53-7221-4efe-90e7-4bedfad8986a',7,true,3,0.3,5);
INSERT INTO options(id, index, automated_Accept, gas, default_Deposit, deposit_Time_Limit)
VALUES ('c890777c-fe27-4fd1-9345-1ba2acaa6841',8,true,3,0.3,5);


INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('3e970e62-93ff-414e-9848-3883b6c30ceb',1,'fd87da59-52c2-40a7-9fcd-3c3f5f748c46','87d937c5-f916-47c3-a994-a834a71a3fd3','Pizzería Gus','Calle Manuel Benítez','RESTAURANT',TRUE, '14:00:00', '22:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('a0cd55d0-f4a2-48fd-9583-d6c905d4647f',2,'a9276917-d8f6-4c2e-bfb9-6f6b109cf9c4','df8838a0-fdbd-42d2-a653-9db0b23cc6fe','Tattoos Rodri','Avenida Juan Pablo Montoya','GENERAL',TRUE, '10:00:00', '20:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('0be90880-745f-4e49-b4db-519e5acc9f48',3,'c334aa7a-18fa-4abf-9a49-22614acaf27b','62951182-29c9-418a-8653-24e954068206','Peluquería Antonio','Plaza Vicente Jiménez','HAIRDRESSER',TRUE, '08:30:00', '19:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('3069289a-0963-4588-b1b1-e86e6b6f3006',4,'fd87da59-52c2-40a7-9fcd-3c3f5f748c46','6a7cf7bd-bb13-454d-8f5f-5dec8ffdc037','Peluquería Gus','Calle Barcelona','HAIRDRESSER',TRUE, '10:00:00', '18:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('a7298a76-cd99-4b14-8e71-5631cdd367eb',5,'087868c6-b3f2-4d21-9399-09f272d49598','43399ccb-a7d8-422e-b34d-e00aef01a93a','Cervecería Josan','Avenida Reina Mercedes','RESTAURANT',TRUE, '10:00:00', '23:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('b909ebd3-a00d-49f3-9749-eb68937c6ae4',6,'087868c6-b3f2-4d21-9399-09f272d49598','4098261f-31ee-4535-b7af-65424e129c48','Restaurante Josan','Calle Florida','RESTAURANT',TRUE, '12:00:00', '22:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('d97fbc99-5113-4929-956b-1c7660e2be54',7,'33ea6ed8-048d-4fc1-93c0-f62bd0359154','7ec05e53-7221-4efe-90e7-4bedfad8986a','Centro de Masajes Fernández','Calle Virgen Macarena','GENERAL',TRUE, '09:00:00', '17:00:00');
INSERT INTO business(id, index, supplier_id, option_id, name, address, type, automated, openTime, closeTime)
VALUES ('54e41b11-3201-4bd8-806c-35ab607c5f73',8,'21b36d00-dbca-4701-a5ab-f4ad9e066065','c890777c-fe27-4fd1-9345-1ba2acaa6841','Gimnasio Chus','Calle Fernando Torres','GENERAL',TRUE, '08:00:00', '19:30:00');


INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('b6ea48d3-28ad-4784-a6d8-7b530bf2215b',1,'Comer','Ven a comer al restaurante y disfruta',15,60,5,3,0.2,'3e970e62-93ff-414e-9848-3883b6c30ceb');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('7e86481b-bc49-40ce-ab92-1a59036c9760',2,'Beber','Beber con amigos y buenas tapas',9,30,3,3,0.33,'3e970e62-93ff-414e-9848-3883b6c30ceb');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('e2b7933e-8fd4-4b6e-94bc-4e47aec54421',3,'Tattoo pequeño','Precio y tiempo de un tattoo pequeño',20,45,1,2,0.10,'a0cd55d0-f4a2-48fd-9583-d6c905d4647f');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('2b5fe54a-632c-42b0-bd52-5f96005fa42c',4,'Consulta de precios','Reunión para preguntar dudas y presentar diseños',2,20,1,0.5,0.25,'a0cd55d0-f4a2-48fd-9583-d6c905d4647f');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('785e2890-b86b-44ed-919c-ffa3c79f4366',5,'Corte caballero','Corte de pelo de caballero',6,12,1,1.2,0.2,'0be90880-745f-4e49-b4db-519e5acc9f48');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('dfc395bb-b52c-44d9-9779-feb01afc42eb',6,'Corte + barba','Corte de pelo caballero + arreglo de barba',12,20,1,4,0.33,'0be90880-745f-4e49-b4db-519e5acc9f48');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('d1c042bc-273c-44b4-a23e-28f2b1cb0dc8',7,'Tattoo grande','Tatuaje mediano/grande',50,90,1,25,0.5,'a0cd55d0-f4a2-48fd-9583-d6c905d4647f');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('e6c53964-8b24-488a-8eb2-b0a4c990432e',8,'Menú del día','Menú del día de la pizzería',20,60,4,5,0.25,'3e970e62-93ff-414e-9848-3883b6c30ceb');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('325a8419-a399-4c28-9c1f-8fddd34450b4',9,'Corte + tinte','Corte de pelo más tinte',15,45,1,3,0.2,'3069289a-0963-4588-b1b1-e86e6b6f3006');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('03e6e021-8c5c-4ca4-bf49-490464938942',10,'Extensiones','Corte de pelo con extensiones',20,45,1,5,0.25,'3069289a-0963-4588-b1b1-e86e6b6f3006');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('4532b795-7b9c-44b6-b927-e0b98eb2df2c',11,'2 Birras Grandes','Cerveza para compartir con amigo',6,30,2,1.2,0.2,'a7298a76-cd99-4b14-8e71-5631cdd367eb');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('12f7771b-67f7-4c1c-8033-dc1c0ebb3b1e',12,'Tinto Rojo','Vino tinto de la mejor bodega',20,45,5,5,0.25,'a7298a76-cd99-4b14-8e71-5631cdd367eb');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('56d9e831-8e13-44d3-ab97-9fa525863d4c',13,'Esferificación de Aceituna','Delicioso plato minimalista',25,30,2,5,0.2,'b909ebd3-a00d-49f3-9749-eb68937c6ae4');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('3f20fcf2-4407-4792-b481-7f318440dccf',14,'Foie de oca','Exquisito manjar francés',15,30,2,3,0.20,'b909ebd3-a00d-49f3-9749-eb68937c6ae4');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('b483f7df-e7bf-4e3d-938a-95e9f48104f5',15,'Masaje tántrico','Masaje muy relajante',15,45,1,3,0.2,'d97fbc99-5113-4929-956b-1c7660e2be54');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('7ddbda0a-e4f8-466a-9d43-1bb56222a08c',16,'Masaje Tailandés','Masaje Asiático',20,45,1,5,0.25,'d97fbc99-5113-4929-956b-1c7660e2be54');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('89e8c26e-e91d-48a0-89ff-cd8e8f1e35f7',17,'Clase de Yoga','Clase de Yoga para principiantes',10,30,1,2,0.2,'54e41b11-3201-4bd8-806c-35ab607c5f73');
INSERT INTO servises(id, index, name, description, price, duration, capacity, deposit, tax, business_id)
VALUES ('d86044ee-119e-43ab-8e22-ad22b7a7b919',18,'Clase de Spinning','Clase para fortalecer la musculatura',20,45,1,4,0.20,'54e41b11-3201-4bd8-806c-35ab607c5f73');

INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61',1,'José','García','23487343A', 'josito@gmail.com', 'josito');
INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('e4acec03-6a6a-44de-9744-2134513c3562',2,'Daniel','San José','34542321D', 'daniel@gmail.com', 'dani');
INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('888e3691-41b2-4387-9bc6-2020c732e86e',3,'Marcos','García','76865443F', 'marquitos@gmail.com', 'marcos');
INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('cae7526e-add4-4dc3-9b18-4ad05d01f88f',4,'José','Rodríguez','34874846S', 'pepero@gmail.com', 'pon');
INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('ab595dc8-333a-4b65-9dfc-86ae4b4669db',5,'José','González','78336089K', 'joselito@gmail.com', 'pepe');
INSERT INTO consumers(id, index, name, lastname, dni, email, username)
VALUES ('20776613-b73b-470b-b3aa-af5654deecd7',6,'Manuel','Pérez','63479381W', 'manolito@gmail.com', 'manu');


INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('c59ee257-f744-43d3-8da6-7ab0ba77313c',1,'4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61','b6ea48d3-28ad-4784-a6d8-7b530bf2215b','2022-01-27 21:00:00','2021-01-15 20:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('74f230d9-2e63-46a4-9ff0-da11bbfb6e05',2,'e4acec03-6a6a-44de-9744-2134513c3562','b6ea48d3-28ad-4784-a6d8-7b530bf2215b','2022-01-25 17:00:00','2021-02-27 17:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('3bcb0a7f-1b38-48e8-9b5e-ca74df74775b',3,'888e3691-41b2-4387-9bc6-2020c732e86e','b6ea48d3-28ad-4784-a6d8-7b530bf2215b','2022-01-20 20:00:00','2021-01-17 15:00:00','COMPLETED');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('28777152-09df-4b8b-a814-dfa90f12508f',4,'4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61','7e86481b-bc49-40ce-ab92-1a59036c9760','2022-01-27 20:00:00','2021-01-27 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('6aea27db-71bb-4a18-89d5-d3195cae2f48',5,'cae7526e-add4-4dc3-9b18-4ad05d01f88f','e2b7933e-8fd4-4b6e-94bc-4e47aec54421','2022-01-20 19:00:00','2021-02-17 12:00:00','COMPLETED');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('1c1ead1f-bc35-4c89-afa6-ad0b8eea85e9',6,'ab595dc8-333a-4b65-9dfc-86ae4b4669db','2b5fe54a-632c-42b0-bd52-5f96005fa42c','2022-01-27 12:00:00','2021-01-10 17:00:00','IN_PROGRESS');   
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('5689b84f-71fd-40a7-a432-0aa069c4b789',7,'888e3691-41b2-4387-9bc6-2020c732e86e','785e2890-b86b-44ed-919c-ffa3c79f4366','2022-01-20 18:00:00','2021-02-17 23:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('82392563-c722-4b3e-93d1-43dedd6371f2',8,'4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61','dfc395bb-b52c-44d9-9779-feb01afc42eb','2022-01-27 12:00:00','2021-02-27 20:00:00','IN_PROGRESS'); 
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('6c96e962-fd9f-4d44-ada7-951ac6cb3f07',9,'e4acec03-6a6a-44de-9744-2134513c3562','325a8419-a399-4c28-9c1f-8fddd34450b4','2022-01-15 16:00:00','2021-01-11 17:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('aad0f7dd-cca5-49fc-b3bd-0d23c31a805b',10,'ab595dc8-333a-4b65-9dfc-86ae4b4669db','03e6e021-8c5c-4ca4-bf49-490464938942','2022-01-27 12:00:00','2021-01-27 09:00:00','IN_PROGRESS'); 
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('acd7fe2d-08b0-4502-95f3-1f3df27e8781',11,'888e3691-41b2-4387-9bc6-2020c732e86e','4532b795-7b9c-44b6-b927-e0b98eb2df2c','2022-01-20 20:00:00','2021-01-17 10:00:00','COMPLETED');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('9645fa85-3abc-4016-8003-33d4d0c626a1',12,'4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61','12f7771b-67f7-4c1c-8033-dc1c0ebb3b1e','2022-01-27 22:00:00','2021-01-27 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('7a559a8c-700d-43a8-ae82-c5d52aca03d3',13,'cae7526e-add4-4dc3-9b18-4ad05d01f88f','56d9e831-8e13-44d3-ab97-9fa525863d4c','2022-01-20 19:00:00','2021-01-17 22:00:00','COMPLETED');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('986ad6a0-6cc6-4b4e-bc82-bb7dc83aab94',14,'ab595dc8-333a-4b65-9dfc-86ae4b4669db','3f20fcf2-4407-4792-b481-7f318440dccf','2022-01-27 13:00:00','2021-01-27 22:00:00','IN_PROGRESS');   
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('0f4a0425-57d8-413d-9546-365a58656303',15,'888e3691-41b2-4387-9bc6-2020c732e86e','b483f7df-e7bf-4e3d-938a-95e9f48104f5','2022-01-20 15:00:00','2021-01-17 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('ded83dfa-742c-4be2-b8c5-5d5a621b99bb',16,'4cc85c6d-ca1b-40a3-b7a9-2db5a1dbfb61','7ddbda0a-e4f8-466a-9d43-1bb56222a08c','2022-01-27 12:00:00','2021-01-27 22:00:00','IN_PROGRESS'); 
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('4be428a1-191b-4629-9664-5eef67809455',17,'e4acec03-6a6a-44de-9744-2134513c3562','89e8c26e-e91d-48a0-89ff-cd8e8f1e35f7','2022-01-15 16:00:00','2021-01-17 22:00:00','IN_PROGRESS');
INSERT INTO bookings(id, index, consumer_id, servise_id, book, emision, status) 
        VALUES ('c77ea738-c71e-40cc-92bc-8358de007ba1',18,'ab595dc8-333a-4b65-9dfc-86ae4b4669db','d86044ee-119e-43ab-8e22-ad22b7a7b919','2022-01-27 12:00:00','2021-01-27 22:00:00','IN_PROGRESS'); 
