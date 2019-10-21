package user.administrator;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import Logic.Category;
import Logic.DB;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyCategoryWindow {

	public JFrame frame;
	private static int id;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyCategoryWindow window = new ModifyCategoryWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ModifyCategoryWindow() {
		initialize();
		id = 0;
	}

	
	private void initialize() {
		frame = new JFrame("Modify category");
		frame.getContentPane().setBackground(Color.GREEN);
		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 45));
		frame.setBounds(100, 100, 260, 550);
		frame.getContentPane().setLayout(null);
		
		DefaultListModel<Category> model = new DefaultListModel<>();
		
		DB db = new DB();
		ArrayList<Category> array = db.getCategories();
		
		for(Category category : array)
		{
			model.addElement(category);
		}
		
		JLabel label = new JLabel("SELECT CATEGORY:");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		label.setBounds(10, 21, 377, 40);
		frame.getContentPane().add(label);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 81, 247, 260);
		frame.getContentPane().add(scroll);
		scroll.setVisible(true);
		
		JList<Category> list = new JList<>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedValue() != null)
				{
					id = list.getSelectedValue().getID();
				}
			}
		});
		scroll.setViewportView(list);
		list.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		list.setBackground(Color.WHITE);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setModel(model);
		
		JLabel label3 = new JLabel("NEW NAME:");
		label3.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		label3.setBounds(21, 351, 170, 31);
		frame.getContentPane().add(label3);
		
		JTextField field = new JTextField();
		field.setBounds(123, 359, 106, 20);
		frame.getContentPane().add(field);
		field.setColumns(10);
		list.setVisible(true);
		
		JButton button1 = new JButton("DONE");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String name = field.getText();
				name = name.trim();
				if(name.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Complete the field!");
				}
				else
				{
					try {
							if(id == 0)
								throw new Exception();
							DB db = new DB();
							db.modifyCategory(id,name);
							frame.dispose();
							AdminWindow.start();
							JOptionPane.showMessageDialog(null, "Operation successfully completed!");
						}
					catch (Exception e) 
					{
						JOptionPane.showMessageDialog(null, "Error!");
					}
				}
			}
		});
		button1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button1.setBounds(17, 392, 209, 23);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AdminWindow.start();
			}
		});
		button2.setBounds(17, 425, 209, 23);
		frame.getContentPane().add(button2);
	}
}
