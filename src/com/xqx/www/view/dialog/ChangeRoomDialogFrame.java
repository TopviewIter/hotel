package com.xqx.www.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.xqx.www.component.ImagePanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Order;
import com.xqx.www.po.Room;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.view.panel.CheckInPanel;
import java.awt.BorderLayout;

public class ChangeRoomDialogFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// 全局的位置变量，用于表示鼠标在窗口上的位置
	private static Point origin = new Point();
		
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private final static int height = Toolkit.getDefaultToolkit()
			.getScreenSize().height;

	private static final int panelCellWidth = 117;
	private static final int panelCellHeight = 117;
	private static final int offset = 10;
	private static final int scrollOffset = 6;

	private ImagePanel bkim = null;
	private JPanel roomPanel;
	private JPanel opsPanel;
	private JButton min, close;

	private CheckInPanel panel;
	private List<Room> rooms;
	private Order order;
	private Room room;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;

	public ChangeRoomDialogFrame(CheckInPanel panel, Order order, Room room)
			throws Exception {

		this.panel = panel;
		this.order = order;
		this.room = room;

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Image loginbk = ImageIO.read(new File("image/bk.png"));
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);
		
		// 中间内容部分开始
		roomPanel = new JPanel();
		roomPanel.setBounds(0, 31, (int) (width * 0.8f) - 50,
				383);
		roomPanel.setOpaque(false);
		// 默认显示客房信息
		fillContent();
		bkim.add(roomPanel);
		// 中间内容部分结束
		
		// 下面说明部分开始
		opsPanel = new JPanel();
		opsPanel.setBackground(Color.YELLOW);
		opsPanel.setLayout(null);
		opsPanel.setBounds(0, 414, (int) (width * 0.8f) - 50,
				80);
		bkim.add(opsPanel);
		
		label_1 = new JLabel("\u6362\u623F\u8BF4\u660E\uFF1A");
		label_1.setBounds(24, 10, 82, 15);
		opsPanel.add(label_1);
		
		label_2 = new JLabel("\u8981\u6362\u623F\u65F6\uFF0C\u53EA\u80FD\u9009\u62E9\u5904\u4E8E\u7A7A\u95F2\u72B6\u6001\u7684\u623F\u95F4");
		label_2.setBounds(55, 36, 273, 15);
		opsPanel.add(label_2);
		
		label_3 = new JLabel("\u70B9\u51FB\u76EE\u6807\u623F\u95F4\u4EE5\u5B9E\u73B0\u6362\u623F");
		label_3.setBounds(55, 57, 210, 15);
		opsPanel.add(label_3);
		// 下面说明部分结束

		windowsmenu();
		setUndecorated(true);
		AWTUtil.windowMove(this, origin);
		AWTUtil.setOpacity(this);
		getContentPane().add(bkim);
		setBounds(width / 3, height / 3, (int) (width * 0.8f) - 50, (int) (height * 0.8f) - 120);
		setVisible(true);
	}

	public void updateContent() {

		fillContent();
	}

	private void fillContent() {

		rooms = getAllRoom();
		int wHeight = (int) (height * 0.8f) - 200;
		int panelHeight = (panelCellHeight + offset)
				* ((rooms.size() + 6 - 1) / 6);
		boolean isScroll = panelHeight > wHeight ? true : false;
		roomPanel.setLayout(new BorderLayout(0, 0));
		roomPanel.add(new JScrollPane(buildAllRoomView(rooms, isScroll)));
	}

	private JPanel buildAllRoomView(List<Room> rooms, boolean isScroll) {

		JPanel p = new JPanel();
		p.setLayout(null);
		p.setPreferredSize(new Dimension((int) (width * 0.8f) - 80, 
				(panelCellHeight + offset)
				* ((rooms.size() + 6 - 1) / 6)));
		int cols = 6;
		for (int i = 0; i < rooms.size(); i++) {
			JLabel label = new JLabel(buildRoomLabel(rooms.get(i)),
					JLabel.CENTER);
			label.setOpaque(true);
			if (isScroll) {
				label.setBounds((i % cols) * (panelCellWidth + scrollOffset)
						+ 10, (i / cols) * (panelCellHeight + scrollOffset)
						+ 10, panelCellWidth, panelCellHeight);
			} else {
				label.setBounds((i % cols) * (panelCellWidth + offset) + 10,
						(i / cols) * (panelCellHeight + offset) + 10,
						panelCellWidth, panelCellHeight);
			}

			if ("空闲".equals(rooms.get(i).getState())) {
				label.addMouseListener(new LabelListener(panel, order, room,
						rooms.get(i)));
				label.setForeground(Color.RED);
				label.setBackground(Color.GREEN);
			} else if ("预定".equals(rooms.get(i).getState())) {
				label.setForeground(Color.BLACK);
				label.setBackground(Color.GRAY);
			} else if ("入住".equals(rooms.get(i).getState())) {
				label.setForeground(Color.WHITE);
				label.setBackground(Color.RED);
			}
			p.add(label);
		}
		return p;
	}

	private List<Room> getAllRoom() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_room order by roomNum";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildRooms(rs);

	}

	private List<Room> buildRooms(ResultSet rs) {

		List<Room> rooms = new ArrayList<Room>();
		try {
			while (rs.next()) {
				Room r = new Room();
				r.setId(rs.getString("id"));
				r.setPrice(String.valueOf(rs.getDouble("price")));
				r.setRemark(rs.getString("remark"));
				r.setRoomNum(rs.getString("roomNum"));
				r.setState(rs.getString("state"));
				r.setType(rs.getString("type"));
				rooms.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rooms;
	}

	private String buildRoomLabel(Room r) {

		StringBuilder sb = new StringBuilder("<html><center>");
		sb.append("房号: ").append(r.getRoomNum()).append("<br/>");
		sb.append("状态: ").append(r.getState()).append("<br/>");
		sb.append("类型: ").append(r.getType()).append("<br/>");
		sb.append("价格: ").append(r.getPrice()).append("<br/>");
		sb.append("</center></html>");
		return sb.toString();
	}

	class LabelListener extends MouseAdapter {

		private Order order;
		private Room room;
		private Room newRoom;
		private CheckInPanel panel;

		public LabelListener(CheckInPanel panel, Order order, Room room,
				Room newRoom) {
			this.order = order;
			this.panel = panel;
			this.room = room;
			this.newRoom = newRoom;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "update t_room set state = '入住' where id = ?";
				boolean result = helper.executeSql(sql,
						new Object[] { newRoom.getId() });
				if(result) {
					sql = "update t_room set state = '空闲' where id = ?";
					result = helper.executeSql(sql,
							new Object[] { room.getId() });
					if(result){
						sql = "update t_order set roomId = ? where id = ?";
						result = helper.executeSql(sql,
								new Object[] { newRoom.getId(), order.getId() });
					}
				}
				if(!result){
					JOptionPane.showMessageDialog(null, "更换房间失败,请稍后");
				}else{
					JOptionPane.showMessageDialog(null, "更换房间成功");
					AWTUtil.updateContent(panel);
					dispose();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	// 窗口操作控制菜单
	public void windowsmenu() {

		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds((int) (width * 0.8f) - 109, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		setbutton(min);
		min.setToolTipText("最小化");
		min.addActionListener(new BtnListener());

		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds((int) (width * 0.8f) - 80, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		setbutton(close);
		close.setToolTipText("关闭");
		close.addActionListener(new BtnListener());

		bkim.add(min);
		bkim.add(close);
	}

	public void setbutton(JButton jb) {

		jb.setContentAreaFilled(false);
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.setOpaque(false);
	}

	class BtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == min) {
				setState(JFrame.ICONIFIED);
			} else if (e.getSource() == close) {
				dispose();
			}
		}
	}

}
