package inter;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 此为前台需要实现的接口<br>
 * 同样应该在后台程序使用成员变量保存一个实现此接口的前台的对象
 * 
 * @author lqy
 * 
 */
public interface FrontGUI {
	/**
	 * 显示进度<br>
	 * 在后台程序完成一部分统计工作时(一般是一定时间间隔后)触发事件,调用 此方法,显示当前进度
	 * 
	 * @param heights
	 *            各个区间现在的高度
	 * @param finished
	 *            完成度,用0.0~1.0的浮点数表示
	 */
	public void showStage(int[] heights, double finished);

	/**
	 * 完成统计后(或者中断后台后)将最终统计数据传回给前台
	 * 
	 * @param lists
	 *            包含各个区间的<b>子文件数--文件夹路径</b>键值对的数组 <br>
	 *            因为不能new泛型类数组,故改变接口的参数 <br>
	 *            不能用Map,因为Map中键是唯一的,不能将子文件数作为键
	 */
	public void complete(ArrayList<LinkedList<Folder>> lists);
}
