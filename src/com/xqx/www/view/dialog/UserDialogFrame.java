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
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.StringUtil;
import com.xqx.www.view.panel.VipPanel;

/**
 * 客户信息添加/修改界面
 * 
 * @author xqx
 *
 */
public class UserDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();
	// 内容展示面板
	private VipPanel vipPanel;
	// 客房信息
	private User user;
	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close, enter;
	private JTextField phone, identify, name, sex;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField password;
	private JTextField isVip;

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
	public UserDialogFrame(VipPanel panel, User user) throws Exception {

		this.vipPanel = panel;
		this.user = user;

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		phone = new JTextField(user.getPhone());
		phone.setBounds(147, 132, 178, 25);
		identify = new JTextField(user.getIdentify());
		identify.setBounds(147, 29, 178, 25);
		name = new JTextField(user.getName());
		name.setBounds(147, 64, 178, 25);
		sex = new JTextField(user.getSex());
		sex.setBounds(147, 97, 178, 25);

		password = new JTextField(user.getPassword());
		password.setBounds(147, 171, 178, 25);

		isVip = new JTextField(user.isVip() == null ? 
				StringUtil.DEFAULT_VALUE : String.valueOf(user.isVip()));
		isVip.setBounds(147, 214, 178, 25);

		enter = new JButton(new ImageIcon("image/enter.png"));
		enter.setRolloverIcon(new ImageIcon("image/enterC.png"));
		enter.setBounds(147, 249, 100, 31);
		setbutton(enter);
		enter.addActionListener(new BtnListener());

		bkim.add(enter);
		bkim.add(phone);
		bkim.add(identify);
		bkim.add(name);
		bkim.add(sex);
		bkim.add(password);
		bkim.add(isVip);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);

		JLabel label = new JLabel("\u53F7\u3000\u3000\u7801");
		label.setBounds(67, 137, 54, 15);
		bkim.add(label);

		JLabel label_1 = new JLabel("\u5BC6\u3000\u3000\u7801");
		label_1.setBounds(67, 174, 54, 15);
		bkim.add(label_1);

		label_2 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);

		label_3 = new JLabel("\u59D3\u3000\u3000\u540D");
		label_3.setBounds(67, 69, 54, 15);
		bkim.add(label_3);

		label_4 = new JLabel("\u6027\u3000\u3000\u522B");
		label_4.setBounds(67, 102, 54, 15);
		bkim.add(label_4);

		JLabel lblvip = new JLabel("VIP \u8EAB\u4EFD");
		lblvip.setBounds(67, 217, 54, 15);
		bkim.add(lblvip);

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
			} else if (e.getSource() == enter) {

				String nt = name.getText();
				String pst = password.getText();
				String it = identify.getText();
				String pt = phone.getText();
				String st = sex.getText();
				String vt = isVip.getText();

				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = StringUtil.DEFAULT_VALUE;
				Object[] param;
				if (user.getId() == null) {
					// 插入操作
					sql = "insert into t_user values(?, ?, ?, ?, ?, ?, ?, ?)";
					param = new Object[] { StringUtil.getUUID(), nt, pst, it,
							pt, st, vt, "I" };
				} else {
					// 更新操作
					sql = "update t_user set identify = ?,"
							+ " isVip = ?, name = ?, password = ?,"
							+ " phone = ?, rank = ?, sex = ? where id = ?";
					param = new Object[] {it, vt, nt, pst, pt, user.getRank(), st,
							user.getId() };
				}
				boolean result = helper.executeSql(sql, param);
				if (result) {
					try {
						JOptionPane.showMessageDialog(null, "操作成功");
						AWTUtil.updateContent(vipPanel);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "操作失败");
				}
			}
		}

	}
}
