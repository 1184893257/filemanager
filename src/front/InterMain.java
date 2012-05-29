package front;

import back.*;

import inter.BackRunner;
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
	JPanel panel3 = new JPanel();// 实现文件拖动框
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

	// 用于存储的一些变量
	int[][] blocks;
	Queue<String> folders;
	
	/**
	 * 后台统计完毕返回的各个区间 文件夹-子文件数 结果
	 */
	protected ArrayList<LinkedList<Folder>> result;
	/**
	 * 顶层文件夹列表
	 */
	protected FolderList fList;
	/**
	 * 弹出各区间详细信息
	 */
	protected JButton show;
	/**
	 * 后台<br>
	 * 前台使用后台接口就够了
	 */
	protected BackRunner back;

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
		blocks=new int[][]{{0,5},{6,10},{11,15},{16,20},{21,1000}};
		// 进度条和按钮
		panel1.add(jp);
		jp.setValue(0);
		jp.setPreferredSize(new Dimension(400,50));
		panel1.add(jb);
		jb.addActionListener(this);
		panel1.setPreferredSize(new Dimension(910, 70));
		//panel1.setBackground(Color.getHSBColor(177, 235, 240));
		// 直方图
		// 这是一个开始图片，使开始的界面美观一点
		ImageIcon img = new ImageIcon(
				"img\\杨.jpg");
		JLabel jl = new JLabel(img);
		panel2.setLayout(new BorderLayout());
		panel2.add(jl,"Center");
		panel2.setPreferredSize(new Dimension(630, 450));
		// 文件拖选框
		/*
		 * 这里要是先文件拖选框，主要是实现Queue<String> folders的设置
		 */
		buildPanel3();
		// 这个先空着就是在panel3里面加东西
		setLayout(new BorderLayout());
		this.setJMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		this.setPreferredSize(new Dimension(910, 520));
		this.setTitle("文件系统优化工具");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocation(300,150);
	}

	/**
	 * 前台初始化<br>
	 * 将后台传给前台,从此前台可以调用后台
	 * 
	 * @param back
	 */
	public void frontInit(BackRunner back) {
		this.back = back;
		this.setVisible(true);
	}
	
	protected void buildPanel3(){
		GridBagLayout layout=new GridBagLayout();
		GridBagConstraints c= new GridBagConstraints();
		panel3.setLayout(layout);
		
		c.gridwidth=GridBagConstraints.REMAINDER;
		c.gridy=GridBagConstraints.RELATIVE;
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1.0;
		c.weighty=0.0;
		
		show=new JButton("显示详细信息");
		show.addActionListener(this);
		show.setEnabled(false);
		layout.setConstraints(show, c);
		panel3.add(show);
		
		c.weighty=1.0;
		fList = new FolderList();
		layout.setConstraints(fList, c);
		panel3.add(fList);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("开始检测")) {
			show.setEnabled(false);
			back.startCal(blocks, fList.getFolders());
			jb.setText("停止");
		} else if (cmd.equals("停止")) // 停止按钮
		{
			back.stopCal();
			jb.setText("开始检测");
		} else if (cmd.equals("显示详细信息")) {
			@SuppressWarnings("unused")
			FolderTable table=new FolderTable(blocks, result);
		}
		else if(e.getActionCommand().equals("set"))
		{
			JF_second=new JFrame();//重新创建一个Frame
			JF_second.setLayout(new GridLayout(6,3));
			/*
			 * 这里弹出JtextFild
			 */
			mytext=new JTextField[5][2];
			for(int i=0;i<5;i++)
			{
				mytext[i][0]=new JTextField(Integer.toString(blocks[i][0]));
				mytext[i][1]=new JTextField(Integer.toString(blocks[i][1]));
				JF_second.add(new Label("第"+i+1+"个区间"));
				JF_second.add(mytext[i][0]);
				JF_second.add(mytext[i][1]);
			}
			
			JF_second.add(jb_commit);
			jb_commit.setActionCommand("commit");
			jb_commit.addActionListener(this);
			JF_second.add(jb_cancel);
			jb_cancel.setActionCommand("cancel");
			jb_cancel.addActionListener(this);
			
			JF_second.setTitle("请输入文件数的区间");
			JF_second.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JF_second.pack();
            
            JF_second.setLocation(500,300); 
			JF_second.setVisible(true);
		}
		else if(e.getActionCommand().equals("help"))
		{
			/*
			 * 这里弹出帮助栏
			 */
			JF_third=new JFrame();
			JLabel label_help=new JLabel("<html><p>操作手册：</p>" +
					                     "<p>1.用户可以通过操作中的设置来自行改变统计时各区间的文件数</p>" +
					                     "如果用户没有设置就将用默认值（0-5，6-10,11-15,16-20,20-正无穷）" +
					                     "<p>2.将用户想要检测的文件夹拖进右边的方框中即可</p>" +
					                     "<p>3.当设置完后，按开始测试按键就开始检测所选的文件夹及其子文件夹</p>" +
					                     "<p>4.进度条读完后，会出来一个柱状图，用户可以通过按钮来浏览各个柱状图所包含的文件夹</p>" +
					                     "<p>5.进入文件夹列表后，可以通过点击来到达所点目录，然后根据自己需要进行文件夹的调整</p>");
			label_help.setFont(new Font("黑体", Font.BOLD, 20));
			JF_third.add(label_help);
			JF_third.setTitle("帮助");
			JF_third.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JF_third.pack();
			JF_third.setLocation(380,300);
			JF_third.setPreferredSize(new Dimension(600, 520));
			JF_third.setVisible(true);
			
		}
		else if(e.getActionCommand().endsWith("beizhu"))
		{
			/*
			 * 这里弹出备注
			 */
			JF_fourth=new JFrame();
			//JLabel mylabel=new JLabel("author:" +"/n"+"杨延中"+"刘乔羽"+"/n"+"2012.6.1");
			JLabel label_beizhu=new JLabel("<html><p>作品名称：文件优化管理软件</p><p>作者：刘乔羽  杨延中 周旭</p><p>时间：2012.6.1(儿童节)</p></html>");
			label_beizhu.setFont(new Font("黑体", Font.BOLD, 20));
			JF_fourth.add(label_beizhu);
			JF_fourth.setTitle("备注");
			JF_fourth.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JF_fourth.setSize(500,200);
			JF_fourth.pack();
			JF_fourth.setLocation(500,300);
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
			System.out.println("ok!");
			JF_second.dispose();
		}
		else if(e.getActionCommand().equals("cancel"))
		{
			JF_second.dispose();
		}
	}

	@Override
	public void showStage(double finished) {
		int temp = (int) (finished * 100);
		jp.setValue(temp);
	}

	@Override
	public void complete(int[] heights, ArrayList<LinkedList<Folder>> lists) {
		this.result = lists;
		show.setEnabled(true);
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
