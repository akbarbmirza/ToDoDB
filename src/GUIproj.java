import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;



import java.awt.Insets;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//import javax.awt.*;
import javax.swing.*; // import all from swing


public class GUIproj {
/*
	private JFrame frameAdd;
	private JFrame frameEdit;
	private JPanel panelAdd;
	private JPanel panelEdit;
	private JButton buttonAddSave;
	private JButton buttonAddCancel;
	private JButton buttonEdit;
	private JLabel labelAddTask;
	private JLabel labelAddCatagory;
	private JLabel labelEdit;
	private JTextArea textAddTask;
	private JTextField textAddCatagory;
	*/
	//***********************This should initialize from database********************
	private String Catagory = "This is catagory"; // Note: I initialize it to test.
	private String Task = "This is task"; // Note: I initialize it to test.
	//*******************************************************************************
	
	private JTextArea textAddTask; // for Add Task method
	private JTextField textAddCatagory; // for Add Task method
	JTextArea textEditTask; // for Edit Task method
	JTextField textEditCatagory; // for Edit Task method

	public static void main(String[] args) {
		GUIproj g = new GUIproj();
		g.addTaskGui();
		//g.editTaskGui();
	
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
				String strTaskOfAdd = textAddTask.getText(); // take input from task
				String strCatagoryOfAdd = textAddCatagory.getText(); // take input from Catagory
				
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
                
                //==============================Creating all necessary boject===============
                JPanel panelEdit = new JPanel(new GridBagLayout());
                //panelEdit.setBackground();             
                JLabel labelEditTask = new JLabel("Task: "); //Label 
                JLabel labelEditCatagory = new JLabel("Catagory: "); // Label
                JButton buttonEditCancel = new JButton("Cancel"); // creating object button of cancel
                JButton buttonEditSave = new JButton("Save"); // creating object button of Save
                textEditTask = new JTextArea(Task,5,20); // Creating object of JTextArea
                // Giving scroll bar when exceed the limit of size of text area window
                JScrollPane scrollPaneEdit = new JScrollPane(textEditTask,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                textEditCatagory = new JTextField(Catagory,20);
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
        				String strTaskOfEdit = textEditTask.getText(); // take input from task
        				String strCatagoryOfEdit = textEditCatagory.getText(); // take input from Catagory
        				
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
