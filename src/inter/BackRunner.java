package inter;

import java.util.Queue;

/**
 * ��̨ͳ�ƽӿ�,��̨������ʵ������ӿ�<br>ǰ̨���ó�Ա��������ʵ�ֺ�̨�ӿڵĵĶ���,���Բ���
 * @author lqy
 *
 */
public interface BackRunner {
	/**
	 * ������̨�߳�,�ݹ�ͳ�ƶ���folders����Ϣ<br>�˷����ĵ��ó�����ǰ̨��ĳ����ť�ĵ����¼���
	 * @param blocks ������Ϣ�Ķ�ά����(������)<br>����:{{0,0},{1,5},{6,10},
	 * {11,20},{21,+��}}
	 * @param folders ���������ļ��е�·��
	 */
	public void startCal(int[][] blocks,Queue<String> folders);
	
	/**
	 * �ں�̨������ܻ�û��ͳ�����֮ǰ�жϺ�̨�ļ������
	 */
	public void stopCal();
}
