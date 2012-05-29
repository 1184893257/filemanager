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
 * 此为前台的主类<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI, ActionListener {

	JPanel panel1=new JPanel();//实现进度条和按钮
	JPanel panel2=new JPanel();//实现柱状图
	JPanel panel3=new JPanel();//实现文件拖动框
	MenuBar mb=new MenuBar();//实现菜单
	JProgressBar jp=new JProgressBar(0,100);
	JButton jb=new JButton("开始检测");
	Menu set=new Menu("设置"); 
	Menu help=new Menu("帮助"); 
	Menu beizhu=new Menu("备注"); 
	
	int Button_flag=0;//用于标记button的状态，为0是没有运行程序，1是正在运行程序
	
	//用于存储的一些变量
	int[][] blocks;
	Queue<String> folders;
	
	protected BackMain back;
	
	/**
	 * 在我这里这个木有用处，不用管了
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *构造函数 
	 */
	public InterMain(){
		//菜单	
		mb.add(set);
		mb.add(help);
		mb.add(beizhu);
		//时间监听器
		set.addActionListener(this);
		help.addActionListener(this);
		beizhu.addActionListener(this);
		/*
		 * 这里应该实现菜单的点击事件
		 * 尤其是设置操作，要返回int[][] blocks;
		 */
		//进度条和按钮
		panel1.add(jp);
		panel1.add(jb);
		jb.addActionListener(this);
		panel1.setSize(300,600);
		//直方图
		//这是一个开始图片，使开始的界面美观一点
		ImageIcon img = new ImageIcon("C:\\Users\\yang\\Desktop\\其他\\2008-0828v14841C.jpg");
		JLabel jl=new JLabel(img);
		panel2.add(jl);
		panel2.setSize(600, 400);
		//文件拖选框
		/*
		 * 这里要是先文件拖选框，主要是实现Queue<String> folders的设置
		 */
		//这个先空着就是在panel3里面加东西
		setLayout(new BorderLayout());
		this.setMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		this.setSize(900, 600);
		this.setTitle("文件优化管理系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(Button_flag==0)//开始按钮
		{
			back.startCal(blocks, folders);
			jb.setText("停止");
			Button_flag=1;
		}
		else			 //停止按钮
		{
			back.stopCal();
			jb.setText("开始检测");
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
		JFreeChart chart = ChartFactory.createBarChart3D("统计结果", 				  //表名字
														"区间号", 				  //横坐标名称
														"文件数", 				  //纵坐标名称
														dataset,				  //数据集
														PlotOrientation.VERTICAL, //水平的
														true, 					  //显示例图
														false, 					  //不生成工具
														false);					  //不生成URL连接
		CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
        CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        panel2=new ChartPanel(chart,true);
	}
	
}
