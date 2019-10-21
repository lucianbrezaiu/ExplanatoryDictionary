package user.authorized;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import Logic.Category;
import Logic.DB;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class WordsWindow2 {

	private JFrame frame;
	private static Category category;
	private int contor;
	private JScrollPane scroll = new JScrollPane();
	
	public static void start(Category category) {
		if(category!=null)
			WordsWindow2.category = category;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordsWindow2 window = new WordsWindow2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordsWindow2() {
		initialize();
		contor = 0;
	}

	private void initialize() {
		frame = new JFrame(category.getName());
		frame.getContentPane().setBackground(Color.GREEN);
		frame.setBounds(100, 100, 916, 299);
		frame.getContentPane().setLayout(null);
		
		DB db = new DB();
		HashMap<String, String> map = null;
		map = db.getWordsWithDescription(category);
		
		ArrayList<String> wordList = new ArrayList<String>();
		Set<Entry<String, String>> values = map.entrySet();
		for(Map.Entry<String, String> me:values)
		{
			String word = me.getKey() + ": " + me.getValue();
			wordList.add(word);
		}
		
		displayNextTen(0, wordList);
		
		JButton button = new JButton("BACK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		button.setBounds(485, 220, 263, 35);
		frame.getContentPane().add(button);
		
		JButton button2 = new JButton("\u25BA");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scroll.setVisible(false);
				contor = contor + 10;
				displayNextTen(contor, wordList);
			}
		});
		button2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		button2.setBounds(185, 220, 231, 35);
		frame.getContentPane().add(button2);
		
		
	}
	
	private void displayNextTen(int contor, ArrayList<String> wordList)
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(String string : wordList)
			if(wordList.indexOf(string)>=contor && wordList.indexOf(string)<contor+10)
				model.addElement((wordList.indexOf(string)+1) + ".  " + string);
		
		if(model.size()==0)
		{
			scroll.setVisible(false);
			this.contor = 0;
			displayNextTen(this.contor, wordList);
		}
		else
		{
			JList<String> list = new JList<>();
			list.setBounds(0, 0, 1439, 180);
			list.setBackground(Color.YELLOW);
			list.setBorder(new LineBorder(new Color(0, 0, 0)));
			list.setModel(model);
			list.setVisible(true);
			
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scroll.setBounds(0, 0, 900, 200);
			scroll.setViewportView(list);
			scroll.setVisible(true);
			frame.getContentPane().add(scroll);
		}
	}
}






