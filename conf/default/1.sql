# --- !Ups

CREATE TABLE "category"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "brand"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "product"
(
    "id"          INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" TEXT    NOT NULL,
    "category"    INT     NOT NULL,
    "brand"    INT     NOT NULL,
    "price"       MONEY   NOT NULL,
    FOREIGN KEY (category) references category (id),
    FOREIGN KEY (brand) references brand(id)
);

CREATE TABLE "comment"
(
    "id"      INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    "owner"   VARCHAR NOT NULL,
    "content" VARCHAR NOT NULL,
    "created" DATE    NOT NULL,
    "product" INTEGER  NOT NULL,
    FOREIGN KEY (product) references product (id)
)

CREATE TABLE "account"
(
    "id"       INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    "login"    VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL
)

CREATE TABLE "coupon"
(
    "id"            INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"          VARCHAR NOT NULL,
    "description"   VARCHAR NOT NULL,
    "availableFrom" DATE    NOT NULL,
    "availableTo"   DATE    NOT NULL,
    "percentage"    FLOAT   NOT NULL
)

CREATE TABLE "shipment_type"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" VARCHAR NOT NULL,
    "price"       MONEY   NOT NULL
)

CREATE TABLE "payment_type"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" VARCHAR NOT NULL
)

create table "payment"
(
    "id"       INTEGER  NOT NULL,
    "amount"   MONEY NOT NULL,
    "order"    INTEGER NOT NULL,
    FOREIGN KEY ("order") references "order"(id)
)

CREATE TABLE "order"
(
    "id"       INTEGER  NOT NULL,
    "client"   INTEGER  NOT NULL,
    "created"  DATE    NOT NULL,
    "shipment" INTEGER NOT NULL,
    "payment_type"  INTEGER NOT NULL,
    "coupon"   INTEGER,
    FOREIGN KEY (client) references account (id),
    FOREIGN KEY (shipment) references shipment_type (id),
    FOREIGN KEY (payment_type) references payment_type (id),
    FOREIGN KEY (coupon) references coupon (id)
)

CREATE TABLE "historical_product"
(
    "id"          INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" TEXT    NOT NULL,
    "category_name"    VARCHAR     NOT NULL,
    "brand_name"    VARCHAR     NOT NULL
)

CREATE TABLE "order_item"
(
    "id"          INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
    "order"       INTEGER NOT NULL,
    "product"     INTEGER NOT NULL,
    "quantity"    INTEGER NOT NULL,
    "price_unit"  MONEY NOT NULL,
    FOREIGN KEY (product) references historical_product(id),
    FOREIGN KEY ("order") references [order](id)
)
# --- !Downs


DROP TABLE "order"
DROP TABLE "payment_type"
DROP TABLE "coupon"
DROP TABLE "shipment_type"
DROP TABLE "comment"
DROP TABLE "account"
DROP TABLE "product"
DROP TABLE "category"
DROP TABLE "historical_product"
DROP TABLE "order_item"