package com.xqx.www.util;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		JFrame frame = new JFrame();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		frame.setVisible(true);
	}
	
}
