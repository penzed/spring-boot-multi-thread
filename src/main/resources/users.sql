CREATE TABLE users
(
  id        varchar(36) NOT NULL COMMENT '主键id'
    PRIMARY KEY,
  userName  varchar(32) NULL COMMENT '用户名',
  passWord  varchar(32) NULL COMMENT '密码',
  user_sex  varchar(32) NULL,
  nick_name varchar(32) NULL
)
  CHARSET = utf8;

