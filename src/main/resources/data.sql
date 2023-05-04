DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    name      VARCHAR(50)  NOT NULL,
    price     INT          NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(100)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
