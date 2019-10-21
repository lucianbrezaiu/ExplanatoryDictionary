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
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Logic.Category;
import Logic.DB;



public class DeleteCategoryWindow {

	public JFrame frame;
	private static int id;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteCategoryWindow window = new DeleteCategoryWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DeleteCategoryWindow() {
		initialize();
		id = 0;
	}

	
	private void initialize() {
		frame = new JFrame("Delete category");
		frame.getContentPane().setBackground(Color.GREEN);
		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 45));
		frame.setBounds(100, 100, 260, 490);
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
		label.setBounds(20, 11, 377, 40);
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
		
		JButton button1 = new JButton("DONE");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(id == 0)
						throw new Exception();
					DB db = new DB();
					db.deleteCategory(id);
					frame.dispose();
					AdminWindow.start();
					JOptionPane.showMessageDialog(null, "Operation successfully completed!");
				}
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, "Error!");
				}
				}
		});
		button1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button1.setBounds(20, 371, 211, 23);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("BACK");
		button2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AdminWindow.start();
			}
		});
		button2.setBounds(20, 404, 211, 23);
		frame.getContentPane().add(button2);
		
		
		
	}
}
