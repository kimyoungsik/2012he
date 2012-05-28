package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import network.*;

public class MainView extends JFrame implements KeyListener {

    private static final long serialVersionUID = -8358781470076832332L;
    private boolean REPLACE = false; //false by default
    private String REPLACE_INTO = "";
    private int CARET_POS = new Integer(0);
    private IFileActionAdapter fileActionA;
    //클라이언터 어뎁터
    private IClientAdapter iCliA;
    //완성글자 받기 어댑터
    private IReceiverAdapter iReceiverA;
    //undo
    final UndoManager undo = new UndoManager();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("파일");
    private JMenu editMenu = new JMenu("편집");
    private JMenu networkMenu = new JMenu("네트워크");
    private JMenu helpMenu = new JMenu("도움말");
//    private JMenuItem newFileMenuItem = new JMenuItem("새로만들기");
//    private JMenuItem openFileMenuItem = new JMenuItem("열기");
    private JMenuItem saveFileMenuItem = new JMenuItem("저장");
    private JMenuItem saveAsFileMenuItem = new JMenuItem("다른 이름으로 저장");
    private JMenuItem exitMenuItem = new JMenuItem("끝내기");
//    private JMenuItem redoMenuItem = new JMenuItem("되살리기");
//    private JMenuItem undoMenuItem = new JMenuItem("실행취소");
    private JMenuItem copyEditMenuItem = new JMenuItem("복사");
    private JMenuItem cutEditMenuItem = new JMenuItem("자르기");
    private JMenuItem pasteEditMenuItem = new JMenuItem("붙여넣기");
    private JMenuItem fontMenuItem = new JMenuItem("글자체");
    private JMenuItem clientNetworkMenuItem = new JMenuItem("연결");
    private JMenuItem resizeNetworkMenuItem = new JMenuItem("크기변경");
//    private JMenuItem allTextNetworkMenuItem = new JMenuItem("전체 불러오기");
    private JMenuItem manualHelpMenuItem = new JMenuItem("메뉴얼");
    private JMenuItem howToUseHelpMenuItem = new JMenuItem("간단사용법");
    private JMenuItem aboutUsHelpMenuItem = new JMenuItem("만든이");
    //텍스트 팬 탭
    private JTabbedPane textTabbedPane = new JTabbedPane();
    //속기 텍스트
    private JTextPane textPane = new JTextPane();
    private JTextArea showTextPane = new JTextArea();
    //축약테이블 탭
    private JTabbedPane reposTabbedPane = new JTabbedPane();
    private static final String NEW_SPEAKER = "> ";
    //현재 폰트 높이
    private int currentFontHight = 32;
    //보여지는 클라이언트 화면 높이
    private double showTextheight;
    //화면에 보여지는 최대 라인 수
    private int limitedLine;

    public MainView(WindowListener wl, IFileActionAdapter fileActionA, /* ITESModelAdapter modelA,*/ /*IServerAdapter iSerA,*/ IClientAdapter iCliA,/* ISenderAdapter iSenderA */ IReceiverAdapter iReceiverA) {
        super("DEAF Client 1.0.2");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(wl);
        this.iCliA = iCliA;
        this.iReceiverA = iReceiverA;
        this.fileActionA = fileActionA;

        init();
        //createRepo("demo.csv","doc/demo.csv");

    }

    private void init() {

        try {
            this.setPreferredSize(new java.awt.Dimension(1040, 540));
            pack();

            //메뉴넣기
            setJMenuBar(menuBar);
            menuBar.setPreferredSize(new Dimension(1040, 25));
            {

                menuBar.add(fileMenu);

                {
//                    fileMenu.add(newFileMenuItem);
//                    newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//                    newFileMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent event) {
//                            MainView.this.beforeNew();
//                            fileActionA.newDocument();
//                        }
//                    });


//                    fileMenu.add(openFileMenuItem);
//                    openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//                    openFileMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent event) {
//                            fileActionA.openDocument();
//                        }
//                    });

                    fileMenu.add(saveFileMenuItem);
                    saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    saveFileMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            fileActionA.saveDocument();
                        }
                    });

                    fileMenu.add(saveAsFileMenuItem);
                    saveAsFileMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            fileActionA.saveAsDocument();
                        }
                    });

                    fileMenu.add(new JSeparator());

                    fileMenu.add(exitMenuItem);
                    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    exitMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
