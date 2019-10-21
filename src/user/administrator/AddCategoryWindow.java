package user.administrator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTextField;

import Logic.DB;
import Logic.UniqueException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddCategoryWindow {

	public JFrame frame;
	private JTextField field;
	private static String name;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCategoryWindow window = new AddCategoryWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public AddCategoryWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Add category");
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 314, 160);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NAME:");
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		label.setBounds(30, 11, 91, 45);
		frame.getContentPane().add(label);
		
		
		field = new JTextField();
		field.setBounds(106, 23, 171, 28);
		frame.getContentPane().add(field);
		field.setColumns(10);
		
		JButton button1 = new JButton("DONE");
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				name = field.getText();
				name = name.trim();
				if(name.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Complete field!");
				}
				else
				{
					try {
							DB db = new DB();
							db.addCategory(name);
							frame.dispose();
							AdminWindow.start();
							JOptionPane.showMessageDialog(null, "Operation successfully completed!");
						}
					catch(UniqueException e)
					{
						JOptionPane.showMessageDialog(null, "The category already exist!");
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error!");
					}
				}
			}
		});
		button1.setBounds(30, 66, 118, 24);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AdminWindow.start();
			}
		});
		button2.setBounds(158, 67, 132, 23);
		frame.getContentPane().add(button2);
		
		
	}
}
