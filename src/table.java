import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollBar;


public class table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//Run the program
					table frame = new table();
					frame.setVisible(true);
					frame.setTitle("ToDo List");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public table() {
		//Make Sure it Exits on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set size of the new window
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GUIproj window = new GUIproj();
		//ContentPane for our table
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Declared a Table, Hardcoded the columnNames and the data inside
		/**Your Job is to pull the data from the database and 
		 * put it into the table*/
		String[] columnNames = { "[  ]", "Task", "Category", "Date"};
		
		
		//=================================================================
		
		String connectionString = "jdbc:hsqldb:testdb,sa,";
		Connection connection = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		// Run SQL queries
		Statement s;
		String sql;
		ResultSet rs;
		String Task;
		
		// DROP TABLE
		sql = "DROP TABLE TODO;";
		try {
			s = connection.createStatement();
			ResultSet rsNew = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// CREATE TABLE
//		sql = "CREATE TABLE CONTACTS(LNAME VARCHAR(24), FNAME VARCHAR(24), AGE INT);";
//		try {
//			s = connection.createStatement();
//			ResultSet rs = s.executeQuery(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		sql = "CREATE TABLE TODO(ID INT, Task VARCHAR(255), Done BOOLEAN NOT NULL );";
	      
		try {
			s = connection.createStatement();
			rs = s.executeQuery(sql);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		// INSERT INTO table
		try {
			sql = "INSERT INTO TODO VALUES(1, 'Homework Assignment', true);";
			s = connection.createStatement();
			ResultSet rsNew = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// INSERT INTO table (more)
		
		try {
			sql = "INSERT INTO TODO VALUES(2, 'Groceries', false);";
			s = connection.createStatement();
			ResultSet rsNew = s.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		
//		try {
//			sql = "INSERT INTO TODO VALUES(3, false, 'Chores');";
//			s = connection.createStatement();
//			ResultSet rsNew = s.executeQuery(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		// SELECT FROM TABLE
		try {
			sql = "SELECT * FROM TODO ORDER BY Done DESC;";
			s = connection.createStatement();
			ResultSet rsNew = s.executeQuery(sql);
			while (rsNew.next()){
				System.out.println(rsNew.getString(2));
				Task = rsNew.getString(2);
				System.out.println("Task saved = " + rsNew.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// DELETE FROM table
//		try {
//			sql = "DELETE FROM CONTACTS WHERE LNAME='Jagger';";
//			s = connection.createStatement();
//			ResultSet rsNew = s.executeQuery(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		// Close database connection
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		//=================================================================
		
		Object[][] data = {
				{true, , "School", "6-05-2016"},
//				{true, "Doctor's Appointment", "Doctor","6-05-2016"},
//				{false, "Meeting", "Work","6-10-2016"},
//				{false, "Meeting", "Work","6-06-2016"},
//				{true, "Meeting", "Work","6-15-2016"},
//				{true, "Meeting", "Work","6-21-2016"},
//				{false, "Doctor's Appointment", "Doctor","7-05-2016"},
//				{false, "Doctor's Appointment", "Doctor","8-05-2016"},
//				{true, "Homework Assignment", "School","9-15-2016"},
//				{false, "Homework Assignment", "School","10-13-2016"},
//				{true, "Homework Assignment", "School","6-05-2016"}
		};
		
		//Declare Table
		JTable table;
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames)
			{
				public Class<?> getColumnClass(int column){
					
					switch(column)
					{
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
		table = new JTable(dtm){
			//Got this code from Stackoverflow to make the table data non-editable
			private static final long serialVersionUID = 1L;
			
			//This function makes only the first column editable
			//The rest of the columns are not editable. 
		     public boolean isCellEditable(int row, int column) {

		                return false;
		    	 	
		        };
		};
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		
		//Add Button - add the task
		JButton btnAdd = new JButton("Add");
		//Clicking the Mouse causes a dialog box to open
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dtm.addRow(new Object[]{true,"ClassWork","School","01-21-2017"});
				window.addTaskGui();
				//JOptionPane.showMessageDialog(null, "Task Added", "Add Window", JOptionPane.INFORMATION_MESSAGE);	

				/** TODO:
				 * Use Add Window to add the to table
				 * Add row to the database
				 * */
				
				
			}
		});
		btnAdd.setBounds(10, 11, 89, 23);
		contentPane.add(btnAdd);
		
		//Delete Button - Delete the task
		JButton btnDelete = new JButton("Delete");
		//Clicking the Mouse causes a dialog box to open
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() != -1){
					dtm.removeRow(table.getSelectedRow());
				}
				JOptionPane.showMessageDialog(null, "Task Deleted", "Delete Message", JOptionPane.INFORMATION_MESSAGE);
				
				/** TODO:
				 * Also Remove Selected Row From Database
				 */
			}
		});
		btnDelete.setBounds(109, 11, 89, 23);
		contentPane.add(btnDelete);
		
		//Edit Button - Edit the task
		JButton btnEdit = new JButton("Edit");
		//Clicking the Mouse causes a dialog box to open
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Edit Task", "Edit Window", JOptionPane.INFORMATION_MESSAGE);
				window.editTaskGui();

				/** TODO:
				 * Edit the table
				 * Also Remove Selected Row From Database
				 */
			}
		});
		btnEdit.setBounds(208, 11, 89, 23);
		contentPane.add(btnEdit);
		
		//Done Button - Marks the task as done
		JButton btnMarkDone = new JButton("Done");
		btnMarkDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table.getSelectedRow();
				if(dtm.getValueAt(i,0).equals(true))
					dtm.setValueAt(false,i,0);
				else
					dtm.setValueAt(true, i, 0);
					
				/** TODO:
				 * Edit the boolean value of done
				 */
			}
		});
		btnMarkDone.setBounds(307, 11, 89, 23);
		contentPane.add(btnMarkDone);
		
		//This makes sure that the table is scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 414, 153);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		//Set Widths for the columns
		table.getColumnModel().getColumn(0).setMaxWidth(6);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
	
	}
}
