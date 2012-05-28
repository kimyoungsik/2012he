package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public class MainView extends JFrame implements KeyListener {

    private static final long serialVersionUID = -8358781470076832332L;
    private boolean REPLACE = false; // false by default
    private String REPLACE_INTO = "";
    private int CARET_POS = new Integer(0);
    private IFileActionAdapter fileActionA;
    private ITESModelAdapter modelA;
    // 서버 어댑터
    private IServerAdapter iSerA;
    private IDeafBookAdapter iDeafBA;
    
    
    // private ISenderAdapter iSenderA;
    final UndoManager undo = new UndoManager();
    /**
     * Hash set of converters, [;./1] by default
     */
    private List<String> converters = new ArrayList<String>();// 변환글자
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("파일");
    private JMenu editMenu = new JMenu("편집");
    private JMenu networkMenu = new JMenu("네트워크");
    private JMenu helpMenu = new JMenu("도움말");
    private JMenuItem newFileMenuItem = new JMenuItem("새로만들기");
    private JMenuItem openFileMenuItem = new JMenuItem("열기");
    private JMenuItem saveFileMenuItem = new JMenuItem("저장");
    private JMenuItem saveAsFileMenuItem = new JMenuItem("다른 이름으로 저장");
    private JMenuItem autoSaveMenuItem = new JCheckBoxMenuItem("자동저장");
    private JMenuItem deafBookMenuItem = new JMenuItem("라이브러리 관리");
    private JMenuItem printMenuItem = new JMenuItem("프린트");
    
    private JMenuItem exitMenuItem = new JMenuItem("끝내기");
    private JMenuItem redoMenuItem = new JMenuItem("되살리기");
    private JMenuItem undoMenuItem = new JMenuItem("실행취소");
    private JMenuItem copyEditMenuItem = new JMenuItem("복사");
    private JMenuItem cutEditMenuItem = new JMenuItem("자르기");
    private JMenuItem pasteEditMenuItem = new JMenuItem("붙여넣기");
    private JMenuItem fontMenuItem = new JMenuItem("글자체");
    private JMenuItem serverNetworkMenuItem = new JMenuItem("연결");
    private JMenuItem manualHelpMenuItem = new JMenuItem("메뉴얼");
    private JMenuItem howToUseHelpMenuItem = new JMenuItem("간단사용법");
    private JMenuItem aboutUsHelpMenuItem = new JMenuItem("만든이");
    private JButton newRepositoryBtn = new JButton("");
    private JButton openRepositoryBtn = new JButton("");
    // 속기 텍스트
    private JTextPane textPane = new JTextPane();
    // 축약테이블 탭
    private JTabbedPane reposTabbedPane = new JTabbedPane();
    private static final String NEW_SPEAKER = "> ";

    public MainView(WindowListener wl, IFileActionAdapter fileActionA,
            IEditActionAdapter editActionA, ITESModelAdapter modelA,
            IServerAdapter iSerA, IDeafBookAdapter iDeafBA) {
        super("DEAF Editor 1.0.4");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(wl);
        this.iSerA = iSerA;
        this.iDeafBA = iDeafBA;

        // this.iSenderA = iSenderA;

        this.fileActionA = fileActionA;

        this.modelA = modelA;
        converters.add(".");
        converters.add(";");
        converters.add("/");
        init();

    }

    private void init() {

        try {
            // serial number
            readKey();

            this.autoSaveMenuItem.setSelected(true);

            this.setPreferredSize(new java.awt.Dimension(1040, 540));
            pack();

            // 메뉴넣기
            setJMenuBar(menuBar);
            menuBar.setPreferredSize(new Dimension(1040, 25));
            {

                menuBar.add(fileMenu);

                {
                    fileMenu.add(newFileMenuItem);
                    newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    newFileMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {

                            //자동저장이 체크 되어 있으면
                            if (autoSaveMenuItem.isSelected() && modelA.GetCurrentFile() != null) {
                                //자동저장
                                AutoSave();
                            } else {
                                MainView.this.beforeNew();
                            }
                            fileActionA.newDocument();

                        }
                    });

                    fileMenu.add(openFileMenuItem);
                    openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    openFileMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            fileActionA.openDocument();
                            // timer.stop();
                            // TIMER_STARTED = false;
                        }
                    });

                    fileMenu.add(saveFileMenuItem);
                    saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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

                    fileMenu.add(autoSaveMenuItem);

                    /*
                     * 자동저장 되는 시점
                     * 1. 프로그램 종료시 
                     * 2. 열기 버튼 클릭시
                     * 3. 새로만들기 클릭시
                     * 4. 단축키만들때
                     */
