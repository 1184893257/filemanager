package inter;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ��Ϊǰ̨��Ҫʵ�ֵĽӿ�<br>
 * ͬ��Ӧ���ں�̨����ʹ�ó�Ա��������һ��ʵ�ִ˽ӿڵ�ǰ̨�Ķ���
 * 
 * @author lqy
 * 
 */
public interface FrontGUI {
	/**
	 * ��ʾ����<br>
	 * �ں�̨�������һ����ͳ�ƹ���ʱ(һ����һ��ʱ������)�����¼�,���� �˷���,��ʾ��ǰ����
	 * 
	 * @param heights
	 *            �����������ڵĸ߶�
	 * @param finished
	 *            ��ɶ�,��0.0~1.0�ĸ�������ʾ
	 */
	public void showStage(int[] heights, double finished);

	/**
	 * ���ͳ�ƺ�(�����жϺ�̨��)������ͳ�����ݴ��ظ�ǰ̨
	 * 
	 * @param lists
	 *            �������������<b>���ļ���--�ļ���·��</b>��ֵ�Ե����� <br>
	 *            ��Ϊ����new����������,�ʸı�ӿڵĲ��� <br>
	 *            ������Map,��ΪMap�м���Ψһ��,���ܽ����ļ�����Ϊ��
	 */
	public void complete(ArrayList<LinkedList<Folder>> lists);
}
