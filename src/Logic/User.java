package Logic;

public class User 
{
	private int ID;
	private String username;
	private String password;
	private State state;
	
	public User(int ID, String username, String password, String state) 
	{
		this.ID = ID;
		this.username = username;
		this.password = password;
		switch(state)
		{
		case "administrator":
			this.state = State.administrator;
			break;
		case "authorized":
			this.state = State.authorized;
			break;
		case "unauthorized":
			this.state = State.unauthorized;
			break;
		}
		
	}
	
	public User(int ID, String username, String password, State state) 
	{
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.state=state;
		
	}
	public User(){ }
	
	public int getID() 
	{
		return ID;
	}
	public void setID(int ID) 
	{
		this.ID = ID;
	}
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "[username: " + username + ", state: " + state + "]"; 
	}
}