/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.screen_recorder.Buttons;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.screen_recorder.JavaScreen_Recorder;

public class StopRecordingButton extends JButton implements ActionListener{
    public StopRecordingButton(int x, int y, int w, int h) {
		setText("Stop Recording");
		setLocation(x, y);
		setSize(w, h);
		setLayout(new FlowLayout());
		addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(JavaScreen_Recorder.getInstance().getRecorder().isRecording()) {
			JavaScreen_Recorder.getInstance().getRecorder().stop();
		}
	}
    
    
}
