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
import com.xqx.www.po.Room;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.util.StringUtil;
import com.xqx.www.view.panel.RoomPanel;

/**
 * �ͷ��������
 * 
 * @author xqx
 *
 */
public class RoomDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// ȫ�ֵ�λ�ñ��������ڱ�ʾ����ڴ����ϵ�λ��
	private static Point origin = new Point();
	//����չʾ���
	private RoomPanel roomPanel;
	// �ͷ���Ϣ
	private Room room;
	// �������
	private ImagePanel bkim = null;
	private JButton min, close, update, delete, insert;
	private JTextField price, roomNum, state, type;

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
	public RoomDialogFrame(RoomPanel panel, Room room) throws Exception {

		this.roomPanel = panel;
		this.room = room;
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);

		price = new JTextField(room.getPrice());
		price.setBounds(147, 132, 178, 25);
		roomNum = new JTextField(room.getRoomNum());
		roomNum.setBounds(147, 29, 178, 25);
		state = new JTextField(room.getState());
		state.setBounds(147, 64, 178, 25);
		type = new JTextField(room.getType());
		type.setBounds(147, 97, 178, 25);
		remark = new JTextArea(room.getRemark());
		remark.setBounds(147, 169, 178, 50);
		remark.setLineWrap(true);
		//password.setOpaque(false);

		insert = new JButton(new ImageIcon("image/roomInsert.png"));
		insert.setRolloverIcon(new ImageIcon("image/roomInsertC.png"));
		insert.setBounds(147, 249, 100, 31);
		setbutton(insert);
		insert.addActionListener(new BtnListener());
		
		update = new JButton(new ImageIcon("image/roomUpdate.png"));
		update.setRolloverIcon(new ImageIcon("image/roomUpdateC.png"));
		update.setBounds(73, 249, 100, 31);
		setbutton(update);
		update.addActionListener(new BtnListener());
		
		delete = new JButton(new ImageIcon("image/roomDel.png"));
		delete.setRolloverIcon(new ImageIcon("image/roomDelC.png"));
		delete.setBounds(225, 249, 100, 31);
		setbutton(delete);
		delete.addActionListener(new BtnListener());
		
		opsButtonVisible(room, insert, delete, update);

		bkim.add(insert);
		bkim.add(delete);
		bkim.add(remark);
		bkim.add(update);
		bkim.add(price);
		bkim.add(roomNum);
		bkim.add(state);
		bkim.add(type);

		windowsmenu();
		this.setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		
		JLabel label = new JLabel("\u4EF7\u3000\u3000\u683C");
		label.setBounds(67, 137, 54, 15);
		bkim.add(label);
		
		JLabel label_1 = new JLabel("\u5907\u3000\u3000\u6CE8");
		label_1.setBounds(67, 174, 54, 15);
		bkim.add(label_1);
		
		label_2 = new JLabel("\u623F\u3000\u3000\u53F7");
		label_2.setBounds(67, 34, 54, 15);
		bkim.add(label_2);
		
		label_3 = new JLabel("\u72B6\u3000\u3000\u6001");
		label_3.setBounds(67, 69, 54, 15);
		bkim.add(label_3);
		
		label_4 = new JLabel("\u7C7B\u3000\u3000\u578B");
		label_4.setBounds(67, 102, 54, 15);
		bkim.add(label_4);
		
		
		this.setSize(400, 290);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void opsButtonVisible(Room r, JButton insertBtn, 
			JButton deleteBtn, JButton updateBtn) {
		
		if(r.getId() == null){
			insertBtn.setVisible(true);
			deleteBtn.setVisible(false);
			updateBtn.setVisible(false);
		}else{
			insertBtn.setVisible(false);
			deleteBtn.setVisible(true);
			updateBtn.setVisible(true);
		}
	}

	class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == min) {
				setState(JFrame.ICONIFIED);
			}else if(e.getSource() == close) {
				dispose();
			}else if(e.getSource() == update) {
				String nt = roomNum.getText();
				String st = state.getText();
				String tt = type.getText();
				Double pt = Double.valueOf(price.getText());
				String rt = remark.getText();
				
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "update t_room set roomNum = ? "
						+ ",state = ?, type = ?, price = ? "
						+ ", remark = ? where id = ?";
				boolean result = helper.executeSql(sql, new Object[]{
						nt, st, tt, pt, rt, room.getId()});
				if(result){
					try {
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
						AWTUtil.updateContent(roomPanel);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}else{
					JOptionPane.showMessageDialog(null, "�޸�ʧ��");
				}
			}else if(e.getSource() == delete) {
				
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "delete from t_room where id = ?";
				boolean result = helper.executeSql(sql, new Object[]{room.getId()});
				if(result){
					try {
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						AWTUtil.updateContent(roomPanel);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}else{
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			}else if(e.getSource() == insert) {
				
				String nt = roomNum.getText();
				String st = state.getText();
				String tt = type.getText();
				Double pt = Double.valueOf(price.getText());
				String rt = remark.getText();
				
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "insert into t_room values(?, ?, ?, ?, ?, ?)";
				boolean result = helper.executeSql(sql, new Object[]{
						StringUtil.getUUID(), nt, st, tt, pt, rt});
				if(result){
					try {
						JOptionPane.showMessageDialog(null, "�����ɹ�");
						AWTUtil.updateContent(roomPanel);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					} 
				}else{
					JOptionPane.showMessageDialog(null, "����ʧ��");
				}
			}
		}

	}
}
