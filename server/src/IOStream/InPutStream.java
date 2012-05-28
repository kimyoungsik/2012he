package IOStream;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import AbbrRepository.*;

public class InPutStream {

	public InPutStream() {
	}

	static public AbbrRepository read(AbbrRepository repo, String file) {
		DataInputStream in = null;

		int count = 0;
		char[] chOrig = null;
		char[] chAbbr = null;
		char[] chConverter = null;

		String orig;
		String abbr;
		String converter;

		try {
			in = new DataInputStream(new FileInputStream(file));
			count = in.readInt();

			for (int i = 0; i < count; i++) {
				orig = in.readUTF();
				abbr = in.readUTF();
				converter = in.readUTF();

				repo.addWord(new Word(orig, abbr, converter));
			}

		} catch (FileNotFoundException fnfe) {
			System.out.println("파일이 존재하지 않습니다.");
		} catch (EOFException eofe) {

		} catch (IOException ioe) {
			System.out.println("파일을 읽을 수 없습니다.");
		} finally {
			try {
				in.close();

			} catch (Exception e) {

			}

		}
		return repo;
	}
}
