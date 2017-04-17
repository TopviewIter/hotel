package com.xqx.www.view;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.StringUtil;

/**
 * 注册界面
 * 
 * @author xqx
 *
 */
public class RegisterFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();

	// 定义组件
	private ImagePanel bkim = null;
	private JButton min, close, register;
	private JTextField identify, name, phone, sex;
	private JPasswordField password, passwordRepeat;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	
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
	public RegisterFrame() throws Exception {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		identify = new JTextField();
		identify.setBounds(147, 132, 178, 25);
		name = new JTextField();
		name.setBounds(147, 29, 178, 25);
		phone = new JTextField();
		phone.setBounds(147, 64, 178, 25);
		sex = new JTextField();
		sex.setBounds(147, 97, 178, 25);
		
		password = new JPasswordField(50);
		password.setBounds(147, 169, 178, 25);
		password.setBorder(new MatteBorder(0, 0, 0, 0, Color.blue));
		//password.setOpaque(false);
		password.setEchoChar('*');
		
		passwordRepeat = new JPasswordField(50);
		passwordRepeat.setBounds(147, 204, 178, 25);
		passwordRepeat.setBorder(new MatteBorder(0, 0, 0, 0, Color.blue));
		//passwordRepeat.setOpaque(false);
		passwordRepeat.setEchoChar('*');

		register = new JButton(new ImageIcon("image/login.png"));
		register.setRolloverIcon(new ImageIcon("image/loginC.png"));
		register.setBounds(114, 249, 183, 31);
		setbutton(register);
		register.addActionListener(new BtnListener());

		bkim.add(password);
		bkim.add(passwordRepeat);
		bkim.add(register);
		bkim.add(identify);
		bkim.add(name);
		bkim.add(phone);
		bkim.add(sex);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		
		JLabel label = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		label.setBounds(67, 137, 54, 15);
		bkim.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u3000\u3000\u7801");
		label_1.setBounds(67, 174, 54, 15);
		bkim.add(label_1);
		
		label_2 = new JLabel("\u59D3\u3000\u3000\u540D");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);
		
		label_3 = new JLabel("\u8054\u7CFB\u7535\u8BDD");
		label_3.setBounds(67, 69, 54, 15);
		bkim.add(label_3);
		
		label_4 = new JLabel("\u6027\u3000\u3000\u522B");
		label_4.setBounds(67, 102, 54, 15);
		bkim.add(label_4);
		
		label_5 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_5.setBounds(67, 209, 54, 15);
		bkim.add(label_5);
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
			}else if(e.getSource() == register) {
				String nt = name.getText();
				String pt = phone.getText();
				String st = sex.getText();
				String it = identify.getText();
				String pst = String.valueOf(password.getPassword());
				String psrt = String.valueOf(passwordRepeat.getPassword());
				//检测性别的输入
				checkParam(nt, pt, it, st, pst, psrt);
				
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "insert into t_user values(?, ?, ?, ?, ?, ?, ?, ?)";
				boolean result = helper.executeSql(sql, new Object[]{
						StringUtil.getUUID(),
						nt, pst, it, pt, st, false, "I"});
				if(result){
					try {
						new LoginFrame();
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}else{
					JOptionPane.showMessageDialog(null, "注册失败,请稍后重试");
				}
			}
		}

		private void checkParam(String name, String phone, String identify, String st, String pst, String psrt) {
			
			if(!StringUtil.isNotEmpty(name) ||
					!StringUtil.isNotEmpty(phone) ||
					!StringUtil.isNotEmpty(identify) ||
					!StringUtil.isNotEmpty(st) ||
					!StringUtil.isNotEmpty(pst) ||
					!StringUtil.isNotEmpty(psrt)){
				
				JOptionPane.showMessageDialog(null, "请填写完整所有信息");
				return;
			}
			
			if(!"男".equals(st) && !"女".equals(st)){
				JOptionPane.showMessageDialog(null, "性别只能为男或女");
				return;
			}
			
			if(identify.length() != 18){
				JOptionPane.showMessageDialog(null, "身份证号只能为18位");
				return;
			}
			
			if(!pst.equals(psrt)){
				password.setText("");
				passwordRepeat.setText("");
				JOptionPane.showMessageDialog(null, "两次输入的密码不一致");
				return;
			}
			
			
		}
		
	}
}
