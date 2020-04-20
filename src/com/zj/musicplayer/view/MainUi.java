package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

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
		initShellEvent();
		setShellInitSize();

	}

	private void setShellInitSize() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setBounds(dimension.width / 4, dimension.height / 4, dimension.width / 2, dimension.height / 2);

	}

	private void createCompsite() {
		shell.setLayout(null);

		compositeTop = new Composite(shell, SWT.NONE);

		compositeTop.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		compositeTop.setLayout(null);

		compositeLeft = new Composite(shell, SWT.NONE);
		compositeLeft.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));

		compositeRight = new Composite(shell, SWT.NONE);
		compositeRight.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));

		compositeBottom = new Composite(shell, SWT.NONE);
		compositeBottom.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));

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

	}

	private void refeshTop() {

		int compositeTopHeight = compositeTop.getSize().y;
		int compositeTopWidth = compositeTop.getSize().x / 10;

		labelPlayerTitle.setBounds(compositeTopWidth / 6, compositeTopHeight / 4, compositeTopWidth * 2 - 40, 25);

		textSearch.setBounds(compositeTopWidth * 2, compositeTopHeight / 4, compositeTopWidth * 2, 25);

//		labelSearch.setImage(ImageUtil.scaleImage("src/images/search.png", 25, 25));

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
				compositeLeft.setBounds(0, shellHeight / 10, shellWidth / 10, 8 * shellHeight / 10);
				compositeRight.setBounds(shellWidth / 10, shellHeight / 10, 9 * shellWidth / 10, 8 * shellHeight / 10);

				compositeBottom.setBounds(0, 9 * shellHeight / 10, shellWidth, shellHeight / 10);
				refeshTop();
			}
		});
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

		labelClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
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
//		labelMax.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(MouseEvent e) {
//				if (shell.getLocation().x != 0 || shell.getLocation().y != 0) {
//					shell.setBounds(0, 0, dimension.width, dimension.height);
//
//				}
//
//			}
//		});
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
			// 点击图标的效果

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
			// 点击图标的效果

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
			// 点击图标的效果

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
			// 点击图标的效果

			@Override
			public void mouseEnter(MouseEvent e) {
				labelSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			}
		});
		// 清除提示信息
		textSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				textSearch.setText("");

			}
		});
	}
}
