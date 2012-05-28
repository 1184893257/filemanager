package back;

import java.io.File;
import java.util.Queue;

/**
 * 用于数文件夹数量的线程<br>
 * 深度优先遍历,占用内存应该比较少的
 * 
 * @author lqy
 * 
 */
public class CountFolder implements Runnable {
	/**
	 * 顶层目录<br>
	 * 本线程就是从这些顶层文件夹开始深度优先遍历,计算文件夹的数量
	 */
	protected Queue<String> folders;
	/**
	 * 是否计算完毕
	 */
	public boolean finished;
	/**
	 * 文件夹的数量
	 */
	public int num;

	/**
	 * 构造CountFolder对象
	 * 
	 * @param folders
	 *            顶层文件夹的队列
	 */
	public CountFolder(Queue<String> folders) {
		this.folders = folders;
	}

	/**
	 * 统计folder中子文件夹的数量,包括自身
	 * 
	 * @param folder
	 *            父文件夹
	 */
	protected void count(File folder) {
		this.num++;

		File[] subs = folder.listFiles();
		for (File e : subs)
			if (e.isDirectory())
				count(e);
	}

	@Override
	public void run() {
		while (!folders.isEmpty()) {
			count(new File(folders.remove()));
		}
		this.finished = true;
	}

}
