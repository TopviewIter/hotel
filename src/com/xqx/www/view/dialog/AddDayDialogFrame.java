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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Order;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;
import com.xqx.www.view.panel.CheckInPanel;

/**
 * 续住界面
 * 
 * @author xqx
 *
 */
public class AddDayDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();
	//内容展示面板
	private CheckInPanel checkInPanel;
	// 订单信息
	private Order order;
	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close, enter;
	private JTextField startTime, endTime, addDay;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel lblyyyymmddHhmmss;
	
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
	public AddDayDialogFrame(CheckInPanel panel, Order order) throws Exception {

		this.checkInPanel = panel;
		this.order = order;
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		startTime = new JTextField(
				DateUtil.getLocalTime(order.getStartTime()));
		startTime.setEditable(false);
		startTime.setBounds(109, 80, 178, 25);
		endTime = new JTextField(DateUtil.getLocalTime(order.getEndTime()));
		endTime.setEditable(false);
		endTime.setBounds(109, 140, 178, 25);
		addDay = new JTextField();
		addDay.setBounds(109, 200, 178, 25);

		enter = new JButton(new ImageIcon("image/enter.png"));
		enter.setRolloverIcon(new ImageIcon("image/enterC.png"));
		enter.setBounds(147, 249, 100, 31);
		setbutton(enter);
		enter.addActionListener(new BtnListener());

		bkim.add(enter);
		bkim.add(startTime);
		bkim.add(endTime);
		bkim.add(addDay);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		
		label_2 = new JLabel("\u5165\u4F4F\u65F6\u95F4");
		label_2.setBounds(109, 52, 54, 15);
		bkim.add(label_2);
		
		label_3 = new JLabel("\u9000\u623F\u65F6\u95F4");
		label_3.setBounds(109, 115, 54, 15);
		bkim.add(label_3);
		
		lblyyyymmddHhmmss = new JLabel("\u7EED\u4F4F\u65F6\u95F4(yyyy-MM-dd HH:mm:ss)");
		lblyyyymmddHhmmss.setBounds(109, 175, 180, 15);
		bkim.add(lblyyyymmddHhmmss);
		
		
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
			}else if(e.getSource() == enter) {
				
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "update t_order set endTime = ? where id = ?";
				boolean result = helper.executeSql(sql, new Object[]{
						DateUtil.getLocalTime(addDay.getText()), order.getId()});
				AWTUtil.updateContent(checkInPanel);
				dispose();
				
				if(result) {
					JOptionPane.showMessageDialog(null, "续住成功,退房时间延长到：" 
							+ addDay.getText());
				}
			}
		}

	}
}
