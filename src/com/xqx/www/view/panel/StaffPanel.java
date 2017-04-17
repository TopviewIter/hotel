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
import com.xqx.www.po.Staff;
import com.xqx.www.util.AWTUtil;
import com.xqx.www.view.dialog.StaffDialogFrame;

/**
 * Ա���������
 * @author xqx
 *
 */
public class StaffPanel extends CustomPanel {

	private static final long serialVersionUID = 1L;

	private final static String[] colsText = {"���֤", "����", "�Ա�",
		"����", "����", "����", "ְ��"};
	
	private JTable table;
	private JPanel panel;
	private JButton insert, update, delete, salary;
	
	private List<Staff> allStaff;

	public StaffPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		initTable();
		initBtn();
		this.setVisible(true);
	}
	
	private void initTable() {
		
		//�û���񲿷ֿ�ʼ
		DefaultTableModel model = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			//�ñ���ѡ�е����ܱ༭
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
		allStaff = getAllStaff();
		buildRows(model, allStaff);
		//JTableҪ�ŵ�JScrollPane�������ʾ����
		add(new JScrollPane(table), BorderLayout.CENTER);
		//�û���񲿷ֽ���
	}
	
	private void initBtn() {
		
		//���������ֿ�ʼ
		panel = new JPanel();
		insert = new JButton("����û�");
		insert.addActionListener(new BtnListener(this));
		panel.add(insert);
		update = new JButton("�޸��û�");
		update.addActionListener(new BtnListener(this));
		panel.add(update);
		delete = new JButton("ɾ���û�");
		delete.addActionListener(new BtnListener(this));
		panel.add(delete);
		salary = new JButton("������н");
		salary.addActionListener(new BtnListener(this));
		panel.add(salary);
		add(panel, BorderLayout.SOUTH);
		//���������ֽ���
	}

	private void buildCols(DefaultTableModel model) {

		for(String t : colsText) {
			model.addColumn(t);
		}
	}
	
	private void buildRows(DefaultTableModel model, List<Staff> staffs) {
		
		Object[] row = new Object[colsText.length];
		for(Staff s : staffs) {
			row[0] = s.getIdentify();
			row[1] = s.getName();
			row[2] = s.getSex();
			row[3] = s.getPhone();
			row[4] = s.getPassword();
			row[5] = s.getSalary();
			row[6] = s.getJob();
			model.addRow(row);
		}
	}

	private List<Staff> getAllStaff() {

		SqlHelper helper = SqlHelper.getSqlHelper();
		String sql = "select * from t_staff order by id";
		ResultSet rs = helper.query(sql, new Object[] {});
		return buildStaffs(rs);
	}

	private List<Staff> buildStaffs(ResultSet rs) {

		List<Staff> staffs = new ArrayList<Staff>();
		try {
			while (rs.next()) {
				Staff staff = new Staff();
				staff.setId(rs.getString("id"));
				staff.setIdentify(rs.getString("identify"));
				staff.setName(rs.getString("name"));
				staff.setPassword(rs.getString("password"));
				staff.setPhone(rs.getString("phone"));
				staff.setSex(rs.getString("sex"));
				staff.setSalary(rs.getString("salary"));
				staff.setJob(rs.getString("job"));
				staffs.add(staff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return staffs;
	}

	class BtnListener implements ActionListener {
		
		private StaffPanel panel;
		
		public BtnListener(StaffPanel panel) {
			this.panel = panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == insert) {
				try {
					new StaffDialogFrame(panel, new Staff());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == update) {
				int index = table.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵ���");
					return;
				}
				Staff staff = allStaff.get(index);
				try {
					new StaffDialogFrame(panel, staff);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == delete) {
				int index = table.getSelectedRow();
				if(index == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ������");
					return;
				}
				Staff staff = allStaff.get(index);
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "delete from t_staff where id = ?";
				boolean result = helper.executeSql(sql, new Object[] {
						staff.getId()
				});
				if(result) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					AWTUtil.updateContent(panel);
				}else{
					JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ����Ժ�����");
				}
			}else if(e.getSource() == salary) {
				
				String money = JOptionPane.showInputDialog(null, "�������н���");
				SqlHelper helper = SqlHelper.getSqlHelper();
				String sql = "{call proc_staff_salary(?)}";
				helper.executeProcess(sql, new Object[] {
						Integer.valueOf(money)
				}, -1, -1);
				JOptionPane.showMessageDialog(null, "��н�ɹ�");
				AWTUtil.updateContent(panel);
				
			}
		}
	}

	@Override
	public void updateContent() {
		
		initTable();
		initBtn();
	}
}
