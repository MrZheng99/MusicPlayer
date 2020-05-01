package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.utils.ImageUtil;
import com.zj.musicplayer.utils.ReadAndWriteRegistery;
import com.zj.musicplayer.utils.StringUtil;

public class SettingUi {

	protected Shell shell;

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
		shell = new Shell(SWT.TITLE);

		shell.setSize(525, 400);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);
		shell.setImage(ImageUtil.scaleImage("src/images/title.png", 50, 50));

		shell.setText("ZJ-MusicPlayer  Setting");
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		label.setBounds(24, 10, 76, 20);
		label.setText("设置");
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		lblNewLabel.setBounds(25, 50, 100, 20);
		lblNewLabel.setText("歌曲下载路径");
		Button btnNewButton = new Button(shell, SWT.NONE);

		btnNewButton.setBounds(131, 45, 98, 30);
		btnNewButton.setText("选择路径");

		Label labelPath = new Label(shell, SWT.NONE);
		labelPath.setBounds(259, 50, 238, 20);
		ReadAndWriteRegistery registery = new ReadAndWriteRegistery();

		Button buttonSave = new Button(shell, SWT.NONE);

		buttonSave.setBounds(220, 290, 98, 30);
		buttonSave.setText("保存退出");
		String pathOld = registery.findInfo("DOWNLOAD_PATH");
		if (StringUtil.checkNull(pathOld)) {
			labelPath.setText(System.getProperty("user.dir"));
		} else {
			labelPath.setText(pathOld);
		}
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell, SWT.OPEN);
				// 设置目录选择对话框的标题
				directoryDialog.setText("下载路径选择");
				// 设置文件目录选择对话框的提示信息
				directoryDialog.setMessage("请选择你下载的歌曲想要保存的路径");

				// 设置默认打开的文件目录
				directoryDialog.setFilterPath(pathOld);

				// 打开窗口，选择用户所选择的文件目录
				final String path = directoryDialog.open();
				if (!StringUtil.checkNull(path)) {
					labelPath.setText(path);
				}
			}
		});

		buttonSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean flag = MessageDialog.openConfirm(shell, "退出", "确认保存修改并退出吗？");
				System.out.println(flag);
				if (flag) {
					registery.writeInfo("DOWNLOAD_PATH", labelPath.getText().trim());
					shell.dispose();
				}
			}
		});
	}
}
