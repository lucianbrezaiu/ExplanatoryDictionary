package Logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class DB {
	
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement prepStmt = null;
	
	private void createConnection()
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
			
	    	con = DriverManager.getConnection("jdbc:sqlite:/E:\\workspace - eclipse\\poo2 - tema 2\\DictionaryDB.db");
	    	con.setAutoCommit(false);
		} 
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("Error!");
		}
	}
	
	public User login(String usr, String pass)
	{
		User rez = null;
		try
	    {
			if(usr.isEmpty() || pass.isEmpty())
				throw new Exception();
			createConnection();
	        String query = "SELECT * FROM User WHERE Username=? and Password=?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setString(1, usr);
	        prepStmt.setString(2, pass);
	        
	        ResultSet rs = prepStmt.executeQuery();	        
	        while (rs.next())
	        {
	        	int id = rs.getInt("Id");
	        	String  username = rs.getString("Username");
	        	String  password = rs.getString("Password");
	        	String  state = rs.getString("State");
	        	rez = new User(id, username, password, state);
	        }
	        rs.close();
	        prepStmt.close();
	        con.close();
	    } 
	    catch (Exception e) 
	    {
	    	return null;
	    }
		
	    return rez;
	}
	
	
	
	public void addUser(String username, String password) throws Exception
	{
		if(username.isEmpty() || password.isEmpty())
			throw new Exception();
		if(checkUniqueUsername(username)==false)
			throw new UniqueException();
		
		createConnection();
		String query = "INSERT INTO User(Username, Password, State) VALUES (?, ?, ?)";
		prepStmt = con.prepareStatement(query);
	    prepStmt.setString(1, username);
	    prepStmt.setString(2, password);
	    prepStmt.setString(3, "unauthorized");
	    prepStmt.execute();	
	    prepStmt.close();
	    con.commit();
	    con.close();
	   
	}
	
	public void addCategory(String name) throws Exception
	{
		if(checkUniqueCategory(name)==false) 
			throw new UniqueException();
		
		createConnection();
	    String query = "INSERT INTO Category(Name) VALUES (?)";
	    prepStmt = con.prepareStatement(query);
	    prepStmt.setString(1, name);
	    prepStmt.execute();
	    prepStmt.close();
	    con.commit();
	    con.close();
	    
		
	}
	
	public void addWord(int idCategory, String name, String description) throws Exception
	{
		if(checkUniqueWord(name)==false)
			throw new UniqueException();
		
		createConnection();
	    String query = "INSERT INTO Word(IDCategory,Name,Description) VALUES (?, ?, ?)";
	    prepStmt = con.prepareStatement(query);
	    prepStmt.setInt(1, idCategory);
	    prepStmt.setString(2, name);
	    prepStmt.setString(3, description);
	    prepStmt.execute();
	    prepStmt.close();
	    con.commit();
	    con.close();
	}
	
	public void addUserWord(int IDWord, int IDUser) throws Exception
	{
		int rate = 1;
		
		createConnection();
	    String query = 	"INSERT INTO UserWord(IDWord,IDUser,Rate) VALUES (?, ?, ?)";
	    prepStmt = con.prepareStatement(query);
	    prepStmt.setInt(1, IDWord);
	    prepStmt.setInt(2, IDUser);
	    prepStmt.setInt(3, rate);
	    prepStmt.execute();
	    prepStmt.close();
	    con.commit();
	    con.close();	
	}
	
	public void incrementUserWord(int ID) throws Exception
	{
		createConnection();
		try
		{
			String query = "UPDATE UserWord SET rate = rate+1 WHERE ID = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setInt(1, ID);
		    prepStmt.execute();	
		    prepStmt.close();
		    con.commit();
		    con.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void modifyWord(int id, String description)
	{
		createConnection();
		try
		{
			String query = "UPDATE Word SET Description = ? WHERE ID = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setString(1, description);
		    prepStmt.setInt(2, id);
		    prepStmt.execute();	
		    prepStmt.close();
		    con.commit();
		    con.close();
		}
		catch(Exception e)
		{
			
		}
		
	}
	
	public void modifyCategory(Integer id, String name)
	{
		createConnection();
		try
	    {
			createConnection();
			String query = "UPDATE Category SET Name = ? WHERE ID = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setString(1, name);
		    prepStmt.setInt(2, id);
		    prepStmt.execute();	
		    prepStmt.close();
		    con.commit();
		    con.close();
	    } 
	    catch (Exception e) 
	    {
	    	
	    }
	}
	

	
	
	public void deleteCategory(Integer id)
	{
		createConnection();
		try
	    {
			createConnection();
			
			String query = "DELETE FROM Word WHERE IDCategory = ?";
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, id);
			prepStmt.execute();	
			
			query = "DELETE FROM Category WHERE ID = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setInt(1, id);
		    prepStmt.execute();	
		    
		   
		    prepStmt.close();
		    con.commit();
		    con.close();
	    } 
	    catch (Exception e) 
	    {
	    	
	    }
	}
	
	public void deleteWord(Integer id)
	{
		createConnection();
		try
	    {
			createConnection();
			String query = "DELETE FROM Word WHERE ID = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setInt(1, id);
		    prepStmt.execute();	
		    
		    query = "DELETE FROM UserWord where IDWord = ?";
		    prepStmt = con.prepareStatement(query);
		    prepStmt.setInt(1, id);
		    prepStmt.execute();	
		    
		    prepStmt.close();
		    con.commit();
		    con.close();
	    } 
	    catch (Exception e) 
	    {
	    	
	    }
	}
	
	
	public User getAdministrator()
	{
		createConnection();
		User admin = null;
	    try
	    {
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE State = 'administrator'");
	        while (rs.next())
	        {
	        	int id = rs.getInt("ID");
	        	String  username = rs.getString("Username");
	        	String  password = rs.getString("Password");
	        	String state = rs.getString("State");
	        	admin = new User(id, username, password, state);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	    } 
	    catch (SQLException e) 
	    {
	    	return null;
	    }
	    return admin;
	}
	
	public ArrayList<User> getUsers()
	{
		createConnection();
		ArrayList<User> users = new ArrayList<User>();
	    try
	    {
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM User");
	        while (rs.next())
	        {
	        	int id = rs.getInt("ID");
	        	String  username = rs.getString("Username");
	        	String  password = rs.getString("Password");
	        	String state = rs.getString("State");
	        	User u = new User(id, username, password, state);
	        	users.add(u);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	    } 
	    catch (SQLException e) 
	    {
	    	return null;
	    }
	    return users;
	}

	public ArrayList<Category> getCategories()
	{
		createConnection();
		ArrayList<Category> categories = new ArrayList<>();
	    try
	    {
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM Category");
	        while (rs.next())
	        {
	        	int id = rs.getInt("ID");
	        	String name = rs.getString("Name");
	        	Category category = new Category(id,name);
	        	categories.add(category);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	    } 
	    catch (SQLException e) 
	    {
	    	return null;
	    }
	    return categories;
		
	}
	
	public ArrayList<Word> getWords()
	{
		createConnection();
		ArrayList<Word> words = new ArrayList<>();
	    try
	    {
	    	stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM Word WHERE IDCategory IS NOT NULL");
	        while (rs.next())
	        {
	        	int id = rs.getInt("ID");
	        	int idCategory = rs.getInt("IDCategory");
	        	String name = rs.getString("Name");
	        	String description = rs.getString("Description");
	        	Word word = new Word(id,idCategory,name,description);
	        	words.add(word);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	    } 
	    catch (SQLException e) 
	    {
	    	return null;
	    }
	    return words;
	}
	
	public int getIDFromUserStory(int idWord, int idUser)
	{
		int id = -1;
		createConnection();
	    try
	    {
	    	stmt = con.createStatement();
	        String query = "SELECT ID FROM UserWord WHERE idWord = ? and IDUser = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setInt(1, idWord);
	        prepStmt.setInt(2, idUser);
	        ResultSet rs = prepStmt.executeQuery();	   
	        while (rs.next())
	        {
	        	id = rs.getInt("ID");
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	        return id;
	        
	    } 
	    catch (SQLException e) 
	    {
	    	return -1;
	    }
	}
	
	public int getNumberOfWordsFromCategory(Category category)
	{
		int id = -1;
		createConnection();
	    try
	    {
	    	stmt = con.createStatement();
	        String query = "SELECT count(*) FROM Word WHERE IDCategory = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setInt(1, category.getID());
	        ResultSet rs = prepStmt.executeQuery();	   
	        while (rs.next())
	        {
	        	id = rs.getInt(1);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
	        return id;
	        
	    } 
	    catch (SQLException e) 
	    {
	    	return -1;
	    }
	}
	
	public Category getCategory(int id)
	{
		Category aux = null;
		createConnection();
		try
		{
			stmt = con.createStatement();
	        String query = "SELECT * FROM Category WHERE ID = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setInt(1, id);
	        ResultSet rs = prepStmt.executeQuery();
	        while (rs.next())
	        {
	        	int ID = rs.getInt("ID");
	        	String name = rs.getString("Name");
	        	aux = new Category(ID,name);
	        }
	        rs.close();
	        prepStmt.close();
	        stmt.close();
	        con.close();
		}
		catch(Exception e)
		{
			return null;
		}
		return aux;
	}
	
	public HashMap<String, Integer> getWordsWithTotalViews(Category category)
	{
		HashMap<String, Integer> map = null;
		createConnection();
		try
		{
			map = new HashMap<String, Integer>();
			
			stmt = con.createStatement();
	        String query = "SELECT word.Name,SUM(uw.Rate) as Views from Word word LEFT JOIN UserWord uw ON uw.IDWord = word.ID WHERE word.IDCategory = ? GROUP BY word.Name";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setInt(1, category.getID());
	        ResultSet rs = prepStmt.executeQuery();
	        while (rs.next())
	        {
	        	String name = rs.getString("Name");
	        	int count = rs.getInt("Views");
	        	map.put(name, count);
	        }
	        rs.close();
	        prepStmt.close();
	        stmt.close();
	        con.close();
			
		}
		catch(Exception e)
		{
			return null;
		}
		return map;
	}
	
	public HashMap<String, String> getWordsWithDescription(Category category)
	{
		HashMap<String, String> map = null;
		createConnection();
		try
		{
			map = new HashMap<String, String>();
			
			stmt = con.createStatement();
	        String query = "SELECT Name,Description from Word WHERE IDCategory = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setInt(1, category.getID());
	        ResultSet rs = prepStmt.executeQuery();
	        while (rs.next())
	        {
	        	String name = rs.getString("Name");
	        	String description = rs.getString("Description");
	        	map.put(name, description);
	        }
	        rs.close();
	        prepStmt.close();
	        stmt.close();
	        con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	public HashMap<String, Integer> getAllWordsWithTotalViews()
	{
		HashMap<String, Integer> map = null;
		createConnection();
		try
		{
			map = new HashMap<String, Integer>();
			
			stmt = con.createStatement();
	        String query = "SELECT word.Name,SUM(uw.Rate) as Views from Word word LEFT JOIN UserWord uw ON uw.IDWord = word.ID GROUP BY word.Name";
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next())
	        {
	        	String name = rs.getString("Name");
	        	int count = rs.getInt("Views");
	        	map.put(name, count);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
			
		}
		catch(Exception e)
		{
			return null;
		}
		return map;
	}
	
	public TreeMap<Integer, String> getWordsWithViewsLikeSthFromUser(User user, String like)
	{
		TreeMap<Integer, String> map = null;
		createConnection();
		try
		{
			map = new TreeMap<Integer, String>();
			
			stmt = con.createStatement();
	        String query = "select word.Name,uw.Rate from User user inner join UserWord uw ON uw.IDUser = user.ID inner join Word word ON word.ID = uw.IDWord where user.ID = ? and  word.Name like ? order by uw.rate DESC";
	        prepStmt = con.prepareStatement(query);
	        
	        prepStmt.setInt(1,user.getID());
	        prepStmt.setString(2,like+"%");
	        ResultSet rs = prepStmt.executeQuery();
	        
	        while (rs.next())
	        {
	        	String name = rs.getString("Name");
	        	Integer rate = rs.getInt("Rate");
	        	map.put(rate, name);
	        }
	        rs.close();
	        stmt.close();
	        con.close();
			
		}
		catch(Exception e)
		{
			return null;
		}
		return map;
	}
	
	public int getIDFromWord(String name)
	{
		int id = 0;
		createConnection();
		try
		{
			stmt = con.createStatement();
	        String query = "SELECT word.ID FROM Word word WHERE word.Name = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setString(1, name);
	        ResultSet rs = prepStmt.executeQuery();
	        while (rs.next())
	        {
	        	id = rs.getInt("ID");
	        	
	        }
	        rs.close();
	        prepStmt.close();
	        stmt.close();
	        con.close();
		}
		catch(Exception e)
		{
			return 0;
		}
		return id;
	}
	
	public String getDescriptionFromWord(String name)
	{
		String description = null;
		createConnection();
		try
		{
			stmt = con.createStatement();
	        String query = "SELECT word.Description FROM Word word WHERE word.Name = ?";
	        prepStmt = con.prepareStatement(query);
	        prepStmt.setString(1, name);
	        ResultSet rs = prepStmt.executeQuery();
	        while (rs.next())
	        {
	        	description = rs.getString("Description");
	        }
	        rs.close();
	        prepStmt.close();
	        stmt.close();
	        con.close();
		}
		catch(Exception e)
		{
			return null;
		}
		return description;
	}
	
	
	
	public void changeUserStatus(User user, String status)
	{
		try {
			createConnection();
			String query = "UPDATE User SET State = ? WHERE Username = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setString(1, status);
		    prepStmt.setString(2, user.getUsername());
		    prepStmt.execute();	
		    prepStmt.close();
		    con.commit();
		    con.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void changePassword(User user, String newPassword)
	{
		try {
			createConnection();
			String query = "UPDATE User SET Password = ? WHERE Username = ?";
			prepStmt = con.prepareStatement(query);
		    prepStmt.setString(1, newPassword);
		    prepStmt.setString(2, user.getUsername());
		    prepStmt.execute();	
		    prepStmt.close();
		    con.commit();
		    con.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	private boolean checkUniqueUsername(String username)
	{
		ArrayList<User> list = getUsers();
		
		for(User user : list)
			if(user.getUsername().matches(username))
				return false;
		return true;
	}

	private boolean checkUniqueCategory(String name)
	{
		ArrayList<Category> list = getCategories();
		
		for(Category category : list)
			if(category.getName().matches(name))
				return false;
		return true;
	}

	private boolean checkUniqueWord(String name)
	{
		ArrayList<Word> words = getWords();
		
		for(Word word : words)
			if(word.getName().matches(name))
				return false;
		return true;
	}

}
