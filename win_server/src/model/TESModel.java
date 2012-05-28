package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import AbbrRepository.AbbrRepository;

public class TESModel {

    private IMainViewAdapter viewA;
    private File currentFile = null;
    private final JFileChooser fileChooser;
    private Map<String, AbbrRepository> repoList = new HashMap<String, AbbrRepository>();

    public TESModel(IMainViewAdapter viewA) {
        this.viewA = viewA;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("doc/"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public void start() {
        // TODO Auto-generated method stub
        newDocument();
    }

    //open
    private String openHeader() {
        String header_path = new String();
        fileChooser.setFileFilter(new CustomHdrFilter());
        int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File hdr = fileChooser.getSelectedFile();
            header_path = hdr.getPath();
        }
        return header_path;
    }

    //잘못되었음 HDR파일 열다.
    public void newDocument() {


        currentFile = null;

        viewA.setTextPane("");
        viewA.setTitle("제목없음");
        String str = "속기자명:\n교과목명: \n담당교수:\n날짜(연/월/일):\n이용정책: 이 원고는 교육지원 서비스를 받는 학생을 위해 제공되었습니다.\n해당자 이외의 사람에 의한 사용은 금지되어 있습니다. 이 문서에 포함된 정보에는 특권이나 저작권이 있는, 또는 그 밖의 기밀사항을 포함합니다.\n";

        viewA.setTextPane(str);

    }

    //열다 - 어댑터에 이것으로 연결되어 있음
    public void loadDocument() throws IOException {
        fileChooser.setFileFilter(new CustomTxtFilter());
        int returnVal = fileChooser.showOpenDialog(viewA.getMainView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            StringBuilder buffer = new StringBuilder();

            try {
                FileInputStream fis = new FileInputStream(currentFile);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                Reader in = new BufferedReader(isr);

                int ch;
                while ((ch = in.read()) > -1) {
                    buffer.append((char) ch);
                }
                in.close();

                viewA.setTextPane(buffer);
                viewA.setTitle(currentFile.getName());
            } catch (FileNotFoundException fne) {
                JOptionPane.showMessageDialog(viewA.getMainView(), "File Not Found Exception.", "warning", JOptionPane.WARNING_MESSAGE);
                fne.printStackTrace();
            }
        }
    }

    public void saveDocument() throws IOException {

        if (currentFile == null) {
            saveAsDocument();
        } else {
            OutputStreamWriter out = null;
            FileOutputStream fos = new FileOutputStream(currentFile);
            out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

            try {
                String saveString = viewA.getTextPane();
                saveString = saveString.replace("\n", "\r\n");

                out.write(saveString);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            viewA.setTitle(currentFile.getName());
        }
    }

    public void saveAsDocument() throws IOException {
        fileChooser.setFileFilter(new CustomTxtFilter());
        int returnVal = fileChooser.showSaveDialog(viewA.getMainView());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            //check the chosen file's extension
            if (!currentFile.getPath().endsWith(".txt")) {
                String filename = currentFile.getPath();
                filename = filename + ".txt";
                currentFile = new File(filename);
            }
            FileOutputStream fos = new FileOutputStream(currentFile);
            OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
            try {
                String saveString = viewA.getTextPane();
                saveString = saveString.replace("\n", "\r\n");

                out.write(saveString);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            viewA.setTitle(currentFile.getName());
        }
    }

    private static class CustomTxtFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            // Allow just directories and files with ".txt" extension...
            return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
        }

        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    }

    private static class CustomHdrFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File file) {
            // Allow just directories and files with ".txt" extension...
            return file.isDirectory() || file.getAbsolutePath().endsWith(".hdr");
        }

        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Header file for DEAF Editor (*.hdr)";
        }
    }

    public RepoCtrlManager createRepo(String title, String source) {
        RepoCtrlManager rcm = new RepoCtrlManager(this, title, source);

        repoList.put(title, rcm.getRepo());
        viewA.addRepoTab(title, rcm);
        return rcm;
    }

    public void updateRepo(AbbrRepository repo) {
        repoList.put(repo.getName(), repo);
    }

    public void removeRepo(AbbrRepository repo) {
        repoList.remove(repo.getName());
    }
}
