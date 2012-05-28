package view;

import java.util.List;

import AbbrRepository.Word;

public interface IRepoMngrAdapter {

	public abstract List<Word> getWords();

	public abstract void saveRepo(List<Word> list);

	public abstract void closeRepo(List<Word> list);

}
