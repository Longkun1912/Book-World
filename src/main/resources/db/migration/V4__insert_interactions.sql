---
---Insert to rates entity for posts
---
insert into rates (rate_value,created_time,user_rate,post_rate) values
(5,timestamp '2022-01-06 08:11:08','a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
(4,timestamp '2022-06-08 01:13:05','6e7cc348-cbf8-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
(5,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),

(1,timestamp '2022-09-02 03:16:04','a00e70ea-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
(3,timestamp '2022-11-11 02:10:11','6e7cc348-cbf8-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
(4,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),

(2,timestamp '2022-07-01 07:12:02','a00e6d84-c6c2-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002'),
(4,timestamp '2022-11-11 05:07:04','6e7cc348-cbf8-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002'),
(2,timestamp '2022-07-09 09:19:01','a00e70ea-c6c2-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002');

---
---Insert to comments entity for posts
---
insert into comments (text,created_time,user_comment,post_comment) values
('Please read follow the rule everyone.',timestamp '2023-01-02 09:19:01','a00e70ea-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
('Ok.',timestamp '2023-02-03 11:10:01','6e7cc348-cbf8-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),
('Whatever! I don''t care actually.',timestamp '2022-12-12 16:10:01','a00e6d84-c6c2-11ed-afa1-0242ac120002','128bfa9a-cbf8-11ed-afa1-0242ac120002'),

('Wow! What a fantastic evaluation! Thank u for your efforts.',timestamp '2023-01-02 11:12:01','a00e70ea-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
('Well done sir.',timestamp '2023-01-01 18:12:53','6e7cc348-cbf8-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),
('Couldn''t agree more.',timestamp '2023-01-01 15:10:52','a00e6d84-c6c2-11ed-afa1-0242ac120002','ac964e56-dc4c-11ed-afa1-0242ac120002'),

('Can i get the source?',timestamp '2022-08-09 15:10:52','6e7cc348-cbf8-11ed-afa1-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002'),
('Got it.',timestamp '2022-08-09 18:10:52','abd271cc-1192-11ee-be56-0242ac120002','77e36764-dc49-11ed-afa1-0242ac120002');

---
---Insert to replies for comments
---
insert into comments (text,created_time,user_comment,parent_id) values
('Yes sir',timestamp '2023-01-02 10:13:01','6e7cc348-cbf8-11ed-afa1-0242ac120002',1),
('Oki doki',timestamp '2023-01-02 11:05:03','a00e6d84-c6c2-11ed-afa1-0242ac120002',1),

('Who ask you anyway?',timestamp '2022-12-12 16:11:01','6e7cc348-cbf8-11ed-afa1-0242ac120002',3),

('Thank you, i''m glad you like it.',timestamp '2023-01-02 11:13:00','a00e6d84-c6c2-11ed-afa1-0242ac120002',4),

('You can find it in Book Wiki.',timestamp '2022-08-09 15:12:52','a00e6d84-c6c2-11ed-afa1-0242ac120002',7);