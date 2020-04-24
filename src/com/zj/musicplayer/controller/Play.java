package com.zj.musicplayer.controller;

import com.zj.musicplayer.utils.ConstantData;

import javazoom.jl.decoder.JavaLayerException;

public class Play extends Thread {

	@Override
	public void run() {
		try {
			ConstantData.player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
