package view;

import IOStream.OutPutStream;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class RepoView extends JPanel {

    private static final long serialVersionUID = 2737049148313719516L;
    private MainView mainview;
    private IRepoMngrAdapter repoMngrA = null;
    private JTable repoTable = new JTable();
    private RepoTableModel repoTableModel;
    private JPanel buttonPnl = new JPanel(new FlowLayout());
    private JButton addBtn = new JButton("");
    private JButton removeBtn = new JButton("");
    private JButton saveBtn = new JButton("");
    private JButton closeBtn = new JButton("");

    public RepoView(MainView mainview) {
        super();
        this.mainview = mainview;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(340, 435));
        setLayout(new BorderLayout());

        addBtn.setIcon(new javax.swing.ImageIcon("img/add.png"));
        addBtn.setToolTipText("새로운 축약어를 추가합니다.");
        addBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AddAbbrResister addAbbr = new AddAbbrResister(RepoView.this);
                addAbbr.setVisible(true);
            }
        });
        removeBtn.setIcon(new javax.swing.ImageIcon("img/delete.png"));
        removeBtn.setToolTipText("선택된 축약어를 라이브러리에서 제거합니다.");
        removeBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] rows = repoTable.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    repoTableModel.removeRow(rows[i] - i);
                }
            }
        });
        saveBtn.setIcon(new javax.swing.ImageIcon("img/save.png"));

        saveBtn.setToolTipText("현재 상태의 축약어 라이브러리를 저장합니다.");
        saveBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (repoMngrA != null) {
                    repoMngrA.saveRepo(repoTableModel.getWords());
                } else {
                    saveNewRepo();
                }
            }
        });
        closeBtn.setIcon(new javax.swing.ImageIcon("img/close.png"));
        closeBtn.setToolTipText("이 라이브러리 창을 닫습니다.");
        closeBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (repoMngrA != null) {
                    repoMngrA.closeRepo(repoTableModel.getWords());
                } else {
                    mainview.closeRepo(RepoView.this);
                }
            }
        });

        buttonPnl.add(addBtn);
        buttonPnl.add(removeBtn);
        buttonPnl.add(saveBtn);
        buttonPnl.add(closeBtn);
        add(buttonPnl, BorderLayout.PAGE_START);
        buttonPnl.setPreferredSize(new Dimension(340, 40));

        setupRepoTable();
    }

    public RepoTableModel getTableModel() {
        return repoTableModel;
    }

    public void setAdapter(IRepoMngrAdapter adpt) {
        repoMngrA = adpt;
        setupRepoTable();
    }

    public void saveLibrary() {
        if(repoMngrA != null)
                    {
            repoMngrA.saveRepo(repoTableModel.getWords());
        }
    }

    /**
     * Sets up displayPanel and returns.
     * 
     * @param stubs
     *            ICommunication stubs to be displayed in the user list
     * @throws RemoteException
     */
    // 가운데 테이블 만들기
    private void setupRepoTable() {
        if (repoMngrA != null) {
            repoTableModel = new RepoTableModel(repoMngrA.getWords());
        } else {
            repoTableModel = new RepoTableModel();
        }
        repoTable = new JTable(repoTableModel);
        repoTable.setPreferredScrollableViewportSize(new java.awt.Dimension(
                340, 395));
        repoTable.setFillsViewportHeight(true);

        repoTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        repoTable.setColumnSelectionAllowed(true);
        repoTable.setRowSelectionAllowed(true);
        repoTable.getTableHeader().setResizingAllowed(true);

        JScrollPane scroll = new JScrollPane(repoTable);
        scroll.setViewportView(repoTable);
        add(scroll, BorderLayout.CENTER);

        setupConverterColumn(new String[]{";", ".", "/"});
    }

    private void setupConverterColumn(String[] converters) {
        // Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (int i = 0; i < converters.length; i++) {
            comboBox.addItem(converters[i]);
        }
        javax.swing.table.TableColumn converterCol = repoTable.getColumnModel().getColumn(2);
        converterCol.setCellEditor(new javax.swing.DefaultCellEditor(comboBox));
        // 콤보박스 셋업

        // Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("클릭하시면 변환자 옵션을 볼 수 있습니다.");
        converterCol.setCellRenderer(renderer);
    }

    // 테이블에 추가
    public void addWord(String orig, String abbr, String conv) {
        repoTableModel.addRow(orig, abbr, conv);
    }

    // 테이블에서 가져오는 것
    public String getOrigForAbbr(String conv, String abbr) {
        return repoTableModel.getOrigForAbbr(conv, abbr);
    }

    private void saveNewRepo() {
        javax.swing.JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Documents/DeafLib"));
        java.io.File currentFile = null;
        int returnVal = fileChooser.showSaveDialog(mainview);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();

            if (!currentFile.getPath().endsWith(".dat")) {
                String filename = currentFile.getPath();
                filename = filename + ".dat";
                currentFile = new File(filename);
            }
            String a = currentFile.getPath();
            // 파일 저장

            OutPutStream.write(repoTableModel.getWords(), currentFile.getPath());

            mainview.createRepo(currentFile.getName(), currentFile.getPath());
            mainview.closeRepo(RepoView.this);
        }
    }
}
