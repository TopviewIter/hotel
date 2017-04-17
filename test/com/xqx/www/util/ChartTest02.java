package com.xqx.www.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

public class ChartTest02 extends JFrame{

	private static final long serialVersionUID = 1L;

	public ChartTest02() throws IOException {
		 
		add(chart());
		setSize(500, 500);
		setVisible(true);
	}
	
	public ChartPanel chart() throws IOException {
		double[][] data = new double[][] { 
				{ 1310, 1220, 1110, 1000 },
				{ 720, 700, 680, 640 }, 
				{ 1130, 1020, 980, 800 },
				{ 440, 400, 360, 300 } };
		Font font = new Font("SimSun", 10, 20);
		
		String[] rowKeys = { "猪肉", "牛肉", "鸡肉", "鱼肉" };
		String[] columnKeys = { "广州", "深圳", "东莞", "佛山" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		JFreeChart chart = ChartFactory.createBarChart3D("肉类销量统计图", "肉类", "销量",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		chart.getTitle().setFont(font);
		 
		CategoryPlot plot = chart.getCategoryPlot();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		
		CategoryAxis domainAxis = plot.getDomainAxis();//(柱状图的x轴)
		domainAxis.setTickLabelFont(font);//设置x轴坐标上的字体    
		domainAxis.setLabelFont(font);//设置x轴上的标题的字体    
		ValueAxis valueAxis = plot.getRangeAxis();//(柱状图的y轴)    
		valueAxis.setTickLabelFont(font);//设置y轴坐标上的字体    
		valueAxis.setLabelFont(font);//设置y轴坐标上的标题的字体
		
		// 显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		// 设置每个地区所包含的平行柱的之间距离
		// renderer.setItemMargin(0.3);
		plot.setRenderer(renderer);
		// 设置地区、销量的显示位置
		// 将下方的“肉类”放到上方
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// 将默认放在左边的“销量”放到右方
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		
		chart.getLegend().setItemFont(font);
		
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setOpaque(false);
		chartpanel.setPreferredSize(new Dimension(1000, 1000));
		return chartpanel;

	}
	
	public static void main(String[] args) throws IOException {
		
		new ChartTest02();
	}
}
