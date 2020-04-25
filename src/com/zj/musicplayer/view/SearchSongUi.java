package com.zj.musicplayer.view;

import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.controller.MMouseTrackListenerAdapter;
import com.zj.musicplayer.controller.PlayMusicThread;
import com.zj.musicplayer.model.SongInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.DateUtil;
import com.zj.musicplayer.utils.ImageUtil;

/**
 * 
 * @description：展示搜索到的歌曲
 * @author ZJ
 * @date 2020年4月22日 下午3:31:07
 */
public class SearchSongUi extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SearchSongUi(Composite parent, int style) {
		super(parent, style);
		parent.layout();
		int width = parent.getSize().x / 4;
		setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(0, 0, parent.getSize().x, parent.getSize().y);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));

		Label label1 = new Label(composite, SWT.NONE);
		label1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label1.setAlignment(SWT.CENTER);
		label1.setText("歌名");
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_1.widthHint = width;
		label1.setLayoutData(gd_lblNewLabel_1);

		Label label2 = new Label(composite, SWT.NONE);
		label2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label2.setAlignment(SWT.CENTER);
		label2.setText("歌手");
		GridData gd_lblNewLabel_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_2.widthHint = width;
		label2.setLayoutData(gd_lblNewLabel_2);

		Label label3 = new Label(composite, SWT.NONE);
		label3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label3.setAlignment(SWT.CENTER);
		label3.setText("专辑");
		GridData gd_lblNewLabel_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_3.widthHint = width;
		label3.setLayoutData(gd_lblNewLabel_3);

		Label label4 = new Label(composite, SWT.NONE);
		label4.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label4.setAlignment(SWT.CENTER);
		label4.setText("时长");
		GridData gd_lblNewLabel_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_4.widthHint = width - 50;
		label4.setLayoutData(gd_lblNewLabel_4);
		SongInfoDao songInfoDao = new SongInfoDao();
		ConstantData.listSongInfo = songInfoDao.findAllByName(ConstantData.compositeTopTextSearch.getText().trim());
		if (ConstantData.listSongInfo == null || ConstantData.listSongInfo.isEmpty()) {

			Label labelTip = new Label(composite, SWT.NONE);
			labelTip.setAlignment(SWT.CENTER);
			labelTip.setText("暂无歌曲信息,请重新搜索");
			labelTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			ConstantData.compositeTopTextSearch.setFocus();
		} else {
			for (Map<String, String> map : ConstantData.listSongInfo) {

				Label labelSongName = new Label(composite, SWT.NONE);
				labelSongName.setAlignment(SWT.CENTER);
				labelSongName.setText(map.get("songname"));
				GridData gd_SongName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_SongName.widthHint = width;
				labelSongName.setLayoutData(gd_SongName);

				Label labelSingerName = new Label(composite, SWT.NONE);
				labelSingerName.setAlignment(SWT.CENTER);
				labelSingerName.setText(map.get("singername"));
				GridData gd_labelSingerName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_labelSingerName.widthHint = width;
				labelSingerName.setLayoutData(gd_labelSingerName);

				Label labelAlbumName = new Label(composite, SWT.NONE);
				labelAlbumName.setAlignment(SWT.CENTER);
				labelAlbumName.setText(map.get("albumname"));
				GridData gd_labelAlbumName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_labelAlbumName.widthHint = width;
				labelAlbumName.setLayoutData(gd_labelAlbumName);

				Label labelDuration = new Label(composite, SWT.NONE);
				labelDuration.setAlignment(SWT.CENTER);
				labelDuration.setText(DateUtil.millisecondToMinuteSecond(map.get("duration")));
				GridData gd_labelDuration = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_labelDuration.widthHint = width - 50;
				labelDuration.setLayoutData(gd_labelDuration);
				/*********** 鼠标事件 ***************/
				labelSongName.addMouseListener(new MouseListener() {

					@Override
					public void mouseDown(MouseEvent arg0) {
						setMouseDownEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);

					}

					@Override
					public void mouseUp(MouseEvent arg0) {
						setMouseUpEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						setMouseDoubleClickEvent(labelSongName, labelSingerName);

					}
				});

				labelSongName.addMouseTrackListener(new MMouseTrackListenerAdapter() {
					@Override
					public void mouseEnter(MouseEvent e) {
						setMouseTrackEnterEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						setMouseTrackExitEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

				});

				labelSingerName.addMouseListener(new MouseListener() {

					@Override
					public void mouseDown(MouseEvent arg0) {
						setMouseDownEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);

					}

					@Override
					public void mouseUp(MouseEvent arg0) {
						setMouseUpEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						setMouseDoubleClickEvent(labelSongName, labelSingerName);

					}
				});

				labelSingerName.addMouseTrackListener(new MMouseTrackListenerAdapter() {
					@Override
					public void mouseEnter(MouseEvent e) {
						setMouseTrackEnterEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						setMouseTrackExitEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

				});
				labelAlbumName.addMouseListener(new MouseListener() {

					@Override
					public void mouseDown(MouseEvent arg0) {
						setMouseDownEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);

					}

					@Override
					public void mouseUp(MouseEvent arg0) {
						setMouseUpEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						setMouseDoubleClickEvent(labelSongName, labelSingerName);

					}
				});

				labelAlbumName.addMouseTrackListener(new MMouseTrackListenerAdapter() {
					@Override
					public void mouseEnter(MouseEvent e) {
						setMouseTrackEnterEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						setMouseTrackExitEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

				});
				labelDuration.addMouseListener(new MouseListener() {

					@Override
					public void mouseDown(MouseEvent arg0) {
						setMouseDownEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);

					}

					@Override
					public void mouseUp(MouseEvent arg0) {
						setMouseUpEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						setMouseDoubleClickEvent(labelSongName, labelSingerName);

					}
				});

				labelDuration.addMouseTrackListener(new MMouseTrackListenerAdapter() {
					@Override
					public void mouseEnter(MouseEvent e) {
						setMouseTrackEnterEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						setMouseTrackExitEvent(labelSongName, labelSingerName, labelAlbumName, labelDuration);
					}

				});

			}

		}
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.layout();

	}

	protected void setMouseDoubleClickEvent(Label labelSongName, Label labelSingerName) {
		if (MessageDialog.openConfirm(getShell(), "播放音乐", "是否播放:" + labelSongName.getText())) {
			Map<String, String> map = null;
			for (int i = 0, len = ConstantData.listSongInfo.size(); i < len; i++) {
				map = ConstantData.listSongInfo.get(i);
				if (map.get("songname").equals(labelSongName.getText())
						&& map.get("singername").equals(labelSingerName.getText())) {
					ConstantData.playIndex = i;
					((Label) ConstantData.component.get("compositeBottom_labelPlayStop"))
							.setImage(ImageUtil.scaleImage("src/images/stop.png", 30, 30));
					((Label) ConstantData.component.get("compositeLeft_labelSongName")).setText(map.get("songname"));
					((Label) ConstantData.component.get("compositeLeft_labelSinger")).setText(map.get("singername"));
					System.out.println(map.get("imageurl"));
					((Label) ConstantData.component.get("compositeLeft_labelSongPic"))
							.setImage(ImageUtil.getImage(map.get("imageurl"), 38, 38));
					if (ConstantData.playMusicThread != null && !ConstantData.playMusicThread.isInterrupted()) {
						ConstantData.playMusicThread.stopPlay();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					new Thread(new Runnable() {
						public void run() {
							ConstantData.playing = true;
							ConstantData.stopPonit = 0;
							ConstantData.playMusicThread = new PlayMusicThread();
							ConstantData.playMusicThread.start();

						};
					}).start();

					return;
				}
			}
		}

	}

	protected void setMouseDownEvent(Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration) {

		labelSongName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

	}

	protected void setMouseTrackEnterEvent(Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration) {

		labelSongName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

	}

	protected void setMouseTrackExitEvent(Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration) {
		labelSongName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
	}

	protected void setMouseUpEvent(Label labelSongName, Label labelSingerName, Label labelAlbumName,
			Label labelDuration) {
		System.out.println(labelSongName.getText());
		labelSongName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelSingerName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelAlbumName.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
		labelDuration.setForeground(SWTResourceManager.getColor(SWT.TRANSPARENT));
	}

	@Override
	protected void checkSubclass() {
	}

}
