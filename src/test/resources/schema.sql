CREATE SCHEMA IF NOT EXISTS "certificate_db";

SET SCHEMA "certificate_db";

DROP TABLE IF EXISTS "certificate_db"."tag";

CREATE TABLE IF NOT EXISTS "certificate_db"."tag"
(
    "id"   int         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name" varchar(25) NOT NULL,
    PRIMARY KEY ("id")
);


DROP TABLE IF EXISTS "certificate_db"."gift_certificate";

CREATE TABLE IF NOT EXISTS "certificate_db"."gift_certificate"
(
    "id"               bigint       NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name"             varchar(255) NOT NULL,
    "description"      clob         NOT NULL,
    "price"            int          NOT NULL,
    "duration"         int,
    "create_date"      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "last_update_date" timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);


DROP TABLE IF EXISTS "certificate_db"."gift_certificate_has_tag";

CREATE TABLE IF NOT EXISTS "certificate_db"."gift_certificate_has_tag"
(
    "gift_certificate_id" bigint NOT NULL,
    "tag_id"              int    NOT NULL,
    PRIMARY KEY ("gift_certificate_id", "tag_id"),
    CONSTRAINT "fk_gift_certificate_has_tag_gift_certificate"
        FOREIGN KEY ("gift_certificate_id")
            REFERENCES "certificate_db"."gift_certificate" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "fk_gift_certificate_has_tag_tag1"
        FOREIGN KEY ("tag_id")
            REFERENCES "certificate_db"."tag" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS "certificate_db"."user";

CREATE TABLE IF NOT EXISTS "certificate_db"."user"
(
    "id"      BIGINT       NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "name"    VARCHAR(32)  NOT NULL,
    "surname" VARCHAR(32)  NOT NULL,
    "email"   VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "certificate_db"."orders";

CREATE TABLE IF NOT EXISTS "certificate_db"."orders"
(
    "id"                  BIGINT    NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    "order_price"         INT       NOT NULL,
    "time"                TIMESTAMP NOT NULL,
    "gift_certificate_id" BIGINT    NOT NULL,
    "user_id"             BIGINT    NOT NULL,
    PRIMARY KEY ("id", "gift_certificate_id", "user_id"),
    CONSTRAINT "fk_order_gift_certificate1"
        FOREIGN KEY ("gift_certificate_id")
            REFERENCES "certificate_db"."gift_certificate" ("id") ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT "fk_order_user1"
        FOREIGN KEY ("user_id")
            REFERENCES "certificate_db"."user" ("id") ON DELETE CASCADE ON UPDATE RESTRICT
);

