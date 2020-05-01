package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.model.UserInfoDao;
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ReadAndWriteRegistery;
import com.zj.musicplayer.utils.StringUtil;

public class LoginUi {

	protected Shell shell;
	private Text textPassword;

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
		shell = new Shell(SWT.MIN);

		shell.setSize(535, 303);
		shell.setText("登录");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);

		Label labelAccount = new Label(shell, SWT.NONE);
		labelAccount.setBounds(89, 61, 76, 20);
		labelAccount.setText("账号");
		Text textAccount = new Text(shell, SWT.NONE);
		textAccount.setBounds(194, 58, 176, 28);
		Label labelPassword = new Label(shell, SWT.NONE);
		labelPassword.setBounds(89, 128, 76, 20);
		labelPassword.setText("密码");

		textPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(194, 125, 176, 26);
		ReadAndWriteRegistery registery = new ReadAndWriteRegistery();
		String username = registery.findInfo("USERNAME");
		String password = registery.findInfo("PASSWORD");
		System.out.println(username + ":" + password);
		if (!StringUtil.checkNull(username)) {
			textAccount.setText(username);
			textPassword.setText(password);
		}

		Button btnCheckButton = new Button(shell, SWT.CHECK);

		btnCheckButton.setBounds(404, 128, 84, 20);
		btnCheckButton.setText("记住密码");

		Label labelAccountTip = new Label(shell, SWT.NONE);
		labelAccountTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelAccountTip.setBounds(89, 98, 101, 20);

		Label labelPasswordTip = new Label(shell, SWT.NONE);
		labelPasswordTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelPasswordTip.setBounds(89, 158, 101, 20);

		Button buttonLogin = new Button(shell, SWT.NONE);
		buttonLogin.setBounds(89, 196, 101, 30);
		buttonLogin.setText("登录");

		Button buttonRegist = new Button(shell, SWT.NONE);

		buttonRegist.setText("注册");
		buttonRegist.setBounds(266, 196, 101, 30);

		buttonLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String pwd = textPassword.getText().trim();
				String account = textAccount.getText().trim();
				if (pwd.length() <= 0) {
					labelPasswordTip.setText("密码不能为空");
					return;
				} else if (account.length() <= 0) {
					labelAccountTip.setText("账号不能为空");
					return;

				} else {
					UserInfoDao userInfoDao = new UserInfoDao();

					Map<String, String> map = userInfoDao.login(account, pwd);
					if (map.size() > 0) {

						ConstantData.currentLoginData = map;
						if (btnCheckButton.getSelection()) {
							ReadAndWriteRegistery registery = new ReadAndWriteRegistery();
							if (!registery.findInfo("USERNAME").equals(account)) {
								registery.writeInfo(account, pwd);

							} else {
								registery.delInfo("USERNAME");
								registery.delInfo("PASSWORD");
								registery.writeInfo(account, pwd);
							}
						}
						MainUi win = new MainUi();
						shell.dispose();
						win.open();
					} else {
						labelAccountTip.setText("请核对正确");

						labelPasswordTip.setText("请核对正确");
					}
				}
			}
		});
		buttonRegist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RegisteryUi win = new RegisteryUi();
				win.open();
			}
		});
		textAccount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (textAccount.getText().trim().length() <= 0) {
					labelAccountTip.setText("账号不能为空");
				}
			}

		});
		textPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				if (textPassword.getText().trim().length() <= 0) {
					labelPasswordTip.setText("密码不能为空");
				}
			}
		});
	}
}