//                            System.out.println("exitMenuItem.actionPerformed, event=" + evt);
                            MainView.this.closing();
                            MainView.this.dispose();//?

                            System.exit(0);
                        }
                    });
                }

                menuBar.add(editMenu);
                {
//                    editMenu.add(undoMenuItem);
//                    undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//                    undoMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent evt) {
//                            try {
//                                if (undo.canUndo()) {
//                                    undo.undo();
//                                }
//                            } catch (CannotUndoException e) {
//                            }
//                        }
//                    });
//
//                    editMenu.add(redoMenuItem);
//                    redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//                    redoMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent evt) {
//                            try {
//                                if (undo.canRedo()) {
//                                    undo.redo();
//                                }
//                            } catch (CannotRedoException e) {
//                            }
//                        }
//                    });

                    editMenu.add(new JSeparator());

                    editMenu.add(cutEditMenuItem);
                    cutEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(copyEditMenuItem);
                    copyEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(pasteEditMenuItem);
                    pasteEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(new JSeparator());

                    editMenu.add(fontMenuItem);
                    fontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    fontMenuItem.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            int selectedIndex = textTabbedPane.getSelectedIndex();

                            FontChooserView fcv = new FontChooserView(MainView.this);
                            FontTracker ft = null;
                            if (selectedIndex == 0) {
                                ft = new FontTracker(showTextPane.getFont(), showTextPane.getForeground(), showTextPane.getBackground());
                            } else if (selectedIndex == 1) {
                                ft = new FontTracker(textPane.getFont(), textPane.getForeground(), textPane.getBackground());
                            }
                            fcv.setSelectedTextFont(ft);
                            fcv.setVisible(true);
                        }
                    });
                }


                menuBar.add(networkMenu);

                {

                    networkMenu.add(clientNetworkMenuItem);
                    clientNetworkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    clientNetworkMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            Network_Client dialog = new Network_Client(iCliA);
                            dialog.setVisible(true);
                        }
                    });



                    networkMenu.add(resizeNetworkMenuItem);
                    resizeNetworkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    resizeNetworkMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            //크기 조정
                            Dimension showText = textTabbedPane.getSize();
                            showTextheight = showText.getHeight();
                            limitedLine = (int) showTextheight / (currentFontHight + 15);

                        }
                    });


