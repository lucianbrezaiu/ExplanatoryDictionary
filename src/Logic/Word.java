package Logic;

public class Word {

	private int id, idCategory;
	private String name, description;
	
	public Word() {}
	
	public Word(int id, int idCategory, String name, String description)
	{
		this.id = id;
		this.idCategory = idCategory;
		this.name = name;
		this.description = description;
	}
	
	public int getId() 
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getIdCategory() 
	{
		return idCategory;
	}
	public void setIdCategory(int idCategory)
	{
		this.idCategory = idCategory;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString()
	{
		return "[" + name + " = " + description + "]";
	}
	
}
