---
---Insert to posts entity with image content
---
insert into posts (id,title,content_text,content_image,created_time,user_post) values
('128bfa9a-cbf8-11ed-afa1-0242ac120002', 'My rules & politics of downloading of Mike''s travel books',
'Although you do not have to pay for this book, the author’s intellectual property rights
remain fully protected by international Copyright law. You are licensed to use this
digital copy strictly for your personal enjoyment only. This edition must not be hosted
or redistributed on other websites without the author’s written permission nor offered
for sale in any form. If you paid for this book, or to gain access to it, we suggest you
demand an immediate refund and report the transaction to the author.',
'https://greenwichbook.blob.core.windows.net/thegioisach/post/Mike_s-Japan.png',
timestamp '2022-12-11 22:34:10','a00e70ea-c6c2-11ed-afa1-0242ac120002'),

('77e36764-dc49-11ed-afa1-0242ac120002','Honest feedback of The Hammer of God',
'I enjoyed the book more than i expected, and was equally as stirred by the idea of very,
very large mountains moving in our solar system which can bring more destruction than any nuclear weapon humanity has broiled up as of today. Nature seems to always have the upper hand when it comes to composing weaponry.<br><br>
I especially liked the comparison of the protagonists and individuals of the story—as well as asteroids— to the heroes of the Trojan War,
I thought that was a nice literary tip of the hat—don’t make God mad, Paris! Clarke certainly was a master of science fiction by the 1990s and he did not lose his mastery of suspense.<br><br>
The science felt authentic and was enough to challenge my mind, while still staying entertaining enough because,after all, it’s fiction.
I am impressed by Clarke’s storytelling and worldbuilding— The Arthur C. Clarke’s Hammer of God is a hardcore, run-of-the-mill sci-fi but delivered a fun ride all the same.',
'https://greenwichbook.blob.core.windows.net/thegioisach/post/hammer-of-god.jpg',
timestamp '2022-11-02 13:45:00','6e7cc348-cbf8-11ed-afa1-0242ac120002');

---
---Insert to posts entity without image content
---
insert into posts (id,title,content_text,created_time,user_post) values
('ac964e56-dc4c-11ed-afa1-0242ac120002','Why Did the First Harry Potter Book Get a Title Change in the U.S.?',
'One of the ongoing anomalies of the Harry Potter series is the differing title of the first adventure. In America, the first outing of J.K. Rowling''s most famous creation is Harry Potter and the Sorcerer''s Stone, but pretty much everywhere else on the planet, it''s Harry Potter and the Philosopher''s Stone.<br><br>
So why did the name change?<br><br>
It dates back to when the rights were sold for the first book: Scholastic Corporation bought the U.S. publishing rights for what was then known as Harry Potter and the Philosopher''s Stone, forking out a comparably high $105,000 for the privilege. Arthur A. Levine, who headed the Scholastic children''s imprint that would publish the book, knew he had a winner on his hands—he just had some reservations about the title.<br><br>
In Philip W. Errington''s biography on Rowling, he wrote that Levine wanted “a title that said ‘magic’ more overtly to American readers." Levine was concerned that Philosopher''s Stone would feel "arcane," so one of the proposed alternatives was Harry Potter and the School of Magic, which Rowling shot down. Eventually, the author suggested Sorcerer''s Stone, and that''s how the book is known in the U.S. market where Scholastic had the rights.<br><br>
The change in title would also be reflected in the first film when it came out in 2001. However, the inconsistencies ended there as the remaining six books all had uniform names.',
timestamp '2023-01-03 08:12:05','a00e70ea-c6c2-11ed-afa1-0242ac120002');

---
---Insert to favorites entity
---
insert into favorites(id,user_id) values
('4ae15a16-224c-11ee-be56-0242ac120002','a00e6d84-c6c2-11ed-afa1-0242ac120002'),
('56697d5a-224c-11ee-be56-0242ac120002','a00e70ea-c6c2-11ed-afa1-0242ac120002'),
('5cd9edd2-224c-11ee-be56-0242ac120002','6e7cc348-cbf8-11ed-afa1-0242ac120002'),
('63a7e8e4-224c-11ee-be56-0242ac120002','f21ae502-1191-11ee-be56-0242ac120002'),
('69bb22fa-224c-11ee-be56-0242ac120002','abd271cc-1192-11ee-be56-0242ac120002'),
('703de2de-224c-11ee-be56-0242ac120002','14fed474-1193-11ee-be56-0242ac120002');

---
---Insert to favorite_books entity
---
insert into favorite_books(favorite_id,book_id) values
('4ae15a16-224c-11ee-be56-0242ac120002',1),
('4ae15a16-224c-11ee-be56-0242ac120002',6),
('4ae15a16-224c-11ee-be56-0242ac120002',8),
('4ae15a16-224c-11ee-be56-0242ac120002',12),
('56697d5a-224c-11ee-be56-0242ac120002',1),
('56697d5a-224c-11ee-be56-0242ac120002',3),
('56697d5a-224c-11ee-be56-0242ac120002',7),
('5cd9edd2-224c-11ee-be56-0242ac120002',2),
('5cd9edd2-224c-11ee-be56-0242ac120002',5),
('5cd9edd2-224c-11ee-be56-0242ac120002',13),
('63a7e8e4-224c-11ee-be56-0242ac120002',14),
('63a7e8e4-224c-11ee-be56-0242ac120002',17),
('69bb22fa-224c-11ee-be56-0242ac120002',4),
('69bb22fa-224c-11ee-be56-0242ac120002',9),
('69bb22fa-224c-11ee-be56-0242ac120002',13),
('703de2de-224c-11ee-be56-0242ac120002',15);




