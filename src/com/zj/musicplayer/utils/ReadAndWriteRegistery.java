package com.zj.musicplayer.utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ReadAndWriteRegistery {

	// 把相应的值储存到变量中去
	public void writeLoginInfo(String userName, String password) {
		// HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下写入注册表值.
		Preferences pre = Preferences.userNodeForPackage(ReadAndWriteRegistery.class);

		pre.put("USERNAME", userName);
		pre.put("PASSWORD", password);

		try {
			pre.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public void writeInfo(String key, String value) {
		// HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下写入注册表值.
		Preferences pre = Preferences.userNodeForPackage(ReadAndWriteRegistery.class);

		pre.put(key, value);

		try {
			pre.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	// 查询是否有信息记录在注册表中
	public String findInfo(String key) {
		Preferences now = Preferences.userNodeForPackage(ReadAndWriteRegistery.class);
		String result = now.get(key, null);
		return result;
	}

	// 删除注册表中对应键的信息
	public void delInfo(String key) {
		Preferences now = Preferences.userNodeForPackage(ReadAndWriteRegistery.class);
		now.remove(key);
	}

}
