package com.zj.musicplayer.model;

import java.util.Map;

public class UserInfoDao {

	public Map<String, String> login(String account, String pwd) {
		DBHelper db = new DBHelper();
		return db.queryString("select userName from USERINFO where userName=? and userPassword=?", account, pwd);
	}

	public Map<String, String> regist(String account, String pwd) {
		DBHelper db = new DBHelper();
		return db.queryString("select userName from USERINFO where userName=? and userPassword=?", account, pwd);
	}
}
