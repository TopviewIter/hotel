package com.xqx.www.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
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
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;
import com.xqx.www.view.panel.OrderRoomPanel;

/**
 * �û���¼����
 * 
 * @author xqx
 *
 */
public class UserIndexFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// ���ڻ�ô��ڵĴ�С
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private final static int height = Toolkit.getDefaultToolkit()
			.getScreenSize().height;

	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	private final static Point origin = new Point();

	// �Ƶ���Ϣ
	private Hotel hotel;

	// �������
	private ImagePanel bkim = null;
	private JButton min, close;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	//private JPanel panel;
	private JPanel panel_1;
	private CardLayout contentCard;

	// ���캯��
	public UserIndexFrame(User user) throws Exception {

		// loginUser = user;
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

		// ϵͳ���⿪ʼ
		lblNewLabel = new JLabel(hotel.getName() + "����Ԥ��", JLabel.CENTER);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 21, (int) (width * 0.8f), 67);
		bkim.add(lblNewLabel);
		// ϵͳ�������

		// ϵͳ�˵���ʼ
		//panel = new JPanel();
		//panel.setBounds(0, 87, (int) (width * 0.8f), 67);
		//panel.setOpaque(false);
		//panel.setLayout(new GridLayout(2, 6));
		//buildMenuPanel(panel);
		//bkim.add(panel);
		// ϵͳ�˵�����

		// �м����ݲ��ֿ�ʼ
		panel_1 = new JPanel();
		panel_1.setBounds(24, 87, (int) (width * 0.8f) - 48,
				(int) (height * 0.8f) - 122);
		panel_1.setOpaque(false);
		contentCard = new CardLayout();
		panel_1.setLayout(contentCard);
		// Ĭ����ʾ�ͷ���Ϣ
		panel_1.add(new JScrollPane(new OrderRoomPanel(user)), "room");
		bkim.add(panel_1);
		// �м����ݲ��ֽ���

		// ��¼��Ϣ��ʼ
		lblNewLabel_1 = new JLabel(user.getName() + " �� "
				+ DateUtil.getLocalTime(new Date()) + " ��¼ ");
		lblNewLabel_1.setBounds(24, (int) (height * 0.8f) - 25,
				(int) (width * 0.8f), 15);
		bkim.add(lblNewLabel_1);
		// ��¼��Ϣ����

		this.setSize((int) (width * 0.8f), (int) (height * 0.8f));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	// ���ڲ������Ʋ˵�
	private void windowsmenu() {

		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds((int) (width * 0.8f) - 56, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		min.setToolTipText("��С��");
		// �Ѱ�ť���ó�͸��
		min.setContentAreaFilled(false);
		min.addActionListener(new BtnListener("min"));

		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds((int) (width * 0.8f) - 29, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		close.setContentAreaFilled(false);
		close.setToolTipText("�ر�");
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
			}
		}

	}
}
