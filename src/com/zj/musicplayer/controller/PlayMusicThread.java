package com.zj.musicplayer.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.zj.musicplayer.utils.ConstantData;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayMusicThread extends Thread {

	private void playMusic(String songUrl) {
		URL url = null;
		URLConnection con = null;
		Bitstream bitstream = null;
		BufferedInputStream bufferedInputStream = null;
		Header header = null;
		try {
			url = new URL(songUrl);
			con = url.openConnection();
			bufferedInputStream = new BufferedInputStream(con.getInputStream());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bitstream = new Bitstream(bufferedInputStream);
			header = bitstream.readFrame();

			int mp3Length = con.getContentLength();
			int time = (int) header.total_ms(mp3Length);
			System.out.println("ConstantData.stopPonit" + ConstantData.stopPonit);
			bufferedInputStream.skip((int) (mp3Length * (double) ConstantData.stopPonit / time));
			ConstantData.player = new Player(bufferedInputStream);
			ConstantData.playing = true;
			ConstantData.player.play();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BitstreamException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (ConstantData.playing) {
			System.out.println(ConstantData.playIndex);
			System.out.println(ConstantData.listSongInfo.get(ConstantData.playIndex).get("songurl"));
			playMusic(ConstantData.listSongInfo.get(ConstantData.playIndex).get("songurl"));
			ConstantData.playIndex++;
			ConstantData.stopPonit = 0;

			if (ConstantData.playIndex == ConstantData.listSongInfo.size()) {
				ConstantData.playIndex = 0;
			}
		}

	}

	public void startPlay() { // 开始播放
		ConstantData.playing = true;
		new Thread(new Runnable() {
			public void run() {
				ConstantData.playMusicThread = new PlayMusicThread();
				ConstantData.playMusicThread.start();
			}
		}).start();

	}

	public void stopPlay() { // 停止播放
		ConstantData.playing = false;
		ConstantData.stopPonit = ConstantData.player.getPosition();

		new Thread(new Runnable() {
			public void run() {
				ConstantData.playMusicThread.stop();
				ConstantData.player.close();
			}
		}).start();
	}
}
