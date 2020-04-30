package com.zj.musicplayer.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ReadAndWriteRegistery;
import com.zj.musicplayer.utils.StringUtil;
import com.zj.musicplayer.view.DownloadSongUi;

/**
 * 
 * @description：下载歌曲
 * @author ZJ
 * @date 2020年4月29日 下午3:14:07
 */
public class DownLoadSong {
	/**
	 * 
	 * @param songUrl
	 * @param songName
	 * @return 其他错误 返回-1 文件存在 返回0 下载成功返回1
	 */
	public void download(Label labelTip) {

		if (ConstantData.mplayIndex < 0) {
			return;
		}
		labelTip.setText("正在下载...");
		ReadAndWriteRegistery registery = new ReadAndWriteRegistery();

		String directoryOld = registery.findInfo("DOWNLOAD_PATH");
		String directory = null;
		if (StringUtil.checkNull(directoryOld)) {
			directory = System.getProperty("user.dir");
		} else {
			directory = directoryOld;
		}
		Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);
		String songUrl = map.get("songurl");
		String songName = map.get("songname");
		try {
			// 获取URL对象
			URL url = new URL(songUrl);
			// 根据URL打开链接
			URLConnection connection = url.openConnection();
			// 从连接处获取输入流对象
			InputStream inputStream = connection.getInputStream();
			@SuppressWarnings("static-access")
			String path = new String().format("%s\\%s.mp3", directory, songName);
			File file = new File(path);
			if (file.exists()) {
				labelTip.setText("已有下载！");
				return;
			}

			System.out.println(path);

			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bt = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(bt)) != -1) {
				out.write(bt, 0, len);
			}
			out.close();
			inputStream.close();

		} catch (MalformedURLException e) {
			labelTip.setText("下载失败,请重试");

			e.printStackTrace();
		} catch (IOException e) {
			labelTip.setText("下载失败，请重试");

			e.printStackTrace();
		}
		labelTip.setText("下载成功！");
		if (ConstantData.downloadMusicUi != null) {
			ConstantData.downloadMusicUi.layout();
		}
	}

	public void download(String songName) {

		ReadAndWriteRegistery registery = new ReadAndWriteRegistery();

		String directoryOld = registery.findInfo("DOWNLOAD_PATH");
		String directory = null;
		if (StringUtil.checkNull(directoryOld)) {
			directory = System.getProperty("user.dir");
		} else {
			directory = directoryOld;
		}
		SongInfoDao songInfoDao = new SongInfoDao();
		Map<String, String> songInfo = songInfoDao.findByName(songName);
		String songUrl = songInfo.get("songurl");
		try {
			// 获取URL对象
			URL url = new URL(songUrl);
			// 根据URL打开链接
			URLConnection connection = url.openConnection();
			// 从连接处获取输入流对象
			InputStream inputStream = connection.getInputStream();
			@SuppressWarnings("static-access")
			String path = new String().format("%s\\%s.mp3", directory, songName);
			File file = new File(path);
			if (file.exists()) {
				return;
			}

			System.out.println(path);

			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] bt = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(bt)) != -1) {
				out.write(bt, 0, len);
			}
			out.close();
			inputStream.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		if (ConstantData.downloadMusicUi != null) {
			ConstantData.downloadMusicUi.dispose();
			ConstantData.downloadMusicUi = new DownloadSongUi((Composite) ConstantData.component.get("compositeRight"),
					SWT.NONE);
		}
	}

}
