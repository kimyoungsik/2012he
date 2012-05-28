package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import model.*;
import view.*;
import network.*;

public class MainController {

    private static MainView view;
    private static TESModel model;
    private static receiverServer receiServer;
    private static ReceiverThread receiverThread;

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


        //클라이언트 구동
        receiServer = new receiverServer(new IMainViewAdapterForNetwork() {

            public JTextPane GetWrittenTextPane() {
                return view.GetWrittenTextPane();
            }

            public JTextArea GetShowTextPane() {
                return view.GetShowTextPane();
            }

            public int GetLimitedLine() {
                return view.GetLimitedLine();
            }
        });


        view = new MainView(_windowListener,
                new IFileActionAdapter() {

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
                },
                new IClientAdapter() {

                    public void receiverStart() {
                        receiServer.receiverStart();
                    }

                    public void receiverClose() {
                        receiServer.receiverClose();
                    }

                    public String GetMessage() {
                        return receiServer.GetMessage();
                    }

                    public int GetPort() {
                        return receiServer.GetPort();
                    }

                    public void SetPort(int port) {
                        receiServer.SetPort(port);
                    }

                    public String GetIp() {
                        return receiServer.GetIp();
                    }

                    public void SetIp(String ip) {
                        receiServer.SetIp(ip);
                    }

                    public receiverServer GetReceiverServer() {
                        return receiServer.GetReceiverServer();
                    }
                },
                new IReceiverAdapter() {

                    public String GetAllText() {
                        String str = receiverThread.GetAllText();

                        return str;
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
                    view.setTitle("DEAF Client - " + name);
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
