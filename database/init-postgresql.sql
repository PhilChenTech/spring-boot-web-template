-- PostgreSQL 資料庫初始化腳本
-- 請以 postgres 超級使用者身份執行此腳本

-- 建立應用程式資料庫
CREATE DATABASE springboot_template_db
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'Chinese (Traditional)_Taiwan.1252'
    LC_CTYPE = 'Chinese (Traditional)_Taiwan.1252'
    TEMPLATE = template0;

-- 建立開發環境資料庫  
CREATE DATABASE springboot_template_db_dev
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'Chinese (Traditional)_Taiwan.1252'
    LC_CTYPE = 'Chinese (Traditional)_Taiwan.1252'
    TEMPLATE = template0;

-- 建立生產環境資料庫
CREATE DATABASE springboot_template_db_prod
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'Chinese (Traditional)_Taiwan.1252'
    LC_CTYPE = 'Chinese (Traditional)_Taiwan.1252'
    TEMPLATE = template0;

-- 建立應用程式專用使用者
CREATE USER springboot_user WITH ENCRYPTED PASSWORD 'springboot_password';

-- 授權使用者存取所有應用程式資料庫
GRANT ALL PRIVILEGES ON DATABASE springboot_template_db TO springboot_user;
GRANT ALL PRIVILEGES ON DATABASE springboot_template_db_dev TO springboot_user;
GRANT ALL PRIVILEGES ON DATABASE springboot_template_db_prod TO springboot_user;

-- 確保使用者可以連接到資料庫
GRANT CONNECT ON DATABASE springboot_template_db TO springboot_user;
GRANT CONNECT ON DATABASE springboot_template_db_dev TO springboot_user;
GRANT CONNECT ON DATABASE springboot_template_db_prod TO springboot_user;

-- 連接到每個資料庫並授予 schema 權限
\c springboot_template_db;
GRANT USAGE ON SCHEMA public TO springboot_user;
GRANT CREATE ON SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO springboot_user;

\c springboot_template_db_dev;
GRANT USAGE ON SCHEMA public TO springboot_user;
GRANT CREATE ON SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO springboot_user;

\c springboot_template_db_prod;
GRANT USAGE ON SCHEMA public TO springboot_user;
GRANT CREATE ON SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springboot_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO springboot_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO springboot_user;

-- 顯示建立的資料庫
\l

-- 完成訊息
\echo '資料庫設定完成！'
\echo '已建立資料庫：springboot_template_db, springboot_template_db_dev, springboot_template_db_prod'
\echo '已建立使用者：springboot_user (密碼：springboot_password)'
