-- 添加複合索引以提高查詢效能
CREATE INDEX idx_users_active_created_at ON users(active, created_at);
CREATE INDEX idx_users_name_active ON users(name, active);

-- 添加 Email 域名查詢支援
-- 這個索引有助於 findByEmailDomain 查詢的效能
CREATE INDEX idx_users_email_domain ON users(substring(email from '@(.*)$'));

-- 添加部分索引（僅針對啟用的使用者）
CREATE INDEX idx_users_active_users_name ON users(name) WHERE active = true;
CREATE INDEX idx_users_active_users_email ON users(email) WHERE active = true;