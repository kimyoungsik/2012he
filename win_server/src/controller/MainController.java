package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import AbbrRepository.Word;
import javax.swing.JTextPane;
import model.*;
import view.*;
import network.*;
import threadPool.*;

public class MainController {

    private static MainView view;
    private static TESModel model;
    private static Server server;
    private static ChatServer poolserver;
    private WindowListener _windowListener = new WindowAdapter() {
    };

    public MainController() {
    }

    public MainController(WindowListener wl) {
        _windowListener = wl;
        init();
    }

    public void start() {
        view.validate();
        view.setVisible(true);
        model.start();


    }

    /**
     * Initializes the system by instantiating the view and the model and all the necessary adapters.
     */
    public void init() {

        //서버구동
        server = new Server(new IMainViewAdapterForNetwork() {

            public JTextPane GetWrittenTextPane() {
                return view.GetWrittenTextPane();
            }
        });
        view = new MainView(_windowListener, new IFileActionAdapter() {

            public void newDocument() {
                model.newDocument();

            }

            public void openDocument() {
                try {
                    model.loadDocument();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "IOException", "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }

            public void saveDocument() {
                try {
                    model.saveDocument();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "IOException", "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }

            public void saveAsDocument() {
                try {
                    model.saveAsDocument();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "IOException", "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }
        }, new IEditActionAdapter() {
        }, new ITESModelAdapter() {

            public Object createRepo(String title, String source) {
                return model.createRepo(title, source);
            }
        }, new IServerAdapter() {

            public void ServerStart() {
                server.ServerStart();
            }

            public String GetMessage() {
                return server.GetMessage();
            }

            public int GetPort() {
                return server.GetPort();
            }

            public void SetPort(int port) {
                server.SetPort(port);
            }

            public void SetType(int type) {
                server.SetType(type);
            }
        });

        model = new TESModel(new IMainViewAdapter() {

            public JFrame getMainView() {
                return view;
            }

            public void setTitle(String name) {
                if (name == null || name == "") {
                    view.setTitle("DEAF Editor 1.0.2");
                } else {
                    view.setTitle("DEAF Server - " + name);
                }
            }

            public void setTextPane(StringBuilder buffer) {
                view.setTextPane(buffer);
            }

            public void setTextPane(String str) {
                view.setTextPane(str);
            }

            public String getTextPane() {
                return view.getTextPane();
            }

            public void addRepoTab(String title, RepoCtrlManager rcm) {
                RepoView rv = view.addRepoTab(title);
                setUpRepo(rcm, rv);
            }

            public Object createRepo(String title, String source) {
                return view.createRepo(title, source);
            }
        });

    }

    private void setUpRepo(final RepoCtrlManager rcm, final RepoView rv) {
        rcm.setAdapter(new IRepoViewAdapter() {

            public void closeRepo() {
                view.closeRepo(rv);
            }
        });
        rv.setAdapter(new IRepoMngrAdapter() {

            public List<Word> getWords() {
                return rcm.getWords();
            }

            public void saveRepo(List<Word> words) {
                rcm.saveRepo(words);
            }

            public void closeRepo(List<Word> words) {
                rcm.closeRepo(words);
            }
        });
    }

    //끝낼때 창 띄우기
    private static void closing() {
        int res = JOptionPane.showConfirmDialog(view,
                "종료 전에 문서를 저장하시겠습니까?",
                "문서 저장 여부",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (!(res > 0)) {
            try {
                model.saveDocument();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * Entry point to the program.
     * Instantiates a MainController object with a WindowListener (WindowAdapater) that checks if the file is stored
     * and calls System.exit(0) when the window is closed.
     */
    public static void main(String[] args) {
        (new MainController(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                closing();
                System.exit(0);
            }
        })).start();
    }
}
