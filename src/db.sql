-- oracle
-- 用户表
drop table USERINFO;
create table USERINFO(
    userId number(10) primary key,
    userName varchar2(16) unique not null,
    userPassword varchar2(100) not null,
    headImage blob,
    email varchar2(50) unique not null,
    sex number(2),
    age number(2),
    state number(2),
    isOnline number(2)
);
create sequence seq_users_id start with 1;
-- 专辑表
--create table SONGS(
  --id int primary key auto_increment,
  --name varchar(50) not null
  --singer varchar

--);
-- 歌曲表
create table SONGS(
  songId number(10) primary key,
  songName varchar2(100) not null,
  duration number(10) not null,
  albumName  varchar2(100),
  singerName varchar2(100) not null,
  songUrl varchar2(100) not null,
  imageUrl varchar2(100),
  lyricUrl varchar2(100)
);
drop table SONGS;
drop table SONGSHEET;
-- 歌单表(多对多)
create table SONGSHEET(
  ssid number(10) primary key,
  ssname varchar2(50),
  songId number(10) not null,
  userId number(10) not null,
  constraint FK_ONLINEUSERS_userId foreign key(userId) references USERINFO(userId),
  constraint FK_ONLINEUSERS_songId foreign key(songId) references SONGS(songId),
  state number(2)

);
create sequence seq_songsheet_id start with 1;

-- 收藏歌单表
drop table COLLECTIONSONGSHEET;

create table COLLECTIONSONGSHEET(
  cssid number(10) primary key,
  cssname varchar2(50),
  songId number(10) not null,
  userId number(10) not null,
  state number(2),
  constraint FK_COLLECTIONSONGSHEET_userId foreign key(userId) references USERINFO(userId),
  constraint FK_COLLECTIONSONGSHEET_songId foreign key(songId) references SONGS(songId)

);
create sequence seq_collectionSongSheet_id start with 1;
select *from SONGS;