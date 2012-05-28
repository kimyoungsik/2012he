package model;

import javax.swing.JFrame;

public interface IMainViewAdapter {

    public abstract JFrame getMainView();

    public abstract void setTitle(String name);

    public abstract void setTextPane(StringBuilder buffer);

    public abstract void setTextPane(String str);

    public abstract String getTextPane();

    public abstract void addRepoTab(String title, RepoCtrlManager rcm);

    public abstract Object createRepo(String title, String source);
}
