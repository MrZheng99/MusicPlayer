package com.zj.musicplayer.model;

import java.util.Map;

public class UserInfoDao {

	public Map<String, String> login(String account, String pwd) {
		DBHelper db = new DBHelper();
		return db.queryString("select userName,userId from USERINFO where userName=? and userPassword=?", account, pwd);
	}

	public boolean existName(String account) {
		DBHelper db = new DBHelper();
		Map<String, String> rs = db.queryString("select userName from USERINFO where userName=?", account);
		if (rs.size() >= 1) {
			return true;
		}
		return false;
	}

	public int regist(String account, String pwd, String email, byte[] pic) {
		DBHelper db = new DBHelper();
		return db.update(
				"insert into USERINFO(userId,userName,userPassword,headImage,email,state) values(seq_users_id.nextval,?,?,?,?,1)",
				account, pwd, pic, email);
	}

	public byte[] queryHeadImage(String account) {
		DBHelper db = new DBHelper();

		Map<String, Object> map = db.query("select  headImage from USERINFO where userName=?", account);
		byte[] bt = (byte[]) map.get("headimage");
		return bt;
	}
}
