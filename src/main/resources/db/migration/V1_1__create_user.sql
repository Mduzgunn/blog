CREATE TABLE IF NOT EXISTS public.blog_user
(
    uid                 character varying(255) NOT NULL,
    creation_date       timestamp without time zone,
    email               character varying(255),
    username            character varying(255),
    CONSTRAINT blog_user_pkey PRIMARY KEY (uid)
)
