INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_1000', 'gift certificate gives 1000₴ discount for 30 days', 1000, 30, NOW());
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_100', 'gift certificate gives 100₴ discount for 30 days', 100, 30, NOW());
INSERT into "gift_certificate" ("name", "description", "price", "duration", "create_date") VALUES ('Promo_500', 'gift certificate gives 500₴ discount for 30 days', 500, 30, NOW());

INSERT into "tag" ("name") VALUES ('discount');
INSERT into "tag" ("name") VALUES ('gift');

INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (1, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (1, 2);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (2, 1);
INSERT into "gift_certificate_has_tag" ("gift_certificate_id", "tag_id") VALUES (2, 2);