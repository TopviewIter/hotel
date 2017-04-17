package com.xqx.www.view.dialog;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.po.Order;
import com.xqx.www.po.Room;
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;

/**
 * 订单详情界面
 * 
 * @author xqx
 *
 */
public class OrderDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();
	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close;
	private JTextField startTime, name, identify, phone;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField endTime;
	private JLabel label_5;
	private JTextField money;
	private JLabel label_6;
	private JTextField ispay;
	
	public void setbutton(JButton jb) {
		
		jb.setContentAreaFilled(false);
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setOpaque(false);
	}

	// 窗口操作控制菜单
	public void windowsmenu() {

		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds(346, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		setbutton(min);
		min.setToolTipText("最小化");
		min.addActionListener(new BtnListener());

		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds(370, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		setbutton(close);
		close.setToolTipText("关闭");
		close.addActionListener(new BtnListener());

		bkim.add(min);
		bkim.add(close);
	}

	// 构造函数
	public OrderDialogFrame(User user, Order order, Room room) throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		startTime = new JTextField(DateUtil.getLocalTime(order.getStartTime()));
		startTime.setEditable(false);
		startTime.setBounds(147, 132, 178, 25);
		endTime = new JTextField(DateUtil.getLocalTime(order.getEndTime()));
		endTime.setEditable(false);
		endTime.setBounds(147, 167, 178, 25);
		name = new JTextField(user.getName());
		name.setEditable(false);
		name.setBounds(147, 29, 178, 25);
		identify = new JTextField(user.getIdentify());
		identify.setEditable(false);
		identify.setBounds(147, 64, 178, 25);
		phone = new JTextField(user.getPhone());
		phone.setEditable(false);
		phone.setBounds(147, 97, 178, 25);
		money = new JTextField(String.valueOf(order.getMomey()));
		money.setEditable(false);
		money.setBounds(147, 202, 178, 25);
		ispay = new JTextField(String.valueOf(order.isPay()));
		ispay.setEditable(false);
		ispay.setBounds(147, 237, 178, 25);
		
		bkim.add(startTime);
		bkim.add(name);
		bkim.add(identify);
		bkim.add(phone);
		bkim.add(money);
		bkim.add(endTime);
		bkim.add(ispay);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		
		JLabel label = new JLabel("\u9884\u8BA2\u5165\u4F4F");
		label.setBounds(67, 137, 54, 15);
		bkim.add(label);
		
		JLabel label_1 = new JLabel("\u9884\u8BA1\u9000\u623F");
		label_1.setBounds(67, 174, 54, 15);
		bkim.add(label_1);
		
		label_2 = new JLabel("\u59D3\u3000\u3000\u540D");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);
		
		label_3 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		label_3.setBounds(67, 69, 54, 15);
		bkim.add(label_3);
		
		label_4 = new JLabel("\u8054\u7CFB\u7535\u8BDD");
		label_4.setBounds(67, 102, 54, 15);
		bkim.add(label_4);
		
		
		
		label_5 = new JLabel("\u5E94\u6536\u91D1\u989D");
		label_5.setBounds(67, 207, 54, 15);
		bkim.add(label_5);
		
		
		
		
		label_6 = new JLabel("\u662F\u5426\u652F\u4ED8");
		label_6.setBounds(67, 242, 54, 15);
		bkim.add(label_6);
		
		
		
		
		this.setSize(400, 290);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == min) {
				setState(JFrame.ICONIFIED);
			}else if(e.getSource() == close) {
				dispose();
			}
		}

	}
}
