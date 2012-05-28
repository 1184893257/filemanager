package back;

import java.io.File;
import java.util.Queue;

/**
 * �������ļ����������߳�<br>
 * ������ȱ���,ռ���ڴ�Ӧ�ñȽ��ٵ�
 * 
 * @author lqy
 * 
 */
public class CountFolder implements Runnable {
	/**
	 * ����Ŀ¼<br>
	 * ���߳̾��Ǵ���Щ�����ļ��п�ʼ������ȱ���,�����ļ��е�����
	 */
	protected Queue<String> folders;
	/**
	 * �Ƿ�������
	 */
	public boolean finished;
	/**
	 * �ļ��е�����
	 */
	public int num;

	/**
	 * ����CountFolder����
	 * 
	 * @param folders
	 *            �����ļ��еĶ���
	 */
	public CountFolder(Queue<String> folders) {
		this.folders = folders;
	}

	/**
	 * ͳ��folder�����ļ��е�����,��������
	 * 
	 * @param folder
	 *            ���ļ���
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
