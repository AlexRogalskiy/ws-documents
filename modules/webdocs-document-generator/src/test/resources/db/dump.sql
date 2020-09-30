--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 12.3

-- Started on 2020-09-23 22:49:36

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 204 (class 1259 OID 19195)
-- Name: documents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents (
                                  id uuid NOT NULL,
                                  created_by character varying(512) NOT NULL,
                                  created timestamp without time zone DEFAULT now() NOT NULL,
                                  modified_by character varying(512),
                                  modified timestamp without time zone,
                                  row_version bigint DEFAULT 0 NOT NULL,
                                  company character varying(255) NOT NULL,
                                  partner character varying(255) NOT NULL,
                                  product character varying(255) NOT NULL,
                                  amount integer NOT NULL,
                                  price numeric NOT NULL,
                                  data text NOT NULL,
                                  status public.status_enum DEFAULT 'NEW'::public.status_enum NOT NULL
);


ALTER TABLE public.documents OWNER TO postgres;

--
-- TOC entry 2827 (class 0 OID 19195)
-- Dependencies: 204
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documents (id, created_by, created, modified_by, modified, row_version, company, partner, product, amount, price, data, status) FROM stdin;
33b15cdc-1748-40c9-8c67-64ebd3870a75	system	2019-08-26 12:39:16	\N	\N	0	Company-01	Partner-01	Product-01	10	100.5	data-01	REGISTERED
fb7dbfa2-0cc3-468e-a2e5-a6d0bc003329	system	2019-08-26 12:39:16	\N	\N	0	Company-02	Partner-02	Product-02	10	200.5	data-02	NEW
bc31a456-0c01-4821-96b1-f0c061201f71	system	2019-08-26 12:39:16	\N	\N	0	Company-03	Partner-03	Product-03	10	300.5	data-03	CANCELLED
1339df96-4b7f-407a-996e-292e18176eb7	system	2019-08-26 12:39:16	\N	\N	0	Company-04	Partner-04	Product-04	10	400.5	data-04	COMPLETED
474b0d63-a5b3-44eb-ac21-04d0c79d3eca	system	2019-08-26 12:39:16	\N	\N	0	Company-05	Partner-05	Product-05	10	500.5	data-05	SUSPENDED
bac303b4-7aae-47cf-aa04-4bdb9ec2a44a	system	2019-08-26 12:39:16	\N	\N	0	Company-06	Partner-06	Product-06	100	50.5	data-06	EDITED
3c14d7a0-372c-4c24-8587-04227b10699c	system	2019-08-26 12:39:16	\N	\N	0	Company-07	Partner-07	Product-07	67	45.5	data-06	PROCESSING
\.


--
-- TOC entry 2699 (class 2606 OID 19205)
-- Name: documents documents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);


--
-- TOC entry 2697 (class 1259 OID 19206)
-- Name: company_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX company_idx ON public.documents USING btree (company);


--
-- TOC entry 2700 (class 1259 OID 19207)
-- Name: partner_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX partner_idx ON public.documents USING btree (partner);


-- Completed on 2020-09-23 22:49:36

--
-- PostgreSQL database dump complete
--

