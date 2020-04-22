package com.zj.musicplayer.model;

import java.util.List;
import java.util.Map;

public class SongInfoDao {
	public List<Map<String, String>> findByCondition(String name) {
		DBHelper db = new DBHelper();
		String sql = "select songName,duration,albumName,singerName from SONGS where songName like '%'||?||'%'";
		List<Map<String, String>> listSong = db.querysString(sql, name);
		// 优先歌曲名称
		if (listSong != null && !listSong.isEmpty()) {
			return listSong;
		} else {
			sql = "select songName,duration,albumName,singerName from SONGS where singerName like '%'||?||'%'";
			return db.querysString(sql, name);
		}
	}

}
