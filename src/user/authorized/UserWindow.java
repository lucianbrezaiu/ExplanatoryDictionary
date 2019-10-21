package user.authorized;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.NavigableMap;

import javax.management.modelmbean.ModelMBean;
import javax.naming.directory.SearchControls;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import GUI.FirstWindow;
import Logic.Category;
import Logic.DB;
import Logic.User;
import Logic.Word;
import user.administrator.WordsWindow1;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class UserWindow {

	private JFrame frame;
	private static User user;
	private static String word;
	
	public static void start(User user) {
		UserWindow.user = user;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserWindow window = new UserWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("User menu");
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 604, 468);
		
		changePassword();
		categories();
		words();
		search();
		disconnection();
	}

	private void changePassword() 
	{
		JButton btnNewButton = new JButton("CHANGE PASSWORD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PasswordWindow.start(user);
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		btnNewButton.setBounds(85, 387, 166, 27);
		frame.getContentPane().add(btnNewButton);
		
	}
	
	private void categories()
	{
		DefaultListModel<String> model = new DefaultListModel<>();
		
		DB db = new DB();
		ArrayList<Category> array = db.getCategories();
		
		for(Category category : array)
		{
			int count = db.getNumberOfWordsFromCategory(category);
			
			model.addElement(" " + category.getName() + " " + count);
			
		}
		frame.getContentPane().setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(420, 52, 168, 377);
		frame.getContentPane().add(scroll);
		
		JList<String> list = new JList<>();
		scroll.setViewportView(list);
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
						WordsWindow2.start(aux);
				}
				
			}
		});
		list.setBackground(Color.YELLOW);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setModel(model);
		list.setVisible(true);
		
	}

	private void words()
	{
		DB db = new DB();
		ArrayList<Word> list = db.getWords();
		TreeSet<String> letters = new TreeSet<String>();
		DefaultListModel<String> model = new DefaultListModel<>();
		
		for(Word word : list)
		{
			String string = word.getName().charAt(0)+"";
			if(model.indexOf(string.toUpperCase())==-1)
			{
				letters.add(string.toUpperCase());
			}
		}
		for(String letter : letters)
			model.addElement(letter);
		
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setBounds(0, 0, 75, 429);
		frame.getContentPane().add(scroll2);
		
		JList<String> jList = new JList<>();
		jList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(jList.getSelectedValue() != null)
				{
					WordsWindow3.start(jList.getSelectedValue());
					
				}
			}
		});
		jList.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		scroll2.setViewportView(jList);
		jList.setBackground(Color.YELLOW);
		jList.setBorder(new LineBorder(new Color(0, 0, 0)));
		jList.setModel(model);
		jList.setVisible(true);
		
		JLabel label = new JLabel("CATEGORIES");
		label.setBounds(453, 19, 106, 23);
		frame.getContentPane().add(label);
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		
	}

	private void search()
	{
		JList<String> list = new JList<>();
		JScrollPane scr = new JScrollPane();
		JTextField field = new JTextField();
		field.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				
				list.setVisible(true);
				scr.setVisible(true);
				DefaultListModel<String> model = new DefaultListModel<>();
				
				word = field.getText();
				
				DB db = new DB();
				TreeMap<Integer, String> map = db.getWordsWithViewsLikeSthFromUser(user, word);
				
				NavigableMap<Integer, String> nmap = map.descendingMap();
				
				Set<Entry<Integer, String>> values = nmap.entrySet();
				for(Map.Entry<Integer, String> me:values)
				{
					String word = me.getValue() + ": " + me.getKey();
					model.addElement(word);
				}

				
				scr.setBounds(99, 43, 182, 100);
				frame.getContentPane().add(scr);
				
			
				scr.setViewportView(list);
				list.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
				list.setBackground(Color.WHITE);
				list.setBorder(new LineBorder(new Color(0, 0, 0)));
				list.setModel(model);
				list.setVisible(true);
				list.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if(list.getSelectedValue() != null)
						{
							String string= list.getSelectedValue();
							String[] array = string.split(":");
							field.setText(array[0]);
							list.setVisible(false);
							scr.setVisible(false);
						}
					}
				});
				
			}
		});
		
		field.setBounds(99, 20, 186, 23);
		frame.getContentPane().add(field);
		field.setColumns(10);
		
		JTextField text = new JTextField();
		text.setBounds(85, 212, 325, 138);
		frame.getContentPane().add(text);
		
		JButton button = new JButton("SEARCH");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					DB db = new DB();
					int idWord = db.getIDFromWord(field.getText());
					text.setText(db.getDescriptionFromWord(field.getText()));
					int IDUserStory = db.getIDFromUserStory(idWord, user.getID());
					
					if(IDUserStory==-1)
					{
						db.addUserWord(idWord, user.getID());
					}
					else
					{
						db.incrementUserWord(IDUserStory);
					}
					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error!");
				}
				
				
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		button.setBounds(302, 21, 108, 21);
		frame.getContentPane().add(button);
	}
	
	private void disconnection()
	{
		JButton button = new JButton("SIGN OUT");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				FirstWindow.main(null);
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		button.setBounds(261, 387, 149, 27);
		frame.getContentPane().add(button);
		
		
	}
}
