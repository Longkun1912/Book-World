---
---Insert to chats entity
---
insert into chats (id,created_time,user1,user2) values
('38eacdbe-cbf9-11ed-afa1-0242ac120002',timestamp '2023-01-01 22:34:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','6e7cc348-cbf8-11ed-afa1-0242ac120002'),
('c1151994-0757-11ee-be56-0242ac120002',timestamp '2023-02-01 22:34:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','a00e6d84-c6c2-11ed-afa1-0242ac120002'),
('cf4125b2-0757-11ee-be56-0242ac120002',timestamp '2023-05-01 22:34:10','6e7cc348-cbf8-11ed-afa1-0242ac120002','a00e6d84-c6c2-11ed-afa1-0242ac120002');

---
---Insert to messages entity for users
---
insert into messages (text,created_time,sender_id,chat_id) values
('Hello',timestamp '2023-02-01 22:34:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','38eacdbe-cbf9-11ed-afa1-0242ac120002'),
('Hi. How can i help you?',timestamp '2023-02-01 23:34:10','6e7cc348-cbf8-11ed-afa1-0242ac120002','38eacdbe-cbf9-11ed-afa1-0242ac120002'),
('I''m curious about the new added books. Do you know where they came from?',timestamp '2023-02-01 23:36:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','38eacdbe-cbf9-11ed-afa1-0242ac120002'),
('Oh! It is quite late. I will figure out and ask you later, sorry!',timestamp '2023-02-01 23:37:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','38eacdbe-cbf9-11ed-afa1-0242ac120002'),
('No problem. You can ask me at anytime, sir.',timestamp '2023-02-01 23:39:10','6e7cc348-cbf8-11ed-afa1-0242ac120002','38eacdbe-cbf9-11ed-afa1-0242ac120002'),

('Konichiwa!',timestamp '2023-02-01 23:34:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','c1151994-0757-11ee-be56-0242ac120002'),
('Nani! You''re annoying! Get lost!',timestamp '2023-02-01 23:35:10','a00e6d84-c6c2-11ed-afa1-0242ac120002','c1151994-0757-11ee-be56-0242ac120002'),
('Ahihi! See you then.',timestamp '2023-02-01 23:37:10','a00e70ea-c6c2-11ed-afa1-0242ac120002','c1151994-0757-11ee-be56-0242ac120002'),

('Hi. Can i ask you something?',timestamp '2023-07-01 20:00:10','a00e6d84-c6c2-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('Sure. How can i help you?',timestamp '2023-07-01 20:02:10','6e7cc348-cbf8-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('I wonder there is any book name The Hammer of God left?',timestamp '2023-07-01 20:03:10','a00e6d84-c6c2-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('Let me check, sir.',timestamp '2023-07-01 20:03:55','6e7cc348-cbf8-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('Oh i''m very sorry sir. The Hammer of God were sold out yesterday. But i can promise you they will arrive to the store in 2 days.',timestamp '2023-07-01 20:11:55','6e7cc348-cbf8-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('That''s fine. I can wait until they arrive.',timestamp '2023-07-01 20:14:55','a00e6d84-c6c2-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002'),
('Thank you for your patience sir. If you have further questions, please just let me know.',timestamp '2023-07-01 20:16:55','6e7cc348-cbf8-11ed-afa1-0242ac120002','cf4125b2-0757-11ee-be56-0242ac120002');





