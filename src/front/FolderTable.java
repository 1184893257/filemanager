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
	 * ���������������Ϣ������
	 */
	protected ArrayList<LinkedList<Folder>> lists;
	/**
	 * ��ʾ������Ϣ�ı��
	 */
	protected JTable folderTable;
	/**
	 * ���Ԫģ��
	 */
	protected TableCell cell;
	/**
	 * ����ѡ���
	 */
	protected JComboBox<String> box;

	/**
	 * �����´�����ʾһ��������ļ���-���ļ���
	 * 
	 * @param blocks
	 *            ����ķ�Χ
	 * @param lists
	 *            ��������������Ϣ������
	 */
	public FolderTable(int[][] blocks, ArrayList<LinkedList<Folder>> lists) {
		this.lists = lists;
		cell = new TableCell();

		JLabel label = new JLabel("��ѡ��Ҫ��ʾ������:");

		// ��������ѡ���
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
	 * ������ʾ��block�������Ϣ
	 * 
	 * @param block
	 *            �����
	 */
	protected void setTable(int block) {
		if (this.folderTable != null) // �Ѿ��и���������ʾ��
			this.remove(folderTable);

		// �½�һ�����
		this.folderTable = new JTable(new FolderTableModel(lists.get(block)));

		// �������һ��Ϊһ����ť
		TableColumnModel column = this.folderTable.getColumnModel();
		final int i = column.getColumnCount() - 1;
		column.getColumn(i).setCellRenderer(cell);
		column.getColumn(i).setCellEditor(cell);

		// ���µı����ӵ������
		this.add(new JScrollPane(folderTable), "Center");
		pack();
	}

	/**
	 * JTable������ģ���ڲ���<br>
	 * ����չʾ�ļ��м������ļ���
	 * 
	 * @author lqy
	 * 
	 */
	protected class FolderTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		String[] columnNames = { "�ļ���·��", "���ļ�����", "����" };
		Object[][] data;

		/**
		 * ʹ��Folder������,����һ����������ģ��
		 * 
		 * @param list
		 *            LinkedList<Folder>����,�ɴ���һ������
		 */
		public FolderTableModel(LinkedList<Folder> list) {
			final int size = list.size();
			data = new Object[size][2];

			// ����1��2�и�ֵ
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

		// �������޸��κε�Ԫ
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}

	/**
	 * ���Ԫģ��<br>
	 * ��Ӵ��ļ��а�ť��folderTable�����һ��
	 * 
	 * @author lqy
	 * 
	 */
	protected class TableCell extends AbstractCellEditor implements
			TableCellRenderer, TableCellEditor, ActionListener {
		private static final long serialVersionUID = 1L;
		/**
		 * ���ļ��а�ť
		 */
		protected JButton open;
		/**
		 * Desktop����<br>
		 * Desktop�����ṩͼ�λ�����һЩ����
		 */
		protected Desktop desk;

		public TableCell() {
			open = new JButton("�򿪴��ļ���");
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
