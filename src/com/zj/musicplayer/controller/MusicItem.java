package com.zj.musicplayer.controller;

import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.view.DownloadSongUi;
import com.zj.musicplayer.view.FindMusicUi;
import com.zj.musicplayer.view.LoveUi;
import com.zj.musicplayer.view.SearchSongUi;

/**
 * 
 * @description：搜索到的（喜欢的）歌曲列表每项的事件
 * 
 * @author ZJ
 * @date 2020年4月30日 下午6:01:14
 */
public class MusicItem {
	private Label labelSongName;
	private Label labelSingerName;
	private Label labelAlbumName;
	private Label labelDuration;
	private MenuItem menuItemDownload;
	private String uiName;
	public static final String UI_SEARCH = "SEARCH_SONG_UI";
	public static final String UI_LOVE = "LOVE_SONG_UI";
	public static final String UI_FIND = "FIND_SONG_UI";
	public static final String UI_DOWNLOAD = "DOWNLOAD_SONG_UI";

	public MusicItem(String uiName, Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration, MenuItem menuItemDownload) {
		this.uiName = uiName;
		this.labelSongName = labelSongName;
		this.labelSingerName = labelSingerName;
		this.labelAlbumName = labelAlbumName;
		this.labelDuration = labelDuration;
		this.menuItemDownload = menuItemDownload;
		bindEvent(labelSongName);
		bindEvent(labelSingerName);
		bindEvent(labelAlbumName);
		bindEvent(labelDuration);
		initMenuItemEvent();
	}

	public MusicItem(String uiName, Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration) {
		this.uiName = uiName;
		this.labelSongName = labelSongName;
		this.labelSingerName = labelSingerName;
		this.labelAlbumName = labelAlbumName;
		this.labelDuration = labelDuration;
		bindEvent(labelSongName);
		bindEvent(labelSingerName);
		bindEvent(labelAlbumName);
		bindEvent(labelDuration);
	}

	// 事件绑定
	public void bindEvent(Control control) {
		control.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				setMouseTrackEnterEvent();
			}

			@Override
			public void mouseExit(MouseEvent e) {
				setMouseTrackExitEvent();
			}
		});
		control.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				setMouseUpEvent();

			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				setMouseDoubleClickEvent();

			}
		});
	}

	protected void initMenuItemEvent() {
		// 点击下载
		menuItemDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DownLoadSong loadSong = new DownLoadSong();
				loadSong.download(labelSongName.getText().trim());
			}
		});
	}

	protected void setMouseDownEvent() {

		labelSongName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

	}

	protected void setMouseTrackEnterEvent() {

		labelSongName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

	}

	protected void setMouseTrackExitEvent() {
		labelSongName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
	}

	protected void setMouseUpEvent() {
		System.out.println(labelSongName.getText());
		labelSongName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
	}

	protected void setMouseDoubleClickEvent() {
		if (MessageDialog.openConfirm(labelAlbumName.getShell(), "播放音乐", "是否播放:" + labelSongName.getText())) {
			switch (uiName) {
			case UI_LOVE:
				ConstantData.listSongInfo = LoveUi.listLoveSong;
				ConstantData.uiFlag = UI_LOVE;
				break;
			case UI_SEARCH:
				ConstantData.listSongInfo = SearchSongUi.listSearchSong;
				ConstantData.uiFlag = UI_SEARCH;

				break;
			case UI_DOWNLOAD:
				ConstantData.listSongInfo = DownloadSongUi.listDownloadSong;
				ConstantData.uiFlag = UI_DOWNLOAD;

				break;
			case UI_FIND:
				ConstantData.listSongInfo = FindMusicUi.listRandomSong;
				ConstantData.uiFlag = UI_FIND;

				break;
			default:
				ConstantData.listSongInfo = null;
				ConstantData.uiFlag = null;

				return;
			}
			Map<String, String> map = null;
			for (int i = 0, len = ConstantData.listSongInfo.size(); i < len; i++) {
				map = ConstantData.listSongInfo.get(i);
				if (map.get("songname").equals(labelSongName.getText())
						&& map.get("singername").equals(labelSingerName.getText())) {
					ConstantData.mplayIndex = i;
					final String lyricurl = map.get("lyricurl");
					final String songurl = map.get("songurl");
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							ConstantData.MM.start(lyricurl, songurl);
						}
					});

					return;
				}
			}
		}

	}
}
