DROP TABLE IF EXISTS manufacturers CASCADE;
DROP TABLE IF EXISTS models CASCADE;
DROP TABLE IF EXISTS cars CASCADE;
DROP TABLE IF EXISTS clients CASCADE;
DROP TABLE IF EXISTS test_drives CASCADE;
DROP TABLE IF EXISTS orders CASCADE;


CREATE TABLE "manufacturers" (
  "manufacturer_id" serial,
  "manufacturer" text not null unique,
  PRIMARY KEY ("manufacturer_id")
);

CREATE TABLE "models" (
  "model_id" serial,
  "model" text not null unique,
  "manufacturer_id" integer,
  PRIMARY KEY ("model_id"),
  CONSTRAINT "FK_models.manufacturer_id"
    FOREIGN KEY ("manufacturer_id")
      REFERENCES "manufacturers"("manufacturer_id")
		ON DELETE RESTRICT
);

CREATE TABLE "cars" (
  "car_id" serial,
  "model_id" integer,
  "vin" varchar(17) unique,
  "year" smallint,
  "price" money,
  "devices" text[],
  "consumers_attrs" jsonb,
  "tech_attrs" jsonb,
  "history_attrs" jsonb,
  PRIMARY KEY ("car_id"),
  CONSTRAINT "FK_cars.model_id"
    FOREIGN KEY ("model_id")
      REFERENCES "models"("model_id")
		ON DELETE RESTRICT
);

CREATE TABLE "clients" (
  "client_id" serial,
  "name" text not null,
  "address" text,
  "email" text,
  "phone" varchar(11),
  PRIMARY KEY ("client_id")
);

CREATE TABLE "test_drives" (
  "client_id" integer,
  "car_id" integer,
  PRIMARY KEY ("client_id", "car_id"),
  CONSTRAINT "FK_test_drives.car_id"
    FOREIGN KEY ("car_id")
      REFERENCES "cars"("car_id")
		ON DELETE CASCADE,
  CONSTRAINT "FK_test_drives.client_id"
    FOREIGN KEY ("client_id")
      REFERENCES "clients"("client_id")
		ON DELETE CASCADE
);

CREATE TYPE status AS ENUM ('В работе',  'На тест-драйве', 'Отменён', 'Завершён');

CREATE TABLE "orders" (
  "order_id" serial,
  "client_id" integer,
  "car_id" integer,
  "date_time" timestamp not null,
  "need_test" boolean not null,
  "status" status not null,
  PRIMARY KEY ("order_id"),
  CONSTRAINT "FK_orders.client_id"
    FOREIGN KEY ("client_id")
      REFERENCES "clients"("client_id")
		ON DELETE CASCADE,
  CONSTRAINT "FK_orders.car_id"
    FOREIGN KEY ("car_id")
      REFERENCES "cars"("car_id")
		ON DELETE CASCADE
);

