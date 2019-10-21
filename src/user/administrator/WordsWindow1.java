package user.administrator;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Logic.Category;
import Logic.DB;
import Logic.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordsWindow1 {

	private JFrame frame;
	private static Category category;
	
	public static void start(Category category) {
		if(category!=null)
			WordsWindow1.category = category;
		else
			WordsWindow1.category = new Category(-1,"Words");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordsWindow1 window = new WordsWindow1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordsWindow1() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame(category.getName());
		frame.setBounds(100, 100, 270, 610);
		
		JButton button = new JButton("BACK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
			}
		});
		frame.getContentPane().setLayout(null);
		button.setBounds(73, 529, 100, 20);
		frame.getContentPane().add(button);
		
		DB db = new DB();
		HashMap<String, Integer> map = null;
		if(category.getID()==-1)
		{
			map = db.getAllWordsWithTotalViews();
		}
		else
		{
			map = db.getWordsWithTotalViews(category);
		}
		Object[] columns = {"ID","Word","Views"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		
		Object[] objects = new Object[3];
	    Set<Entry<String, Integer>> values = map.entrySet();
	    for(Map.Entry<String, Integer> me:values)
	    {
	    	objects[0] = db.getIDFromWord(me.getKey());
	    	objects[1] = me.getKey();
	    	objects[2] = me.getValue();
	    	model.addRow(objects);
	    }
	    
	    
	    JTable table = new JTable();
	    table.setSize(284, 190);
	    table.setLocation(100, 200);
	    table.setModel(model);
	    table.setVisible(true);
	    table.setFont(new Font("Comic Sans MS",1,18));
	    table.setRowHeight(35);
	    table.setBackground(Color.green);
	    
	    JScrollPane scroll = new JScrollPane();
		scroll.setBounds(-1, -1, 259, 508);
		scroll.setViewportView(table);
		scroll.setVisible(true);
		frame.getContentPane().add(scroll);
	    
	}
}






