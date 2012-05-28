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
 * ��Ϊ��̨������<br>
 * ���ܻ��б����
 * 
 * @author lqy
 * 
 */
public class BackMain implements BackRunner, Runnable, ActionListener {
	/**
	 * ���г��ȵ�����,δ�����̫��,�˷��ڴ�
	 */
	private static final int QueueLimit = 1000;

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
	protected ArrayList<LinkedList<Folder>> lists;

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
	/**
	 * �����ļ���������,����һ���߳��������ļ��е�����,������Ϻ���Ⱦ͸���׼��
	 */
	protected CountFolder counter;

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

		// ��CountFolder�Ŀ�����folders
		Queue<String> copy = new LinkedList<String>();
		String path;
		while (!folders.isEmpty()) {
			path = folders.remove();
			queue.add(new File(path));
			copy.add(path);
		}

		// �����̼߳�����������(�ļ�������)
		counter = new CountFolder(copy);
		Thread t = new Thread(counter);
		t.start();

		int i;
		final int len = blocks.length;
		heights = new int[len];
		lists = new ArrayList<LinkedList<Folder>>(len);
		for (i = 0; i < len; ++i)
			lists.add(new LinkedList<Folder>());

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

		lists.get(i).add(new Folder(folder, subs));
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

		final int size = queue.size();
		for (File e : subs)
			if (e.isDirectory()) // �ļ��в����
				if (size < QueueLimit)
					queue.add(e); // �������
				else
					count(e); // �������
	}

	/**
	 * ����ļ��е�������<br>
	 * Ҳ�����ܵ�������,��counter�������֮ǰ,��ʱ��ɨ�������������еĴ�С�ĺ�������
	 * 
	 * @return ��������
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

		// ������,Ȼ�󷵻ظ�����������ļ���--�ļ���·��
		for (LinkedList<Folder> e : lists) {
			Collections.sort(e);
		}

		front.complete(heights, lists);
		lists = null; // �������,��JVM��������Ļ����ڴ�
		queue.clear();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		front.showStage(scanned / (double) this.getFolderNum());
	}

}
