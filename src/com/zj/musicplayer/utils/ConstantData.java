package com.zj.musicplayer.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Text;

import com.zj.musicplayer.controller.PlayMusicThread;
import com.zj.musicplayer.view.SearchSongUi;

import javazoom.jl.player.Player;

public class ConstantData {
//	public static Map<String, String> currentLoginData = null;// 登录用户信息
	public static Text compositeTopTextSearch = null;
	public static SearchSongUi searchSongUi = null;
//	public static GoodsTypeUi goodsTypeUi = null;
//	public static AddGoodsUi addGoodsUi = null;
//	public static ViewGoodsUi viewGoodsUi = null;
//	public static BuyGoodsUi buyGoodsUi = null;
//	public static MPlayMusicThread mplayMusicThread = null;
//	public static ClipMusic clipMusic = new ClipMusic();

	public static Map<String, Object> component = new HashMap<String, Object>();

	public static List<Map<String, String>> listSongInfo;// 存放当前播放列表
	public static int playIndex = -1;
	public static Player player = null;
	public static boolean playing = false;
//	public static AudioClip audioClip = null;
	public static int stopPonit = 0;
	public static int playPonit = 0;

	public static int mp3Length = 0;
	public static int mp3Time = 0;
	public static PlayMusicThread playMusicThread = null; // 控制播放的线程
	public static Thread progrecessThread = null; // 控制进度条的线程
//	public static Thread startPlayThread = null;// 控制暂停后点击开始的线程
//	public static Thread stopPlayThread = null;// 控制暂停的线程
	public static boolean stop = false;
	public static boolean changeSong = false;
	public static boolean playNew = false;

}
