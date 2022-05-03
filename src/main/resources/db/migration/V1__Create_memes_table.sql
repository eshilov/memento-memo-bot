CREATE TABLE IF NOT EXISTS memes
(
    id           bigserial PRIMARY KEY,
    content      varchar(1000),
    viewed       boolean,
    last_checked timestamptz
);
