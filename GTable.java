import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JTable table;
	private JScrollPane scrollPane;
	private Object Catagory = "This is catagory"; // Note: I initialize it to test.
	private Object Task = "This is task"; // Note: I initialize it to test.
	//*******************************************************************************


	private JTextArea textAddTask; // for Add Task method
	private JTextField textAddCatagory; // for Add Task method
	JTextArea textEditTask; // for Edit Task method
	JTextField textEditCatagory; // for Edit Task method
	private DefaultTableModel dtm;


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
		setBounds(100, 100, 600, 500);
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
				
				
				addTaskGui();
				
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
				editTaskGui();

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
		scrollPane.setBounds(10, 45, 550, 300);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		//Set Widths for the columns
		table.getColumnModel().getColumn(0).setMaxWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
	
	}
	
	public void addTaskGui(){
		JFrame frameAdd = new JFrame("Add Task");
		
		frameAdd.setSize(400, 300);
		frameAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//==============================Creating all necessary boject===============
		JPanel panelAdd = new JPanel(new GridBagLayout());
		//panelAdd.setBackground();		
		JLabel labelAddTask = new JLabel("Task: "); //Label 
		JLabel labelAddCatagory = new JLabel("Catagory: "); // Label
		JButton buttonAddCancel = new JButton("Cancel"); // creating object button of cancel
		JButton buttonAddSave = new JButton("Save"); // creating object button of Save
		textAddTask = new JTextArea(5,20); // Creating object of JTextArea
		// Giving scroll bar when exceed the limit of size of text area window
		JScrollPane scrollPaneADD = new JScrollPane(textAddTask,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textAddCatagory = new JTextField(20);
		GridBagConstraints c = new GridBagConstraints(); // for arranging all buttons , labels and text field
		c.insets = new Insets(10,10,10,10); // Putting space among all field
		//===============================End of creating obj========================
	
		//**************************Button Functionality Defined*****************************
		// Cancel button's action
		buttonAddCancel.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	//++++++++++++++++++++NEW+++++++++++++++++++++++++++++++++++
		    	frameAdd.dispose();
		        //System.exit(0);
		    }
		});
		
		// Save button's action
		buttonAddSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dtm.addRow(new Object[]{false,textAddTask.getText(),textAddCatagory.getText(),"05-16-2016"});
				// TODO: call function to save into database
				frameAdd.dispose();
				//System.exit(0); // this will close the window after call function from TODO
				
			}
		});
		
		//************************Button Functionality Defined End****************************
		
		
		//==============================Task Label=================================
		c.gridx = 0; // at col 0
		c.gridy = 0; // at row 1
		panelAdd.add(labelAddTask);		
		//=========================================================================
		

		//==============================Text area field============================
		c.gridx = 0;
		c.gridy = 1;
		panelAdd.add(scrollPaneADD, c);		
		//===============================================================
		
		//==============================Catagory Label================================
		c.gridx = 0;
		c.gridy = 2;
		panelAdd.add(labelAddCatagory, c);		
		//============================================================================
		
		//==============================Catagory TextField================================
		c.gridx = 0;
		c.gridy = 3;
		panelAdd.add(textAddCatagory, c);		
		//============================================================================
				
		//===============================Cancel Button================================
		c.gridx = 0;
		c.gridy = 5;
		panelAdd.add(buttonAddCancel, c);		
		//============================================================================
		
		//================================Save Button=================================
		c.gridx = 1;
		c.gridy = 5;
		panelAdd.add(buttonAddSave, c);		
		//============================================================================

		
		//frameAdd.add(panelAdd, BorderLayout.WEST); // On the Left of the frame
		frameAdd.add(panelAdd, BorderLayout.NORTH);// on the Top of the frame
		frameAdd.setVisible(true);
		
	}

public void editTaskGui(){
		
        JFrame frameEdit = new JFrame("Edit Task");
                //frameEdit.setVisible(true);
                frameEdit.setSize(400, 300);
                
                //++++++++++++++++++++NEW+++++++++++++++++++++++++++++++++++
                frameEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                if(table.getSelectedRow() != -1)
                	{Task = table.getValueAt(table.getSelectedRow(), 1);
                	 Catagory = table.getValueAt(table.getSelectedRow(), 2);
                	}
                //==============================Creating all necessary boject===============
                JPanel panelEdit = new JPanel(new GridBagLayout());
                //panelEdit.setBackground();             
                JLabel labelEditTask = new JLabel("Task: "); //Label 
                JLabel labelEditCatagory = new JLabel("Catagory: "); // Label
                JButton buttonEditCancel = new JButton("Cancel"); // creating object button of cancel
                JButton buttonEditSave = new JButton("Save"); // creating object button of Save
                textEditTask = new JTextArea((String) Task,5,20); // Creating object of JTextArea
                // Giving scroll bar when exceed the limit of size of text area window
                JScrollPane scrollPaneEdit = new JScrollPane(textEditTask,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                textEditCatagory = new JTextField((String)Catagory,20);
                GridBagConstraints c = new GridBagConstraints(); // for arranging all buttons , labels and text field
                c.insets = new Insets(10,10,10,10); // Putting space among all field
                //===============================End of creating obj========================
                
                

        		//**************************Button Functionality Defined*****************************
        		
        		buttonEditCancel.addActionListener(new ActionListener() {
        		    public void actionPerformed(ActionEvent e)
        		    {
        		    	//++++++++++++++++++++NEW+++++++++++++++++++++++++++++++++++
        		    	frameEdit.dispose();
        		       // System.exit(0);
        		    }
        		});
        		
        		buttonEditSave.addActionListener(new ActionListener() {
        			
        			public void actionPerformed(ActionEvent e) {
        				
        				dtm.setValueAt(textEditTask.getText(),table.getSelectedRow(),1);
        				dtm.setValueAt(textEditCatagory.getText() , table.getSelectedRow(),2);
        				// TODO: call function to save into database
        				
        				//++++++++++++++++++++NEW+++++++++++++++++++++++++++++++++++
        				frameEdit.dispose();
        				//System.exit(0); // this will close the window after call function from TODO
        			}
        		});
        		
        		//************************Button Functionality Defined End****************************
        		
                //==============================Task Label=================================
                c.gridx = 0; // at col 0
                c.gridy = 0; // at row 1
                panelEdit.add(labelEditTask);             
                //=========================================================================
                

                //==============================Text area field============================
                c.gridx = 0;
                c.gridy = 1;
                panelEdit.add(scrollPaneEdit, c);         
                //===============================================================
                
                //==============================Catagory Label================================
                c.gridx = 0;
                c.gridy = 2;
                panelEdit.add(labelEditCatagory, c);              
                //============================================================================
                
                //==============================Catagory TextField================================
                c.gridx = 0;
                c.gridy = 3;
                panelEdit.add(textEditCatagory, c);               
                //============================================================================
                                
                //===============================Cancel Button================================
                c.gridx = 0;
                c.gridy = 5;
                panelEdit.add(buttonEditCancel, c);               
                //============================================================================
                
                //================================Save Button=================================
                c.gridx = 1;
                c.gridy = 5;
                panelEdit.add(buttonEditSave, c);         
                //============================================================================

                
                //frameEdit.add(panelEdit, BorderLayout.WEST); // On the Left of the frame
                frameEdit.add(panelEdit, BorderLayout.NORTH);// on the Top of the frame
                frameEdit.setVisible(true);
	}
	
}
