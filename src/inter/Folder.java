package inter;

/**
 * 用于存储路径-子文件数键值对,并实现了Comparable接口,因此能使用Collections.sort来排序,
 * <b>sort之后是逆序的,子文件数多的在前面</b>
 * 
 * @author lqy
 * 
 */
public class Folder implements Comparable<Folder> {
	public String path;
	public int subs;

	/**
	 * Folder的构造方法
	 * 
	 * @param path
	 *            文件夹路径
	 * @param subs
	 *            子文件的个数
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
