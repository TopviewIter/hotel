package com.xqx.www.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.xqx.www.component.CustomPanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.Room;
import com.xqx.www.view.dialog.RoomDialogFrame;

public class RoomPanel extends CustomPanel{

	private static final long serialVersionUID = 1L;
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private final static int height = Toolkit.getDefaultToolkit()
			.getScreenSize().height;
	
	private static final int panelCellWidth = 117;
	private static final int panelCellHeight = 117;
	private static final int offset = 10;
	private static final int scrollOffset = 6;
	
	private List<Room> rooms;
	
	public RoomPanel() {
		
		fillContent();
		setLayout(null);
		this.setVisible(true);
	}
	
	public void updateContent() {
		
		fillContent();
	}
	
	private void fillContent() {
		
		rooms = getAllRoom();
		int wHeight = (int) (height * 0.8f) - 200;
		int panelHeight = (panelCellHeight + offset) * ((rooms.size() + 6 - 1) / 6);
		boolean isScroll = panelHeight > wHeight ? true : false;
		buildAllRoomView(rooms, isScroll);
		//这里要设置宽高才能出现滚动条
		setPreferredSize(new Dimension((int) (width * 0.8f) - 100,
				panelHeight));
	}
	
	private void buildAllRoomView(List<Room> rooms, boolean isScroll) {
		
		int cols = 6;
		for(int i = 0; i < rooms.size(); i++) {
			JLabel label;
			if(i == rooms.size() - 1) {
				label = new JLabel(new ImageIcon("image/newRoom.png"));
			}else{
				label = new JLabel(buildRoomLabel(rooms.get(i)), JLabel.CENTER);
			}
			label.addMouseListener(new LabelListener(this, rooms.get(i)));
			label.setOpaque(true);
			if(isScroll){
				label.setBounds((i % cols) * (panelCellWidth + scrollOffset) + 10, 
						(i / cols) * (panelCellHeight + scrollOffset) + 10, 
						panelCellWidth, panelCellHeight);
			}else{
				label.setBounds((i % cols) * (panelCellWidth + offset) + 10, 
						(i / cols) * (panelCellHeight + offset) + 10, 
						panelCellWidth, panelCellHeight);
			}
			
			if("空闲".equals(rooms.get(i).getState())) {
				label.setForeground(Color.RED);
				label.setBackground(Color.GREEN);
			}else if("预定".equals(rooms.get(i).getState())) {
				label.setForeground(Color.BLACK);
				label.setBackground(Color.GRAY);
			}else if("入住".equals(rooms.get(i).getState())) {
				label.setForeground(Color.WHITE);
				label.setBackground(Color.RED);
			}
			add(label);
		}
	}
	
	private List<Room> getAllRoom(){
		
		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_room order by roomNum";
		ResultSet rs = helper.query(sql, new Object[]{});
		return buildRooms(rs);
		
	}
	
	private List<Room> buildRooms(ResultSet rs) {
		
		List<Room> rooms = new ArrayList<Room>();
		try {
			while(rs.next()) {
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
		// 添加多一个room用于为添加客房的label占位
		rooms.add(new Room());
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

		private Room room;
		private RoomPanel panel;
		
		public LabelListener(RoomPanel panel, Room room) {
			this.panel = panel;
			this.room = room;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				new RoomDialogFrame(panel, room);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

}
