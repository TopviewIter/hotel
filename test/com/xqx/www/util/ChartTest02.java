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
		
		String[] rowKeys = { "����", "ţ��", "����", "����" };
		String[] columnKeys = { "����", "����", "��ݸ", "��ɽ" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		JFreeChart chart = ChartFactory.createBarChart3D("��������ͳ��ͼ", "����", "����",
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
		// ����ÿ��������������ƽ������֮�����
		// renderer.setItemMargin(0.3);
		plot.setRenderer(renderer);
		// ���õ�������������ʾλ��
		// ���·��ġ����ࡱ�ŵ��Ϸ�
		plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
		// ��Ĭ�Ϸ�����ߵġ��������ŵ��ҷ�
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
