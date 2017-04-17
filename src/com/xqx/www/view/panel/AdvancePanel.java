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
import java.util.Date;
import java.util.List;
import java.util.Vector;

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
import com.xqx.www.util.DateUtil;
import com.xqx.www.view.dialog.OrderDialogFrame;

public class AdvancePanel extends CustomPanel {

	private static final long serialVersionUID = 1L;
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;

	private final static String[] headerText = {"姓名", "身份证号", 
		"联系电话", "性别", "房号", "订单创建时间", "标记", "查看详情"};
	
	private final static Color[] cellColor = {Color.WHITE, Color.CYAN,
		Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN,
		Color.WHITE, Color.CYAN};
	
	private final static Rectangle[] rectangle = {
		new Rectangle(0, 5, 60, 30),
		new Rectangle(70, 5, 120, 30),
		new Rectangle(200, 5, 80, 30),
		new Rectangle(290, 5, 30, 30),
		new Rectangle(330, 5, 30, 30),
		new Rectangle(370, 5, 120, 30),
		new Rectangle(500, 5, 120, 30),
		new Rectangle(630, 5, 120, 30)
	};
	
	private static final int eachPanelHeight = 40;

	private List<Order> advanceOrder;

	public AdvancePanel() {

		fillContent();
		setLayout(null);
		this.setVisible(true);
	}

	public void updateContent() {

		fillContent();
	}

	private void fillContent() {

		advanceOrder = getAllAdvanceOrder();
		int panelHeight = eachPanelHeight * advanceOrder.size();
		buildAllAdvanceOrderView(advanceOrder);
		// 这里要设置宽高才能出现滚动条
		setPreferredSize(new Dimension((int) (width * 0.8f) - 100,
				panelHeight));
	}

	private void buildAllAdvanceOrderView(List<Order> ao) {

		JPanel headerPanel = createTabelPanel(headerText, 
				new Color[8], rectangle, null, null, null);
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBounds(10, 0, (int) (width * 0.8f) - 68, 40);
		add(headerPanel);
		for (int i = 0; i < ao.size(); i++) {
			User user = getUser(ao.get(i).getUserId());
			Room room = getRoom(ao.get(i).getRoomId());
			
			JPanel panel = createTabelPanel(new Object[]{
					user.getName(),
					user.getIdentify(),
					user.getPhone(),
					user.getSex(),
					room.getRoomNum(),
					DateUtil.getLocalTime(ao.get(i)
							.getCreateTime()),
					new ImageIcon[]{
						new ImageIcon("image/orderAdvance.png"),
						new ImageIcon("image/orderCheckIn.png"),
						new ImageIcon("image/orderCancel.png")
					},
					new ImageIcon("image/orderDetail.png")
			}, cellColor, rectangle, ao.get(i), user, room);
			panel.setBounds(10, (i + 1) * 40, (int) (width * 0.8f), 40);
			add(panel);
		}

	}

	private JPanel createTabelPanel(Object[] o, Color[] color, 
			Rectangle[] rectangle, Order order, User user, Room room) {
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		for(int i = 0; i < o.length; i++) {
			if(o[i] instanceof String || o[i] instanceof ImageIcon) {
				panel.add(createLabel(o[i], color[i], rectangle[i], 
						order, user, room));
			}else if(o[i] instanceof ImageIcon[]) {
				panel.add(createComboBox(o[i], rectangle[i], order));
			}
		}
		return panel;
	}
	
	private JComboBox<ImageIcon> createComboBox(Object o, Rectangle ra,
			Order order) {
		
		Vector<ImageIcon> v = new Vector<ImageIcon>();
		for(ImageIcon icon : (ImageIcon[])o){
			v.add(icon);
		}
		JComboBox<ImageIcon> box = new JComboBox<ImageIcon>(v);
		box.setBounds(ra);
		box.addActionListener(new ComboBoxListener(box, order, this));
		return box;
	}
	
	private JLabel createLabel(Object o, Color c, 
			Rectangle ra, final Order order, final User user, 
			final Room room) {
		
		JLabel label;
		if(o instanceof String) {
			label = new JLabel((String)o, JLabel.CENTER);
		}else if (o instanceof ImageIcon) {
			label = new JLabel((ImageIcon)o, JLabel.CENTER);
			label.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						new OrderDialogFrame(user, order, room);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}else{
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

	private List<Order> getAllAdvanceOrder() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_order where state = '预定' order by createTime";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildAdvanceOrders(rs);

	}

	private List<Order> buildAdvanceOrders(ResultSet rs) {

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

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = box.getSelectedIndex();
			boolean result = false;
			SqlHelper helper = SqlHelper.getSqlHelper();
			
			switch(index) {
				case 0:
					String aSql = "update t_order set state = ?, createTime = ? where id = ?";
					result = helper.executeSql(aSql, new Object[]{"预定", new Date(), order.getId()});
					break;
				case 1:
					String iSql = "update t_order set state = ?, startTime = ? where id = ?";
					result = helper.executeSql(iSql, new Object[]{"入住", new Date(), order.getId()});
					break;
				case 2:
					String cSql = "update t_order set state = ? where id = ?";
					result = helper.executeSql(cSql, new Object[]{"取消", order.getId()});
					break;
			}
			
			if(result) {
				JOptionPane.showMessageDialog(null, "标记成功");
				AWTUtil.updateContent(panel);
			}else{
				JOptionPane.showMessageDialog(null, "标记失败,请稍后");
			}
		}
		
	}

}
