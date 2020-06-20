CREATE sequence IF NOT EXISTS hibernate_sequence start 1 increment 1;

CREATE TABLE IF NOT EXISTS "documents" ("id" uuid NOT NULL, "created_by" VARCHAR(512) NOT NULL, "created" TIMESTAMP NOT NULL, "modified_by" VARCHAR(512), "modified" TIMESTAMP, "row_version" int8 NOT NULL default 0, "company" VARCHAR(256) NOT NULL, "partner" VARCHAR(256) NOT NULL, "product" VARCHAR(256) NOT NULL, "amount" INT NOT NULL, "price" DECIMAL NOT NULL, "data" BLOB NOT NULL, "status" ENUM('CANCELLED', 'COMPLETED', 'NEW', 'REGISTERED', 'SUSPENDED') NOT NULL DEFAULT 'NEW', PRIMARY KEY ("id"));

CREATE INDEX IF NOT EXISTS company_idx ON "documents" ("company");
CREATE INDEX IF NOT EXISTS partner_idx ON "documents" ("partner");
