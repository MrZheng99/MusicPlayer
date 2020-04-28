package com.zj.musicplayer.controller;

import org.eclipse.swt.widgets.Label;

import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ImageUtil;

public class JoinAndCancleLove {

	public void exe() {
		if (ConstantData.mplayIndex == -1) {
			return;
		}
		SongInfoDao songInfoDao = new SongInfoDao();
		Label leftLabel = ((Label) ConstantData.component.get("compsiteLeft_loveLabel"));
		Label rightLabel = ((Label) ConstantData.component.get("compsiteSongInfo_loveLabel"));

		String songId = ConstantData.listSongInfo.get(ConstantData.mplayIndex).get("songid");
		String userId = ConstantData.currentLoginData.get("userid");
		if (songInfoDao.findLove(songId, userId).size() == 1) {
			if (songInfoDao.cancelLove(songId, userId) > 0) {
				if (leftLabel != null) {
					leftLabel.setImage(ImageUtil.scaleImage("src/images/love_normal.png", 19, 19));
				}
				if (rightLabel != null) {
					rightLabel.setImage(ImageUtil.scaleImage("src/images/love_normal.png", 30, 30));
				}
			}

		} else {
			if (songInfoDao.joinLove(songId, userId) > 0) {
				if (leftLabel != null) {
					leftLabel.setImage(ImageUtil.scaleImage("src/images/love_full.png", 19, 19));
				}
				if (rightLabel != null) {
					rightLabel.setImage(ImageUtil.scaleImage("src/images/love_full.png", 30, 30));
				}
			}

		}
	}
}
