package com.xqx.www.view.panel;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xqx.www.view.LoginFrame;

public class SystemManagerPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	// 用于获得窗口的大小
		private final static int width = Toolkit.getDefaultToolkit()
				.getScreenSize().width;
	
	private Window w;
	private JButton button;
	private JButton button_1;

	public SystemManagerPanel(Window w) {
		this.w = w;
		setLayout(null);
		
		button = new JButton("\u5207\u6362\u7528\u6237");
		button.setBounds(((int) (width * 0.8f) - 93) / 2, 245, 93, 23);
		button.addActionListener(new BtnListener());
		add(button);
		
		button_1 = new JButton("\u9000\u51FA\u7CFB\u7EDF");
		button_1.setBounds(((int) (width * 0.8f) - 93) / 2, 278, 93, 23);
		button_1.addActionListener(new BtnListener());
		add(button_1);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("image/bk.png"));
		lblNewLabel.setBounds(((int) (width * 0.8f) - 152) / 2, 73, 152, 141);
		add(lblNewLabel);
		this.setVisible(true);
	}
	
	class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			w.dispose();
			if(button == e.getSource()) {
				try {
					new LoginFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		
	}
}
