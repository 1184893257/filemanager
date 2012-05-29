package front;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;

public class FolderList extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * �������ļ��е��б�
	 */
	protected DragList list;

	public FolderList() {
		// ���ò��ֹ�����
		setLayout(new BorderLayout());

		JButton clear = new JButton("����б�");
		add(clear, "North");
		clear.addActionListener(this);

		list = new DragList();
		add(new JScrollPane(list), "Center");
	}

	/**
	 * ��ȡ�����ļ���·��
	 * 
	 * @return
	 */
	public Queue<String> getFolders() {
		return list.folders;
	}

	/**
	 * JList������,���϶��ļ�������б�
	 * 
	 * @author lqy
	 * 
	 */
	class DragList extends JList<String> implements DropTargetListener {
		private static final long serialVersionUID = 1L;
		/**
		 * �洢�˸��������ļ���·��
		 */
		public Queue<String> folders;
		/**
		 * JList������ģ��
		 */
		protected DefaultListModel<String> model;

		public DragList() {
			folders = new LinkedList<String>();
			model = new DefaultListModel<String>();
			this.setModel(model);
			new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		}

		/**
		 * ��ն���Ŀ¼�б�
		 */
		public void clearFolders() {
			model.clear();
			folders.clear();
			this.updateUI();
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
