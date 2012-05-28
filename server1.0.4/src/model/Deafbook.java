package model;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Vector;

public class Deafbook {

    private Vector<Student> students = new Vector<Student>(3, 1);
    private int capacity;
    private int length;

    public Deafbook() {
        this.capacity = 3;
        this.length = 0;
    }

    public int Record(String name) {
        int index;

        Student student = new Student(name);

        if (this.length < this.capacity) {
            this.students.add(this.length, student);
        } else {
            this.students.addElement(student);
            this.capacity++;
        }
        index = this.students.size() - 1;
        this.length++;

        return index;
    }
//	public int Correct(int index)
//	{
//		Library library = new Library();
//		library = this.libraries.get(index);
//		library = new Library(library.GetName(),libAddress);
//		this.libraries.setElementAt(library, index);
//		return index;
//	}

    public int Erase(int index) {

        this.students.remove(index);
        this.capacity--;
        this.length--;
        return index = -1;
    }

    public void Arrange() {
        int i, j;
        Student temp;
        for (i = 0; i < this.length - 1; i++) {
            for (j = i + 1; j < this.length; j++) {
                if (this.GetAt(i).GetName().compareTo(this.GetAt(i).GetName()) > 0) {
                    temp = this.students.get(i);
                    this.students.setElementAt(this.students.get(j), i);
                    this.students.setElementAt(temp, j);
                }
            }
        }
    }

    public int Find(String name) {
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
        int index = -1;
        int i = 0;
//		int j =0;
        while (i < this.length) {
            if (this.GetAt(i).GetName().equals(name) == true) {
                index = i;
                i = this.length;
//				indexes[j] = i;
//				j++;
            }
            i++;
        }
        return index;
    }

    /*
     * 
    세이브
    
    1. 전체 이름 갯수를 파악한다
    2. 갯수만큼 반복한다. 
    2.0 인덱스를 센다.
    2.1 이름을 읽는다. 
    2.2 이름을 데이터베이스에 적는다. 한줄
    2.3 라이브러리 갯구를 구한다.
    2.4 라이브러리 갯수만큼 반복한다.
    2.4.1 인덱스와 라이브러리 이름 라이브러리 주소를 저장한다.한줄
    2.5 인덱스를 증가시킨다.
    
    
     * 
     * 적용,취소 눌렀을때
     * 새로운 이름 등록시 
     * 새로운 라이브러리 등록시
     * 이름 지울 시
     * 라이브러리 지울 시 
     * 
     * 프로그램 종료시
     */
    public void Save() throws FileNotFoundException {

        int namesCount = this.length;

        OutputStreamWriter out = null;
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + File.separator + "Library/deafnames.txt");


        out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

        OutputStreamWriter outLibrary = null;
        FileOutputStream fosLibrary = new FileOutputStream(System.getProperty("user.home") + File.separator + "Library/deaflibraries.txt");

        outLibrary = new OutputStreamWriter(fosLibrary, Charset.forName("UTF-8"));

        for (int i = 0; i < namesCount; i++) {
            Student student = this.GetAt(i);
            String name = student.GetName();
            name = name.concat("\n");
            try {
                out.write(name);
            } catch (IOException e) {
                System.out.println(e.toString());
            }

            int librariesCount = student.GetLength();
            for (int j = 0; j < librariesCount; j++) {
                Library library = student.GetAt(j);

                String exLibrary = Integer.toString(i) + "::" + library.GetName() + "::" + library.GetAddress() + "\n";
                try {
                    outLibrary.write(exLibrary);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }

            }

        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        if (outLibrary != null) {
            try {
                outLibrary.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }


    }

    //처름 프로그램 켯을때 실행 (maincontroller)
    public void Load() {
        String lineName;
        try {
            FileReader frName = new FileReader(System.getProperty("user.home") + File.separator + "Library/deafnames.txt");

            BufferedReader brName = new BufferedReader(frName);
            LineNumberReader lrName = new LineNumberReader(brName);

            //내부에 적기
            while ((lineName = lrName.readLine()) != null) {
                this.Record(lineName);
            }


        } catch (IOException e) {
            System.out.println(e.toString());
        }



        String library;
        try {
            FileReader frLibrary = new FileReader(System.getProperty("user.home") + File.separator + "Library/deaflibraries.txt");

            BufferedReader brLibrary = new BufferedReader(frLibrary);
            LineNumberReader lrLibrary = new LineNumberReader(brLibrary);

            //내부에 적기
            while ((library = lrLibrary.readLine()) != null) {
                String libraries[] = library.split("::");
                String strIndex = libraries[0];
                String strName = libraries[1];
                String strAddress = libraries[2];

                //라이블러리 내부에 적기
                int intIndex = Integer.parseInt(strIndex);
                Student student = this.GetAt(intIndex);
                student.Record(strName, strAddress);


            }


        } catch (IOException e) {
            System.out.println(e.toString());
        }


    }

    public Vector<Student> GetStudents() {
        return this.students;
    }

    public Student GetAt(int index) {
        return this.students.get(index);
    }

    public int GetLength() {
        return this.length;
    }

    public int GetCapacity() {
        return this.capacity;
    }
}
