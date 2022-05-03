ALTER TABLE memes
    ADD COLUMN IF NOT EXISTS
        last_check_message_id bigint UNIQUE;
