package com.zj.musicplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.zj.musicplayer.model.UserInfoDao;
import com.zj.musicplayer.utils.ImageUtil;
import com.zj.musicplayer.utils.StringUtil;

/**
 * 
 * @description：注册窗口
 * @author ZJ
 * @date 2020年5月3日 下午2:34:21
 */
public class RegisteryUi {

	protected Shell shell;
	private Text textName;
	private Text textPassword;
	private Text textPasswordAgain;
	private Text textEmail;

	private String path = "src/images/zanwu.jpg";
	private UserInfoDao userInfoDao = new UserInfoDao();
	protected boolean nameRight = false;
	protected boolean pwdRight = false;
	protected boolean pwdAgainRight = false;
	protected boolean pwdEmailRight = false;
	protected boolean emailRight = false;

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
		shell.setSize(540, 450);
		shell.setText("注册");
		shell.setImage(ImageUtil.scaleImage("src/images/title.png", 50, 50));
		shell.setBackgroundImage((ImageUtil.scaleImage("src/images/bk_login.jpg", 540, 450)));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);

		textName = new Text(shell, SWT.BORDER);

		textName.setBounds(220, 19, 140, 26);

		Label labelName = new Label(shell, SWT.NONE);
		labelName.setAlignment(SWT.RIGHT);
		labelName.setBounds(85, 22, 76, 20);
		labelName.setText("用户名");
		labelName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label labelPassword = new Label(shell, SWT.NONE);
		labelPassword.setAlignment(SWT.RIGHT);
		labelPassword.setBounds(85, 68, 76, 20);
		labelPassword.setText("密码");
		labelPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		label.setBounds(85, 108, 76, 20);
		label.setText("确认密码");
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		textPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(220, 65, 140, 26);

		textPasswordAgain = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textPasswordAgain.setBounds(220, 105, 140, 26);

		Label labelEmail = new Label(shell, SWT.NONE);
		labelEmail.setAlignment(SWT.RIGHT);
		labelEmail.setBounds(85, 149, 76, 20);
		labelEmail.setText("Email");
		labelEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		textEmail = new Text(shell, SWT.BORDER);
		textEmail.setBounds(220, 146, 140, 26);

		Label labelHeadImage = new Label(shell, SWT.NONE);
		labelHeadImage.setAlignment(SWT.RIGHT);
		labelHeadImage.setBounds(85, 237, 76, 20);
		labelHeadImage.setText("设置头像");
		labelHeadImage.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setImage(ImageUtil.scaleImage(path, 140, 140));
		labelImage.setBounds(220, 196, 140, 140);
		labelImage.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button buttonRegiste = new Button(shell, SWT.NONE);

		buttonRegiste.setBounds(shell.getSize().x / 4, 350, shell.getSize().x / 5, 30);
		buttonRegiste.setText("注册");
		Button buttonExit = new Button(shell, SWT.NONE);

		buttonExit.setText("返回登录");
		buttonExit.setBounds(shell.getSize().x / 2, 350, shell.getSize().x / 5, 30);

		Label labelTipName = new Label(shell, SWT.NONE);
		labelTipName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelTipName.setBounds(396, 19, 128, 20);
		labelTipName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label labelTipPwd = new Label(shell, SWT.NONE);
		labelTipPwd.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelTipPwd.setBounds(396, 65, 128, 20);
		labelTipPwd.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label labelTipPwdAgain = new Label(shell, SWT.NONE);
		labelTipPwdAgain.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelTipPwdAgain.setBounds(396, 108, 128, 20);
		labelTipPwdAgain.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		Label labelTipEmail = new Label(shell, SWT.NONE);
		labelTipEmail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelTipEmail.setBounds(396, 149, 128, 20);
		labelTipEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		labelImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(shell);
				fileDialog.setFilterExtensions(new String[] { "*.jpg", "*.png" });
				path = fileDialog.open();
				if (StringUtil.checkNull(path)) {
					return;
				}
				Point point = labelImage.getSize();
				labelImage.setImage(ImageUtil.scaleImage(path, point.x, point.y));
			}
		});
		// 用户名是否存在
		textName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (userInfoDao.existName(labelName.getText().trim())) {
					nameRight = false;

					labelTipName.setText("用户名已存在");
				} else {
					nameRight = true;
					labelTipName.setText("");

				}

			}
		});
		textPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (textPassword.getText().trim().length() < 6) {
					pwdRight = false;

					labelTipPwd.setText("长度必须大于等于6位");
				} else {
					pwdRight = true;
					labelTipPwd.setText("");
				}
			}
		});
		textPasswordAgain.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String password = textPassword.getText().trim();
				String passwordAgain = textPasswordAgain.getText().trim();
				if (!passwordAgain.equals(password)) {
					pwdAgainRight = false;

					labelTipPwdAgain.setText("两次密码不一致");
				} else {
					pwdAgainRight = true;
					labelTipPwdAgain.setText("");
				}
			}
		});
		textEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = textEmail.getText().trim();
				String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern p = Pattern.compile(regEx1);
				Matcher m = p.matcher(email);
				if (m.matches()) {
					emailRight = true;
					labelTipEmail.setText("");
				} else {
					emailRight = false;

					labelTipEmail.setText("邮箱格式不正确");
				}

			}
		});

		buttonRegiste.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (nameRight && pwdAgainRight && pwdRight && emailRight) {
					int row = userInfoDao.regist(textName.getText(), textPassword.getText(), textEmail.getText(),
							ImageUtil.readFileToArray(path));
					if (row == 1) {
						boolean success = MessageDialog.openConfirm(shell, "注册成功", "注册成功请点击确认返回登录！");
						if (success) {
							shell.dispose();
						}
					} else {
						MessageDialog.openError(shell, "注册失败", "注册失败请重新注册！");

					}
				}
			}
		});
		buttonExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shell.dispose();

			}
		});
	}
}
