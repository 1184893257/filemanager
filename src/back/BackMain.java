package back;

import inter.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;

/**
 * 此为后台的主类<br>
 * 可能还有别的类
 * 
 * @author lqy
 * 
 */
public class BackMain implements BackRunner, Runnable, ActionListener {
	/**
	 * 队列长度的限制,未免队列太长,浪费内存
	 */
	private static final int QueueLimit = 1000;

	/**
	 * 此接口的对象可代表前台
	 */
	protected FrontGUI front;
	/**
	 * 区间信息
	 */
	protected int[][] blocks;
	/**
	 * 各个直方图目前的高度
	 */
	protected int[] heights;
	/**
	 * 这是后台的扫描队列,其中存的是未扫描过的文件夹子文件夹
	 */
	protected Queue<File> queue;
	/**
	 * 扫描过的文件夹的信息(子文件数-父文件夹)
	 */
	protected ArrayList<LinkedList<Folder>> lists;

	/**
	 * 负责统计运算的线程
	 */
	protected Thread runner;
	/**
	 * 时钟信号,每隔一定时间间隔触发一次XX事件,可用于定时刷新直方图
	 */
	protected Timer timer;
	/**
	 * 中断信号
	 */
	protected boolean stopped;
	/**
	 * 已经扫描过的文件夹数量
	 */
	protected int scanned;
	/**
	 * 计算文件夹数量的,另用一个线程来计算文件夹的数量,计算完毕后进度就更精准了
	 */
	protected CountFolder counter;

	public BackMain() {
		timer = new Timer(1000, this);
	}

	/**
	 * 初始化后台,在程序的生命周期中只需一次
	 * 
	 * @param front
	 *            实现前台接口的对象
	 */
	public void backInit(FrontGUI front) {
		this.front = front;
	}

	@Override
	public void startCal(int[][] blocks, Queue<String> folders) {
		// 初始化相关成员变量
		this.blocks = blocks;
		queue = new LinkedList<File>();

		// 给CountFolder的拷贝版folders
		Queue<String> copy = new LinkedList<String>();
		String path;
		while (!folders.isEmpty()) {
			path = folders.remove();
			queue.add(new File(path));
			copy.add(path);
		}

		// 启动线程计算任务总数(文件夹数量)
		counter = new CountFolder(copy);
		Thread t = new Thread(counter);
		t.start();

		int i;
		final int len = blocks.length;
		heights = new int[len];
		lists = new ArrayList<LinkedList<Folder>>(len);
		for (i = 0; i < len; ++i)
			lists.add(new LinkedList<Folder>());

		// 启动线程
		runner = new Thread(this);
		stopped = false;
		scanned = 0;
		runner.start();
		timer.start();
	}

	@Override
	public void stopCal() {
		stopped = true;
		if (runner != null)
			try {
				runner.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 将有subs个子文件的父文件夹folder添加到相应区间中去
	 * 
	 * @param folder
	 *            父文件夹的路径
	 * @param subs
	 *            子文件的数量
	 */
	protected void add(String folder, int subs) {
		int i;
		for (i = 0; i < blocks.length; ++i)
			if (subs <= blocks[i][1])
				break;

		lists.get(i).add(new Folder(folder, subs));
		heights[i]++;
	}

	/**
	 * 统计一个文件夹的信息,并将子文件夹加入到队列的后边
	 * 
	 * @param father
	 */
	protected void count(File father) {
		scanned++;
		File[] subs = father.listFiles();
		try {
			add(father.getCanonicalPath(), subs.length);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final int size = queue.size();
		for (File e : subs)
			if (e.isDirectory()) // 文件夹才入队
				if (size < QueueLimit)
					queue.add(e); // 广度优先
				else
					count(e); // 深度优先
	}

	/**
	 * 获得文件夹的总数量<br>
	 * 也就是总的任务数,在counter计算完成之前,暂时用扫描过的数量与队列的大小的和来代替
	 * 
	 * @return 任务总数
	 */
	protected int getFolderNum() {
		if (counter.finished)
			return counter.num;
		int t = scanned + queue.size();
		if (counter.num > t)
			return counter.num;
		return t;
	}

	@Override
	public void run() {
		File f;
		while (!stopped && !queue.isEmpty()) {
			f = queue.remove();
			count(f);
		}
		timer.stop();
		front.showStage(scanned / (double) this.getFolderNum());

		// 先排序,然后返回各个区间的子文件数--文件夹路径
		for (LinkedList<Folder> e : lists) {
			Collections.sort(e);
		}

		front.complete(heights, lists);
		lists = null; // 解除引用,让JVM尽可能早的回收内存
		queue.clear();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		front.showStage(scanned / (double) this.getFolderNum());
	}

}
