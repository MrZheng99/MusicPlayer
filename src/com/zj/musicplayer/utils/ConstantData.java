package com.zj.musicplayer.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Clip;

import org.eclipse.swt.widgets.Text;

import com.zj.musicplayer.controller.PlayerMusic;
import com.zj.musicplayer.view.SearchSongUi;
import com.zj.musicplayer.view.SongInfoUi;

import javazoom.jl.player.Player;

public class ConstantData {
	// public static Map<String, String> currentLoginData = null;// 登录用户信息
	public static Text compositeTopTextSearch = null;
	public static SearchSongUi searchSongUi = null;
	public static SongInfoUi songInfoUi = null;

	public static Map<String, Object> component = new HashMap<String, Object>();

	public static List<Map<String, String>> listSongInfo;// 存放当前播放列表
	public static int playIndex = -1; // 播放到哪首歌

	public static boolean playing = false; // 是不是正在播放
	public static int stopPonit = 0; // 暂停点
	public static Player player = null; // 播放器
	public static boolean playNew = false; // 是不是自己切换一首歌

	public static Map<String, String> currentLoginData;

	/************************/
	public static Clip clip = null;
	public static PlayerMusic MM = null;
	public static int mplayIndex = -1;
	public static boolean muteFlag = false;

}
