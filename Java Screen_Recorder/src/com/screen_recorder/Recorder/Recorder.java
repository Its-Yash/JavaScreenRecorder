/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screen_recorder.Recorder;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.screen_recorder.Recorder.Writer;

public class Recorder implements Runnable{
    private final int fps;
	private final String fileName;
	private final ICodec.ID codec;
	
	private static Dimension screenBounds;
	private Thread thread;
	private boolean running;
	
	public Recorder(int fps, String fileName, ICodec.ID codec) {
		this.fps = fps;
		this.fileName = fileName;
		this.codec = codec;
		
		this.running = false;
	}
	
	public Recorder(int fps, String fileName) {
		this(fps, fileName, ICodec.ID.CODEC_ID_MPEG4); // ICodec.ID.CODEC_ID_MPEG4
	}
	
	@Override
	public void run() {
		File f = new File(fileName);
		if(f.exists())
			f.delete();
		
		IMediaWriter writer = ToolFactory.makeWriter(fileName);
		screenBounds = Toolkit.getDefaultToolkit().getScreenSize();
		
		writer.addVideoStream(0, 0, codec, screenBounds.width, screenBounds.height);
		
		long start;
		long elapsed;
		long wait;
		
		Writer w = new Writer(writer);
		Thread t = new Thread(w);
		t.start();
		
		while(running) {
			
			w.write();
			
			try {
				Thread.sleep(1000 / fps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		w.close();
	}
	
	public void start() {
		if(thread != null || running == true)
			stop();
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		running = false;
		thread = null;
	}
	
	public int getMaxFPS() {
		return fps;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public boolean isRecording() {
		return thread != null && running == true;
	}
	
	public ICodec.ID getICodecID() {
		return codec;
	}
	
	protected static BufferedImage convertToType(BufferedImage source, int type) {
		if(source.getType() == type)
			return source;
		
		BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), type);
		image.getGraphics().drawImage(source, 0, 0, null);
		
		return image;
	}
	
	private static Robot r;
	
	protected static BufferedImage getDesktopScreenshot() {
		if(r == null)
			try {
				r = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
		
		return r.createScreenCapture(new Rectangle(screenBounds));
	}
    
    
    
}
