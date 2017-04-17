package com.xqx.www.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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

/**
 * jfreechart工具类
 * @author xqx
 *
 */
public class ChartUtil {

	public static ChartPanel buildLineChart(String title, 
			String xLabel, String yLabel, double[][] data,
			String[] rowKeys, String[] columnKeys, 
			int width, int height) {
		
		Font font = new Font("SimSun", 10, 20);
		
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		JFreeChart chart = ChartFactory.createBarChart3D(title, xLabel, yLabel,
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
		plot.setRenderer(renderer);
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		
		chart.getLegend().setItemFont(font);
		
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setOpaque(false);
		chartpanel.setPreferredSize(new Dimension(width, height));
		return chartpanel;
	}
	
}
