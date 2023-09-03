create extension hstore;
create schema adventure;

create table if not exists adventure."Creature" ("creature_id" BIGSERIAL NOT NULL PRIMARY KEY, "name" VARCHAR NOT NULL, "image" VARCHAR NOT NULL, "vitality" VARCHAR NOT NULL, "strength" VARCHAR NOT NULL, "dexterity" VARCHAR NOT NULL);
create table if not exists adventure."Location" ("location_id" BIGSERIAL NOT NULL PRIMARY KEY, "name" VARCHAR NOT NULL, "image" VARCHAR NOT NULL, "description" VARCHAR NOT NULL);
