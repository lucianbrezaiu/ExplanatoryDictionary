package user.administrator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import Logic.Category;
import Logic.DB;
import Logic.User;
import Logic.State;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import GUI.FirstWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class AdminWindow {

	private JFrame frame;
	private ArrayList<User> users;
	private User selectedAccount;
	private DefaultListModel<User> model1,model2;
	private JList<User> jList1, jList2;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminWindow() {
		DB db = new DB();
		users = db.getUsers();
		selectedAccount = null;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Administrator menu");
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 938, 530);
		
		DB db = new DB();
		
		JLabel label = new JLabel("Welcome back, " + db.getAdministrator().getUsername() + "!");
		label.setBounds(20, 13, 194, 28);
		frame.getContentPane().add(label);
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		
		categories();
		words();
		authorizeUsers();
		cancelUser();
		listUnauthorizedUsers();
		listAuthorizedUsers();
		dictionary();
		tools();
		
		
	}
	
	private void categories()
	{
		JButton button1 = new JButton("Add category");
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddCategoryWindow.start();
				frame.dispose();
			}
		});
		button1.setBounds(170, 350, 138, 39);
		frame.getContentPane().add(button1);
		
		JButton button2 = new JButton("Modify category");
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ModifyCategoryWindow.start();
				frame.dispose();
			}
		});
		button2.setBounds(318, 350, 135, 39);
		frame.getContentPane().add(button2);
		
		JButton button3 = new JButton("Delete category");
		button3.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeleteCategoryWindow.start();
				frame.dispose();
			}
		});
		button3.setBounds(463, 350, 138, 39);
		frame.getContentPane().add(button3);
		
		JLabel label = new JLabel("EDIT CATEGORIES:");
		label.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(20, 354, 171, 28);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.ORANGE);
		panel.setBounds(10, 337, 606, 63);
		frame.getContentPane().add(panel);
		
	}
	
	private void words()
	{
		JButton button1 = new JButton("Add word");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddWordWindow.start();
				frame.dispose();
			}
		});
		button1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		button1.setBounds(170, 424, 138, 39);
		frame.getContentPane().add(button1);
		
		
		JButton button2 = new JButton("Modify word");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ModifyWordWindow.start();
			}
		});
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		button2.setBounds(318, 424, 135, 39);
		frame.getContentPane().add(button2);
		
		
		JButton button3 = new JButton("Delete word");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeleteWordWindow.start();
				frame.dispose();
			}
		});
		button3.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		button3.setBounds(463, 424, 138, 39);
		frame.getContentPane().add(button3);
		
		JLabel label = new JLabel("EDIT WORDS:");
		label.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(20, 435, 140, 14);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.YELLOW);
		panel.setBounds(10, 410, 606, 63);
		frame.getContentPane().add(panel);
	}
	
	private void authorizeUsers()
	{
		JButton button = new JButton("\u25BA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(selectedAccount != null)
				{
					if(selectedAccount.getState()==State.unauthorized)
					{
						DB db = new DB();
						
						db.changeUserStatus(selectedAccount,"authorized");
						for(Object user : model1.toArray())
							if(selectedAccount.getUsername().matches(((User)user).getUsername()))
							{
								model1.removeElement(user);
								break;
							}
							
						
						selectedAccount.setState(State.authorized);
						model2.addElement(selectedAccount);
						
						selectedAccount = null;
					}
				}
				
			}
		});
		button.setBounds(20, 284, 274, 23);
		frame.getContentPane().add(button);
		
	}
	
	private void cancelUser()
	{
		JButton btnNewButton = new JButton("\u25C4");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(selectedAccount != null)
				{
					if(selectedAccount.getState()==State.authorized)
					{
						DB db = new DB();
						
						db.changeUserStatus(selectedAccount,"unauthorized");
						for(Object user : model2.toArray())
							if(selectedAccount.getUsername().matches(((User)user).getUsername()))
							{
								model2.removeElement(user);
								break;
							}
							
						
						selectedAccount.setState(State.unauthorized);
						model1.addElement(selectedAccount);
						
						selectedAccount = null;
					}
				}
				
			}
		});
		btnNewButton.setBounds(327, 284, 274, 23);
		frame.getContentPane().add(btnNewButton);
		
	}
	
	private void listUnauthorizedUsers()
	{
		model1 = new DefaultListModel<>();
		for(User user : users)
			if(user.getState()==State.unauthorized)
				model1.addElement(user);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("UNAUTHORIZED accounts");
		label.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 17));
		label.setBounds(39, 57, 230, 27);
		frame.getContentPane().add(label);
		
		JScrollPane scroll1 = new JScrollPane();
		scroll1.setBounds(20, 90, 274, 170);
		frame.getContentPane().add(scroll1);
		
		
		jList1 = new JList<>();
		jList1.setBackground(Color.YELLOW);
		scroll1.setViewportView(jList1);
		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(jList1.getSelectedValue() != null)
					selectedAccount = new User(jList1.getSelectedValue().getID(),jList1.getSelectedValue().getUsername(),
																		jList1.getSelectedValue().getPassword(),jList1.getSelectedValue().getState());
			}
		});
		jList1.setBorder(new LineBorder(new Color(0, 0, 0)));
		jList1.setModel(model1);
		jList1.setVisible(true);
	}
	
	private void listAuthorizedUsers()
	{
		model2 = new DefaultListModel<>();
		for(User user : users)
			if(user.getState()==State.authorized)
				model2.addElement(user);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("AUTHORIZED accounts");
		label.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 17));
		label.setBounds(338, 62, 246, 18);
		frame.getContentPane().add(label);
		
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setBounds(325, 90, 276, 170);
		frame.getContentPane().add(scroll2);
		
		
		jList2 = new JList<>();
		jList2.setBackground(Color.YELLOW);
		scroll2.setViewportView(jList2);
		jList2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(jList2.getSelectedValue() != null)
					selectedAccount = new User(jList2.getSelectedValue().getID(),jList2.getSelectedValue().getUsername(),
																		jList2.getSelectedValue().getPassword(),jList2.getSelectedValue().getState());
			}
		});
		jList2.setBorder(new LineBorder(new Color(0, 0, 0)));
		jList2.setModel(model2);
		jList2.setVisible(true);
	}

	private void dictionary()
	{
		DefaultListModel<String> model3 = new DefaultListModel<>();
		
		DB db = new DB();
		ArrayList<Category> array = db.getCategories();
		
		for(Category category : array)
		{
			int count = db.getNumberOfWordsFromCategory(category);
			
			model3.addElement(" " + category.getName() + " " + count);
			
		}
		JList<String> list = new JList<>();
		list.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(list.getSelectedValue() != null)
				{
					String[] array = list.getSelectedValue().split(" ");
					String title = array[1];
					
					ArrayList<Category> array2 = db.getCategories();
					Category aux = null;
					for(Category category : array2)
						if(category.getName().matches(title))
						{
							aux = category;
							break;
						}

					if(db.getNumberOfWordsFromCategory(aux)!=0)
						WordsWindow1.start(aux);
				}
				
			}
		});
		list.setBackground(Color.WHITE);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setModel(model3);
		list.setVisible(true);
		
		JScrollPane scroll3 = new JScrollPane();
		scroll3.setBounds(757, 52, 153, 229);
		frame.getContentPane().add(scroll3);
		scroll3.setViewportView(list);
		
	}

	private void tools()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.GREEN);
		panel.setBounds(10, 52, 606, 270);
		frame.getContentPane().add(panel);
		
		JLabel label2 = new JLabel("CATEGORIES:");
		label2.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		label2.setBounds(626, 54, 121, 29);
		frame.getContentPane().add(label2);
		
		JButton button = new JButton("DISPLAY ALL");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WordsWindow1.start(null);
				
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button.setBounds(757, 299, 153, 23);
		frame.getContentPane().add(button);
		
		JButton button2 = new JButton("SIGN OUT");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				FirstWindow.main(null);
			}
		});
		button2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		button2.setBounds(757, 337, 153, 136);
		frame.getContentPane().add(button2);
	}
}

















