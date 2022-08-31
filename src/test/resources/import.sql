
insert into account ( created_at, updated_at, uuid, name, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-40d8-4322-ba33-9c05203868e9', 'Jane J', 'strawberry');

insert into account (created_at, updated_at, uuid, name, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-50d8-4322-ba33-9c05203868e9','Claudia C', 'coco');

insert into account (created_at, updated_at, uuid, name, username) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-60d8-4322-ba33-9c05203868e9',  'Elon Musk', 'spacex');

insert into post (created_at, updated_at, uuid, author_id, likes_count, dislikes_count, text) values ( '2022-07-15 14:52:49.065758', '2022-07-15 14:52:49.065813', '7c20fb12-60d8-4322-ba33-9c05203868a9',  1, 1,1, 'job fair');

insert into post_likes (post_id, account_id) values (1,1);
insert into post_dislikes (post_id, account_id) values (1,2);

insert into comment (created_at, updated_at, uuid, text, author_id, post_id) values ('2022-07-15 14:52:49.065758',  '2022-07-15 14:52:49.065813', '7c20fb12-60d8-4322-ba33-9c05203868b9', 'very interesting', 1, 1);
