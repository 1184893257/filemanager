package inter;
import back.*;

import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;

/**
 * ��Ϊǰ̨������<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI,Runnable, ActionListener {

	JPanel panel1=new JPanel();//ʵ�ֽ������Ͱ�ť
	JPanel panel2=new JPanel();//ʵ����״ͼ
	JPanel panel3=new JPanel();//ʵ���ļ��϶���
	MenuBar mb=new MenuBar();//ʵ�ֲ˵�
	JProgressBar jp=new JProgressBar(0,1);
	JButton jb=new JButton("���");
	Menu set=new Menu("����"); 
	Menu help=new Menu("����"); 
	Menu beizhu=new Menu("��ע"); 
	
	/**
	 * �����������ľ���ô������ù���
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *���캯�� 
	 */
	InterMain(){
		//�˵�	
		mb.add(set);
		mb.add(help);
		mb.add(beizhu);
		this.setMenuBar(mb);
		//�������Ͱ�ť
		panel1.add(jp);
		panel1.add(jb);
		jb.addActionListener(this);
		//
		setLayout(new BorderLayout());
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showStage(int[] heights, double finished) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void complete(ArrayList<LinkedList<Folder>> lists) {
		// TODO Auto-generated method stub
		
	}
	
}
