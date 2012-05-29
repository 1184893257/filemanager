package inter;
import back.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * ��Ϊǰ̨������<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI, ActionListener {

	JPanel panel1=new JPanel();//ʵ�ֽ������Ͱ�ť
	JPanel panel2=new JPanel();//ʵ����״ͼ
	JPanel panel3=new JPanel();//ʵ���ļ��϶���
	MenuBar mb=new MenuBar();//ʵ�ֲ˵�
	JProgressBar jp=new JProgressBar(0,100);
	JButton jb=new JButton("��ʼ���");
	Menu set=new Menu("����"); 
	Menu help=new Menu("����"); 
	Menu beizhu=new Menu("��ע"); 
	
	int Button_flag=0;//���ڱ��button��״̬��Ϊ0��û�����г���1���������г���
	
	//���ڴ洢��һЩ����
	int[][] blocks;
	Queue<String> folders;
	
	protected BackMain back;
	
	/**
	 * �����������ľ���ô������ù���
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *���캯�� 
	 */
	public InterMain(){
		//�˵�	
		mb.add(set);
		mb.add(help);
		mb.add(beizhu);
		//ʱ�������
		set.addActionListener(this);
		help.addActionListener(this);
		beizhu.addActionListener(this);
		/*
		 * ����Ӧ��ʵ�ֲ˵��ĵ���¼�
		 * ���������ò�����Ҫ����int[][] blocks;
		 */
		//�������Ͱ�ť
		panel1.add(jp);
		panel1.add(jb);
		jb.addActionListener(this);
		panel1.setSize(300,600);
		//ֱ��ͼ
		//����һ����ʼͼƬ��ʹ��ʼ�Ľ�������һ��
		ImageIcon img = new ImageIcon("C:\\Users\\yang\\Desktop\\����\\2008-0828v14841C.jpg");
		JLabel jl=new JLabel(img);
		panel2.add(jl);
		panel2.setSize(600, 400);
		//�ļ���ѡ��
		/*
		 * ����Ҫ�����ļ���ѡ����Ҫ��ʵ��Queue<String> folders������
		 */
		//����ȿ��ž�����panel3����Ӷ���
		setLayout(new BorderLayout());
		this.setMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		this.setSize(900, 600);
		this.setTitle("�ļ��Ż�����ϵͳ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(Button_flag==0)//��ʼ��ť
		{
			back.startCal(blocks, folders);
			jb.setText("ֹͣ");
			Button_flag=1;
		}
		else			 //ֹͣ��ť
		{
			back.stopCal();
			jb.setText("��ʼ���");
			Button_flag=0;
		}
	}


	@Override
	public void showStage(double finished) {
		// TODO Auto-generated method stub
		int temp=(int)(finished*100);
		jp.setValue(temp);
	}

	@Override
	public void complete(int[] heights, ArrayList<LinkedList<Folder>> lists) {
		// TODO Auto-generated method stub
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=1;i<=5;i++)
		{
			dataset.addValue(heights[i-1], "", (new Integer(i)).toString());
		}
		JFreeChart chart = ChartFactory.createBarChart3D("ͳ�ƽ��", 				  //������
														"�����", 				  //����������
														"�ļ���", 				  //����������
														dataset,				  //���ݼ�
														PlotOrientation.VERTICAL, //ˮƽ��
														true, 					  //��ʾ��ͼ
														false, 					  //�����ɹ���
														false);					  //������URL����
		CategoryPlot plot=chart.getCategoryPlot();//��ȡͼ���������
        CategoryAxis domainAxis=plot.getDomainAxis();         //ˮƽ�ײ��б�
        domainAxis.setLabelFont(new Font("����",Font.BOLD,14));         //ˮƽ�ײ�����
        domainAxis.setTickLabelFont(new Font("����",Font.BOLD,12));  //��ֱ����
        ValueAxis rangeAxis=plot.getRangeAxis();//��ȡ��״
        rangeAxis.setLabelFont(new Font("����",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("����",Font.BOLD,20));//���ñ�������
        panel2=new ChartPanel(chart,true);
	}
	
}
