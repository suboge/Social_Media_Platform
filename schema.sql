-- 1. 建立使用者資料表 (Users)
CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       phone_number VARCHAR(15) NOT NULL UNIQUE,
                       user_name VARCHAR(50) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       cover_image VARCHAR(255),
                       biography TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE Users IS '使用者表';
COMMENT ON COLUMN Users.user_id IS '使用者 ID (Primary Key)';
-- ... (其餘 Comment 略過以節省空間)

alter table users add column email VARCHAR(100);

-- 2. 建立貼文資料表 (Posts)
CREATE TABLE Posts (
                       post_id SERIAL PRIMARY KEY,
                       user_id INT NOT NULL,
                       content TEXT NOT NULL,
                       image VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

COMMENT ON TABLE Posts IS '貼文表';
-- ...

-- 3. 建立留言資料表 (Comments)
CREATE TABLE Comments (
                          comment_id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL,
                          post_id INT NOT NULL,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                          CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES Posts(post_id) ON DELETE CASCADE
);

ALTER TABLE users ALTER COLUMN password_hash TYPE VARCHAR(100);
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
-- 關鍵點：執行到這裡時，確認 Comments 已經成功建立
COMMENT ON TABLE Comments IS '留言表';
COMMENT ON COLUMN Comments.comment_id IS '留言 ID (Primary Key)';
COMMENT ON COLUMN Comments.user_id IS '留言者 ID (Foreign Key to Users)';
COMMENT ON COLUMN Comments.post_id IS '貼文 ID (Foreign Key to Posts)';
COMMENT ON COLUMN Comments.content IS '留言內容';
COMMENT ON COLUMN Comments.created_at IS '留言時間';


--SP

-- User Registration
CREATE OR REPLACE PROCEDURE sp_register_user(
    p_phone_number VARCHAR,
    p_user_name VARCHAR,
    p_password_hash VARCHAR,
    p_email VARCHAR DEFAULT NULL,
    p_cover_image VARCHAR DEFAULT NULL,
    p_biography TEXT DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO users (phone_number, user_name, password_hash, email, cover_image, biography)
VALUES (p_phone_number, p_user_name, p_password_hash, p_email, p_cover_image, p_biography);
END;
$$;

-- List Posts
CREATE OR REPLACE FUNCTION fn_get_all_posts()
RETURNS TABLE(
    post_id INT,
    user_id INT,
    content TEXT,
    image VARCHAR,
    created_at TIMESTAMP,
    user_name VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
RETURN QUERY
SELECT p.post_id, p.user_id, p.content, p.image, p.created_at, u.user_name
FROM posts p
         JOIN users u ON p.user_id = u.user_id
ORDER BY p.created_at DESC;
END;
$$;

-- Post & Comments
CREATE OR REPLACE PROCEDURE sp_create_post(
    p_user_id INT,
    p_content TEXT,
    p_image VARCHAR DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO Posts (user_id, content, image)
VALUES (p_user_id, p_content, p_image);
END;
$$;


-- 編輯發文 SP
CREATE OR REPLACE PROCEDURE sp_update_post(
    p_post_id INT,
    p_user_id INT,
    p_content TEXT,
    p_image VARCHAR DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- 加上 user_id 條件，確保只能改自己的文
UPDATE Posts
SET content = p_content, image = p_image
WHERE post_id = p_post_id AND user_id = p_user_id;
END;
$$;

-- 刪除發文 SP
CREATE OR REPLACE PROCEDURE sp_delete_post(
    p_post_id INT,
    p_user_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- 加上 user_id 條件，確保只能刪自己的文
DELETE FROM Posts
WHERE post_id = p_post_id AND user_id = p_user_id;
END;
$$;

CREATE OR REPLACE PROCEDURE sp_create_comment(
    p_user_id INT,
    p_post_id INT,
    p_content TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO Comments (user_id, post_id, content)
VALUES (p_user_id, p_post_id, p_content);
END;
$$;