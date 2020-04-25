package com.zj.musicplayer.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ImageUtil;

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

			ConstantData.mp3Length = con.getContentLength();
			ConstantData.mp3Time = (int) header.total_ms(ConstantData.mp3Length);
//			System.out.println("ConstantData.stopPonit" + ConstantData.stopPonit);
			bufferedInputStream
					.skip((int) (ConstantData.mp3Length * (double) ConstantData.stopPonit / ConstantData.mp3Time));
			ConstantData.player = new Player(bufferedInputStream);
			if (ConstantData.playNew) {
				ConstantData.playNew = false;
				ConstantData.progrecessThread = new ProcessBarThread();
				ConstantData.progrecessThread.start();
			}
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

			playMusic(ConstantData.listSongInfo.get(ConstantData.playIndex).get("songurl"));

			if (ConstantData.playIndex < ConstantData.listSongInfo.size() - 1) {
				ConstantData.playIndex++;
			} else {
				ConstantData.playIndex = 0;
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {

					Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.playIndex);
					((Label) ConstantData.component.get("compositeLeft_labelSongName")).setText(map.get("songname"));
					((Label) ConstantData.component.get("compositeLeft_labelSinger")).setText(map.get("singername"));
					((Label) ConstantData.component.get("compositeLeft_labelSongPic"))
							.setImage(ImageUtil.getImage(map.get("imageurl"), 38, 38));
					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/stop.png", 30, 30));
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConstantData.playNew = true;
			ConstantData.stopPonit = 0;
			ConstantData.progrecessThread.stop();

		}

	}

	public void startPlay() { // 开始播放
		ConstantData.playing = true;
		ConstantData.playNew = false;
		new Thread(new Runnable() {
			public void run() {
				ConstantData.playMusicThread = new PlayMusicThread();
				ConstantData.playMusicThread.start();
			}
		}).start();

	}

	public void stopPlay() { // 停止播放
		ConstantData.playing = false;
		ConstantData.playNew = false;

		ConstantData.stopPonit = ConstantData.player.getPosition();

		new Thread(new Runnable() {

			public void run() {
				ConstantData.playMusicThread.stop();

				ConstantData.player.close();
			}

		}).start();
	}

	public void nextSong() {
		stopPlay();

		ConstantData.playNew = true;

		if (ConstantData.playIndex != ConstantData.listSongInfo.size() - 1) {
			ConstantData.playIndex++;
		} else {
			ConstantData.playIndex = 0;
		}

		ConstantData.stopPonit = 0;

		startPlay();
	}

	public void lastSong() {
		stopPlay();
		ConstantData.playNew = true;

		if (ConstantData.playIndex != 0) {
			ConstantData.playIndex--;
		} else {
			ConstantData.playIndex = 0;
		}
		ConstantData.stopPonit = 0;

		startPlay();

	}

}
