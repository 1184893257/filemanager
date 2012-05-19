package inter;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * ��Ϊǰ̨��Ҫʵ�ֵĽӿ�<br>ͬ��Ӧ���ں�̨����ʹ�ó�Ա��������һ��ʵ�ִ˽ӿڵ�ǰ̨�Ķ���
 * @author lqy
 *
 */
public interface FrontGUI {
	/**
	 * ��ʾ����<br>�ں�̨�������һ����ͳ�ƹ���ʱ(һ����һ��ʱ������)�����¼�,����
	 * �˷���,��ʾ��ǰ����
	 * @param heights �����������ڵĸ߶�
	 * @param finished ��ɶ�,��0.0~1.0�ĸ�������ʾ
	 */
	public void showStage(int[] heights,double finished);
	
	/**
	 * ���ͳ�ƺ�(�����жϺ�̨��)������ͳ�����ݴ��ظ�ǰ̨
	 * @param lists �������������<b>���ļ���--�ļ���·��</b>��ֵ�Ե�Map����
	 * <br>��Ϊ����new����������,�ʸı�ӿڵĲ���
	 */
	public void complete(ArrayList<TreeMap<Integer,String>> lists);
}
