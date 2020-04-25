package com.zj.musicplayer.controller;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;

import com.zj.musicplayer.utils.ConstantData;

public class ProcessBarThread extends Thread {

	public void run() {

		while (!ConstantData.playNew) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (ConstantData.playing && ConstantData.stopPonit <= ConstantData.player.getPosition()) {
						((ProgressBar) ConstantData.component.get("compositeBottom_progressBar")).setSelection(
								(int) (100 * (float) ConstantData.player.getPosition() / ConstantData.mp3Time));
					} else {
						((ProgressBar) ConstantData.component.get("compositeBottom_progressBar"))
								.setSelection((int) (100 * (float) ConstantData.stopPonit / ConstantData.mp3Time));
					}

				}
			});
		}

	}

}
