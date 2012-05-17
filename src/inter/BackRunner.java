package inter;

import java.util.Queue;

/**
 * 后台统计接口,后台程序需实现这个接口<br>前台可用成员变量保留实现后台接口的的对象,用以操作
 * @author lqy
 *
 */
public interface BackRunner {
	/**
	 * 启动后台线程,递归统计顶层folders的信息<br>此方法的调用出现在前台的某个按钮的单击事件中
	 * @param blocks 区间信息的二维数组(闭区间)<br>形如:{{0,0},{1,5},{6,10},
	 * {11,20},{21,+∞}}
	 * @param folders 各个顶层文件夹的路径
	 */
	public void startCal(int[][] blocks,Queue<String> folders);
	
	/**
	 * 在后台程序可能还没有统计完成之前中断后台的计算过程
	 */
	public void stopCal();
}
