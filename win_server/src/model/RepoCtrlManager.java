package model;

import java.util.List;

import javax.swing.JOptionPane;
import IOStream.*;
import AbbrRepository.AbbrRepository;
import AbbrRepository.Word;

public class RepoCtrlManager {

    private TESModel model;
    private IRepoViewAdapter viewA;
    private AbbrRepository original_repo;
    private AbbrRepository repo;
    private String source;

    public RepoCtrlManager(TESModel model, String title, String source) {
        this.model = model;
        this.source = source;
        AbbrRepository emptyrepo = new AbbrRepository(title);
        original_repo = InPutStream.read(emptyrepo, source);
        repo = original_repo;
    }

    public void setAdapter(IRepoViewAdapter adpt) {
        viewA = adpt;
    }

    public List<Word> getWords() {
        return repo.getWords();
    }

    public AbbrRepository getRepo() {
        return repo;
    }

    public void saveRepo(List<Word> words) {
        repo.setWords(words);
        OutPutStream.write(repo, source);
        original_repo = repo;
    }

    public void closeRepo(List<Word> words) {
        repo.setWords(words);
        if (original_repo != repo) {
            Object[] options = {"Don't Save", "Cancel", "Save"};
            int response = JOptionPane.showOptionDialog(
                    null, "Do you want to save the changes you made in the repository ��" + repo.getName() + "�� before closing?", "Save Before Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[2]);

            switch (response) {
                case 0:
                    closeRepo();
                    break;
                case 2:
                    saveRepo(words);
                    closeRepo();
                    break;
                case 1:
                case -1: //... Both the quit button (3) and the close box(-1) handled here.
                    break;
                default:
                    //... If we get here, something is wrong.  Defensive programming.
                    JOptionPane.showMessageDialog(null, "Unexpected response " + response);
            }
        } else {
            closeRepo();
        }

    }

    private void closeRepo() {
        model.removeRepo(repo);
        viewA.closeRepo();
    }
}
