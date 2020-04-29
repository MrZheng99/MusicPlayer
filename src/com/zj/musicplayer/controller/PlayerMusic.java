package com.zj.musicplayer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;

import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.DateUtil;
import com.zj.musicplayer.utils.ImageUtil;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**
 * 
 * @description：播放器
 * @author ZJ
 * @date 2020年4月26日 下午8:44:59
 */
public class PlayerMusic {
	private AudioInputStream audioInputStream;// 音频输入流
	public AudioFormat format;// 音频流格式
	public boolean playStartStatus = true; // 启动状态
	public Thread thread = null; // 播放线程

	private long musicAllTime = 0; // 歌曲时长
	private long musicCurrentTime = 0; // 当前播放时间

	private Label labelPlayStop;
	private Label labelMusicAllTime;
	private ProgressBar progressBar; // 进度条

	private Label labelMusicCurrentTime;
	private Label labelSongPic;
	private Label labelSinger;
	private Label labelSongName;
	private Scale scaleVolume;
	private FloatControl gainControl;
	private float volume;
	private Label labelJoinLove;

	public PlayerMusic(Label labelPlayStop, Label labelMusicAllTime, ProgressBar progressBar,
			Label labelMusicCurrentTime, Label labelSongPic, Label labelSinger, Label labelSongName,
			Label labelJoinLove, Scale volume) {
		super();
		this.labelPlayStop = labelPlayStop;
		this.labelMusicAllTime = labelMusicAllTime;
		this.progressBar = progressBar;
		this.labelMusicCurrentTime = labelMusicCurrentTime;
		this.labelSongPic = labelSongPic;
		this.labelSinger = labelSinger;
		this.labelSongName = labelSongName;
		this.labelJoinLove = labelJoinLove;
		this.scaleVolume = volume;
	}

	/**
	 * 读取音频
	 */
	public void readMusic(String songUrl) {

		try {
			MpegAudioFileReader mp = new MpegAudioFileReader();
			AudioInputStream in = mp.getAudioInputStream(new URL(songUrl));
			AudioFormat baseFormat = in.getFormat();
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);

			// 初始化Clip
			ConstantData.clip = AudioSystem.getClip();
			ConstantData.clip.open(audioInputStream);

			musicAllTime = ConstantData.clip.getMicrosecondLength() / 1000; // 获取音频文件的时长
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放音乐
	 */
	@SuppressWarnings("deprecation")
	public void start(String lyricPath, String songUrl) {
		// 先结束以前的歌曲
		if (ConstantData.clip != null) {
			ConstantData.clip.close();
			ConstantData.clip = null;
		}
		ConstantData.playing = true;

		if (thread != null) {
			playStartStatus = false;
			thread.stop();
		}
		// 刷新歌曲详情版面
		if (ConstantData.songInfoUi != null) {
			ConstantData.songInfoUi.refresh();
		}
		// 刷新左边
		final Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);

		refreshLeftInfo(map);

		progressBar.setSelection(0);

		readMusic(songUrl);
		ConstantData.clip.start();
		gainControl = (FloatControl) ConstantData.clip.getControl(FloatControl.Type.MASTER_GAIN);

		scaleVolume.setSelection((int) (1.1625 * gainControl.getValue() + 93));

		// 同步歌词和进度条
		thread = new Thread() {
			@Override
			public void run() {

				playStartStatus = true;
//此处代码块上锁，保证了进度条和时间只能被当前线程修改
				synchronized (thread) {
					while (playStartStatus) {
						musicCurrentTime = ConstantData.clip.getMicrosecondPosition() / 1000;

						// 进度条
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								labelMusicCurrentTime.setText(DateUtil.millisecondToMinuteSecond(musicCurrentTime));
								progressBar.setSelection((int) ((float) musicCurrentTime / musicAllTime * 100));
							}
						});

						if (musicCurrentTime == musicAllTime) {
							break;
						}

						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// 说明这首歌曲已经播完了
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							labelMusicCurrentTime.setText(DateUtil.millisecondToMinuteSecond(musicCurrentTime));
							progressBar.setSelection(100);
						}
					});

					nextSong();
				}
			}
		};

		thread.start();
	}

	public void nextSong() {

		ConstantData.mplayIndex++;
		if (ConstantData.mplayIndex >= ConstantData.listSongInfo.size()) {
			ConstantData.mplayIndex = 0;
		}
		final Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {

				ConstantData.MM.start(map.get("lyricurl"), map.get("songurl"));
			}
		});

	}

	public void lastSong() {

		ConstantData.mplayIndex--;
		if (ConstantData.mplayIndex == -1) {
			ConstantData.mplayIndex = 0;
		}
		final Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				refreshLeftInfo(map);
				ConstantData.MM.start(map.get("lyricurl"), map.get("songurl"));
			}
		});

	}

	public void refreshLeftInfo(Map<String, String> map) {
		SongInfoDao songInfoDao = new SongInfoDao();
		Map<String, String> map2 = songInfoDao.findLove(map.get("songid"), ConstantData.currentLoginData.get("userid"));
		if (map2.size() == 1) {
			labelJoinLove.setImage(ImageUtil.scaleImage("src/images/love_full.png", 19, 19));
		} else {
			labelJoinLove.setImage(ImageUtil.scaleImage("src/images/love_normal.png", 19, 19));

		}
		String songName = map.get("songname").trim();

		if (songName.length() > 5) {
			songName = songName.substring(0, 4) + "..";
		}
		String singername = map.get("singername").trim();

		if (singername.length() > 5) {
			singername = singername.substring(0, 4) + "..";
		}
		labelSongName.setText(songName);

		labelSinger.setText(singername);
		labelSongPic.setImage(ImageUtil.getImage(map.get("imageurl"), 38, 38));
		labelPlayStop.setImage(ImageUtil.scaleImage("src/images/stop_normal.png", 30, 30));
		labelMusicAllTime.setText(DateUtil.millisecondToMinuteSecond(map.get("duration")));
	}

	/**
	 * 暂停
	 */
	public void pause() {
		ConstantData.clip.stop();
		ConstantData.playing = false;
		new Thread(new Runnable() {
			@Override
			public void run() {

				// 获取thread线程锁，即wait在thread线程中使用，保证thread线程同步
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 继续播放
	 */
	public void continuePlay() {
		ConstantData.clip.start();
		ConstantData.playing = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 保证线程同步
				synchronized (thread) {
					thread.notify();
				}
			}
		}).start();
	}

	/**
	 * 停止
	 */
	@SuppressWarnings("deprecation")
	public void closePlay() {
		if (ConstantData.clip == null) {
			return;
		}
		ConstantData.clip.close();
		ConstantData.clip = null;
		playStartStatus = false;
		thread.stop();
	}

	public void loop() {
		final Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				refreshLeftInfo(map);
				ConstantData.MM.start(map.get("lyricurl"), map.get("songurl"));
			}
		});
	}

	public void openMute() {
		volume = gainControl.getValue();
		gainControl.setValue(gainControl.getMinimum());
	}

	public void openVoice() {

		gainControl.setValue(volume);
	}

	public void setVolume(int selection) {
		gainControl.setValue((float) 0.860206 * selection - 80);
	}
}