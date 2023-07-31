/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screen_recorder.Recorder;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;

public class Writer implements Runnable {
    private final long recordingStart;
	private IMediaWriter writer;
	private boolean write;
	private boolean running;
	
	public Writer(IMediaWriter writer) {
		this.recordingStart = System.nanoTime();
		this.writer = writer;
		this.write = false;
		this.running = true;
        }
                 public void run() {
		while(running) {
			//if(write) {
				BufferedImage screen = Recorder.convertToType(Recorder.getDesktopScreenshot(), BufferedImage.TYPE_3BYTE_BGR); // BufferedImage.TYPE_3BYTE_BGR
				writer.encodeVideo(0, screen, System.nanoTime() - recordingStart, TimeUnit.NANOSECONDS);
				write = false;
			//}
		}
	}
	
	public void write() {
		write = true;
	}
	
	public void close() {
		running = false;
		writer.close();
	}
    
    
}
