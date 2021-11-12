CREATE TABLE IF NOT EXISTS public.comment
(
    cid character varying(255) NOT NULL,
    comment character varying(255),
    creation_date timestamp,
    post_id character varying(255),
    user_id character varying(255),
    CONSTRAINT comment_pkey PRIMARY KEY (cid)

);
