package com.xqx.www.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xqx.www.component.CustomPanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Order;
import com.xqx.www.po.Room;
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.view.dialog.AddDayDialogFrame;
import com.xqx.www.view.dialog.ChangeRoomDialogFrame;
import com.xqx.www.view.dialog.PayDialogFrame;

public class CheckInPanel extends CustomPanel {

	private static final long serialVersionUID = 1L;
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;

	private final static String[] headerText = { "姓名", "身份证号", "联系电话", "性别",
			"房号", "状态", "续住", "退房", "结账", "换房" };

	private final static Color[] cellColor = { Color.WHITE, Color.CYAN,
			Color.WHITE, Color.CYAN, Color.WHITE, null, null, null, null, null };

	private final static Rectangle[] rectangle = { new Rectangle(0, 5, 60, 30),
			new Rectangle(70, 5, 120, 30), new Rectangle(200, 5, 80, 30),
			new Rectangle(290, 5, 40, 30), new Rectangle(340, 5, 40, 30),
			new Rectangle(390, 5, 60, 30), new Rectangle(460, 5, 60, 30),
			new Rectangle(530, 5, 60, 30), new Rectangle(600, 5, 60, 30),
			new Rectangle(670, 5, 80, 30) };

	private static final int eachPanelHeight = 40;

	private List<Order> advanceOrder;

	public CheckInPanel() {

		fillContent();
		setLayout(null);
		this.setVisible(true);
	}

	public void updateContent() {

		fillContent();
	}

	private void fillContent() {

		advanceOrder = getAllCheckInOrder();
		int panelHeight = eachPanelHeight * advanceOrder.size();
		buildAllAdvanceOrderView(advanceOrder);
		// 这里要设置宽高才能出现滚动条
		setPreferredSize(new Dimension((int) (width * 0.8f) - 100, panelHeight));
	}

	private void buildAllAdvanceOrderView(List<Order> ao) {

		JPanel headerPanel = createTabelPanel(headerText,
				new Color[headerText.length], rectangle, null, null, null);
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBounds(10, 0, (int) (width * 0.8f) - 68, 40);
		add(headerPanel);
		for (int i = 0; i < ao.size(); i++) {
			User user = getUser(ao.get(i).getUserId());
			Room room = getRoom(ao.get(i).getRoomId());

			JPanel panel = createTabelPanel(
					new Object[] {
							user.getName(),
							user.getIdentify(),
							user.getPhone(),
							user.getSex(),
							room.getRoomNum(),
							new ImageIcon[] {
									new ImageIcon("image/orderCheckIn.png"),
									new ImageIcon("image/checkInOut.png") },
							new ImageIcon[]{
									new ImageIcon("image/addDay.png"),
									new ImageIcon("image/cAddDay.png")
							},
							new ImageIcon[]{
								new ImageIcon("image/checkOutRoom.png"),
								new ImageIcon("image/checkedOutRoom.png")
							},
							new ImageIcon[] {
									new ImageIcon("image/checkInPay.png"),
									new ImageIcon("image/checkInNoPay.png")
							},
							new ImageIcon[] {
								new ImageIcon("image/checkInChangeRoom.png"),
								new ImageIcon("image/checkedInChangeRoom.png")
							}
					},
					cellColor, rectangle, ao.get(i), user, room);
			panel.setBounds(10, (i + 1) * 40, (int) (width * 0.8f), 40);
			add(panel);
		}

	}

	private JPanel createTabelPanel(Object[] o, Color[] color,
			Rectangle[] rectangle, Order order, User user, Room room) {

		JPanel panel = new JPanel();
		panel.setLayout(null);
		for (int i = 0; i < o.length; i++) {
			if (o[i] instanceof String || o[i] instanceof ImageIcon) {
				panel.add(createLabel(o[i], color[i], rectangle[i], order,
						user, room));
			} else if (o[i] instanceof ImageIcon[]) {
				panel.add(createMultiLabel((ImageIcon[]) o[i], rectangle[i],
						order, room, i));
			}
		}
		return panel;
	}

