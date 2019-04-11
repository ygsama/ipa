--------------------------------------------------------
--                   建表语句
--------------------------------------------------------

CREATE TABLE sys_permission
(
	NO VARCHAR(20) PRIMARY KEY NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	URL VARCHAR(200) NOT NULL,
	METHOD VARCHAR(10) NOT NULL,
  CATALOG int(1) DEFAULT 2 NOT NULL comment '1-系统权限; 2-用户权限; 3-公共权限',
	NOTE VARCHAR(200)
);


CREATE TABLE sys_role
(
    NO int(11) PRIMARY KEY NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    CATALOG int(11) DEFAULT 2 NOT NULL,
    NOTE VARCHAR(200) default NULL
);


CREATE TABLE sys_menu
(
	NO VARCHAR(10) PRIMARY KEY NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	MENU_FATHER VARCHAR(10),
	URL VARCHAR(200) comment '菜单链接',
	MENU_LEVEL int(11) comment '菜单层次',
	MENU_ORDER int(11) comment '顺序',
	NOTE VARCHAR(30),
  MENU_ICON VARCHAR(50) comment '菜单图标',
	MENU_SIZE int default 2 comment '菜单大小',
	MENU_BG VARCHAR(30) default NULL comment '菜单背景颜色',
	BUTTON_TAG int default 0 comment '0-父级菜单; 1-接口型按钮; 2-菜单型按钮',
	BUTTON VARCHAR(20) default NULL,
   FOREIGN KEY (BUTTON) REFERENCES sys_permission(NO)
);
ALTER TABLE sys_menu ADD CONSTRAINT sys_menu_ibfk_2 FOREIGN KEY(MENU_FATHER) REFERENCES sys_menu(NO);



CREATE TABLE sys_user
(
	USERNAME VARCHAR(20) PRIMARY KEY NOT NULL COMMENT '用户账号',
	PASSWORD VARCHAR(80) NOT NULL COMMENT  '用户密码（BCrypt加密保存）',
	NAME VARCHAR(50) NOT NULL  COMMENT  '人员姓名',
	STATUS INT NOT NULL default 1 COMMENT  '用户状态 [-1:新增, 0:停用, 1:启用, 2:锁定]',
	ONLINE_FLAG INT default -1 NOT NULL COMMENT  '在线状态 [-1:从未在线, 1:在线, 0:离线]',
	PHONE VARCHAR(20) COMMENT  '办公电话',
	MOBILE VARCHAR(20) COMMENT  '手机号码',
	EMAIL VARCHAR(40) COMMENT  '电子邮件',
	PHOTO VARCHAR(50) COMMENT  '头像',
	LOGIN_IP VARCHAR(20) COMMENT  '上次登录IP',
	LOGIN_TIME VARCHAR(20) COMMENT  '上次登录时间（YYYY-MM-DD HH:MM24:SS）',
	LOGIN_TERM VARCHAR(50) COMMENT  '登录终端:终端类型|版本号,如msie|11.0',
	PASSWORD_EXPIRATION VARCHAR(10) COMMENT  '密码到期日（YYYY-MM-DD）',
	PASSWORD_ERROR int default 0 not null COMMENT  '密码错误次数'
);


create table sys_user_role
(
	USERNAME VARCHAR(20) NOT NULL,
	ROLE_NO int NOT NULL,
  PRIMARY KEY (USERNAME, ROLE_NO),
  CONSTRAINT sys_user_role_fk_1 FOREIGN KEY (USERNAME) REFERENCES sys_user (USERNAME),
  CONSTRAINT sys_user_role_fk_2 FOREIGN KEY (ROLE_NO) REFERENCES sys_role (NO)
);

CREATE TABLE sys_role_menu
(
    ROLE_NO int NOT NULL,
    MENU_NO VARCHAR(10) NOT NULL,
    PRIMARY KEY (ROLE_NO, MENU_NO),
    CONSTRAINT sys_role_menu_fk_1 FOREIGN KEY (ROLE_NO) REFERENCES sys_role (NO),
    CONSTRAINT sys_role_menu_fk_2 FOREIGN KEY (MENU_NO) REFERENCES sys_menu (NO)
);

