package view;

import java.util.Vector;

import model.Deafbook;
import model.Student;

public interface IDeafBookAdapter {

	public abstract int GetLength();
	public abstract Vector<Student> GetStudents();
	public abstract Student GetAt(int index);
	public abstract int Find(String name);
	public abstract void Arrange();
	public abstract int Record(String name);
	public abstract int Erase(int index);
        public abstract void Save();
        public abstract void Load();
   
}



