CREATE TABLE IF NOT EXISTS public.post
(
    pid character varying(255) NOT NULL,
    body character varying(255) ,
    creation_date timestamp,
    post_tags integer,
    title character varying(255),
    user_id character varying(255)

);
