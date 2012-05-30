package front;

import inter.Folder;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class FolderTable extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 存有所有区间的信息的数组
	 */
	protected ArrayList<LinkedList<Folder>> lists;
	/**
	 * 显示区间信息的表格
	 */
	protected JTable folderTable;
	/**
	 * 表格单元模型
	 */
	protected TableCell cell;
	/**
	 * 区间选择框
	 */
	protected JComboBox<String> box;
	/**
	 * 包裹表格的JScrollPane
	 */
	protected JScrollPane pane;

	/**
	 * 弹出新窗体显示一个区间的文件夹-子文件数
	 * 
	 * @param blocks
	 *            区间的范围
	 * @param lists
	 *            包含各个区间信息的数组
	 */
	public FolderTable(int[][] blocks, ArrayList<LinkedList<Folder>> lists) {
		this.lists = lists;
		cell = new TableCell();

		JLabel label = new JLabel("请选择要显示的区间:");

		// 生成下拉选择框
		final int size = blocks.length;
		String[] items = new String[size];
		int i;
		for (i = 0; i < size; ++i) {
			items[i] = Integer.toString(blocks[i][0]) + "-"
					+ Integer.toString(blocks[i][1]);
		}
		box = new JComboBox<String>(items);
		box.setSelectedIndex(size - 1);
		box.addActionListener(this);

		JPanel top = new JPanel();
		top.add(label);
		top.add(box);
		add(top, "North");

		this.setTable(size - 1);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 设置显示第block区间的信息
	 * 
	 * @param block
	 *            区间号
	 */
	protected void setTable(int block) {
		if (this.folderTable != null) // 已经有个区间在显示了
			this.remove(pane);

		// 取得block区间的链表
		LinkedList<Folder> list = lists.get(block);
		final int size = list.size();

		// 新建一个表格,size行,3列
		this.folderTable = new JTable(size, 3);
		int i;
		Iterator<Folder> it = list.iterator();
		Folder e;
		for (i = 0; i < size; ++i) {
			e = it.next();
			folderTable.setValueAt(e.path, i, 0);
			folderTable.setValueAt(e.subs, i, 1);
		}

		// 设置最后一列为一个按钮
		TableColumn column = this.folderTable.getColumnModel().getColumn(2);
		column.setCellRenderer(cell);
		column.setCellEditor(cell);

		// 将新的表格添加到面板上
		pane = new JScrollPane(folderTable);
		this.add(pane, "Center");
		pack();
	}

	/**
	 * 表格单元模型<br>
	 * 添加打开文件夹按钮到folderTable的最后一列
	 * 
	 * @author lqy
	 * 
	 */
	protected class TableCell extends AbstractCellEditor implements
			TableCellRenderer, TableCellEditor, ActionListener {
		private static final long serialVersionUID = 1L;
		/**
		 * 打开文件夹按钮
		 */
		protected JButton open;
		/**
		 * Desktop对象<br>
		 * Desktop对象提供图形环境的一些方法
		 */
		protected Desktop desk;

		public TableCell() {
			open = new JButton("打开此文件夹");
			open.addActionListener(this);
			desk = Desktop.getDesktop();
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			return open;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			return open;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int r = FolderTable.this.folderTable.getSelectedRow();
			String path = (String) FolderTable.this.folderTable
					.getValueAt(r, 0);
			try {
				desk.open(new File(path));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setTable(box.getSelectedIndex());
	}
}
