package model;

import java.util.Vector;

public class Student {

	private String name;
	private Vector<Library> libraries = new Vector<Library>(3, 1);
	private int capacity;
	private int length;

	public Student() {
		this.name = "";
		this.capacity = 3;
		this.length = 0;
	}

	public Student(String name) {
		this.name = name;
		this.capacity = 3;
		this.length = 0;
	}

	public int Record(String libName, String libAddress) {
		int index;
		Library library = new Library(libName, libAddress);
		if (this.length < this.capacity) {
			this.libraries.add(this.length, library);
		} else {
			this.libraries.addElement(library);
			this.capacity++;
		}
		index = this.libraries.size() - 1;
		this.length++;

		return index;
	}
	public int Correct(int index, String libAddress)
	{
		Library library = new Library();
		library = this.libraries.get(index);
		library = new Library(library.GetName(),libAddress);
		this.libraries.setElementAt(library, index);
		return index;
	}
	
	public int Erase(int index)
	{	
		this.libraries.remove(index);
		this.capacity --;
		this.length --;
		return index = -1;	
	}
        
        public void EraseAll()
        {
            for(int i=0; i<this.length; i++)
            {
                this.libraries.remove(0);      
            }
           
        }
	
	public void Arrange()
	{
		int i, j;
		Library temp;
		for(i=0; i<this.length-1; i++)
		{
			for(j=i+1; j<this.length; j++)
			{
				if(this.GetAt(i).GetName().compareTo(this.GetAt(i).GetName())>0)
				{
					temp = this.libraries.get(i);
					this.libraries.setElementAt(this.libraries.get(j), i);
					this.libraries.setElementAt(temp, j);
				}
			}
		}
	}
	public int Find(String name){
		int index = -1;
//		int count = 0;
//		int i = 0;
//		while(i<this.length){
//			if(this.GetAt(i).GetName().equals(name)==true)
//			{
//				count++;
//			}
//			i++;
//		}
//		int[] indexes = new int [count];
//		
		int i=0;
//		int j =0;
		while(i<this.length)
		{
			if(this.GetAt(i).GetName().equals(name)==true){
//				indexes[j] = i;
//				j++;
				index = i;
				i = this.length;
			}
			i++;
		}
		return index;
	}
	
	public Library GetAt(int index){
		return this.libraries.get(index);
	}
	public String GetName()
	{
		return this.name;
	}
	public int GetLength()
	{
		return this.length;
	}
	public int GetCapacity()
	{
		return this.capacity;
	}
	

}
