package com.xqx.www.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.xqx.www.component.CustomPanel;
import com.xqx.www.db.SqlHelper;
import com.xqx.www.po.User;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.view.dialog.UserDialogFrame;

/**
 * 用户管理界面
 * @author xqx
 *
 */
public class VipPanel extends CustomPanel {

	private static final long serialVersionUID = 1L;

	private final static String[] colsText = {"身份证", "名称", "性别",
		"号码", "密码", "等级", "是否为vip"};
	
	private JTable table;
	private JPanel panel;
	private JButton insert, update, delete;
	
	private List<User> allUser;

	public VipPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		initTable();
		initBtn();
		this.setVisible(true);
	}
	
	private void initTable() {
		
		//用户表格部分开始
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			//让表格可选中但不能编辑
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		DefaultTableCellRenderer chr = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
		chr.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, cr);
		table.setBorder(BorderFactory.createBevelBorder(HEIGHT));
		buildCols(model);
		allUser = getAllUser();
		buildRows(model, allUser);
		//JTable要放到JScrollPane里面才显示列名
		add(new JScrollPane(table), BorderLayout.CENTER);
		//用户表格部分结束
	}
	
	private void initBtn() {
		
		//表格操作部分开始
		panel = new JPanel();
		insert = new JButton("添加用户");
		insert.addActionListener(new BtnListener(this));
		panel.add(insert);
		update = new JButton("修改用户");
		update.addActionListener(new BtnListener(this));
		panel.add(update);
		delete = new JButton("删除用户");
		delete.addActionListener(new BtnListener(this));
		panel.add(delete);
		add(panel, BorderLayout.SOUTH);
		//表格操作部分结束
	}

	private void buildCols(DefaultTableModel model) {

		for(String t : colsText) {
			model.addColumn(t);
		}
	}
	
	private void buildRows(DefaultTableModel model, List<User> users) {
		
		Object[] row = new Object[colsText.length];
		for(User u : users) {
			row[0] = u.getIdentify();
			row[1] = u.getName();
			row[2] = u.getSex();
			row[3] = u.getPhone();
			row[4] = u.getPassword();
			row[5] = u.getRank();
			row[6] = u.isVip();
			model.addRow(row);
		}
	}

	private List<User> getAllUser() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_user order by id";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildUsers(rs);
	}

	private List<User> buildUsers(ResultSet rs) {

		List<User> users = new ArrayList<User>();
		try {
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setIdentify(rs.getString("identify"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setSex(rs.getString("sex"));
				user.setVip(rs.getBoolean("isVip"));
				user.setRank(rs.getString("rank"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	class BtnListener implements ActionListener {
		
		private VipPanel panel;
		
		public BtnListener(VipPanel panel) {
			this.panel = panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == insert) {
				try {
					new UserDialogFrame(panel, new User());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == update) {
				int index = table.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "请选中要修改的行");
					return;
				}
				User user = allUser.get(index);
				try {
					new UserDialogFrame(panel, user);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == delete) {
				int index = table.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "请选中要删除的行");
					return;
				}
				User user = allUser.get(index);
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "delete from t_user where id = ?";
				boolean result = helper.executeSql(sql, new Object[] {
						user.getId()
				});
				if(result) {
					JOptionPane.showMessageDialog(null, "删除成功");
					AWTUtil.updateContent(panel);
				}else{
					JOptionPane.showMessageDialog(null, "删除失败，请稍后重试");
				}
			}
		}
	}

	@Override
	public void updateContent() {
		
		initTable();
		initBtn();
	}
}
