package user.authorized;

import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Logic.DB;
import Logic.Word;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;

public class WordsWindow3 {

	private JFrame frame;
	private static String letter;
	
	public static void start(String letter) {
		WordsWindow3.letter=letter;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordsWindow3 window = new WordsWindow3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordsWindow3() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame(letter);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 232, 347);
		frame.getContentPane().setLayout(null);
		
		DB db = new DB();
		ArrayList<Word> words = db.getWords();
		
		Object[] columns = {"Word","Category"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		Object[] objects = new Object[2];
		for(Word word : words)
		{
			if(word.getName().toUpperCase().charAt(0)==letter.toUpperCase().charAt(0))
			{
				objects[0] = word.getName();
				objects[1] = db.getCategory(word.getIdCategory()).getName();
				model.addRow(objects);
			}
		}
		
		JButton button = new JButton("BACK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button.setBounds(31, 271, 146, 24);
		frame.getContentPane().add(button);
		
		JTable table = new JTable();
		table.setSize(284, 190);
		table.setLocation(0, 0);
		table.setModel(model);
		table.setFont(new Font("Comic Sans MS",1,16));
		table.setRowHeight(20);
		table.setBackground(Color.green);
		table.setVisible(true);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(-1, -1, 223, 262);
		scroll.setViewportView(table);
		scroll.setVisible(true);
		frame.getContentPane().add(scroll);
		
	}
}






