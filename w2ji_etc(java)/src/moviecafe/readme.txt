DROP TABLE "usermanagement";
DROP TABLE "productmanagement";
DROP TABLE "product_hist";

CREATE TABLE "product_hist" ( 
	"seq"    	int(11) AUTO_INCREMENT COMMENT '유니크키'  NOT NULL,
	"prod_cd"	varchar(45) COMMENT '상품코드'  NULL,
	"amount" 	varchar(45) COMMENT '입출고 수향'  NULL,
	PRIMARY KEY("seq")
)
COMMENT = '상품 이력 정보' ;
CREATE TABLE "productmanagement" ( 
	"prod_cd"	varchar(45) COMMENT '상품코드'  NOT NULL,
	"prod_nm"	varchar(45) COMMENT '상품명'  NULL,
	"price"  	int(11) COMMENT '판매가'  NULL,
	"amount" 	int(11) COMMENT '재고'  NULL,
	PRIMARY KEY("prod_cd")
)
COMMENT = '상품정보' ;
CREATE TABLE "usermanagement" ( 
	"user_type"	varchar(45) COMMENT '사용자 형태'  NOT NULL,
	"password" 	varchar(45) COMMENT '비밀번호'  NULL,
	PRIMARY KEY("user_type")
)
COMMENT = '사용자정보' ;


INSERT INTO usermanagement(user_type, password) 
    VALUES('관리자', 'java2020');

INSERT INTO usermanagement(user_type, password) 
    VALUES('일반', 'java2020');
