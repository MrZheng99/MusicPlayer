package com.zj.musicplayer.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.controller.DownLoadSong;
import com.zj.musicplayer.controller.JoinAndCancleLove;
import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.DateUtil;
import com.zj.musicplayer.utils.ImageUtil;

public class SongInfoUi extends Composite {

	private List listLyric;
	private int lineNum;
	private int width;
	private int height;
	private Composite composite;
	private Label labelSinger;
	private Label labelSongName;
	private Label labelAlbumName;
	private Label labelDuration;
	private Label labelLove;
	private Label labelDownload;
	private Label labelPic;
	private Label labelDownloadTip;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SongInfoUi(Composite parent, int style) {
		super(parent, style);

		setLayout(new FillLayout(SWT.HORIZONTAL));
		width = parent.getSize().x;
		height = parent.getSize().y;

		initView();

		initEvent();

	}

	private void initEvent() {
		labelDownload.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				labelDownload.setImage(ImageUtil.scaleImage("src/images/download_normal.png", 30, 30));

			}

			@Override
			public void mouseHover(MouseEvent e) {
				labelDownload.setImage(ImageUtil.scaleImage("src/images/download_highlight.png", 30, 30));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelDownload.setImage(ImageUtil.scaleImage("src/images/download_highlight.png", 30, 30));

			}
		});
		// 监听滑轮事件
		listLyric.setSelection(0);
		listLyric.addMouseWheelListener(new MouseWheelListener() {
			public void mouseScrolled(MouseEvent e) {
				if (e.count == 3) {
					if (listLyric.getSelectionIndex() >= 5) {
						listLyric.setSelection(listLyric.getSelectionIndex() - 5);
					} else {
						listLyric.setSelection(0);
					}

				} else {
					if (e.count == -3) {
						if (lineNum - 5 > listLyric.getSelectionIndex()) {
							listLyric.setSelection(listLyric.getSelectionIndex() + 5);
						} else {
							listLyric.setSelection(lineNum);

						}
					}
				}

			}
		});
		// 加入喜欢列表
		labelLove.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				JoinAndCancleLove love = new JoinAndCancleLove();
				love.exe();

			}

		});
		// 下载
		labelDownload.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				DownLoadSong downLoadSong = new DownLoadSong();
				downLoadSong.download(labelDownloadTip);

			}

		});
	}

	private void initView() {
		composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		composite.setBounds(0, 0, width, height);

		labelPic = new Label(composite, SWT.BORDER);
		labelPic.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelPic.setAlignment(SWT.CENTER);
		labelPic.setBounds(0, height / 4, height / 2, height / 2);

		labelSongName = new Label(composite, SWT.NONE);
		labelSongName.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		labelSongName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelSongName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSongName.setBounds(height / 2, 0, 150, 30);

		labelSinger = new Label(composite, SWT.NONE);
		labelSinger.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSinger.setBounds(height / 2, 35, 150, 30);

		labelAlbumName = new Label(composite, SWT.NONE);
		labelAlbumName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelAlbumName.setBounds(height / 2 + 150, 35, 250, 30);

		labelDuration = new Label(composite, SWT.NONE);
		labelDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelDuration.setBounds(height / 2 + 400, 35, 100, 30);

		labelLove = new Label(composite, SWT.NONE);

		labelLove.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelLove.setBounds(height / 8, 3 * height / 4 + 15, 30, 30);
		labelDownload = new Label(composite, SWT.NONE);
		labelDownloadTip = new Label(composite, SWT.NONE);
		labelDownload.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelDownload.setBounds(height / 4, 3 * height / 4 + 15, 30, 30);
		labelDownload.setImage(ImageUtil.scaleImage("src/images/download_normal.png", 30, 30));
		labelDownloadTip.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelDownloadTip.setBounds(height / 4, 3 * height / 4 + 45, 100, 30);
		listLyric = new List(composite, SWT.MULTI);

		listLyric.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		listLyric.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		listLyric.setBounds(height / 2, 80, width / 2, height - 80);
		ConstantData.component.put("compsiteSongInfo_loveLabel", labelLove);

		refresh();
		System.out.println("查看歌曲详情");
	}

	@Override
	protected void checkSubclass() {
	}

	private void readLyric(String lyricPath) {
		File fileLyric = new File(lyricPath);
		if (!fileLyric.exists() || !fileLyric.isFile()) {
			listLyric.add("暂无歌词");
			return;
		}
		System.out.println(lyricPath);
		lineNum = 0;
		listLyric.removeAll();
		String lyric = null;
		FileReader wr = null;
		try {
			wr = new FileReader(fileLyric);

			BufferedReader br = new BufferedReader(wr);

			while ((lyric = br.readLine()) != null) {// 使用readLine方法，一次读一行
				listLyric.add(lyric);
				lineNum += 1;

			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void refresh() {
		Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);
		SongInfoDao songInfoDao = new SongInfoDao();

		String songId = map.get("songid");
		String userId = ConstantData.currentLoginData.get("userid");
		if (songInfoDao.findLove(songId, userId).size() == 1) {

			labelLove.setImage(ImageUtil.scaleImage("src/images/love_full.png", 19, 19));

		} else {
			labelLove.setImage(ImageUtil.scaleImage("src/images/love_normal.png", 30, 30));

		}
		labelPic.setImage(ImageUtil.getImage(map.get("imageurl"), height / 2, height / 2));
		labelSongName.setText(map.get("songname"));
		labelSinger.setText("演唱: " + map.get("singername"));
		labelAlbumName.setText("专辑：" + map.get("albumname"));
		labelDuration.setText("时长：" + DateUtil.millisecondToMinuteSecond(map.get("duration")));
		readLyric(map.get("lyricurl"));
	}

}
