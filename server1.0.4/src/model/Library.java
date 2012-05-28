package model;

public class Library {
	private String name;
	private String address;
	
	public Library(){
		this.name = "";
		this.address = "";
	}
	
	public Library(String name, String address)
	{
		this.name = name;
		this.address = address;
		
	}
	
	public boolean IsEqual (Library other){
		boolean ret = false;
		if(this.name.equals(other.name)== true &&
				this.address.equals(other.address)== true){
			ret = true;
		}
		return ret;
	}

	public boolean IsNotEqual (Library other){
		boolean ret = false;
		if(this.name.equals(other.name)== false ||
				this.address.equals(other.address)== false){
			ret = true;
		}
		return ret;
	}
	
	public String GetName()
	{
		return this.name;
	}
	public String GetAddress()
	{
		return this.address;
	}
}
