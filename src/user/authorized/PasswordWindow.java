package user.authorized;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import Logic.DB;
import Logic.User;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordWindow {

	private JFrame frame;
	private static User currentUser;
	
	public static void start(User currentUser) {
		PasswordWindow.currentUser = currentUser;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordWindow window = new PasswordWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PasswordWindow() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame("Change password");
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);
		
		JLabel label1 = new JLabel("OLD PASSWORD: ");
		label1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		label1.setBounds(26, 41, 184, 28);
		frame.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("NEW PASSWORD: ");
		label2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		label2.setBounds(26, 91, 184, 19);
		frame.getContentPane().add(label2);
		
		JPasswordField field1 = new JPasswordField();
		field1.setBounds(198, 43, 201, 31);
		frame.getContentPane().add(field1);
		
		JPasswordField field2 = new JPasswordField();
		field2.setBounds(198, 79, 201, 31);
		frame.getContentPane().add(field2);
		frame.setBounds(100, 100, 452, 270);
		
		JButton button1 = new JButton("DONE");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					String oldPassword = field1.getText();
					oldPassword = oldPassword.trim();
					String newPassword = field2.getText();
					newPassword = newPassword.trim();
					
					if(oldPassword.isEmpty() || newPassword.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Complete all the fields!");
					}
					else if(oldPassword.matches(currentUser.getPassword())==false)
					{
						JOptionPane.showMessageDialog(null, "Old password is wrong!");
					}
					else
					{
						DB db = new DB();
						db.changePassword(currentUser, newPassword);
						frame.dispose();
						JOptionPane.showMessageDialog(null, "Operation successfully completed.");
					}
					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error!");
				}
			}
		});
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		button1.setBounds(23, 127, 376, 42);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		button2.setBounds(23, 179, 376, 41);
		frame.getContentPane().add(button2);
		
		
		
		
	}
}
