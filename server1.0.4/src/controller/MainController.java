package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import AbbrRepository.Word;

import java.io.File;
import javax.swing.JTextPane;
import model.*;
import view.*;
import network.*;

//import threadPool.*;
public class MainController {

    private static MainView view;
    private static TESModel model;
    private static Server server;
    private static Deafbook deafbook;
//    private static PrintView printView;
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
        deafbook = new Deafbook();
        deafbook.Load();

    }

    public void init() {

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
                    JOptionPane.showMessageDialog(view, "IOException",
                            "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }

            public void saveDocument() {
                try {
                    model.saveDocument();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "IOException",
                            "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }

            public void saveAsDocument() {
                try {
                    model.saveAsDocument();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "IOException",
                            "warning", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }
        },
                new IEditActionAdapter() {
                },
                new ITESModelAdapter() {

                    public Object createRepo(String title, String source) {
                        return model.createRepo(title, source);
                    }

                    public void SetCurrentFile(File currentFile) {
                        model.SetCurrentFile(currentFile);
                    }

                    public void saveDocument() {
                        try {
                            model.saveDocument();
                        } catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    public File GetCurrentFile() {
                        return model.GetCurrentFile();
                    }

                    @Override
                    public String saveTextfiles(String fileName) {
                        String ret = null;
                        try {

                            ret = model.saveTextfiles(fileName);

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return ret;

                    }

                    public void saveOnefile(String fileName) {
                        try {
                            model.saveOnefile(fileName);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                },
                new IServerAdapter() {

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
                },
                new IDeafBookAdapter() {

                    public int GetLength() {
                        return deafbook.GetLength();
                    }

                    public Vector<Student> GetStudents() {
                        return deafbook.GetStudents();
                    }

                    public Student GetAt(int index) {
                        return deafbook.GetAt(index);
                    }

                    public int Find(String name) {
                        return deafbook.Find(name);
                    }

                    public void Arrange() {
                        deafbook.Arrange();
                    }

                    public int Record(String name) {
                        return deafbook.Record(name);
                    }

                    public int Erase(int index) {
                        return deafbook.Erase(index);
                    }

                    public void Save() {
                        try {
                            deafbook.Save();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    public void Load() {

                        deafbook.Load();

                    }
//                           new PrintView()
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

            public void AutoSave() {
                view.AutoSave();
            }
        });

//        printView = new PrintView();



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

    //닫기 버튼 눌렀을때
    private static void closing() {

        //파일 이미 있다면
        if (model.GetCurrentFile() != null) {
            //자동저장
            view.AutoSave();
        } else {
            //파일이 없으면 저장여부 물어봄
            int res = JOptionPane.showConfirmDialog(view, "종료 전에 문서를 저장하시겠습니까?",
                    "문서 저장 여부", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (!(res > 0)) {
                try {
                    model.saveDocument();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        (new MainController(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                closing();
                System.exit(0);
            }
        })).start();
    }
}
