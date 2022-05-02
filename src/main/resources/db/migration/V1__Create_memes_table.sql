CREATE TABLE memes
(
    id           bigserial PRIMARY KEY,
    content      varchar(1000),
    viewed       boolean,
    last_checked timestamptz
);
