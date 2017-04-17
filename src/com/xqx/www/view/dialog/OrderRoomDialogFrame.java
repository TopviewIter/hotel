package com.xqx.www.view.dialog;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

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
import com.xqx.www.po.Room;
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.DateUtil;
import com.xqx.www.util.StringUtil;
import com.xqx.www.view.panel.OrderRoomPanel;

/**
 * �ͷ��������
 * 
 * @author xqx
 *
 */
public class OrderRoomDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	private static Point origin = new Point();
	// ����չʾ���
	private OrderRoomPanel roomPanel;
	// �ͷ���Ϣ
	private Room room;
	// ������Ϣ
	private User user;
	// �������
	private ImagePanel bkim = null;
	private JButton min, close, order;
	private JTextField price, roomNum, type;
	private JLabel label_2;
	private JLabel label_4;
	private JTextField startTime;
	private JTextField endTime;

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
	public OrderRoomDialogFrame(OrderRoomPanel panel, Room room, User user)
			throws Exception {

		this.roomPanel = panel;
		this.room = room;
		this.user = user;

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		price = new JTextField(room.getPrice());
		price.setEditable(false);
		price.setBounds(147, 111, 178, 25);
		roomNum = new JTextField(room.getRoomNum());
		roomNum.setEditable(false);
		roomNum.setBounds(147, 29, 178, 25);
		type = new JTextField(room.getType());
		type.setEditable(false);
		type.setBounds(147, 70, 178, 25);
		// password.setOpaque(false);

		order = new JButton(new ImageIcon("image/orderRoom.png"));
		order.setRolloverIcon(new ImageIcon("image/orderRoomC.png"));
		order.setBounds(147, 249, 100, 31);
		setbutton(order);
		order.addActionListener(new BtnListener());

		bkim.add(order);
		bkim.add(price);
		bkim.add(roomNum);
		bkim.add(type);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);

		JLabel label = new JLabel("\u4EF7\u3000\u3000\u683C");
		label.setBounds(67, 116, 54, 15);
		bkim.add(label);

		label_2 = new JLabel("\u623F\u3000\u3000\u53F7");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);

		label_4 = new JLabel("\u7C7B\u3000\u3000\u578B");
		label_4.setBounds(67, 75, 54, 15);
		bkim.add(label_4);
		
		JLabel label_1 = new JLabel("\u5165\u4F4F\u65F6\u95F4");
		label_1.setBounds(67, 156, 54, 15);
		bkim.add(label_1);
		
		startTime = new JTextField((String) null);
		startTime.setBounds(147, 151, 178, 25);
		bkim.add(startTime);
		
		JLabel label_5 = new JLabel("\u9000\u623F\u65F6\u95F4");
		label_5.setBounds(67, 197, 54, 15);
		bkim.add(label_5);
		
		endTime = new JTextField((String) null);
		endTime.setBounds(147, 192, 178, 25);
		bkim.add(endTime);

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
			} else if (e.getSource() == order) {

				if(!StringUtil.isNotEmpty(startTime.getText())
						|| !StringUtil.isNotEmpty(endTime.getText())) {
					JOptionPane.showMessageDialog(null, "Ԥ��ʱ�䲻��Ϊ��");
					return;
				}
				double day = DateUtil.dayDis(DateUtil.getLocalTime(startTime.getText()),
						DateUtil.getLocalTime(endTime.getText()));
				
				int option = JOptionPane.showConfirmDialog(null, 
						"��Ҫ֧������Ϊ:" + day * Double.valueOf(room.getPrice())
						+ ",�Ƿ�����֧����");
				if(option != 2){
					orderRoom(day, option);
				}else{
					JOptionPane.showMessageDialog(null, "Ԥ��������ȡ��");
				}
				
			}
			
			
		}

		private void orderRoom(double day, int option) {
			
			SqlHelper helper = SqlHelper.getSqlHelper();
			String sql = "update t_room set state = 'Ԥ��' where id = ?";
			boolean result = helper.executeSql(sql,
					new Object[] { room.getId() });
			if (result) {
				sql = "insert into t_order values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				helper.executeSql(sql, new Object[] { StringUtil.getUUID(),
						user.getId(), room.getId(), 
						day * Double.parseDouble(room.getPrice()), option == 0 ? 
								true : false,
						"Ԥ��", DateUtil.getLocalTime(startTime.getText()),
						DateUtil.getLocalTime(endTime.getText()), new Date() });
				if(option == 0) {
					JOptionPane.showMessageDialog(null, "����֧���ɹ�,�밴ʱ��ס");
				} else {
					JOptionPane.showMessageDialog(null, "Ԥ���ɹ�,��ץ��ʱ�䵽ǰ̨���Ѱ�����ס����");
				}
				
				AWTUtil.updateContent(roomPanel);
				dispose();
			}
			if (!result) {
				JOptionPane.showMessageDialog(null, "Ԥ������ʧ��,���Ժ�");
			}
		}
		
	}
}
