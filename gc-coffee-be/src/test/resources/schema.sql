CREATE TABLE if not exists products
(
    product_id   BINARY(16) PRIMARY KEY,
    product_name VARCHAR(20)  NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    price        BIGINT       NOT NULL,
    descriptions VARCHAR(500) ,
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6) NOT NULL
);