//                                        autoSaveMenuItem.addActionListener(new ActionListener() {
//
//						public void actionPerformed(ActionEvent event) {
////							autoSaveMenuItem.setSelected(false);
//                                                    boolean a = autoSaveMenuItem.isSelected();
//						}
//					});
//                                        
                    fileMenu.add(new JSeparator());

                    fileMenu.add(deafBookMenuItem);
                    deafBookMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    deafBookMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
//							UserTableView userTView = new UserTableView(
//									MainView.this, iDeafBA);

//							userTView.setVisible(true);

                            DeafBookView deafBookView = new DeafBookView(
                                    MainView.this, iDeafBA, modelA);

                            deafBookView.setVisible(true);

                        }
                    });
//                    fileMenu.add(new JSeparator());
//                     
//                    fileMenu.add(printMenuItem);
//                    printMenuItem.addActionListener(new ActionListener() {
//
//                        public void actionPerformed(ActionEvent event) {
//                            
//                            modelA.saveOnefile("print");
////                            PrintSampleApp app = new PrintSampleApp();
////                            PrinterSettingUpDialogPrint printView = new PrinterSettingUpDialogPrint(System.getProperty("user.home") + File.separator + "Documents/DeafDoc/print.txt");
//                            PrintView printView = new PrintView();
//                            printView.printPage(System.getProperty("user.home") + File.separator + "Documents/DeafDoc/print.txt");
//
//                        }
//                    });

                    fileMenu.add(new JSeparator());

                    fileMenu.add(exitMenuItem);
                    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    exitMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            MainView.this.closing();
                            MainView.this.dispose();// ?

                            System.exit(0);
                        }
                    });
                }

                menuBar.add(editMenu);
                {
                    editMenu.add(undoMenuItem);
                    undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    undoMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            try {
                                if (undo.canUndo()) {
                                    undo.undo();
                                }
                            } catch (CannotUndoException e) {
                            }
                        }
                    });

                    editMenu.add(redoMenuItem);
                    redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                    redoMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            try {
                                if (undo.canRedo()) {
                                    undo.redo();
                                }
                            } catch (CannotRedoException e) {
                            }
                        }
                    });

                    editMenu.add(new JSeparator());

                    editMenu.add(cutEditMenuItem);
                    cutEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(copyEditMenuItem);
                    copyEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(pasteEditMenuItem);
                    pasteEditMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    editMenu.add(new JSeparator());

                    editMenu.add(fontMenuItem);
                    fontMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    fontMenuItem.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            FontChooserView fcv = new FontChooserView(
                                    MainView.this);
                            FontTracker ft = new FontTracker(
                                    textPane.getFont(), textPane.getForeground(), textPane.getBackground());
                            fcv.setSelectedTextFont(ft);
                            fcv.setVisible(true);
                        }
                    });
                }

                menuBar.add(networkMenu);

                {
                    networkMenu.add(serverNetworkMenuItem);
                    serverNetworkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    serverNetworkMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            Network_Server dialog = new Network_Server(
                                    iSerA);
                            dialog.setVisible(true);
                        }
                    });

                }
                menuBar.add(helpMenu);

                {
                    helpMenu.add(manualHelpMenuItem);
                    manualHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            try {
                                java.awt.Desktop.getDesktop().open(
                                        new File("doc/manual.pdf"));
                            } catch (IOException e) {
                                System.out.println("Opening manual was unsuccessful");
                                e.printStackTrace();
                            }
                        }
                    });

                    helpMenu.add(howToUseHelpMenuItem);
                    howToUseHelpMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

                    howToUseHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {
                            try {
                                java.awt.Desktop.getDesktop().open(
                                        new File("doc/howToUse.pdf"));
                            } catch (IOException e) {
                                System.out.println("Opening manual was unsuccessful");
                                e.printStackTrace();
                            }
                        }
                    });

                    helpMenu.add(aboutUsHelpMenuItem);

                    aboutUsHelpMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent event) {

                            AutoSave();

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
                textPane.getInputMap().put(
                        KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                        "Undo");
                //

                // redo

                textPane.getActionMap().put("Redo", new AbstractAction("Redo") {

                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (undo.canRedo()) {
                                undo.redo();
                            }
                        } catch (CannotRedoException e) {
                        }
                    }
                });

                textPane.getInputMap().put(
                        KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                        "Redo");

                // Pane 나누기
                JSplitPane splitPane = new JSplitPane();
                splitPane.setContinuousLayout(true);
                splitPane.setOneTouchExpandable(true);
                add(splitPane, BorderLayout.CENTER);
                splitPane.setPreferredSize(new Dimension(1040, 515));

                {// 왼쪽 텍스트

                    JScrollPane textScrollPane = new JScrollPane(textPane);
                    textScrollPane.setPreferredSize(new Dimension(800, 515));
                    textScrollPane.setSize(800, 515);
                    // 텍스트 팬을 스플릿팬에 추가한다.
                    splitPane.add(textScrollPane, JSplitPane.LEFT);

                    textPane.setForeground(Color.WHITE);
                    textPane.setBackground(Color.BLACK);
                    textPane.setFont(new Font("Default", Font.PLAIN, 32));
                    textPane.setMargin(new java.awt.Insets(30, 30, 30, 30));
                    textPane.setCaretColor(Color.WHITE);
                    textPane.addKeyListener(this);
                    textPane.setVisible(true);
                }
                {// 오른쪽 축약 테이블
                    JPanel helperPanel = new JPanel(new BorderLayout());
                    helperPanel.setPreferredSize(new Dimension(200, 515));
                    splitPane.add(helperPanel, JSplitPane.RIGHT);

                    {

                        // 버튼 panel
                        JPanel buttonPnl = new JPanel(new FlowLayout());

                        newRepositoryBtn.setIcon(new javax.swing.ImageIcon(
                                "img/new.png"));
                        newRepositoryBtn.setToolTipText("새 라이브러리 창을 엽니다.");
                        newRepositoryBtn.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {

                                addRepoTab(null);

                            }
                        });

                        openRepositoryBtn.setIcon(new javax.swing.ImageIcon(
                                "img/open.png"));
                        openRepositoryBtn.setToolTipText("라이브러리 창을 불어옵니다.");
                        openRepositoryBtn.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                                // open file loader which has a default
                                // folder of src/doc
                                OpenRepoDialog dialog = new OpenRepoDialog(
                                        MainView.this);
                                dialog.setVisible(true);

                            }
                        });

                        buttonPnl.add(newRepositoryBtn);
                        buttonPnl.add(openRepositoryBtn);
                        buttonPnl.setPreferredSize(new Dimension(200, 40));

                        helperPanel.add(buttonPnl, BorderLayout.NORTH);

                    }
                    {
                        reposTabbedPane.setPreferredSize(new Dimension(200, 515));
                        helperPanel.add(reposTabbedPane, BorderLayout.CENTER);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JTextPane GetWrittenTextPane() {
        return this.textPane;
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
    }

    // 키를 입력 받았을 때 - 변환글자확인
    public void keyTyped(KeyEvent e) {

        String key = "" + e.getKeyChar() + "";
        if (converters.contains(key)) {// 변환글자와 같은것이 있으면
            int pos = textPane.getCaretPosition();
            // 캐럿위치
            String from = getInputWord(pos);
            // 캐럿위치의 글자
            String to = null;
            for (int i = 0; i < reposTabbedPane.getComponentCount(); i++) {

                RepoView currentrepo = (RepoView) reposTabbedPane.getComponent(i);
                to = currentrepo.getOrigForAbbr(key, from);
                if ((to != null) && (!to.isEmpty())) { // careful with
                    // nullPointerError
                    StringBuilder tmpBuffer = new StringBuilder(
                            textPane.getText());

                    if (!from.isEmpty()) {
                        tmpBuffer.replace(pos - from.length(), pos, to);
                    }

                    REPLACE = true;
                    REPLACE_INTO = tmpBuffer.toString();
                    CARET_POS = CARET_POS + to.length();

                    textPane.setText(REPLACE_INTO);

                    textPane.setCaretPosition(pos
                            + (to.length() - from.length()));

                    break;

                }
            }

        }

        if (REPLACE) {
            char c = 0;
            e.setKeyChar(c);

            REPLACE = false;
        }

        // 네트워크에 신호 보내기.
        // network.SenderThread.sender = true;
        threadPool.ServerThread.boolsender = true;
    }

    /**
     * Handle the key released event from the text field. Takes a key event of
     * "alt"(for now) to add a new abbreviation to a repository
     */
    // 키를 뗄떼 - 변환, art 키(간축축약표), enter 화자전환
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            int pos = textPane.getCaretPosition();
            // A new abbreviation for now is added to the focused repoTab.!!!
            if (reposTabbedPane.getSelectedComponent() == null) {
                addRepoTab(null);// 축약띄우기전에
            }

            // 축약 더하기 퀵 테이블

            AddAbbrShortcut addAbbr = new AddAbbrShortcut(
                    (RepoView) reposTabbedPane.getSelectedComponent(),
                    getInputWord(pos));

            addAbbr.setVisible(true);
            //자동저장
            AutoSave();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            String alltext = textPane.getText();
            if (alltext.endsWith("\n\n")
                    && (textPane.getCaretPosition() >= alltext.length())) {

                String new_line = NEW_SPEAKER;
                alltext = alltext.concat(new_line);
                textPane.setText(alltext);
                //자동저장
                AutoSave();
            }
            
            
            //이거 없애야 하는지 체크,, set으로 바꿀지 여부 체크
            threadPool.ServerThread.boolsender = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ALT) {

            int pos = textPane.getCaretPosition();
            // A new abbreviation for now is added to the focused repoTab.!!!
            if (reposTabbedPane.getSelectedComponent() == null) {
                addRepoTab(null);// 축약띄우기전에
            }
            RepoView currentRepoView = (RepoView) reposTabbedPane.getSelectedComponent();
            currentRepoView.addWord(getInputWord(pos), getInputWord(pos).substring(0, 1), ";");
            //자동저장
            AutoSave();
        }

    }

    // 위치를 받아 글자를 읽느다.
    private String getInputWord(int pos) {

        String text = new String();
        try {
            text = textPane.getText(0, pos).trim();// 처음과 끝의 공백 짤라버리
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
        int spaceIndex = text.lastIndexOf(' ');
        int enterIndex = text.lastIndexOf('\n');
        // if it's a beginning of the sentence
        if (spaceIndex < enterIndex) {
            spaceIndex = enterIndex;
        }
        String lastWord = spaceIndex == -1 ? text : text.substring(
                spaceIndex + 1, text.length());
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

    // 불러오기 축약테이블
    public Object createRepo(String title, String source) {
        if (title.endsWith(".dat")) {
            int len_of_title = title.length();
            title = title.substring(0, len_of_title - 4);
        }
        if (modelA.createRepo(title, source) != null) {
            return true;

        }
        return false;
    }

    // 축약 탭설정 / 탭에 새로운 repo 넣기
    public RepoView addRepoTab(String title) {
        RepoView rv = new RepoView(this);
        rv.setSize(340, 435);
        if (title == null) {
            title = "제목없음";
        }
        reposTabbedPane.addTab(title, rv); // inititate a new repository view

        return rv;
    }

    public int getRepoCount() {
        return reposTabbedPane.getComponentCount();
    }

    public void closeRepo(RepoView rv) {
        reposTabbedPane.remove(rv);
        reposTabbedPane.validate();
        reposTabbedPane.repaint();
    }

    public void setTextFontTracker(FontTracker newFontTracker) {
        textPane.setFont(newFontTracker.getTextFont());
        textPane.setCaretColor(newFontTracker.getForegroundColor());
        textPane.setForeground(newFontTracker.getForegroundColor());
        textPane.setBackground(newFontTracker.getBackgroundColor());
    }

    public boolean isAutoSave() {
        return this.autoSaveMenuItem.isSelected();
    }

    //자동저장
    public void AutoSave() {

        //자동저장 여부 확인 and 현재 지정된 파일이 있는지 확인
        if (autoSaveMenuItem.isSelected() && modelA.GetCurrentFile() != null) {
            modelA.saveDocument();
            
            
        }
        
        if(autoSaveMenuItem.isSelected())
        {
            saveLibrary();
        }

    }

    public void closing() {
        int res = JOptionPane.showConfirmDialog(this, "종료 전에 문서를 저장하시겠습니까?",
                "문서 저장 여부", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (!(res > 0)) {
            // try {
            fileActionA.saveDocument();

        }
    }

    public void beforeNew() {
        int res = JOptionPane.showConfirmDialog(this, "변경사항을 저장하시겠습니까?",
                "문서 저장 여부", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (!(res > 0)) {
            // try {
            fileActionA.saveDocument();

        }
    }

    public void readKey() throws IOException {

        try {

            // int ch;

            FileReader fr = new FileReader(System.getProperty("user.home")
                    + File.separator + "Library/deafsoftware.txt");

            fr.close();
            // pop up when using first time
        } catch (FileNotFoundException e) {

            KeyView key = new KeyView();
            key.SetAuthorization(false);
//                        key.authorization = false;
            while (key.GetAuthorization() != true) {
                key.setVisible(true);
            }
            key.dispose();
            File doc = new File(System.getProperty("user.home") + File.separator + "Documents/DeafDoc");
            if (doc.exists() == false) {
                doc.mkdirs();
            }
            File lib = new File(System.getProperty("user.home") + File.separator + "Documents/DeafLib");
            if (lib.exists() == false) {
                lib.mkdirs();
            }
        }
    }
    
    public void saveLibrary()
    {
        
        for (int i = 0; i < reposTabbedPane.getComponentCount(); i++) {

                RepoView repo = (RepoView) reposTabbedPane.getComponent(i);
                repo.saveLibrary();
        }
    }
    
    
    
}
