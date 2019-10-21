package user.administrator;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Logic.DB;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteWordWindow {

	private JFrame frame;

	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteWordWindow window = new DeleteWordWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public DeleteWordWindow() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame("Delete word");
		frame.setBounds(100, 100, 445, 300);
		
		DB db = new DB();
		HashMap<String, Integer> map = db.getAllWordsWithTotalViews();
		
		Object[] columns = {" ID ","   Word   "};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		Object[] objects = new Object[2];
	    Set<Entry<String, Integer>> values = map.entrySet();
	    for(Map.Entry<String, Integer> me:values)
	    {
	    	objects[0] = db.getIDFromWord(me.getKey());
	    	objects[1] = me.getKey();
	    	model.addRow(objects);
	    }
	    
	    
	    JTable table = new JTable();
	    table.setSize(284, 190);
	    table.setLocation(100, 200);
	    table.setModel(model);
	    table.setVisible(true);
	    table.setFont(new Font("Comic Sans MS",1,14));
	    table.setRowHeight(35);
	    
	    JScrollPane scroll = new JScrollPane();
		scroll.setBounds(244, 0, 188, 265);
		scroll.setViewportView(table);
		scroll.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(scroll);
		
		JLabel label1 = new JLabel("ID:");
		label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label1.setBounds(27, 23, 62, 35);
		frame.getContentPane().add(label1);
		
		JTextField field1 = new JTextField();
		field1.setBounds(68, 34, 144, 19);
		frame.getContentPane().add(field1);
		field1.setColumns(10);
		
		JButton button1 = new JButton("Done");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String id = field1.getText();
					id = id.trim();
					
					if(id.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Complete the field!");
					}
					else
					{
						DB db = new DB();
						db.deleteWord(Integer.parseInt(id));
						frame.dispose();
						AdminWindow.start();
						JOptionPane.showMessageDialog(null, "Operation successfully completed!");
					}
				} catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, "Error!");
				}
			}
		});
		button1.setBounds(27, 85, 185, 19);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("Back");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AdminWindow.start();
			}
		});
		button2.setBounds(27, 124, 185, 19);
		frame.getContentPane().add(button2);
		
	}
}
