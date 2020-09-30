CREATE TABLE IF NOT EXISTS "documents" ("id" uuid NOT NULL, "created_by" VARCHAR(512) NOT NULL, "created" TIMESTAMP NOT NULL, "modified_by" VARCHAR(512), "modified" TIMESTAMP, "row_version" int8 NOT NULL default 0, "company" VARCHAR(255) NOT NULL, "partner" VARCHAR(255) NOT NULL, "product" VARCHAR(255) NOT NULL, "amount" INT NOT NULL, "price" DECIMAL NOT NULL, "data" TEXT NOT NULL, "status" VARCHAR (32) check ("status" in ('CANCELLED', 'COMPLETED', 'NEW', 'PROCESSING', 'REGISTERED', 'SUSPENDED', 'EDITED')) NOT NULL DEFAULT 'NEW', PRIMARY KEY ("id"));

CREATE INDEX IF NOT EXISTS company_idx ON "documents" ("company");
CREATE INDEX IF NOT EXISTS partner_idx ON "documents" ("partner");
