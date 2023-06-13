INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_1000', 'gift certificate gives a discount of 1000₴ for 30 days', 1000, 30, NOW()+1);
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_100', 'gift certificate gives a discount of 100₴ for 30 days', 100, 30, NOW()+2);
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_500', 'gift certificate gives a discount of 500₴ for 30 days', 500, 30, NOW()+3);
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('MELMAN1500', 'promo code gives a discount of 1500₴ during the next year', 1500, 365, NOW()+4);
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('MELMAN50', 'promo code gives a discount of 50₴ during the next week', 50, 7, NOW()+5);

INSERT into "tag" ("name") VALUES ('discount');
INSERT into "tag" ("name") VALUES ('gift');
INSERT into "tag" ("name") VALUES ('promo_code');

INSERT into "user" ("name", "surname", "email") VALUES ('Petro', 'Bumper', 'bamper@org.com');
INSERT into "user" ("name", "surname", "email") VALUES ('Max', 'Pain', 'pain@org.com');

INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (1, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (1, 2);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (2, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (2, 2);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (3, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (3, 2);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (4, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (4, 3);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (5, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (5, 3);

INSERT into "orders" ("order_price", "time", "gift_certificate_id", "user_id") VALUES (1500, NOW(), 1, 1);
INSERT into "orders" ("order_price", "time", "gift_certificate_id", "user_id") VALUES (500, NOW(), 3, 1);
INSERT into "orders" ("order_price", "time", "gift_certificate_id", "user_id") VALUES (1500, NOW(), 4, 2);
INSERT into "orders" ("order_price", "time", "gift_certificate_id", "user_id") VALUES (50, NOW(), 5, 2);
INSERT into "orders" ("order_price", "time", "gift_certificate_id", "user_id") VALUES (100, NOW(), 2, 2);

