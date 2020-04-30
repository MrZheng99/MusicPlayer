package com.zj.musicplayer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ReadAndWriteRegistery;
import com.zj.musicplayer.utils.StringUtil;

/**
 * 
 * @description：扫描指定文件夹下的所有MP3
 * 
 * @author ZJ
 * @date 2020年4月30日 下午9:10:17
 */
public class MusicFileUtil {
	private ReadAndWriteRegistery registery = null;
	private List<Map<String, String>> listDownloadSong = null;

	public List<Map<String, String>> scanFolder() {
		registery = new ReadAndWriteRegistery();
		String pathOld = registery.findInfo("DOWNLOAD_PATH");
		String path = null;

		if (StringUtil.checkNull(pathOld)) {
			path = System.getProperty("user.dir");
		} else {
			path = pathOld;
		}

		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		File[] arrayFiles = file.listFiles();
		SongInfoDao dao = new SongInfoDao();
		listDownloadSong = new ArrayList<Map<String, String>>();
		for (File f : arrayFiles) {
			String fileName = f.getName();
			int index = fileName.lastIndexOf(".");
			String extension = fileName.substring(index + 1);
			if (extension.equals("mp3")) {
				listDownloadSong.add(dao.findByName(fileName.substring(0, index)));
			}

		}
		return listDownloadSong;
	}
}
