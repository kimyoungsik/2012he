package view;

/**
 * MainView accesses this adapter to interact with TESModel to perform actions on external files.
 * The adapter is initiated in MainController.
 * 
 * @author Narae Kim <narae.kim@headflow.net> <br>
 * Created at: 2011-04-10
 */
public interface IFileActionAdapter {

    public abstract void newDocument();

    public abstract void openDocument();

    public abstract void saveDocument();

    public abstract void saveAsDocument();
}
