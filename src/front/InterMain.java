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
 * ��Ϊǰ̨������<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI, ActionListener {

	JPanel panel1 = new JPanel();// ʵ�ֽ������Ͱ�ť
	JPanel panel2 = new JPanel();// ʵ����״ͼ
	FolderList panel3 = new FolderList();// ʵ���ļ��϶���
	JMenuBar mb = new JMenuBar();// ʵ�ֲ˵�
	JProgressBar jp = new JProgressBar(0, 100);
	JButton jb = new JButton("��ʼ���");
	
	JButton jb_commit=new JButton("ȷ��");//���ò����������õ���button��ȷ��
	JButton jb_cancel=new JButton("ȡ��");//���ò����������õ���button��ȡ��
	JTextField[][] mytext;               //���ò����������õ�
	JFrame JF_second;                    //���ò����������õ�
	JFrame JF_third;					 //�����Ľ���
	JFrame JF_fourth;					 //��ע�Ľ���
	
	JMenu operate = new JMenu("����");
	JMenu other = new JMenu("����");
	JMenuItem set=new JMenuItem("����");
	JMenuItem help=new JMenuItem("����");
	JMenuItem beizhu=new JMenuItem("��ע");

	int Button_flag = 0;// ���ڱ��button��״̬��Ϊ0��û�����г���1���������г���

	// ���ڴ洢��һЩ����
	int[][] blocks;
	Queue<String> folders;

	protected BackMain back;

	/**
	 * �����������ľ���ô������ù���
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���캯��
	 */
	public InterMain() {
		// �˵�
		operate.add(set);
		other.add(help);
		other.add(beizhu);
		mb.add(operate);
		mb.add(other);
		// ʱ�������
		set.setActionCommand("set");
		help.setActionCommand("help");
		beizhu.setActionCommand("beizhu");
		set.addActionListener(this);
		help.addActionListener(this);
		beizhu.addActionListener(this);
		int[][] blocks={{0,5},{6,10},{11,15},{16,20},{21,1000}};
		// �������Ͱ�ť
		jb.setActionCommand("Button");
		panel1.add(jp);
		panel1.add(jb);
		jb.addActionListener(this);
		//panel1.setPreferredSize(new Dimension(300, 600));
		// ֱ��ͼ
		// ����һ����ʼͼƬ��ʹ��ʼ�Ľ�������һ��
		ImageIcon img = new ImageIcon(
				"C:\\Users\\yang\\Desktop\\����\\2008-0828v14841C.jpg");
		JLabel jl = new JLabel(img);
		panel2.add(jl);
		//panel2.setPreferredSize(new Dimension(600, 400));
		// �ļ���ѡ��
		/*
		 * ����Ҫ�����ļ���ѡ����Ҫ��ʵ��Queue<String> folders������
		 */
		// ����ȿ��ž�����panel3����Ӷ���
		setLayout(new BorderLayout());
		this.setJMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		//this.setPreferredSize(new Dimension(900, 600));
		this.setTitle("�ļ��Ż�����ϵͳ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Button"))
		{
			if (Button_flag == 0)// ��ʼ��ť
			{
				back.startCal(blocks, folders);
				jb.setText("ֹͣ");
				Button_flag = 1;
			} 
			else // ֹͣ��ť
			{
				back.stopCal();
				jb.setText("��ʼ���");
				Button_flag = 0;
			}
		}
		else if(e.getActionCommand().equals("set"))
		{
			/*
			 * ���ﵯ��JtextFild
			 */
			mytext=new JTextField[5][2];
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<2;j++)
					mytext[i][j]=new JTextField();
			}
			
			JF_second=new JFrame();//���´���һ��Frame
			JF_second.setLayout(new GridLayout(6,3));
			JF_second.add(new Label("��һ������"));
			JF_second.add(mytext[0][0]);
			JF_second.add(mytext[0][1]);
			JF_second.add(new Label("�ڶ�������"));
			JF_second.add(mytext[1][0]);
			JF_second.add(mytext[1][1]);
			JF_second.add(new Label("����������"));
			JF_second.add(mytext[2][0]);
			JF_second.add(mytext[2][1]);
			JF_second.add(new Label("���ĸ�����"));
			JF_second.add(mytext[3][0]);
			JF_second.add(mytext[3][1]);
			JF_second.add(new Label("���������"));
			JF_second.add(mytext[4][0]);
			JF_second.add(mytext[4][1]);
			JF_second.add(jb_commit);
			jb_commit.setActionCommand("commit");
			jb_commit.addActionListener(this);
			JF_second.add(jb_cancel);
			jb_cancel.setActionCommand("cancel");
			jb_cancel.addActionListener(this);
			
			JF_second.setTitle("�������ļ���������");
			JF_second.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_second.pack();
			JF_second.setVisible(true);
		}
		else if(e.getActionCommand().equals("help"))
		{
			/*
			 * ���ﵯ��������
			 */
			JF_third=new JFrame();
			JLabel label_help=new JLabel("<html><p>�����ֲ᣺</p>" +
					                     "<p>1.�û�����ͨ�������е����������иı�ͳ��ʱ��������ļ���</p>" +
					                     "����û�û�����þͽ���Ĭ��ֵ��0-5��6-10,11-15,16-20,20--��" +
					                     "<p>2.���û���Ҫ�����ļ����Ͻ��ұߵķ����м���</p>" +
					                     "<p>3.��������󣬰���ʼ���԰����Ϳ�ʼ�����ѡ���ļ��м������ļ���</p>" +
					                     "<p>4.����������󣬻����һ����״ͼ���û�����ͨ����ť�����������״ͼ���������ļ���</p>" +
					                     "<p>5.�����ļ����б�󣬿���ͨ���������������Ŀ¼��Ȼ������Լ���Ҫ�����ļ��еĵ���</p>");
			JF_third.add(label_help);
			JF_third.setTitle("����");
			JF_third.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_third.pack();
			JF_third.setVisible(true);
			
		}
		else if(e.getActionCommand().endsWith("beizhu"))
		{
			/*
			 * ���ﵯ����ע
			 */
			JF_fourth=new JFrame();
			//JLabel mylabel=new JLabel("author:" +"/n"+"������"+"������"+"/n"+"2012.6.1");
			JLabel label_beizhu=new JLabel("<html><p>��Ʒ���ƣ��ļ��Ż��������</p><p>���ߣ�������  ������ ����</p><p>ʱ�䣺2012.6.1(��ͯ��)</p></html>");
			JF_fourth.add(label_beizhu);
			JF_fourth.setTitle("��ע");
			JF_fourth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JF_fourth.setSize(500,200);
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
		JFreeChart chart = ChartFactory.createBarChart3D("ͳ�ƽ��", // ������
				"�����", // ����������
				"�ļ���", // ����������
				dataset, // ���ݼ�
				PlotOrientation.VERTICAL, // ˮƽ��
				true, // ��ʾ��ͼ
				false, // �����ɹ���
				false); // ������URL����
		CategoryPlot plot = chart.getCategoryPlot();// ��ȡͼ���������
		CategoryAxis domainAxis = plot.getDomainAxis(); // ˮƽ�ײ��б�
		domainAxis.setLabelFont(new Font("����", Font.BOLD, 14)); // ˮƽ�ײ�����
		domainAxis.setTickLabelFont(new Font("����", Font.BOLD, 12)); // ��ֱ����
		ValueAxis rangeAxis = plot.getRangeAxis();// ��ȡ��״
		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));// ���ñ�������
		panel2 = new ChartPanel(chart, true);
	}

}