	private JLabel createMultiLabel(ImageIcon[] o, Rectangle ra,
			final Order order, final Room room, int index) {

		JLabel label;
		switch (index) {
		case 5: //入住
			if ("入住".equals(order.getState())) {
				label = new JLabel(o[0], JLabel.CENTER);
			} else {
				label = new JLabel(o[1], JLabel.CENTER);
			}
			break;
		case 6: //续费
			if ("入住".equals(order.getState())) {
				label = new JLabel(o[0], JLabel.CENTER);
				label.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							new AddDayDialogFrame(CheckInPanel.this, order);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			} else {
				label = new JLabel(o[1], JLabel.CENTER);
			}
			break;
		case 7: //退房
			if ("入住".equals(order.getState())) {
				label = new JLabel(o[0], JLabel.CENTER);
				label.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							if(order.isPay()) {
								SqlHelper helper = SqlHelper.getSqlHelper();
								String sql = "update t_order set state = '完结' where id = ?";
								boolean result = helper.executeSql(sql, new Object[]{order.getId()});
								if(result){
									sql = "update t_room set state = '空闲' where id = ?";
									result = helper.executeSql(sql, new Object[]{room.getId()});
									if(result){
										JOptionPane.showMessageDialog(null, "退房成功");
									}
								}
								if(!result){
									JOptionPane.showMessageDialog(null, "退房失败,请稍后");
								}
							}else{
								JOptionPane.showMessageDialog(null, "房间尚未结账,请先进行结账");
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						} finally {
							AWTUtil.updateContent(CheckInPanel.this);
						}
					}
				});
			} else {
				label = new JLabel(o[1], JLabel.CENTER);
			}
			break;
		case 8: //结账
			if (order.isPay()) {
				label = new JLabel(o[0], JLabel.CENTER);
			} else {
				label = new JLabel(o[1], JLabel.CENTER);
				label.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							new PayDialogFrame(CheckInPanel.this, order, room);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}
			break;
		case 9: //更换房间
			if ("入住".equals(order.getState())) {
				label = new JLabel(o[0], JLabel.CENTER);
				label.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							new ChangeRoomDialogFrame(CheckInPanel.this, order, room);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			} else {
				label = new JLabel(o[1], JLabel.CENTER);
			}
			break;
		default:
			label = new JLabel();
			break;
		}

		label.setOpaque(true);
		label.setBounds(ra);
		return label;
	}

	private JLabel createLabel(Object o, Color c, Rectangle ra,
			final Order order, final User user, final Room room) {

		JLabel label;
		if (o instanceof String) {
			label = new JLabel((String) o, JLabel.CENTER);
		} else if (o instanceof ImageIcon) {
			label = new JLabel((ImageIcon) o, JLabel.CENTER);
		} else {
			label = new JLabel();
		}

		label.setOpaque(true);
		label.setBackground(c);
		label.setBounds(ra);
		return label;
	}

	private Room getRoom(String roomId) {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_room where id = ?";
		ResultSet rs = helper.query(sql, new Object[] { roomId });
		return buildRoom(rs);
	}

	private Room buildRoom(ResultSet rs) {

		Room room = new Room();
		try {
			while (rs.next()) {
				room.setId(rs.getString("id"));
				room.setPrice(String.valueOf(rs.getDouble("price")));
				room.setRemark(rs.getString("remark"));
				room.setRoomNum(rs.getString("roomNum"));
				room.setState(rs.getString("state"));
				room.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return room;
	}

	private User getUser(String userId) {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_user where id = ?";
		ResultSet rs = helper.query(sql, new Object[] { userId });
		return buildUser(rs);
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

	private List<Order> getAllCheckInOrder() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_order where state = '入住' "
				+ "or state = '完结' order by state, createTime";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildCheckInOrders(rs);

	}

	private List<Order> buildCheckInOrders(ResultSet rs) {

		List<Order> orders = new ArrayList<Order>();
		try {
			while (rs.next()) {
				Order r = new Order();
				r.setId(rs.getString("id"));
				r.setUserId(rs.getString("userId"));
				r.setRoomId(rs.getString("roomId"));
				r.setMomey(rs.getDouble("momey"));
				r.setPay(rs.getBoolean("isPay"));
				r.setState(rs.getString("state"));
				r.setStartTime(rs.getDate("startTime"));
				r.setEndTime(rs.getDate("endTime"));
				r.setCreateTime(rs.getDate("createTime"));
				orders.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	class ComboBoxListener implements ActionListener {

		private JComboBox<ImageIcon> box;
		private Order order;
		private AdvancePanel panel;

		public ComboBoxListener(JComboBox<ImageIcon> box, Order order,
				AdvancePanel panel) {
			this.box = box;
			this.order = order;
			this.panel = panel;
		}

		public ComboBoxListener(JComboBox<ImageIcon> box2, Order order2,
				CheckInPanel checkInPanel) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = box.getSelectedIndex();
			boolean result = false;
			SqlHelper helper = SqlHelper.getSqlHelper();
			String sql = "update t_order set state = ? where id = ?";

			switch (index) {
			case 0:
				result = helper.executeSql(sql,
						new Object[] { "预定", order.getId() });
				break;
			case 1:
				result = helper.executeSql(sql,
						new Object[] { "入住", order.getId() });
				break;
			case 2:
				result = helper.executeSql(sql,
						new Object[] { "取消", order.getId() });
				break;
			}

			if (result) {
				JOptionPane.showMessageDialog(null, "标记成功");
				AWTUtil.updateContent(panel);
			} else {
				JOptionPane.showMessageDialog(null, "标记失败,请稍后");
			}
		}

	}

}
