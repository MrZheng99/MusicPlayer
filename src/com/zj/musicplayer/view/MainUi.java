package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ImageUtil;

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
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainUi window = new MainUi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	}

	private StackLayout stackLayout = new StackLayout();

	private void createCompsite() {
		shell.setLayout(null);

		compositeTop = new Composite(shell, SWT.NONE);
		compositeTop.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		compositeTop.setLayout(null);

		compositeLeft = new Composite(shell, SWT.NONE);
		compositeLeft.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		compositeLeft.setLayout(null);

		compositeRight = new Composite(shell, SWT.NONE);
		compositeRight.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
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
		labelUserName.setText("未登录");
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
		labelUserName.setBounds(compositeTopWidth * 6 + 27, compositeTopHeight / 4, compositeTopWidth - 30, 25);
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
		ConstantData.component.put("compositeLeft_labelSongName", labelSongName);
		ConstantData.component.put("compositeLeft_labelSongPic", labelSongPic);
		ConstantData.component.put("compositeLeft_labelSinger", labelSinger);

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
		labelJoinLove.setImage(ImageUtil.scaleImage("src/images/love.png", 19, 19));
		compositeLeft.layout();
	}

	private Label labelLastSong;
	private Label labelPlayStop;
	private Label labelNextSong;
	private ProgressBar progressBar;

	private void initBottom() {
		labelLastSong = new Label(compositeBottom, SWT.NONE);

		labelPlayStop = new Label(compositeBottom, SWT.NONE);

		labelNextSong = new Label(compositeBottom, SWT.NONE);
		progressBar = new ProgressBar(compositeBottom, SWT.NONE);
		progressBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));

		initCompositeBottomEvent();
		ConstantData.component.put("compositeBottom_labelPlayStop", labelPlayStop);
		ConstantData.component.put("compositeBottom_progressBar", progressBar);

	}

	private void refreshBottom() {
		// int compositeBottomWidth = compositeBottom.getSize().x;
		// int compositeBottomHeight = compositeBottom.getSize().y;
		labelLastSong.setBounds(0, 12, 30, 30);
		labelLastSong.setImage(ImageUtil.scaleImage("src/images/lastsong.png", 30, 30));
		labelPlayStop.setBounds(35, 12, 30, 30);
		labelPlayStop.setImage(ImageUtil.scaleImage("src/images/start.png", 30, 30));
		labelNextSong.setBounds(70, 12, 30, 30);
		labelNextSong.setImage(ImageUtil.scaleImage("src/images/nextsong.png", 30, 30));
		progressBar.setBounds(compositeLeft.getSize().x, 22, compositeRight.getSize().x, 10);

		compositeBottom.layout();
	}

	private boolean down = false;
	private int clickX;
	private int clickY;

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
				}
			}
		});

		labelClose.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if (ConstantData.playMusicThread != null && ConstantData.playMusicThread.isAlive()) {
					ConstantData.playMusicThread.stop();
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
		// labelMax.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseDown(MouseEvent e) {
		// if (shell.getLocation().x != 0 || shell.getLocation().y != 0) {
		// shell.setBounds(0, 0, dimension.width, dimension.height);
		//
		// }
		//
		// }
		// });
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
				case "":
					if (ConstantData.searchSongUi == null) {
						ConstantData.searchSongUi = new SearchSongUi(compositeRight, SWT.NONE);
					}
					stackLayout.topControl = ConstantData.searchSongUi;
					break;
				}
			}
		});
		/*****************/
		labelSongPic.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				labelSongPic.setImage(ImageUtil.scaleImage("src/images/right.png", 38, 38));
				labelSongPic.setBackgroundImage(ImageUtil
						.getImage(ConstantData.listSongInfo.get(ConstantData.playIndex).get("imageurl"), 38, 38));
			}

			@Override
			public void mouseExit(MouseEvent e) {
				labelSongPic.setImage(ImageUtil
						.getImage(ConstantData.listSongInfo.get(ConstantData.playIndex).get("imageurl"), 38, 38));
			}
		});
	}

	private void initCompositeBottomEvent() {
		labelPlayStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.playing) {
					System.out.println("点击暂停");

					ConstantData.playMusicThread.stopPlay();
//					ConstantData.clipMusic.stopPlay();
					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/start.png", 30, 30));
				} else if (!ConstantData.playing) {
					System.out.println("点击播放");
					ConstantData.playMusicThread.startPlay();
//					ConstantData.clipMusic.startPlay();

					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/stop.png", 30, 30));
				}
			}
		});
		labelLastSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.listSongInfo != null && ConstantData.playMusicThread != null) {
					ConstantData.playMusicThread.lastSong();
					Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.playIndex);
					((Label) ConstantData.component.get("compositeLeft_labelSongName")).setText(map.get("songname"));
					((Label) ConstantData.component.get("compositeLeft_labelSinger")).setText(map.get("singername"));
					((Label) ConstantData.component.get("compositeLeft_labelSongPic"))
							.setImage(ImageUtil.getImage(map.get("imageurl"), 38, 38));
					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/stop.png", 30, 30));
				}
			}
		});
		labelNextSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (ConstantData.listSongInfo != null && ConstantData.playMusicThread != null) {
					ConstantData.playMusicThread.nextSong();
					Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.playIndex);
					((Label) ConstantData.component.get("compositeLeft_labelSongName")).setText(map.get("songname"));
					((Label) ConstantData.component.get("compositeLeft_labelSinger")).setText(map.get("singername"));
					((Label) ConstantData.component.get("compositeLeft_labelSongPic"))
							.setImage(ImageUtil.getImage(map.get("imageurl"), 38, 38));
					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/stop.png", 30, 30));
				}
			}
		});

	}
}
