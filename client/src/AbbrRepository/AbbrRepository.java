package AbbrRepository;

import java.util.ArrayList;
import java.util.List;

public class AbbrRepository { // 축약표
	private String name;
	private List<Word> words;

	public static String ORIG = "orig";
	public static String ABBR = "abbr";
	public static String CONVERTER = "converter";
	public static String FREQUENCY = "frequency";

	public AbbrRepository(String name) {
		this.name = name;
		this.words = new ArrayList<Word>();
	}

	public AbbrRepository(String name, List<Word> words) {
		this.name = name;
		this.words = words;
	}

	public String getName() {
		return name;
	}

	public Word getWord(int idx) {
		return words.get(idx);
	}

	public List<Word> getWords() {

		return words;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

	public void addWord(Word word) { // 워드 추가
		words.add(word);
	}

	public boolean removeWord(int idx, String orig) {
		if (!words.isEmpty()) {
			if (words.get(idx).getOrig().equals(orig)) {
				words.remove(idx);
				return true;
			}
			System.out.println("The removing word, " + orig
					+ " does not match its index stored in the repository!");
			return false;
		} else {
			System.out.println("The repository is empty!");
			return false;
		}

	}
}
