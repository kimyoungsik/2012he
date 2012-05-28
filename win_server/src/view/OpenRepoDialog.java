package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OpenRepoDialog extends JDialog {

    private static final long serialVersionUID = 3623688064980653364L;
    private MainView owner;
    private GridBagConstraints gbc = new GridBagConstraints();
    //필드
    private JTextField titleField = new JTextField();
    private JTextField sourceField = new JTextField();
    private JLabel warning = new JLabel("");
    private JButton openBtn = new JButton("찾기");
    private JButton confirmBtn = new JButton("확인");
    private JButton cancelBtn = new JButton("취소");

    public OpenRepoDialog(MainView owner) {
        super(owner, "라이브러리 열기", true);
        this.owner = owner;
        init();
    }

    private void init() {

        this.setSize(new Dimension(330, 140));
        this.setLayout(new GridBagLayout());

        titleField.setText(null);
        sourceField.setText(null);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        //다이얼 로그에 배치하기
        addComponent(new JLabel("제목: "), 0, 0, 1);
        addComponent(new JLabel("위치: "), 1, 0, 1);
        addComponent(titleField, 0, 1, 4);
        addComponent(sourceField, 1, 1, 3);

        //오픈 버튼을 클릭했을때
        openBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    loadCsv();
                } catch (IOException e) {
                    warning.setText("IOException");
                    e.printStackTrace();
                }
            }
        });

        //확인버튼을 클릭했을
        confirmBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                //타이틀필드가 비어있을떄
                if (titleField.getText().trim().isEmpty()) {
                    warning.setText("ERROR: Title field is left empty!");
                } // 소스필드가 비어있을때
                else if (sourceField.getText().trim().isEmpty()) {
                    warning.setText("ERROR: Please select csv file.");
                } else {
                    if (owner.createRepo(titleField.getText(), sourceField.getText()) != null) {
                        dispose();
                    } else {
                        warning.setText("ERROR: Creating repository was unsuccessful!");
                    }
                }
            }
        });
        cancelBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addComponent(openBtn, 1, 4, 1);
        addComponent(confirmBtn, 2, 3, 1);
        addComponent(cancelBtn, 2, 2, 1);
        addComponent(warning, 3, 0, 4);
    }

    private void addComponent(Component comp, int row, int col, int width) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        add(comp, gbc);
    }

    private void loadCsv() throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("doc/"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new CustomFilter());
        int returnVal = fileChooser.showOpenDialog(owner);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            sourceField.setText(file.getPath());
            titleField.setText(file.getName());
        }
    }

    private static class CustomFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            // Allow just directories and files with ".txt" extension...
            return file.isDirectory() || file.getAbsolutePath().endsWith(".dat");
        }

        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "(*.dat)";
        }
    }
}
