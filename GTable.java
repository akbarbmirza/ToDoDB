import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GTable extends JFrame {

	private JPanel contentPane;
	// *******************************************************************************
	private JTextField textField;
	private JTextField textField_1;
	JTextArea textEditTask; // needed for "Edit Task" method

	// DataBase Connection Class
	private ToDoQueries tdq;

	String[] columnNames = { "[  ]", "Task", "Category", "Date", "ID", "USER" };
	Object[][] data = {};

	final DefaultTableModel dtm = new DefaultTableModel(data, columnNames) {
		public Class<?> getColumnClass(int column) {

			switch (column) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			default:
				return String.class;

			}
		}

	};
	final JTable table = new JTable(dtm) {
		// Got this code from Stack Overflow to make the GTable data
		// non-editable
		private static final long serialVersionUID = 1L;

		// This function makes only the first column editable
		// The rest of the columns are not editable.
		public boolean isCellEditable(int row, int column) {

			return false;
		};
	};

	public void runTable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// Run the program
					GTable frame = new GTable(tdq);
					frame.setVisible(true);
					WindowLogin login = new WindowLogin();
					String username = login.getUsername();
					frame.setTitle(username + "'s ToDo List");

					ArrayList<ToDo> newData = tdq.getTodoList();
					frame.addData(newData);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GTable(final ToDoQueries tdq) {
		// set the ToDo queries
		this.tdq = tdq;

		// Make Sure it Exits on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set size of the new window
		setBounds(100, 100, 600, 500);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// ContentPane for our GTable
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setBackground(new Color(204, 204, 255));

		// Set Values for the table
		// Sets the font for the table
		table.setFont(new Font("Serif", Font.PLAIN, 20));
		// Sets how tall the rows should be
		table.setRowHeight(table.getRowHeight() + 20);
		// Allows the user to select rows
		table.setRowSelectionAllowed(true);
		// Make sure the JTable takes up the entire scrollpane
		table.setFillsViewportHeight(true);
		table.setOpaque(false);
		// Prevents the user from reordering the columns
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int column = table.getSelectedColumn();
					int currentRow = table.convertRowIndexToModel(table.getSelectedRow());
					if (column == 0) {
						if (dtm.getValueAt(currentRow, 0).equals(true)) {
							dtm.setValueAt(false, currentRow, 0);
							tdq.markDone(currentRow + 1);
							// marked Not Done by clicking check box
							JOptionPane.showMessageDialog(null, "Task Marked Incomplete", "Done Window",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							dtm.setValueAt(true, currentRow, 0);
							tdq.markDone(currentRow + 1);
							// marked Done by clicking check box
							JOptionPane.showMessageDialog(null, "Task Marked Done", "Done Window",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		// Add Button - add the task
		JButton btnAdd = new JButton("Add");
		// Clicking the Mouse causes a dialog box to open
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addTaskGui();
			}
		});
		btnAdd.setBounds(10, 11, 100, 40);
		contentPane.add(btnAdd);

		// Edit Button - Edit the task
		JButton btnEdit = new JButton("Edit");
		// Clicking the Mouse causes a dialog box to open
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (table.getSelectedRow() != -1) {
					editTaskGui();
				} else
					JOptionPane.showMessageDialog(null, "No Task Selected", "Edit Window", JOptionPane.WARNING_MESSAGE);
			}
		});
		btnEdit.setBounds(120, 11, 100, 40);
		contentPane.add(btnEdit);

		// Done Button - Marks the task as done
		JButton btnMarkDone = new JButton("Done");
		btnMarkDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (table.getSelectedRow() != -1) {
					int currentRow = table.convertRowIndexToModel(table.getSelectedRow());
					if (dtm.getValueAt(currentRow, 0).equals(true)) {
						dtm.setValueAt(false, currentRow, 0);
						// Database markNotDone correctly working
						tdq.markDone(currentRow + 1);
					} else {
						dtm.setValueAt(true, currentRow, 0);
						// Database markDone correctly working
						tdq.markDone(currentRow + 1);
					}
				} else
					JOptionPane.showMessageDialog(null, "No Task Selected", "Done Window", JOptionPane.WARNING_MESSAGE);
			}
		});
		btnMarkDone.setBounds(230, 11, 100, 40);
		contentPane.add(btnMarkDone);

		// This makes sure that the GTable is scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 565, 350);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		// Set Widths for the columns
		table.getColumnModel().getColumn(0).setMaxWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(0);
		table.getColumnModel().getColumn(5).setPreferredWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.removeColumn(table.getColumnModel().getColumn(5));
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.setForeground(Color.orange);
		table.setBackground(Color.GRAY);
	}

	public void addTaskGui() {

		final JFrame frameAdd = new JFrame("Add Task");
		frameAdd.setSize(500, 300);
		frameAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAdd.setVisible(true);

		// ==================== Creating all necessary objects ==============
		setLayout(null);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		frameAdd.add(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 48, 394, 35);
		contentPane.add(scrollPane);

		textField = new JTextField();
		scrollPane.setViewportView(textField);
		textField.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(31, 127, 394, 35);
		contentPane.add(scrollPane_1);

		textField_1 = new JTextField();
		scrollPane_1.setViewportView(textField_1);
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// add using the ENTER button
					dtm.addRow(new Object[] { false, textField.getText(), textField_1.getText(), "05-16-2016" });
					// calling function to save into database from GUI
					tdq.addTask(textField.getText(), textField_1.getText());
					// added to Database
					frameAdd.dispose();
				}

			}
		});
		textField_1.setColumns(10);

		JLabel lblAddTask = new JLabel("Add Task");
		lblAddTask.setBounds(31, 11, 88, 26);
		lblAddTask.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(lblAddTask);

		JLabel lblAddCategory = new JLabel("Add Category");
		lblAddCategory.setBounds(31, 84, 138, 32);
		lblAddCategory.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(lblAddCategory);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(80, 180, 107, 35);
		contentPane.add(btnCancel);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(260, 180, 107, 35);
		contentPane.add(btnSave);

		// Cancel button's action
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameAdd.dispose();
			}
		});

		// Save button's action
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// add using clicking the Add button
				dtm.addRow(new Object[] { false, textField.getText(), textField_1.getText(), "05-22-2016" });
				// call function to save into database from GUI
				tdq.addTask(textField.getText(), textField_1.getText());
				// data added to Database
				frameAdd.dispose();
			}
		});

	}

	public void editTaskGui() {
		final int currentRow = table.convertRowIndexToModel(table.getSelectedRow());
		final JFrame frameEdit = new JFrame("Edit Task");
		frameEdit.setSize(480, 150);
		frameEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEdit.setVisible(true);
		String Task = (String) table.getValueAt(currentRow, 1);

		// =================== Creating all necessary objects ==============
		setLayout(null);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		frameEdit.add(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 48, 394, 35);
		contentPane.add(scrollPane);

		textField = new JTextField(Task);
		scrollPane.setViewportView(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Edit Task in GUI
					dtm.setValueAt(textField.getText(), currentRow, 1);
					// Edit Task in Database from GUI
					tdq.editTask(currentRow + 1, textField.getText());
					frameEdit.dispose();
				}

			}
		});

		JLabel lblAddTask = new JLabel("Edit Task");
		lblAddTask.setBounds(31, 11, 88, 26);
		lblAddTask.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(lblAddTask);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(80, 90, 107, 35);
		contentPane.add(btnCancel);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(260, 90, 107, 35);
		contentPane.add(btnSave);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameEdit.dispose();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.setValueAt(textField.getText(), currentRow, 1);
				// calling function to edit into database
				tdq.editTask(currentRow + 1, textField.getText());
				frameEdit.dispose();
			}
		});
	}

	public final void addData(ArrayList<ToDo> data) {
		for (ToDo item : data) {

			boolean isDone = item.isTaskDone();
			String task = item.getTask();
			String category = item.getCategory();
			Date date = item.getDateAdded();

			Object[] toAdd = { isDone, task, category, date };

			// System.out.println("data added");
			dtm.addRow(toAdd);
		}
	}

	public DefaultTableModel getModel() {
		return dtm;
	}

}