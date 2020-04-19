package com.zj.musicplayer.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javazoom.jl.player.Player;

public class PlayRomoteMusic {
	private String _songName;

	PlayRomoteMusic(String songName) throws IOException, Exception {
		_songName = songName;
		URL url = new URL("http://music.163.com/song/media/outer/url?id=188620.mp3");
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
		Bitstream bt = new Bitstream(bis);

		// 获取mp3时间长度
		Header header = bt.readFrame();
		int mp3Length = con.getContentLength();
		int time = (int) header.total_ms(mp3Length);
		System.out.println(time / 1000);

		Player player = new Player(bis);
		player.play();

	}

	public static void main(String[] args) throws Exception {
//		PlayRomoteMusic mp3 = new PlayRomoteMusic("贝加尔湖畔");

	}
}
