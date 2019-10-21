package Logic;

public class Category {

	private int ID;
	private String name;
	
	public Category()
	{
		
	}
	
	public Category(int ID, String name)
	{
		this.ID = ID;
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		
		return "[ID: " + ID + ", name: "+ name + "]";
	}
}
