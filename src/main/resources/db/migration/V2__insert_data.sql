---
---Insert to roles entity
---
insert into roles (name) values ('admin'),('user');

---
---Insert to users entity for admin
---

insert into users (id,username,image_url,email,phone_number,last_updated,password,user_role) values
--- Admin test account: adminno1@gmail.com
--- Password: Admin2000#
('a00e6d84-c6c2-11ed-afa1-0242ac120002','AdminNo1','https://greenwichbook.blob.core.windows.net/thegioisach/admin/admin.jpg','adminno1@gmail.com','01663403287',timestamp '2021-11-11 17:30:00','$2a$12$UCTdC2fCVD0kADyVvj04/.P05FmbLWdQvNg.z2Er7RBBs57XunHnq',1);

---
---Insert to users entity for users
---
insert into users (id,username,image_url,email,phone_number,status,last_updated,password,user_role) values
--- User test account: userno1@gmail.com
--- Password: User3000$
('a00e70ea-c6c2-11ed-afa1-0242ac120002','UserNo1','https://greenwichbook.blob.core.windows.net/thegioisach/user/user.jpg','userno1@gmail.com','0144222543','Enabled',timestamp '2022-03-02 21:35:08','$2a$12$ZPxO9r9vijv8u2rbwbne1OzkMIoY22RaO7XnYsUrgDLtfhnIlC8AS',2),

--- User test account: usersupreme007@gmail.com
--- Password: User007%
('6e7cc348-cbf8-11ed-afa1-0242ac120002','UserSupreme','https://greenwichbook.blob.core.windows.net/thegioisach/user/user_supreme.jpg','usersupreme007@gmail.com','0987263176','Enabled',timestamp '2022-09-07 20:18:00','$2a$12$obXp50BXPX5wshSjw4uYSOGBwQHme3DyjlsaEhIZ61P9JucyX3TgG',2);
