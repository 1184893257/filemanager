package front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class FolderList extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 可拖入文件夹的列表
	 */
	protected DragList list;

	public FolderList() {
		// 设置布局管理器
		setLayout(new BorderLayout());

		JButton clear = new JButton("清空列表");
		add(clear, "North");
		clear.addActionListener(this);

		list = new DragList("请将要检查的文件夹拖放于此");
		add(list, "Center");
	}

	/**
	 * 获取顶层文件夹路径<br>
	 * 返回拷贝版本,以免列表中的队列被清空
	 * 
	 * @return
	 */
	public Queue<String> getFolders() {
		Queue<String> ans = new LinkedList<String>();
		Iterator<String> it;
		it = list.folders.iterator();
		while (it.hasNext())
			ans.add(it.next());
		return ans;
	}

	/**
	 * JList的子类,可拖动文件到这个列表
	 * 
	 * @author lqy
	 * 
	 */
	class DragList extends JList<String> implements DropTargetListener {
		private static final long serialVersionUID = 1L;
		/**
		 * 存储了各个顶层文件的路径
		 */
		public Queue<String> folders;
		/**
		 * JList的数据模型
		 */
		protected DefaultListModel<String> model;
		/**
		 * 要在JList中间显示的文本
		 */
		protected String text;

		public DragList(String text) {
			this.text = text;
			folders = new LinkedList<String>();
			model = new DefaultListModel<String>();
			this.setModel(model);
			new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}

		/**
		 * 清空顶层目录列表
		 */
		public void clearFolders() {
			model.clear();
			folders.clear();
			this.updateUI();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			int count = this.getModel().getSize();
			if (0 == count) {
				Dimension size = this.getSize();
				int x, y;
				y = size.height / 2;

				FontMetrics fm = this.getFontMetrics(this.getFont());
				x = (size.width - fm.stringWidth(text)) / 2;

				g.drawString(text, x, y);
			}
		}

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void drop(DropTargetDropEvent dtde) {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				try {
					List<File> list = (List<File>) (dtde.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor));
					Iterator<File> it = list.iterator();
					String path;
					File f;
					while (it.hasNext()) {
						f = it.next();
						if (f.isDirectory()) {
							path = f.getCanonicalPath();
							folders.add(path);
							model.addElement(path);
						}
					}
					int size = model.getSize();
					if (size > 0)
						this.scrollRectToVisible(this.getCellBounds(size - 1,
								size - 1));
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				dtde.dropComplete(true);
			} else {
				dtde.rejectDrop();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		list.clearFolders();
	}

}
