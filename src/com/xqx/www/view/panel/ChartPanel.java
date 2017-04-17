package com.xqx.www.view.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.xqx.www.db.SqlHelper;
import com.xqx.www.util.ChartUtil;

public class ChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// 用于获得窗口的大小
	private final static int width = Toolkit.getDefaultToolkit()
			.getScreenSize().width;
	private final static int height = Toolkit.getDefaultToolkit()
			.getScreenSize().height;

	private static final String orderChartTitle = "订单统计图";
	private static final String orderXLabel = "";
	private static final String orderYLabel = "订单数目";
	private static final String[] orderColumnKeys = { "01", "02", "03", "04",
			"05", "06", "07", "08", "09", "10", "11", "12" };
	private static final String[] orderRowKeys = { "完结", "预定", "入住", "取消" };

	private static final String turnOverChartTitle = "收入统计图";
	private static final String turnOverXLabel = "";
	private static final String turnOverYLabel = "收入金额";
	private static final String[] turnOverColumnKeys = { "01", "02", "03",
			"04", "05", "06", "07", "08", "09", "10", "11", "12" };
	private static final String[] turnOverRowKeys = { "收入" };

	private JPanel cardPanel;
	private CardLayout card;

	public ChartPanel() {
		setLayout(new BorderLayout());
		buildCardPanel();
		buildOpsPanel();
		this.setVisible(true);
	}

	public void buildCardPanel() {

		cardPanel = new JPanel();
		card = new CardLayout();
		cardPanel.setLayout(card);
		cardPanel.add(buildOrderChart(), "order");
		add(cardPanel);
	}

	public org.jfree.chart.ChartPanel buildOrderChart() {

		return ChartUtil.buildLineChart(orderChartTitle, orderXLabel,
				orderYLabel, getOrderData(), orderRowKeys, orderColumnKeys,
				(int) (width * 0.8f) - 48, (int) (height * 0.8f) - 200);
	}

	public org.jfree.chart.ChartPanel buildTurnOverChart() {

		return ChartUtil.buildLineChart(turnOverChartTitle, turnOverXLabel,
				turnOverYLabel, getTurnOverData(), turnOverRowKeys,
				turnOverColumnKeys, (int) (width * 0.8f) - 48,
				(int) (height * 0.8f) - 200);
	}

	private double[][] getTurnOverData() {
		double[][] result = new double[1][12];
		SqlHelper helper = SqlHelper.getSqlHelper();
		// 获取各个月的某个状态数据
		String sql = "select sum(momey) record_, DATENAME(month, createTime) month from t_order where "
				+ "state = '完结' group by DATENAME(month, createTime)";
		ResultSet rs = helper.query(sql, new Object[]{});
		try {
			while (rs.next()) {
				result[0][rs.getInt("month") - 1] = rs.getInt("record_");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void buildOpsPanel() {

		// 表格操作部分开始
		JPanel panel = new JPanel();
		JButton order = new JButton("订单统计");
		panel.add(order);
		order.addActionListener(new BtnListener("order"));
		JButton turnOver = new JButton("营业额统计");
		panel.add(turnOver);
		turnOver.addActionListener(new BtnListener("turnOver"));
		add(panel, BorderLayout.SOUTH);
		// 表格操作部分结束
	}

	private double[][] getOrderData() {

		double[][] result = new double[4][12];
		SqlHelper helper = SqlHelper.getSqlHelper();
		// 获取各个月的某个状态数据
		for (int i = 0; i < orderRowKeys.length; i++) {
			String sql = "select count(*) record_, DATENAME(month, createTime) month from t_order where "
					+ "state = ? group by DATENAME(month, createTime)";
			ResultSet rs = helper.query(sql, new Object[] { orderRowKeys[i] });
			try {
				while (rs.next()) {
					result[i][rs.getInt("month") - 1] = rs.getInt("record_");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	class BtnListener implements ActionListener {

		private String text;

		public BtnListener(String text) {
			this.text = text;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (text) {
			case "order":
				cardPanel.add(buildOrderChart(), "order");
				card.show(cardPanel, "order");
				break;
			case "turnOver":
				cardPanel.add(buildTurnOverChart(), "turnOver");
				card.show(cardPanel, "turnOver");
				break;
			}
		}

	}
}
