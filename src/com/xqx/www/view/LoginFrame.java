package com.xqx.www.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Staff;
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;

/**
 * 登录界面
 * 
 * @author xqx
 *
 */
public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();

	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close, login;
	private JComboBox<String> user;
	private JPasswordField password;
	private JLabel register;

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
	public LoginFrame() throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/loginbk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		Vector<String> userId = new Vector<String>();
		userId.add("18823450934");
		userId.add("2");
		userId.add("3");
		userId.add("4");
		userId.add("5");
		user = new JComboBox<String>(userId);
		user.setEditable(true);
		user.setBounds(131, 145, 187, 26);

		password = new JPasswordField(50);
		password.setBounds(131, 180, 178, 25);
		password.setBorder(new MatteBorder(0, 0, 0, 0, Color.blue));
		password.setOpaque(false);
		password.setEchoChar('*');

		register = new JLabel("注册新用户?");
		register.setBounds(240, 220, 178, 25);
		register.addMouseListener(new LabelListener());

		login = new JButton(new ImageIcon("image/login.png"));
		login.setRolloverIcon(new ImageIcon("image/loginC.png"));
		login.setBounds(131, 253, 180, 31);
		setbutton(login);
		login.addActionListener(new BtnListener());

		bkim.add(user);
		bkim.add(password);
		bkim.add(register);
		bkim.add(login);

		windowsmenu();

		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		this.setSize(400, 290);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == min) {
				setState(JFrame.ICONIFIED);
			} else if (e.getSource() == close) {
				dispose();
			} else if (e.getSource() == login) {
				SqlHelper helper = SqlHelper.getSqlHelper();
				// 员工登录
				String staffSql = "{call proc_staff_exist(?, ?, ?)}";
				CallableStatement staffResult = helper.executeProcess(staffSql, new String[] { String.valueOf(user.getSelectedItem()),
						String.valueOf(password.getPassword()) }, 3, Types.INTEGER);
				// 用户登录
				String userSql = "{call proc_user_exist(?, ?, ?)}";
				CallableStatement userResult = helper.executeProcess(userSql,
						new String[] { String.valueOf(user.getSelectedItem()),
								String.valueOf(password.getPassword()) }, 3, Types.INTEGER);
				try {
					if (staffResult.getInt(3) <= 0
							&& userResult.getInt(3) <= 0) {
						JOptionPane.showMessageDialog(null, "用户不存在或密码错误");
					} else {
						if (staffResult.getInt(3) > 0) {
							String getStaffSql = "select * from t_staff where phone = ? and password = ?";
							ResultSet staffInfo = helper.query(
									getStaffSql,
									new String[] {
											String.valueOf(user
													.getSelectedItem()),
											String.valueOf(password
													.getPassword()) });
							// 员工登录界面
							new StaffIndexFrame(buildStaff(staffInfo));
						} else {
							String getUserSql = "select * from t_user where phone = ? and password = ?";
							ResultSet userInfo = helper.query(
									getUserSql,
									new String[] {
											String.valueOf(user
													.getSelectedItem()),
											String.valueOf(password
													.getPassword()) });
							// 用户登录界面
							new UserIndexFrame(buildUser(userInfo));
						}
						dispose();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		}

	}

	private Staff buildStaff(ResultSet rs) {

		Staff staff = new Staff();
		try {
			while (rs.next()) {
				staff.setId(rs.getString("id"));
				staff.setIdentify(rs.getString("identify"));
				staff.setJob(rs.getString("job"));
				staff.setName(rs.getString("name"));
				staff.setPassword(rs.getString("password"));
				staff.setPhone(rs.getString("phone"));
				staff.setSalary(rs.getString("salary"));
				staff.setSex(rs.getString("sex"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staff;
	}

	private User buildUser(ResultSet rs) {

		User user = new User();
		try {
			while (rs.next()) {
				user.setId(rs.getString("id"));
				user.setIdentify(rs.getString("identify"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setSex(rs.getString("sex"));
				user.setVip(rs.getBoolean("isVip"));
				user.setRank(rs.getString("rank"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	class LabelListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == register) {
				try {
					new RegisterFrame();
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