//                    networkMenu.add(allTextNetworkMenuItem);
//                    allTextNetworkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
//
//                    allTextNetworkMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent event) {
//                            String str = ReceiverThread.allString;
//                            //  String receiverStr = iReceiverA.GetAllText();
//                            textPane.setText(str);
//
//                        }
//                    });

                }
                menuBar.add(helpMenu);

                {
                    helpMenu.add(manualHelpMenuItem);
                    manualHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            try {
                                java.awt.Desktop.getDesktop().open(new File("doc/manual.pdf"));
                            } catch (IOException e) {
                                System.out.println("Opening manual was unsuccessful");
                                e.printStackTrace();
                            }
                        }
                    });


                    helpMenu.add(howToUseHelpMenuItem);
                    howToUseHelpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    howToUseHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            try {
                                java.awt.Desktop.getDesktop().open(new File("doc/howToUse.pdf"));
                            } catch (IOException e) {
                                System.out.println("Opening manual was unsuccessful");
                                e.printStackTrace();
                            }
                        }
                    });

                    helpMenu.add(aboutUsHelpMenuItem);
                    aboutUsHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            aboutUs dlg = new aboutUs(new JFrame());
                        }
                    });



                }

                // undo
                Document doc = textPane.getDocument();

                doc.addUndoableEditListener(new UndoableEditListener() {

                    public void undoableEditHappened(UndoableEditEvent evt) {
                        undo.addEdit(evt.getEdit());
                    }
                });

                textPane.getActionMap().put("Undo", new AbstractAction("Undo") {

                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canUndo()) {
                                undo.undo();
                            }
                        } catch (CannotUndoException e) {
                        }
                    }
                });
                textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
                //

                //redo

                textPane.getActionMap().put("Redo",
                        new AbstractAction("Redo") {

                            public void actionPerformed(ActionEvent evt) {
                                try {
                                    if (undo.canRedo()) {
                                        undo.redo();
                                    }
                                } catch (CannotRedoException e) {
                                }
                            }
                        });

                textPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");


                {//왼쪽 텍스트


                    DefaultCaret caret = (DefaultCaret) showTextPane.getCaret();
                    caret.setUpdatePolicy((DefaultCaret.ALWAYS_UPDATE));




                    JScrollPane textScrollPane = new JScrollPane(textPane);

                    //클라이언트 보여지는 면

                    //글을 쓸수 없게
                    showTextPane.setEditable(false);
                    //워드랩 되게
                    showTextPane.setLineWrap(true);

                    JScrollPane showTextScrollPane = new JScrollPane(showTextPane);


                    textTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);


                    textTabbedPane.addTab("실시간", showTextScrollPane);
                    textTabbedPane.addTab("전체", textScrollPane);

                    textTabbedPane.setPreferredSize(new Dimension(700, 515));
                    textTabbedPane.setSize(700, 515);

                    add(textTabbedPane, BorderLayout.CENTER);
                    textTabbedPane.setPreferredSize(new Dimension(1040, 515));
                    textScrollPane.setSize(1040, 515);
                    //   splitPane.add(textTabbedPane, JSplitPane);


                    Dimension showText = textTabbedPane.getSize();
                    this.showTextheight = showText.getHeight();

                    limitedLine = (int) this.showTextheight / (this.currentFontHight + 15);

                    showTextPane.setForeground(Color.WHITE);
                    showTextPane.setBackground(Color.BLACK);
                    showTextPane.setFont(new Font("Default", Font.PLAIN, 32));
                    showTextPane.setMargin(new java.awt.Insets(30, 30, 30, 30));
                    showTextPane.setCaretColor(Color.WHITE);
                    showTextPane.setVisible(true);



                    textPane.setForeground(Color.BLACK);
                    textPane.setBackground(Color.WHITE);
                    textPane.setFont(new Font("Default", Font.PLAIN, 32));
                    textPane.setMargin(new java.awt.Insets(30, 30, 30, 30));
                    textPane.setCaretColor(Color.BLACK);
                    textPane.addKeyListener(this);
                    textPane.setVisible(true);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JTextPane GetWrittenTextPane() {
        return this.textPane;
    }

    public JTextArea GetShowTextPane() {
        return this.showTextPane;
    }

    //화면에 표시되는 제한 
    public int GetLimitedLine() {
        return this.limitedLine;
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
    }

    //키를 입력 받았을 때 - 변환글자확인
    public void keyTyped(KeyEvent e) {
    }

    //키를 뗄떼 - 변환, art 키(간축축약표), enter 화자전환 
    public void keyReleased(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //textPane.scrollRectToVisible(new Rectangle(0,textPane.getHeight()+100,1,1));
            String alltext = textPane.getText();
            if (alltext.endsWith("\n\n") && (textPane.getCaretPosition() >= alltext.length())) {
                //              String new_line = (autoTimingSelected) ? timer.elapsedAsStringShort() + " " + NEW_SPEAKER : NEW_SPEAKER;
                //잘 안되고 있는듯
                //아래로 바꾸면 될듯
                //       String new_line =  timer.elapsedAsStringShort()+" "+NEW_SPEAKER ;
                String new_line = NEW_SPEAKER;
                alltext = alltext.concat(new_line);
                textPane.setText(alltext);
            }

        }


    }

    //위치를 받아 글자를 읽느다. 
    private String getInputWord(int pos) {

        String text = new String();
        try {
            text = textPane.getText(0, pos).trim();//처음과 끝의 공백 짤라버리
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
        int spaceIndex = text.lastIndexOf(' ');
        int enterIndex = text.lastIndexOf('\n');
        // if it's a beginning of the sentence 
        if (spaceIndex < enterIndex) {
            //keyActionLog.append("Beginning of the sentence!!\n");
            spaceIndex = enterIndex;
        }
        String lastWord = spaceIndex == -1 ? text : text.substring(spaceIndex + 1, text.length());
        //lastWord = lastWord.replaceAll("[,:;?!\"\']", "");
        CARET_POS = spaceIndex + 1;
        return lastWord.trim();
    }

    public void setTextPane(StringBuilder buffer) {
        textPane.setText(buffer.toString());
        textPane.setCaretPosition(buffer.length());
    }

    public void setTextPane(String str) {
        textPane.setText(str);
        textPane.setCaretPosition(str.length());
    }

    public String getTextPane() {
        return textPane.getText();
    }

    public void setTextFontTracker(FontTracker newFontTracker) {
        int selectedIndex = textTabbedPane.getSelectedIndex();

        if (selectedIndex == 1) {
            textPane.setFont(newFontTracker.getTextFont());
            textPane.setCaretColor(newFontTracker.getForegroundColor());
            textPane.setForeground(newFontTracker.getForegroundColor());
            textPane.setBackground(newFontTracker.getBackgroundColor());
        } else if (selectedIndex == 0) {

            showTextPane.setFont(newFontTracker.getTextFont());
            showTextPane.setCaretColor(newFontTracker.getForegroundColor());
            showTextPane.setForeground(newFontTracker.getForegroundColor());
            showTextPane.setBackground(newFontTracker.getBackgroundColor());
        }

        //show 에 제한 줄 재설정
        Font newFont = newFontTracker.getTextFont();
        this.currentFontHight = newFont.getSize();


        this.limitedLine = (int) this.showTextheight / (this.currentFontHight + 15);
    }

    public void closing() {
        int res = JOptionPane.showConfirmDialog(this,
                "종료 전에 문서를 저장하시겠습니까?",
                "문서 저장 여부",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (!(res > 0)) {
            //try {
            fileActionA.saveDocument();

        }
    }

    public void beforeNew() {
        int res = JOptionPane.showConfirmDialog(this,
                "변경사항을 저장하시겠습니까?",
                "문서 저장 여부",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (!(res > 0)) {
            //try {
            fileActionA.saveDocument();

        }
    }
}
