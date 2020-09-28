--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 12.3

-- Started on 2020-09-23 22:53:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2827 (class 0 OID 19195)
-- Dependencies: 204
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('33b15cdc-1748-40c9-8c67-64ebd3870a75', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-01', 'Partner-01', 'Product-01', 10, 100.5, 'data-01', 'REGISTERED');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('fb7dbfa2-0cc3-468e-a2e5-a6d0bc003329', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-02', 'Partner-02', 'Product-02', 10, 200.5, 'data-02', 'NEW');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('bc31a456-0c01-4821-96b1-f0c061201f71', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-03', 'Partner-03', 'Product-03', 10, 300.5, 'data-03', 'CANCELLED');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('1339df96-4b7f-407a-996e-292e18176eb7', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-04', 'Partner-04', 'Product-04', 10, 400.5, 'data-04', 'COMPLETED');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('474b0d63-a5b3-44eb-ac21-04d0c79d3eca', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-05', 'Partner-05', 'Product-05', 10, 500.5, 'data-05', 'SUSPENDED');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('bac303b4-7aae-47cf-aa04-4bdb9ec2a44a', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-06', 'Partner-06', 'Product-06', 100, 50.5, 'data-06', 'EDITED');
INSERT INTO public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) VALUES ('3c14d7a0-372c-4c24-8587-04227b10699c', 'system', '2019-08-26 12:39:16', NULL, NULL, 0, 'Company-07', 'Partner-07', 'Product-07', 67, 45.5, 'data-06', 'PROCESSING');


-- Completed on 2020-09-23 22:53:46

--
-- PostgreSQL database dump complete
--

