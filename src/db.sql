create database MUSICPLAYER default character set utf8;
-- 用户表
create table USERS(
    id int primary key auto_increment,
    name varchar(16) unique not null,
    password varchar(100) not null,
    headImageUrl varchar(80) not null,
    sex char(2),
    age char(2),
    state char(2)
);
-- 用户在线表
create table ONLINEUSERS(
	 id int primary key auto_increment,
	 userId int not null,
	 constraint FK_ONLINEUSERS_id foreign key(userId) references USERS(id)
);
-- 专辑表
--create table SONGS(
	--id int primary key auto_increment,
	--name varchar(50) not null
	--singer varchar

--);
-- 歌曲表
create table SONGS(
	id int primary key auto_increment,
	name varchar(50) not null,
	albumName  varchar(50),
	singer varchar(50) not null,
	duration varchar(5) not null,
	lyricUrl varchar(80),
	songUrl varchar(80) not null
);
-- 歌曲图片表
create table SONGIMAGES(
	id int primary key auto_increment,
	imageUrl varchar(80) not null
);
-- 歌曲和图片关系表（多对多）
create table SONGIMAGERELATION(
	id int primary key auto_increment,
	songId int not null,
	imageId int not null,
	constraint FK_SONGIMAGERELATION_songId foreign key(songId) references SONGS(id),
	constraint FK_SONGIMAGERELATION_imageId foreign key(imageId) references SONGIMAGES(id)
);