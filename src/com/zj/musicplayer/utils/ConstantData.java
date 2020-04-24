package com.zj.musicplayer.utils;

import java.applet.AudioClip;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Text;

import com.zj.musicplayer.controller.Play;
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
	public static PlayMusicThread playMusicThread = null;
	public static Map<String, Object> component = new HashMap<String, Object>();

	public static List<Map<String, String>> listSongInfo;// 存放当前播放列表
	public static int playIndex = -1;
	public static Player player = null;
	public static Play playOneSong = null;
	public static boolean playing = false;
	public static AudioClip audioClip = null;
	public static int stopPonit = 0;
}
