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


-- 顯示建立的資料庫
\l

-- 完成訊息
\echo '資料庫設定完成！'
\echo '已建立資料庫：springboot_template_db, springboot_template_db_dev, springboot_template_db_prod'
