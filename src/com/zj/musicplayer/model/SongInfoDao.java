package com.zj.musicplayer.model;

import java.util.List;
import java.util.Map;

public class SongInfoDao {
	public List<Map<String, String>> findAllByName(String name) {
		DBHelper db = new DBHelper();
//		String sql = "select songName,songId,duration,albumName,singerName from SONGS where songName like '%'||?||'%'";

		String sql = "select *from SONGS where songName like '%'||?||'%'";
		List<Map<String, String>> listSong = db.querysString(sql, name);
		// 优先歌曲名称
		if (listSong != null && !listSong.isEmpty()) {
			return listSong;
		} else {
			sql = "select *from SONGS where singerName like '%'||?||'%'";
//			sql = "select songName,songId,duration,albumName,singerName from SONGS where singerName like '%'||?||'%'";
			return db.querysString(sql, name);
		}
	}

	public Map<String, String> findById(String songId) {
		DBHelper db = new DBHelper();
		String sql = "select *from SONGS where songId=?";
		return db.queryString(sql, songId);
	}

}
