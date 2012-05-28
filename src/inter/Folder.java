package inter;

/**
 * ���ڴ洢·��-���ļ�����ֵ��,��ʵ����Comparable�ӿ�,�����ʹ��Collections.sort������,
 * <b>sort֮���������,���ļ��������ǰ��</b>
 * 
 * @author lqy
 * 
 */
public class Folder implements Comparable<Folder> {
	public String path;
	public int subs;

	/**
	 * Folder�Ĺ��췽��
	 * 
	 * @param path
	 *            �ļ���·��
	 * @param subs
	 *            ���ļ��ĸ���
	 */
	public Folder(String path, int subs) {
		this.path = path;
		this.subs = subs;
	}

	public int compare(Folder f1, Folder f2) {
		return f1.subs > f2.subs ? 1 : (f1.subs == f2.subs ? 0 : -1);
	}

	@Override
	public int compareTo(Folder o) {
		return compare(o, this);
	}
}
