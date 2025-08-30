-- 創建 users 表
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 創建索引以提高查詢效能
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_name ON users(name);
CREATE INDEX idx_users_active ON users(active);
CREATE INDEX idx_users_created_at ON users(created_at);

-- 添加註解
COMMENT ON TABLE users IS '使用者資料表';
COMMENT ON COLUMN users.id IS '使用者唯一識別碼';
COMMENT ON COLUMN users.name IS '使用者名稱';
COMMENT ON COLUMN users.email IS '電子郵件地址（唯一）';
COMMENT ON COLUMN users.active IS '使用者是否啟用';
COMMENT ON COLUMN users.created_at IS '建立時間';
COMMENT ON COLUMN users.updated_at IS '最後更新時間';