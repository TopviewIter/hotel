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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Order;
import com.xqx.www.po.Room;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;
import com.xqx.www.view.panel.CheckInPanel;

/**
 * �ͷ��������
 * 
 * @author xqx
 *
 */
public class PayDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	private static Point origin = new Point();
	//����չʾ���
	private CheckInPanel checkInPanel;
	// ������Ϣ
	private Order order;
	// ������Ϣ
	private Room room;
	// �������
	private ImagePanel bkim = null;
	private JButton min, close, enter;
	private JTextField money, startTime, endTime, price;

	private JTextArea remark;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	
	public void setbutton(JButton jb) {

		jb.setContentAreaFilled(false);
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setOpaque(false);
	}

	// ���ڲ������Ʋ˵�
	public void windowsmenu() {

		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds(346, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		setbutton(min);
		min.setToolTipText("��С��");
		min.addActionListener(new BtnListener());

		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds(370, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		setbutton(close);
		close.setToolTipText("�ر�");
		close.addActionListener(new BtnListener());

		bkim.add(min);
		bkim.add(close);
	}

	// ���캯��
	public PayDialogFrame(CheckInPanel panel, Order order, Room room) throws Exception {

		this.checkInPanel = panel;
		this.order = order;
		this.room = room;
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		startTime = new JTextField(
				DateUtil.getLocalTime(order.getStartTime()));
		startTime.setEditable(false);
		startTime.setBounds(147, 29, 178, 25);
		endTime = new JTextField(DateUtil.getLocalTime(order.getEndTime()));
		endTime.setEditable(false);
		endTime.setBounds(147, 64, 178, 25);
		price = new JTextField(room.getPrice());
		price.setEditable(false);
		price.setBounds(147, 97, 178, 25);
		double day = DateUtil.dayDis(order.getStartTime(),
				order.getEndTime());
		money = new JTextField(day + " * " + room.getPrice() + " = "
				+ (day * Double.parseDouble(room.getPrice())));
		money.setEditable(false);
		money.setBounds(147, 132, 178, 25);
		remark = new JTextArea(room.getRemark());
		remark.setBounds(147, 169, 178, 50);
		remark.setLineWrap(true);
		//password.setOpaque(false);

		enter = new JButton(new ImageIcon("image/checkToPay.png"));
		enter.setRolloverIcon(new ImageIcon("image/checkToPayC.png"));
		enter.setBounds(147, 249, 100, 31);
		setbutton(enter);
		enter.addActionListener(new BtnListener());

		bkim.add(enter);
		bkim.add(remark);
		bkim.add(money);
		bkim.add(startTime);
		bkim.add(endTime);
		bkim.add(price);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		
		JLabel label = new JLabel("\u5E94\u6536\u91D1\u989D");
		label.setBounds(67, 137, 54, 15);
		bkim.add(label);
		
		JLabel label_1 = new JLabel("\u5907\u3000\u3000\u6CE8");
		label_1.setBounds(67, 174, 54, 15);
		bkim.add(label_1);
		
		label_2 = new JLabel("\u5165\u4F4F\u65F6\u95F4");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);
		
		label_3 = new JLabel("\u9000\u623F\u65F6\u95F4");
		label_3.setBounds(67, 69, 54, 15);
		bkim.add(label_3);
		
		label_4 = new JLabel("\u623F\u95F4\u4EF7\u683C");
		label_4.setBounds(67, 102, 54, 15);
		bkim.add(label_4);
		
		
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
				String sql = "update t_order set isPay = 'true', state = '���' where id = ?";
				boolean result = helper.executeSql(sql, new Object[]{
						order.getId()});
				if(result){
					try {
						JOptionPane.showMessageDialog(null, "�տ�ɹ�");
						sql = "update t_room set state = '����' where id = ?";
						helper.executeSql(sql, new Object[]{
								room.getId()});
						AWTUtil.updateContent(checkInPanel);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}else{
					JOptionPane.showMessageDialog(null, "�տ�ʧ��");
				}
			}
		}

	}
}
