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
 * ��Ϊǰ̨������<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI, ActionListener {

	JPanel panel1 = new JPanel();// ʵ�ֽ������Ͱ�ť
	JPanel panel2 = new JPanel();// ʵ����״ͼ
	JPanel panel3 = new JPanel();// ʵ���ļ��϶���
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

	// ���ڴ洢��һЩ����
	int[][] blocks;
	Queue<String> folders;
	
	/**
	 * ��̨ͳ����Ϸ��صĸ������� �ļ���-���ļ��� ���
	 */
	protected ArrayList<LinkedList<Folder>> result;
	/**
	 * �����ļ����б�
	 */
	protected FolderList fList;
	/**
	 * ������������ϸ��Ϣ
	 */
	protected JButton show;
	/**
	 * ��̨<br>
	 * ǰ̨ʹ�ú�̨�ӿھ͹���
	 */
	protected BackRunner back;

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
		blocks=new int[][]{{0,5},{6,10},{11,15},{16,20},{21,1000}};
		// �������Ͱ�ť
		panel1.add(jp);
		jp.setValue(0);
		jp.setPreferredSize(new Dimension(400,50));
		panel1.add(jb);
		jb.addActionListener(this);
		panel1.setPreferredSize(new Dimension(910, 70));
		//panel1.setBackground(Color.getHSBColor(177, 235, 240));
		// ֱ��ͼ
		// ����һ����ʼͼƬ��ʹ��ʼ�Ľ�������һ��
		ImageIcon img = new ImageIcon(
				"img\\��.jpg");
		JLabel jl = new JLabel(img);
		panel2.setLayout(new BorderLayout());
		panel2.add(jl,"Center");
		panel2.setPreferredSize(new Dimension(630, 450));
		// �ļ���ѡ��
		/*
		 * ����Ҫ�����ļ���ѡ����Ҫ��ʵ��Queue<String> folders������
		 */
		buildPanel3();
		// ����ȿ��ž�����panel3����Ӷ���
		setLayout(new BorderLayout());
		this.setJMenuBar(mb);
		this.add(panel1, "North");
		this.add(panel2, "West");
		this.add(panel3, "East");
		this.setPreferredSize(new Dimension(910, 520));
		this.setTitle("�ļ�ϵͳ�Ż�����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocation(300,150);
	}

	/**
	 * ǰ̨��ʼ��<br>
	 * ����̨����ǰ̨,�Ӵ�ǰ̨���Ե��ú�̨
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
		
		show=new JButton("��ʾ��ϸ��Ϣ");
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
		if (cmd.equals("��ʼ���")) {
			show.setEnabled(false);
			back.startCal(blocks, fList.getFolders());
			jb.setText("ֹͣ");
		} else if (cmd.equals("ֹͣ")) // ֹͣ��ť
		{
			back.stopCal();
			jb.setText("��ʼ���");
		} else if (cmd.equals("��ʾ��ϸ��Ϣ")) {
			@SuppressWarnings("unused")
			FolderTable table=new FolderTable(blocks, result);
		}
		else if(e.getActionCommand().equals("set"))
		{
			JF_second=new JFrame();//���´���һ��Frame
			JF_second.setLayout(new GridLayout(6,3));
			/*
			 * ���ﵯ��JtextFild
			 */
			mytext=new JTextField[5][2];
			for(int i=0;i<5;i++)
			{
				mytext[i][0]=new JTextField(Integer.toString(blocks[i][0]));
				mytext[i][1]=new JTextField(Integer.toString(blocks[i][1]));
				JF_second.add(new Label("��"+i+1+"������"));
				JF_second.add(mytext[i][0]);
				JF_second.add(mytext[i][1]);
			}
			
			JF_second.add(jb_commit);
			jb_commit.setActionCommand("commit");
			jb_commit.addActionListener(this);
			JF_second.add(jb_cancel);
			jb_cancel.setActionCommand("cancel");
			jb_cancel.addActionListener(this);
			
			JF_second.setTitle("�������ļ���������");
			JF_second.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JF_second.pack();
            
            JF_second.setLocation(500,300); 
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
					                     "����û�û�����þͽ���Ĭ��ֵ��0-5��6-10,11-15,16-20,20-�����" +
					                     "<p>2.���û���Ҫ�����ļ����Ͻ��ұߵķ����м���</p>" +
					                     "<p>3.��������󣬰���ʼ���԰����Ϳ�ʼ�����ѡ���ļ��м������ļ���</p>" +
					                     "<p>4.����������󣬻����һ����״ͼ���û�����ͨ����ť�����������״ͼ���������ļ���</p>" +
					                     "<p>5.�����ļ����б�󣬿���ͨ���������������Ŀ¼��Ȼ������Լ���Ҫ�����ļ��еĵ���</p>");
			label_help.setFont(new Font("����", Font.BOLD, 20));
			JF_third.add(label_help);
			JF_third.setTitle("����");
			JF_third.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JF_third.pack();
			JF_third.setLocation(380,300);
			JF_third.setPreferredSize(new Dimension(600, 520));
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
			label_beizhu.setFont(new Font("����", Font.BOLD, 20));
			JF_fourth.add(label_beizhu);
			JF_fourth.setTitle("��ע");
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
