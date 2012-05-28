package IOStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import AbbrRepository.AbbrRepository;
import AbbrRepository.Word;

public class OutPutStream {

	public OutPutStream() {
	}

	static public void write(List<Word> words, String source) {

		DataOutputStream out = null;

		try {
			out = new DataOutputStream(new FileOutputStream(source));

			out.writeInt(words.size());

			for (int i = 0; i < words.size(); i++) {
				Word word = words.get(i);

				out.writeUTF(word.getOrig());
				out.writeUTF(word.getAbbr());
				out.writeUTF(word.getConverter());

			}

		}

		catch (IOException ioe) {
			System.out.println("파일로 출력할 수 없습니다.");

		} finally {
			try {
				out.close();
			} catch (Exception e) {

			}
		}
	}

	static public void write(AbbrRepository repo, String source) {

		DataOutputStream out = null;

		try {
			out = new DataOutputStream(new FileOutputStream(source));

			out.writeInt(repo.getWords().size());

			for (int i = 0; i < repo.getWords().size(); i++) {
				Word word = repo.getWord(i);

				out.writeUTF(word.getOrig());
				out.writeUTF(word.getAbbr());
				out.writeUTF(word.getConverter());

			}

		}

		catch (IOException ioe) {
			System.out.println("파일로 출력할 수 없습니다.");

		} finally {
			try {

				out.close();
			} catch (Exception e) {

			}
		}
	}

}
