CREATE TABLE UserManagement
(
    `user_type`  VARCHAR(45)    NULL        COMMENT '사용자 형태', 
    `password`   VARCHAR(45)    NULL        COMMENT '비밀번호', 
    PRIMARY KEY (user_type)
);

ALTER TABLE UserManagement COMMENT '사용자정보';

CREATE TABLE ProductManagement
(
    `prod_cd`  INT            NOT NULL    AUTO_INCREMENT COMMENT '상품코드', 
    `prod_nm`  VARCHAR(45)    NULL        COMMENT '상품명', 
    `price`    INT            NULL        COMMENT '판매가', 
    `amount`   INT            NULL        COMMENT '재고', 
    `user_yn`  VARCHAR(45)    NULL        COMMENT '사용여부', 
    PRIMARY KEY (prod_cd)
);

ALTER TABLE ProductManagement COMMENT '상품정보';

CREATE TABLE product_hist
(
    `seq`         INT            NOT NULL    AUTO_INCREMENT COMMENT '유니크키', 
    `prod_cd`     VARCHAR(45)    NULL        COMMENT '상품코드', 
    `type`        VARCHAR(45)    NULL        COMMENT '입출고 판매 구분', 
    `out_amount`  INT            NULL        COMMENT '입고수량', 
    `in_amount`   INT            NULL        COMMENT '출고수량', 
    `price`       INT            NULL        COMMENT '판매가', 
    `reg_date`    DATETIME       NULL        COMMENT '등록시간', 
    PRIMARY KEY (seq)
);

ALTER TABLE product_hist COMMENT '상품 이력 정보';

INSERT INTO usermanagement(user_type, password)      VALUES('관리자', 'java2020');
INSERT INTO usermanagement(user_type, password)      VALUES('일반', 'java2020');