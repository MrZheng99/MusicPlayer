package com.zj.musicplayer.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.controller.MusicItem;
import com.zj.musicplayer.utils.DateUtil;
import com.zj.musicplayer.utils.MusicFileUtil;

public class DownloadSongUi extends Composite {
	private List<MusicItem> listMusicItems = new ArrayList<MusicItem>();
	public static List<Map<String, String>> listDownloadSong = null;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DownloadSongUi(Composite parent, int style) {
		super(parent, style);
		int width = parent.getSize().x / 4;
		setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
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

		MusicFileUtil fileUtil = new MusicFileUtil();
		listDownloadSong = fileUtil.scanFolder();
		for (Map<String, String> map : listDownloadSong) {

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

			listMusicItems.add(new MusicItem(MusicItem.UI_DOWNLOAD, labelSongName, labelSingerName, labelAlbumName,
					labelDuration));
		}
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.layout();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
