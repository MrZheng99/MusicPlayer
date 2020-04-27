package com.zj.musicplayer.view;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.DateUtil;
import com.zj.musicplayer.utils.ImageUtil;

public class SongInfoUi extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SongInfoUi(Composite parent, int style) {
		super(parent, style);

		setLayout(new FillLayout(SWT.HORIZONTAL));
		int width = parent.getSize().x;
		int height = parent.getSize().y;

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		Map<String, String> map = ConstantData.listSongInfo.get(ConstantData.mplayIndex);
		composite.setBounds(0, 0, width, height);

		System.out.println(composite.getSize().x + "=" + composite.getSize().y);

		Label labelPic = new Label(composite, SWT.NONE);
		labelPic.setAlignment(SWT.CENTER);
		labelPic.setBounds(0, height / 4, height / 2, height / 2);
		System.out.println(labelPic.getSize().x + "+" + labelPic.getSize().y);
		labelPic.setImage(ImageUtil.getImage(map.get("imageurl"), width / 2, height / 2));
		Label labelSongName = new Label(composite, SWT.NONE);
		labelSongName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSongName.setText(map.get("songname"));
		labelSongName.setBounds(height / 2, 0, 150, 30);

		Label labelSinger = new Label(composite, SWT.NONE);
		labelSinger.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelSinger.setBounds(height / 2, 35, 100, 30);

		labelSinger.setText("演唱: " + map.get("singername"));

		Label labelAlbumName = new Label(composite, SWT.NONE);
		labelAlbumName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelAlbumName.setBounds(height / 2 + 100, 35, 150, 30);

		labelAlbumName.setText("专辑：" + map.get("albumname"));

		Label labelDuration = new Label(composite, SWT.NONE);
		labelDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		labelDuration.setBounds(height / 2 + 260, 35, 100, 30);

		labelDuration.setText("时长：" + DateUtil.millisecondToMinuteSecond(map.get("duration")));

//		List listLyric = new List(composite, SWT.BORDER);
//		listLyric.setBounds(164, 108, 178, 182);
		System.out.println(map.get("singername"));
		System.out.println("查看歌曲详情");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
