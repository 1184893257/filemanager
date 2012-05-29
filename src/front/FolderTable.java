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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
		box.addActionListener(this);

		JPanel top = new JPanel();
		top.add(label);
		top.add(box);
		add(top, "North");

		this.setTable(0);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * 设置显示第block区间的信息
	 * 
	 * @param block
	 *            区间号
	 */
	protected void setTable(int block) {
		if (this.folderTable != null) // 已经有个区间在显示了
			this.remove(folderTable);

		// 新建一个表格
		this.folderTable = new JTable(new FolderTableModel(lists.get(block)));

		// 设置最后一列为一个按钮
		TableColumnModel column = this.folderTable.getColumnModel();
		final int i = column.getColumnCount() - 1;
		column.getColumn(i).setCellRenderer(cell);
		column.getColumn(i).setCellEditor(cell);

		// 将新的表格添加到面板上
		this.add(new JScrollPane(folderTable), "Center");
		pack();
	}

	/**
	 * JTable的数据模型内部类<br>
	 * 用于展示文件夹及其子文件数
	 * 
	 * @author lqy
	 * 
	 */
	protected class FolderTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		String[] columnNames = { "文件夹路径", "子文件个数", "操作" };
		Object[][] data;

		/**
		 * 使用Folder的链表,建立一个表格的数据模型
		 * 
		 * @param list
		 *            LinkedList<Folder>对象,可代表一个区间
		 */
		public FolderTableModel(LinkedList<Folder> list) {
			final int size = list.size();
			data = new Object[size][2];

			// 给第1、2列赋值
			Iterator<Folder> it = list.iterator();
			int i;
			Folder f;
			for (i = 0; i < size; ++i) {
				f = it.next();
				data[i][0] = f.path;
				data[i][1] = f.subs;
			}
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Class<? extends Object> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		// 不允许修改任何单元
		public boolean isCellEditable(int row, int col) {
			return false;
		}
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
