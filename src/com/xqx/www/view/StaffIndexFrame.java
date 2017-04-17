package com.xqx.www.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Hotel;
import com.xqx.www.po.Staff;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;
import com.xqx.www.view.panel.AdvancePanel;
import com.xqx.www.view.panel.ChartPanel;
import com.xqx.www.view.panel.CheckInPanel;
import com.xqx.www.view.panel.RoomPanel;
import com.xqx.www.view.panel.StaffPanel;
import com.xqx.www.view.panel.SystemManagerPanel;
import com.xqx.www.view.panel.VipPanel;

/**
 * 注册界面
 * 
 * @author xqx
 *
 */
public class StaffIndexFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// 用于获得窗口的大小
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private final static int height = Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	// 菜单图片
	private final static String[] managerImages = { "image/advance.png",
			"image/checkIn.png", "image/staff.png", "image/vip.png",
			"image/room.png", "image/chart.png", "image/systemManager.png" };

	// 点击菜单图片
	private final static String[] managerImagesRoll = { "image/advanceRoll.png",
			"image/checkInRoll.png", 
			"image/staffRoll.png", "image/vipRoll.png", "image/roomRoll.png",
			"image/chartRoll.png", "image/systemManagerRoll.png" };

	// 菜单图片text
	private final static String[] managerImagesText = { "advance", "checkIn",
			"staff", "vip", "room", "chart",
			"systemManager" };
	
	// 菜单图片
		private final static String[] images = { "image/advance.png",
				"image/checkIn.png", 
				"image/room.png", "image/chart.png", "image/systemManager.png" };

		// 点击菜单图片
		private final static String[] imagesRoll = { "image/advanceRoll.png",
				"image/checkInRoll.png", 
				 "image/roomRoll.png",
				"image/chartRoll.png", "image/systemManagerRoll.png" };

		// 菜单图片text
		private final static String[] imagesText = { "advance", "checkIn",
				"room", "chart",
				"systemManager" };

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private final static Point origin = new Point();

	// 菜单按钮
	private JButton[] menuBtn;

	// 登录 人信息
	private Staff loginStaff;
	// 酒店信息
	private Hotel hotel;

	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	private JPanel panel_1;
	private CardLayout contentCard;

	// 构造函数
	public StaffIndexFrame(Staff staff) throws Exception {

		loginStaff = staff;
		hotel = getHotelInfo();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);

		// 系统标题开始
		lblNewLabel = new JLabel(hotel.getName() + "管理系统", JLabel.CENTER);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 21, (int) (width * 0.8f), 67);
		bkim.add(lblNewLabel);
		// 系统标题结束

		// 系统菜单开始
		panel = new JPanel();
		panel.setBounds(0, 87, (int) (width * 0.8f), 67);
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(2, 6));
		buildMenuPanel(panel);
		bkim.add(panel);
		// 系统菜单结束

		// 中间内容部分开始
		panel_1 = new JPanel();
		panel_1.setBounds(24, 164, (int) (width * 0.8f) - 48,
				(int) (height * 0.8f) - 200);
		panel_1.setOpaque(false);
		contentCard = new CardLayout();
		panel_1.setLayout(contentCard);
		// 默认显示客房信息
		panel_1.add(new JScrollPane(new RoomPanel()), "room");
		bkim.add(panel_1);
		// 中间内容部分结束

		// 登录信息开始
		lblNewLabel_1 = new JLabel(loginStaff.getName() + " 于 "
				+ DateUtil.getLocalTime(new Date()) + " 登录 ");
		lblNewLabel_1.setBounds(24, (int) (height * 0.8f) - 25,
				(int) (width * 0.8f), 15);
		bkim.add(lblNewLabel_1);
		// 登录信息结束

		this.setSize((int) (width * 0.8f), (int) (height * 0.8f));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void buildMenuPanel(JPanel panel) {

		if("管理员".equals(loginStaff.getJob())) {
			menuBtn = new JButton[managerImages.length];
			for (int i = 0; i < managerImages.length; i++) {
				panel.add(createMenu(managerImages[i], managerImagesRoll[i], menuBtn[i],
						managerImagesText[i]));
			}
		} else {
			menuBtn = new JButton[images.length];
			for (int i = 0; i < images.length; i++) {
				panel.add(createMenu(images[i], imagesRoll[i], menuBtn[i],
						imagesText[i]));
			}
		}
		
	}

	private JButton createMenu(String imageSrc, String rollIcon, JButton btn,
			String imageText) {

		btn = new JButton(new ImageIcon(imageSrc));
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setOpaque(false);
		btn.setRolloverIcon(new ImageIcon(rollIcon));
		btn.addActionListener(new BtnListener(imageText));
		return btn;
	}

	private Hotel getHotelInfo() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select top 1 * from v_hotel";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildHotel(rs);

	}

	private Hotel buildHotel(ResultSet rs) {

		Hotel hotel = new Hotel();
		try {
			while (rs.next()) {
				hotel.setAddress(rs.getString("address"));
				hotel.setId(rs.getString("id"));
				hotel.setManagerId(rs.getString("managerId"));
				hotel.setName(rs.getString("name"));
				hotel.setPhone(rs.getString("phone"));
				hotel.setRank(rs.getString("rank"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotel;
	}

	// 窗口操作控制菜单
	private void windowsmenu() {

		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds((int) (width * 0.8f) - 56, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		min.setToolTipText("最小化");
		// 把按钮设置成透明
		min.setContentAreaFilled(false);
		min.addActionListener(new BtnListener("min"));

		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds((int) (width * 0.8f) - 29, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		close.setContentAreaFilled(false);
		close.setToolTipText("关闭");
		close.addActionListener(new BtnListener("close"));

		bkim.add(min);
		bkim.add(close);
	}

	class BtnListener implements ActionListener {

		private String text;

		public BtnListener(String text) {
			this.text = text;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (text) {
			case "min":
				setState(JFrame.ICONIFIED);
				break;
			case "close":
				dispose();
				break;
			case "advance":
				panel_1.add(new JScrollPane(new AdvancePanel()), "advance");
				contentCard.show(panel_1, "advance");
				break;
			case "chart":
				panel_1.add(new ChartPanel(), "chart");
				contentCard.show(panel_1, "chart");
				break;
			case "checkIn":
				panel_1.add(new JScrollPane(new CheckInPanel()), "checkIn");
				contentCard.show(panel_1, "checkIn");
				break;
			case "staff":
				panel_1.add(new StaffPanel(), "staff");
				contentCard.show(panel_1, "staff");
				break;
			case "systemManager":
				panel_1.add(new SystemManagerPanel(StaffIndexFrame.this), "systemManager");
				contentCard.show(panel_1, "systemManager");
				break;
			case "vip":
				panel_1.add(new VipPanel(), "vip");
				contentCard.show(panel_1, "vip");
				break;
			case "room":
				contentCard.show(panel_1, "room");
				break;
			}

		}

	}
}
