package com.zj.musicplayer.model;

import java.util.List;
import java.util.Map;

public class SongInfoDao {
	public List<Map<String, String>> findAllByName(String name) {
		DBHelper db = new DBHelper();
		String sql = "select *from SONGS where songName like '%'||?||'%'";
		List<Map<String, String>> listSong = db.querysString(sql, name);
		// 优先歌曲名称
		if (listSong != null && !listSong.isEmpty()) {
			return listSong;
		} else {
			sql = "select *from SONGS where singerName like '%'||?||'%'";
			return db.querysString(sql, name);
		}
	}

	public Map<String, String> findById(String songId) {
		DBHelper db = new DBHelper();
		String sql = "select *from SONGS where songId=?";
		return db.queryString(sql, songId);
	}

	public Map<String, String> findByName(String songName) {
		DBHelper db = new DBHelper();
		String sql = "select *from SONGS where songName=?";
		return db.queryString(sql, songName);
	}

	public Map<String, String> findLove(String songId, String userid) {
		DBHelper db = new DBHelper();
		String sql = "select ssid from SONGSHEET where songId=? and userId=? and ssname=? and state=?";
		return db.queryString(sql, songId, userid, "我的喜欢", 1);
	}

	public List<Map<String, String>> findAllLove(String userId) {
		DBHelper db = new DBHelper();
		String sql = "select s.* from SONGS s,SONGSHEET t where t.userId=? and t.ssname=? and t.state=? and s.songId=t.songId";
		return db.querysString(sql, userId, "我的喜欢", 1);
	}

	public int cancelLove(String songId, String userId) {

		DBHelper db = new DBHelper();
		return db.update("update SONGSHEET set state=? where ssname=? and songId=? and userId=?", 0, "我的喜欢", songId,
				userId);

	}

	public int joinLove(String songId, String userId) {
		DBHelper db = new DBHelper();
		String sql = "select ssid from SONGSHEET where songId=? and userId=? and ssname=?";
		Map<String, String> rs = db.queryString(sql, songId, userId, "我的喜欢");
		if (rs.size() >= 1) {
			return db.update("update SONGSHEET set state=? where ssname=? and songId=? and userId=?", 1, "我的喜欢", songId,
					userId);
		}
		return db.update(
				"insert into SONGSHEET(ssid,ssname,songId,userId,state) values(seq_songsheet_id.nextval,?,?,?,?)",
				"我的喜欢", songId, userId, 1);

	}

	/**
	 * 
	 * @param number 需要记录条数 小于150
	 * @return
	 */
	public List<Map<String, String>> findByRandom(int number) {
		DBHelper db = new DBHelper();
		String sql = "select *from SONGS sample(70) where rownum<=?";
		return db.querysString(sql, number);
	}

}
