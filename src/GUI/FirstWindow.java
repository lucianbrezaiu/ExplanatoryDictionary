package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Logic.DB;
import Logic.User;
import user.administrator.AdminWindow;
import user.authorized.UserWindow;
import user.unauthorized.CreateAccountWindow;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;

public class FirstWindow {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstWindow window = new FirstWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public FirstWindow() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame("Application");
		frame.getContentPane().setBackground(Color.GREEN);
		frame.setBounds(100, 100, 420, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label1 = new JLabel("USERNAME: ");
		label1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		label1.setBounds(40, 22, 134, 31);
		frame.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("PASSWORD: ");
		label2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		label2.setBounds(40, 63, 134, 22);
		frame.getContentPane().add(label2);
		
		JTextField field1 = new JTextField();
		field1.setBounds(184, 31, 178, 22);
		frame.getContentPane().add(field1);
		field1.setColumns(10);
		
		JPasswordField field2 = new JPasswordField();
		field2.setBounds(184, 69, 178, 19);
		frame.getContentPane().add(field2);
		
		JButton button1 = new JButton("CREATE ACCOUNT");
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button1.setBounds(40, 137, 322, 23);
		frame.getContentPane().add(button1);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateAccountWindow.start();
			}
			});
		
		JButton button2 = new JButton("LOG IN");
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				DB db = new DB();
				try
				{
					User user = db.login(field1.getText().trim(), field2.getText().trim());
					if(user!=null)
					{
						switch(user.getState())
						{
						case administrator:
							AdminWindow.start();
							frame.dispose();
							break;
						case authorized:
							UserWindow.start(user);
							frame.dispose();
							break;
						case unauthorized:
							JOptionPane.showMessageDialog(null, "Please wait for an administrator to authorize your account!");
							break;
						}
					}
					else
					{
						if(field1.getText().isEmpty() || field2.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Complete all the fields!");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Wrong username or password!");
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error!");
				}
			}
		});
		button2.setBounds(40, 104, 322, 23);
		frame.getContentPane().add(button2);
		
		JButton button3 = new JButton("EXIT");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button3.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button3.setBounds(40, 170, 322, 21);
		frame.getContentPane().add(button3);
					
	}
}
