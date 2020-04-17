--管理员信息表
create table admin(
  id number(6) primary key,
  name varchar2(100) not null unique,
  pwd varchar2(100) not null,
  email varchar2(50) unique
);
create sequence seq_admin_id start with 101;

--商品类型表
create table goodstype(
  id number(6) primary key,
  name varchar2(100) not null unique,
  status number(2)
);
create sequence seq_goodstype_id start with 101;
--商品信息
create table goods(
  id number(10) primary key,
  typeid number(6)
    constraint FK_goods_typeid references goodstype(id),
  name varchar2(200) not null unique,
  price number(8,2) not null,
  pic blob,
  store number(8),
  status number(2)
);
create sequence seq_goods_id start with 1001;
insert into table admin values(seq_admin_id.nextval,'tom','123','1737965693@qq.com');