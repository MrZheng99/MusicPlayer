package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.controller.JoinAndCancleLove;
import com.zj.musicplayer.controller.MusicItem;
import com.zj.musicplayer.controller.PlayerMusic;
import com.zj.musicplayer.model.UserInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ImageUtil;

/**
 * 
 * @description：主窗口
 * 
 * @author ZJ
 * @date 2020年5月3日 下午2:34:33
 */
public class MainUi {

	protected Shell shell;
	private int shellHeight;
	private int shellWidth;
	private Dimension dimension;

	private Composite compositeTop; // 顶部
	private Composite compositeLeft; // 左边
	private Composite compositeRight; // 右边
	private Composite compositeBottom; // 底部

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		// 居中显示
		shell = new Shell(SWT.NONE);
		createCompsite();
		initTop();
		initLeft();
		initBottom();
		initShellEvent();
		setShellInitSize();
		ConstantData.MM = new PlayerMusic(labelPlayStop, labelMusicAllTime, progressBar, labelMusicCurrentTime,
				labelSongPic, labelSinger, labelSongName, labelJoinLove, scaleSetVolume);

		saveConstantData();
	}

	private void setShellInitSize() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setBounds(dimension.width / 4, dimension.height / 4, dimension.width / 2, dimension.height / 2);

	}

	private void saveConstantData() {
		if (ConstantData.compositeTopTextSearch == null && textSearch != null) {
			ConstantData.compositeTopTextSearch = textSearch;
		}
		ConstantData.component.put("compsiteLeft_loveLabel", labelJoinLove);
		ConstantData.component.put("compositeRight", compositeRight);
	}

	private StackLayout stackLayout = new StackLayout();

	private void createCompsite() {
		shell.setLayout(null);
		shell.setImage(ImageUtil.scaleImage("src/images/title.png", 50, 50));

		compositeTop = new Composite(shell, SWT.NONE);
		compositeTop.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		compositeTop.setLayout(null);

		compositeLeft = new Composite(shell, SWT.NONE);
		compositeLeft.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		compositeLeft.setLayout(null);

		compositeRight = new Composite(shell, SWT.BORDER);
		compositeRight.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		compositeRight.setLayout(stackLayout);

		compositeBottom = new Composite(shell, SWT.NONE);
		compositeBottom.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		compositeBottom.setLayout(null);

	}

	private Label labelClose;
	private Label labelMini;
	private Label labelSearch;
	private Label labelPlayerTitle;
	private Text textSearch;
	private Label labelMax;
	private Label labelUserName;
	private Label labelHead;
	private Label labelSetting;
	private Label labelChangeTheme;
	private final int[] COLOR = new int[] { SWT.COLOR_RED, SWT.COLOR_GRAY, SWT.COLOR_DARK_GREEN, SWT.COLOR_CYAN };

	private void initTop() {
		labelPlayerTitle = new Label(compositeTop, SWT.NONE);
		labelPlayerTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelPlayerTitle.setAlignment(SWT.CENTER);
		labelPlayerTitle.setText("ZJ-Music Player");
		textSearch = new Text(compositeTop, SWT.BORDER);

		textSearch.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.BOLD));
		textSearch.setText("请输入歌手/歌曲名称");

		labelSearch = new Label(compositeTop, SWT.NONE);

		labelSearch.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSearch.setAlignment(SWT.CENTER);
		labelSearch.setText("搜索");
		labelUserName = new Label(compositeTop, SWT.NONE);

		labelUserName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelHead = new Label(compositeTop, SWT.NONE);
		labelSetting = new Label(compositeTop, SWT.NONE);

		labelSetting.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSetting.setAlignment(SWT.CENTER);
		labelSetting.setText("设置");
		labelChangeTheme = new Label(compositeTop, SWT.NONE);

		labelChangeTheme.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelChangeTheme.setAlignment(SWT.CENTER);
		labelChangeTheme.setText("换肤");

		labelUserName.setAlignment(SWT.CENTER);

		labelHead.setAlignment(SWT.CENTER);
		labelClose = new Label(compositeTop, SWT.NONE);
		labelClose.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelMini = new Label(compositeTop, SWT.NONE);
		labelMini.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelMax = new Label(compositeTop, SWT.NONE);
		labelMax.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelClose.setAlignment(SWT.CENTER);
		labelMax.setAlignment(SWT.CENTER);
		labelMini.setAlignment(SWT.CENTER);
		initCompositeTopEvent();
	}

	private void refreshTop() {

		int compositeTopHeight = compositeTop.getSize().y;
		int compositeTopWidth = compositeTop.getSize().x / 10;

		labelPlayerTitle.setBounds(compositeTopWidth / 6, compositeTopHeight / 4, compositeTopWidth * 2 - 40, 25);

		textSearch.setBounds(compositeTopWidth * 2, compositeTopHeight / 4, compositeTopWidth * 2, 25);

		// labelSearch.setImage(ImageUtil.scaleImage("src/images/search.png", 25, 25));

		labelSearch.setBounds(compositeTopWidth * 4, compositeTopHeight / 4, 50, 25);
		labelHead.setBounds(compositeTopWidth * 6, compositeTopHeight / 4, 25, 25);
		String name = ConstantData.currentLoginData.get("username");
		UserInfoDao userInfoDao = new UserInfoDao();

		labelHead.setImage(ImageUtil.getImage(userInfoDao.queryHeadImage(name), 25, 25));
		labelUserName.setBounds(compositeTopWidth * 6 + 27, compositeTopHeight / 4, compositeTopWidth - 30, 25);
		labelUserName.setText(name);
		labelSetting.setBounds(compositeTopWidth * 8, compositeTopHeight / 4, 50, 25);
		labelChangeTheme.setBounds(compositeTopWidth * 8 - 75, compositeTopHeight / 4, 50, 25);

		labelMini.setBounds(compositeTopWidth * 10 - 90, compositeTopHeight / 5, 25, 25);
		labelMax.setBounds(compositeTopWidth * 10 - 60, compositeTopHeight / 5, 25, 25);
		labelClose.setBounds(compositeTopWidth * 10 - 30, compositeTopHeight / 5, 25, 25);

		labelClose.setImage(ImageUtil.scaleImage("src/images/btn_close_normal.png", 25, 25));
		labelMini.setImage(ImageUtil.scaleImage("src/images/btn_mini_normal.png", 25, 25));
		labelMax.setImage(ImageUtil.scaleImage("src/images/btn_max_normal.png", 25, 25));
		compositeTop.layout();
	}

	private TreeItem tiFindMusic;
	private TreeItem tiDownload;
	private TreeItem tiMyLove;
	private Tree treeFunction;
	private Composite compositeSong;
	private Label labelSongPic;
	private Label labelSinger;
	private Label labelSongName;
	private Label labelJoinLove;

	private void initLeft() {
		treeFunction = new Tree(compositeLeft, SWT.NONE);

		TreeItem tiRecommend = new TreeItem(treeFunction, SWT.NONE);
		tiRecommend.setText("推荐");

		tiFindMusic = new TreeItem(tiRecommend, SWT.NONE);
		tiFindMusic.setText("发现音乐");
		tiRecommend.setExpanded(true);

		TreeItem tiMyMusic = new TreeItem(treeFunction, SWT.NONE);
		tiMyMusic.setText("我的音乐");

		tiDownload = new TreeItem(tiMyMusic, SWT.NONE);
		tiDownload.setText("我的下载");
		tiMyMusic.setExpanded(true);

		TreeItem tiCreateSongList = new TreeItem(treeFunction, SWT.NONE);
		tiCreateSongList.setText("歌单列表");

		tiMyLove = new TreeItem(tiCreateSongList, SWT.NONE);
		tiMyLove.setText("我的喜欢");
		tiCreateSongList.setExpanded(true);

		compositeSong = new Composite(compositeLeft, SWT.NONE);

		compositeSong.setLayout(null);

		labelSongPic = new Label(compositeSong, SWT.CENTER);

		labelSinger = new Label(compositeSong, SWT.NONE);
		labelSinger.setText("歌手");
		labelSongName = new Label(compositeSong, SWT.NONE);
		labelSongName.setText("歌名");
		labelJoinLove = new Label(compositeSong, SWT.CENTER);
		labelJoinLove.setText("喜欢");
		initCompositeLeftEvent();

	}

	private void refreshLeft() {
		int compositeLeftWidth = compositeLeft.getSize().x;
		int compositeLeftHeight = compositeLeft.getSize().y;
		treeFunction.setBounds(0, 0, compositeLeftWidth, compositeLeftHeight - 38);
		compositeSong.setBounds(0, compositeLeftHeight - 38, compositeLeftWidth, 38);
		labelSongPic.setBounds(0, 0, 38, 38);
		labelSongPic.setImage(ImageUtil.scaleImage("src/images/java.jpg", 38, 38));

		labelSongName.setBounds(38, 0, compositeLeftWidth - 38, 19);
		labelSinger.setBounds(38, 19, compositeLeftWidth - 57, 19);
		labelJoinLove.setBounds(compositeLeftWidth - 19, 19, 19, 19);
		labelJoinLove.setImage(ImageUtil.scaleImage("src/images/love_normal.png", 19, 19));
		compositeLeft.layout();
	}

	private Label labelLastSong;
	private Label labelPlayStop;
	private Label labelNextSong;
	private ProgressBar progressBar;
	private Label labelMusicAllTime;
	private Label labelMusicCurrentTime;
	private Label labelMute;
	private Label labelSplite;
	private Label labelMusicList;
	private Scale scaleSetVolume;

	private void initBottom() {
		labelLastSong = new Label(compositeBottom, SWT.NONE | SWT.CENTER);

		labelPlayStop = new Label(compositeBottom, SWT.NONE | SWT.CENTER);

		labelNextSong = new Label(compositeBottom, SWT.NONE | SWT.CENTER);
		progressBar = new ProgressBar(compositeBottom, SWT.NONE | SWT.CENTER);

		progressBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));

		labelMusicCurrentTime = new Label(compositeBottom, SWT.NONE | SWT.CENTER);
		labelSplite = new Label(compositeBottom, SWT.NONE | SWT.CENTER);

		labelMusicAllTime = new Label(compositeBottom, SWT.NONE | SWT.CENTER);
		labelMute = new Label(compositeBottom, SWT.NONE | SWT.CENTER);
		labelMusicList = new Label(compositeBottom, SWT.NONE | SWT.CENTER);

		labelMusicCurrentTime.setText("00:00");
		labelSplite.setText("/");
		labelMusicAllTime.setText("00:00");
		scaleSetVolume = new Scale(compositeBottom, SWT.NONE | SWT.CENTER);
		scaleSetVolume.setSelection(100);
		initCompositeBottomEvent();

	}

	private void refreshBottom() {
		// int compositeBottomWidth = compositeBottom.getSize().x;
		// int compositeBottomHeight = compositeBottom.getSize().y;
		labelLastSong.setBounds(0, 12, 30, 30);
		labelLastSong.setImage(ImageUtil.scaleImage("src/images/lastsong_normal.png", 30, 30));
		labelPlayStop.setBounds(35, 12, 30, 30);
		labelPlayStop.setImage(ImageUtil.scaleImage("src/images/play_normal.png", 30, 30));
		labelNextSong.setBounds(70, 12, 30, 30);
		labelNextSong.setImage(ImageUtil.scaleImage("src/images/nextsong_normal.png", 30, 30));
		progressBar.setBounds(compositeLeft.getSize().x, 22, compositeRight.getSize().x - 300, 10);
		labelMusicCurrentTime.setBounds(compositeBottom.getSize().x - 300, 17, 45, 20);
		labelSplite.setBounds(compositeBottom.getSize().x - 255, 17, 10, 20);
		labelMusicAllTime.setBounds(compositeBottom.getSize().x - 245, 17, 45, 20);
		scaleSetVolume.setBounds(compositeBottom.getSize().x - 195, 12, 100, 20);

		labelMute.setBounds(compositeBottom.getSize().x - 90, 14, 25, 25);
		labelMute.setImage(ImageUtil.scaleImage("src/images/voice_normal.png", 25, 25));
		labelMusicList.setBounds(compositeBottom.getSize().x - 45, 14, 25, 25);
		labelMusicList.setImage(ImageUtil.scaleImage("src/images/musiclist_normal.png", 25, 25));
		compositeBottom.layout();
	}

	private boolean down = false;
	private int clickX;
	private int clickY;
	protected boolean muteFlag = false;

	private void initShellEvent() {
		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {

				shellHeight = shell.getSize().y;
				shellWidth = shell.getSize().x;
				compositeTop.setBounds(0, 0, shellWidth, shellHeight / 10);
				compositeLeft.setBounds(0, shellHeight / 10, 20 + shellWidth / 10, 8 * shellHeight / 10);
				compositeRight.setBounds(shellWidth / 10 + 20, shellHeight / 10, 9 * shellWidth / 10 - 20,
						8 * shellHeight / 10);

				compositeBottom.setBounds(0, 9 * shellHeight / 10, shellWidth, shellHeight / 10);
				compositeRight.setBackgroundImage(ImageUtil.scaleImage("src/images/bk.png", compositeRight.getSize().x,
						compositeRight.getSize().y));

				refreshTop();
				refreshLeft();
				refreshBottom();
			}
		});

	}

	private void initCompositeTopEvent() {
		// 设置位置，即界面随着拖动而移动
		compositeTop.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (down) {
					shell.setLocation(shell.getLocation().x + e.x - clickX, shell.getLocation().y + e.y - clickY);
				}
			}
		});
		// 获取按下时候的位置
		compositeTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				down = true;
				clickX = e.x;
				clickY = e.y;
			}

			// 当鼠标松开时处理
			@Override
			public void mouseUp(MouseEvent e) {
				down = false;
			}
		});
		// 搜索歌曲
		labelSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				if (ConstantData.searchSongUi != null) {
					ConstantData.searchSongUi.dispose();
				}
				ConstantData.searchSongUi = new SearchSongUi(compositeRight, SWT.NONE);
				stackLayout.topControl = ConstantData.searchSongUi;
				compositeRight.layout();
			}
		});
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 回车键监听
				if (e.keyCode == SWT.CR) {
					if (ConstantData.searchSongUi != null) {
						ConstantData.searchSongUi.dispose();
					}
					ConstantData.searchSongUi = new SearchSongUi(compositeRight, SWT.NONE);
					stackLayout.topControl = ConstantData.searchSongUi;
					compositeRight.layout();
				}
			}
		});
		// 点击用户名
		labelUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				ChangeUserInfoUi ui = new ChangeUserInfoUi(shell);
				ui.open();
			}
		});
		// 点击设置
		labelSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				SettingUi win = new SettingUi();
				win.open();
			}
		});
		// 点击换肤
		labelChangeTheme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Random random = new Random();

				compositeTop.setBackground(SWTResourceManager.getColor(COLOR[random.nextInt(4)]));

			}
		});
		labelClose.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if (ConstantData.MM != null) {
					ConstantData.MM.closePlay();
				}
				shell.dispose();

			}
		});
		labelMini.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (shellWidth == dimension.width && shellHeight == dimension.height) {
					setShellInitSize();
				} else {
					shell.setMinimized(true);
				}
			}
		});

		labelClose.addMouseTrackListener(new MouseTrackAdapter() {
			// 不放在图标上的效果
			@Override
			public void mouseExit(MouseEvent e) {
				labelClose.setImage(ImageUtil.scaleImage("src/images/btn_close_normal.png", 25, 25));
			}

			// 放到图标上的效果
			@Override
			public void mouseHover(MouseEvent e) {
				labelClose.setImage(ImageUtil.scaleImage("src/images/btn_close_highlight.png", 25, 25));

			}
			// 点击图标的效果

			@Override
			public void mouseEnter(MouseEvent e) {
				labelClose.setImage(ImageUtil.scaleImage("src/images/btn_close_down.png", 25, 25));
			}
		});

		labelMini.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				labelMini.setImage(ImageUtil.scaleImage("src/images/btn_mini_normal.png", 25, 25));
			}

			@Override
			public void mouseHover(MouseEvent e) {
				labelMini.setImage(ImageUtil.scaleImage("src/images/btn_mini_highlight.png", 25, 25));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelMini.setImage(ImageUtil.scaleImage("src/images/btn_mini_down.png", 25, 25));

			}
		});
		labelMax.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent e) {
				labelMax.setImage(ImageUtil.scaleImage("src/images/btn_max_normal.png", 25, 25));
			}

			@Override
			public void mouseHover(MouseEvent e) {
				labelMax.setImage(ImageUtil.scaleImage("src/images/btn_max_highlight.png", 25, 25));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelMax.setImage(ImageUtil.scaleImage("src/images/btn_max_down.png", 25, 25));

			}

		});
		labelSetting.addMouseTrackListener(new MouseTrackAdapter() {
			// 不放在图标上的效果
			@Override
			public void mouseExit(MouseEvent e) {
				labelSetting.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));

			}

			// 放到图标上的效果
			@Override
			public void mouseHover(MouseEvent e) {
				labelSetting.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelSetting.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

		});
		labelChangeTheme.addMouseTrackListener(new MouseTrackAdapter() {
			// 不放在图标上的效果
			@Override
			public void mouseExit(MouseEvent e) {
				labelChangeTheme.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));

			}

			// 放到图标上的效果
			@Override
			public void mouseHover(MouseEvent e) {
				labelChangeTheme.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelChangeTheme.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

		});
		labelUserName.addMouseTrackListener(new MouseTrackAdapter() {
			// 不放在图标上的效果
			@Override
			public void mouseExit(MouseEvent e) {
				labelUserName.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));

			}

			// 放到图标上的效果
			@Override
			public void mouseHover(MouseEvent e) {
				labelUserName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelUserName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

		});
		labelSearch.addMouseTrackListener(new MouseTrackAdapter() {
			// 不放在图标上的效果
			@Override
			public void mouseExit(MouseEvent e) {
				labelSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));

			}

			// 放到图标上的效果
			@Override
			public void mouseHover(MouseEvent e) {
				labelSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}

			@Override
			public void mouseEnter(MouseEvent e) {
				labelSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}
		});
		// 清除提示信息
		textSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (textSearch.getText().trim().equals("请输入歌手/歌曲名称")) {
					textSearch.setText("");
				}
			}
		});
	}

	private void initCompositeLeftEvent() {
		// 切换面板
		treeFunction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem ti = (TreeItem) e.item; // 获取选择节点
				String itName = ti.getText().trim();
				switch (itName) {
				case "我的喜欢":
					if (ConstantData.loveUi == null) {
						ConstantData.loveUi = new LoveUi(compositeRight, SWT.NONE);
					}
					stackLayout.topControl = ConstantData.loveUi;
					compositeRight.layout();
					break;

				case "发现音乐":
					if (ConstantData.findMusicUi == null) {
						ConstantData.findMusicUi = new FindMusicUi(compositeRight, SWT.NONE);
					}
					stackLayout.topControl = ConstantData.findMusicUi;
					compositeRight.layout();
					break;

				case "我的下载":
					if (ConstantData.downloadMusicUi == null) {
						ConstantData.downloadMusicUi = new DownloadSongUi(compositeRight, SWT.NONE);
					}
					stackLayout.topControl = ConstantData.downloadMusicUi;
					compositeRight.layout();
					break;
				}
			}
		});
		// 加入喜欢列表
		labelJoinLove.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				JoinAndCancleLove love = new JoinAndCancleLove();
				love.exe();

			}

		});
		/*****************/
		labelSongPic.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseEnter(MouseEvent e) {
				labelSongPic.setImage(ImageUtil.scaleImage("src/images/right.png", 38, 38));
				if (ConstantData.listSongInfo != null) {
					labelSongPic.setBackgroundImage(ImageUtil
							.getImage(ConstantData.listSongInfo.get(ConstantData.mplayIndex).get("imageurl"), 38, 38));
				}
			}

			@Override
			public void mouseExit(MouseEvent e) {
				if (ConstantData.listSongInfo != null) {
					labelSongPic.setImage(null);

					labelSongPic.setBackgroundImage(ImageUtil
							.getImage(ConstantData.listSongInfo.get(ConstantData.mplayIndex).get("imageurl"), 38, 38));
				}
			}
		});

		labelSongPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.mplayIndex == -1) {
					return;
				}
				if (ConstantData.songInfoUi != null) {
					ConstantData.songInfoUi.dispose();
				}
				ConstantData.songInfoUi = new SongInfoUi(compositeRight, SWT.NONE);

				stackLayout.topControl = ConstantData.songInfoUi;
				compositeRight.layout();
			}
		});
	}

	private void initCompositeBottomEvent() {
		labelLastSong.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent arg0) {
				labelLastSong.setImage(ImageUtil.scaleImage("src/images/lastsong_normal.png", 30, 30));

			}

			@Override
			public void mouseEnter(MouseEvent arg0) {
				labelLastSong.setImage(ImageUtil.scaleImage("src/images/lastsong_highlight.png", 30, 30));

			}

			@Override
			public void mouseHover(MouseEvent arg0) {
				labelLastSong.setImage(ImageUtil.scaleImage("src/images/lastsong_highlight.png", 30, 30));

			}
		});
		labelPlayStop.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent arg0) {
				if (ConstantData.playing) {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/stop_normal.png", 30, 30));

				} else {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/play_normal.png", 30, 30));

				}

			}

			@Override
			public void mouseHover(MouseEvent arg0) {
				if (ConstantData.playing) {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/stop_highlight.png", 30, 30));
				} else {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/play_highlight.png", 30, 30));

				}

			}

			@Override
			public void mouseEnter(MouseEvent arg0) {
				if (ConstantData.playing) {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/stop_highlight.png", 30, 30));
				} else {
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/play_highlight.png", 30, 30));

				}
			}
		});
		labelNextSong.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent arg0) {
				labelNextSong.setImage(ImageUtil.scaleImage("src/images/nextsong_normal.png", 30, 30));

			}

			@Override
			public void mouseEnter(MouseEvent arg0) {
				labelNextSong.setImage(ImageUtil.scaleImage("src/images/nextsong_highlight.png", 30, 30));

			}

			@Override
			public void mouseHover(MouseEvent arg0) {
				labelNextSong.setImage(ImageUtil.scaleImage("src/images/nextsong_highlight.png", 30, 30));

			}
		});
		labelPlayStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.playing) {
					System.out.println("点击暂停");
					ConstantData.MM.pause();
					labelPlayStop.setImage(ImageUtil.scaleImage("src/images/play_normal.png", 30, 30));
				} else if (!ConstantData.playing) {
					System.out.println("点击播放");
					if (ConstantData.listSongInfo != null && !ConstantData.listSongInfo.isEmpty()) {
						ConstantData.MM.continuePlay();
						labelPlayStop.setImage(ImageUtil.scaleImage("src/images/stop_normal.png", 30, 30));

					}
				}
			}
		});
		labelLastSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.listSongInfo != null) {
					ConstantData.MM.lastSong();

				}
			}
		});
		labelNextSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.listSongInfo != null) {
					ConstantData.MM.nextSong();

				}
			}
		});
		scaleSetVolume.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ConstantData.MM.thread != null && ConstantData.MM.thread.isAlive()) {
					if (scaleSetVolume.getSelection() == 0) {
						labelMute.setImage(ImageUtil.scaleImage("src/images/mute_normal.png", 25, 25));

					} else {
						labelMute.setImage(ImageUtil.scaleImage("src/images/voice_normal.png", 25, 25));

					}
					ConstantData.MM.setVolume(scaleSetVolume.getSelection());
				}
			}
		});
		labelMute.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent arg0) {
				if (muteFlag) {
					labelMute.setImage(ImageUtil.scaleImage("src/images/mute_normal.png", 25, 25));
				} else {
					labelMute.setImage(ImageUtil.scaleImage("src/images/voice_normal.png", 25, 25));

				}

			}

			@Override
			public void mouseEnter(MouseEvent arg0) {
				if (muteFlag) {
					labelMute.setImage(ImageUtil.scaleImage("src/images/mute_highlight.png", 25, 25));
				} else {
					labelMute.setImage(ImageUtil.scaleImage("src/images/voice_highlight.png", 25, 25));

				}
			}
		});
		labelMusicList.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseExit(MouseEvent arg0) {
				labelMusicList.setImage(ImageUtil.scaleImage("src/images/musiclist_normal.png", 25, 25));

			}

			@Override
			public void mouseEnter(MouseEvent arg0) {
				labelMusicList.setImage(ImageUtil.scaleImage("src/images/musiclist_highlight.png", 25, 25));

			}
		});
		labelMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (muteFlag) {
					muteFlag = false;
					if (ConstantData.MM.thread != null && ConstantData.MM.thread.isAlive()) {
						ConstantData.MM.openVoice();
					}
					labelMute.setImage(ImageUtil.scaleImage("src/images/voice_highlight.png", 25, 25));

				} else {
					muteFlag = true;

					if (ConstantData.MM.thread != null && ConstantData.MM.thread.isAlive()) {
						ConstantData.MM.openMute();

					}
					labelMute.setImage(ImageUtil.scaleImage("src/images/mute_highlight.png", 25, 25));

				}
			}
		});
		// 点击查看播放列表
		labelMusicList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.uiFlag == null)
					return;
				switch (ConstantData.uiFlag) {
				case MusicItem.UI_LOVE:
					stackLayout.topControl = ConstantData.loveUi;

					break;
				case MusicItem.UI_SEARCH:
					stackLayout.topControl = ConstantData.searchSongUi;

					break;
				case MusicItem.UI_DOWNLOAD:
					stackLayout.topControl = ConstantData.downloadMusicUi;

					break;
				case MusicItem.UI_FIND:
					stackLayout.topControl = ConstantData.findMusicUi;

					break;
				}

				compositeRight.layout();
			}
		});
		progressBar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				ConstantData.MM.fastForward(e.x, progressBar.getSize().x);
			}

		});
		;
	}
}
