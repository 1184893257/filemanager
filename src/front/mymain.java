package front;

import back.*;

/**
 * ������<br>
 * 
 * @author yyz
 */
public class mymain {
	public static void main(String[] args) {
		InterMain myInter = new InterMain();
		BackMain back=new BackMain();
		myInter.frontInit(back);
		back.backInit(myInter);
	}
}
