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
import com.zj.musicplayer.utils.ConstantData;
import com.zj.musicplayer.utils.ImageUtil;
import com.zj.musicplayer.utils.StringUtil;

public class ChangeUserInfoUi {

	protected Shell shell;
	private Text textOldPassword;
	private Text textEmail;
	private Text textNewPassword;
	private boolean emailRight = true;
	private boolean pwdRight;
	private boolean pwdAgainRight;
	private UserInfoDao userInfoDao = null;
	private String path = null;
	private Shell parent = null;
	private Label labelPic;
	private Label labelName;
	private Button buttonModify;
	private Button buttonCancel;
	private Label labelEmailTip;
	private Label labelOldPwdTip;
	private Label labelNewPwdTip;
	private String name;

	/**
	 * @wbp.parser.entryPoint
	 */
	public ChangeUserInfoUi(Shell parent) {
		super();
		this.parent = parent;
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
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
		shell = new Shell();
		shell.setText("个人信息");
		shell.setSize(525, 400);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((dimension.width - shell.getSize().x) / 2, (dimension.height - shell.getSize().y) / 2);
		shell.setImage(ImageUtil.scaleImage("src/images/title.png", 50, 50));

		Label label = new Label(shell, SWT.NONE);
		label.setBounds(112, 10, 76, 20);
		label.setText("用户名");

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(112, 158, 76, 20);
		label_1.setText("邮箱");

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(112, 213, 76, 20);
		label_2.setText("旧密码");

		textOldPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textOldPassword.setBounds(228, 210, 164, 26);

		textEmail = new Text(shell, SWT.BORDER);
		textEmail.setBounds(228, 155, 164, 26);

		textNewPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textNewPassword.setBounds(228, 269, 164, 26);

		labelName = new Label(shell, SWT.NONE);
		labelName.setBounds(252, 10, 76, 20);

		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(112, 72, 76, 20);
		label_3.setText("头像");

		labelPic = new Label(shell, SWT.NONE);
		labelPic.setBounds(252, 53, 108, 68);

		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setBounds(112, 275, 76, 20);
		label_4.setText("新密码");

		buttonModify = new Button(shell, SWT.NONE);

		buttonModify.setBounds(112, 313, 98, 30);
		buttonModify.setText("修改");
		buttonCancel = new Button(shell, SWT.NONE);

		buttonCancel.setBounds(294, 313, 98, 30);
		buttonCancel.setText("返回");

		labelEmailTip = new Label(shell, SWT.NONE);
		labelEmailTip.setBounds(410, 158, 90, 20);
		labelEmailTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		labelOldPwdTip = new Label(shell, SWT.NONE);
		labelOldPwdTip.setBounds(410, 213, 90, 20);
		labelOldPwdTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		labelNewPwdTip = new Label(shell, SWT.NONE);
		labelNewPwdTip.setBounds(410, 269, 90, 20);
		labelNewPwdTip.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		initInfo();
		initEvent();
	}

	private void initEvent() {
		labelPic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(shell);
				fileDialog.setFilterExtensions(new String[] { "*.jpg", "*.png" });
				path = fileDialog.open();
				if (StringUtil.checkNull(path)) {
					return;
				}
				Point point = labelPic.getSize();
				labelPic.setImage(ImageUtil.scaleImage(path, point.x, point.y));
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
					labelEmailTip.setText("");
				} else {
					emailRight = false;

					labelEmailTip.setText("邮箱格式错误");
				}

			}
		});
		textOldPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (userInfoDao.login(name, textOldPassword.getText().trim()).size() >= 1) {
					pwdRight = true;
					labelOldPwdTip.setText("");
				} else {
					pwdRight = false;
					labelOldPwdTip.setText("原密码不正确");
				}
			}
		});
		textNewPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String passwordAgain = textNewPassword.getText().trim();
				if (passwordAgain.length() < 6) {
					pwdAgainRight = false;
					labelNewPwdTip.setText("必须长度>=6位");
				} else {
					pwdAgainRight = true;
					labelNewPwdTip.setText("");
				}
			}
		});
		buttonModify.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (pwdAgainRight && pwdRight && emailRight) {
					int row;
					if (path != null) {
						row = userInfoDao.modify(labelName.getText(), textNewPassword.getText(), textEmail.getText(),
								ImageUtil.readFileToArray(path));
					} else {
						row = userInfoDao.modify(labelName.getText(), textNewPassword.getText(), textEmail.getText());
					}
					System.out.println(row);
					if (row == 1) {
						MessageDialog.openInformation(shell, "修改成功", "修改成功,请重新登录");
						shell.dispose();
						parent.dispose();
						LoginUi win = new LoginUi();
						win.open();
					} else {
						MessageDialog.openError(shell, "修改失败", "修改失败请重试！");

					}

				}
			}

		});
		buttonCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	private void initInfo() {
		name = ConstantData.currentLoginData.get("username");
		labelName.setText(name);
		userInfoDao = new UserInfoDao();
		labelPic.setImage(
				ImageUtil.getImage(userInfoDao.queryHeadImage(name), labelPic.getSize().x, labelPic.getSize().y));
		textEmail.setText(ConstantData.currentLoginData.get("email"));
	}
}
