package back;

import inter.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

import javax.swing.Timer;

/**
 * ��Ϊ��̨������<br>
 * ���ܻ��б����
 * 
 * @author lqy
 * 
 */
public class BackMain implements BackRunner, Runnable, ActionListener {
	/**
	 * �˽ӿڵĶ���ɴ���ǰ̨
	 */
	protected FrontGUI front;
	/**
	 * ������Ϣ
	 */
	protected int[][] blocks;
	/**
	 * ����ֱ��ͼĿǰ�ĸ߶�
	 */
	protected int[] heights;
	/**
	 * ���Ǻ�̨��ɨ�����,���д����δɨ������ļ������ļ���
	 */
	protected Queue<File> queue;
	/**
	 * ɨ������ļ��е���Ϣ(���ļ���-���ļ���)
	 */
	protected ArrayList<TreeMap<Integer, String>> lists;

	/**
	 * ����ͳ��������߳�
	 */
	protected Thread runner;
	/**
	 * ʱ���ź�,ÿ��һ��ʱ��������һ��XX�¼�,�����ڶ�ʱˢ��ֱ��ͼ
	 */
	protected Timer timer;
	/**
	 * �ж��ź�
	 */
	protected boolean stopped;
	/**
	 * �Ѿ�ɨ������ļ�������
	 */
	protected int scanned;

	public BackMain() {
		timer = new Timer(1000, this);
	}

	/**
	 * ��ʼ����̨,�ڳ��������������ֻ��һ��
	 * 
	 * @param front
	 *            ʵ��ǰ̨�ӿڵĶ���
	 */
	public void backInit(FrontGUI front) {
		this.front = front;
	}

	@Override
	public void startCal(int[][] blocks, Queue<String> folders) {
		// ��ʼ����س�Ա����
		this.blocks = blocks;
		queue = new LinkedList<File>();
		while (!folders.isEmpty())
			queue.add(new File(folders.remove()));

		int i;
		final int len = blocks.length;
		heights = new int[len];
		lists = new ArrayList<TreeMap<Integer, String>>(len);
		for (i = 0; i < len; ++i)
			lists.add(new TreeMap<Integer, String>());

		// �����߳�
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
	 * ����subs�����ļ��ĸ��ļ���folder��ӵ���Ӧ������ȥ
	 * 
	 * @param folder
	 *            ���ļ��е�·��
	 * @param subs
	 *            ���ļ�������
	 */
	protected void add(String folder, int subs) {
		int i;
		for (i = 0; i < blocks.length; ++i)
			if (subs <= blocks[i][1])
				break;

		lists.get(i).put(subs, folder);
		heights[i]++;
	}

	/**
	 * ͳ��һ���ļ��е���Ϣ,�������ļ��м��뵽���еĺ��
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

		for (File e : subs)
			queue.add(e);
	}

	@Override
	public void run() {
		File f;
		while (!stopped && !queue.isEmpty()) {
			f = queue.remove();
			count(f);
		}
		timer.stop();
		front.showStage(heights, scanned / (double) (scanned + queue.size()));
		front.complete(lists);
		queue.clear();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		front.showStage(heights, scanned / (double) (scanned + queue.size()));
	}

}
