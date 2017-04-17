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
 * jfreechart������
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
		// �������񱳾���ɫ
		plot.setBackgroundPaint(Color.white);
		// ��������������ɫ
		plot.setDomainGridlinePaint(Color.pink);
		// �������������ɫ
		plot.setRangeGridlinePaint(Color.pink);
		
		CategoryAxis domainAxis = plot.getDomainAxis();//(��״ͼ��x��)
		domainAxis.setTickLabelFont(font);//����x�������ϵ�����    
		domainAxis.setLabelFont(font);//����x���ϵı��������    
		ValueAxis valueAxis = plot.getRangeAxis();//(��״ͼ��y��)    
		valueAxis.setTickLabelFont(font);//����y�������ϵ�����    
		valueAxis.setLabelFont(font);//����y�������ϵı��������
		
		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// Ĭ�ϵ�������ʾ�������У�ͨ����������ɵ������ֵ���ʾ
		// ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ����������
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
