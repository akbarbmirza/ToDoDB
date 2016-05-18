import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;

public class GTable extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	public void runTable() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//Run the program
					GTable frame = new GTable();
					frame.setVisible(true);
					frame.setTitle("ToDo List");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GTable() {
		//Make Sure it Exits on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set size of the new window
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		final GUIproj window = new GUIproj();
		//ContentPane for our GTable
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Declared a Table, Hardcoded the columnNames and the data inside
		/**Your Job is to pull the data from the database and 
		 * put it into the GTable*/
		String[] columnNames = { "[  ]", "Task", "Category", "Date"};
		
		Object[][] data = {
				{true, "Homework Assignment", "School", "6-05-2016"},
				{true, "Doctor's Appointment", "Doctor","6-05-2016"},
				{false, "Meeting", "Work","6-10-2016"},
				{false, "Meeting", "Work","6-06-2016"},
				{true, "Meeting", "Work","6-15-2016"},
				{true, "Meeting", "Work","6-21-2016"},
				{false, "Doctor's Appointment", "Doctor","7-05-2016"},
				{false, "Doctor's Appointment", "Doctor","8-05-2016"},
				{true, "Homework Assignment", "School","9-15-2016"},
				{false, "Homework Assignment", "School","10-13-2016"},
				{true, "Homework Assignment", "School","6-05-2016"},
		};
		
		//Declare Table
		final JTable table;
		final DefaultTableModel dtm = new DefaultTableModel(data,columnNames)
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
			//Got this code from Stackoverflow to make the GTable data non-editable
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
				 * Use Add Window to add the to GTable
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
				 * Edit the GTable
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
		
		//This makes sure that the GTable is scrollable
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
