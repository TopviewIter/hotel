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
 * 工具类
 * 
 * @author xqx
 *
 */
public class AWTUtil {

	// 窗口淡入淡出函数
	public static void setOpacity(final Window w) {

		// 窗口设置淡入淡出代码段
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
		// 设置线程控制
		new Timer(50, lisener).start();
	}

	// 窗体移动函数
	public static void windowMove(final Window w, final Point origin) {

		// 设置没有标题的窗口可以拖动
		w.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				// 按下（mousePressed
				// 不是点击，而是鼠标被按下没有抬起）
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX(); 
				origin.y = e.getY();
			}
		});
		
		w.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				
				Point p = w.getLocation(); // 当鼠标拖动时获取窗口当前位置
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				w.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});
	}
	
	// 回调面板的更新方法
	public static void updateContent(CustomPanel panel) {
		
		panel.removeAll();
		panel.updateContent();
		panel.validate();
		panel.repaint();
	}

}
