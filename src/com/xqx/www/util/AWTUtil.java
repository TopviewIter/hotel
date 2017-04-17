package com.xqx.www.util;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Timer;

import com.sun.awt.AWTUtilities;
import com.xqx.www.component.CustomPanel;

/**
 * ������
 * 
 * @author xqx
 *
 */
public class AWTUtil {

	// ���ڵ��뵭������
	public static void setOpacity(final Window w) {

		// �������õ��뵭�������
		AWTUtilities.setWindowOpacity(w, 0f);
		ActionListener lisener = new ActionListener() {
			float alpha = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (alpha < 0.9) {
					AWTUtilities.setWindowOpacity(w, alpha += 0.1);
				} else {
					AWTUtilities.setWindowOpacity(w, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// �����߳̿���
		new Timer(50, lisener).start();
	}

	// �����ƶ�����
	public static void windowMove(final Window w, final Point origin) {

		// ����û�б���Ĵ��ڿ����϶�
		w.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				// ���£�mousePressed
				// ���ǵ����������걻����û��̧��
				// ����갴�µ�ʱ���ô��ڵ�ǰ��λ��
				origin.x = e.getX(); 
				origin.y = e.getY();
			}
		});
		
		w.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				
				Point p = w.getLocation(); // ������϶�ʱ��ȡ���ڵ�ǰλ��
				// ���ô��ڵ�λ��
				// ���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
				w.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});
	}
	
	// �ص����ĸ��·���
	public static void updateContent(CustomPanel panel) {
		
		panel.removeAll();
		panel.updateContent();
		panel.validate();
		panel.repaint();
	}

}
