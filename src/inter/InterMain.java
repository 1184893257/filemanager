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
 * 此为前台的主类<br>
 * 
 * @author yyz
 * 
 */
public class InterMain extends JFrame implements FrontGUI,Runnable, ActionListener {

	JPanel panel1=new JPanel();//实现进度条和按钮
	JPanel panel2=new JPanel();//实现柱状图
	JPanel panel3=new JPanel();//实现文件拖动框
	MenuBar mb=new MenuBar();//实现菜单
	JProgressBar jp=new JProgressBar(0,1);
	JButton jb=new JButton("检测");
	Menu set=new Menu("设置"); 
	Menu help=new Menu("帮助"); 
	Menu beizhu=new Menu("备注"); 
	
	/**
	 * 在我这里这个木有用处，不用管了
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *构造函数 
	 */
	InterMain(){
		//菜单	
		mb.add(set);
		mb.add(help);
		mb.add(beizhu);
		this.setMenuBar(mb);
		//进度条和按钮
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
