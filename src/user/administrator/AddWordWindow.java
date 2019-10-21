package user.administrator;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Logic.Category;
import Logic.DB;
import Logic.UniqueException;

public class AddWordWindow {

	private JFrame frame;
	private static int id;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddWordWindow window = new AddWordWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public AddWordWindow() {
		initialize();
		id = 0;
	}

	
	private void initialize() {
		frame = new JFrame("Add word");
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 45));
		frame.setBounds(100, 100, 268, 612);
		frame.getContentPane().setLayout(null);
		
		DefaultListModel<Category> model = new DefaultListModel<>();
		
		DB db = new DB();
		ArrayList<Category> array = db.getCategories();
		
		for(Category category : array)
		{
			model.addElement(category);
		}
		
		JLabel label = new JLabel("CATEGORY:");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		label.setBounds(20, 11, 377, 40);
		frame.getContentPane().add(label);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 53, 255, 260);
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
		
		JLabel label3 = new JLabel("NAME:");
		label3.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label3.setBounds(20, 330, 202, 40);
		frame.getContentPane().add(label3);
		
		JLabel label4 = new JLabel("DESCRIPTION:");
		label4.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label4.setBounds(20, 363, 202, 40);
		frame.getContentPane().add(label4);
		
		JTextField field1 = new JTextField();
		field1.setBounds(20, 413, 214, 63);
		frame.getContentPane().add(field1);
		field1.setColumns(10);
		
		JTextField field2 = new JTextField();
		field2.setBounds(112, 344, 129, 20);
		frame.getContentPane().add(field2);
		field2.setColumns(10);
		
		JButton button1 = new JButton("DONE");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String name = field2.getText();
				name = name.trim();
				String description = field1.getText();
				description = description.trim();
				if(name.isEmpty() || description.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Complete all the fields!");
				}
				else
				{
					try {
							if(id == 0)
								throw new Exception();
							DB db = new DB();
							db.addWord(id,name,description);
							frame.dispose();
							AdminWindow.start();
							JOptionPane.showMessageDialog(null, "Operation successfully completed!");
						}
					catch(UniqueException e)
					{
						JOptionPane.showMessageDialog(null, "The word already exist!");
					}
					catch (Exception e) 
					{
						JOptionPane.showMessageDialog(null, "Error!");
					}
				}
			}
		});
		button1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button1.setBounds(20, 499, 214, 23);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AdminWindow.start();
			}
		});
		button2.setBounds(20, 532, 214, 23);
		frame.getContentPane().add(button2);
		
	}

}
