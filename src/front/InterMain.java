package front;

import back.*;

import inter.Folder;
import inter.FrontGUI;

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

	JPanel panel1 = new JPanel();// 实现进度条和按钮
	JPanel panel2 = new JPanel();// 实现柱状图
	FolderList panel3 = new FolderList();// 实现文件拖动框
	JMenuBar mb = new JMenuBar();// 实现菜单
	JProgressBar jp = new JProgressBar(0, 100);
	JButton jb = new JButton("开始检测");
	
	JButton jb_commit=new JButton("确定");//设置操作界面中用到的button，确定
	JButton jb_cancel=new JButton("取消");//设置操作界面中用到的button，取消
	JTextField[][] mytext;               //设置操作界面中用到
	JFrame JF_second;                    //设置操作界面中用到
	JFrame JF_third;					 //帮助的界面
	JFrame JF_fourth;					 //备注的界面
	
	JMenu operate = new JMenu("操作");
	JMenu other = new JMenu("其他");
	JMenuItem set=new JMenuItem("设置");
	JMenuItem help=new JMenuItem("帮助");
	JMenuItem beizhu=new JMenuItem("备注");

	int Button_flag = 0;// 用于标记button的状态，为0是没有运行程序，1是正在运行程序

	// 用于存储的一些变量
	int[][] blocks;
	Queue<String> folders;

	protected BackMain back;

	/**
	 * 在我这里这个木有用处，不用管了
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public InterMain() {
		// 菜单
		operate.add(set);
		other.add(help);
		other.add(beizhu);
		mb.add(operate);
		mb.add(other);
		// 时间监听器
		set.setActionCommand("set");
		help.setActionCommand("help");
		beizhu.setActionCommand("beizhu");
		set.addActionListener(this);
		help.addActionListener(this);
		beizhu.addActionListener(this);
		int[][] blocks={{0,5},{6,10},{11,15},{16,20},{21,1000}};
		// 进度条和按钮
		jb.setActionCommand("Button");
		panel1.add(jp);
		panel1.add(jb);
		jb.addActionListener(this);
		//panel1.setPreferredSize(new Dimension(300, 600));
		// 直方图
		// 这是一个开始图片，使开始的界面美观一点
		ImageIcon img = new ImageIcon(
				"C:\\Users\\yang\\Desktop\\其他\\2008-0828v14841C.jpg");
		JLabel jl = new JLabel(img);
		panel2.add(jl);
		//panel2.setPreferredSize(new Dimension(600, 400));
		// 文件拖选框
		/*
		 * 这里要是先文件拖选框，主要是实现Queue<String> folders的设置
		 */
		// 这个先空着就是在panel3里面加东西
		setLayout(new BorderLayout());
		this.setJMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		//this.setPreferredSize(new Dimension(900, 600));
		this.setTitle("文件优化管理系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Button"))
		{
			if (Button_flag == 0)// 开始按钮
			{
				back.startCal(blocks, folders);
				jb.setText("停止");
				Button_flag = 1;
			} 
			else // 停止按钮
			{
				back.stopCal();
				jb.setText("开始检测");
				Button_flag = 0;
			}
		}
		else if(e.getActionCommand().equals("set"))
		{
			/*
			 * 这里弹出JtextFild
			 */
			mytext=new JTextField[5][2];
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<2;j++)
					mytext[i][j]=new JTextField();
			}
			
			JF_second=new JFrame();//重新创建一个Frame
			JF_second.setLayout(new GridLayout(6,3));
			JF_second.add(new Label("第一个区间"));
			JF_second.add(mytext[0][0]);
			JF_second.add(mytext[0][1]);
			JF_second.add(new Label("第二个区间"));
			JF_second.add(mytext[1][0]);
			JF_second.add(mytext[1][1]);
			JF_second.add(new Label("第三个区间"));
			JF_second.add(mytext[2][0]);
			JF_second.add(mytext[2][1]);
			JF_second.add(new Label("第四个区间"));
			JF_second.add(mytext[3][0]);
			JF_second.add(mytext[3][1]);
			JF_second.add(new Label("第五个区间"));
			JF_second.add(mytext[4][0]);
			JF_second.add(mytext[4][1]);
			JF_second.add(jb_commit);
			jb_commit.setActionCommand("commit");
			jb_commit.addActionListener(this);
			JF_second.add(jb_cancel);
			jb_cancel.setActionCommand("cancel");
			jb_cancel.addActionListener(this);
			
			JF_second.setTitle("请输入文件数的区间");
			JF_second.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_second.pack();
			JF_second.setVisible(true);
		}
		else if(e.getActionCommand().equals("help"))
		{
			/*
			 * 这里弹出帮助栏
			 */
			JF_third=new JFrame();
			JF_third.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_third.pack();
			JF_third.setVisible(true);
			
		}
		else if(e.getActionCommand().endsWith("beizhu"))
		{
			/*
			 * 这里弹出备注
			 */
			JF_fourth=new JFrame();
			JLabel mylabel=new JLabel("author:" +"/n"+"杨延中"+"刘乔羽"+"/n"+"2012.6.1");
			JF_fourth.add(mylabel);
			JF_fourth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_fourth.pack();
			JF_fourth.setVisible(true);
		}
		else if(e.getActionCommand().equals("commit"))
		{
			//blocks=new int[5][2];
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<2;j++)
				{
					blocks[i][j]=Integer.parseInt(mytext[i][j].getText());
				}
			}
			JF_second.dispose();
		}
		else if(e.getActionCommand().equals("cancel"))
		{
			JF_second.dispose();
		}
	}

	@Override
	public void showStage(double finished) {
		// TODO Auto-generated method stub
		int temp = (int) (finished * 100);
		jp.setValue(temp);
	}

	@Override
	public void complete(int[] heights, ArrayList<LinkedList<Folder>> lists) {
		// TODO Auto-generated method stub
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 1; i <= 5; i++) {
			dataset.addValue(heights[i - 1], "", (new Integer(i)).toString());
		}
		JFreeChart chart = ChartFactory.createBarChart3D("统计结果", // 表名字
				"区间号", // 横坐标名称
				"文件数", // 纵坐标名称
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 水平的
				true, // 显示例图
				false, // 不生成工具
				false); // 不生成URL连接
		CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
		panel2 = new ChartPanel(chart, true);
	}

}
