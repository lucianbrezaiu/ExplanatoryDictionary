package user.unauthorized;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Logic.DB;
import Logic.UniqueException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;

public class CreateAccountWindow {

	private JFrame frame;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccountWindow window = new CreateAccountWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public CreateAccountWindow() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame("Create account");
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(150, 150, 410, 270);
		frame.getContentPane().setLayout(null);
		
		JLabel label1 = new JLabel("USERNAME:");
		label1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		label1.setBounds(33, 33, 128, 22);
		frame.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("PASSWORD:");
		label2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		label2.setBounds(33, 67, 128, 22);
		frame.getContentPane().add(label2);
		
		JTextField field1 = new JTextField();
		field1.setBounds(166, 39, 194, 20);
		frame.getContentPane().add(field1);
		field1.setColumns(10);
		
		JPasswordField field2 = new JPasswordField();
		field2.setBounds(166, 73, 194, 19);
		frame.getContentPane().add(field2);
				
		JButton button1 = new JButton("SIGN UP");
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					DB db = new DB();
					
					db.addUser(field1.getText().trim(), field2.getText().trim());
					
					frame.dispose();
					
					JOptionPane.showMessageDialog(null, "Operation successfully completed!");
				}
				catch(UniqueException e)
				{
					JOptionPane.showMessageDialog(null, "The username already exist!!");
					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Complete all fields!");
				}
			}
		});
		button1.setBounds(33, 121, 327, 23);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		button2.setBounds(33, 159, 327, 23);
		frame.getContentPane().add(button2);
		
	}

}
