package com.zj.musicplayer.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public class ImageUtil {
	/**
	 * 缩放图片
	 * 
	 * @param path   图片路径
	 * @param width  指定宽度
	 * @param height 指定高度
	 * @return
	 */
	public static Image scaleImage(String path, int width, int height) {
		Image image = null;
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			ImageData imageData = new ImageData(fileInputStream);
			imageData = imageData.scaledTo(width, height);
			image = new Image(Display.getDefault(), imageData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;

	}

	public static Image getImage(byte[] bt, int width, int height) {
		Image image = null;
		try (InputStream inputStream = new ByteArrayInputStream(bt)) {
			ImageData imageData = new ImageData(inputStream);
			imageData = imageData.scaledTo(width, height);
			image = new Image(Display.getDefault(), imageData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static byte[] readFileToArray(String path) {
		byte[] bt = null;
		if (StringUtil.checkNull(path)) {
			return null;
		}
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			bt = new byte[fileInputStream.available()];
			fileInputStream.read(bt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bt;
	}

	public static Image getImage(String picurl, int width, int height) {
		Image image = null;
		URL url = null;
		URLConnection con = null;
		try {
			url = new URL(picurl);
			con = url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (InputStream inputStream = con.getInputStream()) {
			ImageData imageData = new ImageData(inputStream);
			imageData = imageData.scaledTo(width, height);
			image = new Image(Display.getDefault(), imageData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
//		try (InputStream inputStream = new ByteArrayInputStream(bt)) {
//			ImageData imageData = new ImageData(inputStream);
//			imageData = imageData.scaledTo(width, height);
//			image = new Image(Display.getDefault(), imageData);
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return image;
	}
}
