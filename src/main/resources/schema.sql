CREATE TABLE IF NOT EXISTS products
(
    id bigint NOT NULL,
    category character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    image_path character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);